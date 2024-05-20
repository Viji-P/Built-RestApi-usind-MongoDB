package com.rest.mongospring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rest.mongospring.collections.Photo;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,String>{

}
