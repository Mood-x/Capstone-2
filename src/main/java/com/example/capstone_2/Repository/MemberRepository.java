package com.example.capstone_2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
    List<Member> findByTeamId(Integer teamId);

}
