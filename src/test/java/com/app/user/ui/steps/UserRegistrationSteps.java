package com.app.user.ui.steps;

import com.app.user.ui.pages.RegistrationPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class UserRegistrationSteps {

    @Autowired
    private WebDriver driver;

    @Autowired
    private RegistrationPage registrationPage;

    @Given("I navigate to the registration page")
    public void i_navigate_to_the_registration_page() {
        driver.get("http://localhost:8080/register");
    }

    @When("I fill in the registration form with valid data")
    public void i_fill_in_the_registration_form_with_valid_data() {
        registrationPage.fillRegistrationForm("John", "Doe", "john.doe@example.com", "password123", "1234567890");
    }

    @When("I fill in the registration form with an existing email")
    public void i_fill_in_the_registration_form_with_an_existing_email() {
        registrationPage.fillRegistrationForm("Jane", "Doe", "jane.doe@example.com", "password123", "1234567890");
    }

    @When("I submit the form")
    public void i_submit_the_form() {
        registrationPage.submitForm();
    }

    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        assertTrue(registrationPage.isConfirmationMessageDisplayed());
    }

    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        assertTrue(registrationPage.isErrorMessageDisplayed());
    }
}