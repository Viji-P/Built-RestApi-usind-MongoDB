package com.rest.mongospring.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rest.mongospring.collections.Employee;

@Repository
public interface EmpRepository extends MongoRepository<Employee,String>{

	List<Employee> findByFirstNameStartsWith(String name);
	
	@Query(value = "{'age' : {$gt : ?0,$lt : ?1}}")
	List<Employee> findEmployeeByAge(Integer min,Integer max);
}
