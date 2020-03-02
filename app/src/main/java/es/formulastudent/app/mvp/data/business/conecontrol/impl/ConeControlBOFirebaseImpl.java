package es.formulastudent.app.mvp.data.business.conecontrol.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.conecontrol.ConeControlBO;
import es.formulastudent.app.mvp.data.business.statistics.dto.ExportStatisticsDTO;
import es.formulastudent.app.mvp.data.model.ConeControlEvent;
import es.formulastudent.app.mvp.data.model.ConeControlRegister;
import es.formulastudent.app.mvp.data.model.RaceControlAutocrossState;
import es.formulastudent.app.mvp.data.model.RaceControlEnduranceState;
import es.formulastudent.app.mvp.data.model.RaceControlState;

import static es.formulastudent.app.mvp.data.model.RaceControlEnduranceState.RACING_1D;
import static es.formulastudent.app.mvp.data.model.RaceControlEnduranceState.RACING_2D;

public class ConeControlBOFirebaseImpl implements ConeControlBO {

    private FirebaseFirestore firebaseFirestore;
    private Context context;

    public ConeControlBOFirebaseImpl(FirebaseFirestore firebaseFirestore, Context context) {
        this.firebaseFirestore = firebaseFirestore;
        this.context = context;
    }

    @Override
    public ListenerRegistration getConeControlRegistersRealTime(ConeControlEvent event, final Map<String, Object> filters, final BusinessCallback callback) {


        //Event type
        Query query = firebaseFirestore.collection(event.getFirebaseTable());

        if(ConeControlEvent.ENDURANCE.equals(event)){
            //Race Type filter (electric, combustion and final)
            query = query.whereEqualTo(ConeControlRegister.RACE_ROUND, filters.get("round"));
        }

        //Sector
        if(ConeControlEvent.ENDURANCE.equals(event) ||ConeControlEvent.AUTOCROSS.equals(event)){
            query = query.whereEqualTo(ConeControlRegister.SECTOR_NUMBER, filters.get("sector"));
        }


        //Only registers that are racing
        query = query.whereEqualTo(ConeControlRegister.IS_RACING, true);


        ListenerRegistration registration = query.orderBy(ConeControlRegister.CAR_NUMBER, Query.Direction.ASCENDING)
                .addSnapshotListener((value, e) -> {

                    //Response object
                    ResponseDTO responseDTO = new ResponseDTO();

                    if (e != null) {
                        responseDTO.setError(R.string.cc_realtime_error_retrieving_message);
                        callback.onFailure(responseDTO);
                        return;
                    }

                    List<ConeControlRegister> result = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        result.add(new ConeControlRegister(doc));
                    }

                    responseDTO.setData(result);
                    callback.onSuccess(responseDTO);
                });

