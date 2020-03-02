package es.formulastudent.app.mvp.data.business.mailsender;

import es.formulastudent.app.mvp.data.business.statistics.dto.ExportStatisticsDTO;

public interface MailSender {

    /**
     * Send mail
     * @param dto
     */
    void sendMail(ExportStatisticsDTO dto);
}
