package club_manager;


public class cls_Cost_Item {
    
    //Monday to Friday Costs
    private String sHourLV;
    private String sSchdLV;
    private String sN1InLV;
    private String sN1G2LV;
    private String sN1G3LV;
    private String sN1G4LV;
    private String sN1GPLV;
    private String sN2InLV;
    private String sN2G2LV;
    private String sN2G3LV;
    private String sN2G4LV;
    private String sN2GPLV;
    private String sHPInLV;
    private String sHPG2LV;
    private String sHPG3LV;
    private String sHPG4LV;
    private String sHPGPLV;
    //Weekend Costs
    private String sHourFS;
    private String sSchdFS;
    private String sN1InFS;
    private String sN1G2FS;
    private String sN1G3FS;
    private String sN1G4FS;
    private String sN1GPFS;
    private String sN2InFS;
    private String sN2G2FS;
    private String sN2G3FS;
    private String sN2G4FS;
    private String sN2GPFS;
    private String sHPInFS;
    private String sHPG2FS;
    private String sHPG3FS;
    private String sHPG4FS;
    private String sHPGPFS;
    //Mini Tennis costs
    private String sHourMT;
    private String sSchdMT;
    private String sN1InMT;
    private String sN1G2MT;
    private String sN1G3MT;
    private String sN1G4MT;
    private String sN1GPMT;
    private String sN2InMT;
    private String sN2G2MT;
    private String sN2G3MT;
    private String sN2G4MT;
    private String sN2GPMT;
    private String sHPInMT;
    private String sHPG2MT;
    private String sHPG3MT;
    private String sHPG4MT;
    private String sHPGPMT;
    //Perf Clinic Costs
    private String sHourCP;
    private String sSchdCP;
    private String sN1InCP;
    private String sN1G2CP;
    private String sN1G3CP;
    private String sN1G4CP;
    private String sN1GPCP;
    private String sN2InCP;
    private String sN2G2CP;
    private String sN2G3CP;
    private String sN2G4CP;
    private String sN2GPCP;
    private String sHPInCP;
    private String sHPG2CP;
    private String sHPG3CP;
    private String sHPG4CP;
    private String sHPGPCP;
    //Children clinic costs
    private String sHourCN;
    private String sSchdCN;
    private String sN1InCN;
    private String sN1G2CN;
    private String sN1G3CN;
    private String sN1G4CN;
    private String sN1GPCN;
    private String sN2InCN;
    private String sN2G2CN;
    private String sN2G3CN;
    private String sN2G4CN;
    private String sN2GPCN;
    private String sHPInCN;
    private String sHPG2CN;
    private String sHPG3CN;
    private String sHPG4CN;
    private String sHPGPCN;
    
    
    

    public cls_Cost_Item(String sHourLV, String sSchdLV, String sN1InLV, String sN1G2LV, String sN1G3LV, 
            String sN1G4LV, String sN1GPLV, String sN2InLV, String sN2G2LV, String sN2G3LV, String sN2G4LV, 
            String sN2GPLV, String sHPInLV, String sHPG2LV, String sHPG3LV, String sHPG4LV, String sHPGPLV, 
            String sHourFS, String sSchdFS, String sN1InFS, String sN1G2FS, String sN1G3FS, String sN1G4FS, 
            String sN1GPFS, String sN2InFS, String sN2G2FS, String sN2G3FS, String sN2G4FS, String sN2GPFS, 
            String sHPInFS, String sHPG2FS, String sHPG3FS, String sHPG4FS, String sHPGPFS,
            String sHourMT, String sSchdMT, String sN1InMT, String sN1G2MT, String sN1G3MT, String sN1G4MT, 
            String sN1GPMT, String sN2InMT, String sN2G2MT, String sN2G3MT, String sN2G4MT, String sN2GPMT, 
            String sHPInMT, String sHPG2MT, String sHPG3MT, String sHPG4MT, String sHPGPMT) {
        this.sHourLV = sHourLV;
        this.sSchdLV = sSchdLV;
        this.sN1InLV = sN1InLV;
        this.sN1G2LV = sN1G2LV;
        this.sN1G3LV = sN1G3LV;
        this.sN1G4LV = sN1G4LV;
        this.sN1GPLV = sN1GPLV;
        this.sN2InLV = sN2InLV;
        this.sN2G2LV = sN2G2LV;
        this.sN2G3LV = sN2G3LV;
        this.sN2G4LV = sN2G4LV;
        this.sN2GPLV = sN2GPLV;
        this.sHPInLV = sHPInLV;
        this.sHPG2LV = sHPG2LV;
        this.sHPG3LV = sHPG3LV;
        this.sHPG4LV = sHPG4LV;
        this.sHPGPLV = sHPGPLV;
        this.sHourFS = sHourFS;
        this.sSchdFS = sSchdFS;
        this.sN1InFS = sN1InFS;
        this.sN1G2FS = sN1G2FS;
        this.sN1G3FS = sN1G3FS;
        this.sN1G4FS = sN1G4FS;
        this.sN1GPFS = sN1GPFS;
        this.sN2InFS = sN2InFS;
        this.sN2G2FS = sN2G2FS;
        this.sN2G3FS = sN2G3FS;
        this.sN2G4FS = sN2G4FS;
        this.sN2GPFS = sN2GPFS;
        this.sHPInFS = sHPInFS;
        this.sHPG2FS = sHPG2FS;
        this.sHPG3FS = sHPG3FS;
        this.sHPG4FS = sHPG4FS;
        this.sHPGPFS = sHPGPFS;
        this.sHourMT = sHourMT;
        this.sSchdMT = sSchdMT;
        this.sN1InMT = sN1InMT;
        this.sN1G2MT = sN1G2MT;
        this.sN1G3MT = sN1G3MT;
        this.sN1G4MT = sN1G4MT;
        this.sN1GPMT = sN1GPMT;
        this.sN2InMT = sN2InMT;
        this.sN2G2MT = sN2G2MT;
        this.sN2G3MT = sN2G3MT;
        this.sN2G4MT = sN2G4MT;
        this.sN2GPMT = sN2GPMT;
        this.sHPInMT = sHPInMT;
        this.sHPG2MT = sHPG2MT;
        this.sHPG3MT = sHPG3MT;
        this.sHPG4MT = sHPG4MT;
        this.sHPGPMT = sHPGPMT;
    }

