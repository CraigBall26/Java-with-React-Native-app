package qa.base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.utils.TestUtils;
import java.io.File;

public class BaseTest {
    protected AppiumDriver driver;
    protected Properties props;
    InputStream inputStream;

    public BaseTest() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Parameters({"platformName", "deviceName"})
    @BeforeTest

    public void beforeTest(String platformName, String deviceName) {
        try {
            props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
            desiredCapabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            desiredCapabilities.setCapability("avd", "Pixel_4_XL");
            desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
            desiredCapabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
            String appUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "app" + File.separator + "app-release.apk";
            desiredCapabilities.setCapability("app", appUrl);

            URL url = new URL(props.getProperty("appiumURL"));
            driver = new AndroidDriver(url, desiredCapabilities);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(MobileElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e, String txt){
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public void getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e);
        e.getAttribute(attribute);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

}
