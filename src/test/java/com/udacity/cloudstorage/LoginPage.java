package com.udacity.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "login-form-container")
    private WebElement loginFormContainer;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "login-submit-button")
    private WebElement loginButton;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submit() {
        loginButton.click();
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
        boolean hasError = false;
        try {
            hasError = !loginFormContainer.findElements(By.id("invalid-credentials-message")).isEmpty();

        } catch (Exception ignored) {}

        return hasError;
    }


}
