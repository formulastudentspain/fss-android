package code.formulastudentspain.app.mvp.view.screen.teammember;

import code.formulastudentspain.app.mvp.data.model.TeamMember;

public interface TeamMemberGeneralPresenter {

    /**
     * Create or update team member
     * @param teamMember
     */
    void updateOrCreateTeamMember(TeamMember teamMember);
}
