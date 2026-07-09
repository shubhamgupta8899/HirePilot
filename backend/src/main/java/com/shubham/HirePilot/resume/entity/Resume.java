package com.shubham.HirePilot.resume.entity;

import com.shubham.HirePilot.common.BaseEntity;
import com.shubham.HirePilot.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resumes")
public class Resume extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 100)
    private String contentType;

    @Column(nullable = false)
    private long fileSize;

    @Lob
    @Column(nullable = false)
    private byte[] fileData;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String parsedText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ParseStatus parseStatus = ParseStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_By", nullable = false)
    private User uploadedBy;
}
