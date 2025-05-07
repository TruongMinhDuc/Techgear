package com.techgear.techgear_be.models.general;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techgear.techgear_be.models.BaseEntityAudit;
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
@Table(name = "image")
public class Image extends BaseEntityAudit {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private Long size;


    @Column(name = "`group`", nullable = false)
    private String group;

    @Column(name = "is_thumbnail", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isThumbnail;

    @Column(name = "is_eliminated", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isEliminated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
}
