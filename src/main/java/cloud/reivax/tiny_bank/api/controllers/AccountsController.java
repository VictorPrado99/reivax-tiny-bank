package cloud.reivax.tiny_bank.api.controllers;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.CreateTransactionDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.TransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
public interface AccountsController {

    @Operation(summary = "Get a list of accounts by a comma separated list of Account Ids, " +
            "if not existent or not following UUID rules, the value will be ignored. If parameter is not sent, " +
            "it will retrieve all accounts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts successfully recovered")
    })
    @GetMapping
    ResponseEntity<List<AccountDto>> getAccounts(@RequestParam(required = false) String accountsIds);

    @Operation(summary = "Create a transaction (e.g. deposit, withdraw, transfer)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction completed"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PutMapping("/transactions")
    ResponseEntity<Void> transactionExchange(@RequestBody CreateTransactionDto createTransactionDto);

    @Operation(summary = "Get account balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve balance"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "406", description = "AccountId is not an UUID")
    })
    @GetMapping("/balances/{accountId}")
    ResponseEntity<AccountDto> retrieveAccountBalance(@PathVariable String accountId);

    @Operation(summary = "Get account transaction history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve transaction history"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "406", description = "AccountId is not an UUID")
    })
    @GetMapping("/transactions/{accountId}")
    ResponseEntity<List<TransactionDto>> retrieveAccountTransactionHistory(@PathVariable String accountId);
}
