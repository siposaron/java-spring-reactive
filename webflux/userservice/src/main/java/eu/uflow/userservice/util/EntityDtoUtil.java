package eu.uflow.userservice.util;

import eu.uflow.userservice.dto.TransactionRequestDto;
import eu.uflow.userservice.dto.TransactionResponseDto;
import eu.uflow.userservice.dto.TransactionStatus;
import eu.uflow.userservice.dto.UserDto;
import eu.uflow.userservice.entity.User;
import eu.uflow.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class EntityDtoUtil {
    public static UserDto toDto(User user) {
        final var userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        final var user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserTransaction toEntity(final String userId, final TransactionRequestDto dto) {
        final var entity = new UserTransaction();
        entity.setUserId(userId);
        entity.setAmount(dto.getAmount());
        entity.setTransactionDate(LocalDateTime.now());
        return entity;
    }

    public static TransactionResponseDto toDto(final UserTransaction entity,
                                               final TransactionStatus status) {
        final var dto = new TransactionResponseDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setAmount(entity.getAmount());
        dto.setStatus(status);
        return dto;
    }

    public static TransactionResponseDto fromRequestToDto(
            final String userId,
            final TransactionRequestDto requestDto,
            final TransactionStatus status) {
        final var dto = new TransactionResponseDto();
        dto.setUserId(userId);
        dto.setAmount(requestDto.getAmount());
        dto.setStatus(status);
        return dto;
    }
}
