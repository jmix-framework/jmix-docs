package datamodel.ex1.service;

import datamodel.ex1.entity.Person;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

// tag::person-api-service[]
@Validated
public interface PersonApiService {

    String NAME = "sample_PersonApiService";

    @NotNull
    @Valid // <1>
    List<Person> getPersons();

    void addNewPerson(@NotNull
                      @Length(min = 3)
                      String firstName,
                      @DecimalMax(message = "Person height can not exceed 300 centimeters",
                              value = "300")
                      @DecimalMin(message = "Person height should be positive",
                              value = "0", inclusive = false)
                      BigDecimal height,
                      @NotNull
                      String passportNumber
    );

    @Validated // <2>
    @NotNull
    String validatePerson(@Size(min = 5) String comment,
                          @Valid @NotNull Person person); // <3>
}
// end::person-api-service[]