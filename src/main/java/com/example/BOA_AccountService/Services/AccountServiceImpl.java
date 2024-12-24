package com.example.BOA_AccountService.Services;



import com.BOA_UserManagement.Model.User;
import com.example.BOA_AccountService.Model.Account;
import com.example.BOA_AccountService.Repositories.AccountRepository;
import com.example.BOA_AccountService.Services.Interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate; // Injecting RestTemplate
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    // 1. Create Account
    @Override
    public Account createAccount(Account account) {
        logger.info("Creating new account for user: {}", account.getUserId());

        // Fetch User details from User Service using RestTemplate
        String userUrl = "http://localhost:8083/users/" + account.getUserId();  // Assuming UserService runs at this URL
        User user = restTemplate.getForObject(userUrl, User.class);

        if (user != null) {
            logger.info("User found: {}", user);
            Account createdAccount = accountRepository.save(account);
            logger.info("Created new account with ID: {}", createdAccount.getAccountId());
            return createdAccount;
        } else {
            logger.error("User with ID: {} not found, cannot create account", account.getUserId());
            return null;
        }
    }

    // 2. Update Account
    @Override
    public Account updateAccount(Long accountId, Account account) {
        logger.info("Attempting to update account with ID: {}", accountId);
        Optional<Account> existingAccountOpt = accountRepository.findByAccountId(accountId);

        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            account.setAccountId(accountId);  // Retain the original account ID
            Account updatedAccount = accountRepository.save(account);
            logger.info("Account with ID: {} updated successfully", accountId);
            return updatedAccount;
        }

        logger.error("Account with ID: {} not found for update", accountId);
        return null;
    }

    // 3. Retrieve All Accounts
    @Override
    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        return accountRepository.findAll();
    }

    // 4. Retrieve Accounts by User ID
    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        logger.info("Fetching accounts for user ID: {}", userId);
        return accountRepository.findByUserId(userId);
    }

    // 5. Retrieve Account by Account ID
    @Override
    public Optional<Account> getAccountByAccountId(Long accountId) {
        logger.info("Fetching account with ID: {}", accountId);
        return accountRepository.findByAccountId(accountId);
    }

    // 6. Delete Account by Account ID
    @Override
    public boolean deleteAccount(Long accountId) {
        logger.info("Attempting to delete account with ID: {}", accountId);
        Optional<Account> existingAccount = accountRepository.findByAccountId(accountId);

        if (existingAccount.isPresent()) {
            accountRepository.deleteById(accountId);
            logger.info("Account with ID: {} deleted successfully", accountId);
            return true;
        }

        logger.error("Account with ID: {} not found for deletion", accountId);
        return false;
    }

    // 7. Retrieve Account Balance
    @Override
    public Double getAccountBalance(Long accountId) {
        logger.info("Fetching balance for account with ID: {}", accountId);
        return accountRepository.findByAccountId(accountId)
                .map(Account::getBalance)
                .orElse(null);
    }

    // 8. Fetch Balances for Multiple Accounts of a User
    @Override
    public List<Double> getBalancesForUserAccounts(Long userId) {
        logger.info("Fetching balances for accounts of user ID: {}", userId);
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream().map(Account::getBalance).toList();
    }
}
