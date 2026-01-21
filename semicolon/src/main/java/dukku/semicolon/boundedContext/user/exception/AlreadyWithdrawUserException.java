package dukku.semicolon.boundedContext.user.exception;

public class AlreadyWithdrawUserException extends RuntimeException {

    public AlreadyWithdrawUserException() {
        super("이미 탈퇴한 회원입니다.");
    }
}
