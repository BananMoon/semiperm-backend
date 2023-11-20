package com.project.semipermbackend.domain.member;

import com.project.semipermbackend.common.code.Gender;
import com.project.semipermbackend.common.utils.GenderConverter;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.domain.code.MemberNeeds;
import com.project.semipermbackend.domain.common.BaseTimeEntity;
import com.project.semipermbackend.domain.store.MemberZzimStore;
import com.project.semipermbackend.member.dto.MyPageDto;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자를 무분별하게 사용하지 못하게 하여 setter통한 불완전한 객체 생성을 막는다.
@AllArgsConstructor
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET use_yn = false WHERE member_id = ?")
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

    @Column(nullable = false)
    private LocalDate birth;

    @Convert(converter = GenderConverter.class)
    @Column(nullable = false, length = 5)
    private Gender gender;

    @ElementCollection(fetch = FetchType.LAZY)  // 바로 연달아 select문 수행 X. 필요할 때 N+1 발생
    @CollectionTable(name = "member_interest_fields", joinColumns = @JoinColumn(name = "member_id", nullable = false))
    @Enumerated(EnumType.STRING)    // ENUM의 name값을 Database의 저장
    @Column(name = "field", length = 50, nullable = true)
    @Builder.Default
    private Set<SurgeryCategory> interestingFields = new HashSet<>();


    @ElementCollection(fetch = FetchType.LAZY)  // 바로 연달아 select문 수행 X. 필요할 때 N+1 발생
    @CollectionTable(name = "member_needs", joinColumns = @JoinColumn(name = "member_id", nullable = false))
    @Enumerated(EnumType.STRING)
    @Column(name = "needs", length = 100, nullable = true)
    @Builder.Default
    private Set<MemberNeeds> needInformations = new HashSet<>();

    @Column(name="nickname", nullable = false, length = 50)
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MemberZzimStore> memberZzimStores = new ArrayList<>();


    public void addZzimStore(MemberZzimStore memberZzimStore) {
        this.memberZzimStores.add(memberZzimStore);
    }

    public void updateMyInfo(MyPageDto mypageDto) {
        this.nickname = mypageDto.getNickname();
        this.birth = mypageDto.getBirth();
        this.gender = mypageDto.getGender();
    }
    public void updateMyInterestingSurgery(MyPageDto mypageDto) {
        this.interestingFields = mypageDto.getInterestingFields();
    }

}
