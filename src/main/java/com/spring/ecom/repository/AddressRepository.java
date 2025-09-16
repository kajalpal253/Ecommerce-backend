package com.spring.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.ecom.model.Address;



public interface AddressRepository extends JpaRepository<Address,Long>{
	
}
//repository