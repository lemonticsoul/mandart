package com.swig.manda.config.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import com.swig.manda.config.auth.PrincipalDetails;
import com.swig.manda.model.Member;
import com.swig.manda.repository.MemberRepository;
import com.swig.manda.service.MemberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalOauth2UserService.class);

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = extractProviderId(oauth2User, provider);
        String username = provider + "_" + providerId;
        // 카카오 API로부터 닉네임과 이메일 추출
        String email = null;
        String nickname = null;
        if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
            if (kakaoAccount != null && kakaoAccount.containsKey("email")) {
                email = (String) kakaoAccount.get("email");
            }
            Map<String, Object> properties = oauth2User.getAttribute("properties");
            if (properties != null && properties.containsKey("nickname")) {
                nickname = (String) properties.get("nickname");
            }
        }

        Member userEntity = null; // 초기화

        Optional<Member> userOptional = Optional.ofNullable(memberRepository.findByEmail(email));
        if (!userOptional.isPresent()) {
            userEntity = memberService.registerNewOAuth2User(provider,providerId,email, nickname);
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }


    private String extractProviderId(OAuth2User oauth2User, String provider) {
        if ("google".equals(provider)) {
            return oauth2User.getAttribute("sub");
        } else if ("kakao".equals(provider)) {
            return String.valueOf(oauth2User.getAttribute("id"));
        }
        return null;
    }


}

