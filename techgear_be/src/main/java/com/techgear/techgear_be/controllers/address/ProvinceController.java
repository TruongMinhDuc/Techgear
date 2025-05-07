package com.techgear.techgear_be.controllers.address;

import com.techgear.techgear_be.services.address.ProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provinces")
@AllArgsConstructor
public class ProvinceController {

    private ProvinceService provinceService;
}
