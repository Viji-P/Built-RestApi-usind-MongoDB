package com.rest.mongospring.service;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rest.mongospring.collections.Employee;

public interface EmployeeService {

	String save(Employee employee);

	List<Employee> getEmployeeStartWith(String name);

	void delete(String id);

	List<Employee> getByEmpAge(Integer minAge, Integer maxAge);

	Page<Employee> searchEmployee(String name, Integer minAge, Integer maxAge,String city, Pageable pageable);

	List<Document> getOldestEmpByCity();

	List<Document> getPopulationByCity();
	

}
