package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.entity.ResponseMessage;
import com.example.mdbspringbootreactive.entity.TransferRequest;
import com.example.mdbspringbootreactive.enumeration.ErrorReason;
import com.example.mdbspringbootreactive.exception.AccountNotFoundException;
import com.example.mdbspringbootreactive.exception.TransactionException;
import com.example.mdbspringbootreactive.model.Account;
import com.example.mdbspringbootreactive.model.Txn;
import com.example.mdbspringbootreactive.model.TxnEntry;
import com.example.mdbspringbootreactive.repository.AccountRepository;
//import com.example.mdbspringbootreactive.service.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class AccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private static void printLastLineStackTrace(String context) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        LOGGER.info("Stack trace's last line: " + stackTrace[stackTrace.length - 1].toString() + " from " + context);
    }

    @PostMapping("/create")
    public Mono<Account> createAccount(@RequestBody Account account) {
        printLastLineStackTrace("POST /account");
        return accountRepository.save(account);
    }

    @GetMapping("/account/{id}")
    public Mono<Account> getAccount(@PathVariable String id) {
        printLastLineStackTrace("GET /account/" + id);
//        return accountRepository.findByAccountNum(id).switchIfEmpty(Mono.error(new AccountNotFoundException()));
        return accountRepository.findById(id);
    }

    @GetMapping("/test")
    public Mono<String> testServer() {
        return Mono.just("Hello World");
    }


    @ExceptionHandler(AccountNotFoundException.class)
    ResponseEntity<ResponseMessage> accountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ResponseMessage(ErrorReason.ACCOUNT_NOT_FOUND.name()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<ResponseMessage> duplicateAccount(DuplicateKeyException ex) {
        return ResponseEntity.badRequest().body(new ResponseMessage(ErrorReason.DUPLICATE_ACCOUNT.name()));
    }
}
