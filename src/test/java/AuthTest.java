import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import dataGeneration.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

class AuthTest {
    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @Test
    @DisplayName("Active valid user should login successfully")
    void shouldSuccessfullyLoginActiveUser() {
        var user = DataGenerator.getUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("button.button").click();
        $("h2").shouldBe((Condition.visible)).shouldHave(Condition.exactText("Личный кабинет"));

    }

    @Test
    @DisplayName("Blocked User Should Not Be Able to Login")
    void shouldNotSuccessfullyLoginBlockedUser() {
        var user = DataGenerator.getUser("blocked");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe((Condition.visible))
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("User Should not Be Able to Login With Incorrect Password")
    void shouldGetErrorMessageWhenLoginWithWrongPwd() {
        var user = DataGenerator.getUser("active");
        String pwd = DataGenerator.getRandomPwd();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(pwd);
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content").shouldBe((Condition.visible)).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("User Should not Be Able to Login With Incorrect Password")
    void shouldGetErrorMessageWhenLoginWithWrongUsername() {
        var user = DataGenerator.getUser("active");
        String username = DataGenerator.getRandomEmail();
        $("[data-test-id=login] input").setValue(username);
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe((Condition.visible))
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));

    }
}