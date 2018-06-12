package club_manager;

//Library import section

public class cls_Student {
    
    //Atributes declaration
    
    private String sFstName; //First name
    private String sMidName; //Middle name
    private String sLstName; //Last name
    private String sIdNumb; //Identification number
    private String sEmail; //E-mail
    private String sHomNumb; //Home number
    private String sCelNumb; //Cell number
    private String sAddMain; //Main address
    private String sAddCity; //City
    private String sAddStat; //State
    private String sAddZiCo; //Zip Code
    private String sStuLevl; //Level
    private String sStatus; //Current status
    private String sResClas; //Reserved classes
    private String sRecClas; //Received classes
    private String sMembshp; //Membership
    private String sPayClas; //Payment
    private String sTeaName; //Assigned teacher´s name
    private String sTeaCate; //Teacher´s Category
    private String sStudPIN; //Personal Identification Number
    private String sReservs; //List of reservations under the student
    private String sXXX1;
    private String sXXX2;
    private String sXXX3;
    private String sXXX4;
    
    
    
    //Constructors
    public cls_Student(){
        
    }
    
    public cls_Student(String sFstName, String sMidName, String sLstName, String sIdNumb, String sEmail, 
            String sHomNumb, String sCelNumb, String sAddMain, String sAddCity, String sAddStat,String sAddZiCo,
            String sStuLevl, String sStatus, String sResClas, String sRecClas, String sMembshp, String sPayClas, String sTeaName,
            String sTeaCate, String sStudPIN, String sReservs, String sXXX1, String sXXX2, String sXXX3, String sXXX4){
        this.sFstName = sFstName;
        this.sMidName = sMidName;
        this.sLstName = sLstName;
        this.sIdNumb = sIdNumb;
        this.sEmail = sEmail;
        this.sHomNumb = sHomNumb;
        this.sCelNumb = sCelNumb;
        this.sAddMain = sAddMain;
        this.sAddCity = sAddCity;
        this.sAddStat = sAddStat;
        this.sAddZiCo = sAddZiCo;
        this.sStuLevl = sStuLevl;
        this.sStatus = sStatus;
        this.sResClas = sResClas;
        this.sRecClas = sRecClas;
        this.sMembshp = sMembshp;
        this.sPayClas = sPayClas;
        this.sTeaName = sTeaName;
        this.sTeaCate = sTeaCate;
        this.sStudPIN = sStudPIN;
        this.sReservs = sReservs;
        this.sXXX1 = sXXX1;
        this.sXXX2 = sXXX2;
        this.sXXX3 = sXXX3;
        this.sXXX4 = sXXX4;
    }
    
    //Methods
    
    public void setFstName(String sFstName) {this.sFstName = sFstName;}
    public void setMidName(String sMidName) {this.sMidName = sMidName;}
    public void setLstName(String sLstName) {this.sLstName = sLstName;}
    public void setIdNumb(String sIdNumb) {this.sIdNumb = sIdNumb;}
    public void setEmail(String sEmail) {this.sEmail = sEmail;}
    public void setHomNumb(String sHomNumb) {this.sHomNumb = sHomNumb;}
    public void setCelNumb(String sCelNumb) {this.sCelNumb = sCelNumb;}
    public void setAddMain(String sAddMain) {this.sAddMain = sAddMain;}
    public void setAddCity(String sAddCity) {this.sAddCity = sAddCity;}
    public void setAddStat(String sAddStat) {this.sAddStat = sAddStat;}
    public void setAddZiCo(String sAddZiCo) {this.sAddZiCo = sAddZiCo;}
    public void setStuLevl(String sStuLevl) {this.sStuLevl = sStuLevl;}
    public void setStatus(String sStatus) {this.sStatus = sStatus;}
    public void setResClas(String sResClas) {this.sResClas = sResClas;}
    public void setRecClas(String sRecClas) {this.sRecClas = sRecClas;}
    public void setMemb(String sMemb) {this.sMembshp = sMemb;}
    public void setPayClas(String sPayClas) {this.sPayClas = sPayClas;}
    public void setTeaName(String sTeaName) {this.sTeaName = sTeaName;}
    public void setTeaCate(String sTeaCate) {this.sTeaCate = sTeaCate;}
    public void setStudPIN(String sStudPIN) {this.sStudPIN = sStudPIN;}
    public void setReservs(String sReservs) {this.sReservs = sReservs;}
    public void setXXX1(String sXXX1) {this.sXXX1 = sXXX1;}
    public void setXXX2(String sXXX2) {this.sXXX2 = sXXX2;}
    public void setXXX3(String sXXX3) {this.sXXX3 = sXXX3;}
    public void setXXX4(String sXXX4) {this.sXXX4 = sXXX4;}
    
    

    public String getFstName() {return sFstName;}
    public String getMidName() {return sMidName;}
    public String getLstName() {return sLstName;}
    public String getIdNumb() {return sIdNumb;}
    public String getEmail() {return sEmail;}
    public String getHomNumb() {return sHomNumb;}
    public String getCelNumb() {return sCelNumb;}
    public String getAddMain() {return sAddMain;}
    public String getAddCity() {return sAddCity;}
    public String getAddStat() {return sAddStat;}
    public String getAddZiCo() {return sAddZiCo;}
    public String getStuLevl() {return sStuLevl;}
    public String getStatus() {return sStatus;}
    public String getResClas() {return sResClas;}
    public String getRecClas() {return sRecClas;}
    public String getMembshp() {return sMembshp;}
    public String getPayClas() {return sPayClas;}
    public String getTeaName() {return sTeaName;}
    public String getTeaCate() {return sTeaCate;}
    public String getStudPIN() {return sStudPIN;}
    public String getReservs() {return sReservs;}
    public String getXXX1() {return sXXX1;}
    public String getXXX2() {return sXXX2;}
    public String getXXX3() {return sXXX3;}
    public String getXXX4() {return sXXX4;}
    
        
}
