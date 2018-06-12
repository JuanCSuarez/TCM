package club_manager;

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


public class gui_StudentAdmin extends javax.swing.JFrame {

    //Data Bases Paths
    private String sLocStudDBPath;
    private String sLocProfDBPath;
    //Screen counters
    private int iStudQTY = 0;
    
    private String sOldProf = "";
    
    
    //ArrayList that will store the complete data base of Students
    private ArrayList<cls_Student> alStudDB = new ArrayList<>();
    //ArrayList that will store the complete data base of Teachers
    private ArrayList<cls_Teacher> alProfDB = new ArrayList<>();
    //ArrayList that will store the complete data base of Invoices
    private ArrayList<cls_Invoice> alInvoDB = new ArrayList<>();
    
    //ArrayList contact position
    int iPos;
    String[] arrEmail = new String[2];
    
    //Variables for the last two created PINs
    private String sLastProfPIN = "";
    private String sLastStudPIN = "";
    
    
    public gui_StudentAdmin(String sLocStudDBPath, ArrayList<cls_Student> alTmpStudentDB, 
            String sLocProfDBPath, ArrayList<cls_Teacher> alTmpTeacherDB, 
            int iPos, ArrayList<cls_Invoice> alTmpInvDB) {
        initComponents();
        System.out.println("Starting Students Control Module");
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("TCM - CONTROL DE ALUMNOS");
        
        this.sLocStudDBPath = sLocStudDBPath;
        this.alStudDB = alTmpStudentDB;
        this.sLocProfDBPath = sLocProfDBPath;
        this.alProfDB = alTmpTeacherDB;
        
        this.iPos = iPos;
        
        this.alInvoDB = alTmpInvDB;
        
        loadLastStudPIN();
        
        if ( iPos > -1 ){
            loadStudentData();
        }
    }

    private gui_StudentAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Cleans the whole screen values
    private void cleanScreen(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        this.jtxtNam.setText("");
        this.jtxtLstNam.setText("");
        this.jtxtIde.setText("");
        this.jchkboxYoung.setSelected(false);
        this.jtxtMail.setText("");
        this.jtxtDomain.setText("");
        this.jtxtHomNum.setText("");
        this.jtxtCelNum.setText("");
        this.jtxtAdd.setText("");
        this.jtxtCit.setText("");
        this.jcboxProv.select(0);
        this.jlblStudPIN.setText("ID: ");
        this.jcboxStat.select(0);
        this.jcboxStuLev.select(0);
        this.jtxtRes.setText("");
        this.jtxtRec.setText("");
        this.jtxtPay.setText("");
    }
    //</editor-fold>
    
    //Separates the mail and domain from the provided e-mail address
    //Stores those Strings into an Array in separate positions
    private void separateEmail(String sEmail){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String tmpMail="", tmpDomain="";
        int iPos = 0, i=0;
        do{
            tmpMail = tmpMail + sEmail.charAt(iPos);
            iPos++;
        }
        while ( sEmail.charAt(iPos) != '@');
        arrEmail[0] = tmpMail;
        arrEmail[1] = sEmail.substring(iPos+1);
    }
    //</editor-fold>
    
    //Reformats the ID String to an alpha-numeric value in order to be compared
    private String convertAlphaNumber(String sID){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sIdNum = sID;
        sIdNum = sIdNum.toString().replace('.',' ');
        sIdNum = sID.toString().replaceAll(" ","");
        sIdNum = sIdNum.toString().replaceAll("-","");
        sIdNum = sIdNum.toUpperCase();
        return sIdNum;        
    }
    //</editor-fold>
    
    //Checks if two given Strings match or not
    private boolean compareStrings(String sVal1, String sVal2){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        boolean bFlag = false;
        if ( sVal1.equals(sVal2) ){bFlag = true;}        
        return bFlag;
    }
    //</editor-fold>
    
