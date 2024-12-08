package cloud.reivax.tiny_bank.api.controllers;

import cloud.reivax.tiny_bank.api.dtos.users.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.users.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UsersController {

    @Operation(summary = "Get specific user by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully recovered"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "406", description = "Invalid input provided", content = @Content)
    })
    @GetMapping(path = "/{user}", produces = "application/json")
    ResponseEntity<UserDto> getUser(@PathVariable("user") String userId);

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully, resource path returned in the header \"location\" with format \"/users/{userId}\""),
            @ApiResponse(responseCode = "406", description = "Invalid input provided")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto);

    @Operation(summary = "Disable an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User disabled successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "406", description = "Invalid input provided")
    })
    @DeleteMapping("/{user}")
    ResponseEntity<Void> deactivateUser(@PathVariable("user") String userId);

}
