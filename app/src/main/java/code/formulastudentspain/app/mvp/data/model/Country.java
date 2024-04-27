package code.formulastudentspain.app.mvp.data.model;

import java.io.Serializable;

public enum Country implements Serializable {

        UNITED_KINGDOM (
                "United-Kingdom",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Funited-kingdom.png?alt=media&token=5f2c0536-1953-4610-9736-a81dfd195d8d"
        ),
        AUSTRIA (
                "Austria",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Faustria.png?alt=media&token=309bfc45-2c23-4514-a20a-cf073f6bea28"
        ),
        PORTUGAL (
                "Portugal",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fportugal.png?alt=media&token=1becf0f8-7356-4e56-bace-344d456a0e1b"
        ),
        SLOVAKIA (
                "Slovakia",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fslovakia.png?alt=media&token=b5e4bc85-702c-4ec4-949e-e165b17cb177"
        ),
        ITALY (
                "Italy",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fitaly.png?alt=media&token=912a79be-df75-4d8b-ae9c-811b28f107e0"
        ),
        ICELAND (
                "Iceland",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Ficeland.png?alt=media&token=6572eb8c-3012-4a52-aaa9-b2c2af728b27"
        ),
        POLAND (
                "Poland",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fpoland.png?alt=media&token=e37babb1-a8eb-43f3-a6b7-ed0ae05d053d"
        ),
        NETHERLANDS (
                "Netherlands",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fnetherlands.png?alt=media&token=77b26671-f171-4b42-9dcb-7ce939bfb5a9"
        ),
        SWITZERLAND (
                "Switzerland",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fswitzerland.png?alt=media&token=a870f54a-d345-4ccd-808d-c1e6c8d9a253"
        ),
        FRANCE (
                "France",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Ffrance.png?alt=media&token=3abbb3e0-67ee-4f75-ac56-c24165637760"
        ),
        CANADA (
                "Canada",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fcanada.png?alt=media&token=7e45fcf0-c89c-467d-8469-bee84483a0f4"
        ),
        GERMANY (
                "Germany",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fgermany.png?alt=media&token=b5f4b284-9b68-4cc8-80e4-8804108501be"
        ),
        SPAIN (
                "Spain",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fspain.png?alt=media&token=53979ddd-e51e-4ab7-ab3a-fec4ade07c48"
        ),
        SLOVENIA (
                "Slovenia",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fslovenia.png?alt=media&token=049227b4-9063-40c9-a776-785e64317320"
        ),
        SWEDEN (
                "Sweden",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fsweden.png?alt=media&token=5fcee608-55d2-41ad-8b58-f3757f7e712d"
        ),
        UNITED_STATES (
                "United-States-of-America",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Funited-states.png?alt=media&token=c1b20388-0745-44ad-97e8-051c73925af7"
        ),
        CZECH_REPUBLIC (
                "Czech-Republic",
                "https://firebasestorage.googleapis.com/v0/b/fss-app.appspot.com/o/country_flags%2Fczech-republic.png?alt=media&token=d54efb01-1778-4b2c-a721-c40ba2d43ccb"
        );

        private final String name;
        private final String flagURL;


        Country(String name, String flagURL) {
                this.name = name;
                this.flagURL = flagURL;
        }


        public static Country getByName(String name){

                if(name.equals(UNITED_KINGDOM.getName())){
                        return UNITED_KINGDOM;

                }else if(name.equals(AUSTRIA.getName())){
                        return AUSTRIA;

                }else if(name.equals(PORTUGAL.getName())){
                        return PORTUGAL;

                }else if(name.equals(SLOVAKIA.getName())){
                        return SLOVAKIA;

                }else if(name.equals(ITALY.getName())){
                        return ITALY;

                }else if(name.equals(ICELAND.getName())){
                        return ICELAND;

                }else if(name.equals(POLAND.getName())){
                        return POLAND;

                }else if(name.equals(NETHERLANDS.getName())){
                        return NETHERLANDS;

                }else if(name.equals(SWITZERLAND.getName())){
                        return SWITZERLAND;

                }else if(name.equals(FRANCE.getName())){
                        return FRANCE;

                }else if(name.equals(CANADA.getName())){
                        return CANADA;

                }else if(name.equals(GERMANY.getName())){
                        return GERMANY;

                }else if(name.equals(SPAIN.getName())){
                        return SPAIN;

                }else if(name.equals(SLOVENIA.getName())){
                        return SLOVENIA;

                }else if(name.equals(UNITED_STATES.getName())){
                        return UNITED_STATES;

                }else if(name.equals(CZECH_REPUBLIC.getName())){
                        return CZECH_REPUBLIC;

                }else if(name.equals(SWEDEN.getName())){
                        return SWEDEN;
                }
                return SPAIN;
        }

        public String getName() {
                return name;
        }

        public String getFlagURL() {
                return flagURL;
        }

}
