package com.techgear.techgear_be.repositories.inventory;

import com.techgear.techgear_be.models.inventory.CountVariant;
import com.techgear.techgear_be.models.inventory.CountVariantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountVariantRepository extends JpaRepository<CountVariant, CountVariantKey>,
        JpaSpecificationExecutor<CountVariant> {}
