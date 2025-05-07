package com.techgear.techgear_be.models.reward;

import com.techgear.techgear_be.models.BaseEntityAudit;
import com.techgear.techgear_be.models.authentication.User;
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
@Table(name = "reward_log")
public class RewardLog extends BaseEntityAudit {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardType type;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "note", nullable = false)
    private String note;
}
