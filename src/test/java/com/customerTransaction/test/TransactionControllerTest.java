package com.customerTransaction.test;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.customerTransaction.controller.TransactionController;
import com.customerTransaction.model.Transaction;
import com.customerTransaction.service.TransactionService;

@WebMvcTest(TransactionController.class)
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

      
    @Test
    void testGetTransactionById() throws Exception {
        // Mocking the transaction returned by the service
        Transaction transaction = new Transaction(1, 1, "Transaction 1", 100);
        Mockito.when(transactionService.getTransactionById(1)).thenReturn(transaction);
        
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/transactions/{id}", 1)
        .accept(MediaType.APPLICATION_JSON))
       // mockMvc.perform(MockMvcRequestBuilders.get("transactions/transactions/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())               
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(transaction.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.custId").value(transaction.getCustId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(transaction.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(transaction.getAmount()));
        System.out.println("DOES IT GO IN");
        Mockito.verify(transactionService, Mockito.times(1)).getTransactionById(1);
    }


    
    void testGetAllTransactionsByCustomerId() throws Exception {
        // Mock the behavior of the transactionService.getAllTransactionsByCustomerId method
      	Integer customerId = 1;
        Transaction transaction1 = new Transaction();
        transaction1.setId(1);
        transaction1.setCustId(1);
        transaction1.setAmount(100);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2);
        transaction2.setCustId(2);
        transaction2.setAmount(200);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        Mockito.when(transactionService.getAllTransactionsByCustomerId(customerId)).thenReturn(transactions);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/customers/{customerId}/transactions/{customerId}", customerId))
      
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId", Matchers.is(customerId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].customerId", Matchers.is(customerId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount", Matchers.is(200)));
    }

    
}


