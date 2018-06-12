package club_manager;

import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class gui_Reserves extends javax.swing.JFrame {
    
    //Data Bases Paths
    private String sLocStudDBPath;
    private String sLocProfDBPath;
    //private String sPIN;
    private int iPos;
    //Dynamic Reserve Code
    private String sDRC = "";
    
    //ArrayList that will store the complete data base of Students
    private ArrayList<cls_Student> alStudDB = new ArrayList<>();
    //ArrayList that will store the complete data base of Teachers
    private ArrayList<cls_Teacher> alProfDB = new ArrayList<>();

    //Variables for the last created PINs
    private String sLastStudPIN = "";
    private String sLastProfPIN = "";    
    
    public gui_Reserves(String sLocStudDBPath, ArrayList<cls_Student> alTmpStudentDB, 
            String sLocProfDBPath, ArrayList<cls_Teacher> alTmpTeacherDB, int iPos) {
        initComponents();
        setResizable(false);
        setTitle("TCM-SISTEMA DE RESERVAS");
        
        this.sLocStudDBPath = sLocStudDBPath;
        this.alStudDB = alTmpStudentDB;
        this.sLocProfDBPath = sLocProfDBPath;
        this.alProfDB = alTmpTeacherDB;
        
        this.iPos = iPos;
        
        loadLastStudPIN();
        loadLastProfPIN();
        
        
    }

    private gui_Reserves() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
                    + e, "TCM ERROR - loadLastProfPIN()", JOptionPane.ERROR_MESSAGE );
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
    
    //Shows the System's date in the main screen
    private String setDate(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sDate = "";
        Calendar cal = new GregorianCalendar();
        //Creating time variables for the date. Obtaining values form the System's clock
        String sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String sMon = String.valueOf(cal.get(Calendar.MONTH));
        String sYea = String.valueOf(cal.get(Calendar.YEAR));
        //Changes the day to 2 digits format
        if ( sDay.length() == 1 ){
            sDay = "0" + sDay;
        }
        switch (sMon)
        {
            case "0" : {sMon = "01";break;}
            case "1" : {sMon = "02";break;}
            case "2" : {sMon = "03";break;}
            case "3" : {sMon = "04";break;}
            case "4" : {sMon = "05";break;}
            case "5" : {sMon = "06";break;}
            case "6" : {sMon = "07";break;}
            case "7" : {sMon = "08";break;}
            case "8" : {sMon = "09";break;}
            case "9" : {sMon = "10";break;}
            case "10" : {sMon = "11";break;}
            case "11" : {sMon = "12";break;}
            default : {sMon = "XX";break;}
        }
        //Sets the year format to 2 digits
        sYea = sYea.substring(2);
        //Prepares the text to be displayed in the date label on the main screen
        sDate = sDay + sMon + sYea;
        return sDate;
    }
    //</editor-fold>
    
    //Shows the System's date in the main screen
    private String setStringDate(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sDate = "";
        Calendar cal = new GregorianCalendar();
        //Creating time variables for the date. Obtaining values form the System's clock
        String sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String sMon = String.valueOf(cal.get(Calendar.MONTH));
        String sYea = String.valueOf(cal.get(Calendar.YEAR));
        //Changes the month's number to the month's name
        switch (sMon)
        {
            case "0" : {sMon = "Jan";break;}
            case "1" : {sMon = "Feb";break;}
            case "2" : {sMon = "Mar";break;}
            case "3" : {sMon = "Apr";break;}
            case "4" : {sMon = "May";break;}
            case "5" : {sMon = "Jun";break;}
            case "6" : {sMon = "Jul";break;}
            case "7" : {sMon = "Ago";break;}
            case "8" : {sMon = "Sep";break;}
            case "9" : {sMon = "Oct";break;}
            case "10" : {sMon = "Nov";break;}
            case "11" : {sMon = "Dec";break;}
            default : {sMon = "NA";break;}
        }
         //Prepares the text to be displayed in the date label on the main screen
        sDate = sDay + "-" + sMon + "-" + sYea;
        return sDate;
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
    
    //Gets the complete list of Teachers depending on the selected Category Level
    //Fullfills de corresponding Teachers dropdown list on the screen
    private void getTeachersList(String sCategory){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Cleans the choice menu
        jcboxAsiTea.removeAll();
        jcboxAsiTea.addItem("Escoja un valor");
        if ( !sCategory.equals("Escoja un valor") ){
            //Starts adding components
            for ( cls_Teacher tmp : alProfDB ){
                if ( tmp.getCategry().equals(sCategory) ){
                    jcboxAsiTea.addItem(tmp.getFstName() + " " + tmp.getLstName());
                }
            }
        }
        //Checks if any name was added to the list of Teachers or not
        if ( jcboxAsiTea.getItemCount() == 1 ){
            jcboxAsiTea.addItem("No hay Profesores en Cat.");
        }
    }
    //</editor-fold> 
    
    //Finds a Techer PIN depending on his/her name
    private String findTeacherPIN(String sName){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sPIN = "XXXX";
        for ( cls_Teacher tmp : alProfDB ){
            if ( sName.equals(tmp.getFstName() + " " + tmp.getLstName()) ){
                sPIN = tmp.getProfPIN();
            }
        }
        return sPIN;
    }
    //</editor-fold>
    
    //Updates the Dynamic Reservation Code (DRC) with the values on the screen
    private String updateDRC(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Prepares the variables that build the DRC
        String sStu = "";
        String sDat = "";
        String sMod = "";
        String sDay = "";
        String sPaq = "";
        String sHrs = "";
        String sCat = "";
        String sTea = "";
        String sRes = "";
        String sRec = "";
        String sPay = "";
        String sDRC = "";
        //Starts preparing each variable
        sStu = this.alStudDB.get(iPos).getStudPIN();
        sDat = setDate();
        switch ( this.jcboxMode.getSelectedItem() ){
            case "Standard":{sMod = "STD"; break;}
            case "Clín. de Perfecc.":{sMod = "CLP"; break;}
            case "Clínica de Niños":{sMod = "CLN"; break;}
            case "Mini Tennis":{sMod = "MNT"; break;}
        }
        switch ( this.jcboxDay.getSelectedItem() ){
            case "L-V":{sDay = "LV"; break;}
            case "S-D":{sDay = "FS"; break;}
        }
        switch ( this.jcboxPaq.getSelectedItem() ){
            case "Individual":{sPaq = "IND"; break;}
            case "Grupal 2P":{sPaq = "G2P"; break;}
            case "Grupal 3P":{sPaq = "G3P"; break;}
            case "Grupal 4P":{sPaq = "G4P"; break;}
            case "Grupal 5+":{sPaq = "G5+"; break;}
        }
        switch ( this.jcboxHour.getSelectedItem() ){
            case "Mañana":{sHrs = "MN"; break;}
            case "Día":{sHrs = "DI"; break;}
            case "Tarde":{sHrs = "TD"; break;}
            case "Noche":{sHrs = "NC"; break;}
        }
        sRes = formatDigits(this.jtxtLesQty.getText());
        sRec = "000"; //At 1st reserve, this values must be 0
        sPay = "000"; //At 1st reserve, this values must be 0
        switch ( this.jcboxTeaCat.getSelectedItem() ){
            case "Escoja un valor":{sCat = "XX"; break;}
            case "Nivel 1":{sCat = "N1"; break;}
            case "Nivel 2":{sCat = "N2"; break;}
            case "Head Pro":{sCat = "HP"; break;}
        }
        if ( sCat.equals("XX") ){
            sTea = "PXXX";
        }
        else{
                sTea = findTeacherPIN(jcboxAsiTea.getSelectedItem());
        }
        sDRC = sStu + "." + sDat + "." + sMod + sDay + sPaq + sHrs + "." + sRes + "." + sRec + "." + sPay + "." + sCat + sTea;
        return sDRC;
    }
    //</editor-fold>
    
    //Updates the reserve resume chart
    private void updateResChart(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String aStudData = "Nombre del estudiante: " + alStudDB.get(iPos).getFstName() + " " + alStudDB.get(iPos).getLstName() + "\n" 
                        //+ "Nivel: " + alStudDB.get(iPos).getStuLevl() + "\n"
                        + "Modalidad y paquete: " + this.jcboxMode.getSelectedItem() + " / " + this.jcboxPaq.getSelectedItem() + "\n"
                        + "Día y horario: " + this.jcboxDay.getSelectedItem() + " / " + this.jcboxHour.getSelectedItem() + "\n"
                        + "Lecciones reservadas: " + this.jtxtLesQty.getText() + "\n"
                        + "Profesor asignado: " + this.jcboxAsiTea.getSelectedItem();
        this.jtxtAreStuDat.setText(aStudData);
    }
    //</editor-fold>
    
    //Updates the QTY of reserved lessons for the Student
    private void updateStudResLessons(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Prepares a variable to cont the new lesson QTY 
        int iNewLes = 0;
        if ( !jtxtLesQty.getText().equals("") ){
            iNewLes = Integer.valueOf(jtxtLesQty.getText());
        }
        //Saves the current amount of reserved lessons
        String sResLes = alStudDB.get(iPos).getResClas();
        //Adds the new lessons
        sResLes = String.valueOf(Integer.valueOf(sResLes) + iNewLes);
        //Updates the Student ArrayList with the new QTY of reserved lessons
        alStudDB.get(iPos).setResClas(sResLes);
    }
    //</editor-fold>
    
    //Updates the QTY of reserved lessons under a given Teacher
    private void updateProfResLessons(String sProfName){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        if ( !sProfName.equals("NA") && !sProfName.equals("") ){
            //Prepares a variable to cont the new lesson QTY 
            int iNewLes = 0;
            if ( !jtxtLesQty.getText().equals("") ){
                iNewLes = Integer.valueOf(jtxtLesQty.getText());
                }
                //Saves the current amount of reserved lessons
                String sResLes = "";
                for ( int i=0; i<this.alProfDB.size(); i++ ){
                    if ( sProfName.equals(alProfDB.get(i).getFstName() + " " + alProfDB.get(i).getLstName()) ){
                        //Saves the current amount of reserved lessons
                        sResLes = alProfDB.get(i).getResClas();
                        //Adds the lessons on the new Invoice
                        sResLes = String.valueOf(Integer.valueOf(sResLes) + iNewLes);
                        //Updates the Teachers ArrayList with the new QTY of reserved lessons
                        alProfDB.get(i).setResClas(sResLes);
                    }
                }
            }
    }
    //</editor-fold>
    
    
    //Updates the Teacher's sutdent list with the new Student
    private void updateStudentList(String sTeacher, String sStudent){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int iStudQty = 0;
        //Identifies the Teacher from the data Base
        for ( int i=0; i<alProfDB.size(); i++ ){
            //If the Teacher's name is identified
            if ( (alProfDB.get(i).getFstName() + " " + alProfDB.get(i).getLstName()).equals(sTeacher) ){
                //Checks if the Student already exists in the Teacher's List
                if ( alProfDB.get(i).getStuList().contains(sStudent) == false ){
                    //Removes any possible NA from the begining of the list
                    if ( alProfDB.get(i).getStuList().equals("NA") ){
                        alProfDB.get(i).setStuList("");
                    }
                    //Adds the Student name to the current Teacher's list
                    alProfDB.get(i).setStuList(alProfDB.get(i).getStuList() + sStudent + ">");
                    //Adds one Student to the Teacher's count 
                    iStudQty = Integer.valueOf(alProfDB.get(i).getStudQty()) + 1;
                    alProfDB.get(i).setStudQty(String.valueOf(iStudQty));
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
        jpnlBottom = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbntGenRes = new javax.swing.JButton();
        jbtnClean = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jbtnEmail = new javax.swing.JButton();
        jpnlMid1 = new javax.swing.JPanel();
        jlblMode = new javax.swing.JLabel();
        jcboxMode = new java.awt.Choice();
        jlblDay = new javax.swing.JLabel();
        jcboxDay = new java.awt.Choice();
        jlblPaq = new javax.swing.JLabel();
        jcboxPaq = new java.awt.Choice();
        jlblHour = new javax.swing.JLabel();
        jcboxHour = new java.awt.Choice();
        jpnlMid2 = new javax.swing.JPanel();
        jlblTeaCat = new javax.swing.JLabel();
        jcboxTeaCat = new java.awt.Choice();
        jlblAsiTea = new javax.swing.JLabel();
        jcboxAsiTea = new java.awt.Choice();
        jlblLesQty = new javax.swing.JLabel();
        jtxtLesQty = new javax.swing.JTextField();
        jlblResCod = new javax.swing.JLabel();
        jpnlMid3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtAreStuDat = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jpnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblTop.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jlblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTop.setText("NUEVA RESERVA");

        javax.swing.GroupLayout jpnlTopLayout = new javax.swing.GroupLayout(jpnlTop);
        jpnlTop.setLayout(jpnlTopLayout);
        jpnlTopLayout.setHorizontalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        jbtnExit.setToolTipText("Regresar al menú principal");
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        jbntGenRes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Reservation_lil.png"))); // NOI18N
        jbntGenRes.setText("Generar Reserva");
        jbntGenRes.setToolTipText("Introducir una nueva reserva de lecciones para el alumno");
        jbntGenRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbntGenResActionPerformed(evt);
            }
        });

        jbtnClean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clear_2_medium.png"))); // NOI18N
        jbtnClean.setText("Limpiar Valores");
        jbtnClean.setToolTipText("Limpiar los valores de la ventana");
        jbtnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCleanActionPerformed(evt);
            }
        });

        jbtnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save_medium.png"))); // NOI18N
        jbtnSave.setText("Guardar");
        jbtnSave.setToolTipText("Guardar Reserva en la Base de Datos");
        jbtnSave.setEnabled(false);
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jbtnEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mail_small.png"))); // NOI18N
        jbtnEmail.setText("E-Mail");
        jbtnEmail.setToolTipText("Enviar datos de la reserva por E-mail");
        jbtnEmail.setEnabled(false);
        jbtnEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBottomLayout = new javax.swing.GroupLayout(jpnlBottom);
        jpnlBottom.setLayout(jpnlBottomLayout);
        jpnlBottomLayout.setHorizontalGroup(
            jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbntGenRes, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlBottomLayout.setVerticalGroup(
            jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbntGenRes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnClean)
                        .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jpnlMid1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Lección"));

        jlblMode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblMode.setText("Modalidad  ");

        jcboxMode.addItem("Standard");
        jcboxMode.addItem("Clín. de Perfecc.");
        jcboxMode.addItem("Clínica de Niños");
        jcboxMode.addItem("Mini Tennis");

        jlblDay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblDay.setText("Día  ");

        jcboxDay.addItem("L-V");
        jcboxDay.addItem("S-D");

        jlblPaq.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblPaq.setText("Paquete ");

        jcboxPaq.addItem("Individual");
        jcboxPaq.addItem("Grupal 2P");
        jcboxPaq.addItem("Grupal 3P");
        jcboxPaq.addItem("Grupal 4P");
        jcboxPaq.addItem("Grupal 5+");
        jcboxPaq.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboxPaqItemStateChanged(evt);
            }
        });

        jlblHour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblHour.setText("Horario");

        jcboxHour.addItem("Mañana");
        jcboxHour.addItem("Día");
        jcboxHour.addItem("Tarde");
        jcboxHour.addItem("Noche");
        jcboxHour.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboxHourItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jpnlMid1Layout = new javax.swing.GroupLayout(jpnlMid1);
        jpnlMid1.setLayout(jpnlMid1Layout);
        jpnlMid1Layout.setHorizontalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlblDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblMode, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcboxMode, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxDay, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlblHour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblPaq, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcboxPaq, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxHour, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnlMid1Layout.setVerticalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcboxMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblMode, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcboxDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblDay, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblPaq, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboxPaq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblHour, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboxHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jpnlMid2.setBorder(javax.swing.BorderFactory.createTitledBorder("Profesor "));

        jlblTeaCat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblTeaCat.setText("Nivel  ");

        jcboxTeaCat.addItem("Escoja un valor");
        jcboxTeaCat.addItem("Nivel 1");
        jcboxTeaCat.addItem("Nivel 2");
        jcboxTeaCat.addItem("Head Pro");
        jcboxTeaCat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboxTeaCatItemStateChanged(evt);
            }
        });

        jlblAsiTea.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblAsiTea.setText("Profesor  ");

        //jcboxAsiTea.addItem("Escoja un valor");

        jlblLesQty.setText("Cant. Lecc.");

        javax.swing.GroupLayout jpnlMid2Layout = new javax.swing.GroupLayout(jpnlMid2);
        jpnlMid2.setLayout(jpnlMid2Layout);
        jpnlMid2Layout.setHorizontalGroup(
            jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblLesQty, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(jlblTeaCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblAsiTea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcboxTeaCat, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jcboxAsiTea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtLesQty))
                .addContainerGap())
        );
        jpnlMid2Layout.setVerticalGroup(
            jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcboxTeaCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblTeaCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcboxAsiTea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblAsiTea, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblLesQty)
                    .addComponent(jtxtLesQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlblResCod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlblResCod.setForeground(new java.awt.Color(0, 102, 255));
        jlblResCod.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblResCod.setText("CÓDIGO DE RESERVA DINÁMICO: AXXX.000000.STDLVINDMA.000.000.000.NXPXXX");

        jpnlMid3.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumen de la Reserva"));

        jtxtAreStuDat.setEditable(false);
        jtxtAreStuDat.setColumns(20);
        jtxtAreStuDat.setRows(5);
        jScrollPane1.setViewportView(jtxtAreStuDat);

        javax.swing.GroupLayout jpnlMid3Layout = new javax.swing.GroupLayout(jpnlMid3);
        jpnlMid3.setLayout(jpnlMid3Layout);
        jpnlMid3Layout.setHorizontalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jpnlMid3Layout.setVerticalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
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
                    .addComponent(jpnlBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnlMid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblResCod, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jpnlMid2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jpnlMid3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpnlMid2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpnlMid3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblResCod))
                    .addComponent(jpnlMid1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcboxPaqItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboxPaqItemStateChanged
        
    }//GEN-LAST:event_jcboxPaqItemStateChanged

    private void jcboxHourItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboxHourItemStateChanged
        
    }//GEN-LAST:event_jcboxHourItemStateChanged

    private void jcboxTeaCatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboxTeaCatItemStateChanged
        String sCategory = jcboxTeaCat.getSelectedItem();
        getTeachersList(sCategory);
    }//GEN-LAST:event_jcboxTeaCatItemStateChanged

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

    private void jbntGenResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntGenResActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Confirme que desea generar una nueva reserva de Lecciones");
        if ( opt == 0 ){
            if ( jtxtLesQty.getText().equals("") ){
                JOptionPane.showMessageDialog(this, "No ha definido una cantidad de lecciones", "TCM MSG", JOptionPane.ERROR_MESSAGE);
            }
            else{
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                sDRC = updateDRC();
                updateResChart();
                this.jlblResCod.setText("CÓDIGO DE RESERVA DINÁMICO: " + sDRC);
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "La reserva ha sido creada de forma correcta\n"
                        + "DRC asignado al Alumno:\n" + sDRC);
                this.jbtnSave.setEnabled(true);
                JOptionPane.showMessageDialog(this,"Recuerde que la reserva no será agregada al alumno sino hasta guardar los cambios");
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "No se han efectuado cambios");
        }
    }//GEN-LAST:event_jbntGenResActionPerformed

    private void jbtnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCleanActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Confirme que desea limpiar los valores en pantalla");
        if ( opt == 0 ){
            this.jcboxMode.select(0);
            this.jcboxDay.select(0);
            this.jcboxPaq.select(0);
            this.jcboxHour.select(0);
            this.jcboxTeaCat.select(0);
            this.jcboxAsiTea.select(0);
            this.jtxtLesQty.setText("");
            String aStudData = "Nombre del estudiante: " + alStudDB.get(iPos).getFstName() + " " + alStudDB.get(iPos).getLstName() + "\n" 
                        + "Nivel: " + alStudDB.get(iPos).getStuLevl() + "\n"
                        + "Modalidad y paquete: \n"
                        + "Día y horario: \n"
                        + "Lecciones reservadas: \n"
                        + "Profesor asignado: ";
            this.jtxtAreStuDat.setText(aStudData);
            this.jlblResCod.setText("CÓDIGO DE RESERVA DINÁMICO: AXXX.000000.STDLVINDMA.000.000.000.NXPXXX");
            this.jbtnSave.setEnabled(false);
            this.jbtnEmail.setEnabled(false);
        }
    }//GEN-LAST:event_jbtnCleanActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Confirme que desea guardar la reserva en la Base Datos");
        if ( opt == 0 ){
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //Updates the reserve list for the Student
            if ( alStudDB.get(iPos).getReservs().equals("NA") || alStudDB.get(iPos).getReservs().equals("") ){
                //Creates the very first reserve 
                alStudDB.get(iPos).setReservs(sDRC + ">");
            }
            else{
                //Adds the new reserve (DRC) to the list of reserves under the Student
                alStudDB.get(iPos).setReservs(alStudDB.get(iPos).getReservs() + sDRC + ">");
            }
            //Updates total the amount of reserved lessons
            updateStudResLessons();
            //Updates the Students TXT data base
            updateStudentDB_txt();
            //Updates the list of assigned Students under a Teacher
            updateStudentList(jcboxAsiTea.getSelectedItem(),(alStudDB.get(iPos).getFstName() + " " + alStudDB.get(iPos).getLstName()));
            //Updates total the mount of reserved lessons for the Teacher
            updateProfResLessons(jcboxAsiTea.getSelectedItem());
            //Updates the Teachers TXT data base
            updateTeacherDB_txt();
            //Enables the Email button in case the User wants to send the notification to the Student
            jbtnEmail.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(this, "La nueva reserva ha sido agregada a la Base de Datos del Alumno");
        }
        else{
            JOptionPane.showMessageDialog(this, "No se han efectuado cambios en la Base de Datos");
        }

    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEmailActionPerformed
        //Prepares the mail variables
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String sMailSub = "Top Tennis Center: Confirmación de reserva de lecciones";
        String sMailTo = alStudDB.get(iPos).getEmail();
        String sMailBody = "Hola " + this.alStudDB.get(iPos).getFstName() + ". Esperamos que se encuentre bien.\n\n"
                + "Los siguientes datos corresponden a la reservación de sus lecciones de Tennis:\n\n"
                + "---------------------------------------------------------\n"
                + "Fecha de ingreso al Sistema: " + setStringDate() + "\n"
                + "---------------------------------------------------------\n"
                + this.jtxtAreStuDat.getText() + "\n"
                + "-----------------------------------------------------------------------\n\n"
                + "Esperamos contar con su puntual presencia.\n\n"
                + "Cordialmente;\n\n"
                + "ADMINISTRACIÓN\n"
                + "TOP TENNIS CENTER\n\n";
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
    }//GEN-LAST:event_jbtnEmailActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(gui_Reserves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_Reserves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_Reserves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_Reserves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_Reserves().setVisible(true);
            }
        });
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Variables Declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbntGenRes;
    private javax.swing.JButton jbtnClean;
    private javax.swing.JButton jbtnEmail;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnSave;
    private java.awt.Choice jcboxAsiTea;
    private java.awt.Choice jcboxDay;
    private java.awt.Choice jcboxHour;
    private java.awt.Choice jcboxMode;
    private java.awt.Choice jcboxPaq;
    private java.awt.Choice jcboxTeaCat;
    private javax.swing.JLabel jlblAsiTea;
    private javax.swing.JLabel jlblDay;
    private javax.swing.JLabel jlblHour;
    private javax.swing.JLabel jlblLesQty;
    private javax.swing.JLabel jlblMode;
    private javax.swing.JLabel jlblPaq;
    private javax.swing.JLabel jlblResCod;
    private javax.swing.JLabel jlblTeaCat;
    private javax.swing.JLabel jlblTop;
    private javax.swing.JPanel jpnlBottom;
    private javax.swing.JPanel jpnlMid1;
    private javax.swing.JPanel jpnlMid2;
    private javax.swing.JPanel jpnlMid3;
    private javax.swing.JPanel jpnlTop;
    public javax.swing.JTextArea jtxtAreStuDat;
    private javax.swing.JTextField jtxtLesQty;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
