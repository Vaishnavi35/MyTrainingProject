package com.example.simplejdbcap.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.simplejdbcap.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username );
   
}
