package es.formulastudent.app.mvp.view.activity.racecontrol.dialog;

import es.formulastudent.app.mvp.data.model.Team;

public class RaceControlTeamDTO {

    private Long carNumber;
    private String carName;
    private Boolean alreadyAdded;
    private Boolean selected;

    public RaceControlTeamDTO(){}

    public RaceControlTeamDTO(Team team, boolean alreadyAdded){
        this.alreadyAdded = alreadyAdded;
        this.carNumber = team.getCar().getNumber();
        this.carName = team.getName();
    }


    public Long getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Long carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Boolean getAlreadyAdded() {
        return alreadyAdded;
    }

    public void setAlreadyAdded(Boolean alreadyAdded) {
        this.alreadyAdded = alreadyAdded;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
