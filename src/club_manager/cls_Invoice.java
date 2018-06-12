package club_manager;

//Libraries section

public class cls_Invoice {
    
    //Variables declaration section
    
    private String sInvDate;
    private String sInvNumb;
    private String sCustPIN;
    private String sCusName;
    private String sTeaName;
    private String sCusLevl;
    private String sCusPhon;
    private String sCusMail;
    private String sVenName;
    private String sModName;
    private String sDayName;
    private String sPaqName;
    private String sSchName;
    private String sItACode;
    private String sItA_Qty;
    private String sItADesc;
    private String sItAUnit;
    private String sItATotl;
    private String sItBCode;
    private String sItB_Qty;
    private String sItBDesc;
    private String sItBUnit;
    private String sItBTotl;
    private String sItCCode;
    private String sItC_Qty;
    private String sItCDesc;
    private String sItCUnit;
    private String sItCTotl;
    private String sItDCode;
    private String sItD_Qty;
    private String sItDDesc;
    private String sItDUnit;
    private String sItDTotl;
    private String sInvDesc;
    private String sDescVal;
    private String sInvTaxe;
    private String sTaxeVal;
    private String sSubTotl;
    private String sSubtTax;
    private String sSubtDes;
    private String sInvTotl;
    private String sNullInv;
    private String sPayMeth;
    private String sDRCCode;
    private String sXXX1;
    private String sXXX2;
    private String sXXX3;
        
    
    //Constructors section
    
    public cls_Invoice(String sInvDate, String sInvNumb, String sCustPIN, String sCusName, String sTeaName, String sCusLevl, 
            String sCusPhon, String sCusMail, String sVenName, String sModName, String sDayName, String sPaqName, String sSchName, 
            String sItACode, String sItA_Qty, String sItADesc, String sItAUnit, String sItATotl, String sItBCode, String sItB_Qty, 
            String sItBDesc, String sItBUnit, String sItBTotl, String sItCCode, String sItC_Qty, String sItCDesc, String sItCUnit, 
            String sItCTotl, String sItDCode, String sItD_Qty, String sItDDesc, String sItDUnit, String sItDTotl, String sInvDesc, 
            String sDescVal, String sInvTaxe, String sTaxeVal, String sSubTotl, String sSubtTax, String sSubtDes, String sInvTotl, 
            String sNullInv, String sPayMeth, String sDRCCode, String sXXX1, String sXXX2, String sXXX3) {
        this.sInvDate = sInvDate;
        this.sInvNumb = sInvNumb;
        this.sCustPIN = sCustPIN;
        this.sCusName = sCusName;
        this.sTeaName = sTeaName;
        this.sCusLevl = sCusLevl;
        this.sCusPhon = sCusPhon;
        this.sCusMail = sCusMail;
        this.sVenName = sVenName;
        this.sModName = sModName;
        this.sDayName = sDayName;
        this.sPaqName = sPaqName;
        this.sSchName = sSchName;
        this.sItACode = sItACode;
        this.sItA_Qty = sItA_Qty;
        this.sItADesc = sItADesc;
        this.sItAUnit = sItAUnit;
        this.sItATotl = sItATotl;
        this.sItBCode = sItBCode;
        this.sItB_Qty = sItB_Qty;
        this.sItBDesc = sItBDesc;
        this.sItBUnit = sItBUnit;
        this.sItBTotl = sItBTotl;
        this.sItCCode = sItCCode;
        this.sItC_Qty = sItC_Qty;
        this.sItCDesc = sItCDesc;
        this.sItCUnit = sItCUnit;
        this.sItCTotl = sItCTotl;
        this.sItDCode = sItDCode;
        this.sItD_Qty = sItD_Qty;
        this.sItDDesc = sItDDesc;
        this.sItDUnit = sItDUnit;
        this.sItDTotl = sItDTotl;
        this.sInvDesc = sInvDesc;
        this.sDescVal = sDescVal;
        this.sInvTaxe = sInvTaxe;
        this.sTaxeVal = sTaxeVal;
        this.sSubTotl = sSubTotl;
        this.sSubtTax = sSubtTax;
        this.sSubtDes = sSubtDes;
        this.sInvTotl = sInvTotl;
        this.sNullInv = sNullInv;
        this.sPayMeth = sPayMeth;
        this.sDRCCode = sDRCCode;        
        this.sXXX1 = sXXX1;
        this.sXXX2 = sXXX2;
        this.sXXX3 = sXXX3;        
    }
    
    //Methods secion

