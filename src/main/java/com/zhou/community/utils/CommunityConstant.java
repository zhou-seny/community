package com.zhou.community.utils;

public interface CommunityConstant {
    // 激活
    int ACTINVATION_SUCCESS = 0;
    int ACTINVATION_REPEAT = 1;
    int ACTINVATION_FAIL = 2;

    // 默认状态的登录凭证的超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // 记住状态的登录凭证超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24;

}
