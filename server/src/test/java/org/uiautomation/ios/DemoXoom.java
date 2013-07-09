/*
 * Copyright 2012 ios-driver committers.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the Licence at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing permissions and limitations under
 *  the License.
 */

package org.uiautomation.ios;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.testng.annotations.Test;
import org.uiautomation.ios.server.IOSServer;
import org.uiautomation.ios.server.IOSServerConfiguration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * example for the documentation
 */
public class DemoXoom {

    public static void main(String[] args) throws Exception {
        String[] a = {"-port", "4444", "-host", "localhost",
//                "-aut", SampleApps.getUICatalogFile(),
//                "-aut", SampleApps.getUICatalogIpad(),
//                "-aut", SampleApps.getIntlMountainsFile(),
//                "-aut", "/Users/xsi/Desktop/ios_driver_06/iOS_app/XoomApp.app",
                "-aut", "/Users/xsi/Library/Developer/Xcode/DerivedData/XoomApp-efujdzjqrnaujveuynidgewfdpxs/Build/Products/Debug-iphonesimulator/XoomApp.app",
//                "-beta",
//                "-folder", "applications"};
                "-folder", "applications", "-simulators"};
        IOSServerConfiguration config = IOSServerConfiguration.create(a);

        IOSServer server = new IOSServer(config);
        server.start();

    /*IOSCapabilities cap = IOSCapabilities.iphone("Chrome");
    cap.setCapability(IOSCapabilities.SIMULATOR, false);

    RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
    driver.findElement(By.xpath("//UIAWindow"));
    driver.quit();

    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
    driver.findElement(By.xpath("//UIAWindow"));
    driver.quit(); */

    }

    /**
     * BASIC:
     * 1) Start a session -->  for simulator
     * FIND ELEMENT(S):
     * 2) Find elements --> get all the UIATableCell elements on screen
     * 5) Find element --> @internationalMountainDemoTest_1:LowPriority
     * 6) Find element --> get the element that contains a string
     * 7) Fine element --> get the element that matches a string
     * UTILITY:
     * 3) Take screen shot --> customized file path
     * CONTENT:
     * 4) webElement.getAttribute("name")
     */
    @Test
    public void internationalMountainDemoTest() throws MalformedURLException, InterruptedException {
        //1) Start a session -->  for simulator
        DesiredCapabilities cap = IOSCapabilities.iphone("InternationalMountains", "1.1");
        cap.setCapability(IOSCapabilities.LANGUAGE, "en");
        cap.setCapability("simulator", "true");
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
        driver.getPageSource();

        try {
            //2) Find elements --> get all the UIATableCell elements on screen
//            List<WebElement> cells = driver.findElements(By.className("UIATableCell"));
//            System.out.println("There are " + cells.size() + " UIATableCell elements on screen.");
//            cells.get(0).click();

            //7) Fine element --> get the element that matches a string
            By b = By.linkText("name=Mountain 1");
            WebElement element = driver.findElement(b);
            element.click();

            //3) Take screen shot
            TakesScreenshot screen = (TakesScreenshot) new Augmenter().augment(driver);
            File ss = new File("/Users/xsi/Desktop/ios_driver_06/screen_shot/international_mountain/screenshot-mountain.png");
            screen.getScreenshotAs(OutputType.FILE).renameTo(ss);
            System.out.println("Screenshot Taken :" + ss.getAbsolutePath());

            //4) webElement.getAttribute("name")
            //5) Find element --> @internationalMountainDemoTest_1:LowPriority
            By contentFree = By.xpath("//UIAStaticText[matches(@name,l10n('sentenceFormat'))]");
            WebElement text = driver.findElement(contentFree);// <-- internationalMountainDemoTest_1
            System.out.println(text.getAttribute("name"));

            //6) Find element --> get the element that contains a string
            By selector = By.xpath("//UIAStaticText[contains(@name,'climbed')]");
            WebElement text1 = driver.findElement(selector);
            System.out.println(text1.getAttribute("name"));

            Thread.sleep(30000);
        } finally {
//            driver.quit();
        }
    }

