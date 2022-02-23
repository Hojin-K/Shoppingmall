package com.shop.myapp.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shop.myapp.dto.Member;

@Mapper
public interface MemberRepository {
    int insertMember(Member member);
    int updateMember(Member member);
    List<Member> findAll(@Param(value = "chkInfo") String chkInfo, @Param(value = "condition") String condition);
    Optional<Member> findById(String memberId);
}
