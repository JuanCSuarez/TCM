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


public class gui_TeacherAdmin extends javax.swing.JFrame {

    //Data Bases Paths
    private String sLocProfDBPath;
    private String sLocStudDBPath;
    //Screen counters
    private int iProfQTY = 0;
    
    //ArrayList that will store the complete data base of Teachers
    private ArrayList<cls_Teacher> alProfDB = new ArrayList<>();
    //ArrayList that will store the complete data base of Students
    private ArrayList<cls_Student> alStudDB = new ArrayList<>();
    //ArrayList that will store the complete data base of Invoices
    private ArrayList<cls_Invoice> alInvoDB = new ArrayList<>();
    
    //ArrayList contact position
    int iPos;
    String[] arrEmail = new String[2];
    
    //Variables for the last two created PINs
    private String sLastProfPIN = "";
    
    public gui_TeacherAdmin(String sLocStudDBPath, ArrayList<cls_Student> alTmpStudentDB, 
            String sLocProflDBPath, ArrayList<cls_Teacher> alTmpTeacherDB, 
            int iPos, ArrayList<cls_Invoice> alTmpInvDB) {
        initComponents();
        System.out.println("Starting Teachers Control Module");
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("TCM - CONTROL DE PROFESORES");
        
        this.sLocStudDBPath = sLocStudDBPath;
        this.alStudDB = alTmpStudentDB;
        this.sLocProfDBPath = sLocProflDBPath;
        this.alProfDB = alTmpTeacherDB;
        this.iPos = iPos;
        
        this.alInvoDB = alTmpInvDB;
                
        loadLastProfPIN();
        
        
        if ( iPos > -1 ){
            loadTeacherData();
        }
        
        
    }

    private gui_TeacherAdmin() {
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
        this.jlblProfPIN.setText("ID: ");
        this.jcboxStat.select(0);
        this.jtxtStud.setText("");
        this.jcboxCat.select(0);
        this.jtxtComm.setText("");
        this.jtxtRes.setText("");
        this.jtxtImp.setText("");
        this.jtxtSal.setText("");
        this.jlblProfPIN.setText("ID: ");
    }
    //</editor-fold>
    
    //Separates the mail and domian from the provided e-mail address
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
    
    //Gets the complete list of Students assigned to the Teacher
    public String getStudentsList(String sStudList){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sStudents = "Profesor:\n" + alProfDB.get(iPos).getLstName() + ", " + alProfDB.get(iPos).getFstName() + "\n"
                + "----------------------------------------------------------------------------------------------\n"
                + "LISTA:\n";
        if ( sStudList.equals("NA") ){
            sStudents = sStudents + "Sin alumnos asignados";
        }
        else{
            String sTmp = sStudList.replaceAll(">","\n");
            sStudents = sStudents + sTmp;
        }
        return sStudents;
    }
    //</editor-fold>
    
