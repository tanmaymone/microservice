package com.tanmay.accounts.service.impl;

import com.tanmay.accounts.constants.AccountsConstants;
import com.tanmay.accounts.dto.CustomerDto;
import com.tanmay.accounts.entity.Accounts;
import com.tanmay.accounts.entity.Customer;
import com.tanmay.accounts.exception.CustomerAlreadyExistsException;
import com.tanmay.accounts.mapper.CustomerMapper;
import com.tanmay.accounts.repository.AccountsRepository;
import com.tanmay.accounts.repository.CustomerRepository;
import com.tanmay.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AcountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with mobile number" + customerDto.getMobileNumber());
        }
        Customer savedCustomer =  customerRepository.save(customer);
        accountsRepository.save();
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

}
