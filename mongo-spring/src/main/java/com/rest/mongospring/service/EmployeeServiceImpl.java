package com.rest.mongospring.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.rest.mongospring.collections.Employee;
import com.rest.mongospring.repository.EmpRepository;



@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private EmpRepository empRepository;
	
	@Override
	public String save(Employee employee) {
		// TODO Auto-generated method stub
		return empRepository.save(employee).getId();
	}

	@Override
	public List<Employee> getEmployeeStartWith(String name) {
		// TODO Auto-generated method stub
		return empRepository.findByFirstNameStartsWith(name);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		empRepository.deleteById(id);
		
	}

	@Override
	public List<Employee> getByEmpAge(Integer minAge, Integer maxAge) {
		// TODO Auto-generated method stub
		return empRepository.findEmployeeByAge(minAge, maxAge);
	}

	@Override
	public Page<Employee> searchEmployee(String name, Integer minAge, Integer maxAge,String city, Pageable pageable) {
		// TODO Auto-generated method stub
		Query query = new Query().with(pageable);
		List<Criteria> criteria = new ArrayList<>();
		
		if(name != null && !name.isEmpty()) {
			criteria.add(Criteria.where("firstName").regex(name,"i"));
		}
		
		if(minAge != null && maxAge!=null) {
			
			criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
		}
		
		if(city != null && !city.isEmpty()) {
			criteria.add(Criteria.where("address.city").is(city));
		}
		
		if(!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
		}
		
		Page<Employee> person = PageableExecutionUtils.getPage(
				mongoTemplate.find(query, Employee.class),
						pageable,() -> mongoTemplate.count(query.skip(0).limit(0),Employee.class));
			
		
		return person;
	}

	@Override
	public List<Document> getOldestEmpByCity() {
		UnwindOperation unwindOperation = Aggregation.unwind("address");
		SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"age");
		GroupOperation groupOperation = Aggregation.group("address.city")
				.first(Aggregation.ROOT)
				.as("olderEmployee");
		
		Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);
		
		List<Document> employee = mongoTemplate.aggregate(aggregation,Employee.class,Document.class).getMappedResults();
		
		return employee;
	}

	@Override
	public List<Document> getPopulationByCity() {
		
		UnwindOperation unwindOperation = Aggregation.unwind("address");
		GroupOperation groupOperation = Aggregation.group("address.city")
				.count()
				.as("popCount");
		SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"popCount");
		
		ProjectionOperation projectionOperation
								= Aggregation.project()
								.andExpression("_id").as("city")
								.andExpression("popCount").as("count")
								.andExclude("_id");
		
		Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation,projectionOperation);

		List<Document> document = mongoTemplate.aggregate(aggregation,Employee.class,Document.class).getMappedResults();

		
		return document;
	}
	
	
	

}
