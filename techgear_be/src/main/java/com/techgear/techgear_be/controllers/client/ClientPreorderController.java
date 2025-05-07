package com.techgear.techgear_be.controllers.client;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.constants.FieldNameConst;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.client.ClientPreorderRequest;
import com.techgear.techgear_be.dtos.client.ClientPreorderResponse;
import com.techgear.techgear_be.models.client.Preorder;
import com.techgear.techgear_be.exceptions.ResourceNotFoundException;
import com.techgear.techgear_be.mappers.client.ClientPreorderMapper;
import com.techgear.techgear_be.repositories.client.PreorderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client-api/preorders")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ClientPreorderController {

    private PreorderRepository preorderRepository;
    private ClientPreorderMapper clientPreorderMapper;

    private static final String PREORDER_SORT = "updatedAt,desc";

    @GetMapping
    public ResponseEntity<ListResponse<ClientPreorderResponse>> getAllPreorders(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = ApplicationConst.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = ApplicationConst.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = PREORDER_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        String username = authentication.getName();
        Page<Preorder> preorders = preorderRepository.findAllByUsername(username, sort, filter, PageRequest.of(page - 1, size));
        List<ClientPreorderResponse> clientPreorderResponses = preorders.map(clientPreorderMapper::entityToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientPreorderResponses, preorders));
    }

    @PostMapping
    public ResponseEntity<ClientPreorderResponse> createPreorder(@RequestBody ClientPreorderRequest request) throws Exception {
        Optional<Preorder> preorderOpt = preorderRepository.findByUser_IdAndProduct_Id(request.getUserId(), request.getProductId());

        if (preorderOpt.isPresent()) {
            Preorder preorder = preorderOpt.get();

            if (preorder.getStatus().equals(1)) {
                throw new Exception("Duplicated preorder");
            } else {
                preorder.setUpdatedAt(Instant.now());
                preorder.setStatus(1);
                preorder = preorderRepository.save(preorder);
                return ResponseEntity.status(HttpStatus.OK).body(clientPreorderMapper.entityToResponse(preorder));
            }
        } else {
            Preorder entity = clientPreorderMapper.requestToEntity(request);
            entity = preorderRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientPreorderMapper.entityToResponse(entity));
        }
    }

    @PutMapping
    public ResponseEntity<ClientPreorderResponse> updatePreorder(@RequestBody ClientPreorderRequest request) {
        ClientPreorderResponse clientPreorderResponse = preorderRepository
                .findByUser_IdAndProduct_Id(request.getUserId(), request.getProductId())
                .map(existingEntity -> clientPreorderMapper.partialUpdate(existingEntity, request))
                .map(preorderRepository::save)
                .map(clientPreorderMapper::entityToResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResourceNameConst.PREORDER,
                        List.of(FieldNameConst.USER_ID, FieldNameConst.PRODUCT_ID).toString(),
                        List.of(request.getUserId(), request.getProductId())));
        return ResponseEntity.status(HttpStatus.OK).body(clientPreorderResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePreorders(@RequestBody List<Long> ids) {
        preorderRepository.deleteAllById(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
