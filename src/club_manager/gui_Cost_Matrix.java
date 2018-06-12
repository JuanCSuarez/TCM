
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
import jxl.Sheet;



public class gui_Cost_Matrix extends javax.swing.JFrame {
    
    
    javax.swing.table.DefaultTableModel tblModelLV = new javax.swing.table.DefaultTableModel();
    Object[] LVColumn = new Object [14];
    javax.swing.table.DefaultTableModel tblModelFS = new javax.swing.table.DefaultTableModel();
    Object[] FSColumn = new Object [14];
    javax.swing.table.DefaultTableModel tblModelMT = new javax.swing.table.DefaultTableModel();
    Object[] MTColumn = new Object [14];
    javax.swing.table.DefaultTableModel tblModelMB = new javax.swing.table.DefaultTableModel();
    Object[] MBColumn = new Object [2];
    
    //ArrayList that will store the complete data base of Teachers
    private ArrayList<cls_Cost_Item> alCostDB = new ArrayList<>();
   
    String[][] arrMemb = new String[12][2];
    
    private String sLocCostDBPath = "";
    private String sLocMembDBPath = "";

    //Bidimentional String Array that will store all the data from an .xls file
    private String[][] xlsDataMatrix;
    
    public gui_Cost_Matrix(String sLocCostDBPath, String sLocMembDBPath) {
        initComponents();
        System.out.println("Starting Costs Matrix Control");
        setLocationRelativeTo(null);
        setTitle("TCM - Matriz de Costos");
        setResizable(false);
        
        //Acepts the path of the Costs Data Base
        this.sLocCostDBPath = sLocCostDBPath;
        this.sLocMembDBPath = sLocMembDBPath;
        
        
        //Configures the Tables
        configLVTable();
        configFSTable();
        configMTTable();
        configMBTable();
        
        //Loads the data from the local TXT DB
        loadCostDB();//From the TXT file into the AL
        loadMembDB();//From the TXT file into the Arr
        loadCostTables();//From the AL into the JT
        loadMBTable();
        //loadMembTable();
        
        
    }

