package com.exposition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exposition.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByMid(String mid);
	boolean existsByMid(String mid);
	Member findByName(String name);
	Member findByEmail(String email);
	Member findByNameAndEmail(String name, String email);
	Member findByMidAndEmail(String mid, String email);
}
