package com.techgear.techgear_be.services.general;

import com.techgear.techgear_be.dtos.general.UploadedImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    UploadedImageResponse store(MultipartFile image);

    Resource load(String imageName);

    void delete(String imageName);

}
