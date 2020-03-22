package es.formulastudent.app.mvp.view.activity.briefing;


import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import es.formulastudent.app.mvp.data.business.BusinessCallback;
import es.formulastudent.app.mvp.data.business.ResponseDTO;
import es.formulastudent.app.mvp.data.business.briefing.BriefingBO;
import es.formulastudent.app.mvp.data.business.team.TeamBO;
import es.formulastudent.app.mvp.data.business.teammember.TeamMemberBO;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.data.model.User;

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
    private Context mMockContext;
    private TeamBO mMockTeamBO;
    private TeamMemberBO mMockTeamMemberBO;
    private BriefingBO mMockBriefingBO;
    private User mMockUser;

    @Captor
    ArgumentCaptor<BusinessCallback> callbackCaptor;

    @Before
    public void setUp() {

        callbackCaptor = ArgumentCaptor.forClass(BusinessCallback.class);
        mMockView = mock(BriefingPresenter.View.class);
        mMockContext = mock(Context.class);
        mMockTeamBO = mock(TeamBO.class);
        mMockBriefingBO = mock(BriefingBO.class);
        mMockUser = mock(User.class);

        when(mMockUser.getMail()).thenReturn(MOCK_MAIL);

        presenter = new BriefingPresenter(mMockView, mMockContext, mMockTeamBO, mMockBriefingBO, mMockTeamMemberBO, mMockUser);
    }




    @Test
    public void testCreateRegisterKO(){

        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.createRegistry(new TeamMember());

        //Check the loading is displayed
        verify(mMockView, times(1)).showLoading();

        verify(mMockBriefingBO).createBriefingRegistry(any(TeamMember.class), eq(MOCK_MAIL), callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the loading is hidden
        verify(mMockView, times(1)).hideLoading();

        //Check the error message is displayed
        verify(mMockView, times(1)).createMessage(ERROR_STRING_KEY);
    }


    @Test
    public void testCreateRegisterOK(){

        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();

        //Method to test
        presenter.createRegistry(new TeamMember());

        //Check the loading is displayed
        verify(mMockView, times(1)).showLoading();

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
        verify(mMockView, times(1)).showLoading();

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
        verify(mMockView, times(1)).showLoading();

        verify(mMockBriefingBO).retrieveBriefingRegisters(
            any(),
            any(),
            any(),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the loading is hidden
        verify(mMockView, times(1)).hideLoading();

        //Check the error message is displayed
        verify(mMockView, times(1)).createMessage(ERROR_STRING_KEY);
    }

    @Test
    public void testDeleteBriefingRegisterOK(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setInfo(SUCCESS_STRING_KEY);

        //Method to test
        presenter.deleteBriefingRegister(REGISTER_ID);

        //Check the loading is displayed
        verify(mMockView, times(1)).showLoading();

        verify(mMockBriefingBO).deleteBriefingRegister(
            eq(REGISTER_ID),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onSuccess(responseDTO);

        //Check the error message is displayed
        verify(mMockView, times(1)).createMessage(SUCCESS_STRING_KEY);
    }

    @Test
    public void testDeleteBriefingRegisterKO(){
        //Callback response object
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(ERROR_STRING_KEY);

        //Method to test
        presenter.deleteBriefingRegister(REGISTER_ID);

        //Check the loading is displayed
        verify(mMockView, times(1)).showLoading();

        verify(mMockBriefingBO).deleteBriefingRegister(
            eq(REGISTER_ID),
            callbackCaptor.capture());
        BusinessCallback callback = callbackCaptor.getValue();
        callback.onFailure(responseDTO);

        //Check the loading is hidden
        verify(mMockView, times(1)).hideLoading();

        //Check the error message is displayed
        verify(mMockView, times(1)).createMessage(ERROR_STRING_KEY);
    }


}