package com.techgear.techgear_be.repositories.customer;

import com.techgear.techgear_be.models.customer.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, Long>, JpaSpecificationExecutor<CustomerStatus> {
}