package com.shubham.HirePilot.candidate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortlistCandidateRequest {

    @NotNull(message = "Job ID is required")
    private UUID jobId;

    @NotNull(message = "Resume ID is required")
    private UUID resumeId;

    @NotNull(message = "Match score is required")
    private Double matchScore;

    private String hrNotes;  // Optional HR notes
}