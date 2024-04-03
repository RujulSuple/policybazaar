package policybazaar.utility;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.Random;
import org.json.simple.JSONArray;

public class Utility {

    public void getScreenshot(WebDriver driver, String methodName){

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String filePath = "Screenshots"+File.separator+methodName+".png";
        File destination = new File(filePath);
        try{
            FileUtils.copyFile(source,destination);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot for"+methodName);
        }
    }

    public void writeToExcel(String sheetName, String cellValue, int row, int col) throws IOException {
        String filePath = "TestOutput"+ File.separator+"ProgramOutput.xlsx";
        File excelFile = new File(filePath);
        XSSFWorkbook workbook;

        if(excelFile.exists()){
            FileInputStream inputStr = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(inputStr);
            inputStr.close();
        }
        else{
            workbook = new XSSFWorkbook();
        }

        XSSFSheet sheet = workbook.getSheet(sheetName);
        if(sheet==null){
            sheet = workbook.createSheet(sheetName);
        }

        XSSFRow currentRow = sheet.getRow(row);
        if(currentRow==null){
            currentRow = sheet.createRow(row);
        }

        XSSFCell currentCell = currentRow.getCell(col);
        if(currentCell==null){
            currentCell = currentRow.createCell(col);
        }
        currentCell.setCellValue(cellValue);
        FileOutputStream outputStr = new FileOutputStream(excelFile);
        workbook.write(outputStr);
        outputStr.close();
    }

    public String[] getCurrentDate(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.toString().split("T")[0].split("-");
    }

    public String[] parseTestData(String fileName, String fieldName) {
        String dataDirectory = "src"+ File.separator+"test"+File.separator+"resources"+File.separator+"testData"+File.separator;
        JSONParser parser = new JSONParser();
        String[] fieldValue = null;
        try {
            Object obj = parser.parse(new FileReader(dataDirectory+fileName+".json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get(fieldName);
            fieldValue = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                fieldValue[i] = (String) jsonArray.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldValue;
    }

    public String getRandomData(String[] dataSet){
        Random random = new Random();
        int randomIndex = random.nextInt(dataSet.length);
        return dataSet[randomIndex];
    }
}
