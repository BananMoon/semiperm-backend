package com.project.semipermbackend.post.controller;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.common.utils.PaginationUtil;
import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.post.dto.PostUpdate;
import com.project.semipermbackend.post.dto.PostFindDto;
import com.project.semipermbackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/community")
@RestController
public class PostController {
    private final PostService postService;

    // 게시글 등록
    @PostMapping("/post")
    public ResponseEntity<ApiResultDto<PostCreation.ResponseDto>> postCreation(@Valid @RequestBody PostCreation.RequestDto postCreation) {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        PostCreation.ResponseDto responseDto = postService.create(memberId, postCreation);

        return new ResponseEntity<>(ApiResultDto.success(responseDto), HttpStatus.CREATED);
    }

    // 게시글 상세 조회 (No Filter, No Sort)
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResultDto<PostFindDto.Response>> getPostDetail(@PathVariable Long postId) {

        PostFindDto.Response response = postService.getOne(postId);
        return new ResponseEntity<>(ApiResultDto.success(response), HttpStatus.CREATED);

    }

    /**
     * 게시글 전체 조회 (sortedPost)
     * sorting 필드에 따라 정렬하여 페이징 처리
     *
     * @param page
     * @param perSize
     * @param filteredSurgeryCategory 시술 카테고리 필터링
     * @param sorting                 필드에 따라 정렬 (최신순, 인기순, 좋아요순) - 좋아요순 vs 인기순 차이?
     *                                - 인기순 : 좋아요 + 댓글 수
     *                                - 좋아요순 : 좋아요
     * @param filteredPostCategory    게시글 성격 필터링 (전체, 고민, 정보, 자유게시글)
     */
    @GetMapping("/post")
    public ResponseEntity<ApiResultDto<Pagination<PostFindDto.Response>>> getAllPost(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "perSize", defaultValue = "10", required = false) Integer perSize,
            @RequestParam(name = "surgeryCategory", defaultValue = "TOTAL", required = false) SurgeryCategory filteredSurgeryCategory,
            @RequestParam(name = "postCategory", defaultValue = "TOTAL", required = false) PostCategory filteredPostCategory,
            @RequestParam(name = "sorting", defaultValue = "LATEST", required = false) PostSorting sorting
    ) {

        Page<PostFindDto.Response> postsPage = postService.getAll(page - 1, perSize, filteredSurgeryCategory, filteredPostCategory, sorting);
        Pagination<PostFindDto.Response> postsPagination = PaginationUtil.pageToPagination(postsPage);

        return new ResponseEntity<>(ApiResultDto.success(postsPagination), HttpStatus.FOUND);
    }

    // 커뮤니티 키워드 조회
    @GetMapping("/post/search")
    public ResponseEntity<ApiResultDto<Pagination<PostFindDto.Response>>> searchPost(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(name = "sorting", defaultValue = "LATEST", required = false) PostSorting sorting,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "perSize", defaultValue = "10", required = false) Integer perSize) {

        Page<PostFindDto.Response> searchedPostsPage = postService.searchKeyword(page-1, perSize, keyword, sorting);
        Pagination<PostFindDto.Response> postsPagination = PaginationUtil.pageToPagination(searchedPostsPage);

        return new ResponseEntity<>(ApiResultDto.success(postsPagination), HttpStatus.FOUND);
    }


    // 게시글 좋아요
    @GetMapping("/post/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        postService.likeOne(postId);

        return ResponseEntity.ok().build();
    }


    // 게시글 수정
    @PutMapping("/post/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId,
                                           @Valid @RequestBody PostUpdate.RequestDto requestDto) {
        postService.updateOne(postId, requestDto);

        return ResponseEntity.ok().build();
    }

    // 마이페이지 >> 내가 작성한 글 조회
    @GetMapping("/my-posts")
    public ResponseEntity<ApiResultDto<Pagination<PostFindDto.Response>>> getMyPosts(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "perSize", defaultValue = "10", required = false) Integer perSize) {

        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        Page<PostFindDto.Response> pageMyPosts = postService.findMyPosts(page-1, perSize, memberId);
        return new ResponseEntity<>(ApiResultDto.success(PaginationUtil.pageToPagination(pageMyPosts)), HttpStatus.FOUND);
    }

    // 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deleteOne(postId);
        return ResponseEntity.ok().build();
    }
}