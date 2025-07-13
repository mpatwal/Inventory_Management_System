package com.mona.inventoryms.mailing;
import jakarta.mail.MessagingException;
public interface EmailService {
    public  void sendMail(final  AbstractEmailContext emailContext) throws MessagingException;
}
