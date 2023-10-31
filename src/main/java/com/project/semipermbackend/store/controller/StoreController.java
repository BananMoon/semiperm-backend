package com.project.semipermbackend.store.controller;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.common.utils.PaginationUtil;
import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.store.dto.StoreZzimCreationDto;
import com.project.semipermbackend.store.dto.StoreZzimFindDto;
import com.project.semipermbackend.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/store")
@RestController
public class StoreController {

    private final StoreService storeService;

    /**
     * 찜 (저장하기)
     * @param storeZzimCreation  사업장 id
     * @return 생성된/기존의 store_id
     */
    @PostMapping
    public ResponseEntity<ApiResultDto<StoreZzimCreationDto.Response>> zzimStoreSave(@Valid @RequestBody StoreZzimCreationDto.Request storeZzimCreation) {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        StoreZzimCreationDto.Response response = storeService.create(memberId, storeZzimCreation);
        return new ResponseEntity<>(ApiResultDto.success(response), HttpStatus.CREATED);
    }
    // 나의 찜 조회 - 날짜순(default), 리뷰 갯수 순
    @GetMapping
    public ResponseEntity<ApiResultDto<Pagination<StoreZzimFindDto.Response>>> zzimStores(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "perSize", defaultValue = "10", required = false) Integer perSize,
            @RequestParam(name="sorting", defaultValue = "LATEST", required = false) PostSorting sorting) {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        Page<StoreZzimFindDto.Response> response = storeService.find(page - 1, perSize, memberId, sorting);
        Pagination<StoreZzimFindDto.Response> paginationDto = PaginationUtil.pageToPagination(response);
        return new ResponseEntity<>(ApiResultDto.success(paginationDto), HttpStatus.FOUND);
    }

    // 찜 제거
    // TODO 사용자한테 노출되는 pathVariable은 좀 그런가?
    @DeleteMapping("/{placeId}")
    public ResponseEntity<Void> zzimStoreRemove() {

        return ResponseEntity.ok().build();
    }
}
