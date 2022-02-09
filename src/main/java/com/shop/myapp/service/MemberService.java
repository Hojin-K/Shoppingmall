package com.shop.myapp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.shop.myapp.dto.Member;
import com.shop.myapp.repository.MemberRepository;

@Service
public class MemberService {
	private final SqlSession sqlSession;

	public MemberService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<Member> getMember(){
    	MemberRepository memberRepository 
    	= sqlSession.getMapper(MemberRepository.class);
    	List<Member> members = memberRepository.findAll();
    	for(Member member : members) {
    		System.out.println(member.getMemberBirth());
    	}
        return members;
    }
    
    public int insertMember(Member member) {
    	
    	MemberRepository memberRepository 
    	= sqlSession.getMapper(MemberRepository.class);
    	
    	int result = memberRepository.insertMember(member);
    	
    	return result;
    }
}
