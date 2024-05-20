package com.rest.mongospring.collections;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
	
	private String id;
	private String firstName;
	private String lastName;
	private Integer age;
	private List<String> hobbies;
	private List<Address> address;

}
