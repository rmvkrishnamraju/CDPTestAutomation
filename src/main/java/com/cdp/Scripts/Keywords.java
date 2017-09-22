package main.java.com.cdp.Scripts;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Keywords {
	public  WebDriver driver1;
	public Actions actions;
	public WebDriver driver2;
	public String Result1="";
	public String Result2="";
	public String Result3="";
	public String Result4="";
	
	//*****Launch Web Browser**********
	//METHOD WILL LAUNCH CHROME/IE/FF BROWSER 
		public WebDriver LaunchWebBrowser(String browser,String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
				//APP_LOGS.debug("Click on Button");
			try{
				MainThread.APP_LOGS.debug("Launching"+browser+"Browser");
				if(browser.equals("IE")){
					System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"/External Library Files/IEDriverServerWin32v253/IEDriverServer.exe");
					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
					driver1 = new InternetExplorerDriver();
					return driver1;
				}else if(browser.equals("FF")){
					//System.setProperty("webdriver.gecko.driver", "D:/MohanBaseFramework/VNSAutomationFramewok/External Library Files/geckodriver-v0.15.0-win64/geckodriver.exe");
					//Now you can Initialize marionette driver to launch firefox
					DesiredCapabilities capabilities = DesiredCapabilities.firefox();
					//capabilities.setCapability("marionette", true);
					//driver1= new MarionetteDriver(capabilities);
					capabilities.setBrowserName("firefox");
					capabilities.setCapability("binary", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
					capabilities.setPlatform(Platform.ANY);					
					driver1=new FirefoxDriver(capabilities);
					//driver1=new FirefoxDriver();
				}else if(browser.equals("Chrome")){
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/External Library Files/chromedriver_win32/chromedriver.exe");
					driver1 =new ChromeDriver();
					return driver1;
				}
				}catch(Exception e){
					//ScreenShot(target, data, Correct_Data, Createuser, browser, ExpectedErrorMsg, currentTestName, currentTSID, currentDSID);
					e.printStackTrace();
					return driver1;
					}
			//ScreenShot(target, data, Correct_Data, Createuser, browser, ExpectedErrorMsg, currentTestName, currentTSID, currentDSID);
			return driver1;
		}
		
		//*****OpenWebApp Method*********
		//METHOD WILL OPEN VNS WEB URL
		public String OpenWebApp(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("SUCCESSFULLY OPENED CUREATR WEB APPLICATION");
			try{
					MainThread.APP_LOGS.debug("Application Opening");
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
					driver.get(MainThread.CONFIG.getProperty(target));
					return "PASS";	
				}catch (Exception e){
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
					return "FAIL";
				}
		}
		//*****Maximize Method*********
		//METHOD WILL Maximize BROWSER
		public String Maximize(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			try{
				MainThread.APP_LOGS.debug("Maximizing Window");
				driver.manage().window().maximize();
				return "PASS";
			}catch(Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		   		e.printStackTrace();
				return "FAIL";
			
			}
		}		
		//*****verifyTitle Method*********
		//METHOD WILL BROWSER verifyTitle 
		public String verifyTitle(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			try{
				MainThread.APP_LOGS.debug("Verification of App Title");
			    String ActText=driver.getTitle();
			    MainThread.APP_LOGS.debug("Verifying Title");
			    if(data.equals(ActText)){
			     return "PASS";
			    }else{
			    	ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		return "FAIL"; 
			    }
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		   		e.printStackTrace();
			     return "FAIL";
			   }
		}
		//*****verifyText Method*********
		//METHOD WILL verifyText For Links/Buttons/Label
		public String verifyText(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			try{
				MainThread.APP_LOGS.debug("Verifying Text");
				System.out.println("Exptext is="+data) ;
			    String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getText();
			    System.out.println("Acttext is="+ActText) ;
			    //ActText = ActText.encode('ascii', 'ignore').decode('ascii')
			    if(data.equals(ActText)){
			    	return "PASS";
			    }else{
			    	ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		return "FAIL"; 
			    }
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				MainThread.APP_LOGS.debug(e);
				//MainThread.APP_LOGS.debug(e.printStackTrace());
				e.printStackTrace();
			    return "FAIL";
			   }
		}
		//*****verifyText Method*********
		//METHOD WILL verifyText For Links/Buttons/Label
		public String verifyTextCSS(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			try{
				MainThread.APP_LOGS.debug("Verifying Text");
				System.out.println("Exptext is="+data) ;
			    String ActText=driver.findElement(By.cssSelector(MainThread.CONFIG.getProperty(target))).getText();
			    System.out.println("Acttext is="+ActText) ;
			    //ActText = ActText.encode('ascii', 'ignore').decode('ascii')
			    if(data.equals(ActText)){
			    	return "PASS";
			    }else{
			    	ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		return "FAIL"; 
			    }
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				MainThread.APP_LOGS.debug(e);
				//MainThread.APP_LOGS.debug(e.printStackTrace());
				e.printStackTrace();
			    return "FAIL";
			   }
		}	
		public String verifyWaterMarkAvailability(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			 try{
				 String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getAttribute("placeholder");
		    	 if(data.equals(ActText)){
		    		 return "PASS";
		    	 }else{
		    		 ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				   	 return "FAIL"; 
		    	 }
		     }catch (Exception e){
		    	 ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   	 e.printStackTrace();
		    	  return "FAIL";
		     }
		      
		}
		
		public String verifyWaterMarkUnAvailability(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Get Title OF Opened Web Application");
			   try{
			    String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getAttribute("value");
			    if(data.equals(ActText)){
			     return "PASS";
			    }else{
			     ScreenShot(driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			     return "FAIL"; 
			    }
			    }catch (Exception e){
			     ScreenShot(driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			     e.printStackTrace();
			     return "FAIL";
			    }
			   }
		
		public String VendorSearch(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			 try{
				 String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getAttribute("src");
		    	 System.out.println("******************Act="+ActText);
		    	 System.out.println("******************Exp="+data);
		    	 if(ActText.contains(data)){
		    		 return "PASS";
		    	 }else{
		    		 ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				   	 return "FAIL"; 
		    	 }
		     }catch (Exception e){
		    	 ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   	 e.printStackTrace();
		    	  return "FAIL";
		     }     
		}
		
		public String Type(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Input The Value In Test Box");
			   try{
				   MainThread.APP_LOGS.debug("Input the values in Test Box");
				   driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).sendKeys(data);
				   return "PASS";
			   	}catch (Exception e){
			   		ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL"; 
			   	}
		}
		
		public String ActionType(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName){
			try{
				if (actions==null){
					System.out.println("Action type executed");
					actions = new Actions(driver);
				}
				actions.moveToElement(driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))));
				actions.click();
				actions.sendKeys(data);
				actions.build().perform();
				return "PASS";
			
			}catch (Exception e){
				e.printStackTrace();
				return "FAIL";
				}
		}
		
		public String DragAndDrop(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName){
			try{
				String tar[]=target.split("\\&");
				WebElement source = driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[0]))); 
				WebElement desti = driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1])));
			    if (actions==null){
					System.out.println("Action type executed");
					actions = new Actions(driver);
				}
			    actions.dragAndDrop(source, desti).build().perform();
			    return "PASS";
			
			}catch (Exception e){
				e.printStackTrace();
				return "FAIL";
				}
		}
		
		public String ActionClick(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName){
			try{
				if (actions==null){
					System.out.println("Action click executed");
					actions = new Actions(driver);
				}
				actions.moveToElement(driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))));
				actions.click();
				actions.build().perform();
				return "PASS";
			
			}catch (Exception e){
				e.printStackTrace();
				return "FAIL";
				}
		}
				
		public String MouseHover(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName){
			try{
				if (actions==null){
					System.out.println("Move to Web Element");
					actions = new Actions(driver);
				}
				WebElement element = driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)));
				actions.moveToElement(element);
				actions.perform();
				return "PASS";
			
			}catch (Exception e){
				e.printStackTrace();
				return "FAIL";
				}
		}
		
		public String Click(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Input The Value In Test Box");
			   try{
				   MainThread.APP_LOGS.debug("Click on button");
				   driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).click();
				  return "PASS";
			   	}catch (Exception e){
			   		ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL"; 
			   	}
		}		
		public String verifySignIn(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Input The Value In Test Box");
			   try{
				   if(Correct_Data.equals("Y")){
					  driver.findElement(By.xpath(MainThread.CONFIG.getProperty("atlantis_Dashboardtext"))).isDisplayed();
					  driver.findElement(By.xpath(MainThread.CONFIG.getProperty("user_Profilename"))).isDisplayed();
					  MainThread.APP_LOGS.debug("Login Success and user navigated to Dashboard screen");
					  return "PASS"; 
				   }
				   else{
					   driver.findElement(By.xpath(MainThread.CONFIG.getProperty("username"))).isDisplayed();
					   driver.findElement(By.xpath(MainThread.CONFIG.getProperty("password"))).isDisplayed();
					   MainThread.APP_LOGS.debug("User retained in Login screen with invalid data");
					   return "PASS";	   
				   }
				   
			   	}catch (Exception e){
			   		ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL"; 
			   	}
		}	
		
		public String Clickhidden(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		      //APP_LOGS.debug("Input The Value In Test Box");
		   for (int i=1;i<=5;i++){   
		   try{
		    WebElement element=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)));
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    js.executeScript("arguments[0].click();", element);
		       MainThread.APP_LOGS.debug("Click on button");
		       
		       
		      return "PASS";
		       }catch (Exception e){
		        if(i<5){
		        Thread.sleep(1000);
		        continue;
		        }
		        ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		        e.printStackTrace();
		        return "FAIL"; 
		       }    } return "FAIL";
		  }
		
		public String verifyErrMsg(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Input The Value In Test Box");
			   try{
				   if(data.equals("")){
					  return "PASS";
				   }
				   else{
					   String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getText();
					   System.out.println("ActText"+ActText);
					   System.out.println("data"+data);
					   System.out.println(data.equals(ActText));
					   if(data.equals(ActText)){
						   return "PASS";
					   }
					   else{
						   ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
						   return "FAIL";
					   }	   
				   }
				   
			   	}catch (Exception e){
			   		ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL"; 
			   	}
		}	
		
		public String Browserwait(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Input The Value In Test Box");
			   try{
				   Thread.sleep(Long.valueOf(data));
				   return "PASS";
			   }catch (Exception e){
			   		ScreenShot(driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL"; 
			   	}
		}
		
		public String isVisible(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Input The Value In Test Box");
			   try{
				   	    WebElement element =driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)));
				   	    if(element.isDisplayed()){
				   	    	return "PASS";
				   	    }
				   	    else{
				   	    	ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					   		return "FAIL";
				   	    }
			   }catch (Exception e){
			   		ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL"; 
			   	}
		}	
		
		public String verifyTextContains(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			  //APP_LOGS.debug("Get Title OF Opened Web Application");
			try{				
				String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getText();
				//TSID
				System.out.println("******************************************target=="+target);
				System.out.println("******************************************ActText=="+ActText);
				System.out.println("******************************************ExpText=="+data);
				if(ActText.trim().contains(data)){
					return "PASS";
				}else{
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   			return "FAIL";	
				}
				}catch (Exception e){
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
					return "FAIL";
				}
				}
		
		public String verifyTextBoxValue(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("Get Title OF Opened Web Application");
			try{
				String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getAttribute("value");
				if(data.equals(ActText)){
					return "PASS";
				}else{
					ScreenShot(driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					return "FAIL";	
				}
				}catch (Exception e){
					ScreenShot(driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					e.printStackTrace();
					return "FAIL";
				}
			}	
		
		public String verifyLogout(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Input The Value In Test Box");
			   try{
				   if(Correct_Data.equals("Y")){
					  driver.findElement(By.xpath(MainThread.CONFIG.getProperty("login_header_text"))).isDisplayed();
					  driver.findElement(By.xpath(MainThread.CONFIG.getProperty("forgot_pwd_link"))).isDisplayed();
					  MainThread.APP_LOGS.debug("Logout Success and navigated to Login screen");
					  return "PASS"; 
				   }
				   else{
					   driver.findElement(By.xpath(MainThread.CONFIG.getProperty("home_wc_text"))).isDisplayed();
					   driver.findElement(By.xpath(MainThread.CONFIG.getProperty("home_mychoice_img"))).isDisplayed();
					   MainThread.APP_LOGS.debug("User retained in Dashboard screen");
					   return "PASS";	   
				   }
				   
			   	}catch (Exception e){
			   		ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL"; 
			   	}
		}	
		
		public String uploadPhoto(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			  //APP_LOGS.debug("Get Title OF Opened Web Application");
			try{	
				if(driver.equals("FF")){
				//String path = System.getProperty("User.dir")+"\\src\\uploadFF.exe";
				Runtime.getRuntime().exec("D:\\MyChoiceAutomationFramework\\MyChoiceAutomationFramework\\src\\uploadFF.exe");			
				return "PASS";
				}
				else{
					//String path = System.getProperty("User.dir")+"\\src\\uploadChrome.exe";
					Runtime.getRuntime().exec("D:\\MyChoiceAutomationFramework\\MyChoiceAutomationFramework\\src\\uploadChrome.exe");			
					return "PASS";
				}
			}catch (Exception e){
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
					return "FAIL";
				}
			}	
		
		public String ClearText(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("Input The Value In Test Box");
			try{
				driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).clear();;
				return "PASS";
			}catch (Exception e){
				ScreenShot(driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
			}
		}
		
		public String Backspace(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("Input The Value In Test Box");
			try{
				driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).sendKeys(Keys.BACK_SPACE);;
				return "PASS";
			}catch (Exception e){
				ScreenShot(driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
			}
		}
		
		public String randomEmail(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		     //APP_LOGS.debug("Get Title OF Opened Web Application");
		   try{    
		    Random ran = new Random();
		    int Exten = ran.nextInt(9999) + 10000;
		    String email="pavani"+Exten+"@mailinator.com";
		    driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).sendKeys(email);
		    //#Xlsx_Reader sheet=MainThread.currentTestSuiteXLS;
		    //sheet.setCellData(sheetName, colName, rowNum, Correct_Data)
		    //sheet.setCellInt(sheetName, colName, rowNum, number)
		    //#sheet.setCellData(TCID, "EMAIL", currentTestDataSetID, email);
		    
		    currentTestSuiteXLS.setCellData(currentTestCaseName, "EMAIL", currentTestDataSetID, email);
		    return "PASS";
		    }catch (Exception e){
		     ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		        e.printStackTrace();
		     return "FAIL";
		    }
		  }
		
		public String randomName_Withoutnumbers(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName){
		    //APP_LOGS.debug("Get Title OF Opened Web Application");
		  try{    
		   //Random ran = new Random();
		   System.out.println("hai");
		   String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			Random rnd = new Random();
			String name;
			String names;
			StringBuilder sb = new StringBuilder( AB.length() );
			for( int i = 0; i < 3; i++ ) 
			{
				sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
				System.out.println(sb.toString());
		        name=sb.toString();
		       names=data+name;
		       driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).sendKeys(names);
				    //MainThread.currentTestSuiteXLS.setCellData(MainThread.currentTestCaseName, "NAME", currentTestDataSetID, names);
				}
			
			return "PASS";
		   }catch (Exception e){
		  //  ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user);
		       e.printStackTrace();
		    return "FAIL";
		   }
		  }
		
		 public String randomName(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		     //APP_LOGS.debug("Get Title OF Opened Web Application");
		   try{    
		    Random ran = new Random();
		    int Exten = ran.nextInt(9999) + 10000;
		    String name=data+Exten;
		    driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).sendKeys(name);
		    currentTestSuiteXLS.setCellData(currentTestCaseName, "NAME", currentTestDataSetID, name);
		    return "PASS";
		    }catch (Exception e){
		     ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		        e.printStackTrace();
		     return "FAIL";
		    }
		   }
	
		 public String charbychar(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		     //APP_LOGS.debug("Get Title OF Opened Web Application");
		   try{    
		    int i;
		    String name="java";
		    System.out.println("hi");
		    for(i=0;i<name.length();i++){
		    	System.out.println("for");
		    driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).sendKeys(Character.toString(name.charAt(i)));
		    Thread.sleep(1000);
		    }
		    return "PASS";
		    }catch (Exception e){
		     ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		        e.printStackTrace();
		     return "FAIL";
		    }
		   }
		 
		 public String SelectDropdownValue(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			 try{
				 if(data.equals("")){
					 return "PASS";
				 }
				 List<WebElement> elements = driver.findElements(By.xpath(MainThread.CONFIG.getProperty(target)));
				 for(int i=0;i<elements.size();i++){
					 String elementText=elements.get(i).getText();
					 System.out.println("ActDropDown="+data);
					 System.out.println("elementText="+elementText);
					 System.out.println("DSID="+DSID);
					 if(elementText.equals(data)){
						 elements.get(i).click();
						 return "PASS";
					 }else if(i==elements.size()){
						 ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
						 return "FAIL"; 
					 }else{
						 continue;
					 }
				 }
				 ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				 return "FAIL";
			 }catch (Exception e){
				 ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				 e.printStackTrace();
				 return "FAIL";
		 		}
		 	}
		 
		 public String switchToWindow(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		     //APP_LOGS.debug("Get Title OF Opened Web Application");
		   try{ 
		    Set <String> handles =driver.getWindowHandles();
		    Iterator<String> it = handles.iterator();
		    //iterate through your windows
		    while (it.hasNext()){
		    String parent = it.next();
		    if(data.equals("Parent Window")){
		     driver.switchTo().window(parent);
		    }else{
		    String newwin = it.next();
		    driver.switchTo().window(newwin);
		    }
		   }
		    return "PASS";
		   }catch(Exception e){
		     ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		        e.printStackTrace();
		     return "FAIL";
		    }
		   }
		 
		 public String validateListContent(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		     //APP_LOGS.debug("Get Title OF Opened Web Application");
		   try{ 
		    String tar[]=target.split("\\&");
		    int numPages=driver.findElements(By.xpath(MainThread.CONFIG.getProperty(tar[0]))).size();
		    System.out.println("NumPages"+numPages);
		    int colNum=driver.findElements(By.xpath(MainThread.CONFIG.getProperty(tar[2]))).size();
		    System.out.println("colNum"+colNum);
		    //int rowNum=driver.findElements(By.xpath(MainThread.CONFIG.getProperty(tar[1]))).size();
		    int count=0;
		    //driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[0]))).isDisplayed();
		    if(colNum>0){
		    for(int pg=3;pg<=numPages-2;pg++){
		     int rowNum=driver.findElements(By.xpath(MainThread.CONFIG.getProperty(tar[1]))).size();
		     //System.out.println("Number of rows in page"+pg+"is--->"+rowNum);
		     for(int rc=1;rc<=rowNum;rc++){
		      for(int cc=1;cc<=colNum;cc++){
		       //String xpath=MainThread.CONFIG.getProperty(tar[1])+"["+rc+"]"+"td["+cc+"]";
		       //table[@class='table table-striped b-t b-light listPanelsRecruiters']/tbody/tr
		       String elementText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1])+"["+rc+"]/td["+cc+"]")).getText();
		       //System.out.println("Page number"+pg+"element in row num--->"+rc+"column num--->"+cc+"is--->"+elementText);
		      if(elementText.contains(data)){
		        count=count+1;
		       }
		      }
		      //System.out.println("count for row num="+rc+"is"+count);
		      if(count==0){
		       return "FAIL";
		      }
		     }
		     driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[0])+"["+(pg+1)+"]/a")).click();
		    }
		    return "PASS";
		   }else{
		    //System.out.println("No Matching results found with text--->"+data);
		    return "PASS"; 
		    
		   }
		   
		   }catch(Exception e){
		     ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		     MainThread.APP_LOGS.debug(e);
		     return "FAIL";
		    }
		   }
		 
		//METHOD WILL CAPTURE SCREEN SHOT WHEN TEST STEP FAIL
		 public String ScreenShot(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			   //APP_LOGS.debug("Click on Button");
			   try{
			    File file=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			    String filepath = (SubFolderPath+"/"+"ScreenShots"+"/");
			    String filetype = ".jpg";
			    //Stringf filename = currentTestCaseName;
			    String filenamepath = filepath+TCID+"-"+TSID+"-"+DSID+filetype;
			    FileUtils.copyFile(file, new File(filenamepath));
			    return "PASS";
			   }catch(Exception e){
			    //MainThread.APP_LOGS.debug(e);
			    return "FAIL";
			   }
			  }
		//*****CloseWebApp Method*********
		//METHOD WILL CLOSE WEB APPLICATION
		public String CloseWebApp(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		//APP_LOGS.debug("SUCCESSFULLY CLOSED WEB APPLICATION");
		try{
			driver.close();
			driver.quit();
			System.out.println("driver=="+driver);
	 		return "PASS";	
		}catch (Exception e){
			e.printStackTrace();
			return "FAIL";
			}
		}
		
		public String resetApp(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName){
			try{
				driver.manage().deleteAllCookies();
				Thread.sleep(3000);
				driver.navigate().to(MainThread.CONFIG.getProperty(target));
				driver.navigate().refresh();
				return "PASS";	
			}catch (Exception e){
				e.printStackTrace();
				return "FAIL";
				}
		}
		
		public String ConnectDB(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName){
			try{
				
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://172.16.3.1:3306","root","admin123");  
				//here sonoo is database name, root is username and password  
				java.sql.Statement stmt=con.createStatement();  
				ResultSet rs=stmt.executeQuery("select * from tab");  
				while(rs.next())  
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
				con.close();  
				return "PASS";
			
			}catch (Exception e){
				e.printStackTrace();
				return "FAIL";
				}
		}
		

		//*****verifySignIn Method**********	
		//METHOD WILL VERIFY SING-IN SECUSS OR NOT
		public String validateCreatePolicy(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		//APP_LOGS.debug("Click on Button");
		try{
			if(data.equals("Y")){
				boolean PolicyManagementHdr = driver.findElement(By.xpath(MainThread.CONFIG.getProperty("PolicyManagementHdr"))).isDisplayed();
				boolean SelectedPolicy = driver.findElement(By.xpath(MainThread.CONFIG.getProperty("SelectedPolicy"))).isDisplayed();
				if (PolicyManagementHdr==true && SelectedPolicy==true){
					return "PASS";
				}else{
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					return "FAIL";
				}
			}else{
				boolean CreatePolicy = driver.findElement(By.xpath(MainThread.CONFIG.getProperty("CreatePolicySaveBtn"))).isEnabled();
				System.out.println("CreatePolicy="+CreatePolicy);
				if (CreatePolicy==false){
					return "PASS";
				}else{
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					return "FAIL";
				}
			}
		}catch(Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
				}
		}

		//*****verifyErrorMsg Method**********
		//METHOD WILL GET THE TARGETED TEXT FROM WEB APP & VALIDATE WITH EXPECTED TEXT
		public String verifyErrorMsg(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		//APP_LOGS.debug("Get Title OF Opened Web Application");
		try{
			System.out.println(data);
			System.out.println(data != "");
			if(data != ""){
				String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getText();
				System.out.println("******************************************ActText=="+ActText);
				System.out.println("******************************************ExpText data =="+data);
				if(ActText.equals(data)){
					return "PASS";	
				}else{
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					return "FAIL";
				}
			}else{
				return "PASS";	
			}
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
				}
			}

		//*****SelectList Method**********
		//METHOD WILL SELECT INSTITUTION FROM DROPDOWN LIST & VALIDATES BEFORE SELECTING THE INSTITUTION WITH DROP LIST INSTITUTIONS
		public String SelectList(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
		//APP_LOGS.debug("Select Desired Institution");
		String ActOrg="";
		try{
			if(data == ""){
				return "PASS";
			}
			String tar[]=target.split("\\&");
			driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[0]))).sendKeys(data);
			ActOrg=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1]))).getText();
			if(data.equals(ActOrg)){
				driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1]))).click();
				return "PASS";
			}
			if(data.equals(ActOrg)){
				return "PASS";
			}else{
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				return "FAIL";
			}
		}catch (Exception e){
			ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			e.printStackTrace();
			return "FAIL";
		}
	}
		
		public String btnmode(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			try{
				boolean ButtonState = driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).isEnabled();
				System.out.println("CreatePolicy ="+ButtonState );
				System.out.println("data ="+data );
				if (ButtonState==true && data.equals("true")){
					driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).click();
					return "PASS";
				}else if (ButtonState==false && data.equals("false")){
					return "PASS";
			    }else{
			    	ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		return "FAIL";
			    	}
				}catch(Exception e){
			   		ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
			   		e.printStackTrace();
			   		return "FAIL";
			   	}
			}
		
		public String isLocationSelected(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("Input The Value In Test Box");
			try{
				driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).click();
				String ActText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getAttribute("class");
				System.out.println(ActText);
				if(data.equals("YES") && ActText.contains("map-icon selected leaflet")){
					return "PASS";
				}else if (data.equals("NO") && ActText.contains("map-icon leaflet")){
					return "PASS"; 
				}else{
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					return "FAIL"; 
				}
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
			}
		}
		
		public String VerifyPaging(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("Input The Value In Test Box");
			try{
				List<WebElement> elements = driver.findElements(By.xpath(MainThread.CONFIG.getProperty(target)));
				String leftnav= driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target))).getAttribute("class");
				System.out.println("leftnav="+leftnav);
				String rightnav="";
				String rightnav2="";
				for(int i=1; i<=1000; i++){
					driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)+"["+elements.size()+"]/a")).click();
					rightnav= driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)+"["+elements.size()+"]")).getAttribute("class");
					System.out.println("rightnav="+rightnav);
					if(rightnav.contains("disabled")){
						break;
					}
				}
				for(int j=1; j<=1000; j++){
					driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)+"["+1+"]/a")).click();
					rightnav2= driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)+"["+1+"]")).getAttribute("class");
					System.out.println("rightnav2="+rightnav2);
					if(rightnav2.contains("disabled")){
						break;
					}
				}
				/*
				Int LastPageNum=elements.size()-1;
				int MaxPageNum=driver.findElement(By.xpath("//*[@id='verizonView']/div/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div/div/ul/li["+LastPageNum+"]/a/span")).getText();
				//int Maxpages=int(MaxPageNum);
				for(int k=3; k<=MaxPageNum; k++){
					driver.findElement(By.xpath("//*[@id='verizonView']/div/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div/div/ul/li["+k+"]/a/span")).click();
					rightnav2= driver.findElement(By.xpath(MainThread.CONFIG.getProperty(target)+"["+1+"]")).getAttribute("class");
					System.out.println("rightnav2="+rightnav2);
					if(rightnav2.contains("disabled")){
						break;
					}
				}*/
				System.out.println("rightnav="+rightnav);
				if(rightnav.contains("disabled") && leftnav.contains("disabled") && rightnav2.contains("disabled")){
					return "PASS";
				}else{
					ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
					return "FAIL"; 
				}
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
			}
		}
		
		public String verifyLMSearch(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("Input The Value In Test Box");
			try{
				String tar[]=target.split("\\&");
				List<WebElement> elements = driver.findElements(By.xpath(MainThread.CONFIG.getProperty(tar[0])));
				System.out.println("elements="+elements);
				for(int i=1; i<=elements.size(); i++){
					String SearchText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[0])+"["+i+"]"+MainThread.CONFIG.getProperty(tar[1]))).getText();
					System.out.println("SearchText="+SearchText);
					if(SearchText.contains(data)){
						continue;
					}else{
						ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
						return "FAIL";
					}
				}
				return "PASS";
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
			}
		}
		
		public String verifyLMSearchAddCharByChar(WebDriver driver, String browser,  String target, String data, File SubFolderPath, String TCID, String TSID,  String DSID, String Correct_Data, int currentTestDataSetID, String user, Xlsx_Reader currentTestSuiteXLS, String currentTestCaseName) throws InterruptedException, IOException{
			//APP_LOGS.debug("Input The Value In Test Box");
			try{
				String tar[]=target.split("\\&");
				for(int j=0;j<data.length();j++){
			       	driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[0]))).sendKeys(Character.toString(data.charAt(j)));
			        List<WebElement> elements = driver.findElements(By.xpath(MainThread.CONFIG.getProperty(tar[1])));
					for(int i=1; i<=elements.size(); i++){
						String SearchText1=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1])+"["+i+"]"+"/td["+1+"]")).getText();
						String SearchText2=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1])+"["+i+"]"+"/td["+2+"]")).getText();
						String SearchText3=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1])+"["+i+"]"+"/td["+3+"]")).getText();
						String SearchText4=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1])+"["+i+"]"+"/td["+4+"]")).getText();
						String SearchText5=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[1])+"["+i+"]"+"/td["+5+"]")).getText();
						String InputSearchText=driver.findElement(By.xpath(MainThread.CONFIG.getProperty(tar[0]))).getAttribute("value");
						System.out.println("SearchText="+SearchText1+"=="+SearchText2+"=="+SearchText3+"=="+SearchText4+"=="+SearchText5);
						System.out.println("InputSearchText="+InputSearchText);
						if(SearchText1.contains(InputSearchText)||SearchText2.contains(InputSearchText)||SearchText3.contains(InputSearchText)||SearchText4.contains(InputSearchText)||SearchText5.contains(InputSearchText)){
							continue;
						}else{
							ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
							return "FAIL";
						}
					}
				}
				return "PASS";
			}catch (Exception e){
				ScreenShot( driver, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
				e.printStackTrace();
				return "FAIL";
			}
		}
}