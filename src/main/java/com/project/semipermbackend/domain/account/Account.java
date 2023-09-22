package com.project.semipermbackend.domain.account;

import com.project.semipermbackend.auth.entity.SocialType;
import com.project.semipermbackend.common.code.FlagYn;
import com.project.semipermbackend.common.utils.FlagYnConverter;
import com.project.semipermbackend.domain.common.BaseTimeEntity;
import com.project.semipermbackend.member.dto.MemberCreation;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Builder
@Entity
@Where(clause = "use_yn = true")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "account")
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "social_id", unique = true, nullable = false)
    private String socialId;

    @Column(length = 60, nullable = false)    // sns 타입과 이메일로 구분하므로 이메일 자체는 중복될 수 있다.(non unique)
    private String email;

    @Column(name = "profile_image_url", length = 100, nullable = false)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", length = 10, nullable = false)
    private SocialType socialType;

    @Column(name="refresh_token", length = 200)
    private String refreshToken;

    @Convert(converter = FlagYnConverter.class)
    @Builder.Default
    @Column(name = "member_yn", nullable = false, length = 2)
    private FlagYn memberYn = FlagYn.NO;

    @Column(name = "last_login_date")
    private LocalDate lastLoginDate;

    @Convert(converter = FlagYnConverter.class)
    @Builder.Default
    @Column(name = "personal_info_service_usage_agree_yn", nullable = false, length = 2)
    private FlagYn personalInfoServiceUsageAgreeYn = FlagYn.NO;

    @Convert(converter = FlagYnConverter.class)
    @Builder.Default
    @Column(name = "agree_to_ad_yn", nullable = false, length = 2)
    private FlagYn agreeToADYn = FlagYn.NO;

    @Convert(converter = FlagYnConverter.class)
    @Builder.Default
    @Column(name = "is_order_than_14", nullable = false, length = 2)
    private FlagYn isOrderThan14 = FlagYn.NO;

    public void login(String refreshToken) {
        this.refreshToken = refreshToken;
        this.lastLoginDate = LocalDate.now();
    }

    public void joinSuccess() {
        this.memberYn = FlagYn.YES;
    }

    public void saveAgreeYnInfos(MemberCreation.RequestDto memberCreation) {
        this.personalInfoServiceUsageAgreeYn = memberCreation.getPersonalInfoServiceUsageAgreeYn();
        this.isOrderThan14 = memberCreation.getIsOrderThan14();
        this.agreeToADYn = memberCreation.getAgreeToADYn();
    }
}
