package com.shubham.HirePilot.resume.controller;

import com.shubham.HirePilot.resume.entity.Resume;
import com.shubham.HirePilot.resume.repository.ResumeRepository;
import com.shubham.HirePilot.resume.service.ResumeService;
import com.shubham.HirePilot.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    public final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(@RequestParam("file")MultipartFile file,
                                               Authentication authentication) throws IOException{

        //Take userId from Authentication
        User currentUser = (User) authentication.getPrincipal();

        Resume resume = resumeService.uploadResume(file, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resume);
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getUserResume(Authentication authentication){

        User currentUser = (User) authentication.getPrincipal();
        List<Resume> resumes = resumeService.getUserResume(currentUser.getId()); // Hardcoded for now
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/getresume/{resumeId}")
    public ResponseEntity<Resume> getResume(
            @PathVariable UUID resumeId,
            Authentication authentication){

        User currentUser = (User) authentication.getPrincipal();
        Resume resume = resumeService.getResume(resumeId, currentUser.getId());
        return ResponseEntity.ok(resume);
    }

    @DeleteMapping("/delete/{resumeId}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable UUID resumeId,
            Authentication authentication){

        User currentUser = (User) authentication.getPrincipal();
        resumeService.deleteResume(resumeId, currentUser.getId());
        return ResponseEntity.noContent().build();
    }

}
