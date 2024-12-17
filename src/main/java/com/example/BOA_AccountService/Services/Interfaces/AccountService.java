package com.example.BOA_AccountService.Services.Interfaces;

import com.example.BOA_AccountService.Model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    // Create Account
    Account createAccount(Account account);

    // Update Account
    Account updateAccount(Long accountId, Account account);

    // Retrieve All Accounts
    List<Account> getAllAccounts();

    // Retrieve Accounts by User ID
    List<Account> getAccountsByUserId(Long userId);

    // Retrieve Account by Account ID
    Optional<Account> getAccountByAccountId(Long accountId);

    // Delete Account by Account ID
    boolean deleteAccount(Long accountId);

    // Retrieve Account Balance
    Double getAccountBalance(Long accountId);

    // Fetch Balances for Multiple Accounts of a User
    List<Double> getBalancesForUserAccounts(Long userId);
}
