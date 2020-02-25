package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.formulastudent.app.R;

public enum RaceControlAutocrossState implements Serializable, RaceControlState {

        NOT_AVAILABLE (
                "Not Available",
                "NA",
                new ArrayList<>(Collections.singletonList("RR1")),
                R.drawable.ic_not_available,
                R.color.md_grey_300
        ),

        RACING_ROUND_1 (
                "Racing Round 1",
                "RR1",
                new ArrayList<>(Collections.singletonList("FR1")),
                R.drawable.ic_racing,
                R.color.md_green_200
        ),

        FINISHED_ROUND_1 (
                "Finished Round 1",
                "FR1",
                new ArrayList<>(Collections.singletonList("RR2")),
                R.drawable.ic_finished,
                R.color.md_blue_100
        ),

        RACING_ROUND_2 (
                "Racing Round 2",
                "RR2",
                new ArrayList<>(Collections.singletonList("FR2")),
                R.drawable.ic_racing,
                R.color.md_green_200
        ),

        FINISHED_ROUND_2 (
                "Finished Round 2",
                "FR2",
                new ArrayList<>(Collections.singletonList("RR3")),
                R.drawable.ic_finished,
                R.color.md_blue_100
        ),

        RACING_ROUND_3 (
                "Racing Round 3",
                "RR3",
                new ArrayList<>(Collections.singletonList("FR3")),
                R.drawable.ic_racing,
                R.color.md_green_200
        ),

        FINISHED_ROUND_3 (
                "Finished Round 3",
                "FR3",
                new ArrayList<>(Collections.singletonList("RR4")),
                R.drawable.ic_finished,
                R.color.md_blue_100
        ),

        RACING_ROUND_4 (
                "Racing Round 1",
                "RR4",
                new ArrayList<>(Collections.singletonList("FR4")),
                R.drawable.ic_racing,
                R.color.md_green_200
        ),

        FINISHED_ROUND_4 (
                "Finished Round 4",
                "FR4",
                new ArrayList<>(Collections.<String>emptyList()),
                R.drawable.ic_scrutineering,
                R.color.md_blue_600
        ),

        DNF (
                "DNF",
                "DNF",
                new ArrayList<>(Collections.<String>emptyList()),
                R.drawable.ic_dnf,
                R.color.md_red_500
        );


        private final String name;
        private final String acronym;
        private final List<String> states;
        private final int color;
        private final int icon;


        RaceControlAutocrossState(String name, String acronym, List<String> states, int icon, int color) {
                this.name = name;
                this.acronym = acronym;
                this.states = states;
                this.icon = icon;
                this.color = color;
        }

        @Override
        public String getName() {
                return name;
        }
        @Override
        public String getAcronym() {
                return acronym;
        }
        @Override
        public List<String> getStates() {
                return states;
        }
        @Override
        public int getColor() {
                return color;
        }
        @Override
        public int getIcon() {
                return icon;
        }

        public RaceControlState getStateByAcronym(String acronym){

                if(acronym.equals(NOT_AVAILABLE.getAcronym())){
                        return NOT_AVAILABLE;

                }else if(acronym.equals(RACING_ROUND_1.getAcronym())){
                        return RACING_ROUND_1;

                }else if(acronym.equals(FINISHED_ROUND_1.getAcronym())){
                        return FINISHED_ROUND_1;

                }else if(acronym.equals(RACING_ROUND_2.getAcronym())){
                        return RACING_ROUND_2;

                }else if(acronym.equals(FINISHED_ROUND_2.getAcronym())) {
                        return FINISHED_ROUND_2;

                }else if(acronym.equals(RACING_ROUND_3.getAcronym())){
                        return RACING_ROUND_3;

                }else if(acronym.equals(FINISHED_ROUND_3.getAcronym())){
                        return FINISHED_ROUND_3;

                }else if(acronym.equals(RACING_ROUND_4.getAcronym())){
                        return RACING_ROUND_4;

                }else if(acronym.equals(FINISHED_ROUND_4.getAcronym())) {
                        return FINISHED_ROUND_4;
                }
                return null;
        }
}
