package com.example.demomongo;
import com.example.demomongo.dao.PersonRepository;
import com.example.demomongo.model.Address;
import com.example.demomongo.model.Person;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DemomongoTestRepository  {

    private PersonRepository repository;

    @Autowired
    public void setRepository(PersonRepository repository) {
        this.repository = repository;
    }

    @Test
    public void createPerson() {
        Person p= new Person("Person First",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic"));

    }

    @Test
    public void findAllBooks() {
        List<Person> books = repository.findAll();
        assertNotNull(books);
        assertTrue(!books.isEmpty());
    }

}





/*    @Test
    public void findBookById() {
        Book book = repository.findOne(1);
        assertNotNull(book);
    }

    @Test
    public void deleteBookWithId() {
        Book book = repository.findOne(1);
        repository.delete(book);
        assertNotNull(book);
    }*/