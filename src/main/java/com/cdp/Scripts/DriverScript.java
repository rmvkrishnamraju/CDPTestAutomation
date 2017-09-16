package com.cdp.Scripts;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

class MainThread  {
	public String browser;
	public static  Logger APP_LOGS;
	public long startTime;
	public String startTimes;
	public String StartTimeHH;
	public String FolderName;
	public String SubFolderName;
	public File SubFolderPath;
	Workbook workbook;
	// INITIALIZING POI EXCEL OBJECTS
	public Xlsx_Reader SuiteXLS;
	public int currentSuiteID;
	public String currentTestSuite;
	public Xlsx_Reader ResultsXLS;
	public Xlsx_Reader currentTestSuiteXLS;
	public String currentTestCaseName;
	public String currentTestName;
	public int currentTestStepID;
	public String priority;
	public ArrayList<String> resultSet;
	public String current_data_sheet_name;
	public int currentTestCaseID;
	public int currentTestDataSetID;
	public String Keyword;
	public String target;
	public String data;
	public String user;
	public String TCID;
	public String TSID;
	public String Proceed_ON_FAIL;
	public String Correct_Data;
	public Method method[];
	public Keywords keywords;
	public long DatasetStepEndTime;
	public long DatasetStepStartTime;
	public String DSID;
	public long starttime1;
	public long endtime1;
	public String Result;
	public int count;
	public String Keyword_execution_result_main;
	public WebDriver driver;
	public WebDriver driver1;
	public WebDriver driver2;
	public String colName;
	public String colName1;
	//public Reexecuted Reexecuted;
	//DECLARING TO STORE RESULTS PASS/FAIL/SKIP COUNT
	public int PASSCOUNT=0;
	public int FAILCOUNT=0;
	public int NO_RUN=0;
	//public ArrayList<String> Keyword_execution_result_main;
	
