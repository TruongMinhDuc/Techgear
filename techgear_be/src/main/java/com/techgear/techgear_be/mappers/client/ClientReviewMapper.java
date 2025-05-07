package com.techgear.techgear_be.mappers.client;

import com.techgear.techgear_be.dtos.client.ClientReviewRequest;
import com.techgear.techgear_be.dtos.client.ClientReviewResponse;
import com.techgear.techgear_be.dtos.client.ClientSimpleReviewResponse;
import com.techgear.techgear_be.models.review.Review;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class ClientReviewMapper {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ClientProductMapper clientProductMapper;

    public Review requestToEntity(ClientReviewRequest request) {
        Review entity = new Review();
        entity.setUser(userRepository.getById(request.getUserId()));
        entity.setProduct(productRepository.getById(request.getProductId()));
        entity.setRatingScore(request.getRatingScore());
        entity.setContent(request.getContent());
        entity.setStatus(request.getStatus());
        return entity;
    }

    public ClientReviewResponse entityToResponse(Review entity) {
        ClientReviewResponse response = new ClientReviewResponse();
        response.setReviewId(entity.getId());
        response.setReviewCreatedAt(entity.getCreatedAt());
        response.setReviewUpdatedAt(entity.getUpdatedAt());
        response.setReviewProduct(clientProductMapper.entityToListedResponse(entity.getProduct(), Collections.emptyList()));
        response.setReviewRatingScore(entity.getRatingScore());
        response.setReviewContent(entity.getContent());
        response.setReviewReply(entity.getReply());
        response.setReviewStatus(entity.getStatus());
        return response;
    }

    public ClientSimpleReviewResponse entityToSimpleResponse(Review entity) {
        ClientSimpleReviewResponse response = new ClientSimpleReviewResponse();
        response.setReviewId(entity.getId());
        response.setReviewCreatedAt(entity.getCreatedAt());
        response.setReviewUpdatedAt(entity.getUpdatedAt());
        response.setReviewUser(new ClientSimpleReviewResponse.UserResponse()
                .setUserId(entity.getUser().getId())
                .setUserUsername(entity.getUser().getUsername())
                .setUserFullname(entity.getUser().getFullname()));
        response.setReviewRatingScore(entity.getRatingScore());
        response.setReviewContent(entity.getContent());
        response.setReviewReply(entity.getReply());
        response.setReviewStatus(entity.getStatus());
        return response;
    }

    public Review partialUpdate(Review entity, ClientReviewRequest request) {
        entity.setRatingScore(request.getRatingScore());
        entity.setContent(request.getContent());
        entity.setStatus(request.getStatus());
        return entity;
    }

}
