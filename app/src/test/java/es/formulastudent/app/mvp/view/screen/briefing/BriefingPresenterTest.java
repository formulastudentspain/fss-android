package es.formulastudent.app.mvp.view.screen.briefing;


import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;
import java.util.List;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.Messages;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BriefingPresenterTest {

    private static final String MOCK_MAIL = "app@fss.com";
    private static final int SUCCESS_STRING_KEY = 0;
    private static final int ERROR_STRING_KEY = 1;
    private static final String REGISTER_ID = "abc";

    private BriefingPresenter.View mMockView;
    private BriefingPresenter presenter;
    private TeamBO mMockTeamBO;
    private TeamMemberBO mMockTeamMemberBO;
    private BriefingBO mMockBriefingBO;
    private User mMockUser;
    private LoadingDialog mMockloadingDialog;
    private Messages mMockMessages;

    @Captor
    ArgumentCaptor<BusinessCallback> callbackCaptor;


    @Before
    public void setUp() {

        callbackCaptor = ArgumentCaptor.forClass(BusinessCallback.class);
        mMockView = mock(BriefingPresenter.View.class);
        mMockTeamBO = mock(TeamBO.class);
        mMockBriefingBO = mock(BriefingBO.class);
        mMockUser = mock(User.class);
        mMockloadingDialog = mock(LoadingDialog.class);
        mMockMessages = mock(Messages.class);
        mMockTeamMemberBO = mock(TeamMemberBO.class);

        when(mMockUser.getMail()).thenReturn(MOCK_MAIL);

        presenter = new BriefingPresenter(mMockView, mMockTeamBO, mMockBriefingBO,
                mMockTeamMemberBO, mMockUser, mMockloadingDialog, mMockMessages);
    }

    @Test
    public void testCreateRegisterKO(){

        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.createRegistry(new TeamMember());

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockBriefingBO).createBriefingRegistry(any(TeamMember.class), eq(MOCK_MAIL), callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the loading is hidden
        verify(mMockloadingDialog, times(1)).hide();

        //Check the error message is displayed
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);
    }


    @Test
    public void testCreateRegisterOK(){

        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();

        //Method to test
        presenter.createRegistry(new TeamMember());

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockBriefingBO).createBriefingRegistry(
            any(TeamMember.class),
            eq(MOCK_MAIL),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onSuccess(responseDTO);
    }


    @Test
    public void testRetrieveBriefingRegisterListOK(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();

        //Method to test
        presenter.retrieveBriefingRegisterList();

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockBriefingBO).retrieveBriefingRegisters(
            any(),
            any(),
            any(),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onSuccess(responseDTO);
    }

    @Test
    public void testRetrieveBriefingRegisterListKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.retrieveBriefingRegisterList();

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockBriefingBO).retrieveBriefingRegisters(
            any(),
            any(),
            any(),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the loading is hidden
        verify(mMockloadingDialog, times(1)).hide();

        //Check the error message is displayed
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);
    }

    @Test
    public void testDeleteBriefingRegisterOK(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setInfo(SUCCESS_STRING_KEY);

        //Method to test
        presenter.deleteBriefingRegister(REGISTER_ID);

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockBriefingBO).deleteBriefingRegister(
            eq(REGISTER_ID),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onSuccess(responseDTO);

        //Check the error message is displayed
        verify(mMockMessages, times(0)).showError(SUCCESS_STRING_KEY);

        //Check the list is refreshed after deleting
        verify(mMockBriefingBO, times(1))
                .retrieveBriefingRegisters(any(), any(), any(), any());
    }

    @Test
    public void testDeleteBriefingRegisterKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.deleteBriefingRegister(REGISTER_ID);

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockBriefingBO).deleteBriefingRegister(
            eq(REGISTER_ID),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the loading is hidden
        verify(mMockloadingDialog, times(1)).hide();

        //Check the error message is displayed
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);
    }

    @Test
    public void testNFCTagDetectedKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.onNFCTagDetected(REGISTER_ID);

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockTeamMemberBO).retrieveTeamMemberByNFCTag(
                eq(REGISTER_ID),
                callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);
        verify(mMockloadingDialog, times(1)).hide();
        verify(mMockMessages, times(1)).showError(ERROR_STRING_KEY);
    }

    @Test
    public void testRetrieveTeamsKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.retrieveTeams();

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockTeamBO).retrieveTeams(
                any(),
                any(),
                callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);
        verify(mMockloadingDialog, times(1)).hide();
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

        //Check the loading is displayed
        verify(mMockloadingDialog, times(1)).show();

        verify(mMockTeamBO).retrieveTeams(
                any(),
                any(),
                callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onSuccess(responseDTO);
        verify(mMockloadingDialog, times(1)).hide();
        verify(mMockView, times(1)).initializeTeamsSpinner(any());
        assertFalse(teams.isEmpty());
    }
}