package eu.uflow.userservice.controller;

import eu.uflow.userservice.dto.UserDto;
import eu.uflow.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserDto> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getById(@PathVariable final String id) {
        return this.userService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> createUser(@RequestBody final Mono<UserDto> userDto) {
        return this.userService.createUser(userDto);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(
            @PathVariable final String id,
            @RequestBody final Mono<UserDto> userDto) {
        return this.userService.updateUser(id, userDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable final String id) {
        return this.userService.deleteUser(id);
    }
}
