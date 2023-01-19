import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;



public class FirstAutoTest {
    private static final Logger log = Logger.getLogger(String.valueOf(FirstAutoTest.class));
    private final String login = "wasej93407@dni8.com";
    private final String pas = "Natalia12345!";
    private WebDriver driver;
    ChromeOptions options = new ChromeOptions();

    @Test
    public void autoTest(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        loginInOtus();
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        actions.moveToElement(driver.findElement(By.xpath("//span[@class='header3__user-info-name']")))
                .click(driver.findElement(By.xpath("//*[normalize-space(text()) = 'Мой профиль']"))).release().perform();
        driver.findElement(By.xpath("//*[@id='id_fname']")).clear();
        driver.findElement(By.xpath("//*[@id='id_fname_latin']")).clear();
        driver.findElement(By.xpath("//*[@id='id_lname']")).clear();
        driver.findElement(By.xpath("//*[@id='id_lname_latin']")).clear();
        driver.findElement(By.xpath("//*[@id='id_blog_name']")).clear();
        driver.findElement(By.xpath("//*[@name='date_of_birth']")).clear();
        actions.sendKeys(driver.findElement(By.xpath("//*[@id='id_fname']")),"Имя")
                .sendKeys(driver.findElement(By.xpath("//*[@id='id_fname_latin']")),"FName")
                .sendKeys(driver.findElement(By.xpath("//*[@id='id_lname']")),"Фамилия")
                .sendKeys(driver.findElement(By.xpath("//*[@id='id_lname_latin']")),"LName")
                .sendKeys(driver.findElement(By.xpath("//*[@id='id_blog_name']")),"BlogName")
                .sendKeys(driver.findElement(By.xpath("//*[@name='date_of_birth']")),"11.11.2000")
                .click(driver.findElement(By.cssSelector("[name='country'] ~ div")))
                .click(driver.findElement(By.xpath("//ancestor::*[contains(@data-ajax-slave, 'by_country')]//button[@title='Россия']")))
                .moveToElement(driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city"))).click().perform();
        driver.findElement(By.cssSelector("button.lk-cv-block__select-option[title='Москва']")).click();
        actions.moveToElement(driver.findElement(By.xpath("//div[contains(text(),'Средний')]"))).click().perform();
        driver.findElement(By.cssSelector("button.lk-cv-block__select-option[title='Средний (Intermediate)']")).click();
       actions.click(driver.findElement(By.xpath("//*[text()='Способ связи']")))
                .click(driver.findElement(By.xpath("//ancestor::*[contains(@data-num, '0')]//button[6]")))
                .sendKeys(driver.findElement(By.xpath("//*[@id='id_contact-0-value']")),"111")
                .click(driver.findElement(By.cssSelector(".js-lk-cv-custom-select-add"))).release().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        actions.click(driver.findElement(By.xpath("//*[text()='Способ связи']")))
                .click(driver.findElement(By.xpath("//ancestor::*[contains(@data-num, '1')]//button[6]")))
                .sendKeys(driver.findElement(By.xpath("//*[@id='id_contact-1-value']")),"222")
                .click(driver.findElement(By.xpath("//*[normalize-space(text()) = 'Сохранить и продолжить']"))).release().perform();
        driver.manage().deleteAllCookies();
        driver.get("https://otus.ru");
        loginInOtus();
        actions.moveToElement(driver.findElement(By.xpath("//span[@class='header3__user-info-name']")))
               .click(driver.findElement(By.xpath("//*[normalize-space(text()) = 'Мой профиль']"))).release().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assertions.assertEquals("Имя", driver.findElement(By.xpath("//*[@id='id_fname']")).getAttribute("value"));
        Assertions.assertEquals("Фамилия", driver.findElement(By.xpath("//*[@id='id_lname']")).getAttribute("value"));
        Assertions.assertEquals("FName", driver.findElement(By.xpath("//*[@id='id_fname_latin']")).getAttribute("value"));
        Assertions.assertEquals("LName", driver.findElement(By.xpath("//*[@id='id_lname_latin']")).getAttribute("value"));
        Assertions.assertEquals("BlogName", driver.findElement(By.xpath("//*[@id='id_blog_name']")).getAttribute("value"));
        Assertions.assertEquals("11.11.2000", driver.findElement(By.xpath("//*[@name='date_of_birth']")).getAttribute("value"));
        Assertions.assertEquals("Россия", driver.findElement(By.cssSelector("[name='country'] ~ div")).getText());
        Assertions.assertEquals("Москва", driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city")).getText());
        Assertions.assertEquals("111", driver.findElement(By.xpath("//*[@id='id_contact-0-value']")).getAttribute("value"));
        Assertions.assertEquals("222", driver.findElement(By.xpath("//*[@id='id_contact-1-value']")).getAttribute("value"));
        log.info("Запись в лог");
    }

    private void loginInOtus(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".header3__button-sign-in"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearAndEnter(By.cssSelector("input:not(.js-remove-field-error).js-email-input[placeholder*=\"Электронная почта\"]"), login);
        clearAndEnter(By.cssSelector(".js-psw-input[placeholder*=\"Введите пароль\"]"), pas);
        driver.findElement(By.xpath("//*[normalize-space(text()) = 'Войти' and @type='submit']")).submit();
     }

    private void clearAndEnter(By by, String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }
    @AfterEach
    public void close(){
        if (driver != null)
            driver.quit();
    }

}
