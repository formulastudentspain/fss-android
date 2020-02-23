package es.formulastudent.app.mvp.data.business.conecontrol.dto;

public class ConeControlStatsDTO {

    private Long carNumber;
    private Long offCourseNumber;
    private Long conesNumber;

    public Long getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Long carNumber) {
        this.carNumber = carNumber;
    }

    public Long getOffCourseNumber() {
        return offCourseNumber;
    }

    public void setOffCourseNumber(Long offCourseNumber) {
        this.offCourseNumber = offCourseNumber;
    }

    public Long getConesNumber() {
        return conesNumber;
    }

    public void setConesNumber(Long conesNumber) {
        this.conesNumber = conesNumber;
    }
}
