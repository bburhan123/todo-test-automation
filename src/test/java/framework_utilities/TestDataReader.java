package framework_utilities;

import com.google.gson.JsonObject;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class TestDataReader {
    public static JsonObject testDataJsonObj = CucumberHooks.testDataJsonObj;
    public Scenario scenario;
    public WebDriver driver;

    public TestDataReader() {
        driver = CucumberHooks.driver.get();
        scenario = CucumberHooks.scenario;
    }

    public String readData(String str) {
        if (str != null && str.length() > 0) {
            if (testDataJsonObj.has(str))
                str = testDataJsonObj.get(str).getAsString();
        } else
            str = null;
        return str;
    }
}
