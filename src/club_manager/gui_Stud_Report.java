package club_manager;

import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
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


public class gui_Stud_Report extends javax.swing.JFrame {
    
    javax.swing.table.DefaultTableModel tblModelStuRep = new javax.swing.table.DefaultTableModel();
    Object[] StudRepColumn = new Object [13];
    
    //ArrayList that will store the complete data bases
    private ArrayList<cls_Teacher> alProfDB = new ArrayList<>();
    private ArrayList<cls_Invoice> alInvoDB = new ArrayList<>();
    private ArrayList<cls_Student> alStudDB = new ArrayList<>();
    
    //Data Bases Paths
    private String sLocProfDBPath;
    private String sLocStudDBPath;
    
    //Lesson
    private String sPayLes = "";
    private String sImpLes = "";
    private String sTotRes = "";
    private String sTotPay = "";
    private String sTotRec = "";
    private String sTotUnp = "";
    private String sTotPen = "";
    private String sTotFav = "";
    
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
    
    private String sStudPIN = "";
    
    //Date variables
    private String sDay;
    private String sMon;
    private String sYea;
    
    public gui_Stud_Report(ArrayList<cls_Invoice> alInvoDB, String sLocProflDBPath, ArrayList<cls_Teacher> alTmpTeacherDB, 
            String sLocStudDBPath, ArrayList<cls_Student> alTmpStudentDB, String sStudPIN) {
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
        this.sStudPIN = sStudPIN;
        configStudRepTable();
        setDate();
        
    }

    private gui_Stud_Report() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Shows the System's date in the main screen
    private String setDate(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sDate = "";
        Calendar cal = new GregorianCalendar();
        //Creating time variables for the date. Obtaining values form the System's clock
        sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        sMon = String.valueOf(cal.get(Calendar.MONTH));
        sYea = String.valueOf(cal.get(Calendar.YEAR));
        //formats the day to two digits
        if ( sDay.length()==1 ){sDay = "0" + sDay;}
        //Changes the month's number to the month's name
        switch (sMon)
        {
            case "0" : {sMon = "Enero";break;}
            case "1" : {sMon = "Febrero";break;}
            case "2" : {sMon = "Marzo";break;}
            case "3" : {sMon = "Abril";break;}
            case "4" : {sMon = "Mayo";break;}
            case "5" : {sMon = "Junio";break;}
            case "6" : {sMon = "Julio";break;}
            case "7" : {sMon = "Agosto";break;}
            case "8" : {sMon = "Septiembre";break;}
            case "9" : {sMon = "Octubre";break;}
            case "10" : {sMon = "Noviembre";break;}
            case "11" : {sMon = "Diciembre";break;}
            default : {sMon = "NA";break;}
        }
         //Prepares the text to be displayed in the date label on the main screen
        sDate = sDay + "-" + sMon + "-" + sYea;
        this.jchcCurrentMonth.select(sMon);
        return sDate;
    }
    //</editor-fold>
        
