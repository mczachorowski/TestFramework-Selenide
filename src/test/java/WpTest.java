import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class WpTest {
    private final SelenideElement acceptCookies = $(By.xpath("//button[text() = 'AKCEPTUJĘ I PRZECHODZĘ DO SERWISU']"));
    private final SelenideElement homePageLogo = $(By.cssSelector("a svg[data-testid]"));
    private final SelenideElement homePageEmailLink = $(By.cssSelector("div a[href*='https://poczta.wp.pl']"));
    private final ElementsCollection homePageArticlesHeadlines = $$(By.cssSelector("a[data-bdbd]"));
    private final SelenideElement emailPageLogo = $(By.cssSelector("div img[alt]"));
    private final SelenideElement emailLoginField = $(By.id("login"));
    private final SelenideElement emailPasswordField = $(By.id("password"));
    private final SelenideElement emailLoginBtn = $(By.cssSelector("button[type]"));
    private final SelenideElement emailErrorMessage = $(By.cssSelector("div[type]"));
    private final SelenideElement emailCaptchaElement = $(By.cssSelector("#captcha-container"));

    @Test
    public void wpEntryTest() {
        open("https://www.wp.pl");
        if (acceptCookies.isDisplayed()) {
            acceptCookies.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        } else {
            homePageLogo.shouldBe(Condition.visible, Duration.ofSeconds(5));
        }
    }

    @Test
    public void wpRedirectionToEmail() {
        open("https://www.wp.pl");
        if (acceptCookies.isDisplayed()) {
            acceptCookies.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        } else
            homePageEmailLink.shouldBe(Condition.visible, Duration.ofSeconds(5));
        Assert.assertEquals(homePageEmailLink.getText().toLowerCase(), "poczta");
        homePageEmailLink.click();
        emailPageLogo.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    public void wpEmailWrongCredentials() {
        open("https://www.wp.pl");
        if (acceptCookies.isDisplayed()) {
            acceptCookies.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        } else
            homePageEmailLink.shouldBe(Condition.visible, Duration.ofSeconds(5));
        Assert.assertEquals(homePageEmailLink.getText().toLowerCase(), "poczta");
        homePageEmailLink.click();
        emailLoginField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        emailLoginField.sendKeys("abcdef");
        emailPasswordField.shouldBe(Condition.visible, Duration.ofSeconds(5)).sendKeys("xyz");
        emailCaptchaElement.shouldBe(Condition.visible, Duration.ofSeconds(5));
        emailLoginBtn.click();
        emailErrorMessage.shouldBe(Condition.visible, Duration.ofSeconds(155));
        Assert.assertEquals(emailErrorMessage.getText(), "Nieprawidłowy adres e‑mail lub hasło");
    }

    @Test
    public void wpHomePageArticleImagesCheck() {
        open("https://www.wp.pl");
        if (acceptCookies.isDisplayed()) {
            acceptCookies.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        } else {
            homePageLogo.shouldBe(Condition.visible, Duration.ofSeconds(5));
        }
        List<String> articles = new ArrayList<>();
        for (SelenideElement e : homePageArticlesHeadlines)
            articles.add(e.scrollTo().getText() + "\n");
        System.out.println(articles);
    }
}
