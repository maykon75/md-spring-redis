package com.redis.project.controller;

import com.redis.project.dto.CustomerDTO;
import com.redis.project.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void shouldSaveCustomerInCache() {
        CustomerDTO dto = new CustomerDTO();
        dto.setId("123");
        dto.setName("Test");

        ResponseEntity<Void> response = customerController.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldGetCustomerFromCache() {
        String id = "123";
        CustomerDTO expected = new CustomerDTO();
        expected.setId(id);
        expected.setName("Test");

        when(customerService.getFromCache(id)).thenReturn(expected);

        ResponseEntity<Object> response = customerController.get(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void shouldClearCustomerFromCache() {
        String id = "123";

        ResponseEntity<Void> response = customerController.clear(id);

        verify(customerService, times(1)).clearCache(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