        return registration;
    }

    @Override
    public void createConeControlForAllSectors(ConeControlEvent event,  Long carNumber, String flagURL, String raceRound, int numberOfSectors, BusinessCallback callback) {

        final ResponseDTO responseDTO = new ResponseDTO();

        //Create registers
        List<ConeControlRegister> registerList = new ArrayList<>();
        if(ConeControlEvent.ENDURANCE.equals(event)){
            registerList = this.createEnduranceConeControlRegisters(numberOfSectors, carNumber, flagURL, raceRound);

        }else if(ConeControlEvent.AUTOCROSS.equals(event)){
            registerList = this.createAutocrossConeControlRegisters(numberOfSectors, carNumber, flagURL);

        }else if(ConeControlEvent.SKIDPAD.equals(event)){
            registerList = this.createSkidPadConeControlRegisters(carNumber, flagURL);
        }

        WriteBatch batch = firebaseFirestore.batch();
        for(ConeControlRegister register: registerList){
            DocumentReference docRef = firebaseFirestore
                    .collection(event.getFirebaseTable())
                    .document(register.getId());
            batch.set(docRef, register.toObjectData());
        }

        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.cone_control_create_success);
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.cone_control_create_error);
                    callback.onFailure(responseDTO);
                });

    }

    private List<ConeControlRegister> createEnduranceConeControlRegisters(int numberOfSectors, Long carNumber, String flagURL, String raceRound){

        List<ConeControlRegister> registerList = new ArrayList<>();
        for(int i=1; i<=numberOfSectors; i++){
            ConeControlRegister register = new ConeControlRegister();
            register.setCarNumber(carNumber);
            register.setFlagURL(flagURL);
            register.setIsRacing(false);
            register.setOffCourses(0L);
            register.setSectorNumber((long)i);
            register.setTrafficCones(0L);
            register.setRaceRound(raceRound);
            registerList.add(register);
        }

        return registerList;
    }

    private List<ConeControlRegister> createAutocrossConeControlRegisters(int numberOfSectors, Long carNumber, String flagURL){
       List<ConeControlRegister> registerList = new ArrayList<>();

       for(int raceRound=1; raceRound<=4; raceRound++){
           for(int i=1; i<=numberOfSectors; i++){
               ConeControlRegister register = new ConeControlRegister();
               register.setCarNumber(carNumber);
               register.setFlagURL(flagURL);
               register.setIsRacing(false);
               register.setOffCourses(0L);
               register.setSectorNumber((long)i);
               register.setTrafficCones(0L);
               register.setRaceRound(String.valueOf(raceRound));
               registerList.add(register);
           }
       }

        return registerList;
    }

    private List<ConeControlRegister> createSkidPadConeControlRegisters(Long carNumber, String flagURL) {
        List<ConeControlRegister> registerList = new ArrayList<>();
        for(int raceRound=1; raceRound<=4; raceRound++){
            ConeControlRegister register = new ConeControlRegister();
            register.setCarNumber(carNumber);
            register.setFlagURL(flagURL);
            register.setIsRacing(false);
            register.setOffCourses(0L);
            register.setSectorNumber(null);
            register.setTrafficCones(0L);
            register.setRaceRound(String.valueOf(raceRound));
            registerList.add(register);
        }

        return registerList;
    }

    @Override
    public void updateConeControlRegister(ConeControlEvent event, ConeControlRegister register, BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();
        final DocumentReference registerReference = firebaseFirestore.collection(event.getFirebaseTable()).document(register.getId());

        registerReference.update(register.toObjectData())

                .addOnSuccessListener(aVoid -> {
                    responseDTO.setInfo(R.string.teams_info_update_message);//FIXME
                    callback.onSuccess(responseDTO);
                })
                .addOnFailureListener(e -> {
                    responseDTO.setError(R.string.teams_error_update_message);//FIXME
                    callback.onFailure(responseDTO);
                });
    }


    @Override
    public void enableOrDisableConeControlRegistersByTeam(ConeControlEvent ccEvent, Long carNumber, RaceControlState state, BusinessCallback callback){

        if(ConeControlEvent.AUTOCROSS.equals(ccEvent) || ConeControlEvent.SKIDPAD.equals(ccEvent)){
            this.enableOrDisableConeControlRegistersByTeam(ccEvent, carNumber, (RaceControlAutocrossState) state, callback);

        }else if(ConeControlEvent.ENDURANCE.equals(ccEvent)){
            this.enableOrDisableConeControlRegistersByTeam(ccEvent, carNumber, (RaceControlEnduranceState) state, callback);
        }

    }


    /**
     * Update cones for Endurance
     * @param ccEvent
     * @param carNumber
     * @param state
     * @param callback
     */
    private void enableOrDisableConeControlRegistersByTeam(ConeControlEvent ccEvent, Long carNumber, RaceControlEnduranceState state, BusinessCallback callback){
        final ResponseDTO responseDTO = new ResponseDTO();

        boolean enable = false;
        if(RACING_1D.equals(state)
            || RACING_2D.equals(state)){
            enable = true;
        }

        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        boolean finalEnable = enable;
        firebaseFirestore.collection(ccEvent.getFirebaseTable())
                .whereEqualTo(ConeControlRegister.CAR_NUMBER, carNumber)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        DocumentReference ref = document.getReference();
                        batch.update(ref, ConeControlRegister.IS_RACING, finalEnable);
                    }

                    // Commit the batch
                    batch.commit().addOnSuccessListener(task -> {
                        callback.onSuccess(responseDTO);

                    }).addOnFailureListener(task -> {
                        callback.onFailure(responseDTO);
                    });

                }).addOnFailureListener(e -> {
            responseDTO.setError(R.string.dynamic_event_message_error_retrieving_registers);
            callback.onFailure(responseDTO);
        });
    }

    /**
     * Update cones for Autocross
     * @param ccEvent
     * @param carNumber
     * @param state
     * @param callback
     */
    private void enableOrDisableConeControlRegistersByTeam(ConeControlEvent ccEvent, Long carNumber, RaceControlAutocrossState state, BusinessCallback callback){
        final ResponseDTO responseDTO = new ResponseDTO();

        String roundToEnable = "";
        if(RaceControlAutocrossState.RACING_ROUND_1.equals(state)){
            roundToEnable = "1";
        }else if(RaceControlAutocrossState.RACING_ROUND_2.equals(state)){
            roundToEnable = "2";
        }else if(RaceControlAutocrossState.RACING_ROUND_3.equals(state)){
            roundToEnable = "3";
        }else if(RaceControlAutocrossState.RACING_ROUND_4.equals(state)){
            roundToEnable = "4";
        }

        // Get a new write batch
        WriteBatch batch = firebaseFirestore.batch();

        String finalRoundToEnable = roundToEnable;
        firebaseFirestore.collection(ccEvent.getFirebaseTable())
                .whereEqualTo(ConeControlRegister.CAR_NUMBER, carNumber)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        DocumentReference ref = document.getReference();

                        boolean enableCone = false;
                        ConeControlRegister register = new ConeControlRegister(document);
                        if(finalRoundToEnable.equals(register.getRaceRound())){
                            enableCone = true;
                        }

                        batch.update(ref, ConeControlRegister.IS_RACING, enableCone);
                    }

                    // Commit the batch
                    batch.commit().addOnSuccessListener(task -> {
                        callback.onSuccess(responseDTO);

                    }).addOnFailureListener(task -> {
                        callback.onFailure(responseDTO);
                    });

                }).addOnFailureListener(e -> {
            responseDTO.setError(R.string.dynamic_event_message_error_retrieving_registers);
            callback.onFailure(responseDTO);
        });
    }




    @Override
    public void getConeControlRegistersByRaceRound(ConeControlEvent event, String raceRound, BusinessCallback callback) {
        final ResponseDTO responseDTO = new ResponseDTO();

        Query query = firebaseFirestore.collection(event.getFirebaseTable());

        if(ConeControlEvent.ENDURANCE.equals(event) && raceRound != null){
            query = query.whereEqualTo(ConeControlRegister.RACE_ROUND, raceRound);
        }

        query.orderBy(ConeControlRegister.CAR_NUMBER, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    //Add results to list
                    List<ConeControlRegister> result = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        ConeControlRegister register = new ConeControlRegister(document);
                        result.add(register);
                    }
                    responseDTO.setData(result);
                    //TODO
                    callback.onSuccess(responseDTO);

                }).addOnFailureListener(e -> {
                    //TODO
                    callback.onFailure(responseDTO);
                });
    }



    @Override
    public void exportConesToExcel(ConeControlEvent event, BusinessCallback callback) throws IOException {
        ResponseDTO result = new ResponseDTO();
        AssetManager mngr = context.getAssets();
        final InputStream is = mngr.open("template_export_fss.xls");

        this.getConeControlRegistersByRaceRound(event, null, new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {
                List<ConeControlRegister> listToExport = (List<ConeControlRegister>) responseDTO.getData();

                int columnNum = 0;

                try {

                    Workbook wb = new HSSFWorkbook(is);
                    Sheet sheet = wb.getSheetAt(0);
                    wb.setSheetName(0, event.getName() + " cones");


                    // ** HEADERS ** //

                    //Car number
                    Row row = sheet.createRow(4);
                    Cell cell = row.createCell(columnNum);
                    cell.setCellValue("Car");

                    //Round
                    cell = row.createCell(++columnNum);
                    cell.setCellValue("Round");


                    //Sector
                    if (ConeControlEvent.ENDURANCE.equals(event)
                            || ConeControlEvent.AUTOCROSS.equals(event)) {
                        cell = row.createCell(++columnNum);
                        cell.setCellValue("Sector");
                    }

                    //Cones
                    cell = row.createCell(++columnNum);
                    cell.setCellValue("Cones");

                    //OffCourses
                    cell = row.createCell(++columnNum);
                    cell.setCellValue("Off Courses");


                    /*
                        Test Data Values
                    */
                    int rowNum = 5;

                    for (ConeControlRegister register : listToExport) {

                        //Init values
                        columnNum = 0;
                        row = sheet.createRow(rowNum);

                        //Car number
                        cell = row.createCell(columnNum);
                        cell.setCellValue(register.getCarNumber());

                        //Round
                        cell = row.createCell(++columnNum);
                        cell.setCellValue(register.getRaceRound());


                        //Sector
                        if (ConeControlEvent.ENDURANCE.equals(event)
                                || ConeControlEvent.AUTOCROSS.equals(event)) {
                            cell = row.createCell(++columnNum);
                            cell.setCellValue(register.getSectorNumber());
                        }

                        //Cones
                        cell = row.createCell(++columnNum);
                        cell.setCellValue(register.getTrafficCones());

                        //OffCourses
                        cell = row.createCell(++columnNum);
                        cell.setCellValue(register.getOffCourses());

                        rowNum++;
                    }

                    //Get File Name
                    DateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String nameFile = "Cones" + "_" + sdf.format(timestamp);

                    //Create Directory
                    String rootDirectoryName = Environment.getExternalStorageDirectory() + "";
                    File subDirectory = new File(rootDirectoryName, "FSS/" + event.getName());
                    subDirectory.mkdirs();

                    //Create File
                    OutputStream stream = new FileOutputStream(rootDirectoryName + "/" + "FSS/" + event.getName() + "/" + nameFile + ".xls");

                    wb.write(stream);
                    stream.close();
                    wb.close();


                    ExportStatisticsDTO exportStatisticsDTO = new ExportStatisticsDTO();
                    exportStatisticsDTO.setExportDate(Calendar.getInstance().getTime());
                    exportStatisticsDTO.setFullFilePath(rootDirectoryName + "/FSS/" + event.getName() + "/" + nameFile + ".xls");
                    exportStatisticsDTO.setDescription(event.getName() + " Cones");

                    result.setData(exportStatisticsDTO);

                    callback.onSuccess(result);

                }catch (IOException e){
                    //TODO
                }
            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {
                //TODO
            }
        });
    }
}