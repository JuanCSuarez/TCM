package club_manager;

import java.awt.Color;
import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;


public class gui_Reserve_Manager extends javax.swing.JFrame {
    
    //Data Bases Paths
    private String sLocStudDBPath;
    private String sLocProfDBPath;
    
    //Variables for the last two created PINs
    private String sLastProfPIN = "";
    private String sLastStudPIN = "";
    
    private String sPIN;
    
    private boolean bHide = false;

    javax.swing.table.DefaultTableModel tblModelResMan = new javax.swing.table.DefaultTableModel();
    Object[] ResManColumn = new Object [8];
    
    //ArrayList that will store the complete data base of Students
    private ArrayList<cls_Student> alStudDB = new ArrayList<>();
    //ArrayList that will store the complete data base of Teachers
    private ArrayList<cls_Teacher> alProfDB = new ArrayList<>();
    
    private String[][] arrMemb = new String[30][2];
    
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
    private int iMode; //0: Teacher, 1: Student
    
    

    public gui_Reserve_Manager(String sLocStudDBPath, ArrayList<cls_Student> alTmpStudentDB, String sLocProfDBPath, ArrayList<cls_Teacher> alTmpTeacherDB, String sPIN, int iMode) {
        initComponents();
        System.out.println("Starting Reserves Control Module");
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("TCM - SISTEMA DE CONTROL DE RESERVAS");
        
        this.sLocStudDBPath = sLocStudDBPath;
        this.alStudDB = alTmpStudentDB;
        this.sLocProfDBPath = sLocProfDBPath;
        this.alProfDB = alTmpTeacherDB;  
        this.sPIN = sPIN;
        this.iMode = iMode;
        
        configResManTable();
        loadLastStudPIN();
        
        if ( iMode == 0 ){
            this.jbtnDel.setEnabled(false);
            this.jbtnDel.setVisible(false);
            this.jbtnSave.setEnabled(true);
        }
        else{
            this.jbtnDel.setEnabled(true);
            this.jbtnDel.setVisible(true);
            this.jbtnSave.setEnabled(false);
        }
        
        
    }

