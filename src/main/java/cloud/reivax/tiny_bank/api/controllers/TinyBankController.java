package cloud.reivax.tiny_bank.api.controllers;

import cloud.reivax.tiny_bank.api.dtos.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface TinyBankController {

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId);

    @PostMapping
    ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto);

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deactivateUser(@PathVariable("userId") String userId);

}
