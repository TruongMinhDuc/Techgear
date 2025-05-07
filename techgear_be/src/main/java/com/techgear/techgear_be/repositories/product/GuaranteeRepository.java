package com.techgear.techgear_be.repositories.product;

import com.techgear.techgear_be.models.product.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuaranteeRepository extends JpaRepository<Guarantee, Long>, JpaSpecificationExecutor<Guarantee> {
}