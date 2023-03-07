package com.exposition.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exposition.entity.Company;
import com.exposition.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService implements UserDetailsService {
	
	private final CompanyRepository companyRepository;
	//회원가입
	public Company saveCompany(Company company) {
		validateDuplicateCompany(company);
		return companyRepository.save(company);
	}
	//회원 중복검사
	private void validateDuplicateCompany(Company company) {
		Company findCompany = companyRepository.findByCom(company.getCom());
		if(findCompany != null) {
			throw new IllegalStateException("이미 가입된 회원입니다");
		}	
	}
	//ajax를 이용한 중복검사
	public boolean checkMidDuplicate(String com) {
		return companyRepository.existsByCom(com);
	}
	//로그인
	@Override
	public UserDetails loadUserByUsername(String com) throws UsernameNotFoundException{
		
		Company company = companyRepository.findByCom(com);
		if(company==null) {
			throw new UsernameNotFoundException(com);
		}
		
		return User.builder().username(company.getCom()).password(company.getPasswoad()).roles(company.getRole().toString()).build();
		
	}

}
