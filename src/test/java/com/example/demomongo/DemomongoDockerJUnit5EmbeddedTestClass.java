package com.example.demomongo;

import com.example.demomongo.dao.PersonRepository;
import com.example.demomongo.model.Address;
import com.example.demomongo.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(initializers = DemomongoDockerJUnit5EmbeddedTestClass.Initializer.class)
@Testcontainers
public class DemomongoDockerJUnit5EmbeddedTestClass {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    PersonRepository personRepository;

    @Container
    public static GenericContainer mongoContainer = new GenericContainer("mongo:4.0");

    @Test
    public void testDatabase() {
        mongoTemplate.insert(new Person("Person First",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic")));
        mongoTemplate.insert(new Person("Person Second",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic")));

        List<Person> all = personRepository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                    format("spring.data.mongodb.uri=mongodb://%s:%s",mongoContainer.getContainerIpAddress(), mongoContainer.getMappedPort(27017)));
        }
    }
}

