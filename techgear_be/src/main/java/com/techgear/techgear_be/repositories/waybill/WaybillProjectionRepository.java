package com.techgear.techgear_be.repositories.waybill;

import com.techgear.techgear_be.dtos.statistic.StatisticResource;
import com.techgear.techgear_be.models.waybill.Waybill;
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
public class WaybillProjectionRepository {

    private EntityManager entityManager;

    public List<StatisticResource> getWaybillCountByCreateDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticResource> query = cb.createQuery(StatisticResource.class);

        Root<Waybill> waybill = query.from(Waybill.class);
        query.select(cb.construct(StatisticResource.class, waybill.get("createdAt").as(Instant.class), cb.count(waybill.get("id"))));
        query.groupBy(waybill.get("createdAt").as(Instant.class));
        query.orderBy(cb.asc(waybill.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }

}
