package com.example.test.repository;

import com.example.test.model.User; 
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
	
}