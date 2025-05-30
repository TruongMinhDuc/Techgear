package com.techgear.techgear_be.repositories.order;

import com.techgear.techgear_be.dtos.statistic.StatisticResource;
import com.techgear.techgear_be.models.order.Order;
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
public class OrderProjectionRepository {

    private EntityManager entityManager;

    public List<StatisticResource> getOrderCountByCreateDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticResource> query = cb.createQuery(StatisticResource.class);

        Root<Order> order = query.from(Order.class);
        query.select(cb.construct(StatisticResource.class, order.get("createdAt").as(Instant.class), cb.count(order.get("id"))));
        query.groupBy(order.get("createdAt").as(Instant.class));
        query.orderBy(cb.asc(order.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }

}
