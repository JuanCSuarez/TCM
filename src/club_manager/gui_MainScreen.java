package club_manager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;


public class gui_MainScreen extends javax.swing.JFrame {
    
    javax.swing.table.DefaultTableModel tblModelProf = new javax.swing.table.DefaultTableModel();
    Object[] ProfColumn = new Object [13];
    javax.swing.table.DefaultTableModel tblModelStud = new javax.swing.table.DefaultTableModel();
    Object[] StudColumn = new Object [11];
    javax.swing.table.DefaultTableModel tblModelInvc = new javax.swing.table.DefaultTableModel();
    Object[] InvcColumn = new Object [5];
    
    //ArrayList that will store the complete data base of Teachers
    private ArrayList<cls_Teacher> alProfDB = new ArrayList<>();
    private ArrayList<cls_Teacher> alProfSrchDB = new ArrayList<>();
    //ArrayList that will store the complete data base of Students
    private ArrayList<cls_Student> alStudDB = new ArrayList<>();
    private ArrayList<cls_Student> alStudSrchDB = new ArrayList<>();
    //ArrayList of Invoice history
    private ArrayList<cls_Invoice> alInvoDB = new ArrayList<>();
    private ArrayList<cls_Invoice> alInvoSrchDB = new ArrayList<>();
    //ArrayList for the list of costs
    private ArrayList<cls_Cost_Item> alCostDB = new ArrayList<>();
    
    String[][] arrMemb = new String[12][2];
    
    /*
    //Data Bases Paths
    private String sLocProfDBPath = "n:\\Oracle Projects\\DB Tennis Manager\\Teachers_DB.txt"; //DEVELOPMENT PHASE PATH
    private String sLocStudDBPath = "n:\\Oracle Projects\\DB Tennis Manager\\Students_DB.txt"; //DEVELOPMENT PHASE PATH
    private String sLocInvoDBPath = "n:\\Oracle Projects\\DB Tennis Manager\\Invoices_DB.txt"; //DEVELOPMENT PHASE PATH
    private String sLocAccnDBPath = "n:\\Oracle Projects\\DB Tennis Manager\\Accountn_DB.txt"; //DEVELOPMENT PHASE PATH
    private String sLocCostDBPath = "n:\\Oracle Projects\\DB Tennis Manager\\CostMtrx_DB.txt"; //DEVELOPMENT PHASE PATH
    private String sLocMembDBPath = "n:\\Oracle Projects\\DB Tennis Manager\\MembMtrx_DB.txt"; //DEVELOPMENT PHASE PATH
    */
    
    //Data Bases Paths
    private String sLocProfDBPath = "C:\\Program Files (x86)\\CySSol Apps\\Tennis Club Manager\\Data Files\\Teachers_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocStudDBPath = "C:\\Program Files (x86)\\CySSol Apps\\Tennis Club Manager\\Data Files\\Students_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocInvoDBPath = "C:\\Program Files (x86)\\CySSol Apps\\Tennis Club Manager\\Data Files\\Invoices_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocAccnDBPath = "C:\\Program Files (x86)\\CySSol Apps\\Tennis Club Manager\\Data Files\\Accountn_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocCostDBPath = "C:\\Program Files (x86)\\CySSol Apps\\Tennis Club Manager\\Data Files\\CostMtrx_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocMembDBPath = "C:\\Program Files (x86)\\CySSol Apps\\Tennis Club Manager\\Data Files\\MembMtrx_DB.txt"; //PRODUCTION PHASE PATH
    
    /*
    //Data Bases Paths Windows 7
    private String sLocProfDBPath = "C:\\Program Files\\CySSol Apps\\Tennis Club Manager\\Data Files\\Teachers_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocStudDBPath = "C:\\Program Files\\CySSol Apps\\Tennis Club Manager\\Data Files\\Students_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocInvoDBPath = "C:\\Program Files\\CySSol Apps\\Tennis Club Manager\\Data Files\\Invoices_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocAccnDBPath = "C:\\Program Files\\CySSol Apps\\Tennis Club Manager\\Data Files\\Accountn_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocCostDBPath = "C:\\Program Files\\CySSol Apps\\Tennis Club Manager\\Data Files\\CostMtrx_DB.txt"; //PRODUCTION PHASE PATH
    private String sLocMembDBPath = "C:\\Program Files\\CySSol Apps\\Tennis Club Manager\\Data Files\\MembMtrx_DB.txt"; //PRODUCTION PHASE PATH
    */
    
    //Screen counters
    private int iProfQTY = 0;
    private int iStudQTY = 0;
    private int iInvoQTY = 0;
    //Active tab
    public int iTab;
    
    //When this flags goes False, it indicates that, what is shown in the screen, are just searching results
    boolean bStudDBFlag = true;
    boolean bProfDBFlag = true;
    
    //Variables for the last two created PINs
    private String sLastProfPIN = "";
    private String sLastStudPIN = "";
    private String sLastInvoNum = "";
    
    String sSelected = "";
    
    //DRC decoded data variables
    private String sDRCSPn = "";//Student´s PIN
    private String sDRCDat = "";
    private String sDRCMod = "";
    private String sDRCDay = "";
    private String sDRCPaq = "";//Reserved package
    private String sDRCHrs = "";//Reserved hours
    private String sDRCRes = "";//Reserved Lessons
    private String sDRCRec = "";//Received Lessons
    private String sDRCPay = "";//Payed Lessons
    private String sDRCTLv = "";//Teacher Level
    private String sDRCTPn = "";//Teacher´s PIN
    
    
    public gui_MainScreen(int iTab) {
        initComponents();
        System.out.println("Starting Tool");
        setLocationRelativeTo(null);
        setTitle("TENNIS CLUB MANAGER");
        jrbtnEfec.setSelected(true);
        
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        pack();
//        setSize(screenSize.width /2,screenSize.height /2);
//        this.setExtendedState(this.MAXIMIZED_BOTH);
        
        
        if ( iTab > -1 ){this.jTbPnMid.setSelectedIndex(iTab);}

        

        //Sets the curent Date in the Invoice System Screen
        jtxtDat.setText(setDate());
        
        //Loads Teachers and Students Tables
        configProfTable();
        loadProfDB();
        loadProfTable();
        configStudTable();
        loadStudDB();
        loadStudTable();
        
        //Loads the Invoice data Base
        loadInvoDB();
        //Loads the Costs data base
        loadCostDB();
        //Loads the Membership Data Base
        loadMembDB();
        //Puts the last Invoice Number is the Invoice field
        this.jlblInvNum.setText("FACTURA: " + setNextInvoiceNum());
        this.jlblLstInv.setText("Última factura: " + sLastInvoNum);
        
        
    }

    gui_MainScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    //TABLES HANDLING METHODS ******************
    
