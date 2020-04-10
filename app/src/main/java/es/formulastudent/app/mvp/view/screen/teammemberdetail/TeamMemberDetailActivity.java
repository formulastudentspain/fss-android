package es.formulastudent.app.mvp.view.screen.teammemberdetail;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
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
import es.formulastudent.app.mvp.view.screen.general.GeneralActivity;
import es.formulastudent.app.mvp.view.screen.teammember.dialog.CreateEditTeamMemberDialog;
import info.androidhive.fontawesome.FontTextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class TeamMemberDetailActivity extends GeneralActivity implements TeamMemberDetailPresenter.View, View.OnClickListener {

    private static final int NFC_REQUEST_CODE = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;


    @Inject
    TeamMemberDetailPresenter presenter;

    //View components
    private TextView userName;
    private ImageView userProfilePhoto;
    private ImageView userNFCImage;
    private TextView userNFCTag;
    private TextView teamName;
    private MenuItem filterItem;
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
    TeamMember teamMember;
    boolean lastBriefing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(FSSApp.getApp().component());
        setContentView(R.layout.activity_team_member_detail);
        super.onCreate(savedInstanceState);

        teamMember = (TeamMember) getIntent().getSerializableExtra("selectedTeamMember");
        lastBriefing = getIntent().getBooleanExtra("lastBriefing", false);

        initViews();
        initData();
    }




    /**
     * Inject dependencies method
     * @param appComponent
     */
    protected void setupComponent(AppComponent appComponent) {

        DaggerTeamMemberDetailComponent.builder()
                .appComponent(appComponent)
                .contextModule(new ContextModule(this))
                .teamMemberDetailModule(new TeamMemberDetailModule(this))
                .build()
                .inject(this);
    }


    private void initViews(){

        //Add toolbar title
        setToolbarTitle(getString(R.string.activity_team_members_detail_title));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Instantiate vies components
        userName = findViewById(R.id.user_detail_name);
        teamName = findViewById(R.id.user_detail_team);
        userProfilePhoto = findViewById(R.id.user_detail_profile_image);
        userProfilePhoto.setOnClickListener(this);
        userNFCTag = findViewById(R.id.user_detail_nfc_tag);
        userNFCImage = findViewById(R.id.user_detail_nfc_image);
        userNFCImage.setOnClickListener(this);


        //Role chips
        chipDriver = findViewById(R.id.chipDriver);
        chipESO = findViewById(R.id.chipEso);
        chipASR = findViewById(R.id.chipAsr);
        driverDocument = findViewById(R.id.driverDocument);
        esoDocument = findViewById(R.id.esoDocument);
        asrDocument = findViewById(R.id.asrDocument);
        driverDocumentIcon = findViewById(R.id.driverDocumentIcon);
        esoDocumentIcon = findViewById(R.id.esoDocumentIcon);
        asrDocumentIcon = findViewById(R.id.asrDocumentIcon);

        //Last briefing
        lastBriefingIcon = findViewById(R.id.lastBriefingIcon);
        lastBriefingText = findViewById(R.id.lastBriefingText);

        //Button checked
        buttonChecked = findViewById(R.id.checkedButton);
        buttonChecked.setOnClickListener(this);
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
                chipDriver.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_50)));
                driverDocumentIcon.setText(R.string.fa_check_circle_solid);
                driverDocumentIcon.setTextColor(getResources().getColor(R.color.md_green_400));
                buttonChecked.setVisibility(GONE);
            }else{
                chipDriver.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_grey_200)));
            }
            chipDriver.setVisibility(VISIBLE);
            driverDocument.setVisibility(VISIBLE);
        }else{
            chipDriver.setVisibility(GONE);
            driverDocument.setVisibility(GONE);
        }
        if(teamMember.getESO()!=null && teamMember.getESO()){
            if(teamMember.getCertifiedESO()!=null && teamMember.getCertifiedESO()){
                chipESO.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_50)));
                esoDocumentIcon.setText(R.string.fa_check_circle_solid);
                esoDocumentIcon.setTextColor(getResources().getColor(R.color.md_green_400));
                buttonChecked.setVisibility(GONE);
            }else{
                chipESO.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_grey_200)));
            }
            chipESO.setVisibility(VISIBLE);
            esoDocument.setVisibility(VISIBLE);
        }else{
            chipESO.setVisibility(GONE);
            esoDocument.setVisibility(GONE);
        }
        if(teamMember.getASR()!=null && teamMember.getASR()){
            if(teamMember.getCertifiedASR()!=null && teamMember.getCertifiedASR()){
                chipASR.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_50)));
                asrDocumentIcon.setText(R.string.fa_check_circle_solid);
                asrDocumentIcon.setTextColor(getResources().getColor(R.color.md_green_400));
                buttonChecked.setVisibility(GONE);
            }else{
                chipASR.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_grey_200)));
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
    public void finishView() {

    }

    @Override
    public void showLoading() {
        super.showLoadingDialog();
    }

    @Override
    public void hideLoadingIcon() {
        super.hideLoadingDialog();
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
            createMessage(R.string.team_member_error_photo_mandatory);
            return false;

        }else if(!teamMember.getCertifiedDriver()
                && !teamMember.getCertifiedESO()
                && !teamMember.getCertifiedASR()){
            createMessage(R.string.team_member_error_documents_mandatory);
            return false;

        }else{
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_detail, menu);

        //Search menu item
        filterItem = menu.findItem(R.id.filter_results);
        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                presenter.openEditTeamMemberDialog();
                return true;
            }
        });

        return true;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //NFC reader
        if (requestCode == NFC_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                presenter.onNFCTagDetected(teamMember,result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
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
        Intent i = new Intent(this, NFCReaderActivity.class);
        startActivityForResult(i, NFC_REQUEST_CODE);
    }

    @Override
    public void updateTeamMemberInfo(TeamMember teamMember) {
        this.teamMember = teamMember;
        initData();
    }

    @Override
    public void showEditTeamMemberDialog(List<Team> teams) {
        FragmentManager fm = getSupportFragmentManager();
        CreateEditTeamMemberDialog createEditTeamMemberDialog = CreateEditTeamMemberDialog.newInstance(presenter, this, teams, teamMember);
        createEditTeamMemberDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
