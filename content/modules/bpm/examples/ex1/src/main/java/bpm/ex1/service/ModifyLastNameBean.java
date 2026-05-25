package bpm.ex1.service;

import org.springframework.stereotype.Component;

@Component("sample_ModifyLastNameBean")
public class ModifyLastNameBean {

    public String newLastName(String lastName) {

        return lastName + " (modified)";

    }
}