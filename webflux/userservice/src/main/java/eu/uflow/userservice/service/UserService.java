package eu.uflow.userservice.service;

import eu.uflow.userservice.dto.UserDto;
import eu.uflow.userservice.repository.UserRepository;
import eu.uflow.userservice.util.EntityDtoUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<UserDto> getAll() {
        return this.userRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getById(final String id) {
        return this.userRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUser(final Mono<UserDto> userDto) {
        return userDto
                .map(EntityDtoUtil::toEntity)
                .log()
                .flatMap(this.userRepository::save)
                .log()
                .map(EntityDtoUtil::toDto)
                .log();
    }

    public Mono<UserDto> updateUser(final String id, final Mono<UserDto> userDto) {
        return this.userRepository.findById(id)
                .flatMap(user -> userDto
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(final String id) {
        return this.userRepository.deleteById(id);
    }

}
