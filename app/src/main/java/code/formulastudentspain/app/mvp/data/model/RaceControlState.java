package code.formulastudentspain.app.mvp.data.model;

import java.util.List;

public interface RaceControlState {

    String getName();
    String getAcronym();
    List<String> getStates();
    int getColor();
    int getIcon();
}