    private gui_Reserve_Manager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //Prepares the JTable columns in order to receive the list of Students
    private void configResManTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelResMan.addColumn("ALUMNO");
        tblModelResMan.addColumn("FECHA");
        tblModelResMan.addColumn("DESCRIPCIÓN");
        tblModelResMan.addColumn("LECC. RESERVD.");
        if ( iMode == 0 ){tblModelResMan.addColumn("LECC. IMPARTD.");}
        else{tblModelResMan.addColumn("LECC. RECIVID.");}
        tblModelResMan.addColumn("LECC. PAGADAS");
        tblModelResMan.addColumn("PROFESOR");
        tblModelResMan.addColumn("DRC");
        jtblResMan.setModel(tblModelResMan);
        //Allows the user to sort the items by Column
        jtblResMan.setAutoCreateRowSorter(false);
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblResMan.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.CYAN);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblResMan.setAutoResizeMode(jtblResMan.AUTO_RESIZE_OFF);
        jtblResMan.setRowHeight(24);
        jtblResMan.getColumnModel().getColumn(0).setPreferredWidth(160);
        jtblResMan.getColumnModel().getColumn(0).setResizable(false);
        jtblResMan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblResMan.getColumnModel().getColumn(1).setPreferredWidth(100);
        jtblResMan.getColumnModel().getColumn(1).setResizable(false);
        jtblResMan.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblResMan.getColumnModel().getColumn(2).setPreferredWidth(270);
        jtblResMan.getColumnModel().getColumn(2).setResizable(false);
        jtblResMan.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblResMan.getColumnModel().getColumn(3).setPreferredWidth(120);
        jtblResMan.getColumnModel().getColumn(3).setResizable(false);
        jtblResMan.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblResMan.getColumnModel().getColumn(4).setPreferredWidth(120);
        jtblResMan.getColumnModel().getColumn(4).setResizable(false);
        jtblResMan.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);        
        jtblResMan.getColumnModel().getColumn(5).setPreferredWidth(120);
        jtblResMan.getColumnModel().getColumn(5).setResizable(false);
        jtblResMan.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jtblResMan.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtblResMan.getColumnModel().getColumn(6).setResizable(false);
        jtblResMan.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblResMan.getColumnModel().getColumn(7).setPreferredWidth(230);
        jtblResMan.getColumnModel().getColumn(7).setResizable(false);
        jtblResMan.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Gets the list of corresponding reserves, assigned to the Teacher, from the Student DB
    private void loadResManTable(String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String tmpRSV; 
        int iPos = 0;
        //Looks for the Reserves asigned to each Student
        for ( cls_Student tmp : alStudDB ){
            //Extracts each Reserve String under each Student
            if ( !tmp.getReservs().equals("NA") ){//If it is not empty...
                System.out.println("Analyzing: " + tmp.getReservs());
                do{
                    tmpRSV = "";
                    do{//Extracts the DRCs separately from the whole reservation String 
                        tmpRSV = tmpRSV + tmp.getReservs().charAt(iPos);
                        iPos++;
                    } while(tmp.getReservs().charAt(iPos) != '>');
                    //A DRC has been separated from the String
                    System.out.println("Resulting CRD: " + tmpRSV);
                    if ( tmpRSV.contains(sPIN) ){//If the extracted DRC String contains the give Prof´s or Student's PIN, then...
                        System.out.println("PIN detected in CRD");
                        //Decodes the extracted DRC
                        decodeDRC(tmpRSV);
                        //Loads one line with the DRC info
                        ResManColumn[0] = tmp.getFstName() + " " + tmp.getLstName();//This is the Student´s name
                        ResManColumn[1] = sDRCDat;
                        ResManColumn[2] = sDRCMod + " / " + sDRCPaq + " / " + sDRCDay + ", " + sDRCHrs;
                        ResManColumn[3] = sDRCRes;
                        ResManColumn[4] = sDRCRec;
                        ResManColumn[5] = sDRCPay;
                        ResManColumn[6] = sDRCTPn;
                        ResManColumn[7] = tmpRSV.substring(0,23) + tmpRSV.substring(35);
                        tblModelResMan.addRow(ResManColumn);
                        jtblResMan.setModel(tblModelResMan);
                    }
                    iPos++;
                }while( iPos < tmp.getReservs().length() );
                iPos = 0;
            }
        }
    }
    //</editor-fold>
    
    //Cleans the Teachers List JTable
    private void cleanResManTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelResMan.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelResMan.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de Reservas\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Loads the last Student PIN from the local .txt file
    private void loadLastStudPIN(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sStudPIN="";
        try
        {
            fDataBase = new File(sLocStudDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Reads all the lines on the Students .txt file until reaching the needed one
            chain = br.readLine();
            while( !chain.equals("LAST PIN") )
            {
                chain = br.readLine();
            }
            chain = br.readLine();
            this.sLastStudPIN = chain;
            br.close();
            fr.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"No se pudo la información de PIN de Estudiante desde la base de datos local.\n"
                    + "El Sistema ha generado un error mientras leía los datos:\n"
                    + e, "TCM ERROR - loadLastStudPIN()", JOptionPane.ERROR_MESSAGE );
        }
    }
    //</editor-fold>
    
    //Updates the local .TXT Students Data Base file directly from the Students Data Base ArrayList
    public void updateStudentDB_txt(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        try
        {
            File fDataBase;
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter wr = null;
            
            fDataBase = new File (this.sLocStudDBPath); //points to the local .txt Teachers data base file
            fw = new FileWriter(fDataBase);
            bw = new BufferedWriter(fw);
            wr = new PrintWriter(bw);
            
            //Reads, line by line, all the consults that are currently in the Data Base Array List
            for(cls_Student tmp: this.alStudDB)
            {
                wr.println( tmp.getFstName() + "\t" 
                        + tmp.getMidName() + "\t" 
                        + tmp.getLstName() + "\t" 
                        + tmp.getIdNumb() + "\t" 
                        + tmp.getEmail() + "\t" 
                        + tmp.getHomNumb() + "\t" 
                        + tmp.getCelNumb() + "\t"
                        + tmp.getAddMain() + "\t"
                        + tmp.getAddCity() + "\t"
                        + tmp.getAddStat() + "\t"
                        + tmp.getAddZiCo() + "\t"
                        + tmp.getStuLevl() + "\t"
                        + tmp.getStatus() + "\t"
                        + tmp.getResClas() + "\t"
                        + tmp.getRecClas() + "\t"
                        + tmp.getMembshp() + "\t"
                        + tmp.getPayClas() + "\t"
                        + tmp.getTeaName() + "\t"
                        + tmp.getTeaCate() + "\t"
                        + tmp.getStudPIN() + "\t"
                        + tmp.getReservs() + "\t"
                        + tmp.getXXX1() + "\t"
                        + tmp.getXXX2() + "\t"
                        + tmp.getXXX3() + "\t"
                        + tmp.getXXX4() );
            }
            //Writes the DB lines QTY
            wr.println("DB LINES");
            wr.println(String.valueOf(alStudDB.size()));
            //Writes the last Student PIN created
            wr.println("LAST PIN");
            wr.println(this.sLastStudPIN);
            wr.close();
            bw.close();
            fw.close();
        }
        catch(IOException e){JOptionPane.showMessageDialog(this,"Ocurrió un error mientras se actualizaba la Base de Datos de Alumnos:\n"
                + e, "ERROR - updateStudentDB_txt()", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Updates the local .TXT Teachers Data Base file directly from the Teachers Data Base ArrayList
    public void updateTeacherDB_txt(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        try
        {
            File fDataBase;
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter wr = null;
            fDataBase = new File (this.sLocProfDBPath); //points to the local .txt Teachers data base file
            fw = new FileWriter(fDataBase);
            bw = new BufferedWriter(fw);
            wr = new PrintWriter(bw);
            //Reads, line by line, all the consults that are currently in the Data Base Array List
            for(cls_Teacher tmp: this.alProfDB)
            {
                wr.println( tmp.getFstName() + "\t" 
                        + tmp.getMidName() + "\t" 
                        + tmp.getLstName() + "\t" 
                        + tmp.getIdNumb() + "\t" 
                        + tmp.getEmail() + "\t" 
                        + tmp.getHomNumb() + "\t" 
                        + tmp.getCelNumb() + "\t"
                        + tmp.getAddMain() + "\t"
                        + tmp.getAddCity() + "\t"
                        + tmp.getAddStat() + "\t"
                        + tmp.getAddZiCo() + "\t"
                        + tmp.getCategry() + "\t"
                        + tmp.getStatus() + "\t"
                        + tmp.getStudQty() + "\t"
                        + tmp.getResClas() + "\t"
                        + tmp.getImpClas() + "\t"
                        + tmp.getPayClas() + "\t"
                        + tmp.getComPerc() + "\t"
                        + tmp.getSalary() + "\t"
                        + tmp.getHrsCost() + "\t"
                        + tmp.getStuList() + "\t"
                        + tmp.getProfPIN() + "\t"
                        + tmp.getXXX1() + "\t"
                        + tmp.getXXX2() + "\t"
                        + tmp.getXXX3() + "\t"
                        + tmp.getXXX4() );
            }
            //writes the DB size QTY
            wr.println("DB LINES");
            wr.println(String.valueOf(alProfDB.size()));
            //Writes the last Teacher PIN created
            wr.println("LAST PIN");
            wr.println(this.sLastProfPIN);
            wr.close();
            bw.close();
            fw.close();
        }
        catch(IOException e){JOptionPane.showMessageDialog(this,"Ocurrió un error mientras se actualizaba la Base de Datos de Profesores:\n"
                + e, "TCM ERROR - updateTeacherDB_txt()", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
        
    //Updates the received lessons in the Student´s DRC stored in the Stud´s ArrayList
    private void updateDRCImpLess(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sStudPIN = "";
        String sProfPIN = "";
        String sImpLess = "";
        int StudPos = -1;
        int iChars = 0;
        for ( int i=0; i < this.jtblResMan.getRowCount(); i++ ){
            sStudPIN = jtblResMan.getValueAt(i, 7).toString().substring(0, 5);
            sProfPIN = jtblResMan.getValueAt(i, 6).toString();
            sImpLess = formatDigits(String.valueOf(Integer.valueOf(jtblResMan.getValueAt(i, 4).toString())));
            //Finds the Student into the Std ArrayList
            StudPos = findStudent(sStudPIN);
            updateStudRecLess(jtblResMan.getValueAt(i, 7).toString(), sImpLess, StudPos);
        }
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
    
    //Checks if the Student already exists in the data base based on his PIN
    private int findStudent(String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        int iIndex = -1;
        int i = 0;
        for ( cls_Student tmp : alStudDB ){
            if ( tmp.getStudPIN().equals(sPIN) ){
                iIndex = i;
            }
            i++;
        }
        return iIndex;
    }
    //</editor-fold>
    
    //Checks if the Teacher already exists in the data base
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
        
    //Updates the Student DRC with the given amount of received lessons
    private void updateStudRecLess(String sDRC, String sRecLess, int iPos){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String tmpRSV = "";
        String sMDRC = ""; //mini DRC to be compared with the one on the screen
        String newDRC = "";
        int i=0;
        do{
            //Extracts the different DRCs for this Student
            tmpRSV = "";
            sMDRC = "";
            newDRC = "";
            do{
                tmpRSV = tmpRSV + alStudDB.get(iPos).getReservs().charAt(i);
                i++;
            } while(alStudDB.get(iPos).getReservs().charAt(i) != '>');
            //Analyzes the DRC and updates if necessary
            sMDRC = tmpRSV.substring(0,23) + tmpRSV.substring(35);
            //If the extrated Code matches with the one on the table line
            if ( sMDRC.equals(sDRC) ){
                newDRC = updateDRCRecLes(tmpRSV, sRecLess);
                alStudDB.get(iPos).setReservs(alStudDB.get(iPos).getReservs().replaceFirst(tmpRSV, newDRC));
            }
            i++;
        }while( i < alStudDB.get(iPos).getReservs().length() );
    }
    //</editor-fold>
    
    //Updates the amount of payed lessons in the DRC
    private String updateDRCRecLes(String sDRC, String sRecLes){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sUpdatedDRC = "";
        String sPart1 = sDRC.substring(0, 28);
        String sPart2 = sDRC.substring(28,31);
        String sPart3 = sDRC.substring(31, 42);
        String sResev = sDRC.substring(24, 27);
        //Prepares the new amount of payed hours
        int iResev = Integer.valueOf(sResev);
        int iRecev = Integer.valueOf(sPart2);
        if ( iRecev < iResev ){
            int iNewQty = /*Integer.valueOf(sPart2)*/ + Integer.valueOf(sRecLes);//Su
            String sNBewQty = String.valueOf(iNewQty);
            sPart2 = formatDigits(sNBewQty);
        }
        iRecev = Integer.valueOf(sPart2);
        if ( iRecev > iResev){
            sPart2 = sResev;
        }
        sUpdatedDRC = sPart1 + sPart2 + sPart3;
        return sUpdatedDRC;
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
    
    //Updates Reserves QTY from Student DB
    private String updateResQTY(String sIniQTY, String sChgQTY, String sOp){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        int iIniQTY = Integer.parseInt(sIniQTY);
        System.out.println("Initial: " + sIniQTY);
        int iChgQTY = Integer.parseInt(sChgQTY);
        System.out.println("To add or rem: " + sChgQTY);
        int iNewQTY = 0;
        String sNewQTY = "";
        if ( sOp.equals("SUM")  ){iNewQTY = iIniQTY + iChgQTY;}
        if ( sOp.equals("RES")  ){iNewQTY = iIniQTY - iChgQTY;}
        sNewQTY = String.valueOf(iNewQTY);
        System.out.println("Result: " + sNewQTY);
        return sNewQTY;
    }
    //</editor-fold>
    
    //Updates the Teacher's sutdent list with the new Student
    private void removeStudent(int iTeaPos, String sStudent, int iStuPos){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Tea Pos: " + iTeaPos);
        System.out.println("Student name: " + sStudent);
        System.out.println("Student position: " + iStuPos);
        //Gets the current QTY of active Students assigned the Teacher 
        int iStudQty = Integer.valueOf(alProfDB.get(iTeaPos).getStudQty());
        System.out.println("Current QTY of Studs: " + iStudQty);
        //Checks for any remaining reserve under the same Teacher
        if ( !alStudDB.get(iStuPos).getReservs().contains(alProfDB.get(iTeaPos).getProfPIN()) ){//If the Teacher PIN is not found...
            System.out.println("Teacher PIN was not found between the Student's Reserves, hence removing...");
            //Removes the Stud from the Teacher´s DB
            iStudQty = iStudQty - 1;
            System.out.println("New Std QTY: " + iStudQty);
            System.out.println("Old Reserv code: " + alProfDB.get(iTeaPos).getStuList());
            String sTmp = alProfDB.get(iTeaPos).getStuList().replaceAll(sStudent + ">", "");
            System.out.println("New Reserve code: " + sTmp);
            if ( sTmp.equals("") ){sTmp = "NA";}
            //Updates the values in the Teacher DB ArrayList
            alProfDB.get(iTeaPos).setStuList(sTmp);
            alProfDB.get(iTeaPos).setStudQty(String.valueOf(iStudQty));
            System.out.println("FINAL VALUES");
            System.out.println(alProfDB.get(iTeaPos).getStuList());
            System.out.println(alProfDB.get(iTeaPos).getStudQty());
        }
    }
    //</editor-fold>
    
    //Gets the original amount of received lessons, line by line
    private void saveInitialLessons(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Cleans the Array
        for ( int i=0; i<arrMemb.length; i++ ){
            arrMemb[i][0] = "NA";
            arrMemb[i][1] = "NA";
        }
        //Fulfills the Array with the info in the screen: DRCs and Ini Lessons    
        for ( int i=0; i<jtblResMan.getRowCount(); i++ ){
            arrMemb[i][0] = jtblResMan.getValueAt(i, 7).toString();
            arrMemb[i][1] = jtblResMan.getValueAt(i, 4).toString();
        }
    }
    //</editor-fold>
    
    //Checks for changes into the received lessons and updates the Arrays as necesary
    private void updateResLessons(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Checks the table line by line and compares
        int i = 0;
        int d = 0;
        int iOldLess = 0;
        int iNewLess = 0;
        int iSPos = -1;
        int iTPos = -1;
        //Checks the table line by line
        for ( int r=0; r<jtblResMan.getRowCount(); r++ ){
            //Compares if the DRC on the table line is inside the Array of DRCs
            for ( i =0; i<arrMemb.length; i++ ){
                System.out.println("Checking table line " + r + " agains array pos " + i + "," + 0);
                if ( arrMemb[i][0].equals(jtblResMan.getValueAt(r,7).toString()) ){//If the DRC is found...
                    //Checks if there is a difference between the old value and the current one
                    System.out.println("Table DRC: " + jtblResMan.getValueAt(r,7).toString());
                    System.out.println("Array DRC: " + arrMemb[i][0]);
                    d = Integer.valueOf(jtblResMan.getValueAt(r,4).toString()) - Integer.valueOf(arrMemb[i][1]);
                    System.out.println("Value difference: " + d);
                    if ( d>0 ){//If there is a difference, it adds this difference to both, Student and Teacher
                        //Identifyes Student Position, gets the current QTY of received lessons and updates them
                        iSPos = findStudent(jtblResMan.getValueAt(r,7).toString().substring(0, 5));
                        iOldLess = Integer.valueOf(alStudDB.get(iSPos).getRecClas());
                        iNewLess = iOldLess + d;
                        alStudDB.get(iSPos).setRecClas(String.valueOf(iNewLess));
                        //Identifyes Teacher Position, gets the current QTY of received lessons and updates them
                        iTPos = findTeacher(jtblResMan.getValueAt(r,6).toString());
                        iOldLess = Integer.valueOf(alProfDB.get(iTPos).getImpClas());
                        iNewLess = iOldLess + d;
                        alProfDB.get(iTPos).setImpClas(String.valueOf(iNewLess));
                        System.out.println("Arrays updated:");
                        System.out.println("Stud rec class: " + alStudDB.get(iSPos).getRecClas());
                        System.out.println("Teac imp class:" + alProfDB.get(iTPos).getImpClas());
                    }
                }
            }
            
        }
    }
    //</editor-fold> 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlTop = new javax.swing.JPanel();
        jlblTop = new javax.swing.JLabel();
        jpnlMid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblResMan = new javax.swing.JTable();
        jpnlBot = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jbtnUndo = new javax.swing.JButton();
        jbtnLoad = new javax.swing.JButton();
        jbtnDel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jpnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblTop.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTop.setText("ADMINISTRACIÓN DE LECCIONES Y RESERVAS");

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

        jtblResMan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblResMan);

        javax.swing.GroupLayout jpnlMidLayout = new javax.swing.GroupLayout(jpnlMid);
        jpnlMid.setLayout(jpnlMidLayout);
        jpnlMidLayout.setHorizontalGroup(
            jpnlMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMidLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnlMidLayout.setVerticalGroup(
            jpnlMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMidLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
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

        jbtnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save_medium.png"))); // NOI18N
        jbtnSave.setText("Guardar");
        jbtnSave.setToolTipText("Guardar cambios en Alumno");
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jbtnUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Undo_Mid.png"))); // NOI18N
        jbtnUndo.setText("Descartar");
        jbtnUndo.setToolTipText("Descartar cambios y recargar");
        jbtnUndo.setEnabled(false);
        jbtnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUndoActionPerformed(evt);
            }
        });

        jbtnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loadicon_lil.png"))); // NOI18N
        jbtnLoad.setText("Cargar");
        jbtnLoad.setToolTipText("Guardar cambios en Alumno");
        jbtnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoadActionPerformed(evt);
            }
        });

        jbtnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_medium.png"))); // NOI18N
        jbtnDel.setText("Borrar");
        jbtnDel.setToolTipText("Borra la reserva de la base de datos del alumno");
        jbtnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBotLayout = new javax.swing.GroupLayout(jpnlBot);
        jpnlBot.setLayout(jpnlBotLayout);
        jpnlBotLayout.setHorizontalGroup(
            jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlBotLayout.setVerticalGroup(
            jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBotLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnExit)
                        .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnUndo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jbtnDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                    .addComponent(jpnlBot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Desea guardar los valores actuales?");
        if ( opt == 0 ){
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //Updates the corresponding DRCs in the Stu´s ArrayList
            updateDRCImpLess();
            //Updates the Received and imparted lesson for Tescher and Student
            updateResLessons();
            //Saves the ArrayLists into the coresonding TXT Data Bases
            updateStudentDB_txt();
            updateTeacherDB_txt();
            cleanResManTable();
            loadResManTable(sPIN);
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(this, "Los valores han sido actualizados correctamente");
        }
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoadActionPerformed
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        cleanResManTable();
        loadResManTable(sPIN);
        saveInitialLessons();
        setCursor(Cursor.getDefaultCursor());
        JOptionPane.showMessageDialog(this, "La información ha sido actualizada");
    }//GEN-LAST:event_jbtnLoadActionPerformed

    private void jbtnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUndoActionPerformed

    }//GEN-LAST:event_jbtnUndoActionPerformed

    private void jbtnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDelActionPerformed
        String sDRC = "";
        String sCompleteDRC = "";
        String sUpdQTY = "";
        int iPos = -1;
        int iRow = jtblResMan.getSelectedRow();
        if ( iRow < 0 ){
            JOptionPane.showMessageDialog(this, "Por favor seleccione una de las Reservas de la lista", "TCM MSG", JOptionPane.ERROR_MESSAGE);
        }
        else{
            int opt = JOptionPane.showConfirmDialog(this, "CONFIRME QUE DESEA ELIMINAR LA RESERVA SELECCIONADA");
            if ( opt == 0 ){
                if ( !jtblResMan.getValueAt(iRow, 5).toString().equals("000") || !jtblResMan.getValueAt(iRow, 4).toString().equals("000") ){
                    JOptionPane.showMessageDialog(this, "No se puede eliminar una reserva con lecciones pagadas o recibidas", "TCM MSG", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //Gets the DRC
                    sDRC = jtblResMan.getValueAt(iRow, 7).toString().substring(0, 24) +  
                            jtblResMan.getValueAt(iRow, 3).toString() + "." +
                            jtblResMan.getValueAt(iRow, 4).toString() + "." +
                            jtblResMan.getValueAt(iRow, 5).toString() + "." +
                            jtblResMan.getValueAt(iRow, 7).toString().substring(24) + ">";
                    System.out.println("Code to be deleted: " + sDRC);
                    //Locates the Student position into the Student DB ArrayList
                    iPos = findStudent(sPIN);
                    //Gets the DRC for this Student
                    sCompleteDRC = alStudDB.get(iPos).getReservs();
                    System.out.println("DRC found for this Student: " + sCompleteDRC);
                    //Removing DRC
                    sCompleteDRC = sCompleteDRC.replaceAll(sDRC, "");
                    if ( sCompleteDRC.equals("") ){sCompleteDRC = "NA";}
                    System.out.println("DRC removed. New Code: " + sCompleteDRC);
                    //Overwriting Student Reserves Code with the updated DRC
                    alStudDB.get(iPos).setReservs(sCompleteDRC);
                    System.out.println("The Reserve has een removed from the Student DB ArrayList");
                    //Udates the Student Res QTY
                    sUpdQTY = updateResQTY(alStudDB.get(iPos).getResClas(), jtblResMan.getValueAt(iRow, 3).toString(), "RES");
                    alStudDB.get(iPos).setResClas(sUpdQTY);
                    //Locates the Teacher position into the Teacher's DB ArrayList
                    iPos = findTeacher(this.jtblResMan.getValueAt(iRow, 6).toString());
                    //Upates the Teacher´s assigned classes
                    sUpdQTY = updateResQTY(alProfDB.get(iPos).getResClas(), jtblResMan.getValueAt(iRow, 3).toString(), "RES");
                    alProfDB.get(iPos).setResClas(sUpdQTY);
                    //Removes the Student from the Teacher´s List
                    removeStudent(iPos, jtblResMan.getValueAt(iRow, 0).toString(), findStudent(sPIN));
                    //Updates the TXT data Bases
                    updateStudentDB_txt();
                    updateTeacherDB_txt();
                    //Cleans and reloads the screen table
                    cleanResManTable();
                    loadResManTable(sPIN);
                    setCursor(Cursor.getDefaultCursor());
                    JOptionPane.showMessageDialog(this, "La reserva ha sido removida correctamente");
                }
            } 
            else{
                JOptionPane.showMessageDialog(this, "No se han efectuado cambios");
            }
        }
    }//GEN-LAST:event_jbtnDelActionPerformed


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
            java.util.logging.Logger.getLogger(gui_Reserve_Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_Reserve_Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_Reserve_Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_Reserve_Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_Reserve_Manager().setVisible(true);
            }
        });
    }

    
    //<editor-fold defaultstate="collapsed" desc="Variables declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnDel;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnLoad;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JButton jbtnUndo;
    private javax.swing.JLabel jlblTop;
    private javax.swing.JPanel jpnlBot;
    private javax.swing.JPanel jpnlMid;
    private javax.swing.JPanel jpnlTop;
    private javax.swing.JTable jtblResMan;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
