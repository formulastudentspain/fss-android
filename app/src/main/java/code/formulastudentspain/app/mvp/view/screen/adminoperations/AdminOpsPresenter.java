package code.formulastudentspain.app.mvp.view.screen.adminoperations;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.formulastudentspain.app.R;
import code.formulastudentspain.app.mvp.data.business.DataConsumer;
import code.formulastudentspain.app.mvp.data.business.team.TeamBO;
import code.formulastudentspain.app.mvp.data.business.teammember.TeamMemberBO;
import code.formulastudentspain.app.mvp.data.model.Car;
import code.formulastudentspain.app.mvp.data.model.Country;
import code.formulastudentspain.app.mvp.data.model.Team;
import code.formulastudentspain.app.mvp.data.model.TeamMember;

public class AdminOpsPresenter extends DataConsumer {

    //Dependencies
    private AdminOpsPresenter.View view;
    private Context context;
    private TeamBO teamBO;
    private TeamMemberBO teamMemberBO;


    public AdminOpsPresenter(AdminOpsPresenter.View view, Context context, TeamBO teamBO, TeamMemberBO teamMemberBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
        this.teamMemberBO = teamMemberBO;
    }

    public void deleteAllDrivers() {
        teamMemberBO.deleteAllTeamMembers(response -> {
            //TODO
        }, errorMessage -> {
            //TODO
        });
    }

    public void deleteAllTeams() {
        teamBO.deleteAllTeams(response -> {
            //TODO
        }, errorMessage -> {
            //TODO
        });
    }


    public void importTeamsAndDrivers(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2000);

        final Map<String, Team> teams = new HashMap<>();
        final Map<String, Team> persistedTeams = new HashMap<>();

        try {

            AssetManager mngr = context.getAssets();
            final InputStream is = mngr.open("fss_info.xls");

            Workbook wb = new HSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);


            //Get all teams and information
            int index = 1;
            while(sheet.getRow(index) != null){


                Row row = sheet.getRow(index);
                Cell cellTeamName = row.getCell(0);
                String teamName = cellTeamName.getStringCellValue();

                if(!teams.containsKey(teamName)){
                    Team team = new Team();
                    Car car = new Car();

                    //Team name
                    team.setName(teamName);

                    //Scrutineering values
                    team.setScrutineeringPS(false);
                    team.setScrutineeringAI(false);
                    team.setScrutineeringEI(false);
                    team.setScrutineeringMI(false);
                    team.setScrutineeringTTT(false);
                    team.setScrutineeringNT(false);
                    team.setScrutineeringRT(false);
                    team.setScrutineeringBT(false);
                    
                    //Fee status
                    team.setTransponderFeeGiven(false);
                    team.setTransponderFeeReturned(false);
                    team.setTransponderItemGiven(false);
                    team.setTransponderItemReturned(false);
                    team.setEnergyMeterFeeGiven(false);
                    team.setEnergyMeterFeeReturned(false);
                    team.setEnergyMeterItemGiven(false);
                    team.setEnergyMeterItemReturned(false);


                    //Car type
                    Cell cellCarType = row.getCell(1);
                    String carType = cellCarType.getStringCellValue();
                    if(carType.equalsIgnoreCase("E")){
                        car.setType(Car.CAR_TYPE_ELECTRIC);
                    }else if(carType.equalsIgnoreCase("C")){
                        car.setType(Car.CAR_TYPE_COMBUSTION);
                    }else if(carType.equalsIgnoreCase("DE")){
                        car.setType(Car.CAR_TYPE_AUTONOMOUS_ELECTRIC);
                    }else if(carType.equalsIgnoreCase("DC")){
                        car.setType(Car.CAR_TYPE_AUTONOMOUS_COMBUSTION);
                    }


                    //Car number
                    Cell cellCarNumber = row.getCell(5);
                    Double carNumber = cellCarNumber.getNumericCellValue();
                    car.setNumber(carNumber.longValue());

                    //Country
                    Cell cellCountry = row.getCell(6);
                    String country = cellCountry.getStringCellValue().trim();
                    team.setCountry(Country.getByName(country));

                    team.setCar(car);

                    teams.put(teamName, team);
                }
                index ++;
            }

            //Save all teams
            for(String teamKey: teams.keySet()){
                final Team team = teams.get(teamKey);
                teamBO.createTeam(team, response -> {
                    persistedTeams.put(team.getName(), team);
                    if(persistedTeams.size() == teams.size()){
                        createDrivers(persistedTeams);
                    }
                }, errorMessage -> {
                    //TODO
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createDrivers(Map<String, Team> teams) {
        List<TeamMember> teamMembers = new ArrayList<>();

        try {
            AssetManager mngr = context.getAssets();
            final InputStream is = mngr.open("fss_info.xls");

            Workbook wb = new HSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);

            //Get all teams and information
            int index = 1;
            while (sheet.getRow(index) != null) {

                TeamMember teamMember = new TeamMember();

                Row row = sheet.getRow(index);

                //TeamMember name
                Cell cellUserName = row.getCell(2);
                String userName = cellUserName.getStringCellValue();
                teamMember.setName(userName);

                //Team info
                Cell cellTeamName = row.getCell(0);
                String teamName = cellTeamName.getStringCellValue();
                Team team = teams.get(teamName);
                teamMember.setTeamID(team.getID());
                teamMember.setTeam(team.getCar().getNumber() + " - " + team.getName());
                teamMember.setCarNumber(team.getCar().getNumber());

                //TeamMember mail
                Cell cellUserMail = row.getCell(3);
                if (cellUserMail == null) {
                    teamMember.setMail("");
                } else {
                    String userMail = cellUserMail.getStringCellValue();
                    teamMember.setMail(userMail);
                }

                //TeamMember role
                Cell cellRole = row.getCell(4);
                if (cellRole != null) {
                    String[] roles = cellRole.getStringCellValue().split("/");
                    for (String role : roles) {
                        if (role.trim().equals("DRIVER")) {
                            teamMember.setDriver(true);
                        } else if (role.trim().equals("ESO")) {
                            teamMember.setESO(true);
                        } else if (role.trim().equals("ASR")) {
                            teamMember.setASR(true);
                        }
                    }
                }

                //Profile image
                teamMember.setPhotoUrl(context.getString(R.string.default_image_url));
                teamMembers.add(teamMember);
                index++;
            }

            //Save all teamMembers
            for (TeamMember teamMember : teamMembers) {
                teamMemberBO.createTeamMember(teamMember,
                        response -> {
                            //TODO
                        },
                        errorMessage -> {
                            //TODO

                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }
    }


    public interface View {

        /**
         * Show message to user
         * @param message
         */
        void createMessage(Integer message, Object...args);

        /**
         * Finish current activity
         */
        void finishView();

        /**
         * Show loading icon
         */
        void showLoading();

        /**
         * Hide loading icon
         */
        void hideLoadingIcon();

    }
}
