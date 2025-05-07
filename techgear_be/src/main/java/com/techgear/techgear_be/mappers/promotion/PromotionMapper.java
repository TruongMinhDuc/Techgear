package com.techgear.techgear_be.mappers.promotion;

import com.techgear.techgear_be.dtos.client.ClientPromotionResponse;
import com.techgear.techgear_be.dtos.promotion.PromotionRequest;
import com.techgear.techgear_be.dtos.promotion.PromotionResponse;
import com.techgear.techgear_be.models.product.Category;
import com.techgear.techgear_be.models.product.Product;
import com.techgear.techgear_be.models.promotion.Promotion;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.product.ProductMapper;
import com.techgear.techgear_be.repositories.product.CategoryRepository;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, ProductMapper.class})
public abstract class PromotionMapper implements GenericMapper<Promotion, PromotionRequest, PromotionResponse> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @BeanMapping(qualifiedByName = "addProductsFromCategories")
    @Mapping(source = "productIds", target = "products")
    public abstract Promotion requestToEntity(PromotionRequest request);

    @Override
    @Mapping(source = "productIds", target = "products")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Promotion partialUpdate(@MappingTarget Promotion entity, PromotionRequest request);

    @AfterMapping
    @Named("addProductsFromCategories")
    protected void addProductsFromCategories(@MappingTarget Promotion promotion, PromotionRequest request) {
        if (request.getCategoryIds().size() != 0) {
            Set<Product> productsFromCategories = request.getCategoryIds().stream()
                    .map(categoryRepository::getById)
                    .map(Category::getProducts)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet());

            promotion.setProducts(productsFromCategories);
        }
    }

    @Mapping(source = "id", target = "promotionId")
    @Mapping(source = "percent", target = "promotionPercent")
    public abstract ClientPromotionResponse entityToClientResponse(Promotion promotion);

}
