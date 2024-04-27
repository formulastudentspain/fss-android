package code.formulastudentspain.app.mvp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum TeamMemberRole implements Serializable, Role {

    DRIVER("Driver");

    private final String name;

    TeamMemberRole(String name) {
        this.name = name;
    }

    public static List<Role> getAll(){
        List<Role> list = new ArrayList<>();
        list.add(DRIVER);

        return list;
    }

    @Override
    public String getName() {
        return name;
    }
}
