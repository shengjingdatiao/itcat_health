import cn.itcast.util.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @PackageName: PACKAGE_NAME
 * @ClassName: demo
 * @Author: dongxiyaohui
 * @Date: 2019/12/4 9:17
 * @Description: //TODO
 */
public class demo {
    @Test
    public void test1() throws IOException {
        //创建工作簿
        XSSFWorkbook sheets = new XSSFWorkbook("G://itcast.xlsx");
        //读取工作表
        XSSFSheet sheetAt = sheets.getSheetAt(0);

       //遍历每一行
        for (Row cells : sheetAt) {
//           获取每一个单元格
            for (Cell cell : cells) {
//                获取每一个单元格中的数据
                String stringCellValue = cell.getStringCellValue();
                System.out.println(stringCellValue);
            }
        }
        //释放资源
        sheets.close();
    }
    @Test
    public void test2() throws IOException {
        //创建工作簿
        XSSFWorkbook sheets = new XSSFWorkbook("G://itcast.xlsx");
        //读取工作表
        XSSFSheet sheet = sheets.getSheet("用户表");
//        XSSFSheet sheet = sheets.getSheetAt(0);
        //获取最后一个行的索引
        int lastRowNum = sheet.getLastRowNum();
        //遍历索引，获取索引，根据行号获取对象
        for(int i = 0;i<=lastRowNum;i++){
            //获取每一行的对象
            XSSFRow row = sheet.getRow(i);
            //根据行对象获取最后一个单元格的索引
            short lastCellNum = row.getLastCellNum();
            for(int r=0;r<lastCellNum;r++){
                //获取每一个单元格的数据
                String stringCellValue = row.getCell(r).getStringCellValue();
                System.out.println(stringCellValue);
            }
        }
        //关闭资源
        sheets.close();
    }
    //向Excel中写入数据
    @Test
    public void test3() throws IOException {
        //在内存中创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表，指定工作表的名称
        XSSFSheet sheet =workbook.createSheet("点名表");
        //创建第一行，行索引以0开头
        XSSFRow row = sheet.createRow(0);
        //通过行对象，创建单元格，并且向单元格中写入数据
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        //创建第二行数据
        XSSFRow row1 = sheet.createRow(1);
         row1.createCell(0).setCellValue("001");
         row1.createCell(1).setCellValue("张三");
        XSSFRow row2 = sheet.createRow(2);
        //创建第二行数据
        row2.createCell(0).setCellValue("002");
        row2.createCell(1).setCellValue("李四");
       //将内存中的Excel表，写到磁盘中
        FileOutputStream fileOutputStream = new FileOutputStream("G:学生表.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        workbook.close();
    }

    @Test
   public void tes() throws Exception {
        String date = DateUtils.parseDate2String(new Date());
        System.out.println(date);
    }
}
