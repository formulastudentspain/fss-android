package es.formulastudent.app.mvp.data.model;

import java.io.Serializable;

import es.formulastudent.app.R;

public enum DrawerMenuItem implements Serializable {

    BRIEFING(R.string.drawer_menu_staff_briefing, R.string.fa_comment_dots),
    TEAMS(R.string.drawer_menu_teams, R.string.fa_car_side_solid),
    PRACTICE_TRACK(R.string.drawer_menu_staff_practice_track, R.string.fa_graduation_cap_solid),
    SKIDPAD(R.string.drawer_menu_staff_skidpad, R.string.fa_infinity_solid),
    ACCELERATION(R.string.drawer_menu_staff_acceleration, R.string.fa_road_solid),
    AUTOCROSS(R.string.drawer_menu_staff_autocross, R.string.fa_stopwatch_solid),
    ENDURANCE(R.string.drawer_menu_staff_endurance_efficiency, R.string.fa_flag_checkered_solid),
    TEAM_MEMBER(R.string.drawer_menu_staff_users_management_team_members, R.string.fa_people_carry_solid),
    VOLUNTEERS(R.string.drawer_menu_staff_users_management_volunteers, R.string.fa_smile),
    ADMIN_OPERATIONS(R.string.drawer_menu_admin_operations, R.string.fa_adjust_solid),
    LOGOUT(R.string.drawer_menu_common_logout, R.string.fa_sign_out_alt_solid),

    //Titles
    TITLE_EVENT_CONTROL(R.string.drawer_menu_staff, -1),
    TITLE_RACE_ACCESS(R.string.drawer_menu_race_access, -1),
    TITLE_RACE_CONTROL(R.string.drawer_menu_race_control, -1),
    TITLE_CONE_CONTROL(R.string.drawer_menu_cone_control, -1),
    TITLE_USER_MANAGEMENT(R.string.drawer_menu_staff_users_management, -1),
    TITLE_ADMIN_OPERATIONS(R.string.drawer_menu_admin_operations_title, -1);;

    private final int title;
    private final int icon;

    DrawerMenuItem(int title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
