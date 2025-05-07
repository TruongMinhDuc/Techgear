package com.techgear.techgear_be.repositories.order;

import com.techgear.techgear_be.models.order.OrderVariant;
import com.techgear.techgear_be.models.order.OrderVariantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderVariantRepository extends JpaRepository<OrderVariant, OrderVariantKey>,
        JpaSpecificationExecutor<OrderVariant> {}
