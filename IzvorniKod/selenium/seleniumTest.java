import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class seleniumTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Korisnik\\Desktop\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:3000/login");

        driver.manage().window().maximize();
        driver.findElement(By.name("email")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("password");

        WebElement button = driver.findElement(By.className("accentBtn"));
        button.click();

        driver.getCurrentUrl().then((url) -> {
            if (url.contains("/home")) {
                System.out.println("Login successful!");
            } else {
                System.err.println("Login failed!");
            }
            driver.quit();        
        });

        secondTest(driver);
        thirdTest(driver);
        foruthTest(driver);
    }

    public static void secondTest(WebDriver driver) {
        driver.get("http://localhost:3000/login");
        // Perform additional test steps
        driver.manage().window().maximize();
        WebElement anchorTag = driver.findElement(By.className("login_createAccountLink__FV3W+"));
        anchorTag.click();
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement emailField = driver.findElement(By.name("email"));

        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        emailField.sendKeys("johndoe@fer.com");
        
        WebElement registerButton = driver.findElement(By.className("accentBtn"));
        registerButton.click();
        driver.getCurrentUrl().then((url) -> {
            if (url.contains("/changepass")) {
                System.out.println("registration successful!");
            } else {
                System.err.println("Registration failed!");
            }
            driver.quit();        
        });
    }

    public static void thirdTest(WebDriver driver) {
        driver.get("http://localhost:3000/home");

        driver.manage().window().maximize();
        WebElement element = driver.findElement(By.xpath("//div[@class='card_card__611WY']"));
        element.click();

        driver.getCurrentUrl().then((url) -> {
            if (url.contains("/home/en")) {
                System.out.println("Successful!");
            } else {
                System.err.println("Failed!");
            }
            driver.quit();
        });
    }

    public static void foruthTest(WebDriver driver) {
        driver.get("http://localhost:3000/login");

        driver.manage().window().maximize();
        driver.findElement(By.name("email")).sendKeys("admin");

        WebElement button = driver.findElement(By.className("accentBtn"));
        button.click();

        driver.getCurrentUrl().then((url) -> {
            if (url.contains("/home")) {
                System.out.println("Login successful!");
            } else {
                System.err.println("Login failed!");
            }
            driver.quit();        
        });

    }
}
