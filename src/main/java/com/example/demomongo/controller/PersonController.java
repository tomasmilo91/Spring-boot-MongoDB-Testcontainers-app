package com.example.demomongo.controller;

import com.example.demomongo.dao.PersonRepository;
import com.example.demomongo.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    PersonRepository personRepository;
    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(value = "/all")
    public List<Person> getAll() {
        LOG.info("Getall user data");
        return personRepository.findAll();
    }

    @PutMapping("/insert")
    public void insert(@RequestBody Person person) {
        LOG.info("Insert user data");
        personRepository.insert(person);
    }

    @PostMapping("/update")
    public void update(@RequestBody Person person) {
        LOG.info("Update user data");
        personRepository.save(person);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") String id) {
        LOG.info("Delete user data");
        personRepository.deleteById(id);
    }

    @GetMapping("address/{city}")
    public List<Person> getByCity(@PathVariable("city") String city) {
        List<Person> person = personRepository.findAllByCity(city);
        return person;
    }
}