    public void setHourLV(String sHourLV) {this.sHourLV = sHourLV;}
    public void setSchdLV(String sSchdLV) {this.sSchdLV = sSchdLV;}
    public void setN1InLV(String sN1InLV) {this.sN1InLV = sN1InLV;}
    public void setN1G2LV(String sN1G2LV) {this.sN1G2LV = sN1G2LV;}
    public void setN1G3LV(String sN1G3LV) {this.sN1G3LV = sN1G3LV;}
    public void setN1G4LV(String sN1G4LV) {this.sN1G4LV = sN1G4LV;}
    public void setN1GPLV(String sN1GPLV) {this.sN1GPLV = sN1GPLV;}
    public void setN2InLV(String sN2InLV) {this.sN2InLV = sN2InLV;}
    public void setN2G2LV(String sN2G2LV) {this.sN2G2LV = sN2G2LV;}
    public void setN2G3LV(String sN2G3LV) {this.sN2G3LV = sN2G3LV;}
    public void setN2G4LV(String sN2G4LV) {this.sN2G4LV = sN2G4LV;}
    public void setN2GPLV(String sN2GPLV) {this.sN2GPLV = sN2GPLV;}
    public void setHPInLV(String sHPInLV) {this.sHPInLV = sHPInLV;}
    public void setHPG2LV(String sHPG2LV) {this.sHPG2LV = sHPG2LV;}
    public void setHPG3LV(String sHPG3LV) {this.sHPG3LV = sHPG3LV;}
    public void setHPG4LV(String sHPG4LV) {this.sHPG4LV = sHPG4LV;}
    public void setHPGPLV(String sHPGPLV) {this.sHPGPLV = sHPGPLV;}
    
    public void setHourFS(String sHourFS) {this.sHourFS = sHourFS;}
    public void setSchdFS(String sSchdFS) {this.sSchdFS = sSchdFS;}
    public void setN1InFS(String sN1InFS) {this.sN1InFS = sN1InFS;}
    public void setN1G2FS(String sN1G2FS) {this.sN1G2FS = sN1G2FS;}
    public void setN1G3FS(String sN1G3FS) {this.sN1G3FS = sN1G3FS;}
    public void setN1G4FS(String sN1G4FS) {this.sN1G4FS = sN1G4FS;}
    public void setN1GPFS(String sN1GPFS) {this.sN1GPFS = sN1GPFS;}
    public void setN2InFS(String sN2InFS) {this.sN2InFS = sN2InFS;}
    public void setN2G2FS(String sN2G2FS) {this.sN2G2FS = sN2G2FS;}
    public void setN2G3FS(String sN2G3FS) {this.sN2G3FS = sN2G3FS;}
    public void setN2G4FS(String sN2G4FS) {this.sN2G4FS = sN2G4FS;}
    public void setN2GPFS(String sN2GPFS) {this.sN2GPFS = sN2GPFS;}
    public void setHPInFS(String sHPInFS) {this.sHPInFS = sHPInFS;}
    public void setHPG2FS(String sHPG2FS) {this.sHPG2FS = sHPG2FS;}
    public void setHPG3FS(String sHPG3FS) {this.sHPG3FS = sHPG3FS;}
    public void setHPG4FS(String sHPG4FS) {this.sHPG4FS = sHPG4FS;}
    public void setHPGPFS(String sHPGPFS) {this.sHPGPFS = sHPGPFS;}
    