    //Prepares the JTable columns in order to receive the list of Trainers
    private void configProfTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelProf.addColumn("Código");
        tblModelProf.addColumn("Nombre");
        tblModelProf.addColumn("Apellido");
        tblModelProf.addColumn("Teléfono");
        tblModelProf.addColumn("Celular");
        tblModelProf.addColumn("E-mail");
        tblModelProf.addColumn("Categoría");
        tblModelProf.addColumn("Alumnos");
        tblModelProf.addColumn("Clases Reservadas");
        tblModelProf.addColumn("Clases Impartidas");
        tblModelProf.addColumn("Clases Pagadas");
        tblModelProf.addColumn("Estado actual");
        tblModelProf.addColumn("Identificación");
        jtblProf.setModel(tblModelProf);
        //Allows the user to sort the items by Column
        jtblProf.setAutoCreateRowSorter(true);        
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblProf.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.orange);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblProf.setAutoResizeMode(jtblProf.AUTO_RESIZE_OFF);
        jtblProf.setRowHeight(24);
        jtblProf.getColumnModel().getColumn(0).setPreferredWidth(130);
        jtblProf.getColumnModel().getColumn(0).setResizable(false);
        jtblProf.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(1).setPreferredWidth(150);
        jtblProf.getColumnModel().getColumn(1).setResizable(false);
        jtblProf.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(2).setPreferredWidth(150);
        jtblProf.getColumnModel().getColumn(2).setResizable(false);
        jtblProf.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(3).setPreferredWidth(120);
        jtblProf.getColumnModel().getColumn(3).setResizable(false);
        jtblProf.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(4).setPreferredWidth(120);
        jtblProf.getColumnModel().getColumn(4).setResizable(false);
        jtblProf.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(5).setPreferredWidth(200);
        jtblProf.getColumnModel().getColumn(5).setResizable(false);
        jtblProf.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);        
        jtblProf.getColumnModel().getColumn(6).setPreferredWidth(90);
        jtblProf.getColumnModel().getColumn(6).setResizable(false);
        jtblProf.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(7).setPreferredWidth(90);
        jtblProf.getColumnModel().getColumn(7).setResizable(false);
        jtblProf.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(8).setPreferredWidth(135);
        jtblProf.getColumnModel().getColumn(8).setResizable(false);
        jtblProf.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(9).setPreferredWidth(135);
        jtblProf.getColumnModel().getColumn(9).setResizable(false);
        jtblProf.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(10).setPreferredWidth(135);
        jtblProf.getColumnModel().getColumn(10).setResizable(false);
        jtblProf.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(11).setPreferredWidth(120);
        jtblProf.getColumnModel().getColumn(11).setResizable(false);
        jtblProf.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
        jtblProf.getColumnModel().getColumn(12).setPreferredWidth(140);
        jtblProf.getColumnModel().getColumn(12).setResizable(false);
        jtblProf.getColumnModel().getColumn(12).setCellRenderer(centerRenderer);        
    }
    //</editor-fold>
    
    //Prepares the JTable columns in order to receive the list of Students
    private void configStudTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelStud.addColumn("Nombre");
        tblModelStud.addColumn("Apellido");
        tblModelStud.addColumn("Teléfono");
        tblModelStud.addColumn("Celular");
        tblModelStud.addColumn("E-mail");
        tblModelStud.addColumn("Nivel");
        tblModelStud.addColumn("Clases Reservadas");
        tblModelStud.addColumn("Clases Recibidas");
        tblModelStud.addColumn("Clases Pagadas");
        tblModelStud.addColumn("Identificación");
        tblModelStud.addColumn("PIN");
        jtblStud.setModel(tblModelStud);
        //Allows the user to sort the items by Column
        jtblStud.setAutoCreateRowSorter(true);        
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblStud.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.CYAN);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblStud.setAutoResizeMode(jtblStud.AUTO_RESIZE_OFF);
        jtblStud.setRowHeight(24);
        jtblStud.getColumnModel().getColumn(0).setPreferredWidth(150);
        jtblStud.getColumnModel().getColumn(0).setResizable(false);
        jtblStud.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(1).setPreferredWidth(150);
        jtblStud.getColumnModel().getColumn(1).setResizable(false);
        jtblStud.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(2).setPreferredWidth(120);
        jtblStud.getColumnModel().getColumn(2).setResizable(false);
        jtblStud.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(3).setPreferredWidth(120);
        jtblStud.getColumnModel().getColumn(3).setResizable(false);
        jtblStud.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(4).setPreferredWidth(200);
        jtblStud.getColumnModel().getColumn(4).setResizable(false);
        jtblStud.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);        
        jtblStud.getColumnModel().getColumn(5).setPreferredWidth(120);
        jtblStud.getColumnModel().getColumn(5).setResizable(false);
        jtblStud.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(6).setPreferredWidth(140);
        jtblStud.getColumnModel().getColumn(6).setResizable(false);
        jtblStud.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(7).setPreferredWidth(140);
        jtblStud.getColumnModel().getColumn(7).setResizable(false);
        jtblStud.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(8).setPreferredWidth(140);
        jtblStud.getColumnModel().getColumn(8).setResizable(false);
        jtblStud.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(9).setPreferredWidth(150);
        jtblStud.getColumnModel().getColumn(9).setResizable(false);
        jtblStud.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        jtblStud.getColumnModel().getColumn(10).setPreferredWidth(130);
        jtblStud.getColumnModel().getColumn(10).setResizable(false);
        jtblStud.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Loads the information from the Teacher´s Data Base ArrayList into the corresponding JTable
    private void loadProfTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading data from Teachers ArralyList into JTable");
        for ( cls_Teacher tmp: this.alProfDB ){
            ProfColumn[0] = tmp.getProfPIN();
            ProfColumn[1] = tmp.getFstName();
            ProfColumn[2] = tmp.getLstName();
            ProfColumn[3] = tmp.getHomNumb();
            ProfColumn[4] = tmp.getCelNumb();
            ProfColumn[5] = tmp.getEmail();
            ProfColumn[6] = tmp.getCategry();
            ProfColumn[7] = tmp.getStudQty();
            ProfColumn[8] = tmp.getResClas();
            ProfColumn[9] = tmp.getImpClas();
            ProfColumn[10] = tmp.getPayClas();
            ProfColumn[11] = tmp.getStatus();
            ProfColumn[12] = tmp.getIdNumb();
            tblModelProf.addRow(ProfColumn);
            jtblProf.setModel(tblModelProf);
        }
        jlblProfDBsize.setText("<html><font color='blue'>Profesores inscritos: </font>" + iProfQTY + "</html>");
        System.out.println("Teachers ArrayList loaded in the Teacher's JTable");
    }
    //</editor-fold>
    
    //Loads the information from the Student´s Data Base ArrayList into the corresponding JTable
    private void loadStudTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading data from Students ArralyList into JTable");
        for ( cls_Student tmp: this.alStudDB ){
            StudColumn[0] = tmp.getFstName();
            StudColumn[1] = tmp.getLstName();
            StudColumn[2] = tmp.getHomNumb();
            StudColumn[3] = tmp.getCelNumb();
            StudColumn[4] = tmp.getEmail();
            StudColumn[5] = tmp.getStuLevl();
            StudColumn[6] = tmp.getResClas();
            StudColumn[7] = tmp.getRecClas();
            StudColumn[8] = tmp.getPayClas();
            StudColumn[9] = tmp.getIdNumb();
            StudColumn[10] = tmp.getStudPIN();
            tblModelStud.addRow(StudColumn);
            jtblStud.setModel(tblModelStud);
        }
        jlblStudDBsize.setText("<html><font color='blue'>Alumnos inscritos: </font>" + iStudQTY + "</html>");
        System.out.println("Students ArrayList loaded in the Student's JTable");
    }
    //</editor-fold>
    
    //Cleans the Teachers List JTable
    private void cleanProfTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelProf.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelProf.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de Profesores\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Cleans the Students List JTable
    private void cleanStudTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelStud.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelStud.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de alumnos\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    
    
    //DATA BASE HANDLING METHODS ******************
    
    //Loads the Teachers Data Base from a local .txt file into the Teachers ArrayList
    private void loadProfDB(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading Teachers Data Base from TXT file");
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
        try{
            fDataBase = new File(sLocProfDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Loading the list of Consults from the .txt file into the ArrayList
            chain = br.readLine();
            while( !chain.equals("DB LINES") ){
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
            System.out.println("Loading completed");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"No se pudo cargar la información desde la base de datos local.\n"
                    + "El Sistema ha generado un error mientras cargaba la base de datos local al Arreglo:\n"
                    + e, "TCM ERROR - loadProfDB()", JOptionPane.ERROR_MESSAGE );
            System.out.println("Error while loading");
        }
    }
    //</editor-fold>
    
    //Loads the Students Data Base from a local .txt file into the Students ArrayList
    private void loadStudDB(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading Students data base from TXT file");
        this.alStudDB.clear();
        this.iStudQTY = 0;
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sFstNam="", sMidNam="", sLstNam="", sIdNum="", sEMail="", sHomNum="",
                sCelNum="", sAddMai="", sAddCit="", sAddSta="", sAddZip="",
                sCatego="", sLevel="", sStatut="", sResCla="", sRecCla="",
                sMember="", sPaymnt="", sTeaNam="", sTeaCat="", sStudPIN="", 
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
                sMember = position[15];
                sPaymnt = position[16];
                sTeaNam = position[17];
                sTeaCat = position[18];
                sStudPIN = position[19];
                sReservs = position[20];
                sXXX1 = position[21];
                sXXX2 = position[22];
                sXXX3 = position[23];
                sXXX4 = position[24];
                alStudDB.add(new cls_Student(sFstNam, sMidNam, sLstNam, sIdNum, sEMail, sHomNum, sCelNum, 
                        sAddMai, sAddCit, sAddSta, sAddZip, sLevel, sStatut, sResCla, sRecCla, sMember, 
                        sPaymnt, sTeaNam, sTeaCat, sStudPIN, sReservs, sXXX1, sXXX2, sXXX3, sXXX4));
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
            System.out.println("Loading completed");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"No se pudo cargar la información desde la base de datos local.\n"
                    + "El Sistema ha generado un error mienstras cargaba la base de datos local al Arreglo:\n"
                    + e, "TCM ERROR - loadStudDB()", JOptionPane.ERROR_MESSAGE );
            System.out.println("Error while loading");
        }
    }
    //</editor-fold>
    
    //Loads the Invoice Data Base from a local .txt file into the Invoice ArrayList
    private void loadInvoDB(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    System.out.println("Loading Invoice data base from TXT file");    
    this.alInvoDB.clear();
        this.iInvoQTY = 0;
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sInvDate="", sInvNumb="", sCustPIN="", sCusName="", sTeaName="",
                sCusLevl="", sCusPhon="", sCusMail="", sVenName="", sModName="", sDayName="",
                sPaqName="", sSchName="", sItACode="", sItA_Qty="", sItADesc="", sItAUnit="",
                sItATotl="", sItBCode="", sItB_Qty="", sItBDesc="", sItBUnit="", sItBTotl="", 
                sItCCode="", sItC_Qty="", sItCDesc="", sItCUnit="", sItCTotl="", sItDCode="", 
                sItD_Qty="", sItDDesc="", sItDUnit="", sItDTotl="", sInvDesc="", sDescVal="", 
                sInvTaxe="", sTaxeVal="", sSubTotl="", sSubtTax="", sSubtDes="", sInvTotl="", 
                sNullInv="", sPayMeth="", sDRCCode="", sXXX1="", sXXX2="",sXXX3="";
        try{
            fDataBase = new File(sLocInvoDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Loading the list of Invoices from the .txt file into the ArrayList
            chain = br.readLine();
            while( !chain.equals("DB LINES") ){
                String [] position = chain.split("\t");
                sInvDate = position[0];
                sInvNumb = position[1];
                sCustPIN = position[2];
                sCusName = position[3];
                sTeaName = position[4];
                sCusLevl = position[5];
                sCusPhon = position[6];
                sCusMail = position[7];
                sVenName = position[8];
                sModName = position[9];
                sDayName = position[10];
                sPaqName = position[11];
                sSchName = position[12];
                sItACode = position[13];
                sItA_Qty = position[14];
                sItADesc = position[15];
                sItAUnit = position[16];
                sItATotl = position[17];
                sItBCode = position[18];
                sItB_Qty = position[19];
                sItBDesc = position[20];
                sItBUnit = position[21];
                sItBTotl = position[22];
                sItCCode = position[23];
                sItC_Qty = position[24];
                sItCDesc = position[25];
                sItCUnit = position[26];
                sItCTotl = position[27];
                sItDCode = position[28];
                sItD_Qty = position[29];
                sItDDesc = position[30];
                sItDUnit = position[31];
                sItDTotl = position[32];
                sInvDesc = position[33];
                sDescVal = position[34];
                sInvTaxe = position[35];
                sTaxeVal = position[36];
                sSubTotl = position[37];
                sSubtTax = position[38];
                sSubtDes = position[39];
                sInvTotl = position[40];
                sNullInv = position[41];
                sPayMeth = position[42];
                sDRCCode = position[43];
                sXXX1 = position[44];
                sXXX2 = position[45];
                sXXX3 = position[46];
                alInvoDB.add(new cls_Invoice(sInvDate, sInvNumb, sCustPIN, sCusName, sTeaName,
                        sCusLevl, sCusPhon, sCusMail, sVenName, sModName, sDayName,
                        sPaqName, sSchName, sItACode, sItA_Qty, sItADesc, sItAUnit,
                        sItATotl, sItBCode, sItB_Qty, sItBDesc, sItBUnit, sItBTotl, 
                        sItCCode, sItC_Qty, sItCDesc, sItCUnit, sItCTotl, sItDCode, 
                        sItD_Qty, sItDDesc, sItDUnit, sItDTotl, sInvDesc, sDescVal, 
                        sInvTaxe, sTaxeVal, sSubTotl, sSubtTax, sSubtDes, sInvTotl, 
                        sNullInv, sPayMeth, sDRCCode, sXXX1, sXXX2, sXXX3));
                        chain = br.readLine();
            }
            chain = br.readLine();
            iInvoQTY = Integer.valueOf(chain);
            //Loads the last Invoice Number created
            chain = br.readLine();
            chain = br.readLine();
            this.sLastInvoNum = chain;
            br.close();
            fr.close();
            System.out.println("Loading completed");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"No se pudo cargar la información desde la base de datos local.\n"
                    + "El Sistema ha generado un error mienstras cargaba la base de datos local al Arreglo:\n"
                    + e, "TCM ERROR - loadInvoDB()", JOptionPane.ERROR_MESSAGE );
            System.out.println("Error while loading");
        }
    }
    //</editor-fold>
    
    //Loads the Costs Data Base from a local .txt file into the Costs ArrayList
    private void loadCostDB(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading Costs Data Base from TXT file");
        this.alCostDB.clear();
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sHourLV="", sSchdLV="", sN1InLV="", sN1G2LV="", sN1G3LV="", 
            sN1G4LV="", sN1GPLV="", sN2InLV="", sN2G2LV="", sN2G3LV="", sN2G4LV="", 
            sN2GPLV="", sHPInLV="", sHPG2LV="", sHPG3LV="", sHPG4LV="", sHPGPLV="", 
            sHourFS="", sSchdFS="", sN1InFS="", sN1G2FS="", sN1G3FS="", sN1G4FS="", 
            sN1GPFS="", sN2InFS="", sN2G2FS="", sN2G3FS="", sN2G4FS="", sN2GPFS="", 
            sHPInFS="", sHPG2FS="", sHPG3FS="", sHPG4FS="", sHPGPFS="",
            sHourMT="", sSchdMT="", sN1InMT="", sN1G2MT="", sN1G3MT="", sN1G4MT="", 
            sN1GPMT="", sN2InMT="", sN2G2MT="", sN2G3MT="", sN2G4MT="", sN2GPMT="", 
            sHPInMT="", sHPG2MT="", sHPG3MT="", sHPG4MT="", sHPGPMT="";
        try{
            fDataBase = new File(sLocCostDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Loading the list of Costs from the .txt file into the ArrayList
            chain = br.readLine();
            while( !chain.equals("END") ){
                String [] position = chain.split("\t");
                sHourLV = position[0]; 
                sSchdLV = position[1]; 
                sN1InLV = position[2]; 
                sN1G2LV = position[3]; 
                sN1G3LV = position[4];
                sN1G4LV = position[5]; 
                sN1GPLV = position[6]; 
                sN2InLV = position[7]; 
                sN2G2LV = position[8]; 
                sN2G3LV = position[9]; 
                sN2G4LV = position[10]; 
                sN2GPLV = position[11]; 
                sHPInLV = position[12]; 
                sHPG2LV = position[13]; 
                sHPG3LV = position[14]; 
                sHPG4LV = position[15]; 
                sHPGPLV = position[16]; 
                sHourFS = position[17]; 
                sSchdFS = position[18]; 
                sN1InFS = position[19]; 
                sN1G2FS = position[20]; 
                sN1G3FS = position[21]; 
                sN1G4FS = position[22]; 
                sN1GPFS = position[23]; 
                sN2InFS = position[24]; 
                sN2G2FS = position[25]; 
                sN2G3FS = position[26]; 
                sN2G4FS = position[27]; 
                sN2GPFS = position[28]; 
                sHPInFS = position[29]; 
                sHPG2FS = position[30]; 
                sHPG3FS = position[31]; 
                sHPG4FS = position[32]; 
                sHPGPFS = position[33];
                sHourMT = position[34]; 
                sSchdMT = position[35]; 
                sN1InMT = position[36]; 
                sN1G2MT = position[37]; 
                sN1G3MT = position[38]; 
                sN1G4MT = position[39]; 
                sN1GPMT = position[40]; 
                sN2InMT = position[41]; 
                sN2G2MT = position[42]; 
                sN2G3MT = position[43]; 
                sN2G4MT = position[44]; 
                sN2GPMT = position[45]; 
                sHPInMT = position[46]; 
                sHPG2MT = position[47]; 
                sHPG3MT = position[48]; 
                sHPG4MT = position[49]; 
                sHPGPMT = position[50];
                alCostDB.add(new cls_Cost_Item(sHourLV, sSchdLV, sN1InLV, sN1G2LV, sN1G3LV,
                        sN1G4LV, sN1GPLV, sN2InLV, sN2G2LV, sN2G3LV, sN2G4LV, 
                        sN2GPLV, sHPInLV, sHPG2LV, sHPG3LV, sHPG4LV, sHPGPLV, 
                        sHourFS, sSchdFS, sN1InFS, sN1G2FS, sN1G3FS, sN1G4FS, 
                        sN1GPFS, sN2InFS, sN2G2FS, sN2G3FS, sN2G4FS, sN2GPFS, 
                        sHPInFS, sHPG2FS, sHPG3FS, sHPG4FS, sHPGPFS,
                        sHourMT, sSchdMT, sN1InMT, sN1G2MT, sN1G3MT, sN1G4MT, 
                        sN1GPMT, sN2InMT, sN2G2MT, sN2G3MT, sN2G4MT, sN2GPMT, 
                        sHPInMT, sHPG2MT, sHPG3MT, sHPG4MT, sHPGPMT));
                chain = br.readLine();
            }
            br.close();
            fr.close();
            System.out.println("Loading completed");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"No se pudo cargar la información desde la base de datos local.\n"
                    + "El Sistema ha generado un error mientras cargaba la base de datos local al Arreglo:\n"
                    + e, "TCM ERROR - loadCostDB()", JOptionPane.ERROR_MESSAGE );
            System.out.println("Error while loading");
        }
    }
    //</editor-fold>
    
    //Loads the Membership Data Base from a local .txt file into the Membership 2D-Array
    private void loadMembDB(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading Membership Data Base from TXT file");
        File fDataBase;
        FileReader fr;
        BufferedReader br;
        String chain, sMonth="", sMembr="";
        try{
            fDataBase = new File(sLocMembDBPath);
            fr = new FileReader(fDataBase);
            br = new BufferedReader(fr);
            //Loading the list of Costs from the .txt file into the ArrayList
            chain = br.readLine();
            int r = 0;
            while( !chain.equals("END") ){
                String [] position = chain.split("\t");
                sMonth = position[0];
                sMembr = position[1];
                this.arrMemb[r][0] = sMonth;
                this.arrMemb[r][1] = sMembr;
                r++;
                chain = br.readLine();
            }
            br.close();
            fr.close();
            System.out.println("Loading completed");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"No se pudo cargar la información desde la base de datos local.\n"
                    + "El Sistema ha generado un error mientras cargaba la base de datos local al Arreglo:\n"
                    + e, "TCM ERROR - loadMembDB()", JOptionPane.ERROR_MESSAGE );
            System.out.println("Error while loading");
        }
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
            //Overwrites the txt file, line by line, with all the consults that are currently in the Data Base Array List
            for(cls_Teacher tmp: this.alProfDB){
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
            //Writes the DB lines QTY
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
    
    //Updates the local .TXT Students Data Base file directly from the Students Data Base ArrayList
    public void updateStudentDB_txt(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        try
        {
            File fDataBase;
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter wr = null;
            
            fDataBase = new File (this.sLocStudDBPath); //points to the local .txt Students data base file
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
            jlblStudDBsize.setText("<html><font color='blue'>Alumnos inscritos: </font>" + iStudQTY + "</html>");
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
                + e, "TCM ERROR - updateStudentDB_txt()", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Updates the local .TXT Invoices Data Base file directly from the Invoices Data Base ArrayList
    public void updateInvoiceDB_txt(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Saving the Invoice data base from the ArrayList into the local TXT file");
        try
        {
            File fDataBase;
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter wr = null;
            
            fDataBase = new File (this.sLocInvoDBPath); //points to the local .txt Invoices data base file
            fw = new FileWriter(fDataBase);
            bw = new BufferedWriter(fw);
            wr = new PrintWriter(bw);
            
            //Reads, line by line, all the Invoices that are currently in the Data Base Array List
            for(cls_Invoice tmp: this.alInvoDB){
                wr.println( tmp.getInvDate() + "\t"
                        + tmp.getInvNumb() + "\t"
                        + tmp.getCustPIN() + "\t"
                        + tmp.getCusName() + "\t"
                        + tmp.getTeaName() + "\t"
                        + tmp.getCusLevl() + "\t"
                        + tmp.getCusPhon() + "\t"
                        + tmp.getCusMail() + "\t"
                        + tmp.getVenName() + "\t"
                        + tmp.getModName() + "\t"
                        + tmp.getDayName() + "\t"
                        + tmp.getPaqName() + "\t"
                        + tmp.getSchName() + "\t"
                        + tmp.getItACode() + "\t"
                        + tmp.getItA_Qty() + "\t"
                        + tmp.getItADesc() + "\t"
                        + tmp.getItAUnit() + "\t"
                        + tmp.getItATotl() + "\t"
                        + tmp.getItBCode() + "\t"
                        + tmp.getItB_Qty() + "\t"
                        + tmp.getItBDesc() + "\t"
                        + tmp.getItBUnit() + "\t"
                        + tmp.getItBTotl() + "\t"
                        + tmp.getItCCode() + "\t"
                        + tmp.getItC_Qty() + "\t"
                        + tmp.getItCDesc() + "\t"
                        + tmp.getItCUnit() + "\t"
                        + tmp.getItCTotl() + "\t"
                        + tmp.getItDCode() + "\t"
                        + tmp.getItD_Qty() + "\t"
                        + tmp.getItDDesc() + "\t"
                        + tmp.getItDUnit() + "\t"
                        + tmp.getItDTotl() + "\t"
                        + tmp.getInvDesc() + "\t"
                        + tmp.getDescVal() + "\t"
                        + tmp.getInvTaxe() + "\t"
                        + tmp.getTaxeVal() + "\t"
                        + tmp.getSubTotl() + "\t"
                        + tmp.getSubtTax() + "\t"
                        + tmp.getSubtDes() + "\t"
                        + tmp.getInvTotl() + "\t"
                        + tmp.getNullInv() + "\t"
                        + tmp.getPayMeth() + "\t"
                        + tmp.getDRCCode() + "\t"
                        + tmp.getXXX1() + "\t"
                        + tmp.getXXX2() + "\t"
                        + tmp.getXXX3() );
            }
            //Loads the Invoice QTY into the screen label
            iInvoQTY = alInvoDB.size();
            //Writes the DB lines QTY
            wr.println("DB LINES");
            wr.println(String.valueOf(iInvoQTY));
            //Writes the last Invoice number created
            wr.println("LAST INVOICE");
            wr.println(this.sLastInvoNum);
            wr.close();
            bw.close();
            fw.close();
            System.out.println("Data Base saving completed");
        }
        catch(IOException e){JOptionPane.showMessageDialog(this,"Ocurrió un error mientras se actualizaba la Base de Datos de facturación:\n"
                + e, "TCM ERROR - updateInvoiceDB_txt()", JOptionPane.ERROR_MESSAGE);
        System.out.println("Error while saving the Data Base");}
    }
    //</editor-fold>
    
    //Cleans and resets the data in the Invoice screen
    private void cleanInvoice(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Cleans the Header
        this.jlblInvNum.setText("FACTURA: " + setNextInvoiceNum());
        jtxtDat.setText(setDate());
        this.jtxtStuPIN.setText("");
        this.jtxtStuPIN.setFocusable(false);
        this.jtxtStuNam.setText("");
        this.jtxtStuLev.setText("");
        this.jtxtPhone.setText("");
        this.jtxtMail.setText("");
        this.jtxtVenNam.setText("");
        this.jcboxMode.select(0);
        this.jcboxDay.select(0);
        this.jcboxPaq.select(0);
        this.jcboxHour.select(0);
        this.jchkboxMemb.setState(false);
        this.jchkboxManual.setState(false);
        this.jcboxDRC.removeAll();
        this.jrbtnEfec.setSelected(true);
        this.jlblCoLogo.setIcon(null);        
        //Cleans the Invoice body
        this.jtxtA1.setText("");
        this.jtxtA2.setText("");
        this.jtxtA3.setText("");
        this.jtxtA4.setText("");
        this.jtxtA5.setText("");
        this.jtxtB1.setText("");
        this.jtxtB2.setText("");
        this.jtxtB3.setText("");
        this.jtxtB4.setText("");
        this.jtxtB5.setText("");
        this.jtxtC1.setText("");
        this.jtxtC2.setText("");
        this.jtxtC3.setText("");
        this.jtxtC4.setText("");
        this.jtxtC5.setText("");
        this.jtxtD1.setText("");
        this.jtxtD2.setText("");
        this.jtxtD3.setText("");
        this.jtxtD4.setText("");
        this.jtxtD5.setText("");
        //Cleans the Invoice foot
        this.jtxtDesPer.setText("");
        this.jtxtTaxPer.setText("");
        this.jtxtDesVal.setText("");
        this.jtxtTaxVal.setText("");
        this.jtxtSubTot.setText("");
        this.jtxtSubDes.setText("");
        this.jtxtSubTax.setText("");
        this.jtxtTotal.setText("");
        this.jrbtnEfec.setSelected(true);
        //Reenables the necessary fields
        this.jcboxMode.setEnabled(true);
        this.jcboxDay.setEnabled(true);
        this.jcboxPaq.setEnabled(true);
        this.jcboxHour.setEnabled(true);
        this.jtxtVenNam.setEditable(true);
        this.jbtnAddDRC.setEnabled(true);
        this.jcboxDRC.setEnabled(true);
        this.jchkboxManual.setEnabled(true);
        this.jchkboxMemb.setEnabled(true);
        this.jtxtA1.setEditable(true);
        this.jtxtA2.setEditable(true);
        this.jtxtB1.setEditable(true);
        this.jtxtB2.setEditable(true);
        this.jtxtC1.setEditable(true);
        this.jtxtC2.setEditable(true);
        this.jtxtD1.setEditable(true);
        this.jtxtD2.setEditable(true);
        this.jbtnGen.setEnabled(true);
        this.jrbtnEfec.setEnabled(true);
        this.jrbtnTarj.setEnabled(true);
        this.jrbtnTran.setEnabled(true);
        this.jrbtnAuto.setEnabled(true);
        this.jtxtDesPer.setEditable(true);
        this.jtxtTaxPer.setEditable(true);
        this.jtxtSrchInvo.setText("");
    }
    //</editor-fold>
    
    //Identifies the Teacher´s line number from the Teachers ArraList, depending on the PIN value
    private int getProfLine(String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int iLine = -1;
        int i = 0; //Index
        for(cls_Teacher tmp: this.alProfDB){
            if ( tmp.getProfPIN().equals(sPIN) ){
                iLine = i;
            }
            i++;
        }
        return iLine;
    }
    //</editor-fold>
    
    //Identifies the Student´s line number from the Students ArraList, depending on the PIN value
    private int getStudLine(String sPIN){
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
    
    //Identifies the Invoice´s line number from the Invoice ArraList, depending on the Invoice number
    private int getInvoLine(String sNum){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int iLine = -1;
        
        int i = 0; //Index
        for(cls_Invoice tmp: this.alInvoDB){
            if ( Integer.valueOf(tmp.getInvNumb()) == Integer.valueOf(sNum) ){
                iLine = i;
            }
            i++;
        }
        return iLine;
    }
    //</editor-fold>
    
    
    //Reformats the text in order to be compared
    //Removes accent mark and the "ñ", spaces, hyphens and dots  and sets the text to uppercase 
    private String reformatText(String sText){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        sText = sText.replace('.',' ');
        sText = sText.replace('á','a');
        sText = sText.replace('é','e');
        sText = sText.replace('í','i');
        sText = sText.replace('ó','o');
        sText = sText.replace('ú','u');
        sText = sText.replace('ñ','n');
        sText = sText.replaceAll(" ","");
        sText = sText.replaceAll("-","");
        sText = sText.toUpperCase();
        return sText;
    }
    //</editor-fold>
    
    //Finds an Student on the Student ArrayList depending on the provided String
    //It builds an ArrayList with all the entries that match with the given String
    //The Method looks for the String into the Name, Last Name, Phone and ID fields.
    private void findStudent(String sValue){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Clear the ARrayList that will store the Search results
        alStudSrchDB.clear();
        //Stars searching for the given value entry by entry in the Main Data Base ArrayList
        for ( int i=0; i<alStudDB.size(); i++ ){
            if ( reformatText(alStudDB.get(i).getFstName()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alStudDB.get(i).getLstName()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alStudDB.get(i).getIdNumb()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alStudDB.get(i).getHomNumb()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alStudDB.get(i).getCelNumb()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alStudDB.get(i).getStudPIN()).indexOf(reformatText(sValue)) != -1){
                //If any of the columns contains the given value, it adds this entry in to the new Search Results ArrayList
                alStudSrchDB.add(new cls_Student(alStudDB.get(i).getFstName(),
                        alStudDB.get(i).getMidName(),
                        alStudDB.get(i).getLstName(),
                        alStudDB.get(i).getIdNumb(),
                        alStudDB.get(i).getEmail(),
                        alStudDB.get(i).getHomNumb(),
                        alStudDB.get(i).getCelNumb(),
                        alStudDB.get(i).getAddMain(),
                        alStudDB.get(i).getAddCity(),
                        alStudDB.get(i).getAddStat(),
                        alStudDB.get(i).getAddZiCo(),
                        alStudDB.get(i).getStuLevl(),
                        alStudDB.get(i).getStatus(),
                        alStudDB.get(i).getResClas(),
                        alStudDB.get(i).getRecClas(),
                        alStudDB.get(i).getMembshp(),
                        alStudDB.get(i).getPayClas(),
                        alStudDB.get(i).getTeaName(),
                        alStudDB.get(i).getTeaCate(),
                        alStudDB.get(i).getStudPIN(),
                        alStudDB.get(i).getXXX1(),
                        alStudDB.get(i).getXXX2(),
                        alStudDB.get(i).getXXX3(),
                        alStudDB.get(i).getXXX4(),
                        String.valueOf(i))); //Uses the last position to temporarily save the Student DB ArrayList Position
            }
        }
        //Checks if the process detected results or not
        if ( alStudSrchDB.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "ALUMNO NO ENCONTRADO");
        }
        else{//If the Search operation found results it shows a screen with them
            JOptionPane.showMessageDialog(this, alStudSrchDB.size() + " ALUMNO(S) ENCONTRADO(S)");
            //Loads the resulting list of Students into the Students screen table 
            cleanStudTable();
            try {
                for (cls_Student tmp : alStudSrchDB) {
                    StudColumn[0] = tmp.getFstName();
                    StudColumn[1] = tmp.getLstName();
                    StudColumn[2] = tmp.getHomNumb();
                    StudColumn[3] = tmp.getCelNumb();
                    StudColumn[4] = tmp.getEmail();
                    StudColumn[5] = tmp.getStuLevl();
                    StudColumn[6] = tmp.getResClas();
                    StudColumn[7] = tmp.getRecClas();
                    StudColumn[8] = tmp.getPayClas();
                    StudColumn[9] = tmp.getIdNumb();
                    StudColumn[10] = tmp.getStudPIN();
                    tblModelStud.addRow(StudColumn);
                    jtblStud.setModel(tblModelStud);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this, "Hubo un error mientras cargaba los resultados en pantalla\n"
                        + e, "ERROR - findStudent()", JOptionPane.ERROR_MESSAGE);
            }
            //Warns the System that the DB table screen is now showing the results list
            this.bStudDBFlag = false;
            this.jlblStudDBsize.setText("<html><font color='orange'>RESULTADOS DE LA BÚSQUEDA: </font>" + alStudSrchDB.size() + "</html>");
        }
    }
    //</editor-fold>
    
    //Finds an Teacher on the Teacher ArrayList depending on the provided String
    //It builds an ArrayList with all the entries that match with the given String
    //The Method looks for the String into the Name, Last Name, Phone and ID fields.
    private void findTeacher(String sValue){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Clear the ArrayList that will store the Search results
        alProfSrchDB.clear();
        //Stars searching for the given value entry by entry in the Main Data Base ArrayList
        for ( int i=0; i<alProfDB.size(); i++ ){
            if ( reformatText(alProfDB.get(i).getFstName()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alProfDB.get(i).getLstName()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alProfDB.get(i).getIdNumb()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alProfDB.get(i).getHomNumb()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alProfDB.get(i).getCelNumb()).indexOf(reformatText(sValue)) != -1 ||
                    reformatText(alProfDB.get(i).getProfPIN()).indexOf(reformatText(sValue)) != -1){
                //If any of the columns contains the given value, it adds this entry in to the new Search Results ArrayList
                alProfSrchDB.add(new cls_Teacher(alProfDB.get(i).getFstName(),
                        alProfDB.get(i).getMidName(),
                        alProfDB.get(i).getLstName(),
                        alProfDB.get(i).getIdNumb(),
                        alProfDB.get(i).getEmail(),
                        alProfDB.get(i).getHomNumb(),
                        alProfDB.get(i).getCelNumb(),
                        alProfDB.get(i).getAddMain(),
                        alProfDB.get(i).getAddCity(),
                        alProfDB.get(i).getAddStat(),
                        alProfDB.get(i).getAddZiCo(),
                        alProfDB.get(i).getCategry(),
                        alProfDB.get(i).getStatus(),
                        alProfDB.get(i).getStudQty(),
                        alProfDB.get(i).getResClas(),
                        alProfDB.get(i).getImpClas(),
                        alProfDB.get(i).getPayClas(),
                        alProfDB.get(i).getComPerc(),
                        alProfDB.get(i).getSalary(),
                        alProfDB.get(i).getHrsCost(),
                        alProfDB.get(i).getStuList(),
                        alProfDB.get(i).getProfPIN(),
                        alProfDB.get(i).getXXX1(),
                        alProfDB.get(i).getXXX2(),
                        alProfDB.get(i).getXXX3(),
                        String.valueOf(i))); //Uses the last position to temporarily save the Student DB ArrayList Position
            }
        }
        //Checks if the process detected results or not
        if ( alProfSrchDB.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "PROFESOR NO ENCONTRADO");
        }
        else{//If the Search operation found results it shows a screen with them
            JOptionPane.showMessageDialog(this, alProfSrchDB.size() + " PPROFESOR(ES) ENCONTRADO(S)");
            //Loads the resulting list of Students into the Students screen table 
            cleanProfTable();
            try {
                for (cls_Teacher tmp : alProfSrchDB) {
                    ProfColumn[0] = tmp.getProfPIN();
                    ProfColumn[1] = tmp.getFstName();
                    ProfColumn[2] = tmp.getLstName();
                    ProfColumn[3] = tmp.getHomNumb();
                    ProfColumn[4] = tmp.getCelNumb();
                    ProfColumn[5] = tmp.getEmail();
                    ProfColumn[6] = tmp.getCategry();
                    ProfColumn[7] = tmp.getStudQty();
                    ProfColumn[8] = tmp.getResClas();
                    ProfColumn[9] = tmp.getImpClas();
                    ProfColumn[10] = tmp.getPayClas();
                    ProfColumn[11] = tmp.getStatus();
                    ProfColumn[12] = tmp.getIdNumb();
                    tblModelProf.addRow(ProfColumn);
                    jtblProf.setModel(tblModelProf);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this, "Hubo un error mientras cargaba los resultados en pantalla\n"
                        + e, "ERROR - findTeacher()", JOptionPane.ERROR_MESSAGE);
            }
            //Warns the System that the DB table screen is now showing the results list
            this.bProfDBFlag = false;
            this.jlblProfDBsize.setText("<html><font color='orange'>RESULTADOS DE LA BÚSQUEDA: </font>" + alProfSrchDB.size() + "</html>");
        }
    }
    //</editor-fold>
    
    
    
    //DATE HANDLING METHODS
    
    //Shows the System's date in the main screen
    private String setDate(){
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
        switch (sMon)
        {
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
        sDate = sDay + "-" + sMon + "-" + sYea;
        return sDate;
    }
    //</editor-fold>
    
    //PRINTING HANDLING METHODS
    
    private void printInvoice(){
        Print text = new Print();
        
        
        
       

    //boolean doPrint = job.printDialog();
        
        

    }
    
    //Creates the bobdy of the mail that will contain the Invoice
    private String createBodyMail(String sPIN){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        //Finds the Student position in the current ArrayList DB
        int iPos = this.getStudLine(sPIN);
        String sLineA = "", sLineB = "", sLineC = "", sLineD = "";
        //Checks line by line in the invoice
        if ( !this.jtxtA1.getText().equals("NA") && !this.jtxtA1.getText().equals("") ){
            sLineA = jtxtA1.getText() + "\t" + jtxtA2.getText() + "\t" + jtxtA3.getText() + "\n"
                    + "Valor: c." + jtxtA5.getText();
        }
        if ( !this.jtxtB1.getText().equals("NA") && !this.jtxtB1.getText().equals("") ){
            sLineB = jtxtB1.getText() + "\t" + jtxtB2.getText() + "\t" + jtxtB3.getText() + "\n"
                    + "Valor: c." + jtxtB5.getText();
        }
        if ( !this.jtxtC1.getText().equals("NA") && !this.jtxtC1.getText().equals("") ){
            sLineC = jtxtC1.getText() + "\t" + jtxtC2.getText() + "\t" + jtxtC3.getText() + "\n"
                    + "Valor: c." + jtxtC5.getText();
        }
        if ( !this.jtxtD1.getText().equals("NA") && !this.jtxtD1.getText().equals("") ){
            sLineD = jtxtD1.getText() + "\t" + jtxtD2.getText() + "\t" + jtxtD3.getText() + "\n"
                    + "Valor: c." + jtxtD5.getText();
        }
        //Creates a greeting
        String sGreeting = "Hola " + alStudDB.get(iPos).getFstName() + ". Esperamos que se encuentre bien.\n\n";
        //Creates the rest of the message
        String sMailBody = sGreeting
                + "El siguiente es el detalle de su factura:\n"
                + "-------------------------------------------------------------------------------------------\n"
                + "TOP TENNIS CENTER\n"
                + "Factura: " + jlblInvNum.getText().substring(8) + "\n"
                + "Fecha: " + this.jtxtDat.getText() + "\n"
                + "Cliente: " + this.jtxtStuNam.getText() + "\n"
                + "-------------------------------------------------------------------------------------------\n"
                + "Código / Cant. / Descripción / Valor\n"
                + "-------------------------------------------------------------------------------------------\n";
        if ( !this.jtxtA1.getText().equals("NA") && !this.jtxtA1.getText().equals("") ){
            sMailBody = sMailBody + sLineA + "\n\n";
        }
        if ( !this.jtxtB1.getText().equals("NA") && !this.jtxtB1.getText().equals("") ){
            sMailBody = sMailBody + sLineB + "\n\n";
        }
        if ( !this.jtxtC1.getText().equals("NA") && !this.jtxtC1.getText().equals("") ){
            sMailBody = sMailBody + sLineC + "\n\n";
        }
        if ( !this.jtxtD1.getText().equals("NA") && !this.jtxtD1.getText().equals("") ){
            sMailBody = sMailBody + sLineD + "\n\n";
        }
        String sPayMeth = "";
        if ( this.jrbtnEfec.isSelected() ){sPayMeth = "Efectivo";}
        if ( this.jrbtnTarj.isSelected() ){sPayMeth = "Tarjeta";}
        if ( this.jrbtnTran.isSelected() ){sPayMeth = "Transferencia";}
        if ( this.jrbtnAuto.isSelected() ){sPayMeth = "Cargo automático";}
        sMailBody = sMailBody 
                + "-------------------------------------------------------------------------------------------\n"
                + "\t\t\t\t\tSubtotal: c." + this.jtxtSubTot.getText() + "\n"
                + "\t\t\t\t\tImpuest: c." + this.jtxtTaxVal.getText() + "\n"
                + "\t\t\t\t\tDescuen: c." + this.jtxtDesVal.getText() + "\n"
                + "\t\t\t\t\tTOTAL: c." + this.jtxtTotal.getText() + "\n"
                + "-------------------------------------------------------------------------------------------\n"
                + "Forma de pago: " + sPayMeth + "\n"
                + "-------------------------------------------------------------------------------------------\n"
                + "Esperamos que nuestro servicio sea de su completa satisfacción.\n\n"
                + "Cordialmente;\n\n"
                + "ADMINISTRACIÓN\n"
                + "TOP TENNIS CENTER\n"
                + "Cédula Jurídica: 3-102-752881\n"
                + "Teléfonos: 2282-9540 2282-9222, ext. 4 / 8922-1934\n\n";
        return sMailBody;
    }
    //</editor-fold>



    

  
    
    //DATA VALIDATION, CONVERTION AND FORMAT METHODS
    
    //Validates if a given String is a number
    private boolean validateRealNumber(String sNumb){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">  
        boolean bFlag = true;
        if ( !sNumb.equals("") ){
            try{
                double dNumb = Double.valueOf(sNumb) / 1;
            }
            catch (Exception ex) {
                bFlag = false;
                JOptionPane.showMessageDialog(this, "Ha introducido un número no válido. Por favor corrija.\n"
                            + ex, "ERROR - validateNumber()", JOptionPane.ERROR_MESSAGE);
            }
        }
        return bFlag;
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
    
    //SEQUENTIAL DATA GENERATION
    
    //Generates a new Invoice Number based on the lastone generated
    private String generateNewInvoiceNum(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        //Creates the next Invoice based on the last known Invoice Number
        int iNum = Integer.valueOf(sLastInvoNum) + 1;
        String sNum = String.valueOf(iNum);
        //Reformats the String to five chars length
        //Counts how many char we have so far
        int iCharCount = sNum.length();
        //Then adds the necessary left zeroes at the left
        switch (iCharCount) {
            case 1 : {sNum = "0000" + sNum; break;}
            case 2 : {sNum = "000" + sNum; break;}
            case 3 : {sNum = "00" + sNum; break;}
            case 4 : {sNum = "0" + sNum; break;}
            case 5 : {sNum = sNum; break;}
            default : {break;}
        }
        //Updates the last Invoice number with the one we just created
        this.sLastInvoNum = sNum;
        return sNum;
    }
    //</editor-fold>
    
    //Generates a String value for the next Invoice Number
    private String setNextInvoiceNum(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        int iNextInv = Integer.valueOf(sLastInvoNum) + 1;
        String sNextInv = String.valueOf(iNextInv);
        //Counts how many char we have so far
        int iCharCount = sNextInv.length();
        //Then adds the necessary left zeroes at the left
        switch (iCharCount) {
            case 1 : {sNextInv = "0000" + sNextInv; break;}
            case 2 : {sNextInv = "000" + sNextInv; break;}
            case 3 : {sNextInv = "00" + sNextInv; break;}
            case 4 : {sNextInv = "0" + sNextInv; break;}
            default : {break;}
        }
        return sNextInv;
    }
    //</editor-fold>
    
    //INVOICE CALCULATION METHODS
    
    //Creates and saves a new invoice in the ArrayList data base
    private void createNewInvoice(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code"> 
        //Updates the current date according with the System´s clock
        jtxtDat.setText(setDate());
        //Gets the data from the screen
        String sInvDate="", sInvNumb="", sCustPIN="", sCusName="", sTeaName="", sCusLevl="", 
            sCusPhon="", sCusMail="", sVenName="", sModName="", sDayName="", sPaqName="", sSchName="", 
            sItACode="", sItA_Qty="", sItADesc="", sItAUnit="", sItATotl="", sItBCode="", sItB_Qty="", 
            sItBDesc="", sItBUnit="", sItBTotl="", sItCCode="", sItC_Qty="", sItCDesc="", sItCUnit="", 
            sItCTotl="", sItDCode="", sItD_Qty="", sItDDesc="", sItDUnit="", sItDTotl="", sInvDesc="", 
            sDescVal="", sInvTaxe="", sTaxeVal="", sSubTotl="", sSubtTax="", sSubtDes="", sInvTotl="", 
            sNullInv="", sPayMeth="NA", sDRCCode="NA", sXXX1="NA", sXXX2="NA", sXXX3="NA";
        //Saves the Header
        System.out.println("Fecha en pantalla: " + jtxtDat.getText());
        if ( this.jtxtDat.getText().equals("") ){sInvDate = "NA";}else{sInvDate = this.jtxtDat.getText();}
        sInvNumb = generateNewInvoiceNum();//Generates and saves a new Invoice Number
        if ( this.jtxtStuPIN.getText().equals("") ){sCustPIN = "NA";}else{sCustPIN = this.jtxtStuPIN.getText();}
        if ( this.jtxtStuNam.getText().equals("") ){sCusName = "NA";}else{sCusName = this.jtxtStuNam.getText();}
        sTeaName = "NA";
        if ( this.jtxtStuLev.getText().equals("") ){sCusLevl = "NA";}else{sCusLevl = this.jtxtStuLev.getText();}
        if ( this.jtxtPhone.getText().equals("") ){sCusPhon = "NA";}else{sCusPhon = this.jtxtPhone.getText();}
        if ( this.jtxtMail.getText().equals("") ){sCusMail = "NA";}else{sCusMail = this.jtxtMail.getText();}
        if ( this.jtxtVenNam.getText().equals("") ){sVenName = "NA";}else{sVenName = this.jtxtVenNam.getText();}
        sModName = this.jcboxMode.getSelectedItem();
        sDayName = this.jcboxDay.getSelectedItem();        
        sPaqName = this.jcboxPaq.getSelectedItem();
        sSchName = this.jcboxHour.getSelectedItem();
        if ( this.jcboxDRC.getSelectedItem().equals("Sin Reservas") ){
            sDRCCode = "NA";
        }
        else{
            String sDRC1 = this.jcboxDRC.getSelectedItem().substring(0,23);
            String sDRC2 = this.jcboxDRC.getSelectedItem().substring(35);
            sDRCCode = sDRC1 + sDRC2;
        }
        //Saves the Body
        if ( this.jtxtA1.getText().equals("") ){sItACode = "NA";}else{sItACode = this.jtxtA1.getText();}
        if ( this.jtxtA2.getText().equals("") ){sItA_Qty = "NA";}else{sItA_Qty = this.jtxtA2.getText();}
        if ( this.jtxtA3.getText().equals("") ){sItADesc = "NA";}else{sItADesc = this.jtxtA3.getText();}
        if ( this.jtxtA4.getText().equals("") ){sItAUnit = "NA";}else{sItAUnit = this.jtxtA4.getText();}
        if ( this.jtxtA5.getText().equals("") ){sItATotl = "NA";}else{sItATotl = this.jtxtA5.getText();}
        if ( this.jtxtB1.getText().equals("") ){sItBCode = "NA";}else{sItBCode = this.jtxtB1.getText();}
        if ( this.jtxtB2.getText().equals("") ){sItB_Qty = "NA";}else{sItB_Qty = this.jtxtB2.getText();}
        if ( this.jtxtB3.getText().equals("") ){sItBDesc = "NA";}else{sItBDesc = this.jtxtB3.getText();}
        if ( this.jtxtB4.getText().equals("") ){sItBUnit = "NA";}else{sItBUnit = this.jtxtB4.getText();}
        if ( this.jtxtB5.getText().equals("") ){sItBTotl = "NA";}else{sItBTotl = this.jtxtB5.getText();}
        if ( this.jtxtC1.getText().equals("") ){sItCCode = "NA";}else{sItCCode = this.jtxtC1.getText();}
        if ( this.jtxtC2.getText().equals("") ){sItC_Qty = "NA";}else{sItC_Qty = this.jtxtC2.getText();}
        if ( this.jtxtC3.getText().equals("") ){sItCDesc = "NA";}else{sItCDesc = this.jtxtC3.getText();}
        if ( this.jtxtC4.getText().equals("") ){sItCUnit = "NA";}else{sItCUnit = this.jtxtC4.getText();}
        if ( this.jtxtC5.getText().equals("") ){sItCTotl = "NA";}else{sItCTotl = this.jtxtC5.getText();}
        if ( this.jtxtD1.getText().equals("") ){sItDCode = "NA";}else{sItDCode = this.jtxtD1.getText();}
        if ( this.jtxtD2.getText().equals("") ){sItD_Qty = "NA";}else{sItD_Qty = this.jtxtD2.getText();}
        if ( this.jtxtD3.getText().equals("") ){sItDDesc = "NA";}else{sItDDesc = this.jtxtD3.getText();}
        if ( this.jtxtD4.getText().equals("") ){sItDUnit = "NA";}else{sItDUnit = this.jtxtD4.getText();}
        if ( this.jtxtD5.getText().equals("") ){sItDTotl = "NA";}else{sItDTotl = this.jtxtD5.getText();}
        //Saves the Foot
        if ( this.jrbtnEfec.isSelected() ){sPayMeth = "Efectivo";}
        if ( this.jrbtnTarj.isSelected() ){sPayMeth = "Tarjeta";}
        if ( this.jrbtnTran.isSelected() ){sPayMeth = "Transferencia";}
        if ( this.jrbtnAuto.isSelected() ){sPayMeth = "Cargo automático";}
        if ( this.jtxtDesPer.getText().equals("") ){sInvDesc = "NA";}else{sInvDesc = this.jtxtDesPer.getText();}
        if ( this.jtxtDesVal.getText().equals("") ){sDescVal = "NA";}else{sDescVal = this.jtxtDesVal.getText();}
        if ( this.jtxtTaxPer.getText().equals("") ){sInvTaxe = "NA";}else{sInvTaxe = this.jtxtTaxPer.getText();}
        if ( this.jtxtTaxVal.getText().equals("") ){sTaxeVal = "NA";}else{sTaxeVal = this.jtxtTaxVal.getText();}
        if ( this.jtxtSubTot.getText().equals("") ){sSubTotl = "NA";}else{sSubTotl = this.jtxtSubTot.getText();}
        if ( this.jtxtSubTax.getText().equals("") ){sSubtTax = "NA";}else{sSubtTax = this.jtxtSubTax.getText();}
        if ( this.jtxtSubDes.getText().equals("") ){sSubtDes = "NA";}else{sSubtDes = this.jtxtSubDes.getText();}
        if ( this.jtxtTotal.getText().equals("") ){sInvTotl = "NA";}else{sInvTotl = this.jtxtTotal.getText();}
        sNullInv = "FALSE";//This means that the Invoice IS NOT NULL
        //Values XXX1 to XXX3 alread have "NA" as assigned value
        alInvoDB.add(new cls_Invoice(sInvDate, sInvNumb, sCustPIN, sCusName, sTeaName, sCusLevl,
                sCusPhon, sCusMail, sVenName, sModName, sDayName, sPaqName, sSchName, 
                sItACode, sItA_Qty, sItADesc, sItAUnit, sItATotl, sItBCode, sItB_Qty, 
                sItBDesc, sItBUnit, sItBTotl, sItCCode, sItC_Qty, sItCDesc, sItCUnit, 
                sItCTotl, sItDCode, sItD_Qty, sItDDesc, sItDUnit, sItDTotl, sInvDesc, 
                sDescVal, sInvTaxe, sTaxeVal, sSubTotl, sSubtTax, sSubtDes, sInvTotl, 
                sNullInv, sPayMeth, sDRCCode, sXXX1, sXXX2, sXXX3));
        //Sets the new Invoice number in the corresponding label
        this.jlblInvNum.setText("FACTURA: " + sLastInvoNum);
        this.jlblLstInv.setText("Última Factura: " + sLastInvoNum);
    }
    //</editor-fold> 
    
    //Cancels an exisiting Invoice from the Data Base
    private void cancelInvoice(int iPos){
        //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        alInvoDB.get(iPos).setItAUnit("NA");
        alInvoDB.get(iPos).setItATotl("ANULADA");
        alInvoDB.get(iPos).setItBUnit("NA");
        alInvoDB.get(iPos).setItBTotl("ANULADA");
        alInvoDB.get(iPos).setItCUnit("NA");
        alInvoDB.get(iPos).setItCTotl("ANULADA");
        alInvoDB.get(iPos).setItDUnit("NA");
        alInvoDB.get(iPos).setItDTotl("ANULADA");
        alInvoDB.get(iPos).setInvDesc("NA");
        alInvoDB.get(iPos).setIescVal("NA");
        alInvoDB.get(iPos).setInvTaxe("NA");
        alInvoDB.get(iPos).setTaxeVal("NA");
        alInvoDB.get(iPos).setSubTotl("0");
        alInvoDB.get(iPos).setSubtTax("0");
        alInvoDB.get(iPos).setSubtDes("0");
        alInvoDB.get(iPos).setInvTotl("0");
        alInvoDB.get(iPos).setNullInv("TRUE");
        //tmp.setDRCCode(String sDRCCode) {this.sDRCCode = sDRCCode;}
    
    }
    //</editor-fold> 
    
    //Loads the data from an exisiting Invoice into the main screen
    private void loadInvoice(int iPos){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code"> 
        if ( alInvoDB.get(iPos).getNullInv().equals("TRUE") ){
            this.jlblCoLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Nulo_Mid.fw.png")));
        }
        else{
            this.jlblCoLogo.setIcon(null);
        }
    
        this.jlblInvNum.setText("FACTURA: " + this.alInvoDB.get(iPos).getInvNumb());
        this.jtxtDat.setText(alInvoDB.get(iPos).getInvDate());
        this.jtxtStuNam.setText(alInvoDB.get(iPos).getCusName());
        this.jtxtStuPIN.setText(alInvoDB.get(iPos).getCustPIN());
        if( alInvoDB.get(iPos).getVenName().equals("NA") ){this.jtxtVenNam.setText("");} else{this.jtxtVenNam.setText(alInvoDB.get(iPos).getVenName());}
        this.jtxtStuLev.setText(alInvoDB.get(iPos).getCusLevl());
        this.jtxtPhone.setText(alInvoDB.get(iPos).getCusPhon());
        this.jtxtMail.setText(alInvoDB.get(iPos).getCusMail());
        this.jcboxDRC.add(alInvoDB.get(iPos).getDRCCode());
        if( alInvoDB.get(iPos).getItACode().equals("NA") ){this.jtxtA1.setText("");} else{this.jtxtA1.setText(alInvoDB.get(iPos).getItACode());}
        if( alInvoDB.get(iPos).getItA_Qty().equals("NA") ){this.jtxtA2.setText("");} else{this.jtxtA2.setText(alInvoDB.get(iPos).getItA_Qty());}
        if( alInvoDB.get(iPos).getItADesc().equals("NA") ){this.jtxtA3.setText("");} else{this.jtxtA3.setText(alInvoDB.get(iPos).getItADesc());}
        if( alInvoDB.get(iPos).getItAUnit().equals("NA") ){this.jtxtA4.setText("");} else{this.jtxtA4.setText(alInvoDB.get(iPos).getItAUnit());}
        if( alInvoDB.get(iPos).getItATotl().equals("NA") ){this.jtxtA5.setText("");} else{this.jtxtA5.setText(alInvoDB.get(iPos).getItATotl());}
        if( alInvoDB.get(iPos).getItBCode().equals("NA") ){this.jtxtB1.setText("");} else{this.jtxtB1.setText(alInvoDB.get(iPos).getItBCode());}
        if( alInvoDB.get(iPos).getItB_Qty().equals("NA") ){this.jtxtB2.setText("");} else{this.jtxtB2.setText(alInvoDB.get(iPos).getItB_Qty());}
        if( alInvoDB.get(iPos).getItBDesc().equals("NA") ){this.jtxtB3.setText("");} else{this.jtxtB3.setText(alInvoDB.get(iPos).getItBDesc());}
        if( alInvoDB.get(iPos).getItBUnit().equals("NA") ){this.jtxtB4.setText("");} else{this.jtxtB4.setText(alInvoDB.get(iPos).getItBUnit());}
        if( alInvoDB.get(iPos).getItBTotl().equals("NA") ){this.jtxtB5.setText("");} else{this.jtxtB5.setText(alInvoDB.get(iPos).getItBTotl());}
        if( alInvoDB.get(iPos).getItCCode().equals("NA") ){this.jtxtC1.setText("");} else{this.jtxtC1.setText(alInvoDB.get(iPos).getItCCode());}
        if( alInvoDB.get(iPos).getItC_Qty().equals("NA") ){this.jtxtC2.setText("");} else{this.jtxtC2.setText(alInvoDB.get(iPos).getItC_Qty());}
        if( alInvoDB.get(iPos).getItCDesc().equals("NA") ){this.jtxtC3.setText("");} else{this.jtxtC3.setText(alInvoDB.get(iPos).getItCDesc());}
        if( alInvoDB.get(iPos).getItCUnit().equals("NA") ){this.jtxtC4.setText("");} else{this.jtxtC4.setText(alInvoDB.get(iPos).getItCUnit());}
        if( alInvoDB.get(iPos).getItCTotl().equals("NA") ){this.jtxtC5.setText("");} else{this.jtxtC5.setText(alInvoDB.get(iPos).getItCTotl());}
        if( alInvoDB.get(iPos).getItDCode().equals("NA") ){this.jtxtD1.setText("");} else{this.jtxtD1.setText(alInvoDB.get(iPos).getItDCode());}
        if( alInvoDB.get(iPos).getItD_Qty().equals("NA") ){this.jtxtD2.setText("");} else{this.jtxtD2.setText(alInvoDB.get(iPos).getItD_Qty());}
        if( alInvoDB.get(iPos).getItDDesc().equals("NA") ){this.jtxtD3.setText("");} else{this.jtxtD3.setText(alInvoDB.get(iPos).getItDDesc());}
        if( alInvoDB.get(iPos).getItDUnit().equals("NA") ){this.jtxtD4.setText("");} else{this.jtxtD4.setText(alInvoDB.get(iPos).getItDUnit());}
        if( alInvoDB.get(iPos).getItDTotl().equals("NA") ){this.jtxtD5.setText("");} else{this.jtxtD5.setText(alInvoDB.get(iPos).getItDTotl());}
        if( alInvoDB.get(iPos).getInvDesc().equals("NA") ){this.jtxtDesPer.setText("");} else{this.jtxtDesPer.setText(alInvoDB.get(iPos).getInvDesc());}
        if( alInvoDB.get(iPos).getDescVal().equals("NA") ){this.jtxtDesVal.setText("");} else{this.jtxtDesVal.setText(alInvoDB.get(iPos).getDescVal());}
        if( alInvoDB.get(iPos).getInvTaxe().equals("NA") ){this.jtxtTaxPer.setText("");} else{this.jtxtTaxPer.setText(alInvoDB.get(iPos).getInvTaxe());}
        if( alInvoDB.get(iPos).getTaxeVal().equals("NA") ){this.jtxtTaxVal.setText("");} else{this.jtxtTaxVal.setText(alInvoDB.get(iPos).getTaxeVal());}
        if( alInvoDB.get(iPos).getSubTotl().equals("NA") ){this.jtxtSubTot.setText("");} else{this.jtxtSubTot.setText(alInvoDB.get(iPos).getSubTotl());}
        if( alInvoDB.get(iPos).getSubtDes().equals("NA") ){this.jtxtSubDes.setText("");} else{this.jtxtSubDes.setText(alInvoDB.get(iPos).getSubtDes());}
        if( alInvoDB.get(iPos).getSubtTax().equals("NA") ){this.jtxtSubTax.setText("");} else{this.jtxtSubTax.setText(alInvoDB.get(iPos).getSubtTax());}
        if( alInvoDB.get(iPos).getInvTotl().equals("NA") ){this.jtxtTotal.setText("");} else{this.jtxtTotal.setText(alInvoDB.get(iPos).getInvTotl());}
        this.jcboxMode.select(alInvoDB.get(iPos).getModName());
        this.jcboxDay.select(alInvoDB.get(iPos).getDayName());
        this.jcboxPaq.select(alInvoDB.get(iPos).getPaqName());
        this.jcboxHour.select(alInvoDB.get(iPos).getSchName());
        String sPayMeth = alInvoDB.get(iPos).getPayMeth();
        switch ( sPayMeth ){
            case "Efectivo":{this.jrbtnEfec.setSelected(true); break;}
            case "Tarjeta":{this.jrbtnTarj.setSelected(true); break;}
            case "Transferencia":{this.jrbtnTran.setSelected(true); break;}
            case "Cargo automático":{this.jrbtnAuto.setSelected(true); break;}
        }
        this.jbtnNullInv.setEnabled(true);
        this.jtxtA1.setEditable(false);
        this.jtxtA2.setEditable(false);
        this.jtxtA3.setEditable(false);
        this.jtxtA4.setEditable(false);
        this.jtxtB1.setEditable(false);
        this.jtxtB2.setEditable(false);
        this.jtxtB3.setEditable(false);
        this.jtxtB4.setEditable(false);
        this.jtxtC1.setEditable(false);
        this.jtxtC2.setEditable(false);
        this.jtxtC3.setEditable(false);
        this.jtxtC4.setEditable(false);
        this.jtxtD1.setEditable(false);
        this.jtxtD2.setEditable(false);
        this.jtxtD3.setEditable(false);
        this.jtxtD4.setEditable(false);
        this.jcboxMode.setEnabled(false);
        this.jcboxDay.setEnabled(false);
        this.jcboxPaq.setEnabled(false);
        this.jcboxHour.setEnabled(false);
        this.jcboxDRC.setEnabled(false);
        this.jbtnAddDRC.setEnabled(false);
        this.jbtnGen.setEnabled(false);
        this.jtxtVenNam.setEditable(false);
        this.jtxtDesPer.setEditable(false);
        this.jtxtTaxPer.setEditable(false);
        this.jchkboxManual.setState(false);
        this.jchkboxMemb.setState(false);
        this.jchkboxManual.setEnabled(false);
        this.jchkboxMemb.setEnabled(false);
        this.jrbtnEfec.setEnabled(false);
        this.jrbtnTarj.setEnabled(false);
        this.jrbtnTran.setEnabled(false);
        this.jrbtnAuto.setEnabled(false);
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
    
    //Loads the Invoice according with the information provided in the DRC
    private void loadDRCData(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Decodes the selected DRC from the dropdown list
        decodeDRC(jcboxDRC.getSelectedItem());
        //Updates the amount of chargeable lessons depending on the previous payed lessons
        int iLessons = Integer.valueOf(this.sDRCRes) - Integer.valueOf(this.sDRCPay);
        //With the global variables fulfilled, proceeds to setup the values on the screen
        this.jcboxMode.select(sDRCMod);
        this.jcboxDay.select(sDRCDay);
        this.jcboxPaq.select(sDRCPaq);
        this.jcboxHour.select(sDRCHrs);
        //Checks if the 1st line is popualted or not
        if ( jtxtA2.getText().equals("") && jtxtA3.getText().equals("") ){
            //Adds Prof PIN in the Code field and the pending reserved lessons in the QTY field
            this.jtxtA1.setText(sDRCTPn);
            this.jtxtA2.setText(String.valueOf(iLessons));
            this.jtxtA3.setText(getLessonDesc());
            this.jtxtA4.setText(getLessonCost(getProfLine(jtxtA1.getText())));
        }
        else{
            //Checks if the 2nd line is popualted or not
            if ( jtxtB2.getText().equals("") && jtxtB3.getText().equals("") ){
                //Adds Prof PIN in the Code field and the pending reserved lessons in the QTY field
                this.jtxtB1.setText(sDRCTPn);
                this.jtxtB2.setText(String.valueOf(iLessons));
                this.jtxtB3.setText(getLessonDesc());
                this.jtxtB4.setText(getLessonCost(getProfLine(jtxtB1.getText())));
            }
            else{
                //Checks if the 3rd line is popualted or not
                if ( jtxtC2.getText().equals("") && jtxtC3.getText().equals("") ){
                    //Adds Prof PIN in the Code field and the pending reserved lessons in the QTY field
                    this.jtxtC1.setText(sDRCTPn);
                    this.jtxtC2.setText(String.valueOf(iLessons));
                    this.jtxtC3.setText(getLessonDesc());
                    this.jtxtC4.setText(getLessonCost(getProfLine(jtxtC1.getText())));
                }
                else{
                    //Checks if the 4th line is popualted or not
                    if ( jtxtD2.getText().equals("") && jtxtD3.getText().equals("") ){
                        //Adds Prof PIN in the Code field and the pending reserved lessons in the QTY field
                        this.jtxtD1.setText(sDRCTPn);
                        this.jtxtD2.setText(String.valueOf(iLessons));
                        this.jtxtD3.setText(getLessonDesc());
                        this.jtxtD4.setText(getLessonCost(getProfLine(jtxtD1.getText())));
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "FIN DE LÍNEA: No puede agregarse una línea adicional");
                    }
                }
            }
        }
    }
    //</editor-fold>
    
    //Updates the amount of payed lessons in the DRC
    private String updateDRCPayLes(String sDRC, String sPayLes){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sUpdatedDRC = "";
        String sPart1 = sDRC.substring(0, 32);
        String sPart2 = sDRC.substring(32,35);
        String sPart3 = sDRC.substring(35, 42);
        //Prepares the new amount of payed hours
        int iNewQty = Integer.valueOf(sPart2) + Integer.valueOf(sPayLes);
        String sNBewQty = String.valueOf(iNewQty);
        sPart2 = formatDigits(sNBewQty);
        sUpdatedDRC = sPart1 + sPart2 + sPart3;
        return sUpdatedDRC;
    }
    //</editor-fold>
    
    //Gets the membership according with the screen date
    private String getMembershipCost(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        System.out.println("Obtaining membership cost");
        double dMemb = 0;
        String sMemb = "";
        String sMont = jtxtDat.getText().substring(3, 6);
        System.out.println("Month received: " + sMont);
        //Converts Month to spanish of necessary
        sMont = spanishMonth(sMont).toUpperCase();
        System.out.println("Converted Month in spanish: " + sMont);
        //Converts the Month to the whole word 
        switch ( sMont ){
            case "ENE" : {sMont = "ENERO"; break;}
            case "FEB" : {sMont = "FEBRERO"; break;}
            case "MAR" : {sMont = "MARZO"; break;}
            case "ABR" : {sMont = "ABRIL"; break;}
            case "MAY" : {sMont = "MAYO"; break;}
            case "JUN" : {sMont = "JUNIO"; break;}
            case "JUL" : {sMont = "JULIO"; break;}
            case "AGO" : {sMont = "AGOSTO"; break;}
            case "SEP" : {sMont = "SEPTIEMBRE"; break;}
            case "OCT" : {sMont = "OCTUBRE"; break;}
            case "NOV" : {sMont = "NOVIEMBRE"; break;}
            case "DIC" : {sMont = "DICIEMBRE"; break;}
        }
        System.out.println("Month full name: " + sMont);
        //Looks for the MOnth into the Membership costs 2-D Array and provided its corresponding Cost
        for ( int i=0; i<12; i++ ){
            if ( arrMemb[i][0].toUpperCase().equals(sMont) ){
                //Obtains the String value and converts it to Double. The operation is just to provide a Double with two decs
                dMemb = roundValue(Double.valueOf(arrMemb[i][1]) * 1);
                System.out.println("Found Membership in DB: " + dMemb);
                sMemb = String.valueOf(dMemb);
            }
        }
        return sMemb;
    }
    //</editor-fold>
    
    //Loads a Line in the invoice dpending on the given Code and the affected invoice row
    //FIX UPPER CASE MEMB BUG
    //FIX 2ND TO 4TH LINES LOADING INVOICE ISSUE
    private void loadInvoiceLine(String sCode, int iRow){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        //Analyses the Code only if it has 4 chars
        if ( sCode.length() == 4 ){
            //Checks if the String corresponds to a potential Teacher PIN
            if ( sCode.substring(0,1).toUpperCase().equals("P") ){
                System.out.println("Row received: " + iRow);
                switch (iRow){
                    case 1 : {
                        jtxtA3.setText(getLessonDesc());
                        jtxtA4.setText(getLessonCost(getProfLine(sCode)));
                        break;
                    }
                    case 2 : {
                        jtxtB3.setText(getLessonDesc());
                        jtxtB4.setText(getLessonCost(getProfLine(sCode)));
                        break;
                    }
                    case 3 : {
                        jtxtC3.setText(getLessonDesc());
                        jtxtC4.setText(getLessonCost(getProfLine(sCode)));
                        break;
                    }
                    case 4 : {
                        jtxtD3.setText(getLessonDesc());
                        jtxtD4.setText(getLessonCost(getProfLine(sCode)));
                        break;
                    }
                }
            }
            else{
                //Checks if the Code corresponds to the Membership code
                System.out.println(" Code: " + sCode.toUpperCase());
                if ( sCode.toUpperCase().equals("MEMB") ){
                    switch (iRow){
                        case 1 : {
                            jtxtA2.setText("1");
                            jtxtA3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                            jtxtA4.setText(getMembershipCost());
                            break;
                        }
                        case 2 : {
                            jtxtB2.setText("1");
                            jtxtB3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                            jtxtB4.setText(getMembershipCost());
                            break;
                        }
                        case 3 : {
                            jtxtC2.setText("1");
                            jtxtC3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                            jtxtC4.setText(getMembershipCost());
                            break;
                        }
                        case 4 : {
                            jtxtD2.setText("1");
                            jtxtD3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                            jtxtD4.setText(getMembershipCost());
                            break;
                        }
                    }
                }
            }
        }
        else{
            switch (iRow){
                case 1 : {
                    jtxtA2.setText("");
                    jtxtA3.setText("");
                    jtxtA4.setText("");
                    jtxtA5.setText("");
                    break;
                }
                case 2 : {
                    jtxtB2.setText("");
                    jtxtB3.setText("");
                    jtxtB4.setText("");
                    jtxtB5.setText("");
                    break;
                }
                case 3 : {
                    jtxtC2.setText("");
                    jtxtC3.setText("");
                    jtxtC4.setText("");
                    jtxtC5.setText("");
                    break;
                }
                case 4 : {
                    jtxtD2.setText("");
                    jtxtD3.setText("");
                    jtxtD4.setText("");
                    jtxtD5.setText("");
                    break;
                }
            }
        }
    }
    //</editor-fold>
        
    //Gets the list of reservation under a given Student and shows them into the Invoice Screen DRC List
    private void getReservations(String sReserves){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Cleans the choice menu
        jcboxDRC.removeAll();
        if ( sReserves.equals("NA") || sReserves.equals("") ){
            jcboxDRC.addItem("Sin Reservas");
        }
        else{//If there are reservations...
            jcboxDRC.addItem("Reservas encontradas");
            int i = 0;
            String sTmpDRC;
            do{
                sTmpDRC = "";
                do{
                    sTmpDRC = sTmpDRC + sReserves.charAt(i);
                    i++;
                }
                while( sReserves.charAt(i) != '>' );
                jcboxDRC.addItem(sTmpDRC);
                i++;
            }
            while( i < sReserves.length() );
        }
    }
    //</editor-fold> 
    
    //Updates the QTY of PAYED lessons under a given Teacher depending on a newly created Invoice
    //Calculates the amount of the Teacher's salary depending on the costs per lesson
    //Updates the Teacher ArrayList with the payed hours and accumulatd salary
    private void updateProfPayLes(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Checks line by line in the invoice and gets all the possible lesson values listed
        int iLesA = 0,iLesB = 0, iLesC = 0, iLesD = 0;
        if ( this.jtxtA1.getText().contains("P0") ){iLesA = Integer.valueOf(jtxtA2.getText());}
        if ( this.jtxtB1.getText().contains("P0") ){iLesB = Integer.valueOf(jtxtB2.getText());}
        if ( this.jtxtC1.getText().contains("P0") ){iLesC = Integer.valueOf(jtxtC2.getText());}
        if ( this.jtxtD1.getText().contains("P0") ){iLesD = Integer.valueOf(jtxtD2.getText());}
        //Summarizes the total lessons found
        int iLesTot = iLesA + iLesB + iLesC + iLesD;
        String sResLes = "";
        //Looks for the Teacher depending on his/her PIN
        int iPos = getProfLine(this.sDRCTPn);
        //Updates the amount of payed lessons with the data taken from this invoice
        int iNewLesQty = Integer.valueOf(alProfDB.get(iPos).getPayClas()) + iLesTot;
        String sNewLesQty = String.valueOf(iNewLesQty);
        alProfDB.get(iPos).setPayClas(sNewLesQty);
        //Checks line by line in the invoice and gets all the possible lesson costs payed
        double dCostA = 0,dCostB = 0, dCostC = 0, dCostD = 0;
        if ( this.jtxtA1.getText().toUpperCase().equals(alProfDB.get(iPos).getProfPIN()) ){dCostA = Double.valueOf(jtxtA5.getText());}
        if ( this.jtxtB1.getText().toUpperCase().equals(alProfDB.get(iPos).getProfPIN()) ){dCostB = Double.valueOf(jtxtB5.getText());}
        if ( this.jtxtC1.getText().toUpperCase().equals(alProfDB.get(iPos).getProfPIN()) ){dCostC = Double.valueOf(jtxtC5.getText());}
        if ( this.jtxtD1.getText().toUpperCase().equals(alProfDB.get(iPos).getProfPIN()) ){dCostD = Double.valueOf(jtxtD5.getText());}
        //Calculats the amount of Salary depending on the lessons and commision pencentaje
        double dPaymnt = dCostA + dCostB + dCostC + dCostD;
        double dComPer = Double.valueOf(alProfDB.get(iPos).getComPerc()) / 100;
        double dSalary = roundValue(dPaymnt * dComPer);
        //Calculates the total accumulated
        double dAccuml = roundValue(Double.valueOf(alProfDB.get(iPos).getSalary()) + dSalary);
        //Updates the Teacher's data with the total accumulated
        String sAccuml = String.valueOf(dAccuml);
        alProfDB.get(iPos).setSalary(sAccuml);
    }
    //</editor-fold>
    
    //Updates the QTY of payed lessons under a Teacher depending on a newly created Invoice
    private void updateStudPayedLessons(String sStudName, String sProfName){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Checks line by line in the invoice and gets all the possible lesson values listed
        int iLesA = 0,iLesB = 0, iLesC = 0, iLesD = 0;
        if ( this.jtxtA1.getText().equals("LECC001") ){iLesA = Integer.valueOf(jtxtA2.getText());}
        if ( this.jtxtB1.getText().equals("LECC001") ){iLesB = Integer.valueOf(jtxtB2.getText());}
        if ( this.jtxtC1.getText().equals("LECC001") ){iLesC = Integer.valueOf(jtxtC2.getText());}
        if ( this.jtxtD1.getText().equals("LECC001") ){iLesD = Integer.valueOf(jtxtD2.getText());}
        //Summarizes the total lessons found
        int iLesTot = iLesA + iLesB + iLesC + iLesD;
        String sResLes = "";
        for ( int i=0; i<this.alProfDB.size(); i++ ){
            if ( alProfDB.get(i).getStuList().contains(sStudName) && (alProfDB.get(i).getFstName() + " " + alProfDB.get(i).getLstName()).equals(sProfName) ){
                //Saves the current amount of reserved lessons
                sResLes = alProfDB.get(i).getResClas();
                //Adds the lessons on the new Invoice
                sResLes = String.valueOf(Integer.valueOf(sResLes) + iLesTot);
                //Updates the Teachers ArrayList with the new QTY of reserved lessons
                alProfDB.get(i).setResClas(sResLes);
            }
        }
    }
    //</editor-fold>
    
    //Updates the QTY of PAYED lessons for the Student ArrayList data depending on the newly created Invoice
    //Updates the payed lessons on this invoice in the corresponding DRC (creates a new DRC)
    //Updates the DRC in the Student ArrayList Data Base
    private void updateStudPayLes(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Checks if there is a DRC selected
        if ( !jcboxDRC.getSelectedItem().equals("Reservas encontradas") && !jcboxDRC.getSelectedItem().equals("Sin Reservas") ){
            //Decodes the DRC
            decodeDRC(jcboxDRC.getSelectedItem());
            //Checks line by line in the invoice and gets all the possible lesson values listed
            int iLesA = 0,iLesB = 0, iLesC = 0, iLesD = 0;
            if ( this.jtxtA1.getText().contains("P0") ){iLesA = Integer.valueOf(jtxtA2.getText());}
            if ( this.jtxtB1.getText().contains("P0") ){iLesB = Integer.valueOf(jtxtB2.getText());}
            if ( this.jtxtC1.getText().contains("P0") ){iLesC = Integer.valueOf(jtxtC2.getText());}
            if ( this.jtxtD1.getText().contains("P0") ){iLesD = Integer.valueOf(jtxtD2.getText());}
            //Summarizes the total lessons found
            int iLesTot = iLesA + iLesB + iLesC + iLesD;
            System.out.println("Lessons: " + iLesTot);
            //Looks for the Student depending on his/her PIN
            int iPos = getStudLine(this.sDRCSPn);
            //Updates the amount of payed lessons with the data taken from this invoice
            int iNewLesQty = Integer.valueOf(alStudDB.get(iPos).getPayClas()) + iLesTot;
            String sNewLesQty = String.valueOf(iNewLesQty);
            alStudDB.get(iPos).setPayClas(sNewLesQty);
            //Updates amount of Payed Lessons on the corresponding DRC
            String newDRC = updateDRCPayLes(jcboxDRC.getSelectedItem(), String.valueOf(iLesTot));
            alStudDB.get(iPos).setReservs(alStudDB.get(iPos).getReservs().replaceFirst(jcboxDRC.getSelectedItem(), newDRC));
        }
    }
    //</editor-fold>
    
    //Updates the QTY of PAYED lessons for the Teacher ArrayList data depending on the newly created Invoice
    private void updateTeaPayLes(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Checks if there is a DRC selected
        if ( !jcboxDRC.getSelectedItem().equals("Reservas encontradas") && !jcboxDRC.getSelectedItem().equals("Sin Reservas") ){
            //Decodes the DRC
            decodeDRC(jcboxDRC.getSelectedItem());
            //Checks line by line in the invoice and gets all the possible lesson values listed
            int iLesA = 0,iLesB = 0, iLesC = 0, iLesD = 0;
            if ( this.jtxtA1.getText().contains("P0") ){iLesA = Integer.valueOf(jtxtA2.getText());}
            if ( this.jtxtB1.getText().contains("P0") ){iLesB = Integer.valueOf(jtxtB2.getText());}
            if ( this.jtxtC1.getText().contains("P0") ){iLesC = Integer.valueOf(jtxtC2.getText());}
            if ( this.jtxtD1.getText().contains("P0") ){iLesD = Integer.valueOf(jtxtD2.getText());}
            //Summarizes the total lessons found
            int iLesTot = iLesA + iLesB + iLesC + iLesD;
            System.out.println("Lessons: " + iLesTot);
            //Looks for the Teacher depending on his/her PIN
            int iPos = getProfLine(this.sDRCTPn);
            //Updates the amount of payed lessons with the data taken from this invoice
            int iNewLesQty = Integer.valueOf(alProfDB.get(iPos).getPayClas()) + iLesTot;
            String sNewLesQty = String.valueOf(iNewLesQty);
            alProfDB.get(iPos).setPayClas(sNewLesQty);
        }
    }
    //</editor-fold>
    
    
    
    //Gets the lesson description deoending on the screen drop down list values
    private String getLessonDesc(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        String sDesc = "";
        sDesc = this.jcboxMode.getSelectedItem().toUpperCase() + " / "
                + this.jcboxPaq.getSelectedItem().toUpperCase() + " / "
                + this.jcboxDay.getSelectedItem().toUpperCase() + ", "
                + this.jcboxHour.getSelectedItem().toUpperCase();
        return sDesc;
    }
    //</editor-fold>
    
    //Gets the lesson cost for a given Student depending on the Assigned Teacher
    private String getLessonCost(int iPos){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        System.out.println("Getting Lesson cost\nTeacher´s Pos: " + iPos);
        //The mehtod receives the Teacher's line location into the Array so, it gets his Category
        String sCat = this.alProfDB.get(iPos).getCategry();
        //Prepares a variable for the Cost
        String sLesCos = "";
        //Build a code to easily identify the final cost for the lesson
        String sCode = "";
        switch ( sCat ){
            case "Nivel 1":{
                sCode = sCode + "N1-";
                break;
            }
            case "Nivel 2":{
                sCode = sCode + "N2-";
                break;
            }
            case "Head Pro":{
                sCode = sCode + "HP-";
                break;
            }
        }
        switch ( this.jcboxPaq.getSelectedItem() ){
            case "Individual":{
                sCode = sCode + "Indiv";
                break;
            }
            case "Grupal 2P":{
                sCode = sCode + "Grup2";
                break;
            }
            case "Grupal 3P":{
                sCode = sCode + "Grup3";
                break;
            }
            case "Grupal 4P":{
                sCode = sCode + "Grup4";
                break;
            }
            case "Grupal 5+":{
                sCode = sCode + "Grup+";
                break;
            }
        }
        System.out.println("Identifier generated: " + sCode);
        
        //Checks if the selected Mode is Mini Tennis
        if ( jcboxMode.getSelectedItem().equals("Mini Tennis") ){
            for ( int i=0; i<4; i++ ){
                if ( this.alCostDB.get(i).getSchdLV().equals(this.jcboxHour.getSelectedItem()) ){
                    switch ( sCode ){
                        case "N1-Indiv":{
                            sLesCos = alCostDB.get(i).getN1InMT();
                            break;
                        }
                        case "N1-Grup2":{
                            sLesCos = alCostDB.get(i).getN1G2MT();
                            break;
                        }
                        case "N1-Grup3":{
                            sLesCos = alCostDB.get(i).getN1G3MT();
                            break;
                        }
                        case "N1-Grup4":{
                            sLesCos = alCostDB.get(i).getN1G4MT();
                            break;
                        }
                        case "N2-Indiv":{
                            sLesCos = alCostDB.get(i).getN2InMT();
                            break;
                        }
                        case "N2-Grup2":{
                            sLesCos = alCostDB.get(i).getN2G2MT();
                            break;
                        }
                        case "N2-Grup3":{
                            sLesCos = alCostDB.get(i).getN2G3MT();
                            break;
                        }
                        case "N2-Grup4":{
                            sLesCos = alCostDB.get(i).getN2G4MT();
                            break;
                        }
                        case "HP-Indiv":{
                            sLesCos = alCostDB.get(i).getHPInMT();
                            break;
                        }
                        case "HP-Grup2":{
                            sLesCos = alCostDB.get(i).getHPG2MT();
                            break;
                        }
                        case "HP-Grup3":{
                            sLesCos = alCostDB.get(i).getHPG3MT();
                            break;
                        }
                        case "HP-Grup4":{
                            sLesCos = alCostDB.get(i).getHPG4MT();
                            break;
                        }
                    }
                } 
            }
        }
        else{
            //Starts the process depending on the selected day
            //It looks for the price on one or the other table
            switch ( this.jcboxDay.getSelectedItem() ){
                case "L-V":{
                    for ( int i=0; i<4; i++ ){
                        if ( this.alCostDB.get(i).getSchdLV().equals(this.jcboxHour.getSelectedItem()) ){
                            switch ( sCode ){
                                case "N1-Indiv":{
                                    sLesCos = alCostDB.get(i).getN1InLV();
                                    break;
                                }
                                case "N1-Grup2":{
                                    sLesCos = alCostDB.get(i).getN1G2LV();
                                    break;
                                }
                                case "N1-Grup3":{
                                    sLesCos = alCostDB.get(i).getN1G3LV();
                                    break;
                                }
                                case "N1-Grup4":{
                                    sLesCos = alCostDB.get(i).getN1G4LV();
                                    break;
                                }
                                case "N2-Indiv":{
                                    sLesCos = alCostDB.get(i).getN2InLV();
                                    break;
                                }
                                case "N2-Grup2":{
                                    sLesCos = alCostDB.get(i).getN2G2LV();
                                    break;
                                }
                                case "N2-Grup3":{
                                    sLesCos = alCostDB.get(i).getN2G3LV();
                                    break;
                                }
                                case "N2-Grup4":{
                                    sLesCos = alCostDB.get(i).getN2G4LV();
                                    break;
                                }
                                case "HP-Indiv":{
                                    sLesCos = alCostDB.get(i).getHPInLV();
                                    break;
                                }
                                case "HP-Grup2":{
                                    sLesCos = alCostDB.get(i).getHPG2LV();
                                    break;
                                }
                                case "HP-Grup3":{
                                    sLesCos = alCostDB.get(i).getHPG3LV();
                                    break;
                                }
                                case "HP-Grup4":{
                                    sLesCos = alCostDB.get(i).getHPG4LV();
                                    break;
                                }
                            }
                        } 
                    }
                    break;
                }
                case "S-D":{
                    for ( int i=0; i<4; i++ ){
                        if ( this.alCostDB.get(i).getSchdLV().equals(this.jcboxHour.getSelectedItem()) ){
                            switch ( sCode ){
                                case "N1-Indiv":{
                                    sLesCos = alCostDB.get(i).getN1InFS();
                                    break;
                                }
                                case "N1-Grup2":{
                                    sLesCos = alCostDB.get(i).getN1G2FS();
                                    break;
                                }
                                case "N1-Grup3":{
                                    sLesCos = alCostDB.get(i).getN1G3FS();
                                    break;
                                }
                                case "N1-Grup4":{
                                    sLesCos = alCostDB.get(i).getN1G4FS();
                                    break;
                                }
                                case "N2-Indiv":{
                                    sLesCos = alCostDB.get(i).getN2InFS();
                                    break;
                                }
                                case "N2-Grup2":{
                                    sLesCos = alCostDB.get(i).getN2G2FS();
                                    break;
                                }
                                case "N2-Grup3":{
                                    sLesCos = alCostDB.get(i).getN2G3FS();
                                    break;
                                }
                                case "N2-Grup4":{
                                    sLesCos = alCostDB.get(i).getN2G4FS();
                                    break;
                                }
                                case "HP-Indiv":{
                                    sLesCos = alCostDB.get(i).getHPInFS();
                                    break;
                                }
                                case "HP-Grup2":{
                                    sLesCos = alCostDB.get(i).getHPG2FS();
                                    break;
                                }
                                case "HP-Grup3":{
                                    sLesCos = alCostDB.get(i).getHPG3FS();
                                    break;
                                }
                                case "HP-Grup4":{
                                    sLesCos = alCostDB.get(i).getHPG4FS();
                                    break;
                                }
                            }
                        } 
                    }
                    break;
                }
            }
        }
        sLesCos = String.valueOf(roundValue(Double.valueOf(sLesCos)));
        return sLesCos;
    }
    //</editor-fold>
    
    //Checks if there is calc condition before proceeding calculating each Line total
    private boolean checkCalcCondition(int iRow){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        boolean bFlag = false;
        switch (iRow){
            case 1:{
                if ( !jtxtA2.getText().equals("") && !jtxtA4.getText().equals("") ){bFlag = true;}
                break;
            }
            case 2:{
                if ( !jtxtB2.getText().equals("") && !jtxtB4.getText().equals("") ){bFlag = true;}
                break;
            }
            case 3:{
                if ( !jtxtC2.getText().equals("") && !jtxtC4.getText().equals("") ){bFlag = true;}
                break;
            }
            case 4:{
                if ( !jtxtD2.getText().equals("") && !jtxtD4.getText().equals("") ){bFlag = true;}
                break;
            }
        }
        return bFlag;
    }
    //</editor-fold>
    
    //Calculates each line # total in the Invoice, depending on the given QTY and Value 
    private void calcLineTotal(int iRow){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">  
        double dQTY = 0;
        double dVAL = 0;
        double dRes = 0;
        double dTot = 0;
        if ( !jtxtA2.getText().equals("") && !jtxtA4.getText().equals("") ){
            switch (iRow){
            case 1:{
                    dQTY = Double.valueOf(jtxtA2.getText());
                    dVAL = Double.valueOf(jtxtA4.getText());
                    dRes = dQTY * dVAL;
                    jtxtA5.setText(String.valueOf(dRes));
                    
                    break;
                }
                case 2:{
                    dQTY = Double.valueOf(jtxtB2.getText());
                    dVAL = Double.valueOf(jtxtB4.getText());
                    dRes = dQTY * dVAL;
                    jtxtB5.setText(String.valueOf(dRes));
                    break;
                }
                case 3:{
                    dQTY = Double.valueOf(jtxtC2.getText());
                    dVAL = Double.valueOf(jtxtC4.getText());
                    dRes = dQTY * dVAL;
                    jtxtC5.setText(String.valueOf(dRes));
                    break;
                }
                case 4:{
                    dQTY = Double.valueOf(jtxtD2.getText());
                    dVAL = Double.valueOf(jtxtD4.getText());
                    dRes = dQTY * dVAL;
                    jtxtD5.setText(String.valueOf(dRes));
                    break;
                }
            }
        }
    }
    //</editor-fold>  
    
    //Calculates the Invoice subtotals
    private void calcSubTotal(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">    
        //Prepares the variables
        String sValA="0", sValB="0", sValC="0", sValD="0";
        double dRes=0;
        double dTax=0;
        double dDes=0;
        //Validates the fields
        if ( !jtxtA5.getText().equals("") ){sValA = jtxtA5.getText();}
        if ( !jtxtB5.getText().equals("") ){sValB = jtxtB5.getText();}
        if ( !jtxtC5.getText().equals("") ){sValC = jtxtC5.getText();}
        if ( !jtxtD5.getText().equals("") ){sValD = jtxtD5.getText();}
        dRes = roundValue(Double.valueOf(sValA) + Double.valueOf(sValB) + Double.valueOf(sValC) + Double.valueOf(sValD));
        this.jtxtSubTot.setText(String.valueOf(dRes));
        if ( !jtxtTaxPer.getText().equals("") && validateRealNumber(jtxtTaxPer.getText()) == true ){
            dTax = roundValue(dRes * ((Double.valueOf(jtxtTaxPer.getText()))/100));
            dRes = roundValue(dRes + dTax);
            this.jtxtTaxVal.setText(String.valueOf(dTax));
            this.jtxtSubTax.setText(String.valueOf(dRes));
        }
        else{
            this.jtxtTaxVal.setText("");
            this.jtxtSubTax.setText(String.valueOf(dRes));
        }
        if ( !jtxtDesPer.getText().equals("") && validateRealNumber(jtxtDesPer.getText()) == true ){
            dDes = roundValue(Double.valueOf(jtxtSubTax.getText()) * (Double.valueOf(this.jtxtDesPer.getText())/100));
            dRes = roundValue(dRes - dDes);
            this.jtxtDesVal.setText(String.valueOf(dDes));
            this.jtxtSubDes.setText(String.valueOf(dRes));
        }
        else{
            this.jtxtDesVal.setText("");
            this.jtxtSubDes.setText(String.valueOf(dRes));
        }
        this.jtxtTotal.setText(String.valueOf(dRes));
        
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
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtngrpPayMod = new javax.swing.ButtonGroup();
        jPnlTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jpnlMid = new javax.swing.JPanel();
        jTbPnMid = new javax.swing.JTabbedPane();
        jpnlMain = new javax.swing.JPanel();
        jpnlInvTop = new javax.swing.JPanel();
        jlblInvTop = new javax.swing.JLabel();
        jpnlHeader = new javax.swing.JPanel();
        jlblDat = new javax.swing.JLabel();
        jtxtDat = new javax.swing.JTextField();
        jlblStuIde = new javax.swing.JLabel();
        jtxtStuPIN = new javax.swing.JTextField();
        jlblStuNam = new javax.swing.JLabel();
        jtxtStuNam = new javax.swing.JTextField();
        jlblStuLev = new javax.swing.JLabel();
        jtxtStuLev = new javax.swing.JTextField();
        jchkboxManual = new java.awt.Checkbox();
        jchkboxMemb = new java.awt.Checkbox();
        jlblInvNum = new javax.swing.JLabel();
        jlblPhone = new javax.swing.JLabel();
        jtxtPhone = new javax.swing.JTextField();
        jlblVenNam = new javax.swing.JLabel();
        jtxtVenNam = new javax.swing.JTextField();
        jlblMail = new javax.swing.JLabel();
        jtxtMail = new javax.swing.JTextField();
        jlblMode = new javax.swing.JLabel();
        jcboxMode = new java.awt.Choice();
        jcboxPaq = new java.awt.Choice();
        jlblPaq = new javax.swing.JLabel();
        jcboxHour = new java.awt.Choice();
        jlblHour = new javax.swing.JLabel();
        jlblDay = new javax.swing.JLabel();
        jcboxDay = new java.awt.Choice();
        jSeparator10 = new javax.swing.JSeparator();
        jlblCoLogo = new javax.swing.JLabel();
        jbtnAddDRC = new javax.swing.JButton();
        jcboxDRC = new java.awt.Choice();
        jpnlInvMid = new javax.swing.JPanel();
        jtxtT1 = new javax.swing.JTextField();
        jtxtT2 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jtxtT3 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jtxtT4 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jtxtT5 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jtxtA1 = new javax.swing.JTextField();
        jtxtA2 = new javax.swing.JTextField();
        jtxtA3 = new javax.swing.JTextField();
        jtxtA5 = new javax.swing.JTextField();
        jtxtA4 = new javax.swing.JTextField();
        jtxtB1 = new javax.swing.JTextField();
        jtxtB2 = new javax.swing.JTextField();
        jtxtB3 = new javax.swing.JTextField();
        jtxtB5 = new javax.swing.JTextField();
        jtxtB4 = new javax.swing.JTextField();
        jtxtC1 = new javax.swing.JTextField();
        jtxtC2 = new javax.swing.JTextField();
        jtxtC3 = new javax.swing.JTextField();
        jtxtC5 = new javax.swing.JTextField();
        jtxtC4 = new javax.swing.JTextField();
        jtxtD1 = new javax.swing.JTextField();
        jtxtD2 = new javax.swing.JTextField();
        jtxtD3 = new javax.swing.JTextField();
        jtxtD5 = new javax.swing.JTextField();
        jtxtD4 = new javax.swing.JTextField();
        jbtnADel = new javax.swing.JButton();
        jbtnBDel = new javax.swing.JButton();
        jbtnCDel = new javax.swing.JButton();
        jbtnDDel = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JSeparator();
        jpnlInvBottom = new javax.swing.JPanel();
        jlblDesPer = new javax.swing.JLabel();
        jtxtDesPer = new javax.swing.JTextField();
        jlblDesVal = new javax.swing.JLabel();
        jtxtDesVal = new javax.swing.JTextField();
        jlblTaxPer = new javax.swing.JLabel();
        jtxtTaxPer = new javax.swing.JTextField();
        jlblTaxVal = new javax.swing.JLabel();
        jtxtTaxVal = new javax.swing.JTextField();
        jlblSubDes = new javax.swing.JLabel();
        jlblSubImp = new javax.swing.JLabel();
        jtxtSubDes = new javax.swing.JTextField();
        jtxtSubTax = new javax.swing.JTextField();
        jlblTotal = new javax.swing.JLabel();
        jtxtTotal = new javax.swing.JTextField();
        jbtnInvMail = new javax.swing.JButton();
        jtxtSubTot = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jbtnNullInv = new javax.swing.JButton();
        jbtnGen = new javax.swing.JButton();
        jpnlPayMod = new javax.swing.JPanel();
        jrbtnEfec = new javax.swing.JRadioButton();
        jrbtnTarj = new javax.swing.JRadioButton();
        jrbtnTran = new javax.swing.JRadioButton();
        jrbtnAuto = new javax.swing.JRadioButton();
        jPnlProf = new javax.swing.JPanel();
        jPnlProfBot = new javax.swing.JPanel();
        jbtnNewTeach = new javax.swing.JButton();
        jbtnEditTeach = new javax.swing.JButton();
        jbtnDelTeach = new javax.swing.JButton();
        jbtnRefreshTeach = new javax.swing.JButton();
        jlblProfDBsize = new javax.swing.JLabel();
        jbtnProfFind = new javax.swing.JButton();
        jtxtProfFind = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProf = new javax.swing.JTable();
        jPnlStud = new javax.swing.JPanel();
        jPnlStudBot = new javax.swing.JPanel();
        jbtnNewStud = new javax.swing.JButton();
        jbtnDelStud = new javax.swing.JButton();
        jbtnEditStud = new javax.swing.JButton();
        jbtnRefreshStud = new javax.swing.JButton();
        jlblStudDBsize = new javax.swing.JLabel();
        jbtnNewInv = new javax.swing.JButton();
        jbtnStudFind = new javax.swing.JButton();
        jtxtStudFind = new javax.swing.JTextField();
        jbtnResLes = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblStud = new javax.swing.JTable();
        jPnlLeft = new javax.swing.JPanel();
        jbtnCostMtrx = new javax.swing.JButton();
        jbtnExit = new javax.swing.JButton();
        jbtnClean = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jtxtSrchInvo = new javax.swing.JTextField();
        jbtnSrchInvo = new javax.swing.JButton();
        jlblLstInv = new javax.swing.JLabel();
        jbtnInvoDB = new javax.swing.JButton();
        jMenBarTop = new javax.swing.JMenuBar();
        jMenFile = new javax.swing.JMenu();
        jMenEdit = new javax.swing.JMenu();
        jMenTools = new javax.swing.JMenu();
        jMenHelp = new javax.swing.JMenu();
        jmeniteAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Broadway", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TennisBallLogo_Lil.png"))); // NOI18N
        jLabel1.setText("TENNIS CLUB MANAGER 0.2");

        javax.swing.GroupLayout jPnlTopLayout = new javax.swing.GroupLayout(jPnlTop);
        jPnlTop.setLayout(jPnlTopLayout);
        jPnlTopLayout.setHorizontalGroup(
            jPnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1192, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlTopLayout.setVerticalGroup(
            jPnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 1220, -1));

        jpnlMid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jpnlInvTop.setBackground(new java.awt.Color(102, 102, 102));
        jpnlInvTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpnlInvTop.setForeground(new java.awt.Color(255, 255, 255));

        jlblInvTop.setBackground(new java.awt.Color(102, 102, 102));
        jlblInvTop.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        jlblInvTop.setForeground(new java.awt.Color(50, 255, 200));
        jlblInvTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblInvTop.setText("SISTEMA DE facturación");

        javax.swing.GroupLayout jpnlInvTopLayout = new javax.swing.GroupLayout(jpnlInvTop);
        jpnlInvTop.setLayout(jpnlInvTopLayout);
        jpnlInvTopLayout.setHorizontalGroup(
            jpnlInvTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlInvTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblInvTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnlInvTopLayout.setVerticalGroup(
            jpnlInvTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlInvTopLayout.createSequentialGroup()
                .addComponent(jlblInvTop, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jpnlHeader.setBorder(javax.swing.BorderFactory.createTitledBorder("Encabezado de Comprobante"));

        jlblDat.setText("FECHA");

        jtxtDat.setEditable(false);
        jtxtDat.setForeground(new java.awt.Color(255, 0, 0));
        jtxtDat.setFocusable(false);

        jlblStuIde.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblStuIde.setText("Cliente PIN  ");

        jtxtStuPIN.setEditable(false);
        jtxtStuPIN.setForeground(new java.awt.Color(0, 50, 255));
        jtxtStuPIN.setFocusable(false);
        jtxtStuPIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtStuPINActionPerformed(evt);
            }
        });
        jtxtStuPIN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtStuPINKeyPressed(evt);
            }
        });

        jlblStuNam.setText("Alumno");

        jtxtStuNam.setEditable(false);
        jtxtStuNam.setForeground(new java.awt.Color(0, 50, 255));
        jtxtStuNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtStuNamActionPerformed(evt);
            }
        });

        jlblStuLev.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblStuLev.setText("Nivel  ");

        jtxtStuLev.setEditable(false);
        jtxtStuLev.setForeground(new java.awt.Color(0, 50, 255));
        jtxtStuLev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtStuLevActionPerformed(evt);
            }
        });

        jchkboxManual.setLabel("Valores manuales");
        jchkboxManual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jchkboxManualItemStateChanged(evt);
            }
        });

        jchkboxMemb.setLabel("Incluir Membresía");
        jchkboxMemb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jchkboxMembItemStateChanged(evt);
            }
        });

        jlblInvNum.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        jlblInvNum.setForeground(new java.awt.Color(0, 50, 255));
        jlblInvNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblInvNum.setText("FACTURA -----");

        jlblPhone.setText("Teléfono  ");

        jtxtPhone.setEditable(false);
        jtxtPhone.setForeground(new java.awt.Color(0, 50, 255));

        jlblVenNam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlblVenNam.setText("Repre.  ");

        jlblMail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblMail.setText("E-Mail  ");

        jtxtMail.setEditable(false);
        jtxtMail.setForeground(new java.awt.Color(0, 50, 255));
        jtxtMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtMailActionPerformed(evt);
            }
        });

        jlblMode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblMode.setText("Modalidad  ");

        jcboxMode.addItem("Standard");
        jcboxMode.addItem("Clín. de Perfecc.");
        jcboxMode.addItem("Clínica de Niños");
        jcboxMode.addItem("Mini Tennis");

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

        jlblPaq.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblPaq.setText("Paquete ");

        jcboxHour.addItem("Mañana");
        jcboxHour.addItem("Día");
        jcboxHour.addItem("Tarde");
        jcboxHour.addItem("Noche");
        jcboxHour.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboxHourItemStateChanged(evt);
            }
        });

        jlblHour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblHour.setText("Horario");

        jlblDay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblDay.setText("Día  ");

        jcboxDay.addItem("L-V");
        jcboxDay.addItem("S-D");

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jlblCoLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCoLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo_Lil.png"))); // NOI18N

        jbtnAddDRC.setText("Cargar DRC");
        jbtnAddDRC.setToolTipText("Agrega línea de lecciones según el DRC");
        jbtnAddDRC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddDRCActionPerformed(evt);
            }
        });

        jcboxDRC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboxDRCItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jpnlHeaderLayout = new javax.swing.GroupLayout(jpnlHeader);
        jpnlHeader.setLayout(jpnlHeaderLayout);
        jpnlHeaderLayout.setHorizontalGroup(
            jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addComponent(jlblVenNam, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jtxtVenNam, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                            .addComponent(jlblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jtxtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jlblMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                            .addComponent(jlblStuNam, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jtxtStuNam, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                        .addComponent(jlblDat, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jtxtDat, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jlblStuIde, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                    .addComponent(jlblStuLev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtxtStuLev, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                    .addComponent(jtxtStuPIN)))))
                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpnlHeaderLayout.createSequentialGroup()
                                .addComponent(jbtnAddDRC, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboxDRC, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblMode, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblDay, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblPaq, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblHour, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcboxMode, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxDay, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxPaq, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboxHour, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                        .addComponent(jchkboxMemb, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jchkboxManual, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblCoLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblInvNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlHeaderLayout.setVerticalGroup(
            jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlHeaderLayout.createSequentialGroup()
                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblDat, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblStuIde, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtStuPIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jlblStuNam))
                                    .addComponent(jlblStuLev, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jtxtStuNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jtxtStuLev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jtxtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jtxtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jlblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(6, 6, 6)
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblVenNam, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jtxtVenNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnAddDRC)
                            .addComponent(jcboxDRC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnlHeaderLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jlblMail)))
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlHeaderLayout.createSequentialGroup()
                .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jpnlHeaderLayout.createSequentialGroup()
                            .addComponent(jlblInvNum)
                            .addGap(8, 8, 8)
                            .addComponent(jlblCoLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jpnlHeaderLayout.createSequentialGroup()
                            .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jcboxMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlblMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                    .addComponent(jlblDay, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(jlblPaq, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(jlblHour, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jpnlHeaderLayout.createSequentialGroup()
                                    .addComponent(jcboxDay, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jcboxPaq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(12, 12, 12)
                                    .addComponent(jcboxHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jpnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jchkboxMemb, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jchkboxManual, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(5, 5, 5))
        );

        jpnlInvMid.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuerpo de Comprobante"));

        jtxtT1.setEditable(false);
        jtxtT1.setBackground(new java.awt.Color(102, 102, 102));
        jtxtT1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtT1.setForeground(new java.awt.Color(50, 255, 200));
        jtxtT1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtT1.setText("Código");
        jtxtT1.setFocusable(false);
        jtxtT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtT1ActionPerformed(evt);
            }
        });

        jtxtT2.setEditable(false);
        jtxtT2.setBackground(new java.awt.Color(102, 102, 102));
        jtxtT2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtT2.setForeground(new java.awt.Color(50, 255, 200));
        jtxtT2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtT2.setText("Cantidad");
        jtxtT2.setFocusable(false);
        jtxtT2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtT2ActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jtxtT3.setEditable(false);
        jtxtT3.setBackground(new java.awt.Color(102, 102, 102));
        jtxtT3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtT3.setForeground(new java.awt.Color(50, 255, 200));
        jtxtT3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtxtT3.setText("Descripción");
        jtxtT3.setFocusable(false);
        jtxtT3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtT3ActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jtxtT4.setEditable(false);
        jtxtT4.setBackground(new java.awt.Color(102, 102, 102));
        jtxtT4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtT4.setForeground(new java.awt.Color(50, 255, 200));
        jtxtT4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtT4.setText("Valor Unit.");
        jtxtT4.setFocusable(false);
        jtxtT4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtT4ActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jtxtT5.setEditable(false);
        jtxtT5.setBackground(new java.awt.Color(102, 102, 102));
        jtxtT5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtT5.setForeground(new java.awt.Color(50, 255, 200));
        jtxtT5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtT5.setText("Valor Total");
        jtxtT5.setFocusable(false);
        jtxtT5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtT5ActionPerformed(evt);
            }
        });

        jtxtA1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxtA1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtA1.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                loadInvoiceLine(jtxtA1.getText().toUpperCase(), 1);
            }
            public void removeUpdate(DocumentEvent e) {
                loadInvoiceLine(jtxtA1.getText().toUpperCase(), 1);
            }
            public void insertUpdate(DocumentEvent e) {
                loadInvoiceLine(jtxtA1.getText().toUpperCase(), 1);
            }
        });
        jtxtA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtA1ActionPerformed(evt);
            }
        });
        jtxtA1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtA1KeyPressed(evt);
            }
        });

        jtxtA2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxtA2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtA2.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtA2.getText()) == true ){
                    if ( checkCalcCondition(1) == true ){
                        calcLineTotal(1);
                        calcSubTotal();
                    }
                }
            }
            public void removeUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtA2.getText()) == true ){
                    if ( checkCalcCondition(1) == true ){
                        calcLineTotal(1);
                        calcSubTotal();
                    }
                }
            }
            public void insertUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtA2.getText()) == true ){
                    if ( checkCalcCondition(1) == true ){
                        calcLineTotal(1);
                        calcSubTotal();
                    }
                }
            }
        });
        jtxtA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtA2ActionPerformed(evt);
            }
        });

        jtxtA3.setEditable(false);
        jtxtA3.setBackground(new java.awt.Color(255, 255, 255));
        jtxtA3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxtA3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtxtA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtA3ActionPerformed(evt);
            }
        });

        jtxtA5.setEditable(false);
        jtxtA5.setBackground(new java.awt.Color(204, 204, 204));
        jtxtA5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxtA5.setForeground(new java.awt.Color(0, 50, 255));
        jtxtA5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jtxtA4.setEditable(false);
        jtxtA4.setBackground(new java.awt.Color(255, 255, 255));
        jtxtA4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxtA4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtA4.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jtxtA4ComponentAdded(evt);
            }
        });
        jtxtA4.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jtxtA4InputMethodTextChanged(evt);
            }
        });
        jtxtA4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtA4ActionPerformed(evt);
            }
        });
        jtxtA4.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtA4.getText()) == true ){
                    if ( checkCalcCondition(1) == true ){
                        calcLineTotal(1);
                        calcSubTotal();
                    }
                }
            }
            public void removeUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtA4.getText()) == true ){
                    if ( checkCalcCondition(1) == true ){
                        calcLineTotal(1);
                        calcSubTotal();
                    }
                }
            }
            public void insertUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtA4.getText()) == true ){
                    if ( checkCalcCondition(1) == true ){
                        calcLineTotal(1);
                        calcSubTotal();
                    }
                }
            }
        });

        jtxtB1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxtB1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtA1.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                loadInvoiceLine(jtxtB1.getText().toUpperCase(), 2);
            }
            public void removeUpdate(DocumentEvent e) {
                loadInvoiceLine(jtxtB1.getText().toUpperCase(), 2);
            }
            public void insertUpdate(DocumentEvent e) {
                loadInvoiceLine(jtxtB1.getText().toUpperCase(), 2);
            }
        });
        jtxtB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtB1ActionPerformed(evt);
            }
        });
        jtxtB1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtB1KeyPressed(evt);
            }
        });

        jtxtB2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtxtB2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtB2.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtB2.getText()) == true ){
                    if ( checkCalcCondition(2) == true ){
                        calcLineTotal(2);
                        calcSubTotal();
                    }
                }
            }
            public void removeUpdate(DocumentEvent e) {
                if (  validateRealNumber(jtxtB2.getText()) == true ){
                    if ( checkCalcCondition(2) == true ){
                        calcLineTotal(2);
                        calcSubTotal();
                    }
                }}
                public void insertUpdate(DocumentEvent e) {
                    if (  validateRealNumber(jtxtB2.getText()) == true ){
                        if ( checkCalcCondition(2) == true ){
                            calcLineTotal(2);
                            calcSubTotal();
                        }
                    }}
                });

                jtxtB3.setEditable(false);
                jtxtB3.setBackground(new java.awt.Color(255, 255, 255));
                jtxtB3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtB3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                jtxtB3.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jtxtB3ActionPerformed(evt);
                    }
                });

                jtxtB5.setEditable(false);
                jtxtB5.setBackground(new java.awt.Color(204, 204, 204));
                jtxtB5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtB5.setForeground(new java.awt.Color(0, 50, 255));
                jtxtB5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jtxtB4.setEditable(false);
                jtxtB4.setBackground(new java.awt.Color(255, 255, 255));
                jtxtB4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtB4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                jtxtB4.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtB4.getText()) == true ){
                            if ( checkCalcCondition(2) == true ){
                                calcLineTotal(2);
                                calcSubTotal();
                            }
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtB4.getText()) == true ){
                            if ( checkCalcCondition(2) == true ){
                                calcLineTotal(2);
                                calcSubTotal();
                            }
                        }
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtB4.getText()) == true ){
                            if ( checkCalcCondition(2) == true ){
                                calcLineTotal(2);
                                calcSubTotal();
                            }
                        }
                    }
                });
                jtxtB4.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jtxtB4ActionPerformed(evt);
                    }
                });

                jtxtC1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtC1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jtxtA1.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e){
                        loadInvoiceLine(jtxtC1.getText().toUpperCase(), 3);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        loadInvoiceLine(jtxtC1.getText().toUpperCase(), 3);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        loadInvoiceLine(jtxtC1.getText().toUpperCase(), 3);
                    }
                });
                jtxtC1.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        jtxtC1KeyPressed(evt);
                    }
                });

                jtxtC2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtC2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jtxtC2.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtC2.getText()) == true ){
                            if ( checkCalcCondition(3) == true ){
                                calcLineTotal(3);
                                calcSubTotal();
                            }
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtC2.getText()) == true ){
                            if ( checkCalcCondition(3) == true ){
                                calcLineTotal(3);
                                calcSubTotal();
                            }
                        }
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtC2.getText()) == true ){
                            if ( checkCalcCondition(3) == true ){
                                calcLineTotal(3);
                                calcSubTotal();
                            }
                        }
                    }
                });

                jtxtC3.setEditable(false);
                jtxtC3.setBackground(new java.awt.Color(255, 255, 255));
                jtxtC3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtC3.setHorizontalAlignment(javax.swing.JTextField.LEFT);

                jtxtC5.setEditable(false);
                jtxtC5.setBackground(new java.awt.Color(204, 204, 204));
                jtxtC5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtC5.setForeground(new java.awt.Color(0, 50, 255));
                jtxtC5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jtxtC4.setEditable(false);
                jtxtC4.setBackground(new java.awt.Color(255, 255, 255));
                jtxtC4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtC4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                jtxtC4.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jtxtC4ActionPerformed(evt);
                    }
                });
                jtxtC4.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtC4.getText()) == true ){
                            if ( checkCalcCondition(3) == true ){
                                calcLineTotal(3);
                                calcSubTotal();
                            }
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtC4.getText()) == true ){
                            if ( checkCalcCondition(3) == true ){
                                calcLineTotal(3);
                                calcSubTotal();
                            }
                        }
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtC4.getText()) == true ){
                            if ( checkCalcCondition(3) == true ){
                                calcLineTotal(3);
                                calcSubTotal();
                            }
                        }
                    }
                });

                jtxtD1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtD1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jtxtA1.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e){
                        loadInvoiceLine(jtxtD1.getText().toUpperCase(), 4);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        loadInvoiceLine(jtxtD1.getText().toUpperCase(), 4);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        loadInvoiceLine(jtxtD1.getText().toUpperCase(), 4);
                    }
                });
                jtxtD1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jtxtD1ActionPerformed(evt);
                    }
                });
                jtxtD1.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        jtxtD1KeyPressed(evt);
                    }
                });

                jtxtD2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtD2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jtxtD2.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtD2.getText()) == true ){
                            if ( checkCalcCondition(4) == true ){
                                calcLineTotal(4);
                                calcSubTotal();
                            }
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtD2.getText()) == true ){
                            if ( checkCalcCondition(4) == true ){
                                calcLineTotal(4);
                                calcSubTotal();
                            }
                        }
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtD2.getText()) == true ){
                            if ( checkCalcCondition(4) == true ){
                                calcLineTotal(4);
                                calcSubTotal();
                            }
                        }
                    }
                });

                jtxtD3.setEditable(false);
                jtxtD3.setBackground(new java.awt.Color(255, 255, 255));
                jtxtD3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtD3.setHorizontalAlignment(javax.swing.JTextField.LEFT);

                jtxtD5.setEditable(false);
                jtxtD5.setBackground(new java.awt.Color(204, 204, 204));
                jtxtD5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtD5.setForeground(new java.awt.Color(0, 50, 255));
                jtxtD5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jtxtD4.setEditable(false);
                jtxtD4.setBackground(new java.awt.Color(255, 255, 255));
                jtxtD4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtD4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                jtxtD4.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtD4.getText()) == true ){
                            if ( checkCalcCondition(4) == true ){
                                calcLineTotal(4);
                                calcSubTotal();
                            }
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtD4.getText()) == true ){
                            if ( checkCalcCondition(4) == true ){
                                calcLineTotal(4);
                                calcSubTotal();
                            }
                        }
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if (  validateRealNumber(jtxtD4.getText()) == true ){
                            if ( checkCalcCondition(4) == true ){
                                calcLineTotal(4);
                                calcSubTotal();
                            }
                        }
                    }
                });

                jbtnADel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete-icon_vlil.fw.png"))); // NOI18N
                jbtnADel.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnADelActionPerformed(evt);
                    }
                });

                jbtnBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete-icon_vlil.fw.png"))); // NOI18N
                jbtnBDel.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnBDelActionPerformed(evt);
                    }
                });

                jbtnCDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete-icon_vlil.fw.png"))); // NOI18N
                jbtnCDel.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnCDelActionPerformed(evt);
                    }
                });

                jbtnDDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete-icon_vlil.fw.png"))); // NOI18N
                jbtnDDel.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnDDelActionPerformed(evt);
                    }
                });

                jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

                javax.swing.GroupLayout jpnlInvMidLayout = new javax.swing.GroupLayout(jpnlInvMid);
                jpnlInvMid.setLayout(jpnlInvMidLayout);
                jpnlInvMidLayout.setHorizontalGroup(
                    jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInvMidLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jtxtT1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbtnBDel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtnCDel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtnDDel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtnADel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtD1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtC1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtB1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtA1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtT2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jtxtC2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                        .addComponent(jtxtB2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jtxtA2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jtxtD2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtT3)
                                    .addComponent(jtxtC3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtxtD3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtxtB3)))
                            .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtA3)
                                    .addComponent(jSeparator6))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator9)
                            .addComponent(jtxtT4)
                            .addComponent(jtxtA4)
                            .addComponent(jtxtB4)
                            .addComponent(jtxtC4)
                            .addComponent(jtxtD4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtT5)
                            .addComponent(jtxtA5)
                            .addComponent(jtxtB5)
                            .addComponent(jtxtC5)
                            .addComponent(jtxtD5)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                );
                jpnlInvMidLayout.setVerticalGroup(
                    jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlInvMidLayout.createSequentialGroup()
                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                        .addComponent(jtxtT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator9))
                                    .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jtxtT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtA4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jtxtA1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtA3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jbtnADel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jtxtB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtB5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jbtnBDel)
                                            .addComponent(jtxtB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jbtnCDel)
                                            .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jtxtC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jbtnDDel)
                                            .addGroup(jpnlInvMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jtxtD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtD3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtD4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtD5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jSeparator2)))
                            .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                .addComponent(jtxtT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtA5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator11)
                            .addGroup(jpnlInvMidLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jSeparator3))
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator4))
                        .addContainerGap())
                );

                jpnlInvBottom.setBorder(javax.swing.BorderFactory.createTitledBorder("Pie del Comprobante"));

                jlblDesPer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                jlblDesPer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jlblDesPer.setText("Desc %");

                jtxtDesPer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                jtxtDesPer.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jtxtDesPerActionPerformed(evt);
                    }
                });
                jtxtDesPer.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e) {
                        if ( !jtxtSubTot.getText().equals("") ){
                            calcSubTotal();
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if ( !jtxtSubTot.getText().equals("") ){
                            calcSubTotal();
                        }
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if ( !jtxtSubTot.getText().equals("") ){
                            calcSubTotal();
                        }
                    }
                });

                jlblDesVal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                jlblDesVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jlblDesVal.setText("Descuento");

                jtxtDesVal.setEditable(false);
                jtxtDesVal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jlblTaxPer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                jlblTaxPer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jlblTaxPer.setText("Impuesto %");

                jtxtTaxPer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                jtxtTaxPer.getDocument().addDocumentListener(new DocumentListener(){
                    public void changedUpdate(DocumentEvent e) {
                        if ( !jtxtSubTot.getText().equals("") ){
                            calcSubTotal();
                        }
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if ( !jtxtSubTot.getText().equals("") ){
                            calcSubTotal();
                        }
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if ( !jtxtSubTot.getText().equals("") ){
                            calcSubTotal();
                        }
                    }
                });

                jlblTaxVal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                jlblTaxVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jlblTaxVal.setText("Impuesto");

                jtxtTaxVal.setEditable(false);
                jtxtTaxVal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jlblSubDes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jlblSubDes.setText("Subtotal Desc.");

                jlblSubImp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jlblSubImp.setText("Subtotal Ipm.");

                jtxtSubDes.setEditable(false);
                jtxtSubDes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtSubDes.setForeground(new java.awt.Color(255, 0, 0));
                jtxtSubDes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jtxtSubTax.setEditable(false);
                jtxtSubTax.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtSubTax.setForeground(new java.awt.Color(0, 153, 153));
                jtxtSubTax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jlblTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jlblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jlblTotal.setText("TOTAL");

                jtxtTotal.setEditable(false);
                jtxtTotal.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
                jtxtTotal.setForeground(new java.awt.Color(0, 50, 255));
                jtxtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jbtnInvMail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jbtnInvMail.setForeground(new java.awt.Color(0, 51, 204));
                jbtnInvMail.setText("Enviar por eMail");
                jbtnInvMail.setToolTipText("Envía detalle de la factura en pantalla al Cliente");
                jbtnInvMail.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnInvMailActionPerformed(evt);
                    }
                });

                jtxtSubTot.setEditable(false);
                jtxtSubTot.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jtxtSubTot.setForeground(new java.awt.Color(0, 50, 255));
                jtxtSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

                jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel2.setText("Sub Total");

                jbtnNullInv.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jbtnNullInv.setForeground(new java.awt.Color(255, 0, 51));
                jbtnNullInv.setText("ANULAR");
                jbtnNullInv.setToolTipText("Anula la factura en pantalla");
                jbtnNullInv.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnNullInvActionPerformed(evt);
                    }
                });

                jbtnGen.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jbtnGen.setForeground(new java.awt.Color(0, 102, 102));
                jbtnGen.setText("Generar");
                jbtnGen.setToolTipText("Crea y agrega una nueva factura a la Base de Datos");
                jbtnGen.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnGenActionPerformed(evt);
                    }
                });

                jpnlPayMod.setBorder(javax.swing.BorderFactory.createTitledBorder("Método de pago"));
                jpnlPayMod.setToolTipText("");

                jbtngrpPayMod.add(jrbtnEfec);
                jrbtnEfec.setText("  Efectivo");

                jbtngrpPayMod.add(jrbtnTarj);
                jrbtnTarj.setText("  Tarjeta");

                jbtngrpPayMod.add(jrbtnTran);
                jrbtnTran.setText("  Transferencia");

                jbtngrpPayMod.add(jrbtnAuto);
                jrbtnAuto.setText("  Cargo Automático");

                javax.swing.GroupLayout jpnlPayModLayout = new javax.swing.GroupLayout(jpnlPayMod);
                jpnlPayMod.setLayout(jpnlPayModLayout);
                jpnlPayModLayout.setHorizontalGroup(
                    jpnlPayModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlPayModLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpnlPayModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrbtnEfec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jrbtnTarj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jrbtnTran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jrbtnAuto, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jpnlPayModLayout.setVerticalGroup(
                    jpnlPayModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlPayModLayout.createSequentialGroup()
                        .addComponent(jrbtnEfec, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbtnTarj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbtnTran)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrbtnAuto))
                );

                javax.swing.GroupLayout jpnlInvBottomLayout = new javax.swing.GroupLayout(jpnlInvBottom);
                jpnlInvBottom.setLayout(jpnlInvBottomLayout);
                jpnlInvBottomLayout.setHorizontalGroup(
                    jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlInvBottomLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnInvMail, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(jbtnNullInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnGen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpnlPayMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInvBottomLayout.createSequentialGroup()
                                .addComponent(jlblDesVal, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtDesVal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlblTaxVal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtTaxVal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInvBottomLayout.createSequentialGroup()
                                .addComponent(jlblDesPer, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtDesPer, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlblTaxPer, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtTaxPer, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInvBottomLayout.createSequentialGroup()
                                .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jlblTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlblSubDes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                    .addComponent(jlblSubImp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtxtSubDes, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                    .addComponent(jtxtTotal)
                                    .addComponent(jtxtSubTax)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlInvBottomLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtSubTot, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                );
                jpnlInvBottomLayout.setVerticalGroup(
                    jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlInvBottomLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jbtnInvMail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnNullInv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnGen, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpnlInvBottomLayout.createSequentialGroup()
                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnlInvBottomLayout.createSequentialGroup()
                                .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnlInvBottomLayout.createSequentialGroup()
                                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtxtSubTot)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtxtSubTax)
                                            .addComponent(jlblSubImp)))
                                    .addGroup(jpnlInvBottomLayout.createSequentialGroup()
                                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jlblTaxPer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlblDesPer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jtxtTaxPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtDesPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jtxtTaxVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlblTaxVal, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtDesVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlblDesVal, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxtSubDes, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblSubDes, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlInvBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jpnlPayMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout jpnlMainLayout = new javax.swing.GroupLayout(jpnlMain);
                jpnlMain.setLayout(jpnlMainLayout);
                jpnlMainLayout.setHorizontalGroup(
                    jpnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jpnlInvTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpnlInvBottom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpnlInvMid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpnlHeader, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(13, Short.MAX_VALUE))
                );
                jpnlMainLayout.setVerticalGroup(
                    jpnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMainLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jpnlInvTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnlInvMid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnlInvBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jTbPnMid.addTab("Sistema de Facturación", jpnlMain);

                jPnlProfBot.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                jbtnNewTeach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_medium.png"))); // NOI18N
                jbtnNewTeach.setText("Nuevo");
                jbtnNewTeach.setToolTipText("Agrega un nuevo Profesor");
                jbtnNewTeach.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnNewTeachActionPerformed(evt);
                    }
                });

                jbtnEditTeach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit_medium.png"))); // NOI18N
                jbtnEditTeach.setText("Ver / Editar");
                jbtnEditTeach.setToolTipText("Edita un Profesor");
                jbtnEditTeach.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnEditTeachActionPerformed(evt);
                    }
                });

                jbtnDelTeach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_medium.png"))); // NOI18N
                jbtnDelTeach.setText("Eliminar");
                jbtnDelTeach.setToolTipText("Elimina un Profesor");
                jbtnDelTeach.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnDelTeachActionPerformed(evt);
                    }
                });

                jbtnRefreshTeach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh_2_medium.png"))); // NOI18N
                jbtnRefreshTeach.setText("Refrescar");
                jbtnRefreshTeach.setToolTipText("Recarga la lista Profesores desde la Base de Datos");
                jbtnRefreshTeach.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnRefreshTeachActionPerformed(evt);
                    }
                });

                jlblProfDBsize.setText("Data Base size: ");

                jbtnProfFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_find_medium.png"))); // NOI18N
                jbtnProfFind.setText("Buscar");
                jbtnProfFind.setToolTipText("Buscar Profesor en la Base de Datos");
                jbtnProfFind.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnProfFindActionPerformed(evt);
                    }
                });

                jtxtProfFind.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
                jtxtProfFind.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jtxtProfFindActionPerformed(evt);
                    }
                });
                jtxtProfFind.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        jtxtProfFindKeyPressed(evt);
                    }
                });

                javax.swing.GroupLayout jPnlProfBotLayout = new javax.swing.GroupLayout(jPnlProfBot);
                jPnlProfBot.setLayout(jPnlProfBotLayout);
                jPnlProfBotLayout.setHorizontalGroup(
                    jPnlProfBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlProfBotLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlProfBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlblProfDBsize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPnlProfBotLayout.createSequentialGroup()
                                .addComponent(jbtnNewTeach, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnDelTeach, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jbtnEditTeach, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnRefreshTeach, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtxtProfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnProfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );
                jPnlProfBotLayout.setVerticalGroup(
                    jPnlProfBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlProfBotLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlblProfDBsize)
                        .addGap(37, 37, 37)
                        .addGroup(jPnlProfBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnNewTeach)
                            .addComponent(jbtnEditTeach)
                            .addComponent(jbtnDelTeach)
                            .addComponent(jbtnRefreshTeach)
                            .addComponent(jbtnProfFind)
                            .addComponent(jtxtProfFind, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                        .addContainerGap())
                );

                jtblProf.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
                jtblProf.setModel(new javax.swing.table.DefaultTableModel(
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
                jScrollPane1.setViewportView(jtblProf);

                javax.swing.GroupLayout jPnlProfLayout = new javax.swing.GroupLayout(jPnlProf);
                jPnlProf.setLayout(jPnlProfLayout);
                jPnlProfLayout.setHorizontalGroup(
                    jPnlProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlProfLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPnlProfBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jPnlProfLayout.setVerticalGroup(
                    jPnlProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlProfLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPnlProfBot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );

                jTbPnMid.addTab("Control de Profesores", jPnlProf);

                jPnlStudBot.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                jbtnNewStud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_medium.png"))); // NOI18N
                jbtnNewStud.setText("Nuevo");
                jbtnNewStud.setToolTipText("Agrega un nuevo Alumno");
                jbtnNewStud.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnNewStudActionPerformed(evt);
                    }
                });

                jbtnDelStud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_medium.png"))); // NOI18N
                jbtnDelStud.setText("Eliminar");
                jbtnDelStud.setToolTipText("Elimina un Alumno");
                jbtnDelStud.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnDelStudActionPerformed(evt);
                    }
                });

                jbtnEditStud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit_medium.png"))); // NOI18N
                jbtnEditStud.setText("Ver / Editar");
                jbtnEditStud.setToolTipText("Edita un Alumno");
                jbtnEditStud.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnEditStudActionPerformed(evt);
                    }
                });

                jbtnRefreshStud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/refresh_2_medium.png"))); // NOI18N
                jbtnRefreshStud.setText("Refrescar");
                jbtnRefreshStud.setToolTipText("Recarga los Alumnos desde la Base de Datos");
                jbtnRefreshStud.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnRefreshStudActionPerformed(evt);
                    }
                });

                jlblStudDBsize.setText("Data Base size: ");

                jbtnNewInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Invoice_medium.png"))); // NOI18N
                jbtnNewInv.setText("Facturar");
                jbtnNewInv.setToolTipText("Crear nueva Factura para el alumno marcado");
                jbtnNewInv.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnNewInvActionPerformed(evt);
                    }
                });

                jbtnStudFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_find_medium.png"))); // NOI18N
                jbtnStudFind.setText("Buscar");
                jbtnStudFind.setToolTipText("Buscar Alumno en la Base de Datos");
                jbtnStudFind.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnStudFindActionPerformed(evt);
                    }
                });

                jtxtStudFind.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
                jtxtStudFind.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jtxtStudFindActionPerformed(evt);
                    }
                });
                jtxtStudFind.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        jtxtStudFindKeyPressed(evt);
                    }
                });

                jbtnResLes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tennisball_lil.png"))); // NOI18N
                jbtnResLes.setText("Reservar");
                jbtnResLes.setToolTipText("Agrega lecciones recibidas a los datos del Alumno");
                jbtnResLes.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnResLesActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout jPnlStudBotLayout = new javax.swing.GroupLayout(jPnlStudBot);
                jPnlStudBot.setLayout(jPnlStudBotLayout);
                jPnlStudBotLayout.setHorizontalGroup(
                    jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlStudBotLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlStudBotLayout.createSequentialGroup()
                                .addComponent(jbtnNewStud, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnDelStud, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnEditStud, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnRefreshStud, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 113, Short.MAX_VALUE)
                                .addComponent(jtxtStudFind, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPnlStudBotLayout.createSequentialGroup()
                                .addComponent(jlblStudDBsize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(401, 401, 401)
                                .addComponent(jbtnResLes, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnStudFind, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(jbtnNewInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jPnlStudBotLayout.setVerticalGroup(
                    jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlStudBotLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlblStudDBsize)
                            .addGroup(jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbtnNewInv)
                                .addComponent(jbtnResLes, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlStudBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbtnNewStud)
                                .addComponent(jbtnEditStud)
                                .addComponent(jbtnDelStud)
                                .addComponent(jbtnRefreshStud)
                                .addComponent(jbtnStudFind))
                            .addComponent(jtxtStudFind))
                        .addContainerGap())
                );

                jtblStud.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
                jtblStud.setModel(new javax.swing.table.DefaultTableModel(
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
                jScrollPane2.setViewportView(jtblStud);

                javax.swing.GroupLayout jPnlStudLayout = new javax.swing.GroupLayout(jPnlStud);
                jPnlStud.setLayout(jPnlStudLayout);
                jPnlStudLayout.setHorizontalGroup(
                    jPnlStudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlStudLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlStudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPnlStudBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addContainerGap())
                );
                jPnlStudLayout.setVerticalGroup(
                    jPnlStudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlStudLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPnlStudBot, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );

                jTbPnMid.addTab("Control de Alumnos", jPnlStud);

                javax.swing.GroupLayout jpnlMidLayout = new javax.swing.GroupLayout(jpnlMid);
                jpnlMid.setLayout(jpnlMidLayout);
                jpnlMidLayout.setHorizontalGroup(
                    jpnlMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMidLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTbPnMid)
                        .addContainerGap())
                );
                jpnlMidLayout.setVerticalGroup(
                    jpnlMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlMidLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTbPnMid)
                        .addContainerGap())
                );

                getContentPane().add(jpnlMid, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 1070, 630));

                jPnlLeft.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                jbtnCostMtrx.setText("Costos");
                jbtnCostMtrx.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnCostMtrxActionPerformed(evt);
                    }
                });

                jbtnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit_medium.png"))); // NOI18N
                jbtnExit.setText("Salir");
                jbtnExit.setToolTipText("Cierra la Aplicación");
                jbtnExit.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnExitActionPerformed(evt);
                    }
                });

                jbtnClean.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jbtnClean.setForeground(new java.awt.Color(153, 0, 153));
                jbtnClean.setText("Limpiar valores");
                jbtnClean.setToolTipText("Elimina los valores actuales en pantalla");
                jbtnClean.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnCleanActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout jPnlLeftLayout = new javax.swing.GroupLayout(jPnlLeft);
                jPnlLeft.setLayout(jPnlLeftLayout);
                jPnlLeftLayout.setHorizontalGroup(
                    jPnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlLeftLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnClean, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPnlLeftLayout.createSequentialGroup()
                                .addGroup(jPnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtnCostMtrx, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                );
                jPnlLeftLayout.setVerticalGroup(
                    jPnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlLeftLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbtnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnCostMtrx, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                        .addComponent(jbtnExit)
                        .addContainerGap())
                );

                getContentPane().add(jPnlLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 150, 400));

                jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("FACTURAS"));

                jtxtSrchInvo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jtxtSrchInvo.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        jtxtSrchInvoKeyPressed(evt);
                    }
                });

                jbtnSrchInvo.setText("BUSCAR");
                jbtnSrchInvo.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnSrchInvoActionPerformed(evt);
                    }
                });

                jlblLstInv.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
                jlblLstInv.setForeground(new java.awt.Color(0, 0, 255));
                jlblLstInv.setText("Last Invoice");

                jbtnInvoDB.setText("Base de Datos");
                jbtnInvoDB.setToolTipText("Acceso a la Base de Datos de Facturación");
                jbtnInvoDB.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jbtnInvoDBActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtSrchInvo)
                            .addComponent(jbtnSrchInvo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblLstInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnInvoDB, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtxtSrchInvo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnSrchInvo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblLstInv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnInvoDB, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(37, Short.MAX_VALUE))
                );

                getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, 200));

                jMenFile.setText("Archivo");
                jMenBarTop.add(jMenFile);

                jMenEdit.setText("Editar");
                jMenBarTop.add(jMenEdit);

                jMenTools.setText("Herramientas");
                jMenBarTop.add(jMenTools);

                jMenHelp.setText("Ayuda");

                jmeniteAbout.setText("Acerca de");
                jmeniteAbout.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jmeniteAboutActionPerformed(evt);
                    }
                });
                jMenHelp.add(jmeniteAbout);

                jMenBarTop.add(jMenHelp);

                setJMenuBar(jMenBarTop);

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Desea salir y cerrar la Aplicación?");
        if ( opt == 0 ){
            System.exit(0);
        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jbtnNewTeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNewTeachActionPerformed
        gui_TeacherAdmin tmpTA = new gui_TeacherAdmin(sLocStudDBPath, alStudDB, sLocProfDBPath, alProfDB, -1, alInvoDB); //-1 is for new contact
        tmpTA.setLocationRelativeTo(this);
        tmpTA.setVisible(true);
        dispose();
    }//GEN-LAST:event_jbtnNewTeachActionPerformed

    private void jbtnEditTeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditTeachActionPerformed
        int iRow = -1;
        if ( jtblProf.getSelectedRow() < 0 ){
            JOptionPane.showMessageDialog(this, "Por favor seleccione una línea de la lista", "TCM MSG", JOptionPane.ERROR_MESSAGE);
        }
        else{
            int iSelRow = jtblProf.getSelectedRow();
            iRow = getProfLine(String.valueOf(jtblProf.getValueAt(jtblProf.getSelectedRow(),0)));//this is the PIN column
            if ( iRow == -1 ){
                JOptionPane.showMessageDialog(this, "El PIN del Profesor no fue encontrado en la Base de Datos", "TCM MSG", JOptionPane.ERROR_MESSAGE);
            }
            else{
                gui_TeacherAdmin tmpTA = new gui_TeacherAdmin(sLocStudDBPath, alStudDB, sLocProfDBPath, alProfDB, iRow, alInvoDB);
                tmpTA.setLocationRelativeTo(this);
                tmpTA.setVisible(true);
                dispose();
            }
        }        
    }//GEN-LAST:event_jbtnEditTeachActionPerformed

    private void jbtnDelTeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDelTeachActionPerformed
        int iRow = -1;
        if ( jtblProf.getSelectedRow() < 0 ){
            JOptionPane.showMessageDialog(this, "Por favor seleccione un Profesor de la lista", "TCM MSG", JOptionPane.ERROR_MESSAGE);
        }
        else{
            iRow = getProfLine(String.valueOf(jtblProf.getValueAt(jtblProf.getSelectedRow(),0)));//this is the PIN column
            int opt = JOptionPane.showConfirmDialog(this, "Por favor confirme que desea borrar los datos del Profesor?");
            if ( opt == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                //Removes the Teacher´s entry from the current ArrayList
                alProfDB.remove(iRow);
                //Updates the TXT Data Base with the new changes in the Array
                updateTeacherDB_txt();
                //Cleans the teacher´s table and reloads the ArrayList data
                cleanProfTable();
                loadProfTable();
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "Los datos del Profesor han sido eliminados");
            }
            else{
                JOptionPane.showMessageDialog(this, "No se han efectuado cambios");
            }
        }
    }//GEN-LAST:event_jbtnDelTeachActionPerformed

    private void jbtnRefreshTeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRefreshTeachActionPerformed
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        alProfDB.clear();
        jtxtProfFind.setText("");
        //Loads the TXT Teachers Data Base into the ArrayList 
        loadProfDB();
        //Cleans the reloads the Table with the ArrayLIst data
        cleanProfTable();
        loadProfTable();
        setCursor(Cursor.getDefaultCursor());
        JOptionPane.showMessageDialog(this, "La Base de Datos de Profesores ha sido recargada");
    }//GEN-LAST:event_jbtnRefreshTeachActionPerformed

    private void jbtnNewStudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNewStudActionPerformed
        gui_StudentAdmin tmpSA = new gui_StudentAdmin(sLocStudDBPath, alStudDB, sLocProfDBPath, alProfDB, -1, alInvoDB); //-1 is for new Student
        tmpSA.setLocationRelativeTo(this);
        tmpSA.setVisible(true);
        dispose();
    }//GEN-LAST:event_jbtnNewStudActionPerformed

    private void jbtnDelStudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDelStudActionPerformed
        int iRow = -1;
        if ( jtblStud.getSelectedRow() < 0 ){
            JOptionPane.showMessageDialog(this, "Por favor seleccione un Alumno de la lista", "TCM MSG", JOptionPane.ERROR_MESSAGE);
        }
        else{
            iRow = getStudLine(String.valueOf(jtblStud.getValueAt(jtblStud.getSelectedRow(),10)));
            int opt = JOptionPane.showConfirmDialog(this, "Por favor confirme que desea borrar los datos del Alumno seleccionado?");
            if ( opt == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                //Removes the Studet entry from the ArrayList
                alStudDB.remove(iRow);
                //Updates the lcal Student TXT Data Base with this change
                updateStudentDB_txt();
                //Cleans and reloads the scren table so the changes can be visible now
                cleanStudTable();
                loadStudTable();
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "Los datos del Alumno han sido eliminados");
            }
            else{
                JOptionPane.showMessageDialog(this, "No se han efectuado cambios");
            }
        }
    }//GEN-LAST:event_jbtnDelStudActionPerformed

    private void jbtnEditStudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditStudActionPerformed
        int iRow = -1;
        if ( jtblStud.getSelectedRow() < 0 ){
            JOptionPane.showMessageDialog(this, "Por favor seleccione un Alumno de la lista", "TCM MSG", JOptionPane.ERROR_MESSAGE);
        }
        else{
            iRow = getStudLine(String.valueOf(jtblStud.getValueAt(jtblStud.getSelectedRow(),10)));
            if ( iRow == -1 ){
                JOptionPane.showMessageDialog(this, "El PIN del Alumno no fue encontrado en la Base de Datos", "TCM MSG", JOptionPane.ERROR_MESSAGE);
            }
            else{
                gui_StudentAdmin tmpSA = new gui_StudentAdmin(sLocStudDBPath, alStudDB, sLocProfDBPath, alProfDB, iRow, alInvoDB);
                tmpSA.setLocationRelativeTo(this);
                tmpSA.setVisible(true);
                dispose();
            }
        }
    }//GEN-LAST:event_jbtnEditStudActionPerformed

    private void jbtnRefreshStudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRefreshStudActionPerformed
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Cleans the Find field
        jtxtStudFind.setText("");
        //Cleans and reloads the ArrayList of Students from the TXT Data Base file
        alStudDB.clear();
        loadStudDB();
        //Cleans and reloads the screen table with the refreshed values on the Student ArrayList
        cleanStudTable();
        loadStudTable();
        setCursor(Cursor.getDefaultCursor());
        JOptionPane.showMessageDialog(this, "La base de datos de Alumnos ha sido recargada");
    }//GEN-LAST:event_jbtnRefreshStudActionPerformed

    private void jchkboxManualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jchkboxManualItemStateChanged
        if ( this.jchkboxManual.getState() == true ){
            //this.jtxtDat.setEditable(true);
            this.jtxtStuNam.setEditable(true);
            this.jtxtStuLev.setEditable(true);
            this.jtxtStuPIN.setEditable(true);
            this.jtxtStuPIN.setFocusable(true);
            this.jtxtPhone.setEditable(true);
            this.jtxtMail.setEditable(true);
            //Enabling Invoice cells
            this.jtxtA3.setEditable(true);
            this.jtxtA4.setEditable(true);
            this.jtxtB3.setEditable(true);
            this.jtxtB4.setEditable(true);
            this.jtxtC3.setEditable(true);
            this.jtxtC4.setEditable(true);
            this.jtxtD3.setEditable(true);
            this.jtxtD4.setEditable(true);
        }
        else{
            this.jtxtDat.setEditable(false);
            this.jtxtStuNam.setEditable(false);
            this.jtxtStuLev.setEditable(false);
            this.jtxtStuPIN.setEditable(false);
            this.jtxtStuPIN.setFocusable(false);
            this.jtxtPhone.setEditable(false);
            this.jtxtMail.setEditable(false);
            //Disabling Invoice cells
            this.jtxtA3.setEditable(false);
            this.jtxtA4.setEditable(false);
            this.jtxtB3.setEditable(false);
            this.jtxtB4.setEditable(false);
            this.jtxtC3.setEditable(false);
            this.jtxtC4.setEditable(false);
            this.jtxtD3.setEditable(false);
            this.jtxtD4.setEditable(false);
        }
    }//GEN-LAST:event_jchkboxManualItemStateChanged

    private void jtxtDesPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDesPerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDesPerActionPerformed

    private void jtxtStuPINKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtStuPINKeyPressed
       if ( evt.getKeyCode() == KeyEvent.VK_ENTER ){
            String sStud = jtxtStuPIN.getText().toUpperCase();
            int iLine = getStudLine(sStud);
            if ( iLine == -1 ){
                cleanInvoice();
                JOptionPane.showMessageDialog(this, "El PIN del Alumno no existe en la Base de Datos", "TCM MSG", JOptionPane.ERROR_MESSAGE);
                this.jchkboxManual.setState(true);
            }
            else{
                cleanInvoice();
                this.jtxtStuPIN.setText(alStudDB.get(iLine).getStudPIN().toUpperCase());
                this.jtxtStuNam.setText(alStudDB.get(iLine).getFstName() + " " + alStudDB.get(iLine).getLstName());
                this.jtxtStuLev.setText(alStudDB.get(iLine).getStuLevl());
                if ( !alStudDB.get(iLine).getCelNumb().equals("NA") ){
                    this.jtxtPhone.setText(alStudDB.get(iLine).getCelNumb());
                }
                else{
                    if ( !alStudDB.get(iLine).getHomNumb().equals("NA") ){
                        this.jtxtPhone.setText(alStudDB.get(iLine).getHomNumb());
                    }
                    else{
                        this.jtxtPhone.setText("NA");
                    }
                }
                this.jtxtMail.setText(alStudDB.get(iLine).getEmail());
                getReservations(alStudDB.get(iLine).getReservs());
                this.jchkboxManual.setState(true);
            }
        }
    }//GEN-LAST:event_jtxtStuPINKeyPressed

    private void jbtnStudFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnStudFindActionPerformed
        if ( jtxtStudFind.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "Por favor introduzca un valor a buscar","TCM ERROR MSG",JOptionPane.ERROR_MESSAGE);
        }
        else {
            this.findStudent(jtxtStudFind.getText());
        }
    }//GEN-LAST:event_jbtnStudFindActionPerformed

    private void jtxtStudFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtStudFindKeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER ){
            if ( jtxtStudFind.getText().equals("") ) {
                JOptionPane.showMessageDialog(this, "Por favor introduzca un valor a buscar","TCM ERROR MSG",JOptionPane.ERROR_MESSAGE);
            }
            else {
                this.findStudent(jtxtStudFind.getText());
            }
        }
    }//GEN-LAST:event_jtxtStudFindKeyPressed

    private void jbtnProfFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnProfFindActionPerformed
        if ( jtxtProfFind.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "Por favor introduzca un valor a buscar","TCM ERROR MSG",JOptionPane.ERROR_MESSAGE);
        }
        else {
            this.findTeacher(jtxtProfFind.getText());
        }
    }//GEN-LAST:event_jbtnProfFindActionPerformed

    private void jtxtProfFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtProfFindKeyPressed
       if ( evt.getKeyCode() == KeyEvent.VK_ENTER ){
            if ( jtxtProfFind.getText().equals("") ) {
                JOptionPane.showMessageDialog(this, "Por favor introduzca un valor a buscar","TCM ERROR MSG",JOptionPane.ERROR_MESSAGE);
            }
            else {
                this.findTeacher(jtxtProfFind.getText());
            }
        }
    }//GEN-LAST:event_jtxtProfFindKeyPressed

    private void jtxtStudFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtStudFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtStudFindActionPerformed

    private void jtxtProfFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtProfFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtProfFindActionPerformed

    private void jtxtT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtT1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtT1ActionPerformed

    private void jtxtT2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtT2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtT2ActionPerformed

    private void jtxtT3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtT3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtT3ActionPerformed

    private void jtxtT4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtT4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtT4ActionPerformed

    private void jtxtC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtC4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtC4ActionPerformed

    private void jtxtD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtD1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtD1ActionPerformed

    private void jchkboxMembItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jchkboxMembItemStateChanged
        if ( jchkboxMemb.getState() == true ){//This means that including membership was requested
            //Checks if the 1st line is popualted or not
            if ( jtxtA2.getText().equals("") && jtxtA3.getText().equals("") ){
                jtxtA1.setText("MEMB");
                jtxtA2.setText("1");
                jtxtA3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                jtxtA4.setText(getMembershipCost());
            }
            else{
                //Checks if the 2nd line is popualted or not
                if ( jtxtB2.getText().equals("") && jtxtB3.getText().equals("") ){
                    jtxtB1.setText("MEMB");
                    jtxtB2.setText("1");
                    jtxtB3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                    jtxtB4.setText(getMembershipCost());
                }
                else{
                    //Checks if the 3rd line is popualted or not
                    if ( jtxtC2.getText().equals("") && jtxtC3.getText().equals("") ){
                        jtxtC1.setText("MEMB");
                        jtxtC2.setText("1");
                        jtxtC3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                        jtxtC4.setText(getMembershipCost());
                    }
                    else{
                        //Checks if the 4th line is popualted or not
                        if ( jtxtD2.getText().equals("") && jtxtD3.getText().equals("") ){
                            jtxtD1.setText("MEMB");
                            jtxtD2.setText("1");
                            jtxtD3.setText("MEMBRESÍA / " + jtxtDat.getText().substring(3, 6).toUpperCase());
                            jtxtD4.setText(getMembershipCost());
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "FIN DE LÍNEA: No puede agregarse una línea adicional");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jchkboxMembItemStateChanged

    private void jbtnNewInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNewInvActionPerformed
        int iRow = -1;
        if ( jtblStud.getSelectedRow() < 0 ){
            JOptionPane.showMessageDialog(this, "Por favor seleccione un Alumno de la lista", "TCM MSG", JOptionPane.ERROR_MESSAGE);
        }
        else{
            iRow = getStudLine(String.valueOf(jtblStud.getValueAt(jtblStud.getSelectedRow(),10)));
            if ( iRow == -1 ){
                JOptionPane.showMessageDialog(this, "El PIN del Alumno no fue encontrado en la Base de Datos", "TCM MSG", JOptionPane.ERROR_MESSAGE);
            }
            else{
                cleanInvoice();
                this.jtxtStuPIN.setText(alStudDB.get(iRow).getStudPIN());
                this.jtxtStuNam.setText(alStudDB.get(iRow).getFstName() + " " + alStudDB.get(iRow).getLstName());
                this.jtxtStuLev.setText(alStudDB.get(iRow).getStuLevl());
                if ( !alStudDB.get(iRow).getCelNumb().equals("NA") ){
                    this.jtxtPhone.setText(alStudDB.get(iRow).getCelNumb());
                }
                else{
                    if ( !alStudDB.get(iRow).getHomNumb().equals("NA") ){
                        this.jtxtPhone.setText(alStudDB.get(iRow).getHomNumb());
                    }
                    else{
                        this.jtxtPhone.setText("NA");
                    }
                }
                this.jtxtMail.setText(alStudDB.get(iRow).getEmail());
                this.jTbPnMid.setSelectedIndex(0);
                getReservations(alStudDB.get(iRow).getReservs());
            }
        }
    }//GEN-LAST:event_jbtnNewInvActionPerformed

    private void jtxtA4InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jtxtA4InputMethodTextChanged
        
    }//GEN-LAST:event_jtxtA4InputMethodTextChanged

    private void jtxtA4ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jtxtA4ComponentAdded
        if ( this.jtxtA4.getText().equals("1") ){
            jtxtA5.setText("ONE");
        }
        else{
            jtxtA5.setText("NONE");
        }
    }//GEN-LAST:event_jtxtA4ComponentAdded

    private void jtxtStuPINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtStuPINActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtStuPINActionPerformed

    private void jtxtMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtMailActionPerformed

    private void jcboxPaqItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboxPaqItemStateChanged
        getLessonDesc();
    }//GEN-LAST:event_jcboxPaqItemStateChanged

    private void jcboxHourItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboxHourItemStateChanged
        getLessonDesc();
    }//GEN-LAST:event_jcboxHourItemStateChanged

    private void jbtnGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGenActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Confirma que desea generar la nueva factura?\n"
                + "YA NO SE PODRÁ ACTUALIZAR VALORES");
        if ( opt == 0 ){
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //Saves a copy of the Invoice data into the corresponding Data Base ArrayList
            createNewInvoice();
            //Updates the payed lessons for the corresponding Student and also updates his DRC
            updateStudPayLes();
            //Updates the payed lessons for the correspnding Teacher
            updateTeaPayLes();
            
            
            
            /*
            if ( !this.jcboxDRC.getSelectedItem().equals("Sin Reservas") ){
                //Updates the Teacher's Payed Lessons
                updateProfPayLes();
            }
            */
            //Updates the data bases
            updateInvoiceDB_txt();
            updateTeacherDB_txt();
            updateStudentDB_txt();
            //Updates the data in the Teachers Table
            cleanProfTable();
            loadProfTable();
            cleanStudTable();
            loadStudTable();
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(this, "La nueva Factura ha sido generada correctamente");
        }
        else{
            JOptionPane.showMessageDialog(this, "No se han efectuado cambios");
        }
    }//GEN-LAST:event_jbtnGenActionPerformed

    private void jbtnInvMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnInvMailActionPerformed
        if ( jtxtStuPIN.getText().equals("") ){
            JOptionPane.showMessageDialog(this, "Faltan datos en pantalla de la factura para crear correo electrónico");
        }
        else{
            //Prepares the mail variables
            String sMailSub = "Top Tennis Center. Notificación de Facturación. FACT." + this.jlblInvNum.getText().substring(8);
            String sMailTo = this.jtxtMail.getText();
            String sMailBody = createBodyMail(jtxtStuPIN.getText());
            //OPENS AN OUTLOOK WINDOW WITH THE MAIL READY TO BE SENT
            cls_Mail tmpMail = new cls_Mail(sMailTo, "NA", sMailSub, sMailBody);
            try {
                tmpMail.sendMail();
            } catch (IOException ex) {
                Logger.getLogger(gui_MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(gui_MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_jbtnInvMailActionPerformed

    private void jbtnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCleanActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Confirma que desea descartar los datos en pantalla?");
        if ( opt == 0 ){
            cleanInvoice();
            this.jchkboxManual.setState(false);
            this.jchkboxMemb.setState(false);
            this.jtxtStuPIN.setEditable(false);
            this.jtxtStuNam.setEditable(false);
            this.jtxtStuLev.setEditable(false);
            this.jtxtPhone.setEditable(false);
            this.jtxtMail.setEditable(false);
            this.jtxtA3.setEditable(false);
            this.jtxtA4.setEditable(false);
            this.jtxtB3.setEditable(false);
            this.jtxtB4.setEditable(false);
            this.jtxtC3.setEditable(false);
            this.jtxtC4.setEditable(false);
            this.jtxtD3.setEditable(false);
            this.jtxtD4.setEditable(false);
            JOptionPane.showMessageDialog(this, "Los datos han sido limpiados");
        }
    }//GEN-LAST:event_jbtnCleanActionPerformed

    private void jbtnSrchInvoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSrchInvoActionPerformed
        if ( jtxtSrchInvo.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "Por favor introduzca un valor a buscar","TCM ERROR MSG",JOptionPane.ERROR_MESSAGE);
        }
        else {
            //Obtains the position of the Invoice
            int iPos = getInvoLine(jtxtSrchInvo.getText());
            if ( iPos == -1 ){
                JOptionPane.showMessageDialog(this, "La Factura no fue hallada en la Base de Datos");
            }
            else{
                loadInvoice(iPos);
            }
        }
    }//GEN-LAST:event_jbtnSrchInvoActionPerformed

    private void jtxtSrchInvoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtSrchInvoKeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER ){
            if ( jtxtSrchInvo.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "Por favor introduzca un valor a buscar","TCM ERROR MSG",JOptionPane.ERROR_MESSAGE);
            }
            else {
                //Obtains the position of the Invoice
                int iPos = getInvoLine(jtxtSrchInvo.getText());
                if ( iPos == -1 ){
                    JOptionPane.showMessageDialog(this, "La Factura no fue hallada en la Base de Datos");
                }
                else{
                    loadInvoice(iPos);
                }
            }
        }
    }//GEN-LAST:event_jtxtSrchInvoKeyPressed

    private void jbtnCostMtrxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCostMtrxActionPerformed
        gui_Cost_Matrix tmCM = new gui_Cost_Matrix(sLocCostDBPath, sLocMembDBPath);
        tmCM.setLocationRelativeTo(this);
        tmCM.setVisible(true);
    }//GEN-LAST:event_jbtnCostMtrxActionPerformed

    private void jtxtStuLevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtStuLevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtStuLevActionPerformed

    private void jtxtStuNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtStuNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtStuNamActionPerformed

    private void jtxtA1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtA1KeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_0 ){
            jtxtA1.setText(jtxtA1.getText().toUpperCase());            
        }
    }//GEN-LAST:event_jtxtA1KeyPressed

    private void jtxtB1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtB1KeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_0 ){
            jtxtB1.setText(jtxtB1.getText().toUpperCase());            
        }
    }//GEN-LAST:event_jtxtB1KeyPressed

    private void jtxtC1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtC1KeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_0 ){
            jtxtC1.setText(jtxtC1.getText().toUpperCase());            
        }
    }//GEN-LAST:event_jtxtC1KeyPressed

    private void jtxtD1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtD1KeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_0 ){
            jtxtD1.setText(jtxtD1.getText().toUpperCase());            
        }
    }//GEN-LAST:event_jtxtD1KeyPressed

    private void jtxtA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtA3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtA3ActionPerformed

    private void jbtnADelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnADelActionPerformed
        this.jtxtA1.setText("");
        this.jtxtA2.setText("");
        this.jtxtA3.setText("");
        this.jtxtA4.setText("");
        this.jtxtA5.setText("");
        calcSubTotal();
    }//GEN-LAST:event_jbtnADelActionPerformed

    private void jbtnDDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDDelActionPerformed
        this.jtxtD1.setText("");
        this.jtxtD2.setText("");
        this.jtxtD3.setText("");
        this.jtxtD4.setText("");
        this.jtxtD5.setText("");
        calcSubTotal();
    }//GEN-LAST:event_jbtnDDelActionPerformed

    private void jtxtA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtA1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtA1ActionPerformed

    private void jtxtA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtA2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtA2ActionPerformed

    private void jtxtB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtB1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtB1ActionPerformed

    private void jtxtT5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtT5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtT5ActionPerformed

    private void jtxtA4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtA4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtA4ActionPerformed

    private void jtxtB4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtB4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtB4ActionPerformed

    private void jbtnAddDRCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddDRCActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Esta acción actualizará los valores de la Factura actual?");
        if ( opt == 0 ){
            if ( jcboxDRC.getSelectedItem().equals("Reservas encontradas") || jcboxDRC.getSelectedItem().equals("Sin Reservas") ){
                this.jcboxMode.select(0);
                this.jcboxDay.select(0);
                this.jcboxPaq.select(0);
                this.jcboxHour.select(0);
                this.jtxtA1.setText("");
                this.jtxtA2.setText("");
                this.jtxtA3.setText("");
                this.jtxtA4.setText("");
                this.jtxtA5.setText("");
                this.jtxtB1.setText("");
                this.jtxtB2.setText("");
                this.jtxtB3.setText("");
                this.jtxtB4.setText("");
                this.jtxtB5.setText("");
                this.jtxtC1.setText("");
                this.jtxtC2.setText("");
                this.jtxtC3.setText("");
                this.jtxtC4.setText("");
                this.jtxtC5.setText("");
                this.jtxtD1.setText("");
                this.jtxtD2.setText("");
                this.jtxtD3.setText("");
                this.jtxtD4.setText("");
                this.jtxtD5.setText("");
                calcSubTotal();
            }
            else{
                loadDRCData();
            }
        }
    }//GEN-LAST:event_jbtnAddDRCActionPerformed

    private void jbtnResLesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnResLesActionPerformed
        int iRow = -1;
        if ( jtblStud.getSelectedRow() < 0 ){
            JOptionPane.showMessageDialog(this, "Por favor seleccione un Alumno de la lista", "TCM MSG", JOptionPane.ERROR_MESSAGE);
        }
        else{
            iRow = getStudLine(String.valueOf(jtblStud.getValueAt(jtblStud.getSelectedRow(),10)));
            if ( iRow == -1 ){
                JOptionPane.showMessageDialog(this, "El PIN del Alumno no fue encontrado en la Base de Datos", "TCM MSG", JOptionPane.ERROR_MESSAGE);
            }
            else{
                String aStudData = "Nombre del estudiante: " + alStudDB.get(iRow).getFstName() + " " + alStudDB.get(iRow).getLstName() + "\n" 
                        + "Modalidad y paquete: \n"
                        + "Día y horario: \n"
                        + "Lecciones reservadas: \n"
                        + "Profesor asignado: ";
                gui_Reserves tmpRSV = new gui_Reserves(sLocStudDBPath, alStudDB, sLocProfDBPath, alProfDB, iRow);
                tmpRSV.jtxtAreStuDat.setText(aStudData);
                tmpRSV.setLocationRelativeTo(this);
                tmpRSV.setVisible(true);
                dispose();
            }
        }
        
        
        
        
        
        
        
                
    }//GEN-LAST:event_jbtnResLesActionPerformed

    private void jcboxDRCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboxDRCItemStateChanged
       // TODO add your handling code here:
    }//GEN-LAST:event_jcboxDRCItemStateChanged

    private void jtxtB3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtB3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtB3ActionPerformed

    private void jbtnBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBDelActionPerformed
        this.jtxtB1.setText("");
        this.jtxtB2.setText("");
        this.jtxtB3.setText("");
        this.jtxtB4.setText("");
        this.jtxtB5.setText("");
        calcSubTotal();
    }//GEN-LAST:event_jbtnBDelActionPerformed

    private void jbtnCDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCDelActionPerformed
        this.jtxtC1.setText("");
        this.jtxtC2.setText("");
        this.jtxtC3.setText("");
        this.jtxtC4.setText("");
        this.jtxtC5.setText("");
        calcSubTotal();
    }//GEN-LAST:event_jbtnCDelActionPerformed

    private void jbtnNullInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNullInvActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "ADVERTENCIA:\n"
                + "Esta acción eliminará de la Base de Datos la Factura mostrada en pantalla.\n"
                + "Desea proceder?");
        if ( opt == 0 ){
            opt = JOptionPane.showConfirmDialog(this, "CONFIRME QUE DESEA ANULAR LA FACTURA EN PANTALLA");
            if ( opt == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                int iPos = getInvoLine(this.jlblInvNum.getText().substring(9));
                //Cancels the old Invoice
                cancelInvoice(iPos);
                updateInvoiceDB_txt();
                //Finds and reloads the Invoice in the screen
                iPos = getInvoLine(jtxtSrchInvo.getText());
                loadInvoice(iPos);
                setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "La factura ha sido ANULADA de la Base de Datos.");
            }
        }
    }//GEN-LAST:event_jbtnNullInvActionPerformed

    private void jbtnInvoDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnInvoDBActionPerformed
        gui_InvoiceDB tmpIDB = new gui_InvoiceDB(alInvoDB);
        tmpIDB.setLocationRelativeTo(this);
        tmpIDB.setVisible(true);
    }//GEN-LAST:event_jbtnInvoDBActionPerformed

    private void jmeniteAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmeniteAboutActionPerformed
        JOptionPane.showMessageDialog(this, "ACERCA DE TENNIS CLUB MANAGER\n"
                + "VERSION 0.2\n"
                + "Derechos: CyS Soluciones\n"
                + "Desarrollador: Juan Carlos Suárez Huertas\n"
                + "Fecha: Marzo 2018\n"
                + "Prohibida la copia y/o distribución total o parcial de esta Aplicación\n");
    }//GEN-LAST:event_jmeniteAboutActionPerformed

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
            java.util.logging.Logger.getLogger(gui_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_MainScreen().setVisible(true);
            }
        });
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Variables declaration section">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenBarTop;
    private javax.swing.JMenu jMenEdit;
    private javax.swing.JMenu jMenFile;
    private javax.swing.JMenu jMenHelp;
    private javax.swing.JMenu jMenTools;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPnlLeft;
    private javax.swing.JPanel jPnlProf;
    private javax.swing.JPanel jPnlProfBot;
    private javax.swing.JPanel jPnlStud;
    private javax.swing.JPanel jPnlStudBot;
    private javax.swing.JPanel jPnlTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTbPnMid;
    private javax.swing.JButton jbtnADel;
    private javax.swing.JButton jbtnAddDRC;
    private javax.swing.JButton jbtnBDel;
    private javax.swing.JButton jbtnCDel;
    private javax.swing.JButton jbtnClean;
    private javax.swing.JButton jbtnCostMtrx;
    private javax.swing.JButton jbtnDDel;
    private javax.swing.JButton jbtnDelStud;
    private javax.swing.JButton jbtnDelTeach;
    private javax.swing.JButton jbtnEditStud;
    private javax.swing.JButton jbtnEditTeach;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnGen;
    private javax.swing.JButton jbtnInvMail;
    private javax.swing.JButton jbtnInvoDB;
    private javax.swing.JButton jbtnNewInv;
    private javax.swing.JButton jbtnNewStud;
    private javax.swing.JButton jbtnNewTeach;
    private javax.swing.JButton jbtnNullInv;
    private javax.swing.JButton jbtnProfFind;
    private javax.swing.JButton jbtnRefreshStud;
    private javax.swing.JButton jbtnRefreshTeach;
    private javax.swing.JButton jbtnResLes;
    private javax.swing.JButton jbtnSrchInvo;
    private javax.swing.JButton jbtnStudFind;
    private javax.swing.ButtonGroup jbtngrpPayMod;
    private java.awt.Choice jcboxDRC;
    private java.awt.Choice jcboxDay;
    private java.awt.Choice jcboxHour;
    private java.awt.Choice jcboxMode;
    private java.awt.Choice jcboxPaq;
    private java.awt.Checkbox jchkboxManual;
    private java.awt.Checkbox jchkboxMemb;
    private javax.swing.JLabel jlblCoLogo;
    private javax.swing.JLabel jlblDat;
    private javax.swing.JLabel jlblDay;
    private javax.swing.JLabel jlblDesPer;
    private javax.swing.JLabel jlblDesVal;
    private javax.swing.JLabel jlblHour;
    private javax.swing.JLabel jlblInvNum;
    private javax.swing.JLabel jlblInvTop;
    private javax.swing.JLabel jlblLstInv;
    private javax.swing.JLabel jlblMail;
    private javax.swing.JLabel jlblMode;
    private javax.swing.JLabel jlblPaq;
    private javax.swing.JLabel jlblPhone;
    private javax.swing.JLabel jlblProfDBsize;
    private javax.swing.JLabel jlblStuIde;
    private javax.swing.JLabel jlblStuLev;
    private javax.swing.JLabel jlblStuNam;
    private javax.swing.JLabel jlblStudDBsize;
    private javax.swing.JLabel jlblSubDes;
    private javax.swing.JLabel jlblSubImp;
    private javax.swing.JLabel jlblTaxPer;
    private javax.swing.JLabel jlblTaxVal;
    private javax.swing.JLabel jlblTotal;
    private javax.swing.JLabel jlblVenNam;
    private javax.swing.JMenuItem jmeniteAbout;
    private javax.swing.JPanel jpnlHeader;
    private javax.swing.JPanel jpnlInvBottom;
    private javax.swing.JPanel jpnlInvMid;
    private javax.swing.JPanel jpnlInvTop;
    private javax.swing.JPanel jpnlMain;
    private javax.swing.JPanel jpnlMid;
    private javax.swing.JPanel jpnlPayMod;
    private javax.swing.JRadioButton jrbtnAuto;
    private javax.swing.JRadioButton jrbtnEfec;
    private javax.swing.JRadioButton jrbtnTarj;
    private javax.swing.JRadioButton jrbtnTran;
    private javax.swing.JTable jtblProf;
    private javax.swing.JTable jtblStud;
    private javax.swing.JTextField jtxtA1;
    private javax.swing.JTextField jtxtA2;
    private javax.swing.JTextField jtxtA3;
    private javax.swing.JTextField jtxtA4;
    private javax.swing.JTextField jtxtA5;
    private javax.swing.JTextField jtxtB1;
    private javax.swing.JTextField jtxtB2;
    private javax.swing.JTextField jtxtB3;
    private javax.swing.JTextField jtxtB4;
    private javax.swing.JTextField jtxtB5;
    private javax.swing.JTextField jtxtC1;
    private javax.swing.JTextField jtxtC2;
    private javax.swing.JTextField jtxtC3;
    private javax.swing.JTextField jtxtC4;
    private javax.swing.JTextField jtxtC5;
    private javax.swing.JTextField jtxtD1;
    private javax.swing.JTextField jtxtD2;
    private javax.swing.JTextField jtxtD3;
    private javax.swing.JTextField jtxtD4;
    private javax.swing.JTextField jtxtD5;
    private javax.swing.JTextField jtxtDat;
    private javax.swing.JTextField jtxtDesPer;
    private javax.swing.JTextField jtxtDesVal;
    private javax.swing.JTextField jtxtMail;
    private javax.swing.JTextField jtxtPhone;
    private javax.swing.JTextField jtxtProfFind;
    private javax.swing.JTextField jtxtSrchInvo;
    private javax.swing.JTextField jtxtStuLev;
    private javax.swing.JTextField jtxtStuNam;
    private javax.swing.JTextField jtxtStuPIN;
    private javax.swing.JTextField jtxtStudFind;
    private javax.swing.JTextField jtxtSubDes;
    private javax.swing.JTextField jtxtSubTax;
    private javax.swing.JTextField jtxtSubTot;
    private javax.swing.JTextField jtxtT1;
    private javax.swing.JTextField jtxtT2;
    private javax.swing.JTextField jtxtT3;
    private javax.swing.JTextField jtxtT4;
    private javax.swing.JTextField jtxtT5;
    private javax.swing.JTextField jtxtTaxPer;
    private javax.swing.JTextField jtxtTaxVal;
    private javax.swing.JTextField jtxtTotal;
    private javax.swing.JTextField jtxtVenNam;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
