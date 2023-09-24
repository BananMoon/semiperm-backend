package com.project.semipermbackend.store.service;

import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.store.Store;
import com.project.semipermbackend.domain.store.StoreRepository;
import com.project.semipermbackend.member.service.MemberService;
import com.project.semipermbackend.store.dto.StoreSaveCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
@RequiredArgsConstructor
@Service
public class StoreService {
    private final MemberService memberService;
//    private final StoreRepository storeRepository;

    public StoreSaveCreationDto.Response create(Long memberId, StoreSaveCreationDto.Request storeSaveCreation) {

        // 기존의 plcae_id 있는지 확인 => 인코딩
        // base64 : binary를 ascii 영역의 문자열로 인코딩
        // utf-8 : 유니코드 문자를 8비트 값으로 인코딩.
        String encodedMemberId = Base64.getEncoder().encodeToString(storeSaveCreation.getPlaceId().getBytes());

        //멤버 조회
        Member member = memberService.getMemberByMemberId(memberId);

        Store store;
//        storeRepository.save()
        return null;
    }
}
