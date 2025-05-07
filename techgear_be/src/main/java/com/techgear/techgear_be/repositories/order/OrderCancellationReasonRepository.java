package com.techgear.techgear_be.repositories.order;

import com.techgear.techgear_be.models.order.OrderCancellationReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderCancellationReasonRepository extends JpaRepository<OrderCancellationReason, Long>,
        JpaSpecificationExecutor<OrderCancellationReason> {}
