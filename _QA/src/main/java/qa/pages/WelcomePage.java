package qa.pages;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import qa.base.BaseTest;

public class WelcomePage extends BaseTest {
    @AndroidFindBy (accessibility = "Continue, tap to continue without logging in.") private MobileElement continueWithoutButton;

    public WelcomePage tapContinueWithoutButton() {
        click(continueWithoutButton);
        return this;
    }
}
