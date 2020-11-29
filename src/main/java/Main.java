import java.io.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.lang.reflect.Array;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeOptions;
import java.util.*;
class Task extends Thread {
    private int connI;
    private  ArrayList<String> proxies;
    public Task(int connI, ArrayList<String> proxies){
        this.connI = connI;
        this.proxies = proxies;
    }
    public void run(){ 
        try {
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running");
            String regLink = "https://signup.live.com/signup?wa=wsignin1.0&rpsnv=13&rver=7.3.6963.0&wp=MBI_SSL&wreply=https:%2f%2faccount.xbox.com%2fen-us%2faccountcreation%3freturnUrl%3dhttps%253a%252f%252fwww.xbox.com%253a443%252fen-US%252flive%26ru%3dhttps%253a%252f%252fwww.xbox.com%252fen-US%252flive%26rtc%3d1%26csrf%3d9JOe-TgyeYiNjsg0Lhx27_JugVD9U0mJ74e5A5nylKft5l50tZ8h_x4L0kp-nz3gCtSq3hXLw0Vjt5SP9klbhbHzvhg1&id=292543&aadredir=1&contextid=451899910FBB09FA&bk=1606191499&uiflavor=web&lic=1&mkt=EN-US&lc=1033&uaid=f731cb1cf61142cba1e89499ed17b696";
            Proxy proxy = new Proxy();
            proxy.setSslProxy(proxies.get(connI));
            proxy.setAutodetect(false);
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("proxy", proxy);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            WebDriver driver = new ChromeDriver(capabilities);
            try {
                driver.get(regLink);
                System.out.println("Successful Visit!");
                BufferedReader br = new BufferedReader(new FileReader("WordList.txt"));

                String emailVal = "jewishxbl" + String.valueOf(( int )( Math.random() *1000 +5 )) + "@outlook.com";
                String first = "get";
                String last = "ran";
                WebElement email = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("MemberName")));
                email.sendKeys(emailVal);
                WebElement submit = driver.findElement(By.id("iSignupAction"));
                submit.click();
                WebElement pass = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("PasswordInput")));
                pass.sendKeys("T5?YhQQ&*GFFQ$/");
                WebElement submit1 = driver.findElement(By.id("iSignupAction"));
                submit1.click();
                WebElement firstField = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("FirstName")));
                firstField.sendKeys(first);
                WebElement lastField = driver.findElement(By.id("LastName"));
                lastField.sendKeys(last);
                WebElement submit2 = driver.findElement(By.id("iSignupAction"));
                submit2.click();
                WebElement month = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("BirthMonth")));
                month.click();
                month.sendKeys("se");
                month.sendKeys(Keys.ENTER);
                WebElement day = driver.findElement(By.id("BirthDay"));
                day.sendKeys(String.valueOf(( int )( Math.random() *20 +1 )));
                day.sendKeys(Keys.ENTER);
                WebElement  year = driver.findElement(By.id("BirthYear"));
                year.sendKeys("1999");
                year.sendKeys(Keys.ENTER);
                WebElement submit3 = driver.findElement(By.id("iSignupAction"));
                submit3.click();

            } catch (Exception e) {
                System.out.println("Dead proxy :(");
            }
        } catch (Exception e){
            System.out.println ("Exception is caught");
        }
    }
}
public class Main {
    public static void main(String[] args)throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        ArrayList<String>proxies = new ArrayList<String>();
        ArrayList<String>words = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new FileReader("WordList.txt"));
        String l;
        while((l = in.readLine())!=null){
            words.add(l);
        }
        int connI =0;
        BufferedReader br = new BufferedReader(new FileReader("Proxies.txt"));
        String line;
        while((line = br.readLine())!=null){
            proxies.add(line);
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Accounts.txt")));
        ArrayList<Thread> sessions= new ArrayList<Thread>();
        for(String next : proxies){
            Task object = new Task(connI,proxies);
            sessions.add(object);
            connI++;
        }
        for(Thread next : sessions){
            next.start();
        }
    }
}
