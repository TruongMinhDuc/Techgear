package com.techgear.techgear_be.controllers.client;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.constants.FieldNameConst;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.client.ClientListedProductResponse;
import com.techgear.techgear_be.dtos.client.ClientProductResponse;
import com.techgear.techgear_be.models.BaseEntityAudit;
import com.techgear.techgear_be.models.product.Product;
import com.techgear.techgear_be.exceptions.ResourceNotFoundException;
import com.techgear.techgear_be.mappers.client.ClientProductMapper;
import com.techgear.techgear_be.projections.SimpleProductInventory;
import com.techgear.techgear_be.repositories.ProjectionRepository;
import com.techgear.techgear_be.repositories.product.ProductRepository;
import com.techgear.techgear_be.repositories.review.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client-api/products")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ClientProductController {

    private ProductRepository productRepository;
    private ProjectionRepository projectionRepository;
    private ClientProductMapper clientProductMapper;
    private ReviewRepository reviewRepository;

    @GetMapping
    public ResponseEntity<ListResponse<ClientListedProductResponse>> getAllProducts(
            @RequestParam(name = "page", defaultValue = ApplicationConst.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = ApplicationConst.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "filter", required = false) @Nullable String filter,
            @RequestParam(name = "sort", required = false) @Nullable String sort,
            @RequestParam(name = "search", required = false) @Nullable String search,
            @RequestParam(name = "saleable", required = false) boolean saleable,
            @RequestParam(name = "newable", required = false) boolean newable
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Product> products = productRepository.findByParams(filter, sort, search, saleable, newable, pageable);

        List<Long> productIds = products.map(Product::getId).toList();
        List<SimpleProductInventory> productInventories = projectionRepository.findSimpleProductInventories(productIds);

        List<ClientListedProductResponse> clientListedProductResponses = products
                .map(product -> clientProductMapper.entityToListedResponse(product, productInventories)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientListedProductResponses, products));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ClientProductResponse> getProduct(@PathVariable String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.PRODUCT, FieldNameConst.SLUG, slug));

        List<SimpleProductInventory> productInventories = projectionRepository
                .findSimpleProductInventories(List.of(product.getId()));

        int averageRatingScore = reviewRepository.findAverageRatingScoreByProductId(product.getId());
        int countReviews = reviewRepository.countByProductId(product.getId());

        Page<Product> relatedProducts = productRepository.findByParams(
                String.format("category.id==%s;id!=%s",
                        Optional.ofNullable(product.getCategory())
                                .map(BaseEntityAudit::getId)
                                .map(Object::toString)
                                .orElse("0"),
                        product.getId()),
                "random",
                null,
                false,
                false,
                PageRequest.of(0, 4));

        List<Long> relatedProductIds = relatedProducts.map(Product::getId).toList();
        List<SimpleProductInventory> relatedProductInventories = projectionRepository
                .findSimpleProductInventories(relatedProductIds);

        List<ClientListedProductResponse> relatedProductResponses = relatedProducts
                .map(p -> clientProductMapper.entityToListedResponse(p, relatedProductInventories)).toList();

        ClientProductResponse clientProductResponse = clientProductMapper
                .entityToResponse(product, productInventories, averageRatingScore, countReviews, relatedProductResponses);

        return ResponseEntity.status(HttpStatus.OK).body(clientProductResponse);
    }

}
