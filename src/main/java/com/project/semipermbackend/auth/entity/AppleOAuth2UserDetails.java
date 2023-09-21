package com.project.semipermbackend.auth.entity;

import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
@SuperBuilder
public class AppleOAuth2UserDetails extends CustomOAuth2UserDetails {
    // socialType, socialId 외에 apple에서 추가로 받는 정보들.

    /*private AppleOAuth2User(String userName, String socialId, String email, SocialType socialType, Map<String, Object> attributes) {
        super(socialType, socialId, email, userName, attributes);
        this.socialId = socialId;
        this.userName = userName;
        this.email = email;
        this.socialType = socialType;
        this.attributes = attributes;
    }*/


    /*@Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }*/

    @Override
    public String getEmail() {
        return null;
    }

/*    @Override
    public String getNickname() {
        return null;
    }*/
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