	//DECLARING TO STORE P1/P2/P3/P4 COUNT
	public int P1=0;
	public int P2=0;
	public int P3=0;
	public int P4=0;
	public ExtentReports reports;
	public ExtentTest logger;
	public ExtentTest Child;
	
		
	//DECLARING CONFIGURATION PROPERTIES
	public static Properties CONFIG;
	MainThread(String name){
		browser=name;
	}
	
	
	public void run() {
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug(browser+" browser Started"); 
		APP_LOGS.debug("Calling MainScript");
		try{
			FileInputStream fs= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/cdp/config/config.properties");
			CONFIG= new Properties();
			CONFIG.load(fs);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			starttime1= System.currentTimeMillis();
			SimpleDateFormat subfolderdateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar subfoldercal = Calendar.getInstance();
			startTimes =subfolderdateFormat.format(subfoldercal.getTime());
			MainScript();
			//Reexecuted();
			closebrowsers();
			AutomationTestReport();
			SendEmail();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		APP_LOGS.debug(browser + " exiting...");
		}
	
	@Test
	public void MainScript() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
		System.out.println("MainScript Browser "+browser);
		keywords = new Keywords();
		method = keywords.getClass().getMethods();
		
		APP_LOGS.debug(browser+"::Browser Executing MainScript");
		// TO GET EXECUTION DATE AND START TIME
		APP_LOGS.debug(browser+"::GETTING DATE AND START TIME");
		startTime = System.currentTimeMillis();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Calendar cal1 = Calendar.getInstance();
		StartTimeHH = dateFormat1.format(cal1.getTime());
		//INITIALIZING TEST RESULTS FOLDER WITH SYS DATE
		APP_LOGS.debug(browser+"::INITIALIZING TEST RESULTS FOLDER WITH SYS DATE");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		FolderName = dateFormat.format(cal.getTime());
		APP_LOGS.debug(browser+"::FolderName"+FolderName);
		
		//INITIALIZING SUBFOLDER NAME WITH SYS DATE, TIME IN HH:MM
		APP_LOGS.debug(browser+"::INITIALIZING SUBFOLDER NAME WITH SYS DATE, TIME IN HH:MM");
		SimpleDateFormat subfolderdateFormat = new SimpleDateFormat("HH-mm-ss");
		Calendar subfoldercal = Calendar.getInstance();
		SubFolderName = browser+"-Results-"+subfolderdateFormat.format(subfoldercal.getTime());
		APP_LOGS.debug(browser+"::SubFolderName"+SubFolderName);
		
		//IF THE DIRECTORY DOES NOT EXISTS, ELSE CREATE IT(CREATE DIRECTORY WITH CURRENT DATE)
		APP_LOGS.debug(browser+"::Creatring Directory With Current Date");
		File FolderPath = new File(System.getProperty("user.dir")+"/src/main/java/com/cdp/OutFiles/"+FolderName);
		if (!FolderPath.exists()) {
			boolean result = false;
			try{
				FolderPath.mkdir();
				result = true;
				} 
			catch(SecurityException e){
				e.printStackTrace();
				}		    
			}
		APP_LOGS.debug(browser+"::Created Directory With Current Date");
		APP_LOGS.debug(browser+"::Creating SubDirectory With Current TimeStamp & Browser Name");
		SubFolderPath = new File(FolderPath+"/"+SubFolderName);
		if (!SubFolderPath.exists()) {
			boolean result = false;
		    try{
		    	SubFolderPath.mkdir();
		        result = true;
		    } 
		    catch(SecurityException e){
		        e.printStackTrace();
				}		    
		    }
		copyHTMLDoc(SubFolderPath, "CDP_Automation_Test_Report");
		reports = new ExtentReports(SubFolderPath+"/"+"OutPut_"+"CDP_Automation_Test_Report.html");
		reports.loadConfig(new File("D:/MohanBaseFramework/CDPAutomation/src/main/resources/extent-config.xml"));
		//reports.loadConfig("LoadConfig(@"D:\Report\extent-config.xml");");
		reports.addSystemInfo("Browser", browser);
	
		APP_LOGS.debug(browser+"::Created SubDirectory With Current TimeStamp & Browser Name");
		//COPYING SUITE AND RESULTS XLS FILE FROM INPUT FOLDER TO OUTPUT FOLDER
		copyWorkbook(SubFolderPath, "Suite_Web");
		copyWorkbook(SubFolderPath, "Results");
		
		//READING SUITE_WEB XLS FILE FROM OUTPUT DIRECTORY
		APP_LOGS.debug(browser+"::READING SUITE_WEB XLS FILE FROM OUTPUT DIRECTORY");
		SuiteXLS = new Xlsx_Reader(SubFolderPath+"/"+"OutPut_"+"Suite_Web.xlsx");
		
		//FOR LOOP TO GET/READ TEST SCRIPT NAME/ID FROM SUITE.XLS SHEET
		for(currentSuiteID=2;currentSuiteID<=SuiteXLS.getRowCount("Suite");currentSuiteID++){
			APP_LOGS.debug(SuiteXLS.getCellData("Suite", "TSID", currentSuiteID)+" -- "+SuiteXLS.getCellData("Suite", "Runmode", currentSuiteID));
			currentTestSuite=SuiteXLS.getCellData("Suite", "TSID", currentSuiteID);
			//VALIDATION TO CHECK XLS SHEET EXISTING WITH TEST SCRIPT OR NOT
			if(SuiteXLS.getCellData("Suite", "Runmode", currentSuiteID).equals("Y")){
				System.out.println("testsuite="+currentTestSuite);
				copyWorkbook(SubFolderPath, currentTestSuite);
				//READ RESULTS XLS FILE FROM OUTPUT DIRECTORY
				ResultsXLS= new Xlsx_Reader(SubFolderPath+"/"+"OutPut_"+"Results"+".xlsx");
				//READ CURRENTTESTSUITE XLS FILE FROM OUTPUT DIRECTORY
				currentTestSuiteXLS= new Xlsx_Reader(SubFolderPath+"/"+"OutPut_"+currentTestSuite+".xlsx");
				//FOR LOOP TO GET/READ ALL TEST CASES FROM CURRENTTESTSUITE.XLS SHEET
				for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("TestCases");currentTestCaseID++){
					APP_LOGS.debug(currentTestSuiteXLS.getCellData("TestCases", "TCID", currentTestCaseID)+" -- "+currentTestSuiteXLS.getCellData("TestCases", "Runmode", currentTestCaseID));
					//CURRENT TEST CASE NAME USED TO READ THE TEST STEPS FROM TEST STEPS SHEET
					currentTestCaseName=currentTestSuiteXLS.getCellData("TestCases", "TCID", currentTestCaseID);
					//CURRENTTESTNAME IS USED TO PREFIX THE ERROR SCREENSHOT WITH TEST NAME 
					//CONDITION TO CHECK CUREENT TEST CASE RUN MODE YES/NO
					if(currentTestSuiteXLS.getCellData("TestCases", "Runmode", currentTestCaseID).equals("Y")){
						APP_LOGS.debug(browser+"**********Executing Test Cases**********"+currentTestCaseName);
						resultSet= new ArrayList<String>();
						//WILL READ CURRENT TEST NAME PRIORITY(P1/P2/P3/P4) TO UPDATE FAILED TEST CASE PRIORITY RESUTLS SHEET
						priority=currentTestSuiteXLS.getCellData("TestCases", "Priority", currentTestCaseID);
						if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
							//RUN AS MANY TIMES AS NUMBER OF TEST DATA SETS WITH RUNMODE Y
							current_data_sheet_name=currentTestCaseName;
							for(currentTestDataSetID=2;currentTestDataSetID<=currentTestSuiteXLS.getRowCount(current_data_sheet_name);currentTestDataSetID++){
								//createColumnExecution_Time();
								createColumn();//Creating Result Column In Current Test Steps and Test Cases Sheet
								resultSet= new ArrayList<String>();
							    APP_LOGS.debug("**********ITERATION NUMBER**********"+(currentTestDataSetID-1));
								//CHECKING RUNMODE FOR THE CURRENT DATA SET
								if(currentTestSuiteXLS.getCellData(currentTestCaseName, "Runmode", currentTestDataSetID).equals("Y")){
									logger = reports.startTest(currentTestCaseName+"_DS"+(currentTestDataSetID-1));
									//Correct_Data=currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.TEST_Correct_Data, currentTestDataSetID);
									//Createuser=currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.TEST_Createuser, currentTestDataSetID);
									DSID=currentTestSuiteXLS.getCellData(currentTestCaseName, "DSID", currentTestDataSetID);
									System.out.println("dsid"+DSID);
									//ExpectedErrorMsg=currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.TEST_EXP_ERROR_MSG, currentTestDataSetID);
									// ITERATING THOUGH ALL KEYWORDS
									executeKeywords(browser);//Multiple Steps OF Test Data
								}else{
									logger = reports.startTest(currentTestCaseName+"_DS"+(currentTestDataSetID-1));
									logger.log(LogStatus.SKIP, "StepName", "details1");
									//CALLING METHOD TO PRINT PASS/PASS/SKIP STATUS
									//currentTestSuiteXLS.setCellData("TestCases", colName, currentTestCaseID, "NO RUN");
									if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
										//currentTestSuiteXLS.setCellData(currentTestCaseName, "Result", currentTestDataSetID, "NO RUN");
									}
									NO_RUN=NO_RUN+1;
									ResultsXLS.setCellData("Status", "Result", 7, NO_RUN);
									/*for(currentTestStepID=2;currentTestStepID<=currentTestSuiteXLS.getRowCount("TestSteps");currentTestStepID++){
										if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData("TestSteps", "TCID", currentTestStepID)))
											//currentTestSuiteXLS.setCellData("TestSteps", colName, currentTestStepID, "NO RUN");
										}*/	
									}
									reports.endTest(logger);
									reports.flush();
								}
							}else{
								logger = reports.startTest(currentTestCaseName);
								currentTestDataSetID=0;
								//createColumnExecution_Time();
								createColumn();
								executeKeywords(browser);//No Test Data With The Test
							}
						}else{
							boolean isColExist=false;
							//logger.log(LogStatus.SKIP, browser+ "::"+"Test Case Not Executed");
							//logger.log(LogStatus.SKIP, browser+ "::"+"Test Case Not Executed");
							logger = reports.startTest(currentTestCaseName);
							logger.log(LogStatus.SKIP, "StepName", "details1");
							
							for(int c=0;c<currentTestSuiteXLS.getColumnCount("TestCases");c++){
								if(currentTestSuiteXLS.getCellData("TestCases", c, 1).equals("Result1")){
									isColExist=true;
									break;
								}
							}
							if(!isColExist){
						        currentTestSuiteXLS.addColumn("TestSteps", "Execution_Time1");
						        currentTestSuiteXLS.addColumn("TestSteps", "Result1");
						        currentTestSuiteXLS.addColumn("TestCases", "Result1");
						       }
						       currentTestSuiteXLS.setCellData("TestCases", "Result1", currentTestCaseID, "NO RUN");
						       NO_RUN=NO_RUN+1;
						       ResultsXLS.setCellData("Status", "Result", 7, NO_RUN);
						      }
							reports.endTest(logger);
							reports.flush();
						     }
						    }
						   }
						  }
	public void copyWorkbook(File SubFolderPath, String FileName) throws IOException{
		APP_LOGS.debug(browser+"::COPYING "+FileName+" XLS FILE FROM INPUT FOLDER TO OUTPUT FOLDER");
		File source = new File(System.getProperty("user.dir")+"/src/main/java/com/cdp/InputFiles/"+FileName+".xlsx");
		File dest = new File(SubFolderPath+"/"+"OutPut_"+FileName+".xlsx");
		FileUtils.copyFile(source, dest);
		APP_LOGS.debug(browser+"::COPY COMPLETED FOR "+FileName+" XLS FILE FROM INPUT FOLDER TO OUTPUT FOLDER");
	}
	
	public void copyHTMLDoc(File SubFolderPath, String FileName) throws IOException{
		APP_LOGS.debug(browser+"::COPYING "+FileName+" HTML FILE FROM INPUT FOLDER TO OUTPUT FOLDER");
		File source = new File(System.getProperty("user.dir")+"/src/main/java/com/cdp/InputFiles/"+FileName+".html");
		File dest = new File(SubFolderPath+"/"+"OutPut_"+FileName+".html");
		FileUtils.copyFile(source, dest);
		APP_LOGS.debug(browser+"::COPY COMPLETED FOR "+FileName+" HTML FILE FROM INPUT FOLDER TO OUTPUT FOLDER");
	}
	
	public void executeKeywords(String browser) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, IOException{
		for(currentTestStepID=2;currentTestStepID<=currentTestSuiteXLS.getRowCount("TestSteps");currentTestStepID++){
			APP_LOGS.debug("******Looping "+currentTestCaseName+" ::Steps");
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData("TestSteps", "TCID", currentTestStepID))){
				Keyword=currentTestSuiteXLS.getCellData("TestSteps", "Keyword", currentTestStepID);
				TSID=currentTestSuiteXLS.getCellData("TestSteps", "TSID", currentTestStepID);
				APP_LOGS.debug("TSID::"+TSID);
				APP_LOGS.debug("executing current keyword::"+Keyword);
				target=currentTestSuiteXLS.getCellData("TestSteps", "Target", currentTestStepID);
				APP_LOGS.debug("executing keyword target::"+target);
				data=currentTestSuiteXLS.getCellData("TestSteps", "Data", currentTestStepID);
				APP_LOGS.debug("data::"+data);
				user=currentTestSuiteXLS.getCellData("TestSteps", "user", currentTestStepID);
				TCID=currentTestSuiteXLS.getCellData("TestSteps", "TCID", currentTestStepID);
				APP_LOGS.debug("TCID::"+TCID);
				Proceed_ON_FAIL=currentTestSuiteXLS.getCellData("TestSteps", "Proceed_ON_FAIL", currentTestStepID);
				APP_LOGS.debug("Proceed_ON_FAIL::"+Proceed_ON_FAIL);
				if(currentTestDataSetID>=2){
					Correct_Data=currentTestSuiteXLS.getCellData(currentTestCaseName, "Correct_Data", currentTestDataSetID);
				}else{
					Correct_Data="Y";
				}
				if(data.startsWith("col$")){
					String[] splitData = data.split("\\$");
					data=splitData[1];
					data=currentTestSuiteXLS.getCellData(currentTestCaseName, data, currentTestDataSetID);
				}
				for(int i=0;i<(method.length);i++){
					if(method[i].getName().equals(Keyword)){						
						if(Keyword.equals("LaunchWebBrowser")){
							if(user.equals("user1") && driver1==null){
								APP_LOGS.debug("Launching 1st "+browser+ " ::Browser");
								long StepstartTime = System.currentTimeMillis();
								driver= (WebDriver) method[i].invoke(keywords, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
								long StepEndTime = System.currentTimeMillis();
								ExecutionTime(StepEndTime, StepstartTime);
								driver1=driver;
								if (driver1!=null){
									logger.log(LogStatus.PASS, TSID+"  :  "+Keyword+"");
								}else{
									logger.log(LogStatus.FAIL, TSID+"  :  "+Keyword+"");
								}
								addDriverResults(driver1);
							}else if(user.equals("user2") && driver2==null){
								APP_LOGS.debug("Launching 2nd "+browser+ " ::Browser");
								long StepstartTime = System.currentTimeMillis();
								driver= (WebDriver) method[i].invoke(keywords, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
								long StepEndTime = System.currentTimeMillis();
								ExecutionTime(StepEndTime, StepstartTime);
								driver2=driver;
								if (driver1!=null){
									logger.log(LogStatus.PASS, TSID+"  :  "+Keyword+"");
								}else{
									logger.log(LogStatus.FAIL, TSID+"  :  "+Keyword+"");
								}
								addDriverResults(driver2);
							}else{
								APP_LOGS.debug(browser+ " ::Browser Already Running");
								logger.log(LogStatus.PASS, TSID+"  :  "+Keyword+"  :  "+browser+ " ::Browser Already Running");
								Keyword_execution_result_main="NO RUN";
								resultSet.add(Keyword_execution_result_main);
							}
						}else{
							if(user.equals("user1")){
								long StepstartTime = System.currentTimeMillis();
								Keyword_execution_result_main=(String) method[i].invoke(keywords, driver1, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
								long StepEndTime = System.currentTimeMillis();
								ExecutionTime(StepEndTime, StepstartTime);
								resultSet.add(Keyword_execution_result_main);
								APP_LOGS.debug("resultset"+resultSet);
								if (Keyword_execution_result_main.equals("PASS")){
									logger.log(LogStatus.PASS, TSID+"  :  "+Keyword+"");
								}else{
									logger.log(LogStatus.FAIL, TSID+"  :  "+Keyword+"");
								}
							}else{
								long StepstartTime = System.currentTimeMillis();
								Keyword_execution_result_main= (String) method[i].invoke(keywords, driver2, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
								long StepEndTime = System.currentTimeMillis();
								ExecutionTime(StepEndTime, StepstartTime);
								resultSet.add(Keyword_execution_result_main);
								if (Keyword_execution_result_main.equals("PASS")){
									logger.log(LogStatus.PASS, TSID+"  :  "+Keyword+"");
								}else{
									logger.log(LogStatus.FAIL, TSID+"  :  "+Keyword+"");
								}
							}
						}
						//TO PRINT PASS FAIL STATUS IN TEST STEPS SHEETS AFTER EXECUTING EACH STEPS
						currentTestSuiteXLS.setCellData("TestSteps", colName, currentTestStepID, Keyword_execution_result_main);
						if(Keyword_execution_result_main=="FAIL" && Proceed_ON_FAIL.equals("NO")){
							APP_LOGS.debug("Stopping Current Test Cases Execution because Test Step is Failed and Proceed_ON_FAIL=NO");
							resetApp();
							i=method.length+10;//To break execute keywords FOR loop
							currentTestStepID=5000;//To break execute current steps FOR loop
						
						}
						if((Keyword_execution_result_main=="PASS" || Keyword_execution_result_main=="FAIL") &&Proceed_ON_FAIL.equals("NO")&& data.equals("false")){
							System.out.println("REST APP EXECUTED");
							APP_LOGS.debug("Stopping Current Test Cases Execution because Test Step is Failed and Proceed_ON_FAIL=NO");
							resetApp();
							i=method.length+10;//To break execute keywords FOR loop
							currentTestStepID=5000;//To break execute current steps FOR loop
							
						}
					}
				}
			}
		}
		createXLSReport();
		createGraphReport();
		priorityGraphReport();
		//DatasetStepEndTime = System.currentTimeMillis();
		//ExecutionTime(DatasetStepEndTime, DatasetStepStartTime);
	}

	public void ExecutionTime(long StepEndTime, long StepstartTime){
		long diff = StepEndTime-StepstartTime;
		//long hrs = TimeUnit.MILLISECONDS.toHours(diff) % 24;
        long min = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
        long sec = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
        long mls = diff % 1000;
        String Time = String.format("%02d:%02d:%03d", min, sec, mls);
		System.out.println("minutes="+Time);
		currentTestSuiteXLS.setCellData("TestSteps",  "Execution_Time"+count, currentTestStepID, Time);
		//currentTestSuiteXLS.setCellData("TestCases", colName1, currentTestCaseID,Time );
		if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
			currentTestSuiteXLS.setCellData(currentTestCaseName, "Execution_Time", currentTestDataSetID, Time);
		}
	}
	
	public void addDriverResults(WebDriver driver){
		if(driver.equals(null)){
			Keyword_execution_result_main="FAIL";
			resultSet.add(Keyword_execution_result_main);
		}else{
			Keyword_execution_result_main="PASS";
			resultSet.add(Keyword_execution_result_main);
		}
	}
	public void createColumn(){//Creating Result Column In Current Test Steps and Test Cases Sheet
		APP_LOGS.debug("Creating Result Column In Current Test Steps and Test Cases Sheet");
		if(currentTestDataSetID>1){
			colName="Result"+(currentTestDataSetID-1);
			count=currentTestDataSetID-1;
		}else{
			colName="Result"+1;
			count=1;
		}
		boolean isColExist=false;
		for(int c=0;c<currentTestSuiteXLS.getColumnCount("TestSteps");c++){
			if(currentTestSuiteXLS.getCellData("TestSteps", c, 1).equals(colName)){
				isColExist=true;
				break;
			}
		}
		if(!isColExist){
			currentTestSuiteXLS.addColumn("TestSteps", "Execution_Time"+count);
			currentTestSuiteXLS.addColumn("TestSteps", colName);
			currentTestSuiteXLS.addColumn("TestCases", colName);
		}
	}
	public void createXLSReport(){
		APP_LOGS.debug("Calling printresults Function To Print PASS/FAIL Status In Results Sheet");
		if(resultSet.contains("FAIL")){
			currentTestSuiteXLS.setCellData("TestCases", colName, currentTestCaseID, "FAIL");
			if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
				currentTestSuiteXLS.setCellData(currentTestCaseName, "Result", currentTestDataSetID, "FAIL");
			}
		}else{
			currentTestSuiteXLS.setCellData("TestCases", colName, currentTestCaseID, "PASS");
			if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
				currentTestSuiteXLS.setCellData(currentTestCaseName, "Result", currentTestDataSetID, "PASS");
			}
		}
	}
	//TO PRINT DATE, START TIME, END TIME, ELAPSED TIME AND BROWSER NAME IN RESULTS SHEET
	public void AutomationTestReport(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");    
		//TO PRINT DATE IN RESULTS SHEET
		ResultsXLS.setCellData("Status", "Results10", 4, FolderName);
		SimpleDateFormat subfolderdateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar subfoldercal = Calendar.getInstance();
		String endtimeTime =subfolderdateFormat.format(subfoldercal.getTime());
		endtime1= System.currentTimeMillis();
        System.out.println("endtime1"+endtime1);
		//TO PRINT START TIME IN RESULTS SHEET
		//String StartTimeHH1 = StartTimeHH.substring(StartTimeHH.length() - 8);	
		
		ResultsXLS.setCellData("Status", "Results10", 5, startTimes);
		ResultsXLS.setCellData("Status", "Results10", 6, endtimeTime);
		//TO PRINT ELAPSED TIME IN RESULTS SHEET
		//long elapsedtime=endTime-startTime;
		
		//String elapsedtimestr = Long.toString(elapsedtime);
       
       long diff = endtime1-starttime1;
       System.out.println("diff"+diff);
       long hrs = TimeUnit.MILLISECONDS.toHours(diff) % 24;
       long min = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
       long sec = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
       long mls = diff % 1000;
       String Time = String.format("%02d:%02d:%02d:%03d",hrs, min, sec, mls);
       
		System.out.println("minutes="+Time);
		
		ResultsXLS.setCellData("Status", "Results10", 7, Time);
		//System.out.println(ResultsXLS.setCellData("Status", "Results10", 7, Time));
		//Date resultdate = new Date(elapsedtime);
		
		//TO PRINT BROWSER NAME TIME IN RESULTS SHEET
		ResultsXLS.setCellData("Status", "Results10", 8, browser);
	}
	//TO PRINT PASS, FAIL and NO RUN COUNT
	public void createGraphReport(){
		//TO PRINT PASS FAIL STATUS IN RESULTS SHEET & TO PRINT PASS FAIL STATUS IN TEST CASE SHEET 
		if(resultSet.contains("FAIL")){
				FAILCOUNT=FAILCOUNT+1;
				ResultsXLS.setCellData("Status", "Result", 6, FAILCOUNT);
		}else{
			PASSCOUNT=PASSCOUNT+1;
			ResultsXLS.setCellData("Status", "Result", 5, PASSCOUNT);
		}
	}
	public void closebrowsers() throws InterruptedException, IOException{
		if(driver1!=null){
			keywords.CloseWebApp(driver1, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		}
			else if(driver2!=null){
			keywords.CloseWebApp(driver2, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		}
	}
	
	public void resetApp() throws InterruptedException, IOException{
		if(driver1!=null){
			keywords.resetApp(driver1, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		}
			else if(driver2!=null){
			keywords.resetApp(driver2, browser, target, data, SubFolderPath, TCID, TSID, DSID, Correct_Data, currentTestDataSetID, user, currentTestSuiteXLS, currentTestCaseName);
		}
	}
	
	public void priorityGraphReport(){
		//TO PRINT P1, P2, P3, P4 COUNT IN RESULTS SHEET
		if(resultSet.contains("FAIL")){
				if(priority.contains("P1")){
					P1=P1+1;
					ResultsXLS.setCellData("Status", "Result", 19, P1);
				}else if(priority.contains("P2")){
					P2=P2+1;
					ResultsXLS.setCellData("Status", "Result", 20, P2);
				}else if(priority.contains("P3")){
					P3=P3+1;
					ResultsXLS.setCellData("Status", "Result", 21, P3);
				}else if(priority.contains("P4")){
					P4=P4+1;
					ResultsXLS.setCellData("Status", "Result", 22, P4);
				}
			}
		}
	public void SendEmail() {
		// Sender's email ID needs to be mentioned
		String from = "automationcdp3@gmail.com";
		final String username = "automationcdp3@gmail.com";//change accordingly
		final String password = "cdp@1234";//change accordingly
		
		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		
		// Get the Session object.
		
		Session session = Session.getInstance(props,
		new javax.mail.Authenticator(){
		protected PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(username, password);
	        }
	      });
		  try {
	    	  
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);
	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));
	         // Set To: header field of the header.
	         String[] to =CONFIG.getProperty("EmailAccountsForReports").split("\\|");
	         //String[] to = {"nmksridhar@gmail.com","mohan.nimmala@mtuity.com"};
	         try {
	        	 InternetAddress[] addressTo = new InternetAddress[to.length];
	             for (int i = 0; i < to.length; i++)
	            {
	                 addressTo[i] = new InternetAddress(to[i]);
	             }
	             message.setRecipients(RecipientType.TO, addressTo);
	         }catch(Exception exc) {
	         }
	         // Set Subject: header field
	         message.setSubject("CDP || Dashboard || "+browser+" Browser || Automation Test Report");	 
	 		
	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("PFB... Test Reports. This message generated by Automation Scripts.");

	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);    
	         File resultsFolder = new File(SubFolderPath.toString());
	         File[] attachmentsList = resultsFolder.listFiles();
	         if(attachmentsList!=null){
	        	 
	        	 MimeBodyPart mbp2 = null;
	             FileDataSource fds =null;
	             for(int counter=0;counter<attachmentsList.length;counter++)
	             {
	                 mbp2 = null;
	                 fds =null;
	                 mbp2=new MimeBodyPart();
	                 fds = new FileDataSource(attachmentsList[counter]);
	                 String FileName=fds.getName();
	                 if (FileName.endsWith("Results.xlsx")){
	                	 mbp2.setDataHandler(new DataHandler(fds));
		                 mbp2.setFileName(fds.getName());
		                 multipart.addBodyPart(mbp2);
	                 }
	                 if (FileName.endsWith("Test_Report.html")){
	                	 mbp2.setDataHandler(new DataHandler(fds));
		                 mbp2.setFileName(fds.getName());
		                 multipart.addBodyPart(mbp2);
	                 }
	             }
	         }
	         message.setContent(multipart);
	         message.setSentDate(new Date(startTime));
	         Transport.send(message);
	                  
	       } catch (MessagingException e) {
	         throw new RuntimeException(e);
	       }
	 }
  public void Reexecuted() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, IOException{		
    for(currentSuiteID=2;currentSuiteID<=SuiteXLS.getRowCount("Suite");currentSuiteID++){
    	APP_LOGS.debug(SuiteXLS.getCellData("Suite", "TSID", currentSuiteID)+" -- "+SuiteXLS.getCellData("Suite", "Runmode", currentSuiteID));
		currentTestSuite=SuiteXLS.getCellData("Suite", "TSID", currentSuiteID);
		//VALIDATION TO CHECK XLS SHEET EXISTING WITH TEST SCRIPT OR NOT			
			  if(SuiteXLS.getCellData("Suite", "Runmode", currentSuiteID).equals("Y")){		
			      //FOR LOOP TO GET/READ ALL TEST CASES FROM CURRENTTESTSUITE.XLS SHEET
				     for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("TestCases");currentTestCaseID++){
					  APP_LOGS.debug(currentTestSuiteXLS.getCellData("TestCases", "TCID", currentTestCaseID)+" -- "+currentTestSuiteXLS.getCellData("TestCases", "Runmode", currentTestCaseID));
					  //CURRENT TEST CASE NAME USED TO READ THE TEST STEPS FROM TEST STEPS SHEET
					  currentTestCaseName=currentTestSuiteXLS.getCellData("TestCases", "TCID", currentTestCaseID);
					  //CURRENTTESTNAME IS USED TO PREFIX THE ERROR SCREENSHOT WITH TEST NAME 
					//CONDITION TO CHECK CUREENT TEST CASE RUN MODE YES/NO
					     if(currentTestSuiteXLS.getCellData("TestCases", "Runmode", currentTestCaseID).equals("Y")){
						  APP_LOGS.debug(browser+"**********Executing Test Cases**********"+currentTestCaseName);
						   //WILL READ CURRENT TEST NAME PRIORITY(P1/P2/P3/P4) TO UPDATE FAILED TEST CASE PRIORITY RESUTLS SHEET
						   //priority=currentTestSuiteXLS.getCellData("TestCases", "Priority", currentTestCaseID);
						   if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
								//RUN AS MANY TIMES AS NUMBER OF TEST DATA SETS WITH RUNMODE Y
								current_data_sheet_name=currentTestCaseName;
								for(currentTestDataSetID=2;currentTestDataSetID<=currentTestSuiteXLS.getRowCount(current_data_sheet_name);currentTestDataSetID++){
									//createColumnExecution_Time();
									createColumn();
									resultSet= new ArrayList<String>();
									APP_LOGS.debug("**********ITERATION NUMBER**********"+(currentTestDataSetID-1));
									//CHECKING RUNMODE FOR THE CURRENT DATA SET
									if(currentTestSuiteXLS.getCellData(currentTestCaseName, "Runmode", currentTestDataSetID).equals("Y")){
										//Correct_Data=currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.TEST_Correct_Data, currentTestDataSetID);
										//Createuser=currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.TEST_Createuser, currentTestDataSetID);
										DSID=currentTestSuiteXLS.getCellData(currentTestCaseName, "DSID", currentTestDataSetID);							
										if(currentTestSuiteXLS.getCellData(currentTestCaseName, "Result", currentTestDataSetID).equalsIgnoreCase("FAIL")){
											DSID=currentTestSuiteXLS.getCellData(currentTestCaseName, "DSID", currentTestDataSetID);
											//for(currentTestStepID=2;currentTestStepID<=currentTestSuiteXLS.getRowCount("TestSteps");currentTestStepID++){
												APP_LOGS.debug("******Looping "+currentTestCaseName+" ::Steps");
												//if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData("TestSteps", "TCID", currentTestStepID))){
												  for(int c=0;c<currentTestSuiteXLS.getColumnCount("TestSteps");c++){
													      if(currentTestSuiteXLS.getCellData("TestSteps", c, 1).equalsIgnoreCase("Result"+(currentTestDataSetID-1))){
													    	 System.out.println("hee");
													    	  executeKeywords(browser); 
													    	  
													      }
													} 
												//}
											//}
										}
									}	
								}
						   }
						   else{
								currentTestDataSetID=0;
								if(resultSet.contains("FAIL")){
									//createColumnExecution_Time();
									createColumn();
									executeKeywords(browser);//No Test Data With The Test
								 if(resultSet.contains("PASS")){
								 currentTestSuiteXLS.setCellData("TestCases", colName, currentTestCaseID, "PASS");
								}									
							}
						  }	
					    } 
				     }
			  }
    }	  
  }
} 
public class DriverScript {
	
	public Properties CONFIG;
	@BeforeTest
	@Parameters("browser")
	public void first(String browser) {
		System.out.println("browser Name "+browser);
		MainThread mainThredObj= new MainThread(browser);
		mainThredObj.run();
	 }
}								