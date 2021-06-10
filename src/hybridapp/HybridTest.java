package hybridapp;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import hybridapp.Capability;

public class HybridTest extends Capability {
	
	AndroidDriver<AndroidElement> driver;
	
	@BeforeTest
	public void setup() throws MalformedURLException
	{
		driver = capabilities();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test(enabled=false)
	public void testcase1() {
		System.out.println("New app is working in the emulator");
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Veronika");
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		driver.findElement(By.id("android:id/text1")).click();
		
		//scroll to select country
//		  driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"));").click();

		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"));").click();
		
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
	}
	
	//Negative scenario
	@Test(enabled=false)
	public void testcase2() {
		//driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Veronika");
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		driver.findElement(By.id("android:id/text1")).click();
		
		//scroll to select country

		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Angola\"));").click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		
		//catpure error message
		String errmsg = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		String actmsg = "Please enter your name";
		Assert.assertEquals(actmsg, errmsg);
		
		
	}
	
	//
	@Test(enabled=false)
	public void testcase3() throws InterruptedException {
		System.out.println("New app is working in the emulator");
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Veronika");
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		driver.findElement(By.id("android:id/text1")).click();
		
		//scroll to select country

		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Angola\"));").click();
		
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		
		//click on Add to Cart
		//Twice zero is because when Add to Cart is clicked for the first time, the index of the seconnd button is getting resetted to 0
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		
		Thread.sleep(4000);
		
		String amount1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(0).getText();

		String amount2 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(1).getText();

		//remove $ symbol
		amount1 = amount1.substring(1);
		
		//convert string to double
		Double amount1value = Double.parseDouble(amount1);
		
		//print the value
		System.out.println(amount1value);
		
		amount2 = amount2.substring(1);
		Double amount2value = Double.parseDouble(amount2);
        System.out.println(amount2value);
		
        //adding both values
        Double total = amount1value + amount2value;
        
        System.out.println(total);
        
		String finalamount = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
		finalamount = finalamount.substring(1);
		
		Double finalamount1 = Double.parseDouble(finalamount);
		
		
		Assert.assertEquals(finalamount1, total);

	}
	
	@Test(enabled=true)
	public void testcase4() throws InterruptedException {
		System.out.println("New app is working in the emulator");
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Veronika");
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		driver.findElement(By.id("android:id/text1")).click();
		
		//scroll to select country

		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Angola\"));").click();
		
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

		//scrolling till a particular product
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(textMatches(\"Converse All Star\"))");
        //after scrolling and finding my product, i want to check how many product are visible in the screen
		int count = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
		System.out.println(count);
		for (int i=0;i<count;i++)
		{
			String Name = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
			if(Name.equals("Converse All Star"))
			{
				driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
				break;
			}
		}
		
		//click on view cart
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		
		driver.findElement(By.xpath("//*[@text='Send me e-mails on discounts related to selected products in future']")).click();
		
		  TouchAction t = new TouchAction(driver);
		  WebElement TC = driver.findElement(By.xpath("//*[@text='Please read our terms of conditions']"));
		  t.longPress(longPressOptions().withElement(element(TC)).withDuration(ofSeconds(3))).release().perform();
		  
		  driver.findElement(By.id("android:id/button1")).click();
		  
		  driver.findElement(By.xpath("//*[@text='Visit to the website to complete purchase']")).click();

		  Thread.sleep(9000);
		  
		  //what is the context the web app is in
		  Set<String> contextNames = driver.getContextHandles();
		  for (String contextName : contextNames) {
		      System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		  }
		  
		  driver.context("WEBVIEW_com.androidsample.generalstore");
		  driver.findElement(By.xpath("//*[@name='q']")).sendKeys("IBM");
			//what this line will do
			driver.findElement(By.xpath("//*[@name='q']")).sendKeys(Keys.ENTER);
			
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context("NATIVE_APP");
	
	}
	
	
}
