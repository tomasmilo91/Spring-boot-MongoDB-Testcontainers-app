package com.example.demomongo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.demomongo.model.Address;
import com.example.demomongo.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.demomongo.controller.PersonController;
import com.example.demomongo.dao.PersonRepository;
import org.springframework.http.ResponseEntity;



@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest
{
    @InjectMocks
    PersonController personController;

    @Mock
    PersonRepository personRepository;

    @Test
    public void testGetAllMaping()
    {
        // given
        Person personTest= new Person("Person First",3, "0905567888","emailfirst@email.cz",1000, new Address("Zlin", "Czech republic"));

        List<Person> personTestList= new ArrayList<>();
        personTestList.add(personTest);
        assertThat(personTestList.size()).isEqualTo(1);

        when(personRepository.findAll()).thenReturn(personTestList);

        List<Person> controllerTestList = personController.getAll();

        //check if result is equals to persons
        // then
        assertThat(controllerTestList.size()).isEqualTo(1);

        assertThat(controllerTestList.get(0).getName().compareToIgnoreCase(personTestList.get(0).getName()));

    }
}