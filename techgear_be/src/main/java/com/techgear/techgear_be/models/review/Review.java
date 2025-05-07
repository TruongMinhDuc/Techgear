package com.techgear.techgear_be.models.review;

import com.techgear.techgear_be.models.BaseEntityAudit;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "review", uniqueConstraints = @UniqueConstraint(name = "uc_review", columnNames = {"user_id", "product_id"}))
public class Review extends BaseEntityAudit {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "rating_score", nullable = false, columnDefinition = "TINYINT")
    private Integer ratingScore;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "reply", columnDefinition = "TEXT")
    private String reply;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;
}
