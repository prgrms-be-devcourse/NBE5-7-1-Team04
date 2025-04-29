package com.example.gridscircles.domain.admin.service;

import static com.example.gridscircles.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.example.gridscircles.domain.admin.dto.MemberDetails;
import com.example.gridscircles.domain.admin.repository.MemberRepository;
import com.example.gridscircles.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return memberRepository.findByEmail(email)
            .map(m -> MemberDetails.builder()
                .email(m.getEmail())
                .password(m.getPassword())
                .role(m.getRole())
                .build()
            )
            .orElseThrow(() -> new ErrorException(NOT_FOUND_MEMBER));
    }
}
