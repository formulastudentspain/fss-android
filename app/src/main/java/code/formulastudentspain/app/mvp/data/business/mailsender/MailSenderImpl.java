package code.formulastudentspain.app.mvp.data.business.mailsender;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;

import java.io.File;

import code.formulastudentspain.app.mvp.data.business.statistics.dto.ExportStatisticsDTO;
import code.formulastudentspain.app.mvp.data.model.User;

public class MailSenderImpl implements MailSender{

    private Context context;
    private User loggedUser;

    public MailSenderImpl(Context context, User loggedUser) {
        this.context = context;
        this.loggedUser = loggedUser;
    }

    @Override
    public void sendMail(ExportStatisticsDTO dto) {

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            File fileLocation = new File(dto.getFullFilePath());
            Uri path = Uri.fromFile(fileLocation);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            // set the type to 'email'
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {loggedUser.getMail()};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            // the attachment
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
            // the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[FSS Export] " + dto.getDescription());

            context.startActivity(Intent.createChooser(emailIntent , "Send email...").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

}
