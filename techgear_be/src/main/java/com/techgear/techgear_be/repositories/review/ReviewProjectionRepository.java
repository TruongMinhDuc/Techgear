package com.techgear.techgear_be.repositories.review;

import com.techgear.techgear_be.dtos.statistic.StatisticResource;
import com.techgear.techgear_be.models.review.Review;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

@Repository
@AllArgsConstructor
public class ReviewProjectionRepository {

    private EntityManager entityManager;

    public List<StatisticResource> getReviewCountByCreateDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticResource> query = cb.createQuery(StatisticResource.class);

        Root<Review> review = query.from(Review.class);
        query.select(cb.construct(StatisticResource.class, review.get("createdAt").as(Instant.class), cb.count(review.get("id"))));
        query.groupBy(review.get("createdAt").as(Instant.class));
        query.orderBy(cb.asc(review.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }

}
