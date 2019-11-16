package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.formulastudent.app.R;

public enum Role implements Serializable {


        ADMINISTRATOR(
                "Administrator",
                new ArrayList<>(Arrays.asList("Administrator", "Staff", "Scrutineer", "Marshall", "Official Marshall", "Official Scrutineer", "Official Staff")),
                R.color.md_black_1000
        ),

        STAFF(
                "Staff",
                new ArrayList<>(Collections.<String>emptyList()),
                R.color.md_red_500
        ),

        SCRUTINEER(
                "Scrutineer",
                new ArrayList<>(Collections.<String>emptyList()),
                R.color.md_orange_800
        ),

        MARSHALL(
                "Marshall",
                new ArrayList<>(Collections.<String>emptyList()),
                R.color.md_blue_600
        ),

        OFFICIAL_MARSHALL(
                "Official Marshall",
                new ArrayList<>(Collections.singletonList("Marshall")),
                R.color.md_blue_600
        ),

        OFFICIAL_STAFF(
                "Official Staff",
                new ArrayList<>(Collections.singletonList("Staff")),
                R.color.md_red_500
        ),

        OFFICIAL_SCRUTINEER(
                "Official Scrutineer",
                new ArrayList<>(Collections.singletonList("Scrutineer")),
                R.color.md_orange_800
        );

        private final String name;
        private final List<String> managedRoles;
        private final int color;


        Role(String name, List<String> managedRoles, int color) {
                this.name = name;
                this.managedRoles = managedRoles;
                this.color = color;
        }

        public static Role getRoleByName(String name){

                if(ADMINISTRATOR.getName().equals(name)){
                        return  ADMINISTRATOR;

                }else if(SCRUTINEER.getName().equals(name)){
                        return  SCRUTINEER;

                }else if(MARSHALL.getName().equals(name)){
                        return  MARSHALL;

                }else if(STAFF.getName().equals(name)){
                        return STAFF;

                }else if(OFFICIAL_SCRUTINEER.getName().equals(name)){
                        return OFFICIAL_SCRUTINEER;

                }else if(OFFICIAL_MARSHALL.getName().equals(name)){
                        return OFFICIAL_MARSHALL;

                }else if(OFFICIAL_STAFF.getName().equals(name)){
                        return OFFICIAL_STAFF;
                }

                return null;

        }


        public String getName() {
                return name;
        }

        public List<String> getManagedRoles() {
                return managedRoles;
        }

        public int getColor() {
                return color;
        }


}
