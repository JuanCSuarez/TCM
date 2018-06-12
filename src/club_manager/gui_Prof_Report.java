package club_manager;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import jxl.write.WriteException;


public class gui_Prof_Report extends javax.swing.JFrame {
    
    javax.swing.table.DefaultTableModel tblModelProRep = new javax.swing.table.DefaultTableModel();
    Object[] ProRepColumn = new Object [12];
    
    //ArrayList that will store the complete data bases
    private ArrayList<cls_Teacher> alProfDB = new ArrayList<>();
    private ArrayList<cls_Invoice> alInvoDB = new ArrayList<>();
    private ArrayList<cls_Student> alStudDB = new ArrayList<>();
    
    //Data Bases Paths
    private String sLocProfDBPath;
    private String sLocStudDBPath;
    
    //Lesson
    private String sPayLess = "";
    private String sImpLess = "";
    private String sTotPaym = "";
    
    //DRC decoded data variables
    private String sDRCSPn = "";//Student´s PIN
    private String sDRCDat = "";
    private String sDRCMod = "";
    private String sDRCDay = "";
    private String sDRCPaq = "";
    private String sDRCHrs = "";
    private String sDRCRes = "";//Reserved Lessons
    private String sDRCRec = "";//Received Lessons
    private String sDRCPay = "";//Payed Lessons
    private String sDRCTLv = "";//Teacher Level
    private String sDRCTPn = "";//Teacher´s PIN
    
    private String sProfPIN = "";
    
    public gui_Prof_Report(ArrayList<cls_Invoice> alInvoDB, String sLocProflDBPath, ArrayList<cls_Teacher> alTmpTeacherDB, 
            String sLocStudDBPath, ArrayList<cls_Student> alTmpStudentDB, String sProfPIN) {
        initComponents();
        System.out.println("Starting Reserves Control Module");
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("TCM - REPORTE DE ESTADO DEL PROFESOR");
        
        this.alInvoDB = alInvoDB;
        this.sLocProfDBPath = sLocProflDBPath;
        this.alProfDB = alTmpTeacherDB;
        this.sLocStudDBPath = sLocStudDBPath;
        this.alStudDB = alTmpStudentDB;
        this.sProfPIN = sProfPIN;
        configProfRepTable();
        
    }

    private gui_Prof_Report() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
        
