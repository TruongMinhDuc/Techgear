package com.techgear.techgear_be.repositories.product;

import com.techgear.techgear_be.models.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    List<Category> findByParentCategoryIsNull();

    Optional<Category> findBySlug(String slug);

}
