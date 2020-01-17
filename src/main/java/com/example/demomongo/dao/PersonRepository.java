package com.example.demomongo.dao;

import com.example.demomongo.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends MongoRepository<Person,String>{
    @Query(value= "{address.city:?0}")
    List<Person> findAllByCity(String city);
}
