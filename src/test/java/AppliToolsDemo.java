import com.applitools.eyes.Eyes;
import com.applitools.eyes.MatchLevel;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by ONUR on 07.08.2016.
 */
public class AppliToolsDemo {
    final String APP_NAME = "BitbarSampleApp";
    final String TEST_NAME = "BitbarDemo";
    final String DEVICE = "GoogleGalaxyNexus";
    private AndroidDriver driver;
    private WebDriverWait wait;
    private Eyes eyes;

    //Do the Setup before tests
    @BeforeClass
    public void setUp() throws MalformedURLException {
        //Setup of Applitools Eyes
        eyes = new Eyes();
        //Set API Key of Eyes
        eyes.setApiKey("YOUR IP KEY");
        //Set Match Level
        eyes.setMatchLevel(MatchLevel.STRICT);
        //Set baseline name
        eyes.setBaselineName("GoogleGalaxyNexus-Demo");
        //Set host operating System as our device
        eyes.setHostOS(DEVICE);

        //Setup of Appium
        DesiredCapabilities caps = DesiredCapabilities.android();
        //Set apk location
        caps.setCapability(MobileCapabilityType.APP, "C:\\ApplitoolsApk\\BitbarSampleApp.apk");
        //Set device name
        caps.setCapability(MobileCapabilityType.DEVICE_NAME,"GoogleGalaxyNexus");
        //Set platform name as Android
        caps.setCapability("platformName", "Android");
        //Set emulator version as 4.4.2
        caps.setCapability("platformVersion", "4.4.2");
        //And finally create a driver variable with above settings.
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);

        //Set Wait Time
        wait = new WebDriverWait(driver,15);
    }


    @Test
    public void bitBarDemoTest() throws MalformedURLException, InterruptedException,URISyntaxException {
        //Open Eyes and start visual testing
        eyes.open(driver, APP_NAME, TEST_NAME);

        //Visual check point #1
        eyes.checkWindow("Start Screen");

        //Click second radio button
        driver.findElement(By.name("Use Testdroid Cloud")).click();

        //Visual check point #2
        eyes.checkWindow("Answer selected");

        //Write "SW Test Academy" to text box
        driver.findElement(By.className("android.widget.EditText")).sendKeys("SW Test Academy");

        //Click answer button
        driver.findElement(By.name("Answer")).click();

        //Synchronization after click. Wait until "You are right!" text appear on second screen
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("You are right!")));

        // Visual validation point #3
        eyes.checkWindow("Answer is correct");

        // End visual testing. Validate visual correctness.
        eyes.close();
    }

    @AfterClass
    public void teardown(){
        //Abort eyes if it is not closed
        eyes.abortIfNotClosed();

        //close the app
        driver.quit();
    }

}

