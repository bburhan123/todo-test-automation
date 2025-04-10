package com.todomvc.utils;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public static WebDriver createWebDriver() throws MalformedURLException {
        WebDriver webDriver;

        String browser = ConfigManager.getConfig().get("browser").toString().toLowerCase();
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-browser-side-navigation");
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;
            case "chrome-docker":
                URL hubURL = new URL("http://localhost:4444/wd/hub");
                ChromeOptions dockerChromeOptions = new ChromeOptions();
                dockerChromeOptions.addArguments("disable-infobars");
                dockerChromeOptions.addArguments("--start-maximized");
                dockerChromeOptions.addArguments("--disable-browser-side-navigation");
                webDriver = new RemoteWebDriver(hubURL, dockerChromeOptions);
                ((RemoteWebDriver) webDriver).setFileDetector(new LocalFileDetector());
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return webDriver;
    }
}