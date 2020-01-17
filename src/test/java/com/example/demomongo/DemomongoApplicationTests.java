package com.example.demomongo;

		import com.example.demomongo.controller.PersonController;
        import com.example.demomongo.model.Address;
        import com.example.demomongo.model.Person;
        import com.mongodb.BasicDBObjectBuilder;
        import com.mongodb.DBObject;
        import org.junit.jupiter.api.DisplayName;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.data.mongodb.core.MongoTemplate;
        import org.springframework.http.MediaType;
        import org.springframework.mock.web.MockHttpServletRequest;
        import org.springframework.test.context.junit.jupiter.SpringExtension;
        import static org.assertj.core.api.Assertions.assertThat;
        import org.junit.jupiter.api.Assertions.*;
        import org.springframework.test.web.servlet.MvcResult;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
        import org.springframework.web.context.request.RequestContextHolder;
        import org.springframework.web.context.request.ServletRequestAttributes;
        import static org.junit.jupiter.api.Assertions.*;




@DataMongoTest
@ExtendWith(SpringExtension.class)
class DemomongoApplicationTests {

    @InjectMocks
    PersonController personController;

    @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")

    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();


        mongoTemplate.save(objectToSave, "collection");



        Person p= new Person("Person First",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic"));

        //mongoTemplate.save(p, "collection");
        // then


        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");

       // assertThat(mongoTemplate.findAll(Person.class, "collection")).contains(p);



    }

}