    /**
     * BASIC:
     * 1) Click a button
     * 2) Check if the button is displayed
     */
    @Test
    public void xoomDemoTest() throws MalformedURLException {
        DesiredCapabilities cap = IOSCapabilities.iphone("XoomApp");
        cap.setCapability("simulator", true);
        cap.setCapability("translation", false);
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://192.168.3.19:4444/wd/hub"), cap);

        try {
//            String pageSource=driver.getPageSource();
            List<WebElement> buttons = driver.findElements(By.className("UIAButton"));
            WebElement button3 = buttons.get(2);
            //2)Check if the button is displayed
            System.out.println("Button3 is displayed: " + button3.isDisplayed());
            //1) Click a button
            button3.click();
        } finally {
            driver.quit();
        }
    }

    /**
     * FIND ELEMENT(S):
     * 1) Find element --> by accessibility id
     * 2) Fine element --> by accessibility label
     * 3) Find element --> by showing text
     * 4) Find element --> by localization string
     */
    @Test
    public void xoomFindElementsTest() throws MalformedURLException, InterruptedException {
        DesiredCapabilities cap = IOSCapabilities.iphone("XoomApp");
        cap.setCapability("simulator", true);
        cap.setCapability("translation", false);
        cap.setCapability(IOSCapabilities.LANGUAGE, "en");
        RemoteWebDriver driver =  new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
//        driver.getPageSource();

        try {



            //1) Find element --> by accessibility id
//            WebElement loginButton = driver.findElement(By.linkText("name=AILoginButton"));
            //4) Find element --> by localization string
//            By by1 = By.xpath("//UIAButton[matches(@name,l10n('Welcome.logInButtonTitle'))]");
//            WebElement loginButton = driver.findElement(by1);
            WebElement loginButton=driver.findElement(By.id("Log In"));




//            String sessionID= String.valueOf(driver.getSessionId());
//            String rawJS="var root = UIAutomation.cache.get('1');"
//                    + "var result = root.tree(false);"
//                    + "UIAutomation.createJSONResponse(':sessionId',0,result);";
//            rawJS=rawJS.replace(":sessionId", sessionID);
//
//            Object result=executeScript(driver, rawJS);




            System.out.println("Login button is visible: " + loginButton.isDisplayed());
            loginButton.click();
            Thread.sleep(1000);

            //2) Fine element --> by accessibility label
//            WebElement emailAddressInput = driver.findElement(By.linkText("name=AL First"));
//            WebElement emailAddressInput = driver.findElement(By.linkText("label=AL First"));

            //4) Find element --> by localization string
            By by = By.xpath("//UIATextField[matches(@value,l10n('SignIn.loginInputFieldLabel'))]");
            WebElement emailAddressInput = driver.findElement(by);
            emailAddressInput.sendKeys("tom.sawyer@test.com");

            //1) Find element --> by accessibility id
//            WebElement passwordInput = driver.findElement(By.linkText("name=AI Second"));
            By by2 = By.xpath("//UIASecureTextField[matches(@value,l10n('SignIn.passwordInputFieldLabel'))]");
            WebElement passwordInput = driver.findElement(by2);
            passwordInput.sendKeys("11111111");

            //3) Find element --> by showing text
            WebElement userLoginButton = driver.findElement(By.id("Log In"));
            userLoginButton.click();

//It might work.
//            By selector = By.xpath("//UIAStaticText[contains(@name,'Log In')]");
//            WebElement userLoginButton=driver.findElement(selector);
//            userLoginButton.click();

            Thread.sleep(2000);
        } finally {
            driver.quit();
        }
    }


    /**
     * Open a browser on mobile for downloading app for testing.
     */
    @Test
    public void openABrowserAndGoingToAnAddressTest() throws MalformedURLException {
        DesiredCapabilities safari = IOSCapabilities.iphone("Safari");
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), safari);
        try {
            driver.get("http://www.xoom.com");
            System.out.println(driver.getTitle());
        } finally {
            driver.quit();
        }
    }

    /**
     * Possible usage for deploying XoomApp to a real device
     */
    @Test
    public void deployXoomAppTest() throws MalformedURLException {
        DesiredCapabilities safari = IOSCapabilities.iphone("Safari");
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:5555/wd/hub"), safari);

        try {
            driver.get("http://mbl102.corp.xoom.com:8080/job/app-ios-hub-qa/lastSuccessfulBuild/artifact/output/Publish/index.html");
        } finally {
            driver.quit();
        }
    }




    private static Object executeScript(WebDriver driver, String script, Object... args) {
        Response response= (Response) ((JavascriptExecutor) driver).executeScript(script, args);

        return null;
    }
}