    //Removes Student from Teacher´s List
    private void removeStudentFromTeacherList(String sTeacher, String sStudent){
    //<editor-fold defaultstate="collpased" desc="Method Source Code">    
        System.out.println("Teacher: " + sTeacher);
        System.out.println("Student: " + sStudent);
        int iStudQty = 0;
        String sStudList = "";
        //Identifies the Teacher from the data Base
        for ( int i=0; i<alProfDB.size(); i++ ){
            //If the Teacher's name is identified
            if ( (alProfDB.get(i).getFstName() + " " + alProfDB.get(i).getLstName()).equals(sTeacher) ){
                //Removes one Student from the total QTY of assigned people
                System.out.println("Current Student QTY: " + alProfDB.get(i).getStudQty());
                iStudQty = Integer.valueOf(alProfDB.get(i).getStudQty()) - 1;
                alProfDB.get(i).setStudQty(String.valueOf(iStudQty));
                System.out.println("New Student QTY: " + alProfDB.get(i).getStudQty());
                //Removes the Student from the Student´s List
                sStudList = alProfDB.get(i).getStuList();
                System.out.println("Current StudList: " + sStudList);
                System.out.println(sStudent + ">");
                sStudList = sStudList.replaceAll(sStudent + ">", "");
                System.out.println("Candidate to New StusList: " + sStudList);
                alProfDB.get(i).setStuList(sStudList);
                System.out.println("New StusList: " + alProfDB.get(i).getStuList());
            }
        }
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
    
    //Checks the String into the filed and replaces "" or "Escoja un valor" with a "NA"
    private String checkField(String sField){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sValue = "NA";
        if (!sField.equals("") && !sField.equals("Escoja un valor") ){
            sValue = sField;
        }
        return sValue;
    }
    //</editor-fold>
    
    //Creates a Student contact according with the screen values
    private void createNewStudent(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sFstNam="NA", sMidNam="NA", sLstNam="NA", sIdNum="NA", sEMail="NA", sHomNum="NA",
                sCelNum="NA", sAddMai="NA", sAddCit="NA", sAddSta="NA", sAddZip="NA",
                sLevel="NA", sStatut="NA", sResCla="NA", sRecCla="NA", sMemshp="NA",
                sPayClas="NA", sTeaNam="", sTeaCat="NA",  sStudPIN="NA", sReservs="NA", 
                sXXX1="NA", sXXX2="NA", sXXX3="NA", sXXX4="NA"; 
        //Captures each line on the table screen
        sFstNam = checkField(this.jtxtNam.getText());
        sMidNam = "NA";
        sLstNam = checkField(this.jtxtLstNam.getText());
        
        if ( jchkboxYoung.isSelected() == true ){sIdNum = "Menor";}
        else{sIdNum = checkField(this.jtxtIde.getText());}
        
        if ( this.jtxtMail.getText().equals("") || this.jtxtDomain.getText().equals("") ){sEMail = "NA";}
        else{sEMail = jtxtMail.getText() + "@" + jtxtDomain.getText();}
        
        sHomNum = checkField(this.jtxtHomNum.getText());
        sCelNum = checkField(this.jtxtCelNum.getText());
        sAddMai = checkField(this.jtxtAdd.getText());
        sAddCit = checkField(this.jtxtCit.getText());
        sAddSta = checkField(this.jcboxProv.getSelectedItem());
        sAddZip = "NA";
        sLevel = checkField(this.jcboxStuLev.getSelectedItem());
        sStatut = checkField(this.jcboxStat.getSelectedItem());
        sResCla = "0";
        sRecCla = "0";
        sMemshp = "0";
        sPayClas = "0";
        sTeaNam = "NA";
        sTeaCat = "NA";
        sStudPIN = generateStudPIN();
        sReservs = "NA";
        sXXX1 = "NA";
        sXXX2 = "NA";
        sXXX3 = "NA";
        sXXX4 = "NA";
        //Creates new lines in the ArrayList for every captured line from the screen table (this includes the changes made)
        alStudDB.add(new cls_Student(sFstNam, sMidNam, sLstNam, sIdNum, sEMail, sHomNum, sCelNum,
                sAddMai, sAddCit, sAddSta, sAddZip, sLevel, sStatut, sResCla, sRecCla, sMemshp, 
                sPayClas, sTeaNam, sTeaCat,  sStudPIN, sReservs, sXXX1, sXXX2, sXXX3, sXXX4));
        //Sets the new ID in the corresponding label
        this.jlblStudPIN.setText("ID: " + sStudPIN);
    }
    //</editor-fold>
    
    //Updates a Student contact in the ArrayList according with the screen values
    private void saveStudent(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sFstNam="NA", sLstNam="NA", sIdNum="NA", sEMail="NA", sHomNum="NA", sCelNum="NA", 
                sAddMai="NA", sAddCit="NA", sAddSta="NA", sLevel="NA", sStatut="NA", sResCla="NA", 
                sRecCla="NA", sPayCla="NA"; 
        //Captures each line on the table screen
        sFstNam = checkField(this.jtxtNam.getText());
        sLstNam = checkField(this.jtxtLstNam.getText());
        
        if ( jchkboxYoung.isSelected() == true ){sIdNum = "Menor";}
        else{sIdNum = checkField(this.jtxtIde.getText());}
        
        if ( this.jtxtMail.getText().equals("") || this.jtxtDomain.getText().equals("") ){sEMail = "NA";}
        else{sEMail = jtxtMail.getText() + "@" + jtxtDomain.getText();}
        
        sHomNum = checkField(this.jtxtHomNum.getText());
        sCelNum = checkField(this.jtxtCelNum.getText());
        sAddMai = checkField(this.jtxtAdd.getText());
        sAddCit = checkField(this.jtxtCit.getText());
        sAddSta = checkField(this.jcboxProv.getSelectedItem());
        sLevel = checkField(this.jcboxStuLev.getSelectedItem());
        sStatut = checkField(this.jcboxStat.getSelectedItem());
        if ( checkField(this.jtxtRes.getText()).equals("NA") ){sResCla = "0";} else{sResCla = jtxtRes.getText();}
        if ( checkField(this.jtxtRec.getText()).equals("NA") ){sRecCla = "0";} else{sRecCla = jtxtRec.getText();}
        sPayCla = checkField(this.jtxtPay.getText());
        //Updates the ArrayList line with every captured field from the screen table (this includes the changes made)
        alStudDB.get(iPos).setFstName(sFstNam);
        alStudDB.get(iPos).setLstName(sLstNam);
        alStudDB.get(iPos).setIdNumb(sIdNum);
        alStudDB.get(iPos).setHomNumb(sHomNum);
        alStudDB.get(iPos).setEmail(sEMail);
        alStudDB.get(iPos).setCelNumb(sCelNum);
        alStudDB.get(iPos).setAddMain(sAddMai);
        alStudDB.get(iPos).setAddCity(sAddCit);
        alStudDB.get(iPos).setAddStat(sAddSta);
        alStudDB.get(iPos).setStuLevl(sLevel);
        alStudDB.get(iPos).setStatus(sStatut);
        alStudDB.get(iPos).setResClas(sResCla);
        alStudDB.get(iPos).setRecClas(sRecCla);
        alStudDB.get(iPos).setPayClas(sPayCla);        
    }
    //</editor-fold>
    
    //Loads the Student Data Base from a local .txt file into the Students ArrayList
    private void loadStudDB(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        this.alStudDB.clear();
        this.iStudQTY = 0;
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sFstNam="", sMidNam="", sLstNam="", sIdNum="", sEMail="", sHomNum="",
                sCelNum="", sAddMai="", sAddCit="", sAddSta="", sAddZip="",
                sCatego="", sLevel="", sStatut="", sResCla="", sRecCla="",
                sMemshp="", sPayClas="", sTeaNam="", sTeaCat="", sStudPIN="", 
                sReservs="", sXXX1="", sXXX2="", sXXX3="", sXXX4="";
        try
        {
            fDataBase = new File(sLocStudDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Loading the list of Students from the .txt file into the ArrayList
            chain = br.readLine();
            while( !chain.equals("DB LINES") )
            {
                String [] position = chain.split("\t");
                sFstNam = position[0];
                sMidNam = position[1];
                sLstNam = position[2];
                sIdNum = position[3];
                sEMail = position[4];
                sHomNum = position[5];
                sCelNum = position[6];
                sAddMai = position[7];
                sAddCit = position[8];
                sAddSta = position[9];
                sAddZip = position[10];
                sLevel = position[11];
                sStatut = position[12];
                sResCla = position[13];
                sRecCla = position[14];
                sMemshp = position[15];
                sPayClas = position[16];
                sTeaNam = position[17];
                sTeaCat = position[18];
                sStudPIN = position[19];
                sReservs = position[20];
                sXXX1 = position[21];
                sXXX2 = position[22];
                sXXX3 = position[23];
                sXXX4 = position[24];
                alStudDB.add(new cls_Student(sFstNam, sMidNam, sLstNam, sIdNum, sEMail, sHomNum, sCelNum, 
                        sAddMai, sAddCit, sAddSta, sAddZip, sLevel, sStatut, sResCla, sRecCla, sMemshp, 
                        sPayClas, sTeaNam, sTeaCat, sStudPIN, sReservs, sXXX1, sXXX2, sXXX3, sXXX4));
                chain = br.readLine();
            }
            chain = br.readLine();
            iStudQTY = Integer.valueOf(chain);
            //Loads the last Student PIN created
            chain = br.readLine();
            chain = br.readLine();
            this.sLastStudPIN = chain;
            br.close();
            fr.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"No se pudo cargar la información desde la base de datos local.\n"
                    + "El Sistema ha generado un error mienstras cargaba la base de datos local al Arreglo:\n"
                    + e, "TCM ERROR - loadStudDB()", JOptionPane.ERROR_MESSAGE );
        }
    }
    //</editor-fold>
    
    //Loads the Student data from the ArrayList index position into the edition screen
    private void loadStudentData(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        this.jtxtNam.setText(alStudDB.get(iPos).getFstName());
        this.jtxtLstNam.setText(alStudDB.get(iPos).getLstName());
        if ( alStudDB.get(iPos).getIdNumb().equals("Menor") ){
            jchkboxYoung.setSelected(true);
            this.jtxtIde.setText("");
        }
        else{
            this.jtxtIde.setText(alStudDB.get(iPos).getIdNumb());
        }
        if ( !alStudDB.get(iPos).getEmail().equals("NA") ){
            separateEmail(alStudDB.get(iPos).getEmail());
            this.jtxtMail.setText(arrEmail[0]);
            this.jtxtDomain.setText(arrEmail[1]);
        }
        else{
            this.jtxtMail.setText("NA");
            this.jtxtDomain.setText("NA");
        }
        this.jtxtHomNum.setText(alStudDB.get(iPos).getHomNumb());
        this.jtxtCelNum.setText(alStudDB.get(iPos).getCelNumb());
        this.jtxtAdd.setText(alStudDB.get(iPos).getAddMain());
        this.jtxtCit.setText(alStudDB.get(iPos).getAddCity());
        this.jcboxProv.select(alStudDB.get(iPos).getAddStat());
        this.jcboxStat.select(alStudDB.get(iPos).getStatus());
        this.jlblStudPIN.setText("ID: " + alStudDB.get(iPos).getStudPIN());//Loads the Student PIN into the corresponding label
        this.jcboxStuLev.select(alStudDB.get(iPos).getStuLevl());
        this.jtxtRes.setText(alStudDB.get(iPos).getResClas());
        this.jtxtRec.setText(alStudDB.get(iPos).getRecClas());
        this.jtxtPay.setText(alStudDB.get(iPos).getPayClas());
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
            //Loads the Student QTY into the screen label
            iStudQTY = alStudDB.size();
            //Writes the DB lines QTY
            wr.println("DB LINES");
            wr.println(String.valueOf(iStudQTY));
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
            //Writes the DB lines QTY
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
        
    //Generates a new PIN for the Student
    private String generateStudPIN(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        System.out.println("Last PIN: " + sLastStudPIN);
        //Creates the next PIN as of the last known PIN
        String sTmpPIN = sLastStudPIN.substring(1);
        int iPIN = Integer.valueOf(sTmpPIN) + 1;
        String sPIN = String.valueOf(iPIN);
        //Reformats the String to six chars length
        //Counts how many char we have so far
        int iCharCount = sPIN.length();
        //Then adds the necessary left zeroes at the left
        switch (iCharCount) {
            case 1 : {sPIN = "000" + sPIN; break;}
            case 2 : {sPIN = "00" + sPIN; break;}
            case 3 : {sPIN = "0" + sPIN; break;}
            case 4 : {sPIN = sPIN; break;}
            default : {break;}
        }
        //Updates the last Student PIN value
        sPIN = "A" + sPIN;
        this.sLastStudPIN = sPIN;
        return sPIN;
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
    
    //Creates an string with a description of the assigned Teacher
    private String getTeacherData(int iIndex){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sData = "";
        sData = sData  + "Profesor asignado: " + this.alStudDB.get(iIndex).getTeaName();
        return sData;
    }
    //</editor-fold>
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        checkbox4 = new java.awt.Checkbox();
        jpnlTop = new javax.swing.JPanel();
        jlblTop = new javax.swing.JLabel();
        jpnlBottom = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnPic = new javax.swing.JButton();
        jbtnUndo = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jbtnReserv = new javax.swing.JButton();
        jbtnNew = new javax.swing.JButton();
        jpnlMiddle = new javax.swing.JPanel();
        jpnlMid1 = new javax.swing.JPanel();
        jlblNam = new javax.swing.JLabel();
        jtxtNam = new javax.swing.JTextField();
        jlblLstNam = new javax.swing.JLabel();
        jtxtLstNam = new javax.swing.JTextField();
        jlblIde = new javax.swing.JLabel();
        jtxtIde = new javax.swing.JTextField();
        jlblMail = new javax.swing.JLabel();
        jtxtMail = new javax.swing.JTextField();
        jlblNum = new javax.swing.JLabel();
        jtxtHomNum = new javax.swing.JTextField();
        jtxtDomain = new javax.swing.JTextField();
        jlblArrob = new javax.swing.JLabel();
        checkbox1 = new java.awt.Checkbox();
        checkbox3 = new java.awt.Checkbox();
        checkbox2 = new java.awt.Checkbox();
        jchkboxYoung = new javax.swing.JCheckBox();
        jlblPhoneIcon = new javax.swing.JLabel();
        jlblCellIcon = new javax.swing.JLabel();
        jtxtCelNum = new javax.swing.JTextField();
        jpnlMid2 = new javax.swing.JPanel();
        jlblAdd = new javax.swing.JLabel();
        jtxtAdd = new javax.swing.JTextField();
        jlblAdd2 = new javax.swing.JLabel();
        jtxtCit = new javax.swing.JTextField();
        jcboxProv = new java.awt.Choice();
        jcboxStat = new java.awt.Choice();
        jlblStat = new javax.swing.JLabel();
        jpnlMid3 = new javax.swing.JPanel();
        jlblStuLev = new javax.swing.JLabel();
        jcboxStuLev = new java.awt.Choice();
        jpnlMid4 = new javax.swing.JPanel();
        jlblRes = new javax.swing.JLabel();
        jtxtRes = new javax.swing.JTextField();
        jlblRec = new javax.swing.JLabel();
        jtxtRec = new javax.swing.JTextField();
        jbtnStudReport = new javax.swing.JButton();
        jlblPay = new javax.swing.JLabel();
        jtxtPay = new javax.swing.JTextField();
        jlblStudPic = new javax.swing.JLabel();
        jlblStudPIN = new javax.swing.JLabel();

        checkbox4.setLabel("checkbox2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jpnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblTop.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jlblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTop.setText("CONTROL DE ALUMNOS");

        javax.swing.GroupLayout jpnlTopLayout = new javax.swing.GroupLayout(jpnlTop);
        jpnlTop.setLayout(jpnlTopLayout);
        jpnlTopLayout.setHorizontalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnlTopLayout.setVerticalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTop)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jpnlBottom.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbtnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit_medium.png"))); // NOI18N
        jbtnExit.setText("Salir");
        jbtnExit.setToolTipText("Regresar a pantalla principal");
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        jbtnPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/photo_medium.png"))); // NOI18N
        jbtnPic.setText("Subir Foto");
        jbtnPic.setToolTipText("Subir fotografía del Alumno");
        jbtnPic.setEnabled(false);

        jbtnUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh_2_medium.png"))); // NOI18N
        jbtnUndo.setText("Refrescar");
        jbtnUndo.setToolTipText("Descartar cambios y recargar");
        jbtnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUndoActionPerformed(evt);
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

        jbtnReserv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/numberlist_medium.png"))); // NOI18N
        jbtnReserv.setText("Reservas");
        jbtnReserv.setToolTipText("Ver información de Profesor asigando");
        jbtnReserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReservActionPerformed(evt);
            }
        });

        jbtnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_medium.png"))); // NOI18N
        jbtnNew.setText("Nuevo");
        jbtnNew.setToolTipText("Limpia la pantalla para agregar un nuevo Alumno");
        jbtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBottomLayout = new javax.swing.GroupLayout(jpnlBottom);
        jpnlBottom.setLayout(jpnlBottomLayout);
        jpnlBottomLayout.setHorizontalGroup(
            jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnReserv, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnPic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlBottomLayout.setVerticalGroup(
            jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnUndo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnReserv)
                    .addComponent(jbtnExit)
                    .addComponent(jbtnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpnlMiddle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jpnlMid1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        jlblNam.setText("Nombre");

        jlblLstNam.setText("Apellido(s)");

        jlblIde.setText("Núm. Identificación");

        jlblMail.setText("E-Mail");

        jlblNum.setText("Teléfono / Celular");

        jtxtHomNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtHomNumActionPerformed(evt);
            }
        });

        jlblArrob.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlblArrob.setForeground(new java.awt.Color(0, 100, 255));
        jlblArrob.setText("@");

        checkbox1.setLabel("checkbox1");

        checkbox3.setLabel("checkbox2");

        checkbox2.setLabel("checkbox2");

        jchkboxYoung.setText(" Menor de edad");
        jchkboxYoung.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jchkboxYoungItemStateChanged(evt);
            }
        });
        jchkboxYoung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkboxYoungActionPerformed(evt);
            }
        });

        jlblPhoneIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/phone_Lil.fw.png"))); // NOI18N

        jlblCellIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Cell_Lil.fw.png"))); // NOI18N

        javax.swing.GroupLayout jpnlMid1Layout = new javax.swing.GroupLayout(jpnlMid1);
        jpnlMid1.setLayout(jpnlMid1Layout);
        jpnlMid1Layout.setHorizontalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblIde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblLstNam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblMail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblNam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtNam)
                    .addComponent(jtxtLstNam)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlMid1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jtxtMail, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(jtxtIde)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpnlMid1Layout.createSequentialGroup()
                                .addComponent(jlblPhoneIcon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtHomNum)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpnlMid1Layout.createSequentialGroup()
                                .addComponent(jlblArrob)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtDomain, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jchkboxYoung, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpnlMid1Layout.createSequentialGroup()
                                .addComponent(jlblCellIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtCelNum)))))
                .addContainerGap())
        );
        jpnlMid1Layout.setVerticalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblNam)
                    .addComponent(jtxtNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblLstNam)
                    .addComponent(jtxtLstNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblIde)
                    .addComponent(jtxtIde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jchkboxYoung))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblMail)
                    .addComponent(jtxtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtDomain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblArrob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlblNum)
                        .addComponent(jtxtHomNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlblPhoneIcon)
                    .addComponent(jlblCellIcon)
                    .addComponent(jtxtCelNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnlMid2.setBorder(javax.swing.BorderFactory.createTitledBorder("Localización"));

        jlblAdd.setText("Dirección");

        jlblAdd2.setText("Cuidad  / Provincia");

        jcboxProv.add("Escoja un valor");
        jcboxProv.add("San José");
        jcboxProv.add("Alajuela");
        jcboxProv.add("Heredia");
        jcboxProv.add("Cartago");
        jcboxProv.add("Puntarenas");
        jcboxProv.add("Guanacaste");
        jcboxProv.add("Limón");

        javax.swing.GroupLayout jpnlMid2Layout = new javax.swing.GroupLayout(jpnlMid2);
        jpnlMid2.setLayout(jpnlMid2Layout);
        jpnlMid2Layout.setHorizontalGroup(
            jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlblAdd2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(jlblAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtAdd)
                    .addGroup(jpnlMid2Layout.createSequentialGroup()
                        .addComponent(jtxtCit, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxProv, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpnlMid2Layout.setVerticalGroup(
            jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblAdd)
                    .addComponent(jtxtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlblAdd2)
                        .addComponent(jtxtCit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcboxProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jcboxStat.add("Escoja un valor");
        jcboxStat.add("Activo");
        jcboxStat.add("Inactivo");
        jcboxStat.add("Vacaciones");
        jcboxStat.add("Ninguno");

        jlblStat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblStat.setText("ESTADO   ");

        jpnlMid3.setBorder(javax.swing.BorderFactory.createTitledBorder("Asignación de Niveles"));

        jlblStuLev.setText("Nivel del Alumno");

        jcboxStuLev.addItem("Escoja un valor");
        jcboxStuLev.addItem("Principiante");
        jcboxStuLev.addItem("Intermedio");
        jcboxStuLev.addItem("Avanzado");

        javax.swing.GroupLayout jpnlMid3Layout = new javax.swing.GroupLayout(jpnlMid3);
        jpnlMid3.setLayout(jpnlMid3Layout);
        jpnlMid3Layout.setHorizontalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblStuLev, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboxStuLev, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jpnlMid3Layout.setVerticalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblStuLev)
                    .addComponent(jcboxStuLev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnlMid4.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado Actual (Reservas activas)"));

        jlblRes.setText("Lecciones reservadas");

        jtxtRes.setEditable(false);

        jlblRec.setText("Lecciones recibidas");

        jtxtRec.setEditable(false);

        jbtnStudReport.setText("Crear Reporte");
        jbtnStudReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnStudReportActionPerformed(evt);
            }
        });

        jlblPay.setText("Lecciones pagadas");

        jtxtPay.setEditable(false);

        javax.swing.GroupLayout jpnlMid4Layout = new javax.swing.GroupLayout(jpnlMid4);
        jpnlMid4.setLayout(jpnlMid4Layout);
        jpnlMid4Layout.setHorizontalGroup(
            jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnStudReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpnlMid4Layout.createSequentialGroup()
                        .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlblPay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblRes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jlblRec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtRes, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(jtxtRec)
                            .addComponent(jtxtPay))))
                .addContainerGap())
        );
        jpnlMid4Layout.setVerticalGroup(
            jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblRes)
                    .addComponent(jtxtRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblRec)
                    .addComponent(jtxtRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblPay)
                    .addComponent(jtxtPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnStudReport)
                .addContainerGap())
        );

        jlblStudPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblStudPic.setText("FOTO");
        jlblStudPic.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblStudPIN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlblStudPIN.setForeground(new java.awt.Color(0, 102, 255));
        jlblStudPIN.setText("   ID: ");

        javax.swing.GroupLayout jpnlMiddleLayout = new javax.swing.GroupLayout(jpnlMiddle);
        jpnlMiddle.setLayout(jpnlMiddleLayout);
        jpnlMiddleLayout.setHorizontalGroup(
            jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMiddleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlMid2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlMid1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlMiddleLayout.createSequentialGroup()
                        .addComponent(jpnlMid4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblStudPic, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlMiddleLayout.createSequentialGroup()
                        .addComponent(jlblStudPIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblStat, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxStat, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpnlMid3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlMiddleLayout.setVerticalGroup(
            jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMiddleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMiddleLayout.createSequentialGroup()
                        .addComponent(jpnlMid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpnlMid2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlMiddleLayout.createSequentialGroup()
                        .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jlblStat)
                                .addComponent(jlblStudPIN))
                            .addComponent(jcboxStat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnlMid3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpnlMid4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblStudPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlMiddle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlMiddle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpnlBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Desea salir?");
        if ( opt == 0 ){
            gui_MainScreen tmpMS = new gui_MainScreen(2);
            tmpMS.setLocationRelativeTo(this);
            tmpMS.setVisible(true);
            tmpMS.iTab = 2;
            dispose();
        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        int opt = -1;
        if ( iPos == -1 ){ //This means that this is a new Student
            //Checks if the new Student has been saved before closing the windows for the first time
            int iIndex = findStudent(this.jlblStudPIN.getText().substring(4));
            if ( iIndex >= 0 ){ //This means that the new Student was previously saved into the Data Base
                opt = JOptionPane.showConfirmDialog(this, "El Alumno ya existe es la Base de Datos. Desea sobrescribirlo con los valores actuales?");
                if ( opt == 0 ){//The Student will be overwritten
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //Updates the Student position with the new asigned
                    iPos = iIndex;
                    //Overwrites the data of a Student in the ArrayList
                    saveStudent();
                    //Updates the Student TXT Data Base
                    updateStudentDB_txt();
                    setCursor(Cursor.getDefaultCursor());
                    JOptionPane.showMessageDialog(this, "La información del Alumno ha sido actualizada");
                }
            }
            else{//This means that the new Student has not been saved yet 
                opt = JOptionPane.showConfirmDialog(this, "Confirma que desea crear el nuevo Alumno?");
                if ( opt == 0 ){
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //Saves the ccurent data in the screen as new entry in the Students ArrayList 
                    createNewStudent();
                    //Updates the Student TXT Data Base
                    updateStudentDB_txt();
                    setCursor(Cursor.getDefaultCursor());
                    JOptionPane.showMessageDialog(this, "El nuevo Alumno ha sido agregado a la Base de Datos");
                }
                else{
                    JOptionPane.showMessageDialog(this, "No se ha efectuado ningún cambio");
                }
            }
        }
        else{//This means that we are editing an existing Student
            opt = JOptionPane.showConfirmDialog(this, "Confirma que desea guardar los valores actuales?");
            if ( opt == 0 ){//Current values will be saved
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                //Overwrites the data of a Student in the ArrayList
                saveStudent();
                //Updates the Student TXT Data Base
                updateStudentDB_txt();
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "La información ha sido guardada en la Base de Datos");
            }
            else{
                JOptionPane.showMessageDialog(this, "No se han guardado cambios");
            }
        }
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUndoActionPerformed
        //Checks ifthe Student in the screen has been already saved or not
        if ( iPos == -1 ){
            JOptionPane.showMessageDialog(this,"El Alumno no existe aun en la Base de Datos");
        }
        else{//If the Student exists...
            int opt = JOptionPane.showConfirmDialog(this, "ADVERTENCIA: Los cambios no guardados se perderán. Desea continuar?");
            if ( opt == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                //Reload the Student data accoding with the info in the Student ArrayList
                loadStudDB();
                //Looks for the given Student and loads his info into the screen
                loadStudentData();
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "La información del Alumno ha sido refrescada correctamente");
            } 
        }
    }//GEN-LAST:event_jbtnUndoActionPerformed

    private void jbtnReservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReservActionPerformed
        int iIndex = findStudent(this.jlblStudPIN.getText().substring(4));
        if ( iIndex == -1 ){//Checks if the Student already exists into the ArrayList data base
             JOptionPane.showMessageDialog(this, "El Alumno no fue encontrado en la Base de Datos.");
         }
         else{//If the Student exists in the data Base...
            gui_Reserve_Manager tmpRM = new gui_Reserve_Manager(sLocStudDBPath, alStudDB, sLocProfDBPath, alProfDB, alStudDB.get(iIndex).getStudPIN(), 1);
            tmpRM.setLocationRelativeTo(this);
            tmpRM.setVisible(true);
        }
        
       
        
        
        
        
        
    }//GEN-LAST:event_jbtnReservActionPerformed

    private void jchkboxYoungItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jchkboxYoungItemStateChanged
        if ( jchkboxYoung.isSelected() == true ){
            jtxtIde.setText("");
            jtxtIde.setEnabled(false);
        }
        else{
            jtxtIde.setEnabled(true);
        }
    }//GEN-LAST:event_jchkboxYoungItemStateChanged

    private void jtxtHomNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtHomNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtHomNumActionPerformed

    private void jbtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNewActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Desea limpiar pantalla y agregar otro Alumno?\nADVERTENCIA: Los cambios que no haya guardado se perderán");
        if ( opt == 0 ){
            iPos = -1;
            cleanScreen();
            JOptionPane.showMessageDialog(this, "Puede proceder a agregar un nuevo Alumno");
        }
    }//GEN-LAST:event_jbtnNewActionPerformed

    private void jchkboxYoungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkboxYoungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkboxYoungActionPerformed

    private void jbtnStudReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnStudReportActionPerformed
        gui_Stud_Report tmpPR = new gui_Stud_Report(alInvoDB, sLocProfDBPath, alProfDB, sLocStudDBPath, alStudDB, this.jlblStudPIN.getText().substring(4) );
        tmpPR.setLocationRelativeTo(this);
        tmpPR.setVisible(true);
    }//GEN-LAST:event_jbtnStudReportActionPerformed

    
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
            java.util.logging.Logger.getLogger(gui_StudentAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_StudentAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_StudentAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_StudentAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_StudentAdmin().setVisible(true);
            }
        });
    }

    //<editor-fold defaultstate="collapsed" desc="Variables declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Checkbox checkbox1;
    private java.awt.Checkbox checkbox2;
    private java.awt.Checkbox checkbox3;
    private java.awt.Checkbox checkbox4;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnNew;
    private javax.swing.JButton jbtnPic;
    private javax.swing.JButton jbtnReserv;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JButton jbtnStudReport;
    private javax.swing.JButton jbtnUndo;
    private java.awt.Choice jcboxProv;
    private java.awt.Choice jcboxStat;
    private java.awt.Choice jcboxStuLev;
    private javax.swing.JCheckBox jchkboxYoung;
    private javax.swing.JLabel jlblAdd;
    private javax.swing.JLabel jlblAdd2;
    private javax.swing.JLabel jlblArrob;
    private javax.swing.JLabel jlblCellIcon;
    private javax.swing.JLabel jlblIde;
    private javax.swing.JLabel jlblLstNam;
    private javax.swing.JLabel jlblMail;
    private javax.swing.JLabel jlblNam;
    private javax.swing.JLabel jlblNum;
    private javax.swing.JLabel jlblPay;
    private javax.swing.JLabel jlblPhoneIcon;
    private javax.swing.JLabel jlblRec;
    private javax.swing.JLabel jlblRes;
    private javax.swing.JLabel jlblStat;
    private javax.swing.JLabel jlblStuLev;
    private javax.swing.JLabel jlblStudPIN;
    private javax.swing.JLabel jlblStudPic;
    private javax.swing.JLabel jlblTop;
    private javax.swing.JPanel jpnlBottom;
    private javax.swing.JPanel jpnlMid1;
    private javax.swing.JPanel jpnlMid2;
    private javax.swing.JPanel jpnlMid3;
    private javax.swing.JPanel jpnlMid4;
    private javax.swing.JPanel jpnlMiddle;
    private javax.swing.JPanel jpnlTop;
    private javax.swing.JTextField jtxtAdd;
    private javax.swing.JTextField jtxtCelNum;
    private javax.swing.JTextField jtxtCit;
    private javax.swing.JTextField jtxtDomain;
    private javax.swing.JTextField jtxtHomNum;
    private javax.swing.JTextField jtxtIde;
    private javax.swing.JTextField jtxtLstNam;
    private javax.swing.JTextField jtxtMail;
    private javax.swing.JTextField jtxtNam;
    private javax.swing.JTextField jtxtPay;
    private javax.swing.JTextField jtxtRec;
    private javax.swing.JTextField jtxtRes;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
