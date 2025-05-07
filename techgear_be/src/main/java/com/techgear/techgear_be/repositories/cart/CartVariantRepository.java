package com.techgear.techgear_be.repositories.cart;

import com.techgear.techgear_be.models.cart.CartVariant;
import com.techgear.techgear_be.models.cart.CartVariantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartVariantRepository extends JpaRepository<CartVariant, CartVariantKey>,
        JpaSpecificationExecutor<CartVariant> {}