    //Prepares the JTable columns in order to receive the list of Reserves and Invoice Lines
    private void configStudRepTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelStuRep.addColumn("Alumno");
        tblModelStuRep.addColumn("Reserva");
        tblModelStuRep.addColumn("Fecha");
        tblModelStuRep.addColumn("MES");
        tblModelStuRep.addColumn("Profesor");
        tblModelStuRep.addColumn("Factura(s)");
        tblModelStuRep.addColumn("Tarifa diaria");
        tblModelStuRep.addColumn("L. Reservd");
        tblModelStuRep.addColumn("L. Pagadas");
        tblModelStuRep.addColumn("L. Recibds");
        tblModelStuRep.addColumn("L. Adeudad");
        tblModelStuRep.addColumn("Saldo Pend.");
        tblModelStuRep.addColumn("L. a favor");
        jtblStuRep.setModel(tblModelStuRep);
        //Allows the user to sort the items by Column
        jtblStuRep.setAutoCreateRowSorter(true);        
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblStuRep.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.YELLOW);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblStuRep.setAutoResizeMode(jtblStuRep.AUTO_RESIZE_OFF);
        jtblStuRep.setRowHeight(24);
        jtblStuRep.getColumnModel().getColumn(0).setPreferredWidth(170);
        jtblStuRep.getColumnModel().getColumn(0).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(1).setPreferredWidth(270);
        jtblStuRep.getColumnModel().getColumn(1).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(2).setPreferredWidth(90);
        jtblStuRep.getColumnModel().getColumn(2).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(3).setPreferredWidth(70);
        jtblStuRep.getColumnModel().getColumn(3).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(4).setPreferredWidth(170);
        jtblStuRep.getColumnModel().getColumn(4).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(5).setPreferredWidth(150);
        jtblStuRep.getColumnModel().getColumn(5).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);        
        jtblStuRep.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtblStuRep.getColumnModel().getColumn(6).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(7).setPreferredWidth(100);
        jtblStuRep.getColumnModel().getColumn(7).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(8).setPreferredWidth(100);
        jtblStuRep.getColumnModel().getColumn(8).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(9).setPreferredWidth(100);
        jtblStuRep.getColumnModel().getColumn(9).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(10).setPreferredWidth(100);
        jtblStuRep.getColumnModel().getColumn(10).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(11).setPreferredWidth(100);
        jtblStuRep.getColumnModel().getColumn(11).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
        jtblStuRep.getColumnModel().getColumn(12).setPreferredWidth(100);
        jtblStuRep.getColumnModel().getColumn(12).setResizable(false);
        jtblStuRep.getColumnModel().getColumn(12).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Cleans the Teachers List JTable
    private void cleanProfRepTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelStuRep.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelStuRep.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar el Reporte de Profesore\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Fulfills the Invoice list according with the given date range
    private void loadStudRepTable(String sIniDat, String sFinDat, String sStudPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        System.out.println("Loading data from Invoice ArralyList into JTable");
        //Receives the range of dates and adds the necessary zeroes
        sIniDat = sIniDat + " 00:00";
        sFinDat = sFinDat + " 00:00";
        System.out.println("Initial date: " + sIniDat);
        System.out.println("Final date: " + sFinDat);
        
        //Gets the list of current reserves under this Student
        int iStudPos = findStudent(sStudPIN);
        String sReserves = this.alStudDB.get(iStudPos).getReservs();
        String tmpRSV; //Temporary variable to store the separatred DRCs
        int iPos = 0;
        
        //Variable to calculate lesson differences
        int iPenLess = 0;
        int iFavLess = 0;
        //Variables to calculate Invoices and payed costs
        String sInvoices = "";
        double dCosts = 0;
        double dIndCost = 0;
        double dPenCost = 0;
        
        
        //Extracts each Reserve String under each Student
        if ( !sReserves.equals("NA") ){//If there are active reserves, then...
            System.out.println("Analyzing Reserves Code: " + sReserves);
            do{
                tmpRSV = "";//Cleans the Temporary DRC Code
                sInvoices = "";
                dCosts = 0;
                do{//Extracts the DRCs separately from the whole reservation String 
                    tmpRSV = tmpRSV + sReserves.charAt(iPos);
                    iPos++;
                } while(sReserves.charAt(iPos) != '>');//Stops exmracting the Code at '>'
                //A DRC has been separated from the String
                System.out.println("CRD Extracted: " + tmpRSV);
                //Deecodes the DRC in order to start fulfilling the Jtable
                decodeDRC(tmpRSV);
                //Looks for Invoices and costs ll over the Invoice Data Base
                for ( cls_Invoice tmp : alInvoDB  ){
                    if ( tmp.getDRCCode().equals(tmpRSV.substring(0,23) + tmpRSV.substring(35)) ){//If the MiniDRC is detected
                        //Adds the invoice number to the list
                        if ( sInvoices.equals("") ){sInvoices = tmp.getInvNumb();}
                        else{sInvoices = sInvoices + ", " + tmp.getInvNumb();}
                        //Gets the associated costs into the Invoice and adds them
                        dCosts = dCosts + getLessonsCost(sDRCTPn, tmp.getInvNumb());
                    }
                }
                //Loads one line with the DRC info
                StudRepColumn[0] = alStudDB.get(iStudPos).getFstName() + " " + alStudDB.get(iStudPos).getLstName();//This is the Student´s name
                StudRepColumn[1] = sDRCMod + " / " + sDRCPaq + " / " + sDRCDay + ", " + sDRCHrs;
                StudRepColumn[2] = sDRCDat;
                StudRepColumn[3] = sDRCDat.substring(3,6).toUpperCase();
                StudRepColumn[4] = alProfDB.get(findTeacher(sDRCTPn)).getFstName() + " " + alProfDB.get(findTeacher(sDRCTPn)).getLstName();
                StudRepColumn[5] = sInvoices;
                dIndCost = dCosts / Integer.valueOf(sDRCPay);
                dIndCost = roundValue(dIndCost);
                StudRepColumn[6] = String.valueOf(dIndCost);
                StudRepColumn[7] = sDRCRes;
                StudRepColumn[8] = sDRCPay;
                StudRepColumn[9] = sDRCRec;
                iPenLess = Integer.valueOf(sDRCRes) - Integer.valueOf(sDRCPay);
                StudRepColumn[10] = formatDigits(String.valueOf(iPenLess));//Res less  - Pay Less
                dPenCost = dIndCost * iPenLess;
                StudRepColumn[11] = String.valueOf(dPenCost);
                iFavLess = Integer.valueOf(sDRCPay) - Integer.valueOf(sDRCRec);
                StudRepColumn[12] = formatDigits(String.valueOf(iFavLess));//Pay less - Rec less
                tblModelStuRep.addRow(StudRepColumn);
                jtblStuRep.setModel(tblModelStuRep);
            iPos++;
            }while( iPos < sReserves.length() );
            iPos = 0;
        }
        else{
            JOptionPane.showMessageDialog(this,"No hay Reservas activas para mostrar");
        }
        
        
        System.out.println("Informatio loaded into the Student Report's JTable");
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
    
    //Identifies the Invoice´s line number from the Invoices ArraList, depending on the Invoice number
    private int findInvoice(String sInv){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int iLine = -1;
        int i = 0; //Index
        for(cls_Invoice tmp: this.alInvoDB){
            if ( tmp.getInvNumb().equals(sInv) ){
                iLine = i;
            }
            i++;
        }
        return iLine;
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
    
    //Calculates the total reserved lessons for the Student according with the values on the screen
    private void calculateTotRes(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        int iTotRes = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
            iTotRes = iTotRes + Integer.valueOf(jtblStuRep.getValueAt(i, 7).toString());
        }
        sTotRes = String.valueOf(iTotRes);
    }
    //</editor-fold>
    
    //Calculates the total Payed lessons for the Student according with the values on the screen
    private void calculateTotPay(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        int iTotPay = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
            iTotPay = iTotPay + Integer.valueOf(jtblStuRep.getValueAt(i, 8).toString());
        }
        sTotPay = String.valueOf(iTotPay);
    }
    //</editor-fold>
    
    //Calculates the total received lessons for the Student according with the values on the screen
    private void calculateTotRec(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        int iTotRec = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
            iTotRec = iTotRec + Integer.valueOf(jtblStuRep.getValueAt(i, 9).toString());
        }
        sTotRec = String.valueOf(iTotRec);
    }
    //</editor-fold>
    
    //Calculates the total unpaid lessons for the Student according with the values on the screen
    private void calculateTotUnp(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        int iTotUnp = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
            iTotUnp = iTotUnp + Integer.valueOf(jtblStuRep.getValueAt(i, 10).toString());
        }
        sTotUnp = String.valueOf(iTotUnp);
    }
    //</editor-fold>
    
    //Calculates the total pending for the Student according with the values on the screen
    private void calculateTotPen(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        double dTotPend = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
            dTotPend = dTotPend + Double.parseDouble(jtblStuRep.getValueAt(i, 11).toString());
        }
        dTotPend = roundValue(dTotPend);
        sTotPen = String.valueOf(dTotPend);
    }
    //</editor-fold>
    
    //Calculates the total favor lessons for the Student according with the values on the screen
    private void calculateTotFav(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        int iTotFav = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
            iTotFav = iTotFav + Integer.valueOf(jtblStuRep.getValueAt(i, 12).toString());
        }
        sTotFav = String.valueOf(iTotFav);
    }
    //</editor-fold>
    
    //Sets a String number to 3 digits format
    private String formatDigits(String sNumber){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        if ( sNumber.equals("") ){
            sNumber = "0";
        }
        //Reformats the String to three chars length
        //Counts how many char we have so far
        int iCharCount = sNumber.length();
        //Then adds the necessary left zeroes at the left
        switch (iCharCount) {
            case 1 : {sNumber = "00" + sNumber; break;}
            case 2 : {sNumber = "0" + sNumber; break;}
            case 3 : {sNumber = sNumber; break;}
            default : {break;}
        }
        return sNumber;
    }
    //</editor-fold>
    
    //Gets the total Cost per lesson of a given prof PIN from an Invoice
    private double getLessonsCost(String sProfPin, String sInvNumb){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        double dTotal = 0;
        //Finds the invoice position into the corresponding Array
        int iPos = findInvoice(sInvNumb);
        if ( alInvoDB.get(iPos).getItACode().equals(sProfPin) ){dTotal = dTotal + Double.parseDouble(alInvoDB.get(iPos).getItATotl());}
        if ( alInvoDB.get(iPos).getItBCode().equals(sProfPin) ){dTotal = dTotal + Double.parseDouble(alInvoDB.get(iPos).getItBTotl());}
        if ( alInvoDB.get(iPos).getItCCode().equals(sProfPin) ){dTotal = dTotal + Double.parseDouble(alInvoDB.get(iPos).getItCTotl());}
        if ( alInvoDB.get(iPos).getItDCode().equals(sProfPin) ){dTotal = dTotal + Double.parseDouble(alInvoDB.get(iPos).getItDTotl());}
        dTotal = roundValue(dTotal);
        return dTotal;
    }
    //</editor-fold>
    
    //Creates the e-mail body to send the Student status to the customers
    private String createBodyMail(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sBodyMail = "";
        //calculates the Students's total pendings
        calculateTotRes();
        calculateTotPay();
        calculateTotRec();
        calculateTotUnp();
        calculateTotPen();
        calculateTotFav();
        for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
            sBodyMail = sBodyMail + 
                    "RESERVA " + (i+1) + ": \n" +
                    "Fecha inscrip.: " + jtblStuRep.getValueAt(i, 2).toString() + "\n" +
                    "Descrip.: " + jtblStuRep.getValueAt(i, 1).toString() + "\n" +
                    "Profesor: " + jtblStuRep.getValueAt(i, 4).toString() + "\n" +
                    "Estado actual:\n" +
                    "Lecc. Reservds.: " + jtblStuRep.getValueAt(i,  7).toString() + " / Lecc. Pagadas: " + jtblStuRep.getValueAt(i, 8).toString() + " / Lecc. Recibidas: " + jtblStuRep.getValueAt(i, 9).toString() + "\n" +
                    "Lecc. Adeudadas: " + jtblStuRep.getValueAt(i, 10).toString() + " / Lecc. a favor: " + jtblStuRep.getValueAt(i, 12).toString() + "\n" +
                    "Facturas relacionadas: " + jtblStuRep.getValueAt(i, 5).toString() + "\n" +
                    "Saldo pendiente: c." + jtblStuRep.getValueAt(i, 11).toString() + "\n" +
                    "------------------------------------------------------------------\n";
        }
        sBodyMail = sBodyMail +
                "TOTALES: \n" + 
                "• L. Reserv.: " + sTotRes + "\n" +
                "• L. Pagads.: " + sTotPay + "\n" +
                "• L. Recibd.: " + sTotRec + "\n" +
                "• L. Adeuda.: " + sTotUnp + "\n" +
                "• L. a favor: " + sTotFav + "\n" +
                "• Saldo pend: " + sTotPen + "\n" +
                "------------------------------------------------------------------\n";
        return sBodyMail;
    }
    //</editor-fold>
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlTop = new javax.swing.JPanel();
        jlblTop = new javax.swing.JLabel();
        jchcCurrentMonth = new java.awt.Choice();
        jlblCurrentMonth = new javax.swing.JLabel();
        jpnlMid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblStuRep = new javax.swing.JTable();
        jpnlBot = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnExp = new javax.swing.JButton();
        jbtnMail = new javax.swing.JButton();
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
        jlblTop.setText("                                            REPORTE DE ALUMNO");

        jchcCurrentMonth.addItem("Enero");
        jchcCurrentMonth.addItem("Febrero");
        jchcCurrentMonth.addItem("Marzo");
        jchcCurrentMonth.addItem("Abril");
        jchcCurrentMonth.addItem("Mayo");
        jchcCurrentMonth.addItem("Junio");
        jchcCurrentMonth.addItem("Julio");
        jchcCurrentMonth.addItem("Agosto");
        jchcCurrentMonth.addItem("Septiembre");
        jchcCurrentMonth.addItem("Octubre");
        jchcCurrentMonth.addItem("Noviembre");
        jchcCurrentMonth.addItem("Diciembre");

        jlblCurrentMonth.setText("MES A REPORTAR");

        javax.swing.GroupLayout jpnlTopLayout = new javax.swing.GroupLayout(jpnlTop);
        jpnlTop.setLayout(jpnlTopLayout);
        jpnlTopLayout.setHorizontalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTop, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblCurrentMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchcCurrentMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlTopLayout.setVerticalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jlblTop)
                        .addComponent(jchcCurrentMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jlblCurrentMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jpnlMid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jtblStuRep.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblStuRep);

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

        jbtnMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mail_small.png"))); // NOI18N
        jbtnMail.setText("E-Mail");
        jbtnMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBotLayout = new javax.swing.GroupLayout(jpnlBot);
        jpnlBot.setLayout(jpnlBotLayout);
        jpnlBotLayout.setHorizontalGroup(
            jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnExp, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnMail, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlBotLayout.setVerticalGroup(
            jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBotLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBotLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnExit)
                            .addComponent(jbtnExp)))
                    .addComponent(jbtnMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jcboxIniDay.setEnabled(false);

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
        jcboxIniMon.setEnabled(false);

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
        jcboxIniYea.setEnabled(false);

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
        jcboxFinDay.setEnabled(false);

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
        jcboxFinMon.setEnabled(false);

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
        jcboxFinYea.setEnabled(false);

        jlblSpec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlblSpec.setText("Ver específico");

        jcboxSpec.addItem("Escoja un valor");
        jcboxSpec.addItem("Hoy");
        jcboxSpec.addItem("Ayer");
        jcboxSpec.addItem("Última semana");
        jcboxSpec.addItem("Último mes");
        jcboxSpec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jcboxSpec.setEnabled(false);
        jcboxSpec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboxSpecItemStateChanged(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jbtnLoadInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loadicon_lil.png"))); // NOI18N
        jbtnLoadInv.setText("Cargar Reservas");
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
                loadStudRepTable(sIniDat, sFinDat, sStudPIN);
                break;
            }
            case "Hoy" :{
                String sIniDat = getCurrentDate();
                String sFinDat = getCurrentDate();
                loadStudRepTable(sIniDat, sFinDat, sStudPIN);
                break;
            }
            case "Ayer":{
                String sIniDat = tmpDM.substractDate(1);
                String sFinDat = tmpDM.substractDate(1);
                loadStudRepTable(sIniDat, sFinDat, sStudPIN);
                break;
            }
            case "Última semana":{
                String sIniDat = tmpDM.substractDate(7);
                String sFinDat = getCurrentDate();
                loadStudRepTable(sIniDat, sFinDat, sStudPIN);
                break;
            }
            case "Último mes":{
                String sIniDat = tmpDM.substractDate(30);
                String sFinDat = getCurrentDate();
                loadStudRepTable(sIniDat, sFinDat, sStudPIN);
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
            //calculates the Students's total pendings
            calculateTotRes();
            calculateTotPay();
            calculateTotRec();
            calculateTotUnp();
            calculateTotPen();
            calculateTotFav();
            //Find the Student into the DB ArrayList and gets his/her name
            int iPos = findStudent(sStudPIN);
            String sStuName = alStudDB.get(iPos).getFstName() + " " + alStudDB.get(iPos).getLstName();
            //Creates a 2D Array according with the dimmensions on the screen
            String[][] aDataBase = new String[jtblStuRep.getRowCount()][jtblStuRep.getColumnCount()];
            //Fulfills the Array with the values on the screen
            for ( int i=0; i<jtblStuRep.getRowCount(); i++ ){
                for ( int j=0; j<jtblStuRep.getColumnCount(); j++ ){
                    aDataBase[i][j] = jtblStuRep.getValueAt(i, j).toString();
                }
            }
            //Sends the 2D Aray to be exported
            cls_Excel_Manager tmpEM = new cls_Excel_Manager();
            try{
                tmpEM.exportStudRepXLSFile(aDataBase, sStuName, sTotRes, sTotPay, sTotRec, sTotUnp, sTotPen, sTotFav);
            }
            catch (WriteException ex){
                Logger.getLogger(gui_InvoiceDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jbtnExpActionPerformed

    private void jbtnMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMailActionPerformed
        //Prepares the mail variables
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Gets Student position into the al
        int iPos = findStudent(sStudPIN);
        //Creates the list of reserves from the screen
        String sReserves = createBodyMail();
        String sMailSub = "Top Tennis Center: Estado de Cuenta para el Mes de " + jchcCurrentMonth.getSelectedItem() + sYea;
        String sMailTo = alStudDB.get(iPos).getEmail();
        String sMailBody = "Hola " + this.alStudDB.get(iPos).getFstName() + ". Le saludamos de parte de Top Tennis Center de Costa Rica.\n\n"
                + "Los siguientes datos corresponden a su estado de cuenta actual:\n\n"
                + "---------------------------------------------------------\n"
                + sReserves + "\n"
                + "CONDICIONES Y FORMA DE PAGO:\n"
                + "El pago debe efectuarse en los primeros 7 días de cada mes.\n"
                + "Les ofrecemos las siguientes facilidades para efectuar su pago:\n"
                + "• Cargo Automático:\n"
                + "Solicita por este medio el formulario para pago Automático, o comuníquese vía telefónica y realice el respectivo cargo a su tarjeta "
                + "a los teléfonos: Oficina 2282-9540 2282-9222, ext. 4 Tennis, o con Administración al 8922-1934 con Ariana Quesada.\n"
                + "• Transferencias Bancarias:\n"
                + "BAC San José:\n"
                + "\tCuenta Corriente 935009779\n"
                + "\tCuenta Cliente 10200009350097791\n"
                + "Banco de Costa Rica:\n"
                + "\tCuenta Corriente 001-0460958-1\n"
                + "\tCuenta Cliente 15201001046095818\n"
                + "Ambas cuentas a nombre de TOP TENNIS CENTER LTDA. Cédula Jurídica: 3-102-752881\n"
                + "• Cheques:\n"
                + "Se giran a nombre de TOP TENNIS CENTER LTDA\n"
                + "-------------------------------------------------------------------------------------------\n\n"
                + "Cordialmente;\n\n"
                + "ADMINISTRACIÓN\n"
                + "TOP TENNIS CENTER\n"
                + "Cédula Jurídica: 3-102-752881\n"
                + "Teléfonos: 2282-9540 2282-9222, ext. 4 / 8922-1934\n\n";
        //OPENS AN OUTLOOK WINDOW WITH THE MAIL READY TO BE SENT
        cls_Mail tmpMail = new cls_Mail(sMailTo, "NA", sMailSub, sMailBody);
        try {
            tmpMail.sendMail();
        } catch (IOException ex) {
            Logger.getLogger(gui_MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(gui_MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_jbtnMailActionPerformed


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
            java.util.logging.Logger.getLogger(gui_Stud_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_Stud_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_Stud_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_Stud_Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new gui_Stud_Report().setVisible(true);
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
    private javax.swing.JButton jbtnMail;
    private javax.swing.JButton jbtnReset;
    private java.awt.Choice jcboxFinDay;
    private java.awt.Choice jcboxFinMon;
    private java.awt.Choice jcboxFinYea;
    private java.awt.Choice jcboxIniDay;
    private java.awt.Choice jcboxIniMon;
    private java.awt.Choice jcboxIniYea;
    private java.awt.Choice jcboxSpec;
    private java.awt.Choice jchcCurrentMonth;
    private javax.swing.JLabel jlblCurrentMonth;
    private javax.swing.JLabel jlblFin;
    private javax.swing.JLabel jlblIni;
    private javax.swing.JLabel jlblSpec;
    private javax.swing.JLabel jlblTop;
    private javax.swing.JPanel jpnlBot;
    private javax.swing.JPanel jpnlMid;
    private javax.swing.JPanel jpnlMid1;
    private javax.swing.JPanel jpnlTop;
    private javax.swing.JTable jtblStuRep;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
