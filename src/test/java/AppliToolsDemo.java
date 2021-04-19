
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.selenium.Eyes;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppliToolsDemo {
    final   String        APP_NAME  = "BitbarSampleApp";
    final   String        TEST_NAME = "BitbarDemo";
    final   String        DEVICE    = "Galaxy Nexus 4";
    private AndroidDriver driver;
    private WebDriverWait wait;
    private Eyes          eyes;

    //Do the Setup before tests
    @BeforeClass
    public void setUp() throws MalformedURLException {
        //Setup of Appium
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Galaxy Nexus 4");
        caps.setCapability(MobileCapabilityType.UDID, "192.168.56.102:5555"); //DeviceId from "adb devices" command
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator1"); //UIAutomator2 is only supported since Android 5.0
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("app", "/Users/onur/OneDrive/swtestacademy/stuff/bitbarapk/BitbarSampleApp.apk");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        //Set Wait Time
        wait = new WebDriverWait(driver, 30);

        //Setup of Applitools Eyes
        eyes = new Eyes();
        //Set API Key of Eyes
        eyes.setApiKey("RQiNJdjsA4lxbk4bevRCo103hWdDPxU3xiQPp1023TSA5wI110");
        //Set Match Level
        eyes.setMatchLevel(MatchLevel.STRICT);
        //Set host operating System as our device
        eyes.setHostOS(DEVICE);
    }

    @Test
    public void bitBarDemoTest() throws MalformedURLException, InterruptedException, URISyntaxException {
        //Open Eyes and start visual testing
        eyes.open(driver, APP_NAME, TEST_NAME);

        //Visual check point #1
        eyes.checkWindow("Start Screen");

        //Click second radio button

        driver.findElement(By.id("com.bitbar.testdroid:id/radio1")).click();
        //"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ScrollView"
        //                + "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.RadioGroup/android.widget"
        //                + ".RadioButton[2]"

        //Visual check point #2
        eyes.checkWindow("Answer selected");

        //Write "SW Test Academy" to text box
        driver.findElement(By.id("com.bitbar.testdroid:id/editText1")).sendKeys("SW Test Academy");

        //Click answer button
        driver.findElement(By.id("com.bitbar.testdroid:id/button1")).click();

        //Synchronization after click. Wait until "You are right!" text appear on second screen
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.bitbar.testdroid:id/textView1")));

        // Visual validation point #3
        eyes.checkWindow("Answer is correct");

        // End visual testing. Validate visual correctness.
        eyes.close();
    }

    @AfterClass
    public void teardown() {
        //close the app
        driver.quit();
        //Abort eyes if it is not closed
        eyes.abortIfNotClosed();
    }
}

