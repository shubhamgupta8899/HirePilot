package com.shubham.HirePilot.candidate.entity;

import com.shubham.HirePilot.common.BaseEntity;
import com.shubham.HirePilot.jobdescription.entity.JobDescription;
import com.shubham.HirePilot.resume.entity.Resume;
import com.shubham.HirePilot.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "job_application")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateShortlist extends BaseEntity {

    @JoinColumn(name = "job_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private JobDescription job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShortlistStatus status;

    @Column(columnDefinition = "TEXT")
    private String hrNotes;   // HR notes

    @Column
    private Double matchScore;   // Resume matching score

    @Column
    private LocalDateTime appliedAt;

    @Column
    private LocalDateTime shortlistedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User shortlistedBy;  // HR who shortlisted
}
