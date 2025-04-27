package com.example.gridscircles.domain.admin.repository;

import com.example.gridscircles.domain.admin.entitiy.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
