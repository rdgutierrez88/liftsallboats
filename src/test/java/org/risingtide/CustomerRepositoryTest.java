package org.risingtide;

import org.junit.jupiter.api.Test;
import org.risingtide.model.Customer;
import org.risingtide.repository.AccountRepository;
import org.risingtide.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveCustomerSuccess() {
        Customer customer = new Customer("091234567890", "Juan dela Cruz", "juandc@foo.bar", "123 Foo St.", "C");
        Customer savedCustomer = customerRepository.save(customer);
        Customer retrievedCustomer = entityManager.find(Customer.class, savedCustomer.getId());

        assertThat(retrievedCustomer).isNotNull();
        assertThat(retrievedCustomer.getId()).isNotNull();
        assertThat(retrievedCustomer.getPhoneNumber()).isEqualTo("091234567890");
        assertThat(retrievedCustomer.getName()).isEqualTo("Juan dela Cruz");
        assertThat(retrievedCustomer.getEmail()).isEqualTo("juandc@foo.bar");
        assertThat(retrievedCustomer.getAddress1()).isEqualTo("123 Foo St.");
        assertThat(retrievedCustomer.getAccountType()).isEqualTo(Customer.AccountType.C);
    }

    @Test
    public void testFindByPhoneNumberSuccess() {
        Customer customer = new Customer("091234567890", "Juan dela Cruz", "juandc@foo.bar", "123 Foo St.", "C");
        entityManager.persistAndFlush(customer);

        Optional<Customer> foundCustomer = customerRepository.findByPhoneNumber("091234567890");

        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getName()).isEqualTo("Juan dela Cruz");
        assertThat(foundCustomer.get().getAccountType()).isEqualTo(Customer.AccountType.C);
    }

    @Test
    public void testFindByPhoneNumberNotFound() {
        Customer customer = new Customer("091234567890", "Juan dela Cruz", "juandc@foo.bar", "123 Foo St.", "C");
        entityManager.persistAndFlush(customer);

        Optional<Customer> foundCustomer = customerRepository.findByPhoneNumber("091234567891");

        assertThat(foundCustomer).isNotPresent();
    }
}