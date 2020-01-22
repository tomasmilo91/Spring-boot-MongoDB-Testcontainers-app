package com.example.demomongo;

import com.example.demomongo.dao.PersonRepository;
import com.example.demomongo.model.Address;
import com.example.demomongo.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
* Jupiter integration is provided by means of the @Testcontainers annotation.
*
* The extension finds all fields that are annotated with @Container and calls their container lifecycle methods (methods on the Startable interface).
* Containers declared as static fields will be shared between test methods. They will be started only once before any test method is executed and
* stopped after the last test method has executed. Containers declared as instance fields will be started and stopped for every test method.
*/
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = DemomongoDockerJUnit5TestClass.Initializer.class)

public class DemomongoDockerJUnit5TestClass {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    protected MockMvc mvc;

    //anotation container that are shared between all methods of a test class
    @Container
    public static GenericContainer mongoContainer = new GenericContainer("mongo:4.0");

    //properties for connecting to docker database
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                    format("spring.data.mongodb.uri=mongodb://%s:%s",mongoContainer.getContainerIpAddress(), mongoContainer.getMappedPort(27017)));
        }
    }

    @Test
    public void testDatabase(){

        mongoTemplate.insert(new Person("Person First",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic")));
        mongoTemplate.insert(new Person("Person Second",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic")));
        List<Person> all = personRepository.findAll();

        //there are 6 entities of person because 4 we already inserted at DatabaseSeeder.class and another two we inserted here
        Assertions.assertEquals(6,all.size());
    }

    @Test
    public void testRepository() {
        Person p= new Person("Person First",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic"));
        List<Person> books = personRepository.findAll();
        assertNotNull(books);
    }


    @Test
    public void getAllPersonsAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get("/persons/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
