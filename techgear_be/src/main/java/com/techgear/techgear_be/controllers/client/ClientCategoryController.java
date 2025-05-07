package com.techgear.techgear_be.controllers.client;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.constants.FieldNameConst;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.dtos.CollectionWrapper;
import com.techgear.techgear_be.dtos.client.ClientCategoryResponse;
import com.techgear.techgear_be.models.product.Category;
import com.techgear.techgear_be.exceptions.ResourceNotFoundException;
import com.techgear.techgear_be.mappers.client.ClientCategoryMapper;
import com.techgear.techgear_be.repositories.product.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-api/categories")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ClientCategoryController {

    private CategoryRepository categoryRepository;
    private ClientCategoryMapper clientCategoryMapper;

    @GetMapping
    public ResponseEntity<CollectionWrapper<ClientCategoryResponse>> getAllCategories() {
        List<Category> firstCategories = categoryRepository.findByParentCategoryIsNull();
        List<ClientCategoryResponse> clientCategoryResponses = clientCategoryMapper.entityToResponse(firstCategories, 3);
        return ResponseEntity.status(HttpStatus.OK).body(CollectionWrapper.of(clientCategoryResponses));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ClientCategoryResponse> getCategory(@PathVariable("slug") String slug) {
        ClientCategoryResponse clientCategoryResponse = categoryRepository.findBySlug(slug)
                .map(category -> clientCategoryMapper.entityToResponse(category, false))
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.CATEGORY, FieldNameConst.SLUG, slug));
        return ResponseEntity.status(HttpStatus.OK).body(clientCategoryResponse);
    }

}
