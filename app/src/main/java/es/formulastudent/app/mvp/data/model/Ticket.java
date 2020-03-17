package es.formulastudent.app.mvp.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Ticket {

    //Database constants
    public static final String CAR_NUMBER = "carNumber";
    public static final String NUMBER = "number";
    public static final String STATUS = "status";
    public static final String AREA = "area";
    public static final String IS_WAITING = "isWaiting";
    public static final String IS_IN_PROGRESS = "isInProgress";
    public static final String IS_CLOSED = "isClosed";
    public static final String IS_DELETED = "isDeleted";
    public static final String WAITING_DATE = "waitingDate";
    public static final String IN_PROGRESS_DATE = "inProgressDate";
    public static final String CLOSED_DATE = "closedDate";
    public static final String DELETED_DATE = "deletedDate";


    private String id;
    private Long number;
    private Long carNumber;
    private TicketStatus status;
    private TicketArea area;
    private Date waitingDate;
    private Date inProgressDate;
    private Date closedDate;
    private Date deletedDate;
    private Boolean waiting;
    private Boolean inProgress;
    private Boolean closed;
    private Boolean deleted;
    private Long currentTurn; //Not persisted


    public Ticket(){
        this.id = UUID.randomUUID().toString();
        this.setWaiting(true);
    }


    public Ticket(DocumentSnapshot object) {
        this.id = object.getReference().getId();
        this.number = object.getLong(Ticket.NUMBER);
        this.carNumber = object.getLong(Ticket.CAR_NUMBER);
        this.status = TicketStatus.getByName(object.getString(Ticket.STATUS));
        this.area = TicketArea.getByName(object.getString(Ticket.AREA));
        this.waiting = object.getBoolean(Ticket.IS_WAITING);
        this.inProgress = object.getBoolean(Ticket.IS_IN_PROGRESS);
        this.closed = object.getBoolean(Ticket.IS_CLOSED);
        this.deleted = object.getBoolean(Ticket.IS_DELETED);
        this.waitingDate = object.getDate(Ticket.WAITING_DATE);
        this.inProgressDate = object.getDate(Ticket.IN_PROGRESS_DATE);
        this.closedDate = object.getDate(Ticket.CLOSED_DATE);
        this.deletedDate = object.getDate(Ticket.DELETED_DATE);
    }

    public Map<String, Object> toDocumentData() {

        Map<String, Object> docData = new HashMap<>();
        docData.put(Ticket.NUMBER, this.getNumber());
        docData.put(Ticket.CAR_NUMBER, this.getCarNumber());
        docData.put(Ticket.STATUS, this.getStatus().getName());
        docData.put(Ticket.AREA, this.getArea().getName());
        docData.put(Ticket.IS_WAITING, this.isWaiting());
        docData.put(Ticket.IS_IN_PROGRESS, this.isInProgress());
        docData.put(Ticket.IS_CLOSED, this.isClosed());
        docData.put(Ticket.IS_DELETED, this.isDeleted());
        docData.put(Ticket.WAITING_DATE, this.getWaitingDate());
        docData.put(Ticket.IN_PROGRESS_DATE, this.getInProgressDate());
        docData.put(Ticket.CLOSED_DATE, this.getClosedDate());
        docData.put(Ticket.DELETED_DATE, this.getDeletedDate());

        return docData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Long carNumber) {
        this.carNumber = carNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketArea getArea() {
        return area;
    }

    public void setArea(TicketArea area) {
        this.area = area;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        if(waiting){
            this.closed = false;
            this.deleted = false;
            this.inProgress = false;
            this.status = TicketStatus.WAITING;
            this.waitingDate = Calendar.getInstance().getTime();
        }
        this.waiting = waiting;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        if(inProgress){
            this.waiting = false;
            this.closed = false;
            this.deleted = false;
            this.status = TicketStatus.IN_PROGRESS;
            this.inProgressDate = Calendar.getInstance().getTime();
        }
        this.inProgress = inProgress;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        if(closed){
            this.waiting = false;
            this.inProgress = false;
            this.deleted = false;
            this.status = TicketStatus.CLOSED;
            this.closedDate = Calendar.getInstance().getTime();
        }
        this.closed = closed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        if(deleted){
            this.waiting = false;
            this.inProgress = false;
            this.closed = false;
            this.status = TicketStatus.DELETED;
            this.deletedDate = Calendar.getInstance().getTime();
        }
        this.deleted = deleted;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getWaitingDate() {
        return waitingDate;
    }

    public void setWaitingDate(Date waitingDate) {
        this.waitingDate = waitingDate;
    }

    public Date getInProgressDate() {
        return inProgressDate;
    }

    public void setInProgressDate(Date inProgressDate) {
        this.inProgressDate = inProgressDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Long getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Long currentTurn) {
        this.currentTurn = currentTurn;
    }
}