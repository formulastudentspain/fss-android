package code.formulastudentspain.app.mvp.data.model;

public class DrawerMenu {

    public User user;
    public boolean canUserSeeBriefing;
    public boolean canUserSeeTeams;
    public boolean canUserSeeRaceAccess;
    public boolean canUserSeeRaceControl;
    public boolean canUserSeeConeControl;
    public boolean canUserSeeVolunteers;
    public boolean canUserSeeTeamMembers;
    public boolean canUserSeeAdminOperations;

    public DrawerMenu(User user) {
        this.user = user;
        this.canUserSeeBriefing = canUserSeeBriefing(user);
        this.canUserSeeTeams = canUserSeeTeams(user);
        this.canUserSeeRaceAccess = canUserSeeRaceAccess(user);
        this.canUserSeeRaceControl = canUserSeeRaceControl(user);
        this.canUserSeeConeControl = canUserSeeConeControl(user);
        this.canUserSeeVolunteers = canUserSeeVolunteers(user);
        this.canUserSeeTeamMembers = canUserSeeTeamMembers(user);
        this.canUserSeeAdminOperations = canUserSeeAdminOperations(user);
    }

    public DrawerMenuItem briefing = DrawerMenuItem.BRIEFING;
    public DrawerMenuItem teams = DrawerMenuItem.TEAMS;
    public DrawerMenuItem practiceTrack = DrawerMenuItem.PRACTICE_TRACK;
    public DrawerMenuItem skidpad = DrawerMenuItem.SKIDPAD;
    public DrawerMenuItem acceleration = DrawerMenuItem.ACCELERATION;
    public DrawerMenuItem autocross = DrawerMenuItem.AUTOCROSS;
    public DrawerMenuItem endurance = DrawerMenuItem.ENDURANCE;
    public DrawerMenuItem volunteers = DrawerMenuItem.VOLUNTEERS;
    public DrawerMenuItem teamMember = DrawerMenuItem.TEAM_MEMBER;
    public DrawerMenuItem adminOperations = DrawerMenuItem.ADMIN_OPERATIONS;
    public DrawerMenuItem statistics = DrawerMenuItem.STATISTICS;
    public DrawerMenuItem logout = DrawerMenuItem.LOGOUT;

    //Titles
    public DrawerMenuItem titleEventControl = DrawerMenuItem.TITLE_EVENT_CONTROL;
    public DrawerMenuItem titleRaceAccess = DrawerMenuItem.TITLE_RACE_ACCESS;
    public DrawerMenuItem titleRaceControl = DrawerMenuItem.TITLE_RACE_CONTROL;
    public DrawerMenuItem titleConeControl = DrawerMenuItem.TITLE_CONE_CONTROL;
    public DrawerMenuItem titleUserManagement = DrawerMenuItem.TITLE_USER_MANAGEMENT;
    public DrawerMenuItem titleAdminOperations = DrawerMenuItem.TITLE_ADMIN_OPERATIONS;

    private boolean canUserSeeBriefing(User user){
        return user.isAdministrator()
                || user.isOfficial()
                || user.getRole().equals(UserRole.STAFF);
    }

    private boolean canUserSeeTeams(User user){
        return user.isAdministrator()
                || user.isOfficial()
                || user.getRole().equals(UserRole.STAFF)
                || user.getRole().equals(UserRole.SCRUTINEER);
    }

    private boolean canUserSeeRaceAccess(User user){
        return  user.isAdministrator()
                || user.isOfficial()
                || user.getRole().equals(UserRole.MARSHALL);
    }

    private boolean canUserSeeRaceControl(User user){
        return user.isAdministrator()
                || user.isOfficial()
                || user.getRole().equals(UserRole.MARSHALL);
    }

    private boolean canUserSeeConeControl(User user){
        return user.isAdministrator()
                || user.isOfficial()
                || user.getRole().equals(UserRole.MARSHALL);
    }

    private boolean canUserSeeVolunteers(User user){
        return user.isAdministrator()
                || user.isOfficial();
    }

    private boolean canUserSeeTeamMembers(User user){
        return user.isAdministrator()
                || user.isOfficial()
                || user.getRole().equals(UserRole.STAFF);
    }

    private boolean canUserSeeAdminOperations(User user){
        return user.isAdministrator();
    }
}
