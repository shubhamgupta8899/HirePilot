package com.shubham.HirePilot.resume.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobMatchResponse {

    private Long resumeId;
    private String fileName;
    private Long userId;
    private Double matchScore;
    private String resumeContent;
}
