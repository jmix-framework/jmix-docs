package ui.ex1.exception;

public class ZeroAccountBalanceException extends RuntimeException {

    public ZeroAccountBalanceException() {
        super("Insufficient funds in your account");
    }
}
