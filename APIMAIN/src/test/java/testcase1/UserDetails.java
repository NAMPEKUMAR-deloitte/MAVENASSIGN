package testcase1;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class UserDetails {
    public  static String  UserName = null, UserEmail = null, UserPassword = null;
    String UserAge = null;
    public String token;



    public HashMap registerUser() throws Exception {

        String path = "C:\\Users\\nampekumar\\APIMAIN\\UserDetails.xlsx";
        FileInputStream inputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        int rows = sheet.getLastRowNum() + 1;
        for (int i = 1; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            UserName = row.getCell(0).getStringCellValue();
            //UserName = dataFormatter.formatCellValue(sheet.getRow(i).getCell(0));
            UserEmail = dataFormatter.formatCellValue(sheet.getRow(i).getCell(1));
            UserPassword = dataFormatter.formatCellValue(sheet.getRow(i).getCell(2));
            UserAge = dataFormatter.formatCellValue(sheet.getRow(i).getCell(3));
            token = dataFormatter.formatCellValue(sheet.getRow(i).getCell(4));
        }

        HashMap hashMap = new HashMap();
        hashMap.put("name", UserName);
        hashMap.put("email", UserEmail);
        hashMap.put("password", UserPassword);
        hashMap.put("age", UserAge);
        hashMap.put("token",token);
        return hashMap;
    }
    public HashMap registerToken(String RegisterToken) throws Exception
    {
        String path = "C:\\Users\\nampekumar\\APIMAIN\\UserDetails.xlsx";
        FileInputStream inputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet worksheet = workbook.getSheetAt(0);
        Cell cell ;
        cell = worksheet.getRow(1).getCell(4);
        cell.setCellValue(RegisterToken);
        inputStream.close();
        FileOutputStream outputStream =new FileOutputStream(path);
        workbook.write(outputStream);
        outputStream.close();
        HashMap hashMap = new HashMap<>();
        hashMap.put("token", RegisterToken);
        return hashMap;
    }



}