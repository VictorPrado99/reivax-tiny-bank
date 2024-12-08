package cloud.reivax.tiny_bank.api.controllers;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.CreateTransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
public interface AccountsController {

    @Operation(summary = "Get a list of accounts by a comma separated list of Account Ids, " +
            "if not existent or not following UUID rules, the value will be ignored")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts successfully recovered")
    })
    @GetMapping
    ResponseEntity<List<AccountDto>> getAccounts(@RequestParam(required = false) String accountsIds);

    @Operation(summary = "Create a transaction (e.g. deposit, withdraw, transfer)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdraw completed")
    })
    @PutMapping("/transactions")
    ResponseEntity<Void> transactionExchange(@RequestBody CreateTransactionDto createTransactionDto);
}
