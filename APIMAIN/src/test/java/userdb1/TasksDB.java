package userdb1;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TasksDB {
    public static String Task;

    public ArrayList addTasks() throws Exception
    {
        ArrayList arrayList;
        arrayList =new ArrayList<>();
        String path = "C:\\Users\\nampekumar\\APIMAIN\\Book1.xlsx";
        FileInputStream inputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum()+1;
        for(int i=1;i<rows;i++)
        {
            XSSFRow row =sheet.getRow(i);
            Task=row.getCell(0).getStringCellValue();
            arrayList.add(Task);
        }
        return arrayList;
    }
    public HashMap storeOwnerInDB(String storeOwner) throws Exception
    {
        String path = "src\\test\\DataBaseFiles\\Book1.xlsx";
        FileInputStream inputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet worksheet = workbook.getSheetAt(0);
        FileOutputStream outputStream =new FileOutputStream(path);
        XSSFRow row = null;
        Cell cell ;
        cell = worksheet.getRow(1).getCell(1);
        //cell=row.createCell(1);
        cell.setCellValue(storeOwner);
        inputStream.close();
        workbook.write(outputStream);
        outputStream.close();
        HashMap hashMap = new HashMap<>();
        hashMap.put("Owner", storeOwner);
        return hashMap;
    }
    public HashMap storeIdInDB(String storeId) throws Exception
    {
        String path = "src\\test\\DataBaseFiles\\User_Tasks.xlsx";
        FileInputStream inputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet worksheet = workbook.getSheetAt(0);
        FileOutputStream outputStream =new FileOutputStream(path);
        Cell cell ;
        cell = worksheet.getRow(1).createCell(2);
        cell.setCellValue(storeId);
        inputStream.close();

        workbook.write(outputStream);
        outputStream.close();
        HashMap hashMap = new HashMap<>();
        hashMap.put("token", storeId);
        return hashMap;
    }
}
