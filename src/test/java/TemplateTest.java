import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TemplateTest {
    private final SelenideElement searchArea = $(By.xpath("//textarea[@aria-label='Szukaj']"));
    private final SelenideElement rejectCookies = $(By.xpath("//div[@class='QS5gu sy4vM' and text() = 'Odrzuć wszystko']"));
    private final SelenideElement acceptCookies = $(By.xpath("//div[@class='QS5gu sy4vM' and text() = 'Zaakceptuj wszystko']"));

    @Test
    public void userOpensWebsite() {
        open("https://google.pl");
        rejectCookies.shouldBe(visible, Duration.ofSeconds(5));
        acceptCookies.shouldBe(visible, Duration.ofSeconds(5)).click();
        searchArea.shouldBe(visible, Duration.ofSeconds(5));
    }

}
