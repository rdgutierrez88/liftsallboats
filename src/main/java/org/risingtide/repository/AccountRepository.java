package org.risingtide.repository;

import org.risingtide.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Customer, Long> {
}
