package policybazaar.utility;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.asserts.SoftAssert;
import policybazaar.baseclass.BaseClass;


public class ExtentReportManager extends BaseClass implements ITestListener {

    Utility utility = new Utility();
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String reportName;

    public static boolean errorMessage = false;

    static String screenShotPath = System.getProperty("user.dir")+File.separator+"Screenshots"+File.separator;
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        String browser = testContext.getCurrentXmlTest().getParameter("browser");

        reportName = "PolicyBazaar-"+browser+"Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+File.separator+"TestOutput"+File.separator+ reportName);
        sparkReporter.config().setDocumentTitle("Policy Bazaar Test Report");
        sparkReporter.config().setReportName("Policy Bazaar");
        sparkReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "Policy Bazaar");
        extent.setSystemInfo("Module", "Hackathon Project");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", browser);
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS,result.getName()+" got successfully executed");
        if(errorMessage){
            utility.getScreenshot(driver, result.getName());
            test.addScreenCaptureFromPath(screenShotPath + result.getName()+".png");
        }
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL,result.getName()+" got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
        utility.getScreenshot(driver,result.getName());
        test.addScreenCaptureFromPath(screenShotPath+result.getName()+".png");
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName()+" got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }
    public void onFinish(ITestContext testContext) {
        extent.flush();
        String pathOfExtentReport = System.getProperty("user.dir")+File.separator+"TestOutput"+File.separator+ reportName;
        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}