    private gui_Cost_Matrix() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Prepares the JTable columns in order to receive the list of Costs
    private void configLVTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelLV.addColumn("Hora");
        tblModelLV.addColumn("Horario");
        tblModelLV.addColumn("N1-Indiv");
        tblModelLV.addColumn("N1-Grup2");
        tblModelLV.addColumn("N1-Grup3");
        tblModelLV.addColumn("N1-Grup4");
        tblModelLV.addColumn("N2-Indiv");
        tblModelLV.addColumn("N2-Grup2");
        tblModelLV.addColumn("N2-Grup3");
        tblModelLV.addColumn("N2-Grup4");
        tblModelLV.addColumn("HP-Indiv");
        tblModelLV.addColumn("HP-Grup2");
        tblModelLV.addColumn("HP-Grup3");
        tblModelLV.addColumn("HP-Grup4");
        jtblLV.setModel(tblModelLV);
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblLV.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.orange);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblLV.setAutoResizeMode(jtblLV.AUTO_RESIZE_OFF);
        jtblLV.setRowHeight(24);
        jtblLV.getColumnModel().getColumn(0).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(0).setResizable(false);
        jtblLV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(1).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(1).setResizable(false);
        jtblLV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(2).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(2).setResizable(false);
        jtblLV.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(3).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(3).setResizable(false);
        jtblLV.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(4).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(4).setResizable(false);
        jtblLV.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);        
        jtblLV.getColumnModel().getColumn(5).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(5).setResizable(false);
        jtblLV.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(6).setResizable(false);
        jtblLV.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(7).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(7).setResizable(false);
        jtblLV.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(8).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(8).setResizable(false);
        jtblLV.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(9).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(9).setResizable(false);
        jtblLV.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(10).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(10).setResizable(false);
        jtblLV.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(11).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(11).setResizable(false);
        jtblLV.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(12).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(12).setResizable(false);
        jtblLV.getColumnModel().getColumn(12).setCellRenderer(centerRenderer);
        jtblLV.getColumnModel().getColumn(13).setPreferredWidth(100);
        jtblLV.getColumnModel().getColumn(13).setResizable(false);
        jtblLV.getColumnModel().getColumn(13).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Prepares the JTable columns in order to receive the list of Costs
    private void configFSTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelFS.addColumn("Hora");
        tblModelFS.addColumn("Horario");
        tblModelFS.addColumn("N1-Indiv");
        tblModelFS.addColumn("N1-Grup2");
        tblModelFS.addColumn("N1-Grup3");
        tblModelFS.addColumn("N1-Grup4");
        tblModelFS.addColumn("N2-Indiv");
        tblModelFS.addColumn("N2-Grup2");
        tblModelFS.addColumn("N2-Grup3");
        tblModelFS.addColumn("N2-Grup4");
        tblModelFS.addColumn("HP-Indiv");
        tblModelFS.addColumn("HP-Grup2");
        tblModelFS.addColumn("HP-Grup3");
        tblModelFS.addColumn("HP-Grup4");
        jtblFS.setModel(tblModelFS);
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblFS.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.CYAN);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblFS.setAutoResizeMode(jtblFS.AUTO_RESIZE_OFF);
        jtblFS.setRowHeight(24);
        jtblFS.getColumnModel().getColumn(0).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(0).setResizable(false);
        jtblFS.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(1).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(1).setResizable(false);
        jtblFS.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(2).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(2).setResizable(false);
        jtblFS.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(3).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(3).setResizable(false);
        jtblFS.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(4).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(4).setResizable(false);
        jtblFS.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);        
        jtblFS.getColumnModel().getColumn(5).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(5).setResizable(false);
        jtblFS.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(6).setResizable(false);
        jtblFS.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(7).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(7).setResizable(false);
        jtblFS.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(8).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(8).setResizable(false);
        jtblFS.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(9).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(9).setResizable(false);
        jtblFS.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(10).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(10).setResizable(false);
        jtblFS.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(11).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(11).setResizable(false);
        jtblFS.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(12).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(12).setResizable(false);
        jtblFS.getColumnModel().getColumn(12).setCellRenderer(centerRenderer);
        jtblFS.getColumnModel().getColumn(13).setPreferredWidth(100);
        jtblFS.getColumnModel().getColumn(13).setResizable(false);
        jtblFS.getColumnModel().getColumn(13).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Prepares the JTable columns in order to receive the list of Costs
    private void configMTTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelMT.addColumn("Hora");
        tblModelMT.addColumn("Horario");
        tblModelMT.addColumn("N1-Indiv");
        tblModelMT.addColumn("N1-Grup2");
        tblModelMT.addColumn("N1-Grup3");
        tblModelMT.addColumn("N1-Grup4");
        tblModelMT.addColumn("N2-Indiv");
        tblModelMT.addColumn("N2-Grup2");
        tblModelMT.addColumn("N2-Grup3");
        tblModelMT.addColumn("N2-Grup4");
        tblModelMT.addColumn("HP-Indiv");
        tblModelMT.addColumn("HP-Grup2");
        tblModelMT.addColumn("HP-Grup3");
        tblModelMT.addColumn("HP-Grup4");
        jtblMT.setModel(tblModelMT);
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblMT.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.YELLOW);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblMT.setAutoResizeMode(jtblMT.AUTO_RESIZE_OFF);
        jtblMT.setRowHeight(24);
        jtblMT.getColumnModel().getColumn(0).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(0).setResizable(false);
        jtblMT.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(1).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(1).setResizable(false);
        jtblMT.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(2).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(2).setResizable(false);
        jtblMT.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(3).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(3).setResizable(false);
        jtblMT.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(4).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(4).setResizable(false);
        jtblMT.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);        
        jtblMT.getColumnModel().getColumn(5).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(5).setResizable(false);
        jtblMT.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(6).setResizable(false);
        jtblMT.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(7).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(7).setResizable(false);
        jtblMT.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(8).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(8).setResizable(false);
        jtblMT.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(9).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(9).setResizable(false);
        jtblMT.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(10).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(10).setResizable(false);
        jtblMT.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(11).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(11).setResizable(false);
        jtblMT.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(12).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(12).setResizable(false);
        jtblMT.getColumnModel().getColumn(12).setCellRenderer(centerRenderer);
        jtblMT.getColumnModel().getColumn(13).setPreferredWidth(100);
        jtblMT.getColumnModel().getColumn(13).setResizable(false);
        jtblMT.getColumnModel().getColumn(13).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Prepares the JTable columns in order to receive the list of Costs
    private void configMBTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelMB.addColumn("MES");
        tblModelMB.addColumn("COSTO");
        jtblMB.setModel(tblModelMB);
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblMB.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblMB.setAutoResizeMode(jtblMB.AUTO_RESIZE_OFF);
        jtblMB.setRowHeight(24);
        jtblMB.getColumnModel().getColumn(0).setPreferredWidth(100);
        jtblMB.getColumnModel().getColumn(0).setResizable(false);
        jtblMB.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblMB.getColumnModel().getColumn(1).setPreferredWidth(100);
        jtblMB.getColumnModel().getColumn(1).setResizable(false);
        jtblMB.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }
    //</editor-fold>
    
    //Cleans the LV List JTable
    private void cleanLVTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelLV.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelLV.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de L-V\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Cleans the FS List JTable
    private void cleanFSTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelFS.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelFS.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de Fin de Semana\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Cleans the MT List JTable
    private void cleanMTTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelMT.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelMT.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de Fin de Semana Mini Tennis\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Cleans the MB List JTable
    private void cleanMBTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelMB.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelMB.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de costos de Membresía\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Loads the information from the 2d-Matrix (Excel file) into LV JTable
    private void loadLVTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading data from String Matrix into screen JTable");
        int r;
        for ( r=0; r<4; r++ ){
            LVColumn[0] = xlsDataMatrix[r][0];
            LVColumn[1] = xlsDataMatrix[r][1];
            LVColumn[2] = xlsDataMatrix[r][2];
            LVColumn[3] = xlsDataMatrix[r][3];
            LVColumn[4] = xlsDataMatrix[r][4];
            LVColumn[5] = xlsDataMatrix[r][5];
            LVColumn[6] = xlsDataMatrix[r][6];
            LVColumn[7] = xlsDataMatrix[r][7];
            LVColumn[8] = xlsDataMatrix[r][8];
            LVColumn[9] = xlsDataMatrix[r][9];
            LVColumn[10] = xlsDataMatrix[r][10];
            LVColumn[11] = xlsDataMatrix[r][11];
            LVColumn[12] = xlsDataMatrix[r][12];
            LVColumn[13] = xlsDataMatrix[r][13];
            
            tblModelLV.addRow(LVColumn);
            jtblLV.setModel(tblModelLV);
        }
        System.out.println("Matrix loaded in the screen's JTable");
    }
    //</editor-fold>
    
    //Loads the information from the 2d-Matrix (Excel file) into FS JTable
    private void loadFSTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading data from String Matrix into screen JTable");
        int r;
        for ( r=4; r<8; r++ ){
            FSColumn[0] = xlsDataMatrix[r][0];
            FSColumn[1] = xlsDataMatrix[r][1];
            FSColumn[2] = xlsDataMatrix[r][2];
            FSColumn[3] = xlsDataMatrix[r][3];
            FSColumn[4] = xlsDataMatrix[r][4];
            FSColumn[5] = xlsDataMatrix[r][5];
            FSColumn[6] = xlsDataMatrix[r][6];
            FSColumn[7] = xlsDataMatrix[r][7];
            FSColumn[8] = xlsDataMatrix[r][8];
            FSColumn[9] = xlsDataMatrix[r][9];
            FSColumn[10] = xlsDataMatrix[r][10];
            FSColumn[11] = xlsDataMatrix[r][11];
            FSColumn[12] = xlsDataMatrix[r][12];
            FSColumn[13] = xlsDataMatrix[r][13];
            
            tblModelFS.addRow(FSColumn);
            jtblFS.setModel(tblModelFS);
        }
        System.out.println("Matrix loaded in the screen's JTable");
    }
    //</editor-fold>
    
    //Loads the information from the 2d-Matrix (Excel file) into MT JTable
    private void loadMTTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading data from String Matrix into screen JTable");
        int r;
        for ( r=8; r<12; r++ ){
            MTColumn[0] = xlsDataMatrix[r][0];
            MTColumn[1] = xlsDataMatrix[r][1];
            MTColumn[2] = xlsDataMatrix[r][2];
            MTColumn[3] = xlsDataMatrix[r][3];
            MTColumn[4] = xlsDataMatrix[r][4];
            MTColumn[5] = xlsDataMatrix[r][5];
            MTColumn[6] = xlsDataMatrix[r][6];
            MTColumn[7] = xlsDataMatrix[r][7];
            MTColumn[8] = xlsDataMatrix[r][8];
            MTColumn[9] = xlsDataMatrix[r][9];
            MTColumn[10] = xlsDataMatrix[r][10];
            MTColumn[11] = xlsDataMatrix[r][11];
            MTColumn[12] = xlsDataMatrix[r][12];
            MTColumn[13] = xlsDataMatrix[r][13];
            
            tblModelMT.addRow(MTColumn);
            jtblMT.setModel(tblModelMT);
        }
        System.out.println("Matrix loaded in the screen's JTable");
    }
    //</editor-fold>
    
    //Loads the information from the 2d-Matrix into MB JTable
    private void loadMBTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading data from String Matrix into screen JTable");
        int r;
        for ( r=0; r<12; r++ ){
            MBColumn[0] = arrMemb[r][0];
            MBColumn[1] = arrMemb[r][1];
            
            tblModelMB.addRow(MBColumn);
            jtblMB.setModel(tblModelMB);
        }
        System.out.println("Matrix loaded in the screen's JTable");
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
    
    //Loads the information from the Costs´s Data Base ArrayList into the corresponding JTables
    private void loadCostTables(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        System.out.println("Loading data from Cost ArralyList into the JTables");
        String sMode = "";
        for ( cls_Cost_Item tmp: this.alCostDB ){
            //Loads the LV table
            LVColumn[0] = tmp.getHourLV();
            LVColumn[1] = tmp.getSchdLV();
            LVColumn[2] = tmp.getN1InLV();
            LVColumn[3] = tmp.getN1G2LV();
            LVColumn[4] = tmp.getN1G3LV();
            LVColumn[5] = tmp.getN1G4LV();
//            LVColumn[6] = tmp.getN1GPLV();
            LVColumn[6] = tmp.getN2InLV();
            LVColumn[7] = tmp.getN2G2LV();
            LVColumn[8] = tmp.getN2G3LV();
            LVColumn[9] = tmp.getN2G4LV();
//            LVColumn[11] = tmp.getN2GPLV();
            LVColumn[10] = tmp.getHPInLV();
            LVColumn[11] = tmp.getHPG2LV();
            LVColumn[12] = tmp.getHPG3LV();
            LVColumn[13] = tmp.getHPG4LV();
//            LVColumn[16] = tmp.getHPGPLV();
            //Loads the FS table
            FSColumn[0] = tmp.getHourFS();
            FSColumn[1] = tmp.getSchdFS();
            FSColumn[2] = tmp.getN1InFS();
            FSColumn[3] = tmp.getN1G2FS();
            FSColumn[4] = tmp.getN1G3FS();
            FSColumn[5] = tmp.getN1G4FS();
//            FSColumn[6] = tmp.getN1GPFS();
            FSColumn[6] = tmp.getN2InFS();
            FSColumn[7] = tmp.getN2G2FS();
            FSColumn[8] = tmp.getN2G3FS();
            FSColumn[9] = tmp.getN2G4FS();
//            FSColumn[11] = tmp.getN2GPFS();
            FSColumn[10] = tmp.getHPInFS();
            FSColumn[11] = tmp.getHPG2FS();
            FSColumn[12] = tmp.getHPG3FS();
            FSColumn[13] = tmp.getHPG4FS();
//            FSColumn[16] = tmp.getHPGPFS();
            //Loads the MT table
            MTColumn[0] = tmp.getHourMT();
            MTColumn[1] = tmp.getSchdMT();
            MTColumn[2] = tmp.getN1InMT();
            MTColumn[3] = tmp.getN1G2MT();
            MTColumn[4] = tmp.getN1G3MT();
            MTColumn[5] = tmp.getN1G4MT();
//            MTColumn[6] = tmp.getN1GPMT();
            MTColumn[6] = tmp.getN2InMT();
            MTColumn[7] = tmp.getN2G2MT();
            MTColumn[8] = tmp.getN2G3MT();
            MTColumn[9] = tmp.getN2G4MT();
//            MTColumn[11] = tmp.getN2GPMT();
            MTColumn[10] = tmp.getHPInMT();
            MTColumn[11] = tmp.getHPG2MT();
            MTColumn[12] = tmp.getHPG3MT();
            MTColumn[13] = tmp.getHPG4MT();
//            MTColumn[16] = tmp.getHPGPMT();
            tblModelLV.addRow(LVColumn);
            jtblLV.setModel(tblModelLV);
            tblModelFS.addRow(FSColumn);
            jtblFS.setModel(tblModelFS);
            tblModelMT.addRow(MTColumn);
            jtblMT.setModel(tblModelMT);

        }
        System.out.println("Costs ArrayList loaded in the Costs's JTables");
    }
    //</editor-fold>
    
    //Updates the local Costs Data Base ArrayList according with the changes on the screen Jtables
    private void updateCostsALDataBase() {
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        alCostDB.clear();
        String sHourLV="", sSchdLV="", sN1InLV="", sN1G2LV="", sN1G3LV="", 
            sN1G4LV="", sN1GPLV="", sN2InLV="", sN2G2LV="", sN2G3LV="", sN2G4LV="", 
            sN2GPLV="", sHPInLV="", sHPG2LV="", sHPG3LV="", sHPG4LV="", sHPGPLV="", 
            sHourFS="", sSchdFS="", sN1InFS="", sN1G2FS="", sN1G3FS="", sN1G4FS="", 
            sN1GPFS="", sN2InFS="", sN2G2FS="", sN2G3FS="", sN2G4FS="", sN2GPFS="", 
            sHPInFS="", sHPG2FS="", sHPG3FS="", sHPG4FS="", sHPGPFS="",
            sHourMT="", sSchdMT="", sN1InMT="", sN1G2MT="", sN1G3MT="", sN1G4MT="", 
            sN1GPMT="", sN2InMT="", sN2G2MT="", sN2G3MT="", sN2G4MT="", sN2GPMT="", 
            sHPInMT="", sHPG2MT="", sHPG3MT="", sHPG4MT="", sHPGPMT="";
        for ( int i=0; i < this.jtblLV.getRowCount(); i++ ){
            //Gets the values from the LV Table
            sHourLV = jtblLV.getValueAt(i, 0).toString(); 
            sSchdLV = jtblLV.getValueAt(i, 1).toString(); 
            sN1InLV = jtblLV.getValueAt(i, 2).toString();
            sN1G2LV = jtblLV.getValueAt(i, 3).toString(); 
            sN1G3LV = jtblLV.getValueAt(i, 4).toString(); 
            sN1G4LV = jtblLV.getValueAt(i, 5).toString(); 
            sN1GPLV = "N/A"; 
            sN2InLV = jtblLV.getValueAt(i, 6).toString(); 
            sN2G2LV = jtblLV.getValueAt(i, 7).toString(); 
            sN2G3LV = jtblLV.getValueAt(i, 8).toString(); 
            sN2G4LV = jtblLV.getValueAt(i, 9).toString(); 
            sN2GPLV = "N/A"; 
            sHPInLV = jtblLV.getValueAt(i, 10).toString(); 
            sHPG2LV = jtblLV.getValueAt(i, 11).toString(); 
            sHPG3LV = jtblLV.getValueAt(i, 12).toString(); 
            sHPG4LV = jtblLV.getValueAt(i, 13).toString(); 
            sHPGPLV = "N/A";
            //Gets the values from the FS table
            sHourFS = jtblFS.getValueAt(i, 0).toString(); 
            sSchdFS = jtblFS.getValueAt(i, 1).toString(); 
            sN1InFS = jtblFS.getValueAt(i, 2).toString(); 
            sN1G2FS = jtblFS.getValueAt(i, 3).toString(); 
            sN1G3FS = jtblFS.getValueAt(i, 4).toString(); 
            sN1G4FS = jtblFS.getValueAt(i, 5).toString(); 
            sN1GPFS = "N/A"; 
            sN2InFS = jtblFS.getValueAt(i, 6).toString(); 
            sN2G2FS = jtblFS.getValueAt(i, 7).toString(); 
            sN2G3FS = jtblFS.getValueAt(i, 8).toString(); 
            sN2G4FS = jtblFS.getValueAt(i, 9).toString(); 
            sN2GPFS = "N/A"; 
            sHPInFS = jtblFS.getValueAt(i, 10).toString(); 
            sHPG2FS = jtblFS.getValueAt(i, 11).toString(); 
            sHPG3FS = jtblFS.getValueAt(i, 12).toString(); 
            sHPG4FS = jtblFS.getValueAt(i, 13).toString(); 
            sHPGPFS = "N/A";
            //Gets the values from the MT table
            sHourMT = jtblMT.getValueAt(i, 0).toString(); 
            sSchdMT = jtblMT.getValueAt(i, 1).toString(); 
            sN1InMT = jtblMT.getValueAt(i, 2).toString(); 
            sN1G2MT = jtblMT.getValueAt(i, 3).toString(); 
            sN1G3MT = jtblMT.getValueAt(i, 4).toString(); 
            sN1G4MT = jtblMT.getValueAt(i, 5).toString(); 
            sN1GPMT = "N/A"; 
            sN2InMT = jtblMT.getValueAt(i, 6).toString(); 
            sN2G2MT = jtblMT.getValueAt(i, 7).toString(); 
            sN2G3MT = jtblMT.getValueAt(i, 8).toString(); 
            sN2G4MT = jtblMT.getValueAt(i, 9).toString(); 
            sN2GPMT = "N/A"; 
            sHPInMT = jtblMT.getValueAt(i, 10).toString(); 
            sHPG2MT = jtblMT.getValueAt(i, 11).toString(); 
            sHPG3MT = jtblMT.getValueAt(i, 12).toString(); 
            sHPG4MT = jtblMT.getValueAt(i, 13).toString(); 
            sHPGPMT = "N/A";
            alCostDB.add(new cls_Cost_Item(sHourLV, sSchdLV, sN1InLV, sN1G2LV, sN1G3LV,
                    sN1G4LV, sN1GPLV, sN2InLV, sN2G2LV, sN2G3LV, sN2G4LV, 
                    sN2GPLV, sHPInLV, sHPG2LV, sHPG3LV, sHPG4LV, sHPGPLV, 
                    sHourFS, sSchdFS, sN1InFS, sN1G2FS, sN1G3FS, sN1G4FS, 
                    sN1GPFS, sN2InFS, sN2G2FS, sN2G3FS, sN2G4FS, sN2GPFS, 
                    sHPInFS, sHPG2FS, sHPG3FS, sHPG4FS, sHPGPFS,
                    sHourMT, sSchdMT, sN1InMT, sN1G2MT, sN1G3MT, sN1G4MT, 
                    sN1GPMT, sN2InMT, sN2G2MT, sN2G3MT, sN2G4MT, sN2GPMT, 
                    sHPInMT, sHPG2MT, sHPG3MT, sHPG4MT, sHPGPMT));
        }
        this.cleanLVTable();
        this.cleanFSTable();
        this.cleanMTTable();
        loadCostTables();
    }
    //</editor-fold>
    
    //Updates the local Membership Data Base 2-D Array according with the changes on the screen Jtables
    private void updateMembArDataBase() {
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        String sMonth="", sMembr="";
        for ( int i=0; i < 12; i++ ){
            //Gets the values from the MB Table
            sMonth = jtblMB.getValueAt(i, 0).toString(); 
            sMembr = jtblMB.getValueAt(i, 1).toString(); 
            this.arrMemb[i][0] = sMonth;
            this.arrMemb[i][1] = sMembr;
        }
        this.cleanMBTable();
        loadMBTable();
    }
    //</editor-fold>
    
    //Updates the local .TXT Costs Data Base file directly from the Costs Data Base ArrayList
    public void updateCostsDB_txt(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        try{
            File fDataBase;
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter wr = null;
            fDataBase = new File (this.sLocCostDBPath); //points to the local .txt Costs data base file
            fw = new FileWriter(fDataBase);
            bw = new BufferedWriter(fw);
            wr = new PrintWriter(bw);
            //Reads, line by line, all the Costs that are currently in the Data Base Array List
            for(cls_Cost_Item tmp: this.alCostDB){
                wr.println(tmp.getHourLV() + "\t"
                        + tmp.getSchdLV() + "\t"
                        + tmp.getN1InLV() + "\t"
                        + tmp.getN1G2LV() + "\t"
                        + tmp.getN1G3LV() + "\t"
                        + tmp.getN1G4LV() + "\t"
                        + tmp.getN1GPLV() + "\t"
                        + tmp.getN2InLV() + "\t"
                        + tmp.getN2G2LV() + "\t"
                        + tmp.getN2G3LV() + "\t"
                        + tmp.getN2G4LV() + "\t"
                        + tmp.getN2GPLV() + "\t"
                        + tmp.getHPInLV() + "\t"
                        + tmp.getHPG2LV() + "\t"
                        + tmp.getHPG3LV() + "\t"
                        + tmp.getHPG4LV() + "\t"
                        + tmp.getHPGPLV() + "\t"    
                        + tmp.getHourFS() + "\t"
                        + tmp.getSchdFS() + "\t"
                        + tmp.getN1InFS() + "\t"
                        + tmp.getN1G2FS() + "\t"
                        + tmp.getN1G3FS() + "\t"
                        + tmp.getN1G4FS() + "\t"
                        + tmp.getN1GPFS() + "\t"
                        + tmp.getN2InFS() + "\t"
                        + tmp.getN2G2FS() + "\t"
                        + tmp.getN2G3FS() + "\t"
                        + tmp.getN2G4FS() + "\t"
                        + tmp.getN2GPFS() + "\t"
                        + tmp.getHPInFS() + "\t"
                        + tmp.getHPG2FS() + "\t"
                        + tmp.getHPG3FS() + "\t"
                        + tmp.getHPG4FS() + "\t"
                        + tmp.getHPGPFS() + "\t"
                        + tmp.getHourMT() + "\t"
                        + tmp.getSchdMT() + "\t"
                        + tmp.getN1InMT() + "\t"
                        + tmp.getN1G2MT() + "\t"
                        + tmp.getN1G3MT() + "\t"
                        + tmp.getN1G4MT() + "\t"
                        + tmp.getN1GPMT() + "\t"
                        + tmp.getN2InMT() + "\t"
                        + tmp.getN2G2MT() + "\t"
                        + tmp.getN2G3MT() + "\t"
                        + tmp.getN2G4MT() + "\t"
                        + tmp.getN2GPMT() + "\t"
                        + tmp.getHPInMT() + "\t"
                        + tmp.getHPG2MT() + "\t"
                        + tmp.getHPG3MT() + "\t"
                        + tmp.getHPG4MT() + "\t"
                        + tmp.getHPGPMT());
            }
            wr.println("END");
            wr.close();
            bw.close();
            fw.close();
        }
        catch(IOException e){JOptionPane.showMessageDialog(this,"Ocurrió un error mientras se actualizaba la Base de Datos de Costos:\n"
                + e, "TCM ERROR - updateCostsDB_txt()", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    //Updates the local .TXT Membership Data Base file directly from the Membership Data Base 2D-Array
    public void updateMembrDB_txt(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        try{
            File fDataBase;
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter wr = null;
            fDataBase = new File (this.sLocMembDBPath); //points to the local .txt Costs data base file
            fw = new FileWriter(fDataBase);
            bw = new BufferedWriter(fw);
            wr = new PrintWriter(bw);
            //Reads, line by line, all the Costs that are currently in the Data Base Array List
            for ( int i=0; i<12; i++ ){
                wr.println(arrMemb[i][0] + "\t" + arrMemb[i][1] );
            }
            wr.println("END");
            wr.close();
            bw.close();
            fw.close();
        }
        catch(IOException e){JOptionPane.showMessageDialog(this,"Ocurrió un error mientras se actualizaba la Base de Datos de Costos:\n"
                + e, "TCM ERROR - updateCostsDB_txt()", JOptionPane.ERROR_MESSAGE);}
    }
    //</editor-fold>
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlTop = new javax.swing.JPanel();
        jlblTop = new javax.swing.JLabel();
        jpnlBottom = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jbtnUndo = new javax.swing.JButton();
        jbtnImp = new javax.swing.JButton();
        jtbpnCost = new javax.swing.JTabbedPane();
        jpnlMid1 = new javax.swing.JPanel();
        jlblD1 = new javax.swing.JLabel();
        jlblD2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblLV = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblFS = new javax.swing.JTable();
        jpnlMid2 = new javax.swing.JPanel();
        jlblMT = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblMT = new javax.swing.JTable();
        jpnlMid3 = new javax.swing.JPanel();
        jlblMB = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtblMB = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jpnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblTop.setFont(new java.awt.Font("Vacation Postcard NF", 1, 30)); // NOI18N
        jlblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTop.setText("Matriz de Costos");

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
        jbtnExit.setToolTipText("Regresa a la ventana incial");
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        jbtnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save_medium.png"))); // NOI18N
        jbtnSave.setText("Guardar");
        jbtnSave.setToolTipText("Guarda los valores actuales en la base de datos");
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jbtnUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Undo_Mid.png"))); // NOI18N
        jbtnUndo.setText("Deshacer");
        jbtnUndo.setToolTipText("Deshace los cambios no guardados");
        jbtnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUndoActionPerformed(evt);
            }
        });

        jbtnImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/import_medium.png"))); // NOI18N
        jbtnImp.setText("IMPORTAR");
        jbtnImp.setToolTipText("Importa archivo de Excel con los datos de Costos");
        jbtnImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBottomLayout = new javax.swing.GroupLayout(jpnlBottom);
        jpnlBottom.setLayout(jpnlBottomLayout);
        jpnlBottomLayout.setHorizontalGroup(
            jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnImp, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnlBottomLayout.setVerticalGroup(
            jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnExit)
                        .addComponent(jbtnSave)
                        .addComponent(jbtnImp))
                    .addComponent(jbtnUndo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jpnlMid1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jlblD1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlblD1.setText("Lunes a Viernes");

        jlblD2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlblD2.setText("Fines de semana");

        jtblLV.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblLV);

        jtblFS.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtblFS);

        javax.swing.GroupLayout jpnlMid1Layout = new javax.swing.GroupLayout(jpnlMid1);
        jpnlMid1.setLayout(jpnlMid1Layout);
        jpnlMid1Layout.setHorizontalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addGroup(jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblD2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblD1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jpnlMid1Layout.setVerticalGroup(
            jpnlMid1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblD1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblD2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtbpnCost.addTab("Lista de Costos", jpnlMid1);

        jlblMT.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlblMT.setText("Fines de Semana");

        jtblMT.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jtblMT);

        javax.swing.GroupLayout jpnlMid2Layout = new javax.swing.GroupLayout(jpnlMid2);
        jpnlMid2.setLayout(jpnlMid2Layout);
        jpnlMid2Layout.setHorizontalGroup(
            jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                    .addGroup(jpnlMid2Layout.createSequentialGroup()
                        .addComponent(jlblMT, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnlMid2Layout.setVerticalGroup(
            jpnlMid2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblMT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jtbpnCost.addTab("Mini Tennis", jpnlMid2);

        jlblMB.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jlblMB.setText("Valores de Membresía");

        jtblMB.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jtblMB);

        javax.swing.GroupLayout jpnlMid3Layout = new javax.swing.GroupLayout(jpnlMid3);
        jpnlMid3.setLayout(jpnlMid3Layout);
        jpnlMid3Layout.setHorizontalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblMB, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(780, Short.MAX_VALUE))
        );
        jpnlMid3Layout.setVerticalGroup(
            jpnlMid3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlMid3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblMB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jtbpnCost.addTab("Membresía", jpnlMid3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnlBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtbpnCost, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtbpnCost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpnlBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jbtnImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImpActionPerformed
        //Cleans the Excel 2D-Array
        xlsDataMatrix = null;
        System.out.println("Preparing to import Costs Matrix Excel data sheet");
        //Preparing a class Excel_Manager instance in order to import and create a bidimensional Array with data from .xls or .csv files
        cls_Excel_Manager xlsManager = new cls_Excel_Manager();
        //Imports a File (Excel file) from the HDD
        File fl  = xlsManager.importXLSFile();
        //Cheks if the File has been imported or not
        if ( fl == null ){
            JOptionPane.showMessageDialog(this, "ERROR: El archivo no fue importado");
        }
        else{
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //Cleans the data tables
            cleanLVTable();
            cleanFSTable();
            cleanMTTable();
            //Gets the first Sheet of the File -if it exists-
            Sheet sh = xlsManager.createExcelSheet(fl);
            //Creates a Bidimentional Array with that Sheet
            xlsDataMatrix = xlsManager.loadXLSsheet_toArray(sh);
            loadLVTable();
            loadFSTable();
            loadMTTable();
            setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_jbtnImpActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        int opc = JOptionPane.showConfirmDialog(this,"AVERTENCIA: Esta acción sobreescribirá la Base de Datos de Costos?\n Desea continuar?");
            if ( opc == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                //Updates the AL with the info in the JTs
                updateCostsALDataBase();
                updateMembArDataBase();
                //Saves the AL inf into the local TXT file
                updateCostsDB_txt();
                updateMembrDB_txt();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                JOptionPane.showMessageDialog(this, "Los cambios han sido salvados");
            }
            else{
                JOptionPane.showMessageDialog(this, "No se guardaron cambios");
            }
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUndoActionPerformed
        int opc = JOptionPane.showConfirmDialog(this,"Desea revertir los cambios no salvados?");
            if ( opc == 0 ){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                this.cleanFSTable();
                this.cleanLVTable();
                this.cleanMTTable();
                this.cleanMBTable();
                this.loadCostDB();
                this.loadMembDB();
                this.loadCostTables();
                this.loadMBTable();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                JOptionPane.showMessageDialog(this, "Los cambios han sido descartados");
            }
    }//GEN-LAST:event_jbtnUndoActionPerformed

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
            java.util.logging.Logger.getLogger(gui_Cost_Matrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_Cost_Matrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_Cost_Matrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_Cost_Matrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_Cost_Matrix().setVisible(true);
            }
        });
    }

    
    //<editor-fold defaultstate="collapsed" desc="Variables declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnImp;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JButton jbtnUndo;
    private javax.swing.JLabel jlblD1;
    private javax.swing.JLabel jlblD2;
    private javax.swing.JLabel jlblMB;
    private javax.swing.JLabel jlblMT;
    private javax.swing.JLabel jlblTop;
    private javax.swing.JPanel jpnlBottom;
    private javax.swing.JPanel jpnlMid1;
    private javax.swing.JPanel jpnlMid2;
    private javax.swing.JPanel jpnlMid3;
    private javax.swing.JPanel jpnlTop;
    private javax.swing.JTable jtblFS;
    private javax.swing.JTable jtblLV;
    private javax.swing.JTable jtblMB;
    private javax.swing.JTable jtblMT;
    private javax.swing.JTabbedPane jtbpnCost;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
