package com.shubham.HirePilot.jobdescription.entity;

import com.shubham.HirePilot.common.BaseEntity;
import com.shubham.HirePilot.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_descriptions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDescription extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String department;

    @Column(length = 100)
    private String location;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}
