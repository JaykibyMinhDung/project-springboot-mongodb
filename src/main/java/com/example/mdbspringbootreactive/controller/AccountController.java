package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.config.ApiResponse;
import com.example.mdbspringbootreactive.entity.ResponseMessage;
import com.example.mdbspringbootreactive.enumeration.ErrorReason;
import com.example.mdbspringbootreactive.exception.AccountNotFoundException;
import com.example.mdbspringbootreactive.model.Account;
import com.example.mdbspringbootreactive.model.Product;
import com.example.mdbspringbootreactive.repository.AccountRepository;
//import com.example.mdbspringbootreactive.service.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @PostMapping
    public Mono<ResponseEntity<ApiResponse>> createAccount(@RequestBody Account account) {
        printLastLineStackTrace("POST /account");
        return accountRepository.save(account).then(Mono.just(ResponseEntity.ok().body(
                new ApiResponse("Thêm người dùng thành công", HttpStatus.OK.value())
        ))).onErrorResume(error -> {
            LOGGER.error("Lỗi khi thêm người dùng " + error.getMessage());
            ApiResponse<List<Product>> response = new ApiResponse<>("Lỗi khi thêm người dùng", 0, null);
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
        });
    }

    @GetMapping("/{id}")
    public Mono<Account> getAccount(@PathVariable String id) {
        printLastLineStackTrace("GET /account/" + id);
        return accountRepository.findById(id);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse>> updatedAccount(@PathVariable String id, @RequestBody Account newUser) {
        printLastLineStackTrace("PATCH /account/" + id);
        return accountRepository.findById(id)
                .flatMap(user -> {
                    return accountRepository.save(newUser)
                            .map(updatedProduct -> ResponseEntity.ok().body(
                                    new ApiResponse("Cập nhật người dùng thành công", HttpStatus.OK.value(), updatedProduct)
                            ));
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse("Không tìm thấy người dùng", HttpStatus.NOT_FOUND.value(), null)
                )).onErrorResume(error -> {
                    LOGGER.error("Lỗi khi cập nhật ngươời dùng: " + error.getMessage());
                    ApiResponse<Product> response = new ApiResponse<>("Lỗi khi cập nhật ngươời dùng", 0, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse>> deleteAccount(@PathVariable String id) {
        printLastLineStackTrace("DELETE /account/" + id);
        return accountRepository.findById(id).flatMap(user -> {
            user.setActive(true);
            return accountRepository.save(user)
                    .then(Mono.just(ResponseEntity.ok().body(
                            new ApiResponse("Xóa người dùng thành công", HttpStatus.OK.value())
                    ))).onErrorResume(error -> {
                        LOGGER.error("Lỗi khi lấy danh sách đơn hàng: " + error.getMessage());
                        ApiResponse<Product> response = new ApiResponse<>("Xóa người dùng thất bại", 0, null);
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                    });
        });
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
