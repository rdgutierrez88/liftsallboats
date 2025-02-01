package org.risingtide.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Customer.AccountType accountType;

    @Column
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Customer customer;
}
