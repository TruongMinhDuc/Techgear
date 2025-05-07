package com.techgear.techgear_be.mappers.client;

import com.techgear.techgear_be.dtos.client.ClientWishRequest;
import com.techgear.techgear_be.dtos.client.ClientWishResponse;
import com.techgear.techgear_be.models.client.Wish;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class ClientWishMapper {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ClientProductMapper clientProductMapper;

    public Wish requestToEntity(ClientWishRequest request) {
        Wish entity = new Wish();
        entity.setUser(userRepository.getById(request.getUserId()));
        entity.setProduct(productRepository.getById(request.getProductId()));
        return entity;
    }

    public ClientWishResponse entityToResponse(Wish entity) {
        ClientWishResponse response = new ClientWishResponse();
        response.setWishId(entity.getId());
        response.setWishCreatedAt(entity.getCreatedAt());
        response.setWishProduct(clientProductMapper.entityToListedResponse(entity.getProduct(), Collections.emptyList()));
        return response;
    }

}
