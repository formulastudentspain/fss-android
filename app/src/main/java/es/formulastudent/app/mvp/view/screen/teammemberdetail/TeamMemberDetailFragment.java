package es.formulastudent.app.mvp.view.screen.teammemberdetail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import es.formulastudent.app.FSSApp;
import es.formulastudent.app.R;
import es.formulastudent.app.di.component.AppComponent;
import es.formulastudent.app.di.component.DaggerTeamMemberDetailComponent;
import es.formulastudent.app.di.module.ContextModule;
import es.formulastudent.app.di.module.activity.TeamMemberDetailModule;
import es.formulastudent.app.mvp.data.model.Team;
import es.formulastudent.app.mvp.data.model.TeamMember;
import es.formulastudent.app.mvp.view.screen.NFCReaderActivity;
import es.formulastudent.app.mvp.view.screen.teammember.dialog.CreateEditTeamMemberDialog;
import es.formulastudent.app.mvp.view.utils.LoadingDialog;
import es.formulastudent.app.mvp.view.utils.messages.Messages;
import info.androidhive.fontawesome.FontTextView;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class TeamMemberDetailFragment extends Fragment implements TeamMemberDetailPresenter.View, View.OnClickListener{

    private static final int NFC_REQUEST_CODE = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;

    @Inject
    TeamMemberDetailPresenter presenter;

    @Inject
    Messages messages;

    @Inject
    LoadingDialog loadingDialog;

    //View components
    private TextView userName;
    private ImageView userProfilePhoto;
    private ImageView userNFCImage;
    private TextView userNFCTag;
    private TextView teamName;
    private Chip chipDriver;
    private Chip chipESO;
    private Chip chipASR;
    private LinearLayout driverDocument;
    private LinearLayout esoDocument;
    private LinearLayout asrDocument;
    private FontTextView driverDocumentIcon;
    private FontTextView esoDocumentIcon;
    private FontTextView asrDocumentIcon;
    private MaterialButton buttonChecked;
    private FontTextView lastBriefingIcon;
    private TextView lastBriefingText;

    //Selected teamMember
    private TeamMember teamMember;
    private boolean lastBriefing;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        super.onCreate(savedInstanceState);

        //Observer to display loading dialog
        presenter.getLoadingData().observe(this, loadingData -> {
            if(loadingData){
                loadingDialog.show();
            }else{
                loadingDialog.hide();
            }
        });

        //Observer to display errors
        presenter.getErrorToDisplay().observe(this, message ->
                messages.showError(message.getStringID(), message.getArgs()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_team_member_detail, container, false);

        TeamMemberDetailFragmentArgs args = TeamMemberDetailFragmentArgs.fromBundle(getArguments());
        teamMember = args.getTeamMember();
        lastBriefing = args.getLastBriefing();

        initViews(view);
        return view;
    }


    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {
        DaggerTeamMemberDetailComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(getContext(), getActivity()))
                .teamMemberDetailModule(new TeamMemberDetailModule(this))
                .build()
                .inject(this);
    }


    private void initViews(View view){

        //Instantiate vies components
        userName = view.findViewById(R.id.user_detail_name);
        teamName = view.findViewById(R.id.user_detail_team);
        userProfilePhoto = view.findViewById(R.id.user_detail_profile_image);
        userProfilePhoto.setOnClickListener(this);
        userNFCTag = view.findViewById(R.id.user_detail_nfc_tag);
        userNFCImage = view.findViewById(R.id.user_detail_nfc_image);
        userNFCImage.setOnClickListener(this);

        //Role chips
        chipDriver = view.findViewById(R.id.chipDriver);
        chipESO = view.findViewById(R.id.chipEso);
        chipASR = view.findViewById(R.id.chipAsr);
        driverDocument = view.findViewById(R.id.driverDocument);
        esoDocument = view.findViewById(R.id.esoDocument);
        asrDocument = view.findViewById(R.id.asrDocument);
        driverDocumentIcon = view.findViewById(R.id.driverDocumentIcon);
        esoDocumentIcon = view.findViewById(R.id.esoDocumentIcon);
        asrDocumentIcon = view.findViewById(R.id.asrDocumentIcon);

        //Last briefing
        lastBriefingIcon = view.findViewById(R.id.lastBriefingIcon);
        lastBriefingText = view.findViewById(R.id.lastBriefingText);

        //Button checked
        buttonChecked = view.findViewById(R.id.checkedButton);
        buttonChecked.setOnClickListener(this);
        initData();
    }

    private void initData() {

        //Set data
        userName.setText(teamMember.getName());
        teamName.setText(teamMember.getTeam());

        //Photo URL
        Picasso.get().load(teamMember.getPhotoUrl()).into(userProfilePhoto);

        //Registered
        if(teamMember.getNFCTag()!=null && !teamMember.getNFCTag().isEmpty()){
            userNFCImage.setImageResource(R.drawable.ic_user_nfc_registered);
            userNFCTag.setText(teamMember.getNFCTag());
        }else{
            userNFCImage.setImageResource(R.drawable.ic_user_nfc_not_registered);
            userNFCTag.setText("Not registered");
        }

        //Roles
        if(teamMember.getDriver()!=null && teamMember.getDriver()){
            if(teamMember.getCertifiedDriver()!=null && teamMember.getCertifiedDriver()){
                chipDriver.setChipBackgroundColor(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.md_red_50)));
                driverDocumentIcon.setText(R.string.fa_check_circle_solid);
                driverDocumentIcon.setTextColor(getResources().getColor(R.color.md_green_400));
                buttonChecked.setVisibility(GONE);
            }else{
                chipDriver.setChipBackgroundColor(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.md_grey_200)));
            }
            chipDriver.setVisibility(VISIBLE);
            driverDocument.setVisibility(VISIBLE);
        }else{
            chipDriver.setVisibility(GONE);
            driverDocument.setVisibility(GONE);
        }
        if(teamMember.getESO()!=null && teamMember.getESO()){
            if(teamMember.getCertifiedESO()!=null && teamMember.getCertifiedESO()){
                chipESO.setChipBackgroundColor(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.md_green_50)));
                esoDocumentIcon.setText(R.string.fa_check_circle_solid);
                esoDocumentIcon.setTextColor(getResources().getColor(R.color.md_green_400));
                buttonChecked.setVisibility(GONE);
            }else{
                chipESO.setChipBackgroundColor(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.md_grey_200)));
            }
            chipESO.setVisibility(VISIBLE);
            esoDocument.setVisibility(VISIBLE);
        }else{
            chipESO.setVisibility(GONE);
            esoDocument.setVisibility(GONE);
        }
        if(teamMember.getASR()!=null && teamMember.getASR()){
            if(teamMember.getCertifiedASR()!=null && teamMember.getCertifiedASR()){
                chipASR.setChipBackgroundColor(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.md_blue_50)));
                asrDocumentIcon.setText(R.string.fa_check_circle_solid);
                asrDocumentIcon.setTextColor(getResources().getColor(R.color.md_green_400));
                buttonChecked.setVisibility(GONE);
            }else{
                chipASR.setChipBackgroundColor(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.md_grey_200)));
            }
            chipASR.setVisibility(VISIBLE);
            asrDocument.setVisibility(VISIBLE);
        }else{
            chipASR.setVisibility(GONE);
            asrDocument.setVisibility(GONE);
        }

        if(lastBriefing){
            lastBriefingIcon.setText(R.string.fa_check_circle_solid);
            lastBriefingIcon.setTextColor(getResources().getColor(R.color.md_green_400));
            lastBriefingText.setText("<24 hours ago");
        }
    }

    @Override
    public void updateNFCInformation(String TAG) {
        userNFCImage.setImageResource(R.drawable.ic_user_nfc_registered);
        userNFCTag.setText(TAG);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.user_detail_nfc_image){
            if(isAllDataFilled()){
                presenter.checkMaxNumDrivers();
            }

        }else if(view.getId() == R.id.user_detail_profile_image){
            dispatchTakePictureIntent();

        }else if(view.getId() == R.id.checkedButton){
            presenter.checkDocuments(teamMember);
        }
    }

    private boolean isAllDataFilled() {
        if(teamMember.getPhotoUrl().equals(getString(R.string.default_image_url))){
            messages.showError(R.string.team_member_error_photo_mandatory);
            return false;

        }else if(!teamMember.getCertifiedDriver()
                && !teamMember.getCertifiedESO()
                && !teamMember.getCertifiedASR()){
            messages.showError(R.string.team_member_error_documents_mandatory);
            return false;
        }else{
            return true;
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_detail, menu);
        MenuItem filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(menuItem -> {
            presenter.openEditTeamMemberDialog();
            return true;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //NFC reader
        if (requestCode == NFC_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                presenter.onNFCTagDetected(teamMember,result);
            }
        }

        //Camera image
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            presenter.uploadProfilePicture(imageBitmap, teamMember);
        }
    }

    @Override
    public void updateProfilePicture(Bitmap imageBitmap){
        userProfilePhoto.setImageBitmap(imageBitmap);
    }

    @Override
    public TeamMember getSelectedUser() {
        return teamMember;
    }

    @Override
    public void openNFCReader() {
        Intent i = new Intent(getContext(), NFCReaderActivity.class);
        startActivityForResult(i, NFC_REQUEST_CODE);
    }

    @Override
    public void updateTeamMemberInfo(TeamMember teamMember) {
        this.teamMember = teamMember;
        initData();
    }

    @Override
    public void showEditTeamMemberDialog(List<Team> teams) {
        FragmentManager fm = getParentFragmentManager();
        CreateEditTeamMemberDialog createEditTeamMemberDialog = CreateEditTeamMemberDialog
                .newInstance(presenter, getContext(), teams, teamMember);
        createEditTeamMemberDialog.show(fm, "fragment_edit_name");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
