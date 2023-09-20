package com.project.semipermbackend.common.utils;

import com.project.semipermbackend.auth.entity.SocialType;

import java.util.Random;

public class StringUtils {
    private static final int BOUND = Integer.MAX_VALUE;

    /**
     * 난수 생성 : 소셜 타입_난수 랜덤값
     * @param socialType
     * @return
     */
    public static String makeRandomNickname(SocialType socialType) {
        Random random = new Random();
        String prefix = socialType.getSocialName() + "_";
        return prefix + random.nextInt(BOUND);
    }
}
