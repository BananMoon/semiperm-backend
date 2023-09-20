package com.project.semipermbackend.domain.member;

import com.project.semipermbackend.common.code.Gender;
import com.project.semipermbackend.common.utils.GenderConverter;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.code.Category;
import com.project.semipermbackend.domain.code.MemberNeeds;
import com.project.semipermbackend.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자를 무분별하게 사용하지 못하게 하여 setter통한 불완전한 객체 생성을 막는다.
@AllArgsConstructor
@Table(name = "member")
@Where(clause = "use_yn = true")
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = true, length = 20)
    private String name;

    @Column(nullable = true)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Convert(converter = GenderConverter.class)
    @Column(nullable = true, length = 2)    // TODO null여부 fix 필요
    private Gender gender;

    @ElementCollection(fetch = FetchType.LAZY)  // 바로 연달아 select문 수행 X. 필요할 때 N+1 발생
    @CollectionTable(name = "member_interest_fields", joinColumns = @JoinColumn(name = "member_id", nullable = false))
    @Enumerated(EnumType.STRING)
    @Column(name = "field", length = 50, nullable = true)
    @Builder.Default
    private Set<Category> interestingFields = new HashSet<>();


    @ElementCollection(fetch = FetchType.LAZY)  // 바로 연달아 select문 수행 X. 필요할 때 N+1 발생
    @CollectionTable(name = "member_needs", joinColumns = @JoinColumn(name = "member_id", nullable = false))
    @Enumerated(EnumType.STRING)
    @Column(name = "needs", length = 100, nullable = true)
    @Builder.Default
    private Set<MemberNeeds> needInformations = new HashSet<>();


    @Column(name="nickname", nullable = false, length = 50)
    private String nickname;

}
