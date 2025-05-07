package com.techgear.techgear_be.repositories.product;

import com.techgear.techgear_be.constants.SearchFieldsConst;
import com.techgear.techgear_be.models.inventory.Docket;
import com.techgear.techgear_be.models.inventory.DocketVariant;
import com.techgear.techgear_be.models.product.Product;
import com.techgear.techgear_be.models.product.Variant;
import com.techgear.techgear_be.utils.SearchUtils;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import io.github.perplexhub.rsql.RSQLCustomPredicate;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    default Page<Product> findDocketedProducts(Pageable pageable) {
        Specification<Product> spec = (root, query, cb) -> {
            Join<Product, Variant> variant = root.join("variants");
            Join<Variant, DocketVariant> docketVariant = variant.join("docketVariants");

            query.distinct(false);
            query.orderBy(cb.desc(docketVariant.get("docket").get("id")));

            return query.getRestriction();
        };

        return findAll(spec, pageable);
    }

    default Page<Product> findByParams(String filter,
                                       String sort,
                                       String search,
                                       boolean saleable,
                                       boolean newable,
                                       Pageable pageable) {
        RSQLCustomPredicate<String> jsonPredicate = new RSQLCustomPredicate<>(
                new ComparisonOperator("=json=", true),
                String.class,
                input -> {
                    CriteriaBuilder cb = input.getCriteriaBuilder();

                    Object[] values = input.getArguments().stream().skip(1).toArray();

                    return cb.function("JSON_EXTRACT", String.class,
                            input.getPath(),
                            cb.function("REPLACE", String.class,
                                    cb.function("JSON_UNQUOTE", String.class,
                                            cb.function("JSON_SEARCH", String.class,
                                                    input.getPath(),
                                                    cb.literal("one"),
                                                    cb.literal(input.getArguments().get(0)))
                                    ),
                                    cb.literal(".code"),
                                    cb.literal(".value")
                            )
                    ).in(values);
                });

        Specification<Product> filterable = RSQLJPASupport.toSpecification(filter, List.of(jsonPredicate));
        Specification<Product> searchable = SearchUtils.parse(search, SearchFieldsConst.CLIENT_PRODUCT);

        Specification<Product> docketable = (root, query, cb) -> {
            List<Predicate> wheres = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            Join<Product, Variant> variant = root.join("variants");
            Join<Variant, DocketVariant> docketVariant = variant.join("docketVariants");
            Join<DocketVariant, Docket> docket = docketVariant.join("docket");

            if (saleable) {
                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<Variant> variantSq = subquery.from(Variant.class);
                Join<Variant, DocketVariant> docketVariantSq = variantSq.join("docketVariants");
                Join<DocketVariant, Docket> docketSq = docketVariantSq.join("docket");

                subquery.select(cb.diff(
                        cb.sum(
                                cb.<Integer>selectCase()
                                        .when(cb.and(cb.equal(docketSq.get("type"), 1),
                                                        cb.equal(docketSq.get("status"), 3)),
                                                docketVariantSq.get("quantity"))
                                        .when(cb.and(cb.equal(docketSq.get("type"), 2),
                                                        cb.equal(docketSq.get("status"), 3)),
                                                cb.prod(docketVariantSq.get("quantity"), -1))
                                        .otherwise(0)
                        ),
                        cb.sum(
                                cb.<Integer>selectCase()
                                        .when(cb.and(cb.equal(docketSq.get("type"), 2),
                                                        docketSq.get("status").in(1, 2)),
                                                docketVariantSq.get("quantity"))
                                        .otherwise(0)
                        )
                ));

                subquery.where(cb.equal(variantSq.get("product").get("id"), root.get("id")));
                subquery.groupBy(variantSq.get("product").get("id"));

                wheres.add(cb.greaterThan(subquery, 0));
            }

            if ("lowest-price".equals(sort)) {
                orders.add(cb.asc(cb.min(variant.get("price"))));
            }

            if ("highest-price".equals(sort)) {
                orders.add(cb.desc(cb.max(variant.get("price"))));
            }

            if ("random".equals(sort)) {
                orders.add(cb.asc(cb.function("RAND", Void.class)));
            }

            if (newable) {
                wheres.add(cb.equal(docket.get("type"), 1));
                wheres.add(cb.equal(docket.get("status"), 3));

                orders.add(cb.desc(cb.max(docket.get("createdAt"))));
                orders.add(cb.asc(root.get("id")));
            }

            Optional.ofNullable(filterable.toPredicate(root, query, cb)).ifPresent(wheres::add);
            Optional.ofNullable(searchable.toPredicate(root, query, cb)).ifPresent(wheres::add);

            query.where(wheres.toArray(Predicate[]::new));
            query.groupBy(root.get("id"));
            query.orderBy(orders);

            return query.getRestriction();
        };

        List<Product> products = findAll(docketable);
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), products.size());

        return new PageImpl<>(products.subList(start, end), pageable, products.size());
    }

    Optional<Product> findBySlug(String slug);


    @Query("SELECT COUNT(p.id) FROM Product p")
    int countByProductId();

}
