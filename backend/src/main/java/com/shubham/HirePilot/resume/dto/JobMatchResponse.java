package com.shubham.HirePilot.resume.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobMatchResponse {

    private UUID resumeId;
    private String fileName;
    private UUID userId;
    private Double matchScore;
    private String resumeContent;
}
