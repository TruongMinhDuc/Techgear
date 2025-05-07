package com.techgear.techgear_be.controllers.inventory;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.constants.FieldNameConst;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.inventory.ProductInventoryResponse;
import com.techgear.techgear_be.dtos.inventory.VariantInventoryResponse;
import com.techgear.techgear_be.models.inventory.DocketVariant;
import com.techgear.techgear_be.models.product.Product;
import com.techgear.techgear_be.models.product.Variant;
import com.techgear.techgear_be.exceptions.ResourceNotFoundException;
import com.techgear.techgear_be.mappers.product.ProductInventoryMapper;
import com.techgear.techgear_be.mappers.product.VariantInventoryMapper;
import com.techgear.techgear_be.projections.ProductInventory;
import com.techgear.techgear_be.projections.VariantInventory;
import com.techgear.techgear_be.repositories.inventory.DocketVariantRepository;
import com.techgear.techgear_be.repositories.product.ProductRepository;
import com.techgear.techgear_be.repositories.product.VariantRepository;
import com.techgear.techgear_be.utils.InventoryUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class InventoryController {

    private ProductRepository productRepository;
    private DocketVariantRepository docketVariantRepository;
    private ProductInventoryMapper productInventoryMapper;
    private VariantRepository variantRepository;
    private VariantInventoryMapper variantInventoryMapper;

    @GetMapping("/product-inventories")
    public ResponseEntity<ListResponse<ProductInventoryResponse>> getProductInventories(
            @RequestParam(name = "page", defaultValue = ApplicationConst.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = ApplicationConst.DEFAULT_PAGE_SIZE) int size
    ) {
        Page<Product> products = productRepository.findDocketedProducts(PageRequest.of(page - 1, size));

        List<ProductInventory> productInventories = new ArrayList<>();

        for (Product product : products) {
            ProductInventory productInventory = new ProductInventory();

            productInventory.setProduct(product);

            List<DocketVariant> transactions = docketVariantRepository.findByProductId(product.getId());
            productInventory.setTransactions(transactions);

            Map<String, Integer> inventoryIndices = InventoryUtils.calculateInventoryIndices(transactions);

            productInventory.setInventory(inventoryIndices.get("inventory"));
            productInventory.setWaitingForDelivery(inventoryIndices.get("waitingForDelivery"));
            productInventory.setCanBeSold(inventoryIndices.get("canBeSold"));
            productInventory.setAreComing(inventoryIndices.get("areComing"));

            productInventories.add(productInventory);
        }

        List<ProductInventoryResponse> productInventoryResponses = productInventoryMapper.toResponse(productInventories);

        return ResponseEntity.status(HttpStatus.OK).body(new ListResponse<>(productInventoryResponses, products));
    }

    @GetMapping("/variant-inventories")
    public ResponseEntity<ListResponse<VariantInventoryResponse>> getVariantInventories(
            @RequestParam(name = "page", defaultValue = ApplicationConst.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = ApplicationConst.DEFAULT_PAGE_SIZE) int size
    ) {
        Page<Variant> variants = variantRepository.findDocketedVariants(PageRequest.of(page - 1, size));

        List<VariantInventory> variantInventories = new ArrayList<>();

        for (Variant variant : variants) {
            VariantInventory variantInventory = new VariantInventory();

            variantInventory.setVariant(variant);

            List<DocketVariant> transactions = docketVariantRepository.findByVariantId(variant.getId());
            variantInventory.setTransactions(transactions);

            Map<String, Integer> inventoryIndices = InventoryUtils.calculateInventoryIndices(transactions);

            variantInventory.setInventory(inventoryIndices.get("inventory"));
            variantInventory.setWaitingForDelivery(inventoryIndices.get("waitingForDelivery"));
            variantInventory.setCanBeSold(inventoryIndices.get("canBeSold"));
            variantInventory.setAreComing(inventoryIndices.get("areComing"));

            variantInventories.add(variantInventory);
        }

        List<VariantInventoryResponse> variantInventoryResponses = variantInventoryMapper.toResponse(variantInventories);

        return ResponseEntity.status(HttpStatus.OK).body(new ListResponse<>(variantInventoryResponses, variants));
    }

    @GetMapping("/variant-inventories/{variantId}")
    public ResponseEntity<VariantInventoryResponse> getVariantInventory(@PathVariable("variantId") Long variantId) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.VARIANT, FieldNameConst.ID, variantId));

        VariantInventory variantInventory = new VariantInventory();

        variantInventory.setVariant(variant);

        List<DocketVariant> transactions = docketVariantRepository.findByVariantId(variant.getId());
        variantInventory.setTransactions(transactions);

        Map<String, Integer> inventoryIndices = InventoryUtils.calculateInventoryIndices(transactions);

        variantInventory.setInventory(inventoryIndices.get("inventory"));
        variantInventory.setWaitingForDelivery(inventoryIndices.get("waitingForDelivery"));
        variantInventory.setCanBeSold(inventoryIndices.get("canBeSold"));
        variantInventory.setAreComing(inventoryIndices.get("areComing"));

        VariantInventoryResponse variantInventoryResponse = variantInventoryMapper.toResponse(variantInventory);

        return ResponseEntity.status(HttpStatus.OK).body(variantInventoryResponse);
    }

}
