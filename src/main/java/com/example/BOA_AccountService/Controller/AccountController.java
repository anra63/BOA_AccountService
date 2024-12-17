package com.example.BOA_AccountService.Controller;

import com.example.BOA_AccountService.Model.Account;
import com.example.BOA_AccountService.Services.Interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // 1. Create Account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // 2. Update Account
    @PutMapping("/{accountid}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountid, @RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(accountid, account);
        return updatedAccount != null ? ResponseEntity.ok(updatedAccount)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 3. Retrieve All User Accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // 4. Retrieve Account by User ID
    @GetMapping("/by-user")
    public List<Account> getAccountByUserId(@RequestParam Long userid) {
        return accountService.getAccountsByUserId(userid);
    }

    // 5. Retrieve Account by Account ID
    @GetMapping("/{accountid}")
    public ResponseEntity<Account> getAccountByAccountId(@PathVariable Long accountid) {
        Optional<Account> account = accountService.getAccountByAccountId(accountid);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 6. Delete Account by Account ID
    @DeleteMapping("/{accountid}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountid) {
        return accountService.deleteAccount(accountid) ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 7. Retrieve Account Balance
    @GetMapping("/{accountid}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable Long accountid) {
        Double balance = accountService.getAccountBalance(accountid);
        return balance != null ? ResponseEntity.ok(balance)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 8. Fetch Balances for Multiple Accounts of a User
    @GetMapping("/balances")
    public List<Double> getBalancesForUserAccounts(@RequestParam Long userid) {
        return accountService.getBalancesForUserAccounts(userid);
    }
}
