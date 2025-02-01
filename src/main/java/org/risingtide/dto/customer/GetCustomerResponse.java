package org.risingtide.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.risingtide.dto.ApiResponse;
import org.risingtide.model.Account;
import org.risingtide.model.Customer;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCustomerResponse extends ApiResponse {
    private String phoneNumber;
    private String name;
    private String email;
    private String address1;
    private String address2;
    private List<Account> accounts;

    public GetCustomerResponse(Customer customer) {
        this.phoneNumber = customer.getPhoneNumber();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address1 = customer.getAddress1();
        this.address2 = customer.getAddress2();
        this.accounts = customer.getAccounts();
    }
}