    public void setInvDate(String sInvDate) {this.sInvDate = sInvDate;}
    public void setInvNumb(String sInvNumb) {this.sInvNumb = sInvNumb;}
    public void setCustPIN(String sCustPIN) {this.sCustPIN = sCustPIN;}
    public void setCusName(String sCusName) {this.sCusName = sCusName;}
    public void setTeaName(String sTeaName) {this.sTeaName = sTeaName;}
    public void setCusLevl(String sCusLevl) {this.sCusLevl = sCusLevl;}
    public void setCusPhon(String sCusPhon) {this.sCusPhon = sCusPhon;}
    public void setCusMail(String sCusMail) {this.sCusMail = sCusMail;}
    public void setVenName(String sVenName) {this.sVenName = sVenName;}
    public void setModName(String sModName) {this.sModName = sModName;}
    public void setDayName(String sDayName) {this.sDayName = sDayName;}
    public void setPaqName(String sPaqName) {this.sPaqName = sPaqName;}
    public void setSchName(String sSchName) {this.sSchName = sSchName;}
    public void setItACode(String sItACode) {this.sItACode = sItACode;}
    public void setItA_Qty(String sItA_Qty) {this.sItA_Qty = sItA_Qty;}
    public void setItADesc(String sItADesc) {this.sItADesc = sItADesc;}
    public void setItAUnit(String sItAUnit) {this.sItAUnit = sItAUnit;}
    public void setItATotl(String sItATotl) {this.sItATotl = sItATotl;}
    public void setItBCode(String sItBCode) {this.sItBCode = sItBCode;}
    public void setItB_Qty(String sItB_Qty) {this.sItB_Qty = sItB_Qty;}
    public void setItBDesc(String sItBDesc) {this.sItBDesc = sItBDesc;}
    public void setItBUnit(String sItBUnit) {this.sItBUnit = sItBUnit;}
    public void setItBTotl(String sItBTotl) {this.sItBTotl = sItBTotl;}
    public void setItCCode(String sItCCode) {this.sItCCode = sItCCode;}
    public void setItC_Qty(String sItC_Qty) {this.sItC_Qty = sItC_Qty;}
    public void setItCDesc(String sItCDesc) {this.sItCDesc = sItCDesc;}
    public void setItCUnit(String sItCUnit) {this.sItCUnit = sItCUnit;}
    public void setItCTotl(String sItCTotl) {this.sItCTotl = sItCTotl;}
    public void setItDCode(String sItDCode) {this.sItDCode = sItDCode;}
    public void setItD_Qty(String sItD_Qty) {this.sItD_Qty = sItD_Qty;}
    public void setItDDesc(String sItDDesc) {this.sItDDesc = sItDDesc;}
    public void setItDUnit(String sItDUnit) {this.sItDUnit = sItDUnit;}
    public void setItDTotl(String sItDTotl) {this.sItDTotl = sItDTotl;}
    public void setInvDesc(String sInvDesc) {this.sInvDesc = sInvDesc;}
    public void setIescVal(String sDescVal) {this.sDescVal = sDescVal;}
    public void setInvTaxe(String sInvTaxe) {this.sInvTaxe = sInvTaxe;}
    public void setTaxeVal(String sTaxeVal) {this.sTaxeVal = sTaxeVal;}
    public void setSubTotl(String sSubTotl) {this.sSubTotl = sSubTotl;}
    public void setSubtTax(String sSubtTax) {this.sSubtTax = sSubtTax;}
    public void setSubtDes(String sSubtDes) {this.sSubtDes = sSubtDes;}
    public void setInvTotl(String sInvTotl) {this.sInvTotl = sInvTotl;}
    public void setNullInv(String sNullInv) {this.sNullInv = sNullInv;}
    public void setPayMeth(String sPayMeth) {this.sPayMeth = sPayMeth;}
    public void setDRCCode(String sDRCCode) {this.sDRCCode = sDRCCode;}
    public void setXX1(String sXXX1) {this.sXXX1 = sXXX1;}
    public void setXX2(String sXXX2) {this.sXXX2 = sXXX2;}
    public void setXX3(String sXXX3) {this.sXXX3 = sXXX3;}
            
    
    public String getInvDate() {return sInvDate;}
    public String getInvNumb() {return sInvNumb;}
    public String getCustPIN() {return sCustPIN;}
    public String getCusName() {return sCusName;}
    public String getTeaName() {return sTeaName;}
    public String getCusLevl() {return sCusLevl;}
    public String getCusPhon() {return sCusPhon;}
    public String getCusMail() {return sCusMail;}
    public String getVenName() {return sVenName;}
    public String getModName() {return sModName;}
    public String getDayName() {return sDayName;}
    public String getPaqName() {return sPaqName;}
    public String getSchName() {return sSchName;}
    public String getItACode() {return sItACode;}
    public String getItA_Qty() {return sItA_Qty;}
    public String getItADesc() {return sItADesc;}
    public String getItAUnit() {return sItAUnit;}
    public String getItATotl() {return sItATotl;}
    public String getItBCode() {return sItBCode;}
    public String getItB_Qty() {return sItB_Qty;}
    public String getItBDesc() {return sItBDesc;}
    public String getItBUnit() {return sItBUnit;}
    public String getItBTotl() {return sItBTotl;}
    public String getItCCode() {return sItCCode;}
    public String getItC_Qty() {return sItC_Qty;}
    public String getItCDesc() {return sItCDesc;}
    public String getItCUnit() {return sItCUnit;}
    public String getItCTotl() {return sItCTotl;}
    public String getItDCode() {return sItDCode;}
    public String getItD_Qty() {return sItD_Qty;}
    public String getItDDesc() {return sItDDesc;}
    public String getItDUnit() {return sItDUnit;}
    public String getItDTotl() {return sItDTotl;}
    public String getInvDesc() {return sInvDesc;}
    public String getDescVal() {return sDescVal;}
    public String getInvTaxe() {return sInvTaxe;}
    public String getTaxeVal() {return sTaxeVal;}
    public String getSubTotl() {return sSubTotl;}
    public String getSubtTax() {return sSubtTax;}
    public String getSubtDes() {return sSubtDes;}
    public String getInvTotl() {return sInvTotl;}
    public String getNullInv() {return sNullInv;}
    public String getPayMeth() {return sPayMeth;}
    public String getDRCCode() {return sDRCCode;}   
    public String getXXX1() {return sXXX1;}
    public String getXXX2() {return sXXX2;}
    public String getXXX3() {return sXXX3;}
    
    
}
