package com.project.semipermbackend.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
/*
{
    "resultcode": "00",
    "message": "success",
    "response": {
        "id": "Wp0KwPxXNByA3aLKS0BCfh6W8poco4EM5_CoiAxhbrI",
        "nickname": "YJM",
        "profile_image": "https://phinf.pstatic.net/contact/20221130_298/1669812243296jGcdW_JPEG/MicrosoftTeams-image.jpg",
        "email": "younji1115@gmail.com"
    }
}

 */
@SuperBuilder
@Getter
public class NaverOAuth2User extends CustomOAuth2User {
//    String profileImageUrl;
    String nickname;    // social type마다 주는 곳 다를 듯.

    protected NaverOAuth2User(String socialId, String email, SocialType socialType,
                            String profileImageUrl, String nickname) {
        super(socialType, socialId, email, profileImageUrl);
//        this.socialId = socialId;
//        this.userName = userName;
//        this.email = email;
//        this.socialType = socialType;
//        this.attributes = attributes;
//        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return super.userName;
    }
}
