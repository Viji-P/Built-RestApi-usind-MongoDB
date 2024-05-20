package com.rest.mongospring.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.mongospring.collections.Employee;
import com.rest.mongospring.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("")
	public String save(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
	
	@GetMapping("")
	public List<Employee> getEmployeeStartWith(@RequestParam("name") String name){
		
		return employeeService.getEmployeeStartWith(name);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		employeeService.delete(id);
		
	}
	
	@GetMapping("/age")
	public List<Employee> getByEmpAge(@RequestParam Integer minAge,@RequestParam Integer maxAge){
		return employeeService.getByEmpAge(minAge,maxAge);
	}
	
	@GetMapping("/search")
	public Page<Employee> searchEmployee(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer minAge,
			@RequestParam(required = false) Integer maxAge,
			@RequestParam(required = false) String city,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "5") Integer size){
		Pageable pageable = PageRequest.of(page, size);
		
		return employeeService.searchEmployee(name,minAge,maxAge,city,pageable);
		
	}
	
	@GetMapping("/oldestPerson")
	public List<Document> getOldestPerson(){
		return employeeService.getOldestEmpByCity();
		
	}
	
	@GetMapping("/population")
	public List<Document> getPopulationByCity(){
		return employeeService.getPopulationByCity();
	}
	
	

}
