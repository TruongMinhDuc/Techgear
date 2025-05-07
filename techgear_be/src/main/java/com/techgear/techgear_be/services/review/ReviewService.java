package com.techgear.techgear_be.services.review;

import com.techgear.techgear_be.dtos.review.ReviewRequest;
import com.techgear.techgear_be.dtos.review.ReviewResponse;
import com.techgear.techgear_be.services.CrudService;

public interface ReviewService extends CrudService<Long, ReviewRequest, ReviewResponse> {}
