package dataaccess.ex1.bean;

import org.springframework.stereotype.Component;

@Component
public class EncryptionService {

    public String encrypt(String input) {
        return "{encrypted}" + input;
    }

    public String decrypt(String input) {
        return input == null || !input.startsWith("{encrypted}") ? input : input.substring(11);
    }
}
