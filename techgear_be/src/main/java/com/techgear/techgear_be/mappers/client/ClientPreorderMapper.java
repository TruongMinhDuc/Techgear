package com.techgear.techgear_be.mappers.client;

import com.techgear.techgear_be.dtos.client.ClientPreorderRequest;
import com.techgear.techgear_be.dtos.client.ClientPreorderResponse;
import com.techgear.techgear_be.models.client.Preorder;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;

@Component
@AllArgsConstructor
public class ClientPreorderMapper {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ClientProductMapper clientProductMapper;

    public Preorder requestToEntity(ClientPreorderRequest request) {
        Preorder entity = new Preorder();
        entity.setUser(userRepository.getById(request.getUserId()));
        entity.setProduct(productRepository.getById(request.getProductId()));
        entity.setStatus(request.getStatus());
        return entity;
    }

    public ClientPreorderResponse entityToResponse(Preorder entity) {
        ClientPreorderResponse response = new ClientPreorderResponse();
        response.setPreorderId(entity.getId());
        response.setPreorderCreatedAt(entity.getCreatedAt());
        response.setPreorderUpdatedAt(entity.getUpdatedAt());
        response.setPreorderProduct(clientProductMapper.entityToListedResponse(entity.getProduct(), Collections.emptyList()));
        response.setPreorderStatus(entity.getStatus());
        return response;
    }

    public Preorder partialUpdate(Preorder entity, ClientPreorderRequest request) {
        entity.setUpdatedAt(Instant.now());
        entity.setStatus(request.getStatus());
        return entity;
    }

}
