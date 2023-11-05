package com.project.semipermbackend.review.controller;

import com.project.semipermbackend.review.dto.ReviewCreation;
import com.project.semipermbackend.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> memberCreation(@Valid @RequestBody ReviewCreation.RequestDto requestDto) {
        reviewService.create(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
