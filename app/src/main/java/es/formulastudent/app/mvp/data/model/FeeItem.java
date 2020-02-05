package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public enum FeeItem implements Serializable {

        TRANSPONDER_FEE_GIVEN (
                "Fee given",
                "The transponder fee has been given to the organization",
                false
        ),

        TRANSPONDER_GIVEN (
                "Transponder given",
                "The transponder has been given to the team",
                false
        ),

        TRANSPONDER_RETURNED (
                "Transponder returned",
                "The transponder has been returned to the organization",
                false
        ),

        TRANSPONDER_FEE_RETURNED (
                "Fee returned",
                "The transponder fee has been returned to the team",
                false
        ),

        ENERGY_METER_FEE_GIVEN (
                "Fee given",
                "The energy meter fee has been given to the organization",
                false
        ),

        ENERGY_METER_GIVEN (
                "Energy meter given",
                "The energy meter has been given to the team",
                false
        ),

        ENERGY_METER_RETURNED (
                "Energy meter returned",
                "The energy meter has been returned to the organization",
                false
        ),

        ENERGY_METER_FEE_RETURNED (
                "Fee returned",
                "The energy meter fee has been returned to the team",
                false
        );


        private final String name;
        private final String description;
        private Boolean value;


        FeeItem(String name, String description, Boolean value) {
                this.name = name;
                this.description = description;
                this.value = value;
        }


        public String getName() {
                return name;
        }

        public String getDescription() {
                return description;
        }

        public Boolean getValue() {
                return value;
        }

        public void setValue(Boolean value) {
                this.value = value;
        }

        public static List<FeeItem> getTransponderStates(){
                List<FeeItem> feeItems = new ArrayList<>();
                feeItems.add(TRANSPONDER_FEE_GIVEN);
                feeItems.add(TRANSPONDER_GIVEN);
                feeItems.add(TRANSPONDER_RETURNED);
                feeItems.add(TRANSPONDER_FEE_RETURNED);

                return feeItems;
        }

        public static List<FeeItem> getEnergyMeterStates(){
                List<FeeItem> feeItems = new ArrayList<>();
                feeItems.add(ENERGY_METER_FEE_GIVEN);
                feeItems.add(ENERGY_METER_GIVEN);
                feeItems.add(ENERGY_METER_RETURNED);
                feeItems.add(ENERGY_METER_FEE_RETURNED);

                return feeItems;
        }
}
