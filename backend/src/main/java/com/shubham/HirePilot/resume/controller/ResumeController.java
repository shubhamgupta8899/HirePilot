package com.shubham.HirePilot.resume.controller;

import com.shubham.HirePilot.resume.entity.Resume;
import com.shubham.HirePilot.resume.repository.ResumeRepository;
import com.shubham.HirePilot.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    public final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(@RequestParam("file")MultipartFile file,
                                               Authentication authentication) throws IOException{

        //Take userId from Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Resume resume = resumeService.uploadResume(file, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(resume);
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getUserResume(Authentication authentication){

        List<Resume> resumes = resumeService.getUserResume(1L); // Hardcoded for now
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<Resume> getResume(
            @PathVariable Long resumeId,
            Authentication authentication){

        Resume resume = resumeService.getResume(resumeId, 1L);
        return ResponseEntity.ok(resume);
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable Long resumeId,
            Authentication authentication){

        resumeService.deleteResume(resumeId, 1L);
        return ResponseEntity.noContent().build();
    }

}
