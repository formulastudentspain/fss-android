package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.formulastudent.app.R;

public enum RaceControlEnduranceState implements Serializable {

        NOT_AVAILABLE (
                "Not Available",
                "NA",
                new ArrayList<>(Collections.singletonList("WA")),
                R.drawable.ic_not_available,
                R.color.md_white_1000
        ),

        WAITING_AREA (
                "Waiting Area",
                "WA",
                new ArrayList<>(Collections.singletonList("SCR")),
                R.drawable.ic_waiting_area,
                R.color.md_grey_300
        ),

        SCRUTINEERING (
                "Scrutineering",
                "SCR",
                new ArrayList<>(Arrays.asList("FIX","RR1D")),
                R.drawable.ic_scrutineering,
                R.color.md_orange_100
        ),

        FIXING (
                "Fixing",
                "FIX",
                new ArrayList<>(Collections.singletonList("SCR")),
                R.drawable.ic_fixing,
                R.color.md_orange_200
        ),

        READY_TO_RACE_1D (
                "Ready to Race 1D",
                "RR1D",
                new ArrayList<>(Collections.singletonList("R1D")),
                R.drawable.ic_ready_to_race,
                R.color.md_blue_100
        ),

        RACING_1D (
                "Racing 1D",
                "R1D",
                new ArrayList<>(Collections.singletonList("RR2D")),
                R.drawable.ic_racing,
                R.color.md_green_100
        ),

        READY_TO_RACE_2D (
                "Ready to Race 2D",
                "RR2D",
                new ArrayList<>(Collections.singletonList("R2D")),
                R.drawable.ic_ready_to_race,
                R.color.md_blue_100
        ),

        RACING_2D (
                "Racing 2D",
                "R2D",
                new ArrayList<>(Collections.singletonList("FIN")),
                R.drawable.ic_racing,
                R.color.md_green_100
        ),

        FINISHED (
                "Finished",
                "FIN",
                new ArrayList<>(Collections.<String>emptyList()),
                R.drawable.ic_finished,
                R.color.md_grey_500
        ),

        DNF (
                "DNF",
                "DNF",
                new ArrayList<>(Collections.<String>emptyList()),
                R.drawable.ic_dnf,
                R.color.md_red_500
        ),

        RUN_LATER (
                "Run Later",
                "RL",
                new ArrayList<>(Collections.<String>emptyList()),
                R.drawable.ic_run_later,
                R.color.md_red_500
        );


        private final String name;
        private final String acronym;
        private final List<String> states;
        private final int color;
        private final int icon;

        RaceControlEnduranceState(String name, String acronym, List<String> states, int icon, int color) {
                this.name = name;
                this.acronym = acronym;
                this.states = states;
                this.icon = icon;
                this.color = color;
        }


        public String getName() {
                return name;
        }

        public String getAcronym() {
                return acronym;
        }

        public List<String> getStates() {
                return states;
        }

        public int getColor() {
                return color;
        }

        public int getIcon() {
                return icon;
        }

        public static RaceControlEnduranceState getStateByAcronym(String acronym){

                if(acronym.equals(WAITING_AREA.getAcronym())){
                        return WAITING_AREA;

                }else if(acronym.equals(SCRUTINEERING.getAcronym())){
                        return SCRUTINEERING;

                }else if(acronym.equals(FIXING.getAcronym())){
                        return FIXING;

                }else if(acronym.equals(READY_TO_RACE_1D.getAcronym())){
                        return READY_TO_RACE_1D;

                }else if(acronym.equals(RACING_1D.getAcronym())){
                        return RACING_1D;

                }else if(acronym.equals(READY_TO_RACE_2D.getAcronym())){
                        return READY_TO_RACE_2D;

                }else if(acronym.equals(RACING_2D.getAcronym())) {
                        return RACING_2D;

                }else if(acronym.equals(FINISHED.getAcronym())) {
                        return FINISHED;

                }else if(acronym.equals(DNF.getAcronym())) {
                        return DNF;

                }else if(acronym.equals(RUN_LATER.getAcronym())) {
                        return RUN_LATER;
                }

                return null;
        }
}
