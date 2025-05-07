package com.techgear.techgear_be.controllers.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.constants.FieldNameConst;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.dtos.client.ClientCartRequest;
import com.techgear.techgear_be.dtos.client.ClientCartResponse;
import com.techgear.techgear_be.dtos.client.ClientCartVariantKeyRequest;
import com.techgear.techgear_be.models.cart.Cart;
import com.techgear.techgear_be.models.cart.CartVariant;
import com.techgear.techgear_be.models.cart.CartVariantKey;
import com.techgear.techgear_be.exceptions.ResourceNotFoundException;
import com.techgear.techgear_be.mappers.client.ClientCartMapper;
import com.techgear.techgear_be.repositories.cart.CartRepository;
import com.techgear.techgear_be.repositories.cart.CartVariantRepository;
import com.techgear.techgear_be.repositories.inventory.DocketVariantRepository;
import com.techgear.techgear_be.utils.InventoryUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-api/carts")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ClientCartController {

    private CartRepository cartRepository;
    private CartVariantRepository cartVariantRepository;
    private ClientCartMapper clientCartMapper;
    private DocketVariantRepository docketVariantRepository;

    @GetMapping
    public ResponseEntity<ObjectNode> getCart(Authentication authentication) {
        String username = authentication.getName();
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode response = cartRepository.findByUsername(username)
                .map(clientCartMapper::entityToResponse)
                .map(clientCartResponse -> mapper.convertValue(clientCartResponse, ObjectNode.class))
                .orElse(mapper.createObjectNode());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ClientCartResponse> saveCart(@RequestBody ClientCartRequest request) {
        final Cart cartBeforeSave;

        if (request.getCartId() == null) {
            cartBeforeSave = clientCartMapper.requestToEntity(request);
        } else {
            cartBeforeSave = cartRepository.findById(request.getCartId())
                    .map(existingEntity -> clientCartMapper.partialUpdate(existingEntity, request))
                    .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.CART, FieldNameConst.ID, request.getCartId()));
        }

        for (CartVariant cartVariant : cartBeforeSave.getCartVariants()) {
            int inventory = InventoryUtils
                    .calculateInventoryIndices(docketVariantRepository.findByVariantId(cartVariant.getCartVariantKey().getVariantId()))
                    .get("canBeSold");
            if (cartVariant.getQuantity() > inventory) {
                throw new RuntimeException("Variant quantity cannot greater than variant inventory");
            }
        }

        Cart cart = cartRepository.save(cartBeforeSave);
        ClientCartResponse clientCartResponse = clientCartMapper.entityToResponse(cart);
        return ResponseEntity.status(HttpStatus.OK).body(clientCartResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItems(@RequestBody List<ClientCartVariantKeyRequest> idRequests) {
        List<CartVariantKey> ids = idRequests.stream()
                .map(idRequest -> new CartVariantKey(idRequest.getCartId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        cartVariantRepository.deleteAllById(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
