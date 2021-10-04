package framework_utilities;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions(
        features = "classpath:feature_files",
        glue = {"framework_utilities", "step_definitions"},
        plugin = {"pretty",
                "json:target/cucumber_json.json",
                "rerun:target/rerun.txt",
                "timeline:target/timeline-report"},
        monochrome = true,
        strict = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        if (System.getProperty("dataproviderthreadcount") == null)
            System.setProperty("dataproviderthreadcount", "1");
        return super.scenarios();
    }
}
