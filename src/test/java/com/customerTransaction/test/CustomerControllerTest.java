package com.customerTransaction.test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.customerTransaction.controller.CustomerController;
import com.customerTransaction.model.Customer;
import com.customerTransaction.model.Transaction;
import com.customerTransaction.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;






@WebMvcTest(CustomerController.class)
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
public class CustomerControllerTest {

	@Mock
    private CustomerService customerService;
    
    @InjectMocks
    private CustomerController customerController;
    
    private MockMvc mockMvc;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(1, "John Doe", "IC123", null);

        when(customerService.getCustomerById(anyInt())).thenReturn(Optional.of(customer));

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customer)));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John Doe", "IC123", null));
        customers.add(new Customer(2, "Jane Smith", "IC456", null));

        when(customerService.findAllCustomers()).thenReturn(customers);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers)));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer(1, "John Doe", "IC123", null);

        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customer)));
    }

    @Test 
    void testUpdateCustomer() throws Exception {
        // Create a Customer object with the desired updates
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1);
        updatedCustomer.setName("Updated Name");
        updatedCustomer.setIc("Updated IC");

        // Create a list of transactions for the updated customer
        List<Transaction> updatedTransactions = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 1, "Updated Transaction 1", 500); // Update existing transaction
        Transaction transaction2 = new Transaction(3, 1, "New Transaction", 1000); // Add new transaction
        updatedTransactions.add(transaction1);
        updatedTransactions.add(transaction2);
        updatedCustomer.setTransactions(updatedTransactions);

        // Convert the Customer object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updatedCustomer);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/customers/{customerId}", updatedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedCustomer.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedCustomer.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ic").value(updatedCustomer.getIc()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[0].id").value(transaction1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[0].custId").value(transaction1.getCustId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[0].name").value(transaction1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[0].amount").value(transaction1.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[1].id").value(transaction2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[1].custId").value(transaction2.getCustId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[1].name").value(transaction2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions[1].amount").value(transaction2.getAmount()));
    }


    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(customerService, times(1)).deleteCustomer(1);
    }
}