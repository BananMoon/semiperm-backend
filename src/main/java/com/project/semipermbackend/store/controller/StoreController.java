package com.project.semipermbackend.store.controller;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.store.dto.StoreSaveCreationDto;
import com.project.semipermbackend.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/store")
@RestController
public class StoreController {

    private final StoreService storeService;

    // 찜 (저장하기)
    // memberId, request : place id
    // return :생성된/기존의 store_id
    @PostMapping()
    public ResponseEntity<Void> saveStore(@RequestBody StoreSaveCreationDto.Request storeSaveCreation) {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        StoreSaveCreationDto.Response response = storeService.create(memberId, storeSaveCreation);
        return null;
    }

}
