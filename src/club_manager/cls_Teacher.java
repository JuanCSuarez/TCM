package club_manager;

//Library import section

public class cls_Teacher {
    
    //Attributes declaration
    
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
    private String sCategry; //Category
    private String sStatus; //Current status
    private String sStudQty; //Qty of assigned students
    private String sResClas; //Reserved classes
    private String sImpClas; //Imparted classes
    private String sPayClas; //Payed classes
    private String sComPerc; //Commision Percentaje
    private String sSalary; //Salary
    private String sHrsCost; //Hours Cost
    private String sStuList; //List of students assigned to the Teacher
    private String sProfPIN; //Personal Identification Number
    private String sXXX1;
    private String sXXX2;
    private String sXXX3;
    private String sXXX4;
    
    
    //Constructors
    
    public cls_Teacher(){
        
    }
    
    public cls_Teacher(String sFstName, String sMidName, String sLstName, String sIdNumb, String sEmail, 
            String sHomNumb, String sCelNumb, String sAddMain, String sAddCity, String sAddStat,String sAddZiCo,
            String sCategry, String sStatus, String sStudQty, String sResClas, String sImpClas, String sPayClas, String sComPerc, String sSalary,
            String sHrsCost, String sStuList, String sProfPIN, String sXXX1, String sXXX2, String sXXX3, String sXXX4){
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
        this.sCategry = sCategry;
        this.sStatus = sStatus;
        this.sStudQty = sStudQty;
        this.sResClas = sResClas;
        this.sImpClas = sImpClas;
        this.sPayClas = sPayClas;
        this.sComPerc = sComPerc;
        this.sSalary = sSalary;
        this.sHrsCost = sHrsCost;
        this.sStuList = sStuList;
        this.sProfPIN = sProfPIN;
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
    public void setCategry(String sCategry) {this.sCategry = sCategry;}
    public void setStatus(String sStatus) {this.sStatus = sStatus;}
    public void setStudQty(String sStudQty) {this.sStudQty = sStudQty;}
    public void setResClas(String sResClas) {this.sResClas = sResClas;}
    public void setImpClas(String sImpClas) {this.sImpClas = sImpClas;}
    public void setPayClas(String sPayClas) {this.sPayClas = sPayClas;}
    public void setComPerc(String sComPerc) {this.sComPerc = sComPerc;}
    public void setSalary(String sSalary) {this.sSalary = sSalary;}
    public void setHrsCost(String sHrsCost) {this.sHrsCost = sHrsCost;}
    public void setStuList(String sStuList) {this.sStuList = sStuList;}
    public void setProfPIN(String sProfPIN) {this.sProfPIN = sProfPIN;}
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
    public String getCategry() {return sCategry;}
    public String getStatus() {return sStatus;}
    public String getStudQty() {return sStudQty;}
    public String getResClas() {return sResClas;}
    public String getImpClas() {return sImpClas;}
    public String getPayClas() {return sPayClas;}
    public String getComPerc() {return sComPerc;}
    public String getSalary() {return sSalary;}
    public String getHrsCost() {return sHrsCost;}
    public String getStuList() {return sStuList;}
    public String getProfPIN() {return sProfPIN;}
    public String getXXX1() {return sXXX1;}
    public String getXXX2() {return sXXX2;}
    public String getXXX3() {return sXXX3;}
    public String getXXX4() {return sXXX4;}
    
    
    
    
}
