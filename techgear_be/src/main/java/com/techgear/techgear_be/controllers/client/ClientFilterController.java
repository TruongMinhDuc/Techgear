package com.techgear.techgear_be.controllers.client;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.client.ClientBrandResponse;
import com.techgear.techgear_be.dtos.client.ClientFilterResponse;
import com.techgear.techgear_be.models.product.Brand;
import com.techgear.techgear_be.repositories.product.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-api/filters")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ClientFilterController {

    private BrandRepository brandRepository;

    @GetMapping("/category")
    public ResponseEntity<ClientFilterResponse> getFilterByCategorySlug(@RequestParam String slug) {
        List<Brand> brands = brandRepository.findByCategorySlug(slug);
        ClientFilterResponse clientFilterResponse = new ClientFilterResponse();
        clientFilterResponse.setFilterBrands(mapToClientBrandResponse(brands));
        return ResponseEntity.status(HttpStatus.OK).body(clientFilterResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ClientFilterResponse> getFilterBySearchQuery(@RequestParam String query) {
        List<Brand> brands = brandRepository.findBySearchQuery(query);
        ClientFilterResponse clientFilterResponse = new ClientFilterResponse();
        clientFilterResponse.setFilterBrands(mapToClientBrandResponse(brands));
        return ResponseEntity.status(HttpStatus.OK).body(clientFilterResponse);
    }

    private List<ClientBrandResponse> mapToClientBrandResponse(List<Brand> brands) {
        return brands.stream()
                .map(brand -> new ClientBrandResponse()
                        .setBrandId(brand.getId())
                        .setBrandName(brand.getName()))
                .collect(Collectors.toList());
    }

}
