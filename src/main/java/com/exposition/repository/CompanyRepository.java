package com.exposition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exposition.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	Company findByCom(String com);
	boolean existsByCom(String com);
	Company findByEmail(String email);
	Company findByComAndEmail(String com, String email);
<<<<<<< HEAD

=======
>>>>>>> 5371799397205571a6fb266bcdae6220e9d0d0d6

}
