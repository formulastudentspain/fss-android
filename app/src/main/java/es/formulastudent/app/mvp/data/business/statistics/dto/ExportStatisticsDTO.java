package es.formulastudent.app.mvp.data.business.statistics.dto;

import java.util.Date;

import es.formulastudent.app.mvp.data.model.EventType;

public class ExportStatisticsDTO {

    private EventType eventType;
    private Date exportDate;
    private String fullFilePath;
    private String description;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public String getFullFilePath() {
        return fullFilePath;
    }

    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
