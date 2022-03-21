package ui.ex1.exception;

// tag::exception[]
public class ZeroBalanceException extends RuntimeException {

    public ZeroBalanceException() {
        super("Insufficient funds in your account");
    }
}
// end::exception[]
