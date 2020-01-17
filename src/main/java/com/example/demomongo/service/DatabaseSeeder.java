package com.example.demomongo.service;

import com.example.demomongo.dao.PersonRepository;
import com.example.demomongo.model.Address;
import com.example.demomongo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private PersonRepository personRepository;

    @Autowired
    public DatabaseSeeder(PersonRepository personRepository){
        this.personRepository=personRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Person> persons= new ArrayList<>();

        personRepository.deleteAll();

        persons.add(new Person("Person First",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic")));
        persons.add(new Person("Person Second",35, "0964567888","emailpersonsec@email.cz",2000,new Address("Zlin", "Czech republic")));
        persons.add(new Person("Person Third",31, "0909767888","emailthird@gmail.com",3000,new Address("Munchen", "Germany")));
        persons.add(new Person("Person Fourth",39, "0910867888","emailperson@fourth.sk", 1600,new Address("Presov", "Slovakia")));

        personRepository.saveAll(persons);

    }
}
