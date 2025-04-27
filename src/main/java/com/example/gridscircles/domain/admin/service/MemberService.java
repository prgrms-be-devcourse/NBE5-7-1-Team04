package com.example.gridscircles.domain.admin.service;

import com.example.gridscircles.domain.admin.dto.MemberDetails;
import com.example.gridscircles.domain.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
            .map(m -> MemberDetails.builder()
                .email(m.getEmail())
                .password(m.getPassword())
                .role(m.getRole())
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
    }
}
