package org.uiautomation.ios.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Pages;
import org.openqa.selenium.environment.webserver.AppServer;
import org.openqa.selenium.environment.webserver.WebbitAppServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.uiautomation.ios.IOSCapabilities;
import org.uiautomation.ios.client.uiamodels.impl.RemoteIOSDriver;
import org.uiautomation.ios.server.IOSServer;
import org.uiautomation.ios.server.IOSServerConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.TestWaiter.waitFor;
import static org.openqa.selenium.WaitingConditions.pageTitleToBe;
import static org.testng.Assert.assertEquals;

public class MultiSafari {

  private IOSServer server;
  private static String[] args = {"-port", "4444", "-host", "localhost", "-beta"};
  private static IOSServerConfiguration config = IOSServerConfiguration.create(args);
  private String url = "http://" + config.getHost() + ":" + config.getPort() + "/wd/hub";
  protected Pages pages;
  protected AppServer appServer;

  @BeforeClass
  public void setup() throws Throwable {
    //startIOSServer();
    startTestServer();

    // safari.setLanguage("fr");


  }

  public static void main(String[] args) throws Exception {
    new MultiSafari().startIOSServer();
  }

  public void startIOSServer() throws Exception {
    server = new IOSServer(config);
    server.start();
  }

  public void startTestServer() {
    appServer = new WebbitAppServer();
    appServer.start();

    pages = new Pages(appServer);

  }

  public void stopIOSServer() throws Exception {
    server.stop();
  }


  @AfterClass
  public void tearDown() throws Exception {

    try {
      stopIOSServer();
      appServer.stop();
    } catch (Exception e) {
      //System.err.println("cannot quit properly :" + e.getMessage());
    }
  }

  @Test(invocationCount = 4,threadPoolSize = 4)
  public void navigateAndClick() throws MalformedURLException {
    IOSCapabilities safari = IOSCapabilities.iphone("Safari");
    safari.setCapability(IOSCapabilities.SIMULATOR,false);
    RemoteIOSDriver driver =   new RemoteIOSDriver(new URL(url), safari);

    for (int i=0;i<5;i++){
      driver.get(pages.formPage);
      driver.findElement(By.id("submitButton")).click();
      waitFor(pageTitleToBe(driver, "We Arrive Here"));
      assertEquals(driver.getTitle(), ("We Arrive Here"));
    }
    driver.quit();
  }
}
