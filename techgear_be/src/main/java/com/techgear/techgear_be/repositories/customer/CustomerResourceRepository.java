package com.techgear.techgear_be.repositories.customer;

import com.techgear.techgear_be.models.customer.CustomerResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerResourceRepository extends JpaRepository<CustomerResource, Long>, JpaSpecificationExecutor<CustomerResource> {
}