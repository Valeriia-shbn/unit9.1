package dataGeneration;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {


    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void createUser(RegistrationDto registrationDto) {
        given()
                .spec(requestSpec)
                .body(registrationDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

    }

    public static RegistrationDto getUser(String status) {
        Faker faker = new Faker(new Locale("en"));
        var user = new RegistrationDto(faker.internet().emailAddress(), faker.internet().password(), status);
        createUser(user);
        return user;
    }

    public static String getRandomPwd() {
        Faker faker = new Faker(new Locale("en"));
        return faker.internet().password();
    }

    public static String getRandomEmail() {
        Faker faker = new Faker(new Locale("en"));
        return faker.internet().emailAddress()
    }
}
