package com.techgear.techgear_be.repositories.authentication;

import com.techgear.techgear_be.dtos.statistic.StatisticResource;
import com.techgear.techgear_be.models.authentication.User;
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
public class UserProjectionRepository {

    private EntityManager entityManager;

    public List<StatisticResource> getUserCountByCreateDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticResource> query = cb.createQuery(StatisticResource.class);

        Root<User> user = query.from(User.class);
        query.select(cb.construct(StatisticResource.class, user.get("createdAt").as(Instant.class), cb.count(user.get("id"))));
        query.groupBy(user.get("createdAt").as(Instant.class));
        query.orderBy(cb.asc(user.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }

}
