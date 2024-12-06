package cloud.reivax.tiny_bank.api.controllers;

import cloud.reivax.tiny_bank.api.dtos.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface TinyBankController {

    @Operation(summary = "Get specific user by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully recovered"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "406", description = "Invalid input provided")
    })
    @GetMapping(path = "/{userId}", produces = "application/json")
    ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId);

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "406", description = "Invalid input provided")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto);

    @Operation(summary = "Disable a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User disabled successfully"),
            @ApiResponse(responseCode = "406", description = "Invalid input provided")
    })
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deactivateUser(@PathVariable("userId") String userId);

}
