package com.example.gridscircles.domain.admin.entitiy;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import com.example.gridscircles.domain.admin.enums.Role;
import com.example.gridscircles.domain.admin.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@TestInstance(PER_CLASS)
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        if (memberRepository.count() > 0) {
            return;
        }

        Member admin = Member.builder()
            .email("admin@example.com")
            .password(passwordEncoder.encode("1234"))
            .role(Role.ADMIN)
            .build();
        memberRepository.save(admin);

        Member user = Member.builder()
            .email("user@example.com")
            .password(passwordEncoder.encode("abcd"))
            .role(Role.USER)
            .build();
        memberRepository.save(user);
    }

    @Test
    void testDummyAdmin() {
        Member admin = memberRepository.findByEmail("admin@example.com")
            .orElseThrow(() -> new RuntimeException("Admin not found"));
        System.out.println("ADMIN -> " + admin.getEmail() + " / " + admin.getPassword());
    }

    @Test
    void testDummyUser() {
        Member user = memberRepository.findByEmail("user@example.com")
            .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("USER  -> " + user.getEmail() + " / " + user.getPassword());
    }
}