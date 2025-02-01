package org.risingtide.model;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Customer {
    public Customer(String phoneNumber, String name, String email, String address1, String accountType) {
        this(phoneNumber, name, email, address1, null, accountType);
    }

    public Customer(String phoneNumber, String name, String email, String address1, String address2, String accountType) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.accountType = AccountType.valueOf(accountType);

        Account account = new Account();
        account.setAccountType(this.accountType);
        account.setBalance(0.0);
        account.setCustomer(this);

        this.accounts = new ArrayList<>();
        this.accounts.add(account);
    }

    @RequiredArgsConstructor
    public enum AccountType {
        S("savings"),
        C("checking");

        @Getter
        private final String value;

        @Override
        public String toString() {
            return this.name();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message="phoneNumber is required field")
    private String phoneNumber;

    @NotBlank(message="name is required field")
    private String name;

    @Email(message="email is invalid")
    @NotBlank(message="email is required field")
    private String email;

    @NotBlank(message="address is required field")
    private String address1;
    private String address2;

    @NotNull(message="accountType is required field")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(mappedBy="customer", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Account> accounts;
}
