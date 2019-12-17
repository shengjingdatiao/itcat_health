package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entify.Result;
import cn.itcast.service.ReportService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: ReportController
 * @Author: dongxiyaohui
 * @Date: 2019/12/11 8:43
 * @Description: //TODO
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private ReportService reportService;
    //会员统计
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获取当前前12个月的日期
        List<String> list = new ArrayList<>();
        for (int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        map.put("months",list);
        List<Integer>membersCount = null;
        try {
            membersCount = reportService.findMemberCount(list);
            map.put("memberCount",membersCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }
    //{ "setmealNames":["套餐1","套餐2","套餐3"], 
    // "setmealCount":[{"name":"套餐1","value":10},{"name":"套餐2","value":30},{"name":"套餐3","value":25}]},
    // "flag":true,
    // "message":"获取套餐统计数据成功"
    //套餐统计
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
       Map<String,Object> map  = new HashMap<>();
        //查询所有套餐的名称和数量
        List<String> setmealName = new ArrayList<>();
        List<Map<String,Object>> list1 = null;
        try {
            list1 = reportService.findSetMealNameAndCount();
            for (Map<String, Object> stringObjectMap : list1) {
                //通过键获取值，得到每一个套餐的名称
                String name = (String) stringObjectMap.get("name");
                setmealName.add(name);
            }
            map.put("setmealNames",setmealName);
            map.put("setmealCount",list1);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> map =reportService.findBusinessData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        //获取远程数据
        try {
            Map<String, Object> businessData = reportService.findBusinessData();
            String reportDate = (String) businessData.get("reportDate");//获取当前日期
            Integer todayNewMember = (Integer) businessData.get("todayNewMember");//获取新增会员数
            Integer totalMember = (Integer) businessData.get("totalMember"); //获取会员总数
            Integer thisWeekNewMember = (Integer) businessData.get("thisWeekNewMember"); //获取本周新增会员数
            Integer thisMonthNewMember = (Integer) businessData.get("thisMonthNewMember"); //获取本月新增会员数
            Integer todayOrderNumber = (Integer) businessData.get("todayOrderNumber");  //获取当天预约数
            Integer todayVisitsNumber = (Integer) businessData.get("todayVisitsNumber"); //获取当天诊断人数
            Integer thisWeekOrderNumber = (Integer) businessData.get("thisWeekOrderNumber");// 获取本周预约数
            Integer thisWeekVisitsNumber = (Integer) businessData.get("thisWeekVisitsNumber");//获取本周到访人数
            Integer thisMonthOrderNumber = (Integer) businessData.get("thisMonthOrderNumber");//获取本月预约人数
            Integer thisMonthVisitsNumber = (Integer) businessData.get("thisMonthVisitsNumber");//获取本月到诊人数
            List<Map> hotSetmeal = (List<Map>) businessData.get("hotSetmeal");//获取热门套餐
            //获取Excel的绝对路径
            String template = request.getSession().getServletContext().getRealPath("/template") + File.separator + "report_template.xlsx";
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(template)));
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);  //获取第一张表

            XSSFRow row = sheetAt.getRow(2); //获取第三行对象
            row.getCell(5).setCellValue(reportDate); //给第6个单元格赋值

            row=sheetAt.getRow(4); //获取第5行对象
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            row=sheetAt.getRow(5);//获取第6行对象
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            row=sheetAt.getRow(7);//获取第8行对象
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);

            row=sheetAt.getRow(8);//获取第9行对象
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row=sheetAt.getRow(9);//获取第10行对象
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

           int num = 12;
            for (Map map : hotSetmeal) {
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row=sheetAt.getRow(num);
                num++;
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.doubleValue());
            }
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment;filename=report.xls");
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            xssfWorkbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
