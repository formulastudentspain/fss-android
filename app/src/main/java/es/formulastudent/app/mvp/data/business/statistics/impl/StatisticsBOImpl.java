package es.formulastudent.app.mvp.data.business.statistics.impl;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.statistics.StatisticsBO;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;

public class StatisticsBOImpl implements StatisticsBO {


    private DynamicEventBO dynamicEventBO;
    private Context context;


    public StatisticsBOImpl(DynamicEventBO dynamicEventBO, Context context) {
        this.dynamicEventBO = dynamicEventBO;
        this.context = context;
    }

    @Override
    public void exportDynamicEvent(final EventType eventType, final BusinessCallback callback) throws IOException {

        AssetManager mngr = context.getAssets();
        final InputStream is = mngr.open("template_export_fss.xls");

        dynamicEventBO.retrieveRegisters(null, null, null, null, eventType, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

                ResponseDTO responseDTOExport = new ResponseDTO();

                List<EventRegister> listToExport = (List<EventRegister>) responseDTO.getData();

                try{
                    Workbook wb = new HSSFWorkbook(is);
                    Sheet sheet = wb.getSheetAt(0);
                    wb.setSheetName(0, eventType.getActivityTitle());

                }catch(IOException e){
                    responseDTOExport.getErrors().add("Mal");
                    callback.onFailure(responseDTOExport);
                }

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

            }
        });






    }
/*
    public String exportTestToExcelIntoDirectory(Test test, Context context, String subDirectoryName) throws Exception {
        AssetManager mngr = context.getAssets();
        InputStream is = mngr.open("template_export_test.xls");

        Workbook wb = new HSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, test.getName());

        //Data test
        String testTemplateName = TemplateBO.getInstance().getTemplate(test.getTemplateId()).getName();
        String testDate = test.getDate().toString();
        String testDriver = test.getDriver();
        String testVehicle = VehicleBO.getInstance().getVehicle(test.getVehicleId()).getName();
        String testDrivingMode = test.getDrivingMode();
        String testTyreSpecFront = test.getTyreSpecsFront();
        String testTyreSpecRear = test.getTyreSpecsRear();
        String testTyrePressureFront = test.getTyrePressureFront();
        String testTyrePressureRear = test.getTyrePressureRear();
        String testAxleLoadFront = test.getAxleLoadFront();
        String testAxleLoadRear = test.getAxleLoadRear();
        String testWeather = test.getWeather();
        String testAirTemperature = test.getAirTemperature();
        String testRemarks = test.getRemarks();
        Long testProjectId = VehicleBO.getInstance().getVehicle(test.getVehicleId()).getProjectId();
        String testProject = ProjectBO.getInstance().getProject(testProjectId).getName();

        Row row = sheet.getRow(0);
        //Template
        Cell cell = row.getCell(0);
        cell.setCellValue(testTemplateName);
        //Project
        cell = row.getCell(3);
        cell.setCellValue(testProject);

        row = sheet.getRow(1);
        //Vehicle
        cell = row.getCell(3);
        cell.setCellValue(testVehicle);
        //Driver
        cell = row.getCell(5);
        cell.setCellValue(testDriver);

        row = sheet.getRow(2);
        //Date
        cell = row.getCell(3);
        cell.setCellValue(testDate);
        //Weather
        cell = row.getCell(5);
        cell.setCellValue(testWeather);
        //Remarks
        cell = row.getCell(9);
        cell.setCellValue(testRemarks);

        row = sheet.getRow(3);
        //Driving mode
        cell = row.getCell(3);
        cell.setCellValue(testDrivingMode);
        //Air Temperature
        cell = row.getCell(5);
        cell.setCellValue(testAirTemperature);

        row = sheet.getRow(4);
        //Axle Front
        cell = row.getCell(3);
        cell.setCellValue(testAxleLoadFront);
        //Axle Rear
        cell = row.getCell(5);
        cell.setCellValue(testAxleLoadRear);

        row = sheet.getRow(5);
        //Tyre Spec Front
        cell = row.getCell(3);
        cell.setCellValue(testTyreSpecFront);
        //Tyre Spec Rear
        cell = row.getCell(5);
        cell.setCellValue(testTyreSpecRear);

        row = sheet.getRow(6);
        //Tyre Spec Front
        cell = row.getCell(3);
        cell.setCellValue(testTyrePressureFront);
        //Tyre Spec Rear
        cell = row.getCell(5);
        cell.setCellValue(testTyrePressureRear);


        int rowNum = 7;
        int cellNum = 0;

        TestData data = test.getData();
        for (Group group : data.getGroupList()) {
            row = sheet.createRow(++rowNum);
            cellNum = 0;
            cell = row.createCell(cellNum);
            cell.setCellValue("Group");
            cell = row.createCell(++cellNum);
            cell.setCellValue(group.getName());

            for (SubGroup subGroup : group.getSubGroupList()) {
                row = sheet.createRow(++rowNum);
                cellNum = 0;
                cell = row.createCell(cellNum);
                cell.setCellValue("Subgroup");
                cell = row.createCell(++cellNum);
                cell.setCellValue(subGroup.getName());

                for (Task task : subGroup.getTaskList()) {
                    row = sheet.createRow(++rowNum);
                    cellNum = 0;
                    cell = row.createCell(cellNum);
                    cell.setCellValue("Task");
                    cell = row.createCell(++cellNum);
                    cell.setCellValue(task.getDescription());
                    cellNum = 3;
                    cell = row.createCell(cellNum);
                    cell.setCellValue(task.getInfo1());
                    cellNum = 6;
                    cell = row.createCell(cellNum);
                    cell.setCellValue(task.getInfo2());
                    cellNum = 9;
                    cell = row.createCell(cellNum);
                    cell.setCellValue(task.getRating());
                    cell = row.createCell(++cellNum);
                    cell.setCellValue(task.getTendency1());
                    cell = row.createCell(++cellNum);
                    cell.setCellValue(task.getTendency2());
                    cell = row.createCell(++cellNum);
                    cell.setCellValue(task.getTendencyValue());
                    cell = row.createCell(++cellNum);
                    cell.setCellValue(task.getComments());
                }
            }
        }

        //Get File Name
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String nameFile = testProject+"_"+testVehicle+"_"+test.getName() + "_" + sdf.format(timestamp);

        //Create Directory
        String rootDirectoryName = Environment.getExternalStorageDirectory()+"";
        File subDirectory = new File(rootDirectoryName, subDirectoryName);
        subDirectory.mkdirs();

        //Create File
        OutputStream stream = new FileOutputStream(rootDirectoryName+"/"+subDirectoryName+"/"+nameFile+".xls");

        wb.write(stream);
        stream.close();
        wb.close();

        return rootDirectoryName+"/"+subDirectoryName+"/"+nameFile+".xls";
    }

    */


}
