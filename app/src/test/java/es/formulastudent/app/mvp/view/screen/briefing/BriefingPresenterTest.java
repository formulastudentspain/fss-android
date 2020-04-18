package es.formulastudent.app.mvp.view.screen.briefing;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.formulastudent.app.mvp.data.business.OnFailureCallback;
import es.formulastudent.app.mvp.data.business.OnSuccessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.BriefingRegister;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.utils.messages.Message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SuppressWarnings("ALL")
public class BriefingPresenterTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private static final String MOCK_MAIL = "app@fss.com";
    private static final int ERROR_STRING_KEY = 12345;
    private static final String REGISTER_ID = "registerId";
    private static final String TEAM_ID = "teamId";

    private BriefingPresenter.View mMockView;
    private BriefingPresenter presenter;
    private TeamBO mMockTeamBO;
    private TeamMemberBO mMockTeamMemberBO;
    private BriefingBO mMockBriefingBO;

    @Captor
    ArgumentCaptor<OnSuccessCallback> callbackSuccessCaptor;

    @Captor
    ArgumentCaptor<OnFailureCallback> callbackFailureCaptor;


    @Before
    public void setUp() {

        callbackSuccessCaptor = ArgumentCaptor.forClass(OnSuccessCallback.class);
        callbackFailureCaptor = ArgumentCaptor.forClass(OnFailureCallback.class);
        mMockView = mock(BriefingPresenter.View.class);
        mMockTeamBO = mock(TeamBO.class);
        mMockBriefingBO = mock(BriefingBO.class);
        User mMockUser = mock(User.class);
        mMockTeamMemberBO = mock(TeamMemberBO.class);

        when(mMockUser.getMail()).thenReturn(MOCK_MAIL);

        presenter = new BriefingPresenter(mMockView, mMockTeamBO, mMockBriefingBO,
                mMockTeamMemberBO, mMockUser);
    }

    @Test
    public void testCreateRegisterKO(){

        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.createRegistry(new TeamMember());

        verify(mMockBriefingBO).createBriefingRegistry(any(TeamMember.class), eq(MOCK_MAIL),
                callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());
        OnFailureCallback callback = callbackFailureCaptor.getValue();
        callback.onFailure(new Message(ERROR_STRING_KEY));

        //Check that an error is displayed
        assert presenter.getErrorToDisplay().getValue() != null;
        int errorToDisplay = presenter.getErrorToDisplay().getValue().getStringID();
        assertEquals(ERROR_STRING_KEY, errorToDisplay);

        //Check list is not refreshed after error
        verify(mMockView, times(0)).refreshBriefingRegisterItems();
    }


    @Test
    public void testCreateRegisterOK(){

        //Method to test
        presenter.createRegistry(new TeamMember());

        //Mock callback for create method
        verify(mMockBriefingBO).createBriefingRegistry(any(TeamMember.class), eq(MOCK_MAIL),
                callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());

        OnSuccessCallback callbackCreate = callbackSuccessCaptor.getValue();
        callbackCreate.onSuccess(null); //No data needed

        //After create, the method to retrieve register is called
        verify(mMockBriefingBO).retrieveBriefingRegisters(
                any(), any(), any(), callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());

        OnSuccessCallback callbackGetList = callbackSuccessCaptor.getValue();
        callbackGetList.onSuccess(createBriefingRegisterList(6));

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

        verify(mMockBriefingBO).retrieveBriefingRegisters(
                any(), any(), any(), callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());

        OnSuccessCallback callbackGetList = callbackSuccessCaptor.getValue();
        callbackGetList.onSuccess(createBriefingRegisterList(6));

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
            any(), any(), any(), callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());

        OnFailureCallback callback = callbackFailureCaptor.getValue();
        callback.onFailure(new Message(ERROR_STRING_KEY));

        //Check that an error is displayed
        assert presenter.getErrorToDisplay().getValue() != null;
        int errorToDisplay = presenter.getErrorToDisplay().getValue().getStringID();
        assertEquals(ERROR_STRING_KEY, errorToDisplay);

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
        verify(mMockBriefingBO).deleteBriefingRegister(eq(REGISTER_ID),
                callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());
        OnSuccessCallback callback = callbackSuccessCaptor.getValue();
        callback.onSuccess(null); //No data needed

        //After create, the method to retrieve register is called
        verify(mMockBriefingBO).retrieveBriefingRegisters(
                any(), any(), any(), callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());

        OnSuccessCallback callbackGetList = callbackSuccessCaptor.getValue();
        callbackGetList.onSuccess(createBriefingRegisterList(numberOfElements-1));

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
            eq(REGISTER_ID), callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());

        OnFailureCallback callback = callbackFailureCaptor.getValue();
        callback.onFailure(new Message(ERROR_STRING_KEY));

        //Check that an error is displayed
        assert presenter.getErrorToDisplay().getValue() != null;
        int errorToDisplay = presenter.getErrorToDisplay().getValue().getStringID();
        assertEquals(ERROR_STRING_KEY, errorToDisplay);

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

        verify(mMockTeamMemberBO).retrieveTeamMemberByNFCTag(eq(REGISTER_ID),
                callbackSuccessCaptor.capture(), callbackFailureCaptor.capture());

        OnFailureCallback callback = callbackFailureCaptor.getValue();
        callback.onFailure(new Message(ERROR_STRING_KEY));

        //Check that an error is displayed
        assert presenter.getErrorToDisplay().getValue() != null;
        int errorToDisplay = presenter.getErrorToDisplay().getValue().getStringID();
        assertEquals(ERROR_STRING_KEY, errorToDisplay);
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

        verify(mMockTeamBO).retrieveTeams(any(), any(), callbackSuccessCaptor.capture(),
                callbackFailureCaptor.capture());

        OnFailureCallback callback = callbackFailureCaptor.getValue();
        callback.onFailure(new Message(ERROR_STRING_KEY));

        //Check that an error is displayed
        assert presenter.getErrorToDisplay().getValue() != null;
        int errorToDisplay = presenter.getErrorToDisplay().getValue().getStringID();
        assertEquals(ERROR_STRING_KEY, errorToDisplay);
        verify(mMockView, times(0)).initializeTeamsSpinner(any());
    }


    @Test
    public void testRetrieveTeamsOK(){
        List<Team> teams = new ArrayList<>();

        //Method to test
        presenter.retrieveTeams();

        verify(mMockTeamBO).retrieveTeams(any(), any(), callbackSuccessCaptor.capture(),
                callbackFailureCaptor.capture());
        OnSuccessCallback callback = callbackSuccessCaptor.getValue();
        callback.onSuccess(teams);
        verify(mMockView, times(1)).initializeTeamsSpinner(any());
        assertFalse(teams.isEmpty());
        assertEquals("All", teams.stream().findFirst().orElse(null).getName());
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