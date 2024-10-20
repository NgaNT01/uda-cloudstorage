package com.udacity.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationFlowTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;

	@BeforeAll
	static void setupDriver() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void init() {
		this.driver = new FirefoxDriver();
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
	}

	@AfterEach
	public void teardown() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testLoginFailureForNonexistentUser() {
		Assertions.assertFalse(attemptLogin("NON_EXISTENT", "password123"));
	}

	@Test
	public void testSuccessfulLogin() {
		Assertions.assertTrue(createNewUser("Nguyen", "Nga", "TEST", "password123"));

		Assertions.assertTrue(attemptLogin("NEW_USER", "password123"));
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	public void testSuccessfulLogout() {
		driver.get("http://localhost:" + this.port + "/signup");

		signupPage.setUsername("NEW_USER");
		signupPage.setPassword("password123");
		signupPage.setLastName("Nguyen");
		signupPage.setFirstName("Nga");

		signupPage.submit();
		Assertions.assertTrue(signupPage.success());

		driver.get("http://localhost:" + this.port + "/login");

		loginPage.setUsername("NEW_USER");
		loginPage.setPassword("password123");
		loginPage.submit();

		Assertions.assertTrue(loginPage.success());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/logout");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void testRedirectToLoginForUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testLoginPageAccessibilityForUnauthorizedUser() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testSignupPageAccessibilityForUnauthorizedUser() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	private boolean attemptLogin(String username, String password) {
		driver.get("http://localhost:" + this.port + "/login");

		loginPage.setUsername(username);
		loginPage.setPassword(password);
		loginPage.submit();

		return loginPage.success();
	}

	private boolean createNewUser(String firstName, String lastName, String username, String password) {
		driver.get("http://localhost:" + this.port + "/signup");

		signupPage.setUsername(username);
		signupPage.setPassword(password);
		signupPage.setLastName(firstName);
		signupPage.setFirstName(lastName);
		signupPage.submit();

		return signupPage.success();
	}

}
