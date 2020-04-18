package es.formulastudent.app.mvp.view.screen.briefing;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.utils.Messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BriefingPresenterTest {

    private static final String MOCK_MAIL = "app@fss.com";
    private static final int ERROR_STRING_KEY = 1;
    private static final String REGISTER_ID = "registerId";
    private static final String TEAM_ID = "teamId";

    private BriefingPresenter.View mMockView;
    private BriefingPresenter presenter;
    private TeamBO mMockTeamBO;
    private TeamMemberBO mMockTeamMemberBO;
    private BriefingBO mMockBriefingBO;
    private Messages mMockMessages;

    @Captor
    ArgumentCaptor<BusinessCallback> callbackCaptor;


    @Before
    public void setUp() {

        callbackCaptor = ArgumentCaptor.forClass(BusinessCallback.class);
        mMockView = mock(BriefingPresenter.View.class);
        mMockTeamBO = mock(TeamBO.class);
        mMockBriefingBO = mock(BriefingBO.class);
        User mMockUser = mock(User.class);
        mMockMessages = mock(Messages.class);
        mMockTeamMemberBO = mock(TeamMemberBO.class);

        when(mMockUser.getMail()).thenReturn(MOCK_MAIL);

        presenter = new BriefingPresenter(mMockView, mMockTeamBO, mMockBriefingBO,
                mMockTeamMemberBO, mMockUser, mMockMessages);
    }

    @Test
    public void testCreateRegisterKO(){

        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.createRegistry(new TeamMember());

        verify(mMockBriefingBO).createBriefingRegistry(any(TeamMember.class), eq(MOCK_MAIL), callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the error message is displayed
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);

        //Check list is not refreshed after error
        verify(mMockView, times(0)).refreshBriefingRegisterItems();
    }


    @Test
    public void testCreateRegisterOK(){

        //Method to test
        presenter.createRegistry(new TeamMember());

        //Mock callback for create method
        verify(mMockBriefingBO).createBriefingRegistry(
            any(TeamMember.class), eq(MOCK_MAIL), callbackCaptor.capture());

        BusinessCallback callbackCreate = callbackCaptor.getValue();
        callbackCreate.onSuccess(new ResponseDTO());

        //After create, the method to retrieve register is called
        verify(mMockBriefingBO).retrieveBriefingRegisters(
                any(), any(), any(), callbackCaptor.capture());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(createBriefingRegisterList(6));

        BusinessCallback callbackGetList = callbackCaptor.getValue();
        callbackGetList.onSuccess(responseDTO);

        assertEquals(6, presenter.getBriefingRegisterList().size());
        presenter.getBriefingRegisterList()
                .forEach(briefingRegister -> {
                    assertEquals(TEAM_ID, briefingRegister.getTeamID());
                    assertEquals(MOCK_MAIL, briefingRegister.getDoneByUserMail());
                    assertNotNull(briefingRegister.getID());
                });
        verify(mMockView, times(1)).refreshBriefingRegisterItems();
    }


    @Test
    public void testRetrieveBriefingRegisterListOK(){

        //Method to test
        presenter.retrieveBriefingRegisterList();

        //After create, the method to retrieve register is called
        verify(mMockBriefingBO).retrieveBriefingRegisters(
                any(), any(), any(), callbackCaptor.capture());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(createBriefingRegisterList(6));

        BusinessCallback callbackGetList = callbackCaptor.getValue();
        callbackGetList.onSuccess(responseDTO);

        assertEquals(6, presenter.getBriefingRegisterList().size());
        presenter.getBriefingRegisterList()
                .forEach(briefingRegister -> {
                    assertEquals(TEAM_ID, briefingRegister.getTeamID());
                    assertEquals(MOCK_MAIL, briefingRegister.getDoneByUserMail());
                    assertNotNull(briefingRegister.getID());
                });
        verify(mMockView, times(1)).refreshBriefingRegisterItems();
    }

    @Test
    public void testRetrieveBriefingRegisterListKO(){

        //Method to test
        presenter.retrieveBriefingRegisterList();

        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        verify(mMockBriefingBO).retrieveBriefingRegisters(
            any(), any(), any(), callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the error message is displayed
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);

        //Check list is not refreshed after error
        verify(mMockView, times(0)).refreshBriefingRegisterItems();
    }

    @Test
    public void testDeleteBriefingRegisterOK(){

        //Initialize the list with 3 elements
        int numberOfElements = 3;
        presenter.updateBriefingRegisters(this.createBriefingRegisterList(numberOfElements));

        //Method to test
        presenter.deleteBriefingRegister(REGISTER_ID);

        //Callback response object
        verify(mMockBriefingBO).deleteBriefingRegister(
            eq(REGISTER_ID),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onSuccess( new ResponseDTO());

        //After create, the method to retrieve register is called
        verify(mMockBriefingBO).retrieveBriefingRegisters(
                any(), any(), any(), callbackCaptor.capture());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(createBriefingRegisterList(numberOfElements-1));

        BusinessCallback callbackGetList = callbackCaptor.getValue();
        callbackGetList.onSuccess(responseDTO);

        assertEquals(numberOfElements-1, presenter.getBriefingRegisterList().size());
        verify(mMockView, times(2))
                .refreshBriefingRegisterItems(); //2 times due to "Initialize the list"
    }

    @Test
    public void testDeleteBriefingRegisterKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.deleteBriefingRegister(REGISTER_ID);

        verify(mMockBriefingBO).deleteBriefingRegister(
            eq(REGISTER_ID),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the error message is displayed
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);

        //Check the list is not refreshed
        verify(mMockView, times(0)).refreshBriefingRegisterItems();
    }

    @Test
    public void testNFCTagDetectedKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.onNFCTagDetected(REGISTER_ID);

        verify(mMockTeamMemberBO).retrieveTeamMemberByNFCTag(eq(REGISTER_ID), callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);
    }

    @Ignore("Know how to test static methods")
    @Test
    public void testNFCTagDetectedOK(){
        //TODO test that confirm dialog is opened
    }

    @Test
    public void testRetrieveTeamsKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.retrieveTeams();

        verify(mMockTeamBO).retrieveTeams(any(), any(), callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);
        verify(mMockView, times(0)).initializeTeamsSpinner(any());
    }

    @Test
    public void testRetrieveTeamsOK(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        List<Team> teams = new ArrayList<>();
        responseDTO.setData(teams);

        //Method to test
        presenter.retrieveTeams();

        verify(mMockTeamBO).retrieveTeams(
                any(), any(), callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onSuccess(responseDTO);
        verify(mMockView, times(1)).initializeTeamsSpinner(any());
        assertFalse(teams.isEmpty());
    }

    private BriefingRegister createBriefingRegister(){
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamID(TEAM_ID);
        return new BriefingRegister(teamMember, new Date(), MOCK_MAIL);
    }

    private List<BriefingRegister> createBriefingRegisterList(int elements){
        List<BriefingRegister> list = new ArrayList<>();
        for(int i=0; i<elements; i++){
            list.add(createBriefingRegister());
        }
        return list;
    }
}