    //Sets the complete list of Students assigned to the Teacher
    public String setStudentsList(String sStudList){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sStudents = "";
        if ( sStudList.equals("NA") ){
            sStudents = sStudents + "Sin alumnos asignados";
        }
        else{
            String sTmp = sStudList.replaceAll("\n",">");
            sStudents = sStudents + sTmp;
        }
        return sStudents;
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
    
    //Creates a Teachers contact according with the screen values
    private void createNewTeacher(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        //Cleans the DB ArrayList
        String sFstNam="NA", sMidNam="NA", sLstNam="NA", sIdNum="NA", sEMail="NA", sHomNum="NA",
                sCelNum="NA", sAddMai="NA", sAddCit="NA", sAddSta="NA", sAddZip="NA", sCatego="NA", 
                sStatut="NA", sStuden="NA", sResCla="NA", sImpCla="NA", sPayCla="NA", sComPer="NA", 
                sSalary="NA", sHrsCost="NA", sStuList="NA", sProfPIN="NA", sXXX1="NA", sXXX2="NA", 
                sXXX3="NA", sXXX4="NA"; 
        //Captures each line on the table screen
        sFstNam = checkField(this.jtxtNam.getText());
        sMidNam = "NA";
        sLstNam = checkField(this.jtxtLstNam.getText());
        if ( jchkboxYoung.isSelected() == true  ){sIdNum = "Menor";}
        else{sIdNum = checkField(this.jtxtIde.getText());}
        if ( this.jtxtMail.getText().equals("") || this.jtxtDomain.getText().equals("") ){sEMail = "NA";}
        else{sEMail = jtxtMail.getText() + "@" + jtxtDomain.getText();}
        sHomNum = checkField(this.jtxtHomNum.getText());
        sCelNum = checkField(this.jtxtCelNum.getText());
        sAddMai = checkField(this.jtxtAdd.getText());
        sAddCit = checkField(this.jtxtCit.getText());
        sAddSta = checkField(this.jcboxProv.getSelectedItem());
        sAddZip = "NA";
        sCatego = checkField(this.jcboxCat.getSelectedItem());
        sStatut = checkField(this.jcboxStat.getSelectedItem());
        sStuden = "0";
        sResCla = "0";
        sImpCla = "0";
        sPayCla = "0";
        sComPer = checkField(this.jtxtComm.getText());
        sSalary = "0";
        sHrsCost = "NA";
        sStuList = "NA";
        sProfPIN = generateProfPIN();
        sXXX1 = "NA";
        sXXX2 = "NA";
        sXXX3 = "NA";
        sXXX4 = "NA";
        //Creates new lines in the ArrayList for every captured line from the screen table (this includes the changes made)
        alProfDB.add(new cls_Teacher(sFstNam, sMidNam, sLstNam, sIdNum, sEMail, sHomNum, sCelNum, 
                        sAddMai, sAddCit, sAddSta, sAddZip, sCatego, sStatut, sStuden, sResCla, sImpCla, sPayCla,
                        sComPer, sSalary, sHrsCost, sStuList, sProfPIN, sXXX1, sXXX2, sXXX3, sXXX4));
        //Sets the new ID in the corresponding label
        this.jlblProfPIN.setText("ID: " + sProfPIN);
    }
    //</editor-fold>
    
    //Updates a Teachers contact according with the screen values
    private void saveTeacher(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        //Cleans the DB ArrayList
        String sFstNam="NA", sMidNam="NA", sLstNam="NA", sIdNum="NA", sEMail="NA", sHomNum="NA",
                sCelNum="NA", sAddMai="NA", sAddCit="NA", sAddSta="NA", sAddZip="NA",
                sCatego="NA", sStatut="NA", sStuden="NA", sResCla="NA", sImpCla="NA", sPayCla="NA",
                sComPer="NA", sSalary="NA", sHrsCost="NA"; 
        //Captures each line on the table screen
        sFstNam = checkField(this.jtxtNam.getText());
        sMidNam = "NA";
        sLstNam = checkField(this.jtxtLstNam.getText());
        if ( jchkboxYoung.isSelected() == true  ){sIdNum = "Menor";}
        else{sIdNum = checkField(this.jtxtIde.getText());}
        if ( this.jtxtMail.getText().equals("") || this.jtxtDomain.getText().equals("") ){sEMail = "NA";}
        else{sEMail = jtxtMail.getText() + "@" + jtxtDomain.getText();}
        sHomNum = checkField(this.jtxtHomNum.getText());
        sCelNum = checkField(this.jtxtCelNum.getText());
        sAddMai = checkField(this.jtxtAdd.getText());
        sAddCit = checkField(this.jtxtCit.getText());
        sAddSta = checkField(this.jcboxProv.getSelectedItem());
        sAddZip = "NA";
        sCatego = checkField(this.jcboxCat.getSelectedItem());
        sStatut = checkField(this.jcboxStat.getSelectedItem());
        if ( checkField(this.jtxtStud.getText()).equals("NA") ){sStuden = "0";} else{sStuden = jtxtStud.getText();}
        if ( checkField(this.jtxtRes.getText()).equals("NA") ){sResCla = "0";} else{sResCla = jtxtRes.getText();}
        if ( checkField(this.jtxtImp.getText()).equals("NA") ){sImpCla = "0";} else{sImpCla = jtxtImp.getText();}
        if ( checkField(this.jtxtPay.getText()).equals("NA") ){sPayCla = "0";} else{sPayCla = jtxtPay.getText();}
        sComPer = checkField(this.jtxtComm.getText());
        if ( checkField(this.jtxtSal.getText()).equals("NA") ){sSalary = "0";} else{sSalary=jtxtSal.getText();}
        //Updates the ArrayList line with every captured field from the screen table (this includes the changes made)
        alProfDB.get(iPos).setFstName(sFstNam);
        alProfDB.get(iPos).setMidName(sMidNam);
        alProfDB.get(iPos).setLstName(sLstNam);
        alProfDB.get(iPos).setIdNumb(sIdNum);
        alProfDB.get(iPos).setHomNumb(sHomNum);
        alProfDB.get(iPos).setEmail(sEMail);
        alProfDB.get(iPos).setCelNumb(sCelNum);
        alProfDB.get(iPos).setAddMain(sAddMai);
        alProfDB.get(iPos).setAddCity(sAddCit);
        alProfDB.get(iPos).setAddStat(sAddSta);
        alProfDB.get(iPos).setAddZiCo(sAddZip);
        alProfDB.get(iPos).setCategry(sCatego);
        alProfDB.get(iPos).setStatus(sStatut);
        alProfDB.get(iPos).setStudQty(sStuden);
        alProfDB.get(iPos).setResClas(sResCla);
        alProfDB.get(iPos).setImpClas(sImpCla);
        alProfDB.get(iPos).setPayClas(sPayCla);
        alProfDB.get(iPos).setComPerc(sComPer);
        alProfDB.get(iPos).setSalary(sSalary);
        alProfDB.get(iPos).setHrsCost(sHrsCost);
    }
    //</editor-fold>
    
    //Loads the Teachers Data Base from a local .txt file into the Teachers ArrayList
    private void loadProfDB(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        this.alProfDB.clear();
        this.iProfQTY = 0;
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sFstNam="", sMidNam="", sLstNam="", sIdNum="", sEMail="", sHomNum="",
                sCelNum="", sAddMai="", sAddCit="", sAddSta="", sAddZip="",
                sCatego="", sStatut="", sStuden="", sResCla="", sImpCla="", sPayCla="",
                sComPer="", sSalary="", sHrsCost="", sStuList="", sProfPIN="", 
                sXXX1="", sXXX2="", sXXX3="", sXXX4="";
        try
        {
            fDataBase = new File(sLocProfDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Loading the list of Consults from the .txt file into the ArrayList
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
                sCatego = position[11];
                sStatut = position[12];
                sStuden = position[13];
                sResCla = position[14];
                sImpCla = position[15];
                sPayCla = position[16];
                sComPer = position[17];
                sSalary = position[18];
                sHrsCost = position[19];
                sStuList = position[20];
                sProfPIN = position[21];
                sXXX1 = position[22];
                sXXX2 = position[23];
                sXXX3 = position[24];
                sXXX4 = position[25];
                alProfDB.add(new cls_Teacher(sFstNam, sMidNam, sLstNam, sIdNum, sEMail, sHomNum, sCelNum, 
                        sAddMai, sAddCit, sAddSta, sAddZip, sCatego, sStatut, sStuden, sResCla, sImpCla, sPayCla,
                        sComPer, sSalary, sHrsCost, sStuList, sProfPIN, sXXX1, sXXX2, sXXX3, sXXX4));
                chain = br.readLine();
            }
            chain = br.readLine();
            iProfQTY = Integer.valueOf(chain);
            //Loads the last Teacher PIN created
            chain = br.readLine();
            chain = br.readLine();
            this.sLastProfPIN = chain;
            br.close();
            fr.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"No se pudo cargar la información desde la base de datos local.\n"
                    + "El Sistema ha generado un error mientras cargaba la base de datos local al Arreglo:\n"
                    + e, "TCM ERROR - loadProfDB()", JOptionPane.ERROR_MESSAGE );
        }
    }
    //</editor-fold>
        
