package com.udacity.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "signup-submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-form-container")
    private WebElement signupFormContainer;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submit() {
        submitButton.click();
    }

    public void setFirstName(String name) {
        firstNameField.clear();
        firstNameField.sendKeys(name);
    }

    public void setLastName(String name) {
        lastNameField.clear();
        lastNameField.sendKeys(name);
    }

    public void setUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public boolean success() {
        return !hasErrorMessage();
    }

    private boolean hasErrorMessage() {
        boolean result = false;
        try {
            result = !signupFormContainer.findElements(By.id("signup-error-message")).isEmpty();

        } catch (Exception ignored) {}

        return result;
    }

}
