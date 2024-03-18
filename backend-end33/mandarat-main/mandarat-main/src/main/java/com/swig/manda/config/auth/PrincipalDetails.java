package com.swig.manda.config.auth;

import com.swig.manda.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails ,OAuth2User{
    private Member member;
    private Map<String, Object> attributes;

    //일반로그인
    public PrincipalDetails(Member member) {

        this.member = member;
    }
public Member getMember(){
        return member;
}
    // OAuth 로그인
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.attributes = attributes;
        this.member = member;
    }

    @Override
    public String getAttribute(String name) {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
       return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authority = new ArrayList<>();
        authority.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });


        return authority;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() { return this.member.getUserId();
    }


    public Long getId() {
        return member.getId();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getName() {
        return null;
    }
}