    //Loads the Teacher data from the ArrayList index position into the edition screen
    private void loadTeacherData(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        this.jtxtNam.setText(alProfDB.get(iPos).getFstName());
        this.jtxtLstNam.setText(alProfDB.get(iPos).getLstName());
        
        if ( alProfDB.get(iPos).getIdNumb().equals("Menor") ){
            jchkboxYoung.setSelected(true);
            this.jtxtIde.setText("");
        }
        else{
            this.jtxtIde.setText(alProfDB.get(iPos).getIdNumb());
        }
                
        if ( !alProfDB.get(iPos).getEmail().equals("NA") ){
            separateEmail(alProfDB.get(iPos).getEmail());
            this.jtxtMail.setText(arrEmail[0]);
            this.jtxtDomain.setText(arrEmail[1]);
        }
        else{
            this.jtxtMail.setText("NA");
            this.jtxtDomain.setText("NA");
        }
        
        this.jtxtHomNum.setText(alProfDB.get(iPos).getHomNumb());
        this.jtxtCelNum.setText(alProfDB.get(iPos).getCelNumb());
        this.jtxtAdd.setText(alProfDB.get(iPos).getAddMain());
        this.jtxtCit.setText(alProfDB.get(iPos).getAddCity());
        this.jcboxProv.select(alProfDB.get(iPos).getAddStat());
        this.jlblProfPIN.setText("ID: " + alProfDB.get(iPos).getProfPIN());//Loads the PIN in the corresponding label
        this.jcboxStat.select(alProfDB.get(iPos).getStatus());
        this.jtxtStud.setText(alProfDB.get(iPos).getStudQty());
        this.jcboxCat.select(alProfDB.get(iPos).getCategry());
        this.jtxtComm.setText(alProfDB.get(iPos).getComPerc());
        this.jtxtRes.setText(alProfDB.get(iPos).getResClas());
        this.jtxtImp.setText(alProfDB.get(iPos).getImpClas());
        this.jtxtPay.setText(alProfDB.get(iPos).getPayClas());
        this.jtxtSal.setText(alProfDB.get(iPos).getSalary());
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
            //Loads the Teacher QTY into the screen label
            iProfQTY = alProfDB.size();
            wr.println("DB LINES");
            wr.println(String.valueOf(iProfQTY));
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
    
    //Generates a new PIN for the Teacher
    private String generateProfPIN(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        System.out.println("Last PIN: " + sLastProfPIN);
        //Creates the next PIN as of the last known PIN
        String sTmpPIN = sLastProfPIN.substring(1);
        int iPIN = Integer.valueOf(sTmpPIN) + 1;
        String sPIN = String.valueOf(iPIN);
        //Reformats the String to six chars length
        //Counts how many char we have so far
        int iCharCount = sPIN.length();
        //Then adds the necessary left zeroes at the left
        switch (iCharCount) {
            case 1 : {sPIN = "00" + sPIN; break;}
            case 2 : {sPIN = "0" + sPIN; break;}
            case 3 : {sPIN = sPIN; break;}
            default : {break;}
        }
        //Updates the last Teacher´s PIN value
        sPIN = "P" + sPIN;
        sLastProfPIN = sPIN;
        return sPIN;
    }
    //</editor-fold>
    
    //Loads the last Teacher PIN from the local .txt file
    private void loadLastProfPIN(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sProfPIN="";
        try
        {
            fDataBase = new File(sLocProfDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Reads all the lines on the Teachers .txt file until reaching the needed one
            chain = br.readLine();
            while( !chain.equals("LAST PIN") )
            {
                chain = br.readLine();
            }
            chain = br.readLine();
            this.sLastProfPIN = chain;
            br.close();
            fr.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"No se pudo la información de PIN de Profesor desde la base de datos local.\n"
                    + "El Sistema ha generado un error mientras leía los datos:\n"
                    + e, "TCM ERROR - loadProfDB()", JOptionPane.ERROR_MESSAGE );
        }
    }
    //</editor-fold>
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlTop = new javax.swing.JPanel();
        jlblTop = new javax.swing.JLabel();
        jpnlBottom = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnPic = new javax.swing.JButton();
        jbtnRefresh = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jbtnChkLst = new javax.swing.JButton();
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
        jtxtDomain = new javax.swing.JTextField();
        jlblArrob = new javax.swing.JLabel();
        checkbox1 = new java.awt.Checkbox();
        jchkboxYoung = new javax.swing.JCheckBox();
        jlblPhoneIcon = new javax.swing.JLabel();
        jtxtHomNum = new javax.swing.JTextField();
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
        jlblStud = new javax.swing.JLabel();
        jtxtStud = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtComm = new javax.swing.JTextField();
        jlblCat = new javax.swing.JLabel();
        jcboxCat = new java.awt.Choice();
        jlblPer = new javax.swing.JLabel();
        jtxtSal = new javax.swing.JTextField();
        jpnlMid4 = new javax.swing.JPanel();
        jlblRes = new javax.swing.JLabel();
        jtxtRes = new javax.swing.JTextField();
        jlblImp = new javax.swing.JLabel();
        jtxtImp = new javax.swing.JTextField();
        jlblPay = new javax.swing.JLabel();
        jbtnReport = new javax.swing.JButton();
        jtxtPay = new javax.swing.JTextField();
        jlblPic = new javax.swing.JLabel();
        jlblProfPIN = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jpnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblTop.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jlblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTop.setText("CONTROL DE PROFESORES");

        javax.swing.GroupLayout jpnlTopLayout = new javax.swing.GroupLayout(jpnlTop);
        jpnlTop.setLayout(jpnlTopLayout);
        jpnlTopLayout.setHorizontalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jlblTop, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addGap(203, 203, 203))
        );
        jpnlTopLayout.setVerticalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTop)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jbtnPic.setToolTipText("Subir fotografía de Profesor");
        jbtnPic.setEnabled(false);

        jbtnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh_2_medium.png"))); // NOI18N
        jbtnRefresh.setText("Refrescar");
        jbtnRefresh.setToolTipText("Descartar cambios y recargar");
        jbtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRefreshActionPerformed(evt);
            }
        });

        jbtnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save_medium.png"))); // NOI18N
        jbtnSave.setText("Guardar");
        jbtnSave.setToolTipText("Guardar cambios en Profesor");
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jbtnChkLst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/DataList_medium.png"))); // NOI18N
        jbtnChkLst.setText("Reservas");
        jbtnChkLst.setToolTipText("Ver lista de Alumnos asignados");
        jbtnChkLst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnChkLstActionPerformed(evt);
            }
        });

        jbtnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_medium.png"))); // NOI18N
        jbtnNew.setText("Nuevo");
        jbtnNew.setToolTipText("Limpia la pantalla para agregar un nuevo Profesor");
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
                .addComponent(jbtnChkLst, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnPic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlBottomLayout.setVerticalGroup(
            jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnChkLst))
                    .addGroup(jpnlBottomLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnExit))
                    .addComponent(jbtnNew, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpnlMiddle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jpnlMid1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        jlblNam.setText("Nombre");

        jlblLstNam.setText("Apellido(s)");

        jlblIde.setText("Núm. Identificación");

        jlblMail.setText("E-Mail");

        jlblNum.setText("Teléfono / Celular");

        jlblArrob.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlblArrob.setForeground(new java.awt.Color(0, 102, 255));
        jlblArrob.setText("@");

        checkbox1.setLabel("checkbox1");

        jchkboxYoung.setText(" Menor de edad");
        jchkboxYoung.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jchkboxYoungItemStateChanged(evt);
            }
        });

        jlblPhoneIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/phone_Lil.fw.png"))); // NOI18N

        jtxtHomNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtHomNumActionPerformed(evt);
            }
        });

        jlblCellIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Cell_Lil.fw.png"))); // NOI18N

        jtxtCelNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCelNumActionPerformed(evt);
            }
        });

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
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpnlMid1Layout.createSequentialGroup()
                                .addComponent(jlblPhoneIcon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtHomNum))
                            .addGroup(jpnlMid1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtxtIde, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                    .addComponent(jtxtMail))))
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
                    .addComponent(jlblNum)
                    .addComponent(jlblPhoneIcon)
                    .addComponent(jtxtHomNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jpnlMid3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ajustes Contables"));

        jlblStud.setText("Cantidad de Alumnos");

        jtxtStud.setEditable(false);

        jLabel4.setText("Comisión");

        jlblCat.setText("Nivel");

        jcboxCat.addItem("Escoja un valor");
        jcboxCat.addItem("Nivel 1");
        jcboxCat.addItem("Nivel 2");
        jcboxCat.addItem("Head Pro");

        jlblPer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlblPer.setText("%");

        jtxtSal.setEditable(false);

        javax.swing.GroupLayout jpnlMid3Layout = new javax.swing.GroupLayout(jpnlMid3);
        jpnlMid3.setLayout(jpnlMid3Layout);
        jpnlMid3Layout.setHorizontalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblStud, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtStud, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(jtxtComm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMid3Layout.createSequentialGroup()
                        .addComponent(jlblCat, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcboxCat, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                    .addGroup(jpnlMid3Layout.createSequentialGroup()
                        .addComponent(jlblPer, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jtxtSal)))
                .addContainerGap())
        );
        jpnlMid3Layout.setVerticalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlblStud)
                        .addComponent(jtxtStud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlblCat))
                    .addComponent(jcboxCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtComm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPer)
                    .addComponent(jtxtSal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnlMid4.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado Actual (Reservas activas)"));

        jlblRes.setText("Lecciones asignadas");

        jtxtRes.setEditable(false);

        jlblImp.setText("Lecciones impartidas");

        jtxtImp.setEditable(false);

        jlblPay.setText("Lecciones pagadas");

        jbtnReport.setText("Crear Reporte");
        jbtnReport.setToolTipText("Ver reporte de estado del Profesor");
        jbtnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReportActionPerformed(evt);
            }
        });

        jtxtPay.setEditable(false);

        javax.swing.GroupLayout jpnlMid4Layout = new javax.swing.GroupLayout(jpnlMid4);
        jpnlMid4.setLayout(jpnlMid4Layout);
        jpnlMid4Layout.setHorizontalGroup(
            jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlMid4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpnlMid4Layout.createSequentialGroup()
                        .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlblPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblImp, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(jlblRes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtRes)
                            .addComponent(jtxtImp)
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
                    .addComponent(jlblImp)
                    .addComponent(jtxtImp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblPay)
                    .addComponent(jtxtPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnReport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlblPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblPic.setText("FOTO");
        jlblPic.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblProfPIN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlblProfPIN.setForeground(new java.awt.Color(0, 102, 255));
        jlblProfPIN.setText("   ID: ");

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
                    .addGroup(jpnlMiddleLayout.createSequentialGroup()
                        .addComponent(jpnlMid4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblPic, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlMiddleLayout.createSequentialGroup()
                        .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jpnlMiddleLayout.createSequentialGroup()
                                .addComponent(jlblProfPIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlblStat, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboxStat, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jpnlMid3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                                .addComponent(jlblProfPIN))
                            .addComponent(jcboxStat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnlMid3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlMiddleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpnlMid4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnlMiddle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            gui_MainScreen tmpMS = new gui_MainScreen(1);
            tmpMS.setLocationRelativeTo(this);
            tmpMS.setVisible(true);
            tmpMS.iTab = 2;
            dispose();
        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        int opt = -1;
        if ( iPos == -1 ){ //This means that this is a new Teacher
            //Checks if this new Teacher is already saved or not
            int iIndex = findTeacher(this.jlblProfPIN.getText().substring(4));
            if ( iIndex >= 0 ){ //This means that the new Teacher was previously saved into the Data Base
                opt = JOptionPane.showConfirmDialog(this, "El Profesor ya existe es la Base de Datos. Desea socreescribirlo con los valores actuales?");
                if ( opt == 0 ){
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //Sets the Teacher´s new position into the ArraList
                    iPos = iIndex;
                    //Saves the Teachers data into the ArrayList positin according with the screen info
                    saveTeacher();
                    //Updates the local TXT Data Base with the new changes
                    updateTeacherDB_txt();
                    setCursor(Cursor.getDefaultCursor());
                    JOptionPane.showMessageDialog(this, "La información ha sido actualizada");
                }
            }
            else{//This means that this is a brand new Teache
                opt = JOptionPane.showConfirmDialog(this, "Confirma que desea crear el nuevo Profesor?");
                if ( opt == 0 ){
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //Creates and saves a new Teacher into the Teachers ArrayList
                    createNewTeacher();
                    //Updates the local TXT Data Base with this new changes in the ArrayList
                    updateTeacherDB_txt();
                    setCursor(Cursor.getDefaultCursor());
                    JOptionPane.showMessageDialog(this, "El nuevo Profesor ha sido agregado a la Base de Datos");
                }
                else{
                    JOptionPane.showMessageDialog(this, "No se ha efectuado ningún cambio");
                }
            }
        }
        else{//This means that this is an existing Teacher
            opt = JOptionPane.showConfirmDialog(this, "Confirma que desea guardar los valores actuales?");
            if ( opt == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                //Saves the Teacher data in the ArrayList given position, according with the info in the screen
                saveTeacher();
                //Updates the TXT Data Base according with the values in the ArrayList
                updateTeacherDB_txt();
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "La información ha sido guardada en la Base de Datos");
            }
            else{
                JOptionPane.showMessageDialog(this, "No se han guardado cambios");
            }
        }
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRefreshActionPerformed
        if ( iPos == -1 ){
            JOptionPane.showMessageDialog(this,"El Profesor no existe aun en la Base de Datos");
        }
        else{
            int opt = JOptionPane.showConfirmDialog(this, "ADVERTENCIA: Los cambios no guardados se perderán. Desea continuar?");
            if ( opt == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                //Loads the Teacher´s Data Base from the TXT file into the Array List
                loadProfDB();
                //Lodas the specific Teacher data into the screen
                loadTeacherData();
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "La información del Profesor ha sido recargada");
            }
        }
    }//GEN-LAST:event_jbtnRefreshActionPerformed

    private void jbtnChkLstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnChkLstActionPerformed
        int iIndex = findTeacher(this.jlblProfPIN.getText().substring(4));
        if ( iIndex == -1 ){
             JOptionPane.showMessageDialog(this, "El Profesor no fue encontrado en la Base de Datos.");
         }
         else{
            gui_Reserve_Manager tmpRM = new gui_Reserve_Manager(sLocStudDBPath, alStudDB, sLocProfDBPath, alProfDB, alProfDB.get(iIndex).getProfPIN(), 0);
            tmpRM.setLocationRelativeTo(this);
            tmpRM.setVisible(true);
        }
        
    }//GEN-LAST:event_jbtnChkLstActionPerformed

    private void jchkboxYoungItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jchkboxYoungItemStateChanged
        if ( jchkboxYoung.isSelected() == true ){
            jtxtIde.setText("");
            jtxtIde.setEnabled(false);
        }
        else{
            jtxtIde.setEnabled(true);
        }
    }//GEN-LAST:event_jchkboxYoungItemStateChanged

    private void jtxtCelNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCelNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCelNumActionPerformed

    private void jtxtHomNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtHomNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtHomNumActionPerformed

    private void jbtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNewActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Desea limpiar pantalla y agregar otro Profesor?\nADVERTENCIA: Los cambios no guardados se perderán");
        if ( opt == 0 ){
            iPos = -1;
            //Cleans the screen data
            cleanScreen();
            JOptionPane.showMessageDialog(this, "Puede proceder a agregar un nuevo Profesor");
        }
    }//GEN-LAST:event_jbtnNewActionPerformed

    private void jbtnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReportActionPerformed
        gui_Prof_Report tmpPR = new gui_Prof_Report(alInvoDB, sLocProfDBPath, alProfDB, sLocStudDBPath, alStudDB, this.jlblProfPIN.getText().substring(4) );
        tmpPR.setLocationRelativeTo(this);
        tmpPR.setVisible(true);
    }//GEN-LAST:event_jbtnReportActionPerformed

    
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
            java.util.logging.Logger.getLogger(gui_TeacherAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_TeacherAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_TeacherAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_TeacherAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_TeacherAdmin().setVisible(true);
            }
        });
    }

    //<editor-fold defaultstate="collapsed" desc="Variables declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Checkbox checkbox1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbtnChkLst;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnNew;
    private javax.swing.JButton jbtnPic;
    private javax.swing.JButton jbtnRefresh;
    private javax.swing.JButton jbtnReport;
    private javax.swing.JButton jbtnSave;
    private java.awt.Choice jcboxCat;
    private java.awt.Choice jcboxProv;
    private java.awt.Choice jcboxStat;
    private javax.swing.JCheckBox jchkboxYoung;
    private javax.swing.JLabel jlblAdd;
    private javax.swing.JLabel jlblAdd2;
    private javax.swing.JLabel jlblArrob;
    private javax.swing.JLabel jlblCat;
    private javax.swing.JLabel jlblCellIcon;
    private javax.swing.JLabel jlblIde;
    private javax.swing.JLabel jlblImp;
    private javax.swing.JLabel jlblLstNam;
    private javax.swing.JLabel jlblMail;
    private javax.swing.JLabel jlblNam;
    private javax.swing.JLabel jlblNum;
    private javax.swing.JLabel jlblPay;
    private javax.swing.JLabel jlblPer;
    private javax.swing.JLabel jlblPhoneIcon;
    private javax.swing.JLabel jlblPic;
    private javax.swing.JLabel jlblProfPIN;
    private javax.swing.JLabel jlblRes;
    private javax.swing.JLabel jlblStat;
    private javax.swing.JLabel jlblStud;
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
    private javax.swing.JTextField jtxtComm;
    private javax.swing.JTextField jtxtDomain;
    private javax.swing.JTextField jtxtHomNum;
    private javax.swing.JTextField jtxtIde;
    private javax.swing.JTextField jtxtImp;
    private javax.swing.JTextField jtxtLstNam;
    private javax.swing.JTextField jtxtMail;
    private javax.swing.JTextField jtxtNam;
    private javax.swing.JTextField jtxtPay;
    private javax.swing.JTextField jtxtRes;
    private javax.swing.JTextField jtxtSal;
    private javax.swing.JTextField jtxtStud;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
