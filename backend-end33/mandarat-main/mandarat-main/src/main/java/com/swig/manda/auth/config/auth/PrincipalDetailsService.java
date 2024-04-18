package com.swig.manda.auth.config.auth;

import com.swig.manda.common.login.repository.LoginRepository;
import com.swig.manda.common.signup.model.Member;
import com.swig.manda.common.signup.repository.SignupRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Data
public class PrincipalDetailsService implements UserDetailsService {

   @Autowired
   private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = loginRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        return new PrincipalDetails(member);
    }
}
