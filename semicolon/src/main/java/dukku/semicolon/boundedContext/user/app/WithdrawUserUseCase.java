package dukku.semicolon.boundedContext.user.app;

import dukku.semicolon.boundedContext.user.entity.User;
import dukku.semicolon.boundedContext.user.out.UserRepository;
import dukku.semicolon.shared.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawUserUseCase {

    private final UserSupport userSupport;

    public void withdraw(UUID userUuid) {
        User user = userSupport.getActiveUserByUuid(userUuid);
        user.withdraw();
    }
}
