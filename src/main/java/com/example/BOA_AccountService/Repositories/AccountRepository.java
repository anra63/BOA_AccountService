package com.example.BOA_AccountService.Repositories;

import com.example.BOA_AccountService.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);  // Custom query for finding accounts by user ID
    Optional<Account> findByAccountId(Long accountId);  // Find account by accountId
}
