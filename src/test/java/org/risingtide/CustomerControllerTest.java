package org.risingtide;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.risingtide.controller.CustomerController;
import org.risingtide.model.Customer;
import org.risingtide.repository.AccountRepository;
import org.risingtide.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    public static final String BASE_PATH = "/api/v1/account";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private AccountRepository accountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAddCustomerSuccess() throws Exception {
        Customer customer = new Customer("091234567890", "Juan dela Cruz", "juandc@foo.bar", "123 Foo St.", "C");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("phoneNumber").value("091234567890"));
    }

    @Test
    public void testAddCustomerInvalidEmail() throws Exception {
        Customer customer = new Customer("091234567890", "Juan dela Cruz", "invalid_email", "123 Foo St.", "C");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testAddCustomerInvalidAccountType() throws Exception {
        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"id\":null,\"phoneNumber\":\"091234567890\",\"name\":\"Juan dela Cruz\",\"email\":\"juandc@foo.bar\",\"address1\":\"123 Foo St.\",\"address2\":null,\"accountType\":\"invalid_account_type\",\"accounts\":null}"
                        ))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddCustomerMissingPhoneNumber() throws Exception {
        Customer customer = new Customer();
        customer.setName("Juan dela Cruz");
        customer.setEmail("juandc@foo.bar");
        customer.setAddress1("123 Foo St.");
        customer.setAccountType(Customer.AccountType.C);

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testAddCustomerMissingName() throws Exception {
        Customer customer = new Customer();
        customer.setPhoneNumber("091234567890");
        customer.setEmail("juandc@foo.bar");
        customer.setAddress1("123 Foo St.");
        customer.setAccountType(Customer.AccountType.C);

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testAddCustomerMissingEmail() throws Exception {
        Customer customer = new Customer();
        customer.setPhoneNumber("091234567890");
        customer.setName("Juan dela Cruz");
        customer.setAddress1("123 Foo St.");
        customer.setAccountType(Customer.AccountType.C);

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testAddCustomerMissingAddress() throws Exception {
        Customer customer = new Customer();
        customer.setPhoneNumber("091234567890");
        customer.setName("Juan dela Cruz");
        customer.setEmail("juandc@foo.bar");
        customer.setAccountType(Customer.AccountType.C);

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testAddCustomerMissingAccountType() throws Exception {
        Customer customer = new Customer();
        customer.setPhoneNumber("091234567890");
        customer.setName("Juan dela Cruz");
        customer.setEmail("juandc@foo.bar");
        customer.setAddress1("123 Foo St.");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testGetCustomerSuccess() throws Exception {
        Customer customer = new Customer("091234567890", "Juan dela Cruz", "juandc@foo.bar", "123 Foo St.", "C");

        when(customerRepository.findByPhoneNumber("091234567890")).thenReturn(Optional.of(customer));

        mockMvc.perform(get(BASE_PATH + "/091234567890"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("phoneNumber").value("091234567890"));
    }

    @Test
    public void testGetCustomerNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/account/091234567891"))
                .andExpect(jsonPath("$.transactionStatusCode").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.transactionStatusDescription").value(Matchers.containsString("Customer not found")));
    }
}
