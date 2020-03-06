package es.formulastudent.app.mvp.data.business.statistics.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.dynamicevent.DynamicEventBO;
import es.formulastudent.app.mvp.data.business.statistics.StatisticsBO;
import es.formulastudent.app.mvp.data.business.statistics.dto.ExportStatisticsDTO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.EventRegister;
import es.formulastudent.app.mvp.data.model.EventType;
import es.formulastudent.app.mvp.data.model.PreScrutineeringRegister;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.Utils;

public class StatisticsBOImpl implements StatisticsBO {


    private DynamicEventBO dynamicEventBO;
    private TeamMemberBO teamMemberBO;
    private Context context;

    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);


    public StatisticsBOImpl(DynamicEventBO dynamicEventBO, TeamMemberBO teamMemberBO, Context context) {
        this.dynamicEventBO = dynamicEventBO;
        this.teamMemberBO = teamMemberBO;
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

                try {
                    Workbook wb = new HSSFWorkbook(is);
                    Sheet sheet = wb.getSheetAt(0);
                    wb.setSheetName(0, eventType.getActivityTitle());


                    //HEADERS


                    //TEAM NAME
                    Row row = sheet.createRow(4);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("TEAM NAME");

                    //DRIVER NAME
                    cell = row.createCell(1);
                    cell.setCellValue("DRIVER NAME");

                    //DATE
                    cell = row.createCell(2);
                    cell.setCellValue("DATE");

                    //DONE BY USERMAIL
                    cell = row.createCell(3);
                    cell.setCellValue("DONE BY");

                    if(!eventType.equals(EventType.BRIEFING)) {

                        //CAR TYPE
                        cell = row.createCell(4);
                        cell.setCellValue("CAR TYPE");

                        //CAR NUMBER
                        cell = row.createCell(5);
                        cell.setCellValue("CAR NUMBER");

                        //BRIEFING DONE
                        cell = row.createCell(6);
                        cell.setCellValue("BRIEFING DONE");


                        if (eventType.equals(EventType.PRE_SCRUTINEERING)) {
                            //EGRESS TIME
                            cell = row.createCell(7);
                            cell.setCellValue("EGRESS TIME");
                        }
                    }



                    /*
                        Test Data Values
                    */
                    int rowNum = 4;
                    int cellNum;

                    for (EventRegister register : listToExport) {

                        //Init values
                        cellNum = 0;
                        row = sheet.createRow(++rowNum);

                        //TEAM NAME
                        cell = row.createCell(cellNum);
                        cell.setCellValue(register.getTeam()==null ? "" : register.getTeam());

                        //DRIVER NAME
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(register.getUser()==null ? "" : register.getUser());

                        //DATE
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(register.getDate()==null ? "" : df.format(register.getDate()));

                        //DONE EMAIL BY
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(register.getDoneByUserMail()==null ? "" : register.getDoneByUserMail());


                        if(!eventType.equals(EventType.BRIEFING)) {

                            //CAR NUMBER
                            cell = row.createCell(++cellNum);
                            cell.setCellValue(register.getCarNumber() == null ? "" : register.getCarNumber().toString());

                            //BRIEFING DONE
                            cell = row.createCell(++cellNum);
                            cell.setCellValue(register.getBriefingDone() == null ? "" : register.getBriefingDone().toString());

                            //EGRESS TIME
                            if (eventType.equals(EventType.PRE_SCRUTINEERING)) {
                                cell = row.createCell(++cellNum);
                                String value = ((PreScrutineeringRegister) register).getTime() == null ? "" : Utils.chronoFormatter(((PreScrutineeringRegister) register).getTime());
                                cell.setCellValue(value);
                            }
                        }



                    }


                    //Get File Name
                    DateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String nameFile = "Export" + "_" + sdf.format(timestamp);

                    //Create Directory
                    String rootDirectoryName = Environment.getExternalStorageDirectory() + "";
                    File subDirectory = new File(rootDirectoryName, "FSS/" + eventType.getActivityTitle());
                    subDirectory.mkdirs();

                    //Create File
                    OutputStream stream = new FileOutputStream(rootDirectoryName + "/" + "FSS/" + eventType.getActivityTitle() + "/" + nameFile + ".xls");

                    wb.write(stream);
                    stream.close();
                    wb.close();


                    ExportStatisticsDTO exportStatisticsDTO = new ExportStatisticsDTO();
                    exportStatisticsDTO.setEventType(eventType);
                    exportStatisticsDTO.setExportDate(Calendar.getInstance().getTime());
                    exportStatisticsDTO.setFullFilePath(rootDirectoryName + "/FSS/" + eventType.getActivityTitle() + "/" + nameFile + ".xls");

                    responseDTOExport.setData(exportStatisticsDTO);

                    responseDTOExport.setInfo(R.string.statistics_info_exporting_dynamic_event);
                    callback.onSuccess(responseDTOExport);

                } catch (IOException e) {
                    responseDTOExport.setError(R.string.statistics_error_exporting_dynamic_event);
                    callback.onFailure(responseDTOExport);
                }

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                responseDTO.setError(R.string.statistics_error_exporting_dynamic_event);
                callback.onFailure(responseDTO);
            }
        });
    }

    @Override
    public void exportUsers(final BusinessCallback businessCallback) throws IOException {

        AssetManager mngr = context.getAssets();
        final InputStream is = mngr.open("template_export_fss.xls");


        teamMemberBO.retrieveTeamMembers(null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                ResponseDTO responseDTOExport = new ResponseDTO();

                List<TeamMember> listToExport = (List<TeamMember>) responseDTO.getData();

                try {
                    Workbook wb = new HSSFWorkbook(is);
                    Sheet sheet = wb.getSheetAt(0);
                    wb.setSheetName(0, "Users");

                    //HEADERS

                    //MAIL
                    Row row = sheet.createRow(4);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("MAIL");

                    //NAME
                    cell = row.createCell(1);
                    cell.setCellValue("NAME");

                    //ROLE
                    cell = row.createCell(2);
                    cell.setCellValue("ROLE");

                    //NFC TAG
                    cell = row.createCell(3);
                    cell.setCellValue("NFC TAG");

                    //TEAM
                    cell = row.createCell(4);
                    cell.setCellValue("TEAM");

                    //TEAM
                    cell = row.createCell(5);
                    cell.setCellValue("CAR NUMBER");


                    /*
                        Test Data Values
                    */
                    int rowNum = 4;
                    int cellNum;

                    for (TeamMember teamMember : listToExport) {

                        //Init values
                        cellNum = 0;
                        row = sheet.createRow(++rowNum);

                        //MAIL
                        cell = row.createCell(cellNum);
                        cell.setCellValue(teamMember.getMail() == null ? "" : teamMember.getMail());

                        //NAME
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(teamMember.getName() == null ? "" : teamMember.getName());

                        //ROLE
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(teamMember.getRole() == null ? "" : teamMember.getRole());

                        //NFC TAG
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(teamMember.getNFCTag() == null ? "" : teamMember.getNFCTag());

                        //TEAM
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(teamMember.getTeam() == null ? "" : teamMember.getTeam());

                        //CAR NUMBER
                        cell = row.createCell(++cellNum);
                        cell.setCellValue(teamMember.getTeam() == null ? "" : teamMember.getTeam());
                    }


                    //Get File Name
                    DateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String nameFile = "Export" + "_" + sdf.format(timestamp);

                    //Create Directory
                    String rootDirectoryName = Environment.getExternalStorageDirectory() + "";
                    File subDirectory = new File(rootDirectoryName, "FSS/USERS");
                    subDirectory.mkdirs();

                    //Create File
                    OutputStream stream = new FileOutputStream(rootDirectoryName + "/" + "FSS/USERS" + "/" + nameFile + ".xls");

                    wb.write(stream);
                    stream.close();
                    wb.close();

                    ExportStatisticsDTO exportStatisticsDTO = new ExportStatisticsDTO();
                    exportStatisticsDTO.setExportDate(Calendar.getInstance().getTime());
                    exportStatisticsDTO.setFullFilePath(rootDirectoryName + "/" + "FSS/USERS" + "/" + nameFile + ".xls");

                    responseDTOExport.setData(exportStatisticsDTO);

                    responseDTOExport.setInfo(R.string.statistics_info_exporting_users);
                    businessCallback.onSuccess(responseDTOExport);

                } catch (IOException e) {
                    responseDTOExport.setError(R.string.statistics_error_exporting_users);
                    businessCallback.onFailure(responseDTOExport);
                }

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                responseDTO.setError(R.string.statistics_error_exporting_users);
                businessCallback.onFailure(responseDTO);
            }
        });
    }

}
