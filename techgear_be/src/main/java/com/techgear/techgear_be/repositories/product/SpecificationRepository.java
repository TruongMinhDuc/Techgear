package com.techgear.techgear_be.repositories.product;

import com.techgear.techgear_be.models.product.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecificationRepository extends JpaRepository<Specification, Long>, JpaSpecificationExecutor<Specification> {
}
