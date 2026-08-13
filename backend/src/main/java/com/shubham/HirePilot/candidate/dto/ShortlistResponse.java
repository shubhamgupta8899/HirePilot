package com.shubham.HirePilot.candidate.dto;

import com.shubham.HirePilot.candidate.entity.ShortlistStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortlistResponse {

    private UUID shortlistId;
    private UUID jobId;
    private String jobTitle;
    private UUID resumeId;
    private String resumeFileName;
    private ShortlistStatus status;
    private Double matchScore;
    private String hrNotes;
    private LocalDateTime shortlistedAt;
    private String shortlistedBy;  // HR name
}