    //Prepares the JTable columns in order to receive the list of Reserves and Invoice Lines
    private void configProfRepTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelProRep.addColumn("Alumno");
        tblModelProRep.addColumn("Reserva");
        tblModelProRep.addColumn("Factura");
        tblModelProRep.addColumn("Fecha");
        tblModelProRep.addColumn("MES");
        tblModelProRep.addColumn("Tarifa diaria");
        tblModelProRep.addColumn("Cant. Lecciones");
        tblModelProRep.addColumn("% Profesor");
        tblModelProRep.addColumn("Lecc. Pagadas");
        tblModelProRep.addColumn("Lecc. Impartidas");
        tblModelProRep.addColumn("Lecc. Pendientes");
        tblModelProRep.addColumn("Monto Profesor");
        jtblProRep.setModel(tblModelProRep);
        //Allows the user to sort the items by Column
        jtblProRep.setAutoCreateRowSorter(true);        
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblProRep.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.ORANGE);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblProRep.setAutoResizeMode(jtblProRep.AUTO_RESIZE_OFF);
        jtblProRep.setRowHeight(24);
        jtblProRep.getColumnModel().getColumn(0).setPreferredWidth(170);
        jtblProRep.getColumnModel().getColumn(0).setResizable(false);
        jtblProRep.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(1).setPreferredWidth(290);
        jtblProRep.getColumnModel().getColumn(1).setResizable(false);
        jtblProRep.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(2).setPreferredWidth(90);
        jtblProRep.getColumnModel().getColumn(2).setResizable(false);
        jtblProRep.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(3).setPreferredWidth(100);
        jtblProRep.getColumnModel().getColumn(3).setResizable(false);
        jtblProRep.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(4).setPreferredWidth(100);
        jtblProRep.getColumnModel().getColumn(4).setResizable(false);
        jtblProRep.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);        
        jtblProRep.getColumnModel().getColumn(5).setPreferredWidth(120);
        jtblProRep.getColumnModel().getColumn(5).setResizable(false);
        jtblProRep.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtblProRep.getColumnModel().getColumn(6).setResizable(false);
        jtblProRep.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(7).setPreferredWidth(100);
        jtblProRep.getColumnModel().getColumn(7).setResizable(false);
        jtblProRep.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(8).setPreferredWidth(100);
        jtblProRep.getColumnModel().getColumn(8).setResizable(false);
        jtblProRep.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(9).setPreferredWidth(100);
        jtblProRep.getColumnModel().getColumn(9).setResizable(false);
        jtblProRep.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(10).setPreferredWidth(100);
        jtblProRep.getColumnModel().getColumn(10).setResizable(false);
        jtblProRep.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        jtblProRep.getColumnModel().getColumn(11).setPreferredWidth(120);
        jtblProRep.getColumnModel().getColumn(11).setResizable(false);
        jtblProRep.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Cleans the Teachers List JTable
    private void cleanProfRepTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelProRep.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelProRep.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar el Reporte de Profesore\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Fulfills the Invoice list according with the given date range
    private void loadProfRepTable(String sIniDat, String sFinDat, String sProfPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        System.out.println("Loading data from Invoice ArralyList into JTable");
        //Receives the range of dates and adds the necessary zeroes
        sIniDat = sIniDat + " 00:00";
        sFinDat = sFinDat + " 00:00";
        System.out.println("Initial date: " + sIniDat);
        System.out.println("Final date: " + sFinDat);
        //Prepares the variable for each Invoice date (zeroes will be added later)
        String sInvDat = "";
        String sY = "", sM = "", sD = "";
        //Values to calculate the Prof salary
        double dPercent = 0;
        double dImpLess = 0;
        double dValLess = 0;
        double dAccSala = 0;
        for ( cls_Invoice tmp: this.alInvoDB ){
            System.out.println("Checking first Date: " + tmp.getInvDate());
            sY = this.getYear(tmp.getInvDate());
            //sY = tmp.getInvDate().substring(8);
            System.out.println(sY);
            sM = this.getMonth(tmp.getInvDate());
            //sM = tmp.getInvDate().substring(4, 7);
            System.out.println(sM);
            sD = this.getDate(tmp.getInvDate());
            //sD = tmp.getInvDate().substring(1, 3);
            System.out.println(sD);
            sInvDat = sY + "-" + sM + "-" + sD + " 00:00";
            //sInvDat = sY + "-" + sM + "-" + sD + " 00:00";
            System.out.println("Inv Date: " + sInvDat);
            if ( checkDate(sIniDat, sInvDat) == true && checkDate(sInvDat, sFinDat) == true ){
                System.out.println("Date into the range");
                if ( tmp.getItACode().equals(sProfPIN) ){
                    ProRepColumn[0] = tmp.getCusName();
                    ProRepColumn[1] = tmp.getItADesc();
                    ProRepColumn[2] = tmp.getInvNumb();
                    ProRepColumn[3] = tmp.getInvDate();
                    ProRepColumn[4] = tmp.getInvDate().substring(3, 6);
                    ProRepColumn[5] = tmp.getItAUnit();
                    ProRepColumn[6] = tmp.getItA_Qty();
                    ProRepColumn[7] = getProfPercentaje(tmp.getItACode());
                    getLessonsData(tmp.getDRCCode(), tmp.getCustPIN());
                    ProRepColumn[8] = sPayLess;
                    ProRepColumn[9] = sImpLess;
                    ProRepColumn[10] = String.valueOf(Integer.valueOf(sPayLess) - Integer.valueOf(sImpLess));
                    dPercent = Double.parseDouble(getProfPercentaje(tmp.getItACode()));
                    dImpLess = Double.parseDouble(sImpLess);
                    dValLess = Double.parseDouble(tmp.getItAUnit());
                    dAccSala = roundValue((dPercent/100)*dValLess*dImpLess);
                    System.out.println("Prof Mount: " + dAccSala);
                    ProRepColumn[11] = String.valueOf(dAccSala);
                    tblModelProRep.addRow(ProRepColumn);
                    jtblProRep.setModel(tblModelProRep);    
                }
                if ( tmp.getItBCode().equals(sProfPIN) ){
                    ProRepColumn[0] = tmp.getCusName();
                    ProRepColumn[1] = tmp.getItBDesc();
                    ProRepColumn[2] = tmp.getInvNumb();
                    ProRepColumn[3] = tmp.getInvDate();
                    ProRepColumn[4] = tmp.getInvDate().substring(3, 6);
                    ProRepColumn[5] = tmp.getItBUnit();
                    ProRepColumn[6] = tmp.getItB_Qty();
                    ProRepColumn[7] = getProfPercentaje(tmp.getItBCode());
                    getLessonsData(tmp.getDRCCode(), tmp.getCustPIN());
                    ProRepColumn[8] = sPayLess;
                    ProRepColumn[9] = sImpLess;
                    ProRepColumn[10] = String.valueOf(Integer.valueOf(sPayLess) - Integer.valueOf(sImpLess));
                    ProRepColumn[11] = roundValue(Double.valueOf(tmp.getItBUnit()) * Double.valueOf(sImpLess) * Double.valueOf(Integer.valueOf(getProfPercentaje(tmp.getItBCode())) / 100));
                    tblModelProRep.addRow(ProRepColumn);
                    jtblProRep.setModel(tblModelProRep);    
                }
                if ( tmp.getItCCode().equals(sProfPIN) ){
                    ProRepColumn[0] = tmp.getCusName();
                    ProRepColumn[1] = tmp.getItCDesc();
                    ProRepColumn[2] = tmp.getInvNumb();
                    ProRepColumn[3] = tmp.getInvDate();
                    ProRepColumn[4] = tmp.getInvDate().substring(3, 6);
                    ProRepColumn[5] = tmp.getItCUnit();
                    ProRepColumn[6] = tmp.getItC_Qty();
                    ProRepColumn[7] = getProfPercentaje(tmp.getItCCode());
                    getLessonsData(tmp.getDRCCode(), tmp.getCustPIN());
                    ProRepColumn[8] = sPayLess;
                    ProRepColumn[9] = sImpLess;
                    ProRepColumn[10] = String.valueOf(Integer.valueOf(sPayLess) - Integer.valueOf(sImpLess));
                    ProRepColumn[11] = roundValue(Double.valueOf(tmp.getItCUnit()) * Double.valueOf(sImpLess) * Double.valueOf(Integer.valueOf(getProfPercentaje(tmp.getItCCode())) / 100));
                    tblModelProRep.addRow(ProRepColumn);
                    jtblProRep.setModel(tblModelProRep);    
                }
                if ( tmp.getItDCode().equals(sProfPIN) ){
                    ProRepColumn[0] = tmp.getCusName();
                    ProRepColumn[1] = tmp.getItDDesc();
                    ProRepColumn[2] = tmp.getInvNumb();
                    ProRepColumn[3] = tmp.getInvDate();
                    ProRepColumn[4] = tmp.getInvDate().substring(3, 6);
                    ProRepColumn[5] = tmp.getItDUnit();
                    ProRepColumn[6] = tmp.getItD_Qty();
                    ProRepColumn[7] = getProfPercentaje(tmp.getItDCode());
                    getLessonsData(tmp.getDRCCode(), tmp.getCustPIN());
                    ProRepColumn[8] = sPayLess;
                    ProRepColumn[9] = sImpLess;
                    ProRepColumn[10] = String.valueOf(Integer.valueOf(sPayLess) - Integer.valueOf(sImpLess));
                    ProRepColumn[11] = roundValue(Double.valueOf(tmp.getItDUnit()) * Double.valueOf(sImpLess) * Double.valueOf(Integer.valueOf(getProfPercentaje(tmp.getItDCode())) / 100));
                    tblModelProRep.addRow(ProRepColumn);
                    jtblProRep.setModel(tblModelProRep);    
                }
                sInvDat = "";
            }
        }
        System.out.println("Invoice Data Base loaded in the Profesor Report's JTable");
    }
    //</editor-fold>
    
    //Gets the System's current date
    private String getCurrentDate(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sDate = "";
        Calendar cal = new GregorianCalendar();
        //Creating time variables for the date. Obtaining values form the System's clock
        String sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String sMon = String.valueOf(cal.get(Calendar.MONTH));
        String sYea = String.valueOf(cal.get(Calendar.YEAR));
        //formats the day to two digits
        if ( sDay.length()==1 ){sDay = "0" + sDay;}
        //Changes the month's number to the month's name
        switch (sMon){
            case "0" : {sMon = "Ene";break;}
            case "1" : {sMon = "Feb";break;}
            case "2" : {sMon = "Mar";break;}
            case "3" : {sMon = "Abr";break;}
            case "4" : {sMon = "May";break;}
            case "5" : {sMon = "Jun";break;}
            case "6" : {sMon = "Jul";break;}
            case "7" : {sMon = "Ago";break;}
            case "8" : {sMon = "Sep";break;}
            case "9" : {sMon = "Oct";break;}
            case "10" : {sMon = "Nov";break;}
            case "11" : {sMon = "Dic";break;}
            default : {sMon = "NA";break;}
        }
         //Prepares the text to be displayed in the date label on the main screen
        sDate = sYea + "-" + sMon + "-" + sDay;
        return sDate;
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="METHODS TO GET YEAR, MONTH AND DAY FROM THE DATE">
    private String getYear(String sDate){
        System.out.println("Received Date: " + sDate);
        String sY = "";
         int iPos = 7;
        do{
            sY = sY + sDate.charAt(iPos);
            iPos++;
        } while ( iPos < sDate.length());
        return sY;
    }
    
    private String getMonth(String sDate){
        System.out.println("Received Date: " + sDate);
        String sM = "";
        int iPos = 3;
        do{
            sM = sM + sDate.charAt(iPos);
            iPos++;
        } while ( sDate.charAt(iPos) != '-');
        sM = spanishMonth(sM);
        return sM;
    }
    
    private String getDate(String sDate){
        System.out.println("Received Date: " + sDate);
        String sD = "";
        int iPos = 0;
        do{
            sD = sD + sDate.charAt(iPos);
            iPos++;
        } while ( sDate.charAt(iPos) != '-');
        return sD;
    }
    //</editor-fold>
    
    
    //Converts a given String Month name MMM from English to Spanish
    private String spanishMonth(String sMonth){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        switch (sMonth){
            case "Jan" :{sMonth = "Ene"; break;}
            case "Apr" :{sMonth = "Abr"; break;}
            case "Dec" :{sMonth = "Dic"; break;}
        }
        return sMonth;
    }
    //</editor-fold>
    
    //Identifies if a given Date corresponds to the given range
    private boolean checkDate(String sIniDat, String sFinDat){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        boolean bFlag = false;
        cls_Date_Manager tmpDM = new cls_Date_Manager();
        Date dIniDat = tmpDM.string_toDate(sIniDat);
        Date dFinDat = tmpDM.string_toDate(sFinDat);
        int iDifMin = tmpDM.getMinsBetween(dIniDat, dFinDat, false);
        if ( iDifMin >= 0 ){
            bFlag = true;
        }
        return bFlag;
    }
    //</editor-fold>
    
    //Identifies the Prof % from the Prof ArrayList
    private String getProfPercentaje(String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sPerc = "";
        int iPos = findTeacher(sPIN);
        sPerc = alProfDB.get(iPos).getComPerc();
        return sPerc;
    }
    //</editor-fold>
    
    //Identifies the Teacher´s line number from the Teachers ArraList, depending on the PIN value
    private int findTeacher(String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        int iIndex = -1;
        int i = 0;
        for ( cls_Teacher tmp : alProfDB ){
            if ( tmp.getProfPIN().equals(sPIN) ){
                iIndex = i;
            }
            i++;
        }
        return iIndex;
    }
    //</editor-fold>
    
    //Identifies the Student´s line number from the Students ArraList, depending on the PIN value
    private int findStudent(String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int iLine = -1;
        int i = 0; //Index
        for(cls_Student tmp: this.alStudDB){
            if ( tmp.getStudPIN().equals(sPIN) ){
                iLine = i;
            }
            i++;
        }
        return iLine;
    }
    //</editor-fold>
    
    //Gets the total amount of payed lessons and imparted lessons according with the given mDRC
    private void getLessonsData(String sMDRC, String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        int i = findStudent(sPIN);
        int iPos = 0;
        String tmpRSV = "";
        String sMiniDRC = "";
        do{
            tmpRSV = "";
            do{
                tmpRSV = tmpRSV + alStudDB.get(i).getReservs().charAt(iPos);
                iPos++;
            } while(alStudDB.get(i).getReservs().charAt(iPos) != '>');
            System.out.println("Resulting DRC: " + tmpRSV);
            //Getting mDRC from the obtained DRC
            sMiniDRC = tmpRSV.substring(0,23) + tmpRSV.substring(35);
            System.out.println("Resulting mDRC: " + sMDRC);
            //Compares if the extracted mDRC is the same as the wanted one
            if ( sMiniDRC.equals(sMDRC) ){//If it is...
                //Decodes the extracted DRC
                decodeDRC(tmpRSV);
                sPayLess = this.sDRCPay;
                sImpLess = this.sDRCRec;
            }
            iPos++;
        }while( iPos < alStudDB.get(i).getReservs().length() );
        iPos = 0;
    }
    //</editor-fold>
    
    
    //Decodes the data from the DRC (Dynamic Reservation Code)
    private void decodeDRC(String sDRC){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Gets the Student´s PIN
        this.sDRCSPn = sDRC.substring(0, 5);
        //Gets the date when the reservation was received
        String sDay = sDRC.substring(6,8);
        String sMon = sDRC.substring(8,10);
        String sYea = sDRC.substring(10,12);
        switch (sMon){
            case "01":{sMon = "Ene";break;}
            case "02":{sMon = "Feb";break;}
            case "03":{sMon = "Mar";break;}
            case "04":{sMon = "Abr";break;}
            case "05":{sMon = "May";break;}
            case "06":{sMon = "Jun";break;}
            case "07":{sMon = "Jul";break;}
            case "08":{sMon = "Ago";break;}
            case "09":{sMon = "Sep";break;}
            case "10":{sMon = "Oct";break;}
            case "11":{sMon = "Nov";break;}
            case "12":{sMon = "Dic";break;}
        }
        this.sDRCDat = sDay + "-" + sMon + "-" + "20" + sYea;
        //Gets the paquet mode
        this.sDRCMod = sDRC.substring(13,16);
        switch (sDRCMod){
            case "STD":{sDRCMod = "Standard";break;}
            case "CLP":{sDRCMod = "Clín. de Perfecc.";break;}
            case "CLN":{sDRCMod = "Clínica de Niños";break;}
            case "MNT":{sDRCMod = "Mini Tennis";break;}
        }
        //Gets the Paquest Day
        this.sDRCDay = sDRC.substring(16,18);
        switch (sDRCDay){
            case "LV":{sDRCDay = "L-V";break;}
            case "FS":{sDRCDay = "S-D";break;}
        }
        //Gets the Paquet type
        this.sDRCPaq = sDRC.substring(18, 21);
        switch (sDRCPaq){
            case "IND":{sDRCPaq = "Individual";break;}
            case "G2P":{sDRCPaq = "Grupal 2P";break;}
            case "G3P":{sDRCPaq = "Grupal 3P";break;}
            case "G4P":{sDRCPaq = "Grupal 4P";break;}
            case "G5+":{sDRCPaq = "Grupal 5+";break;}
        }
        //Gets the paquet day schedule
        this.sDRCHrs = sDRC.substring(21, 23);
        switch (sDRCHrs){
            case "MN":{sDRCHrs = "Mañana";break;}
            case "DI":{sDRCHrs = "Día";break;}
            case "TD":{sDRCHrs = "Tarde";break;}
            case "NC":{sDRCHrs = "Noche";break;}
        }
        //Gets the reserved lessons
        this.sDRCRes = sDRC.substring(24, 27);
        //Gets the received lessons
        this.sDRCRec = sDRC.substring(28, 31);
        //Gets the payed lessons
        this.sDRCPay = sDRC.substring(32, 35);
        //Gets the Teacher´s Level (Category)
        this.sDRCTLv = sDRC.substring(36, 38);
        switch (sDRCTLv){
            case "N1":{sDRCTLv = "Nivel 1";break;}
            case "N2":{sDRCTLv = "Nivel 2";break;}
            case "HP":{sDRCTLv = "HeadPro";break;}
        }
        //Gets the Teacher´s PIN
        this.sDRCTPn = sDRC.substring(38);
    }
    //</editor-fold>
    
    //Rounds the Double Value to 2 decimals
    public double roundValue(double dValue){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        BigDecimal bd = new BigDecimal(dValue);
        //rounds the value to 2 decimals
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    //</editor-fold>
    
     //Calculates the total payment for the professor according with the values on the screen
    private void calculateTotPaym(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        double dTotPaym = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblProRep.getRowCount(); i++ ){
            dTotPaym = dTotPaym + Double.parseDouble(jtblProRep.getValueAt(i, 11).toString());
        }
        dTotPaym = roundValue(dTotPaym);
        sTotPaym = String.valueOf(dTotPaym);
    }
    //</editor-fold>
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlTop = new javax.swing.JPanel();
        jlblTop = new javax.swing.JLabel();
        jpnlMid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProRep = new javax.swing.JTable();
        jpnlBot = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnExp = new javax.swing.JButton();
        jpnlMid1 = new javax.swing.JPanel();
        jlblIni = new javax.swing.JLabel();
        jcboxIniDay = new java.awt.Choice();
        jcboxIniMon = new java.awt.Choice();
        jcboxIniYea = new java.awt.Choice();
        jlblFin = new javax.swing.JLabel();
        jcboxFinDay = new java.awt.Choice();
        jcboxFinMon = new java.awt.Choice();
        jcboxFinYea = new java.awt.Choice();
        jlblSpec = new javax.swing.JLabel();
        jcboxSpec = new java.awt.Choice();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jbtnLoadInv = new javax.swing.JButton();
        jbtnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jpnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblTop.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTop.setText("REPORTE DE PROFESOR");

        javax.swing.GroupLayout jpnlTopLayout = new javax.swing.GroupLayout(jpnlTop);
        jpnlTop.setLayout(jpnlTopLayout);
        jpnlTopLayout.setHorizontalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnlTopLayout.setVerticalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTop)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnlMid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jtblProRep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblProRep);

        javax.swing.GroupLayout jpnlMidLayout = new javax.swing.GroupLayout(jpnlMid);
        jpnlMid.setLayout(jpnlMidLayout);
        jpnlMidLayout.setHorizontalGroup(
            jpnlMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMidLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jpnlMidLayout.setVerticalGroup(
            jpnlMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMidLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpnlBot.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbtnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit_medium.png"))); // NOI18N
        jbtnExit.setText("Salir");
        jbtnExit.setToolTipText("Regresar a pantalla principal");
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        jbtnExp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/xlsexport_lil.png"))); // NOI18N
        jbtnExp.setText("Exportar");
        jbtnExp.setToolTipText("Exporta los valores en pantalla a .XLS");
        jbtnExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBotLayout = new javax.swing.GroupLayout(jpnlBot);
        jpnlBot.setLayout(jpnlBotLayout);
        jpnlBotLayout.setHorizontalGroup(
            jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnExp, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlBotLayout.setVerticalGroup(
            jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBotLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnExit)
                    .addComponent(jbtnExp))
                .addContainerGap())
        );

        jpnlMid1.setBorder(javax.swing.BorderFactory.createTitledBorder("Rangos"));

        jlblIni.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlblIni.setText("Fecha Inicial");

        jcboxIniDay.addItem("01");
        jcboxIniDay.addItem("02");
        jcboxIniDay.addItem("03");
        jcboxIniDay.addItem("04");
        jcboxIniDay.addItem("05");
        jcboxIniDay.addItem("06");
        jcboxIniDay.addItem("07");
        jcboxIniDay.addItem("08");
        jcboxIniDay.addItem("09");
        jcboxIniDay.addItem("10");
        jcboxIniDay.addItem("11");
        jcboxIniDay.addItem("12");
        jcboxIniDay.addItem("13");
        jcboxIniDay.addItem("14");
        jcboxIniDay.addItem("15");
        jcboxIniDay.addItem("16");
        jcboxIniDay.addItem("17");
        jcboxIniDay.addItem("18");
        jcboxIniDay.addItem("19");
        jcboxIniDay.addItem("20");
        jcboxIniDay.addItem("21");
        jcboxIniDay.addItem("22");
        jcboxIniDay.addItem("23");
        jcboxIniDay.addItem("24");
        jcboxIniDay.addItem("25");
        jcboxIniDay.addItem("26");
        jcboxIniDay.addItem("27");
        jcboxIniDay.addItem("28");
        jcboxIniDay.addItem("29");
        jcboxIniDay.addItem("30");
        jcboxIniDay.addItem("31");
        jcboxIniDay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jcboxIniMon.addItem("Ene");
        jcboxIniMon.addItem("Feb");
        jcboxIniMon.addItem("Mar");
        jcboxIniMon.addItem("Abr");
        jcboxIniMon.addItem("May");
        jcboxIniMon.addItem("Jun");
        jcboxIniMon.addItem("Jul");
        jcboxIniMon.addItem("Ago");
        jcboxIniMon.addItem("Sep");
        jcboxIniMon.addItem("Oct");
        jcboxIniMon.addItem("Nov");
        jcboxIniMon.addItem("Dic");
        jcboxIniMon.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jcboxIniYea.addItem("2018");
        jcboxIniYea.addItem("2019");
        jcboxIniYea.addItem("2020");
        jcboxIniYea.addItem("2021");
        jcboxIniYea.addItem("2022");
        jcboxIniYea.addItem("2023");
        jcboxIniYea.addItem("2024");
        jcboxIniYea.addItem("2025");
        jcboxIniYea.addItem("2026");
        jcboxIniYea.addItem("2027");
        jcboxIniYea.addItem("2028");
        jcboxIniYea.addItem("2029");
        jcboxIniYea.addItem("2030");
        jcboxIniYea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jlblFin.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlblFin.setText("Fecha Final");

        jcboxFinDay.addItem("01");
        jcboxFinDay.addItem("02");
        jcboxFinDay.addItem("03");
        jcboxFinDay.addItem("04");
        jcboxFinDay.addItem("05");
        jcboxFinDay.addItem("06");
        jcboxFinDay.addItem("07");
        jcboxFinDay.addItem("08");
        jcboxFinDay.addItem("09");
        jcboxFinDay.addItem("10");
        jcboxFinDay.addItem("11");
        jcboxFinDay.addItem("12");
        jcboxFinDay.addItem("13");
        jcboxFinDay.addItem("14");
        jcboxFinDay.addItem("15");
        jcboxFinDay.addItem("16");
        jcboxFinDay.addItem("17");
        jcboxFinDay.addItem("18");
        jcboxFinDay.addItem("19");
        jcboxFinDay.addItem("20");
        jcboxFinDay.addItem("21");
        jcboxFinDay.addItem("22");
        jcboxFinDay.addItem("23");
        jcboxFinDay.addItem("24");
        jcboxFinDay.addItem("25");
        jcboxFinDay.addItem("26");
        jcboxFinDay.addItem("27");
        jcboxFinDay.addItem("28");
        jcboxFinDay.addItem("29");
        jcboxFinDay.addItem("30");
        jcboxFinDay.addItem("31");
        jcboxFinDay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jcboxFinMon.addItem("Ene");
        jcboxFinMon.addItem("Feb");
        jcboxFinMon.addItem("Mar");
        jcboxFinMon.addItem("Abr");
        jcboxFinMon.addItem("May");
        jcboxFinMon.addItem("Jun");
        jcboxFinMon.addItem("Jul");
        jcboxFinMon.addItem("Ago");
        jcboxFinMon.addItem("Sep");
        jcboxFinMon.addItem("Oct");
        jcboxFinMon.addItem("Nov");
        jcboxFinMon.addItem("Dic");
        jcboxFinMon.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jcboxFinYea.addItem("2018");
        jcboxFinYea.addItem("2019");
        jcboxFinYea.addItem("2020");
        jcboxFinYea.addItem("2021");
        jcboxFinYea.addItem("2022");
        jcboxFinYea.addItem("2023");
        jcboxFinYea.addItem("2024");
        jcboxFinYea.addItem("2025");
        jcboxFinYea.addItem("2026");
        jcboxFinYea.addItem("2027");
        jcboxFinYea.addItem("2028");
        jcboxFinYea.addItem("2029");
        jcboxFinYea.addItem("2030");
        jcboxFinYea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jlblSpec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlblSpec.setText("Ver específico");

        jcboxSpec.addItem("Escoja un valor");
        jcboxSpec.addItem("Hoy");
        jcboxSpec.addItem("Ayer");
        jcboxSpec.addItem("Última semana");
        jcboxSpec.addItem("Último mes");
        jcboxSpec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jcboxSpec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboxSpecItemStateChanged(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jbtnLoadInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loadicon_lil.png"))); // NOI18N
        jbtnLoadInv.setText("Cargar Valores");
        jbtnLoadInv.setToolTipText("Muestra la lista de facturación según el rango especificado");
        jbtnLoadInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoadInvActionPerformed(evt);
            }
        });

        jbtnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clear_2_medium.png"))); // NOI18N
        jbtnReset.setText("Limpiar");
        jbtnReset.setToolTipText("Elimina los ajustes de pantalla y limpia los resultados");
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlMid1Layout = new javax.swing.GroupLayout(jpnlMid1);
        jpnlMid1.setLayout(jpnlMid1Layout);
        jpnlMid1Layout.setHorizontalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblIni)
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addComponent(jcboxIniDay, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxIniMon, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxIniYea, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addComponent(jcboxFinDay, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxFinMon, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxFinYea, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlblFin))
                .addGap(12, 12, 12)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblSpec, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboxSpec, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 314, Short.MAX_VALUE)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnLoadInv, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlMid1Layout.setVerticalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addComponent(jlblSpec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxSpec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addComponent(jbtnLoadInv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnReset))
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addComponent(jlblIni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcboxIniDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxIniMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxIniYea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addComponent(jlblFin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcboxFinDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxFinMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxFinYea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlMid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlMid1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlBot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlMid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlMid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpnlBot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Desea salir?");
        if ( opt == 0 ){
            dispose();
        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jcboxSpecItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboxSpecItemStateChanged
        if ( !jcboxSpec.getSelectedItem().equals("Escoja un valor") ){
            this.jcboxIniDay.select(0);
            this.jcboxIniMon.select(0);
            this.jcboxIniYea.select(0);
            this.jcboxFinDay.select(0);
            this.jcboxFinMon.select(0);
            this.jcboxFinYea.select(0);
            this.jcboxIniDay.setEnabled(false);
            this.jcboxIniMon.setEnabled(false);
            this.jcboxIniYea.setEnabled(false);
            this.jcboxFinDay.setEnabled(false);
            this.jcboxFinMon.setEnabled(false);
            this.jcboxFinYea.setEnabled(false);
        }
        else{
            this.jcboxIniDay.setEnabled(true);
            this.jcboxIniMon.setEnabled(true);
            this.jcboxIniYea.setEnabled(true);
            this.jcboxFinDay.setEnabled(true);
            this.jcboxFinMon.setEnabled(true);
            this.jcboxFinYea.setEnabled(true);
        }
    }//GEN-LAST:event_jcboxSpecItemStateChanged

    private void jbtnLoadInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoadInvActionPerformed
        cleanProfRepTable();
        cls_Date_Manager tmpDM = new cls_Date_Manager();
        switch ( this.jcboxSpec.getSelectedItem() ){
            case "Escoja un valor" :{
                String sIniDat = jcboxIniYea.getSelectedItem() + "-" + jcboxIniMon.getSelectedItem() + "-" + this.jcboxIniDay.getSelectedItem();
                String sFinDat = jcboxFinYea.getSelectedItem() + "-" + jcboxFinMon.getSelectedItem() + "-" + this.jcboxFinDay.getSelectedItem();
                loadProfRepTable(sIniDat, sFinDat, sProfPIN);
                break;
            }
            case "Hoy" :{
                String sIniDat = getCurrentDate();
                String sFinDat = getCurrentDate();
                loadProfRepTable(sIniDat, sFinDat, sProfPIN);
                break;
            }
            case "Ayer":{
                String sIniDat = tmpDM.substractDate(1);
                String sFinDat = tmpDM.substractDate(1);
                loadProfRepTable(sIniDat, sFinDat, sProfPIN);
                break;
            }
            case "Última semana":{
                String sIniDat = tmpDM.substractDate(7);
                String sFinDat = getCurrentDate();
                loadProfRepTable(sIniDat, sFinDat, sProfPIN);
                break;
            }
            case "Último mes":{
                String sIniDat = tmpDM.substractDate(30);
                String sFinDat = getCurrentDate();
                loadProfRepTable(sIniDat, sFinDat, sProfPIN);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Los valores han sido cargados");        
    }//GEN-LAST:event_jbtnLoadInvActionPerformed

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnResetActionPerformed
        this.jcboxIniDay.select(0);
        this.jcboxIniMon.select(0);
        this.jcboxIniYea.select(0);
        this.jcboxFinDay.select(0);
        this.jcboxFinMon.select(0);
        this.jcboxFinYea.select(0);
        this.jcboxSpec.select(0);
        this.jcboxIniDay.setEnabled(true);
        this.jcboxIniMon.setEnabled(true);
        this.jcboxIniYea.setEnabled(true);
        this.jcboxFinDay.setEnabled(true);
        this.jcboxFinMon.setEnabled(true);
        this.jcboxFinYea.setEnabled(true);
        this.cleanProfRepTable();
    }//GEN-LAST:event_jbtnResetActionPerformed

    private void jbtnExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExpActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Confirme que desea exportar los valores en pantalla");
        if ( opt == 0 ){
            //calculates the teacher's total payment
            calculateTotPaym();
            //Find the Teacher into the DB ArrayList and gets his/her name
            int iPos = findTeacher(sProfPIN);
            String sTeaName = alProfDB.get(iPos).getFstName() + " " + alProfDB.get(iPos).getLstName();
            //Gets the initial and final report dates
            cls_Date_Manager tmpDM = new cls_Date_Manager();
            String sIniDat = "NA";
            String sFinDat = "NA";
            switch ( this.jcboxSpec.getSelectedItem() ){
                case "Escoja un valor" :{
                    sIniDat = jcboxIniYea.getSelectedItem() + "-" + jcboxIniMon.getSelectedItem() + "-" + this.jcboxIniDay.getSelectedItem();
                    sFinDat = jcboxFinYea.getSelectedItem() + "-" + jcboxFinMon.getSelectedItem() + "-" + this.jcboxFinDay.getSelectedItem();
                    break;
                }
                case "Hoy" :{
                    sIniDat = getCurrentDate();
                    sFinDat = getCurrentDate();
                    break;
                }
                case "Ayer":{
                    sIniDat = tmpDM.substractDate(1);
                    sFinDat = tmpDM.substractDate(1);
                    break;
                }
                case "Última semana":{
                    sIniDat = tmpDM.substractDate(7);
                    sFinDat = getCurrentDate();
                    break;
                }
                case "Último mes":{
                    sIniDat = tmpDM.substractDate(30);
                    sFinDat = getCurrentDate();
                    break;
                }
            }
            //Creates a 2D Array according with the dimmensions on the screen
            String[][] aDataBase = new String[jtblProRep.getRowCount()][jtblProRep.getColumnCount()];
            //Fulfills the Array with the values on the screen
            for ( int i=0; i<jtblProRep.getRowCount(); i++ ){
                for ( int j=0; j<jtblProRep.getColumnCount(); j++ ){
                    aDataBase[i][j] = jtblProRep.getValueAt(i, j).toString();
                }
            }
            //Sends the 2D Aray to be exported
            cls_Excel_Manager tmpEM = new cls_Excel_Manager();
            try{
                tmpEM.exportProfRepXLSFile(aDataBase, sTeaName, sIniDat, sFinDat, sTotPaym);
            }
            catch (WriteException ex){
                Logger.getLogger(gui_InvoiceDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jbtnExpActionPerformed


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gui_Prof_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_Prof_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_Prof_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_Prof_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_Prof_Report().setVisible(true);
            }
        });
    }

    
    //<editor-fold defaultstate="collapsed" desc="Variables declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnExp;
    private javax.swing.JButton jbtnLoadInv;
    private javax.swing.JButton jbtnReset;
    private java.awt.Choice jcboxFinDay;
    private java.awt.Choice jcboxFinMon;
    private java.awt.Choice jcboxFinYea;
    private java.awt.Choice jcboxIniDay;
    private java.awt.Choice jcboxIniMon;
    private java.awt.Choice jcboxIniYea;
    private java.awt.Choice jcboxSpec;
    private javax.swing.JLabel jlblFin;
    private javax.swing.JLabel jlblIni;
    private javax.swing.JLabel jlblSpec;
    private javax.swing.JLabel jlblTop;
    private javax.swing.JPanel jpnlBot;
    private javax.swing.JPanel jpnlMid;
    private javax.swing.JPanel jpnlMid1;
    private javax.swing.JPanel jpnlTop;
    private javax.swing.JTable jtblProRep;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
