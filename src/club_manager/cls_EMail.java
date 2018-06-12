package club_manager;



import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;


//Check https://github.com/bbottema/simple-java-mail

public class cls_EMail {

    private byte[] imageByteArray;
    
    
    
    public void sendMail(){
        
        /*
        Email email = EmailBuilder.startingBlank()
                .to("sayacr@outlook.com")
                .cc("sayacr@outlook.com")
                .withSubject("Prueba de envío de correo electrónico")
                .withPlainText("Veamos si inserta la imagen")
                .withEmbeddedImage("wink1", imageByteArray, "Images/TennisBallLogo_Lil.png")
                .withHeader("Heaher", 5)
                .buildEmail();
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp-mail.outlook.com", 587, "suarez.jc@outlook.com", "password")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withSessionTimeout(10 * 1000)
                .clearEmailAddressCriteria() // turns off email validation
                .withDebugLogging(true)
                .buildMailer();
        mailer.sendMail(email);
                
                
        */        
        
        
        
        
    }
    
    
    
    
    
}
