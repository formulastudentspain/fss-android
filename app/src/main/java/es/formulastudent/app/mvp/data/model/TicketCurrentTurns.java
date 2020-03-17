package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class TicketCurrentTurns implements Parameter{

    //Key
    public static final String KEY = "TICKET_CURRENT_TURNS";

    //Database constants
    public static final String PRE_SCRUTINEERING = "preScrutineering";
    public static final String ACCUMULATION_INSPECTION = "accumulationInspection";
    public static final String ELECTRICAL_INSPECTION = "electricalInspection";
    public static final String MECHANICAL_INSPECTION = "mechanicalInspection";
    public static final String TILT_TABLE_TEST = "tiltTableTest";
    public static final String NOISE_TEST = "noiseTest";
    public static final String RAIN_TEST = "rainTest";
    public static final String BRAKE_TEST = "brakeTest";


    private String id;
    private Long preScrutinering;
    private Long accumulationInspection;
    private Long electricalInspection;
    private Long mechanicalInspection;
    private Long tiltTableTest;
    private Long noiseTest;
    private Long rainTest;
    private Long brakeTest;


    public TicketCurrentTurns(DocumentSnapshot object) {
        this.id = object.getReference().getId();
        this.preScrutinering = object.getLong(TicketCurrentTurns.PRE_SCRUTINEERING);
        this.accumulationInspection = object.getLong(TicketCurrentTurns.ACCUMULATION_INSPECTION);
        this.electricalInspection = object.getLong(TicketCurrentTurns.ELECTRICAL_INSPECTION);
        this.mechanicalInspection = object.getLong(TicketCurrentTurns.MECHANICAL_INSPECTION);
        this.tiltTableTest = object.getLong(TicketCurrentTurns.TILT_TABLE_TEST);
        this.noiseTest = object.getLong(TicketCurrentTurns.NOISE_TEST);
        this.rainTest = object.getLong(TicketCurrentTurns.RAIN_TEST);
        this.brakeTest = object.getLong(TicketCurrentTurns.BRAKE_TEST);
    }

    @Override
    public TicketCurrentTurns fromDocumentSnapshot(DocumentSnapshot object){
        return new TicketCurrentTurns(object);
    }

    @Override
    public Map<String, Object> toDocumentData() {

        Map<String, Object> docData = new HashMap<>();
        docData.put(TicketCurrentTurns.PRE_SCRUTINEERING, this.getPreScrutinering());
        docData.put(TicketCurrentTurns.ACCUMULATION_INSPECTION, this.getAccumulationInspection());
        docData.put(TicketCurrentTurns.ELECTRICAL_INSPECTION, this.getElectricalInspection());
        docData.put(TicketCurrentTurns.MECHANICAL_INSPECTION, this.getMechanicalInspection());
        docData.put(TicketCurrentTurns.TILT_TABLE_TEST, this.getTiltTableTest());
        docData.put(TicketCurrentTurns.NOISE_TEST, this.getNoiseTest());
        docData.put(TicketCurrentTurns.RAIN_TEST, this.getRainTest());
        docData.put(TicketCurrentTurns.BRAKE_TEST, this.getBrakeTest());

        return docData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPreScrutinering() {
        return preScrutinering;
    }

    public void setPreScrutinering(Long preScrutinering) {
        this.preScrutinering = preScrutinering;
    }

    public Long getAccumulationInspection() {
        return accumulationInspection;
    }

    public void setAccumulationInspection(Long accumulationInspection) {
        this.accumulationInspection = accumulationInspection;
    }

    public Long getElectricalInspection() {
        return electricalInspection;
    }

    public void setElectricalInspection(Long electricalInspection) {
        this.electricalInspection = electricalInspection;
    }

    public Long getMechanicalInspection() {
        return mechanicalInspection;
    }

    public void setMechanicalInspection(Long mechanicalInspection) {
        this.mechanicalInspection = mechanicalInspection;
    }

    public Long getTiltTableTest() {
        return tiltTableTest;
    }

    public void setTiltTableTest(Long tiltTableTest) {
        this.tiltTableTest = tiltTableTest;
    }

    public Long getNoiseTest() {
        return noiseTest;
    }

    public void setNoiseTest(Long noiseTest) {
        this.noiseTest = noiseTest;
    }

    public Long getRainTest() {
        return rainTest;
    }

    public void setRainTest(Long rainTest) {
        this.rainTest = rainTest;
    }

    public Long getBrakeTest() {
        return brakeTest;
    }

    public void setBrakeTest(Long brakeTest) {
        this.brakeTest = brakeTest;
    }
}