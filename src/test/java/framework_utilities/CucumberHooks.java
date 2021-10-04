package framework_utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.time.Instant;

public class CucumberHooks {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static JsonObject testDataJsonObj;
    public static Scenario scenario;

    @Before(order = 0)
    public void initializeSetup(Scenario scenario) throws Exception {
        System.out.println(Instant.now().toString() + " Initializing the setup");

        //Reads common config details
        if(System.getProperty("dataproviderthreadcount") == null) {
            System.getProperties().load(new FileInputStream("src/test/resources/test_data/config.properties"));
        }

        JsonParser parser = new JsonParser();
        //Reads common test data from json file
        Object testDataObj = parser.parse(new FileReader("src/test/resources/test_data/CommonTestData.json"));
        testDataJsonObj = (JsonObject) testDataObj;

        CucumberHooks.scenario = scenario;
    }

    @Before( order = 1)
    public void initializeWebDriverSetup() throws Exception {
        System.out.println(Instant.now().toString() + " Initializing the web driver setup");
        System.out.println("browser name - " + System.getProperty("browser"));
        driver.set(WebDriverUtils.createWebDriver());
    }

    @After(order = 0)
    public void cleanupSetup() {
        try {
            if (scenario.isFailed()) {
                BufferedImage image = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver.get()).getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                scenario.embed(imageInByte, "image/png","");
                baos.close();
            }
            driver.get().quit();
            driver.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Instant.now().toString() + " Cleanup setup was executed");
    }
}
