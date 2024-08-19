package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.Model.Member;
import com.example.capstone_2.Repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository; 

    public Member addMember(Member member){
        return memberRepository.save(member);
    }

    public List<Member> getMemberByTeamId(Integer teamId){
        return memberRepository.findByTeamId(teamId); 
    }
}
