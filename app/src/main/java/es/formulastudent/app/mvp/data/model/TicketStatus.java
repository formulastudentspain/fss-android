package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

import es.formulastudent.app.R;

public enum TicketStatus implements Serializable {


    WAITING(
        "Waiting",
        R.color.md_grey_400
    ),

    IN_PROGRESS(
        "In Progress",
        R.color.md_orange_200
    ),

    CLOSED(
        "Closed",
        R.color.md_green_300
    ),

    DELETED(
        "Deleted",
        R.color.md_red_300
    );


    private final String name;
    private final int color;

    TicketStatus(String name, int color) {
        this.name = name;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public static TicketStatus getByName(String name) {
        if(TicketStatus.WAITING.getName().equals(name)){
            return WAITING;
        }else if(TicketStatus.IN_PROGRESS.getName().equals(name)){
            return IN_PROGRESS;
        }else if(TicketStatus.CLOSED.getName().equals(name)){
            return CLOSED;
        }else if(TicketStatus.DELETED.getName().equals(name)){
            return DELETED;
        }else{
            return null;
        }
    }
}
