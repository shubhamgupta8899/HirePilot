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
public class RejectCandidateRequest {

    @NotNull(message = "Job ID is required")
    private UUID jobId;

    @NotNull(message = "Resume ID is required")
    private UUID resumeId;

    private String reason;  // Why rejected
}