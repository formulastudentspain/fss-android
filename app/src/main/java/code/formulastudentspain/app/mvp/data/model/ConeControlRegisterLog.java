package code.formulastudentspain.app.mvp.data.model;

import java.util.Date;

public class ConeControlRegisterLog {

    private Date date;
    private Long cones;
    private Long offcourses;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCones() {
        return cones;
    }

    public void setCones(Long cones) {
        this.cones = cones;
    }

    public Long getOffcourses() {
        return offcourses;
    }

    public void setOffcourses(Long offcourses) {
        this.offcourses = offcourses;
    }
}