    public void setHourMT(String sHourMT) {this.sHourMT = sHourMT;}
    public void setSchdMT(String sSchdMT) {this.sSchdMT = sSchdMT;}
    public void setN1InMT(String sN1InMT) {this.sN1InMT = sN1InMT;}
    public void setN1G2MT(String sN1G2MT) {this.sN1G2MT = sN1G2MT;}
    public void setN1G3MT(String sN1G3MT) {this.sN1G3MT = sN1G3MT;}
    public void setN1G4MT(String sN1G4MT) {this.sN1G4MT = sN1G4MT;}
    public void setN1GPMT(String sN1GPMT) {this.sN1GPMT = sN1GPMT;}
    public void setN2InMT(String sN2InMT) {this.sN2InMT = sN2InMT;}
    public void setN2G2MT(String sN2G2MT) {this.sN2G2MT = sN2G2MT;}
    public void setN2G3MT(String sN2G3MT) {this.sN2G3MT = sN2G3MT;}
    public void setN2G4MT(String sN2G4MT) {this.sN2G4MT = sN2G4MT;}
    public void setN2GPMT(String sN2GPMT) {this.sN2GPMT = sN2GPMT;}
    public void setHPInMT(String sHPInMT) {this.sHPInMT = sHPInMT;}
    public void setHPG2MT(String sHPG2MT) {this.sHPG2MT = sHPG2MT;}
    public void setHPG3MT(String sHPG3MT) {this.sHPG3MT = sHPG3MT;}
    public void setHPG4MT(String sHPG4MT) {this.sHPG4MT = sHPG4MT;}
    public void setHPGPMT(String sHPGPMT) {this.sHPGPMT = sHPGPMT;}

    
    public String getHourLV() {return sHourLV;}
    public String getSchdLV() {return sSchdLV;}
    public String getN1InLV() {return sN1InLV;}
    public String getN1G2LV() {return sN1G2LV;}
    public String getN1G3LV() {return sN1G3LV;}
    public String getN1G4LV() {return sN1G4LV;}
    public String getN1GPLV() {return sN1GPLV;}
    public String getN2InLV() {return sN2InLV;}
    public String getN2G2LV() {return sN2G2LV;}
    public String getN2G3LV() {return sN2G3LV;}
    public String getN2G4LV() {return sN2G4LV;}
    public String getN2GPLV() {return sN2GPLV;}
    public String getHPInLV() {return sHPInLV;}
    public String getHPG2LV() {return sHPG2LV;}
    public String getHPG3LV() {return sHPG3LV;}
    public String getHPG4LV() {return sHPG4LV;}
    public String getHPGPLV() {return sHPGPLV;}
    
    public String getHourFS() {return sHourFS;}
    public String getSchdFS() {return sSchdFS;}
    public String getN1InFS() {return sN1InFS;}
    public String getN1G2FS() {return sN1G2FS;}
    public String getN1G3FS() {return sN1G3FS;}
    public String getN1G4FS() {return sN1G4FS;}
    public String getN1GPFS() {return sN1GPFS;}
    public String getN2InFS() {return sN2InFS;}
    public String getN2G2FS() {return sN2G2FS;}
    public String getN2G3FS() {return sN2G3FS;}
    public String getN2G4FS() {return sN2G4FS;}
    public String getN2GPFS() {return sN2GPFS;}
    public String getHPInFS() {return sHPInFS;}
    public String getHPG2FS() {return sHPG2FS;}
    public String getHPG3FS() {return sHPG3FS;}
    public String getHPG4FS() {return sHPG4FS;}
    public String getHPGPFS() {return sHPGPFS;}
    
    public String getHourMT() {return sHourMT;}
    public String getSchdMT() {return sSchdMT;}
    public String getN1InMT() {return sN1InMT;}
    public String getN1G2MT() {return sN1G2MT;}
    public String getN1G3MT() {return sN1G3MT;}
    public String getN1G4MT() {return sN1G4MT;}
    public String getN1GPMT() {return sN1GPMT;}
    public String getN2InMT() {return sN2InMT;}
    public String getN2G2MT() {return sN2G2MT;}
    public String getN2G3MT() {return sN2G3MT;}
    public String getN2G4MT() {return sN2G4MT;}
    public String getN2GPMT() {return sN2GPMT;}
    public String getHPInMT() {return sHPInMT;}
    public String getHPG2MT() {return sHPG2MT;}
    public String getHPG3MT() {return sHPG3MT;}
    public String getHPG4MT() {return sHPG4MT;}
    public String getHPGPMT() {return sHPGPMT;}

    
    
    
    
    
    
    
    
    
    
}
