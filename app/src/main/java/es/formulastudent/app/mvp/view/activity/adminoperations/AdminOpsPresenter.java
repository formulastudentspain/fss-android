package es.formulastudent.app.mvp.view.activity.adminoperations;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.formulastudent.app.R;
import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.user.UserBO;
import es.formulastudent.app.mvp.data.model.Car;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;

public class AdminOpsPresenter {

    //Dependencies
    private AdminOpsPresenter.View view;
    private Context context;
    private TeamBO teamBO;
    private UserBO userBO;


    public AdminOpsPresenter(AdminOpsPresenter.View view, Context context, TeamBO teamBO, UserBO userBO) {
        this.view = view;
        this.context = context;
        this.teamBO = teamBO;
        this.userBO = userBO;
    }

    public void deleteAllDrivers() {
        userBO.deleteAllDrivers(new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

            }
        });

    }

    public void deleteAllTeams() {
        teamBO.deleteAllTeams(new BusinessCallback() {
            @Override
            public void onSuccess(ResponseDTO responseDTO) {

            }

            @Override
            public void onFailure(ResponseDTO responseDTO) {

            }
        });
    }


    public void importTeamsAndDrivers(){

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

                    team.setCar(car);

                    teams.put(teamName, team);
                }
                index ++;
            }

            //Save all teams
            for(String teamKey: teams.keySet()){
               final Team team = teams.get(teamKey);

                teamBO.createTeam(team, new BusinessCallback() {
                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {
                        persistedTeams.put(team.getName(), team);

                        if(persistedTeams.size() == teams.size()){
                            createDrivers(persistedTeams);
                        }
                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {

                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createDrivers(Map<String, Team> teams){

        List<TeamMember> teamMembers = new ArrayList<>();

        try {

            AssetManager mngr = context.getAssets();
            final InputStream is = mngr.open("fss_info.xls");

            Workbook wb = new HSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);


            //Get all teams and information
            int index = 1;
            while(sheet.getRow(index) != null){

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
                teamMember.setTeam(team.getName());
                teamMember.setCarNumber(team.getCar().getNumber());

                //TeamMember mail
                Cell cellUserMail = row.getCell(3);
                if(cellUserMail == null){
                    teamMember.setMail("");
                }else{
                    String userMail = cellUserMail.getStringCellValue();
                    teamMember.setMail(userMail);
                }


                //Role
                teamMember.setRole("DRIVER");

                //Profile image
                teamMember.setPhotoUrl(context.getString(R.string.default_image_url));

                teamMembers.add(teamMember);

                index ++;
            }

            //Save all teamMembers
            for(TeamMember teamMember : teamMembers){

                userBO.createUser(teamMember, new BusinessCallback() {
                    @Override
                    public void onSuccess(ResponseDTO responseDTO) {

                    }

                    @Override
                    public void onFailure(ResponseDTO responseDTO) {

                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
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

        /**
         * Return the activity
         * @return
         */
        AdminOpsActivity getActivity();

    }
}
