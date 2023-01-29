package com.example.security1.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;


    // response={id=vWEONN4kUA8SGFcTnbUL1SDmC-b70ZFgUTaUXMvVBOU, email=kbsserver@naver.com, name=김민우}
    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

}
