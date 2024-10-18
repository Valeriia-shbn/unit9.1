package dataGeneration;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import static io.restassured.RestAssured.given;

@Getter
public class DataGenerator {


    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    public static void createUser(RegistrationDto registrationDto){
        given()
                .spec(requestSpec)
                .body(registrationDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

    }

    public static Person getActiveUser(){
        Person person = Person.generateFakePerson();
        createUser(new RegistrationDto(person.getFullName(), person.getPassword(), "active"));
        return person;
    }
    public static Person getBlockedUser() {
        Person person = Person.generateFakePerson();
        createUser(new RegistrationDto(person.getFullName(), person.getPassword(), "blocked"));
        return person;
    }

    public static String getRandomPwd(){
        return Person.generateFakePerson().getPassword();
    }

    public static String getRandomEmail(){
        return Person.generateFakePerson().getFullName();
    }
}
