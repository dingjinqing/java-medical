package com.vpu.mp.controller.excel;

import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelReader;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author 李晓冰
 * @date 2019年07月29日
 */
public class ExcelTest {

    private HttpServletResponse response;

    private HttpServletRequest request;

    private MultipartFile multipartFile;

    private static final String HEADER_LANG = "V-Lang";

    private static final String WRITER_PATH = "/excel/excelWriter.xlsx";
    private static final String READER_PATH = "/excel/excelReader.xlsx";


    @Before
    public void setUp() throws IOException {
        response=mock(HttpServletResponse.class);
        request=mock(HttpServletRequest.class);
        multipartFile=mock(MultipartFile.class);

        URL resource = ExcelTest.class.getResource(WRITER_PATH);


        when(request.getParameter(HEADER_LANG)).thenReturn("zh_CN");
        when(request.getParameter("fileType")).thenReturn("a.xlsx");
        InputStream in = new FileInputStream(new File(ExcelTest.class.getResource(READER_PATH).getPath()));
        when(multipartFile.getInputStream()).thenReturn(in);

        ServletOutputStream outputStream=new ServletOutputStream() {

            FileOutputStream fileOutputStream = new FileOutputStream(new File(resource.getPath()));
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener listener) {

            }

            @Override
            public void write(int b) throws IOException {
                fileOutputStream.write(b);
            }
        };

        when(response.getOutputStream()).thenReturn(outputStream);
    }

    /**
     * 导出excel模板
     * @throws IOException
     */
    @Test
    public void excelExportTemplate() throws IOException {
        String lang=request.getHeader(HEADER_LANG);

        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter=new ExcelWriter(lang,workbook);

        excelWriter.createExcelTemplate(PersonModel.class);

        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);

        outputStream.close();
    }

    /**
     * 导出excel模板和数据
     * （导出的excel文件最终是在target文件夹下）
     * @throws IOException
     */
    @Test
    public void excelExportData() throws IOException {
        List<PersonModel> modelData = getModelData();

        String lang=request.getHeader(HEADER_LANG);

        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter=new ExcelWriter(lang,workbook);

        excelWriter.writeModelList(modelData,PersonModel.class);

        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);

        outputStream.close();
    }

    private List<PersonModel> getModelData(){
        List<PersonModel> models=new ArrayList<>();
        PersonModel personModel=new PersonModel();
        personModel.setPersonName("张四");
        personModel.setPersonAge(22);
        personModel.setPersonAddress("address");
        personModel.setPersonSalary(BigDecimal.valueOf(125.5));
        personModel.setBirth(Timestamp.valueOf(LocalDateTime.now()));
        models.add(personModel);

        return models;
    }


    /**
     * 读取上传文件
     * @throws IOException
     */
    @Test
    public void excelReadData() throws IOException {

        String lang=request.getHeader("HEADER_LANG");

        ExcelTypeEnum type;
        if (request.getParameter("fileType").indexOf(ExcelTypeEnum.XLSX.getSuffix()) > 0) {
            type = ExcelTypeEnum.XLSX;
        } else {
            type=ExcelTypeEnum.XLS;
        }

        InputStream inputStream = multipartFile.getInputStream();

        Workbook workbook = ExcelFactory.createWorkbook(inputStream, type);

        /**
         * excel解析错误处理器
         */
        MyExcelWrongHandler handler=new MyExcelWrongHandler();

        ExcelReader excelReader=new ExcelReader(lang,workbook,handler);

        List<PersonModel> models = excelReader.readModelList(PersonModel.class);

        System.out.println(models);
    }
}
