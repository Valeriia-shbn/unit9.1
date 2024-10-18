package dataGeneration;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.Locale;


@Data
@AllArgsConstructor
@Setter
public class Person {
    private String fullName;
    private String password;


    public static Person generateFakePerson() {
        Faker faker = new Faker(new Locale("en"));
        return new Person(
                faker.internet().emailAddress(),
                faker.internet().password(8, 12, true, true, true));
    }

}
