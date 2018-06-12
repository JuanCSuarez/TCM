package club_manager;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class cls_Mail {
    
    
    //VARIABLES DECLARATION
    String mailTo;
    String mailCC;
    String mailSub;
    String mailBody;
       
    
    //CONSTRUCTORS
    
    public cls_Mail(){
    }
    
    public cls_Mail(String mailTo, String mailCC, String mailSub, String mailBody){
        this.mailTo = mailTo;
        this.mailCC = mailCC;
        this.mailSub = mailSub;
        this.mailBody = mailBody;
        
    }
    
    //SETS & GETS

    public void setMailTo(String mailTo) {this.mailTo = mailTo;}
    public void setMailCC(String mailCC) {this.mailCC = mailCC;}
    public void setMailSub(String mailSub) {this.mailSub = mailSub;}
    public void setMailBody(String mailBody) {this.mailBody = mailBody;}
    
    public String getMailTo() {return mailTo;}
    public String getMailCC() {return mailCC;}
    public String getMailSub() {return mailSub;}
    public String getMailBody() {return mailBody;}
        
    //MAIN METHODS
    
    //Launchs an Outlook SenTo mail window
    public void sendMail() throws IOException, URISyntaxException
    {
        if ( mailTo.equals("n/a") ){mailTo = formatURI(" ");}
        if ( mailCC.equals("n/a") ){mailCC = formatURI(" ");}
        mailTo = formatURI(mailTo);
        mailCC = formatURI(mailCC);
        mailSub = formatURI(mailSub);
        mailBody = formatURI(mailBody);
        //String sMail = "mailto:" + mailTo + "?cc=" + mailCC + "&subject=" + mailSub;
        String sMail = "mailto:" + mailTo + "?cc=" + "%20" + "&subject=" + mailSub + "&BODY=" + mailBody;
        Desktop.getDesktop().mail( new URI( sMail ) );
    }

    //Formats a text string to URI format
    private String formatURI(String text){    
        //<editor-fold defaultstate="collapsed" desc="Methoud Source Code">
        String tmpText;
        tmpText = text.replaceAll("#", "%23");
        tmpText = tmpText.replaceAll("\t", "%09");
        tmpText = tmpText.replaceAll("\n", "%0D");
        //tmpText = tmpText.replaceAll(" | ", "\\%7C");
        //tmpText = text.replaceAll("(", "%28");
        //tmpText = text.replaceAll(")", "%29");
        //tmpText = tmpText.replaceAll(";", "%3B");
        //tmpText = tmpText.replaceAll("?", "%3F");
        //tmpText = tmpText.replaceAll("/", "%2F");
        //tmpText = tmpText.replaceAll(":", "%3A");
        //tmpText = tmpText.replaceAll("&", "%24");
        //tmpText = tmpText.replaceAll("+", "%2B");
        //tmpText = tmpText.replaceAll("$", "%26");
        //tmpText = tmpText.replaceAll(",", "%2C");
        tmpText = tmpText.replaceAll(" ", "%20");
        tmpText = tmpText.replaceAll("<", "%3C");
        tmpText = tmpText.replaceAll(">", "%3E");
        //tmpText = tmpText.replaceAll("<", "%3C");
        //tmpText = tmpText.replaceAll(">", "%3E");
        //tmpText = tmpText.replaceAll("~", "%7E");
        //tmpText = tmpText.replaceAll("$", "%26");
        //tmpText = tmpText.replaceAll("%", "%25");
        return tmpText;
        //</editor-fold>
    }
    
    
    
}
