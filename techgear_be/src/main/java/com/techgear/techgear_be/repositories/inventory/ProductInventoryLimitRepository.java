package com.techgear.techgear_be.repositories.inventory;

import com.techgear.techgear_be.models.inventory.ProductInventoryLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductInventoryLimitRepository extends JpaRepository<ProductInventoryLimit, Long>,
        JpaSpecificationExecutor<ProductInventoryLimit> {}
