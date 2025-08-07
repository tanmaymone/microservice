package com.tanmay.accounts.service;

import com.tanmay.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     * @param customerDto- Customer DTO Object
     */
    void createAccount(CustomerDto customerDto);
}