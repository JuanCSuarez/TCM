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


public class gui_InvoiceDB extends javax.swing.JFrame {
    
    javax.swing.table.DefaultTableModel tblModelInvDB = new javax.swing.table.DefaultTableModel();
    Object[] InvDBColumn = new Object [9];
    
    private ArrayList<cls_Invoice> alInvoDB;
    
    private String sTotEfec;
    private String sTotCard;
    private String sTotTran;
    private String sTotAuto;
    
    
    public gui_InvoiceDB(ArrayList<cls_Invoice> alInvoDB) {
        initComponents();
        System.out.println("Starting Invoice DB Management Module");
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("TCM - BASE DE DATOS DE FACTURACIÓN");
        
        this.alInvoDB = alInvoDB;
        
        configInvDBTable();
        
        
        
    }

    private gui_InvoiceDB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Prepares the JTable columns in order to receive the list of Invoice Lines
    private void configInvDBTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        tblModelInvDB.addColumn("Fecha");
        tblModelInvDB.addColumn("Alumno");
        tblModelInvDB.addColumn("Factura");
        tblModelInvDB.addColumn("Línea");
        tblModelInvDB.addColumn("Descripción");
        tblModelInvDB.addColumn("QTY");
        tblModelInvDB.addColumn("Monto");
        tblModelInvDB.addColumn("Código");
        tblModelInvDB.addColumn("Forma de Pago");
        jtblInvDB.setModel(tblModelInvDB);
        //Allows the user to sort the items by Column
        jtblInvDB.setAutoCreateRowSorter(true);        
        //Prepares the Table to aling values to center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer FontRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Preparing the header line
        JTableHeader header = jtblInvDB.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.YELLOW);
        header.setReorderingAllowed(false); //will not allow the user to reorder the columns position
        //Configure rows and columns
        jtblInvDB.setAutoResizeMode(jtblInvDB.AUTO_RESIZE_OFF);
        jtblInvDB.setRowHeight(24);
        jtblInvDB.getColumnModel().getColumn(0).setPreferredWidth(120);
        jtblInvDB.getColumnModel().getColumn(0).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jtblInvDB.getColumnModel().getColumn(1).setPreferredWidth(170);
        jtblInvDB.getColumnModel().getColumn(1).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        jtblInvDB.getColumnModel().getColumn(2).setPreferredWidth(90);
        jtblInvDB.getColumnModel().getColumn(2).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jtblInvDB.getColumnModel().getColumn(3).setPreferredWidth(70);
        jtblInvDB.getColumnModel().getColumn(3).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jtblInvDB.getColumnModel().getColumn(4).setPreferredWidth(290);
        jtblInvDB.getColumnModel().getColumn(4).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);        
        jtblInvDB.getColumnModel().getColumn(5).setPreferredWidth(70);
        jtblInvDB.getColumnModel().getColumn(5).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jtblInvDB.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtblInvDB.getColumnModel().getColumn(6).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        jtblInvDB.getColumnModel().getColumn(7).setPreferredWidth(100);
        jtblInvDB.getColumnModel().getColumn(7).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        jtblInvDB.getColumnModel().getColumn(8).setPreferredWidth(120);
        jtblInvDB.getColumnModel().getColumn(8).setResizable(false);
        jtblInvDB.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
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
        sM = this.spanishMonth(sM);
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
        System.out.println("Checking if date is into the given range");
        System.out.println("RANGE. Ini: " + sIniDat + " / Fin: " + sFinDat);
        boolean bFlag = false;
        cls_Date_Manager tmpDM = new cls_Date_Manager();
        System.out.println("Convert from String to Date");
        Date dIniDat = tmpDM.string_toDate(sIniDat);
        Date dFinDat = tmpDM.string_toDate(sFinDat);
        System.out.println("Results. " + dIniDat + " / Fin: " + dFinDat);
        System.out.println("Checkign difference between dates");
        int iDifMin = tmpDM.getMinsBetween(dIniDat, dFinDat, false);
        if ( iDifMin >= 0 ){
            bFlag = true;
        }
        return bFlag;
    }
    //</editor-fold>
    
    //Fulfills the Invoice list according with the given date range
    private void loadInvoiceTable(String sIniDat, String sFinDat){
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
        for ( cls_Invoice tmp: this.alInvoDB ){
            System.out.println(tmp.getInvDate());
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
                if ( !tmp.getItACode().equals("NA") ){
                    InvDBColumn[0] = tmp.getInvDate();
                    InvDBColumn[1] = tmp.getCusName();
                    InvDBColumn[2] = tmp.getInvNumb();
                    InvDBColumn[3] = "1";
                    InvDBColumn[4] = tmp.getItADesc();
                    InvDBColumn[5] = tmp.getItA_Qty();
                    InvDBColumn[6] = tmp.getItATotl();
                    if ( tmp.getItACode().substring(0, 1).toUpperCase().equals("P") ){
                        InvDBColumn[7] = tmp.getItACode();
                    }
                    else{
                        InvDBColumn[7] = "NA";
                    }
                    InvDBColumn[8] = tmp.getPayMeth();
                    tblModelInvDB.addRow(InvDBColumn);
                    jtblInvDB.setModel(tblModelInvDB);
                }
                if ( !tmp.getItBCode().equals("NA") ){
                    InvDBColumn[0] = tmp.getInvDate();
                    InvDBColumn[1] = tmp.getCusName();
                    InvDBColumn[2] = tmp.getInvNumb();
                    InvDBColumn[3] = "2";
                    InvDBColumn[4] = tmp.getItBDesc();
                    InvDBColumn[5] = tmp.getItB_Qty();
                    InvDBColumn[6] = tmp.getItBTotl();
                    if ( tmp.getItBCode().substring(0, 1).toUpperCase().equals("P") ){
                        InvDBColumn[7] = tmp.getItBCode();
                    }
                    else{
                        InvDBColumn[7] = "NA";
                    }
                    InvDBColumn[8] = tmp.getPayMeth();
                    tblModelInvDB.addRow(InvDBColumn);
                    jtblInvDB.setModel(tblModelInvDB);
                }
                if ( !tmp.getItCCode().equals("NA") ){
                    InvDBColumn[0] = tmp.getInvDate();
                    InvDBColumn[1] = tmp.getCusName();
                    InvDBColumn[2] = tmp.getInvNumb();
                    InvDBColumn[3] = "3";
                    InvDBColumn[4] = tmp.getItCDesc();
                    InvDBColumn[5] = tmp.getItC_Qty();
                    InvDBColumn[6] = tmp.getItCTotl();
                    if ( tmp.getItCCode().substring(0, 1).toUpperCase().equals("P") ){
                        InvDBColumn[7] = tmp.getItCCode();
                    }
                    else{
                        InvDBColumn[7] = "NA";
                    }
                    InvDBColumn[8] = tmp.getPayMeth();
                    tblModelInvDB.addRow(InvDBColumn);
                    jtblInvDB.setModel(tblModelInvDB);
                }
                if ( !tmp.getItDCode().equals("NA") ){
                    InvDBColumn[0] = tmp.getInvDate();
                    InvDBColumn[1] = tmp.getCusName();
                    InvDBColumn[2] = tmp.getInvNumb();
                    InvDBColumn[3] = "4";
                    InvDBColumn[4] = tmp.getItDDesc();
                    InvDBColumn[5] = tmp.getItD_Qty();
                    InvDBColumn[6] = tmp.getItDTotl();
                    if ( tmp.getItDCode().substring(0, 1).toUpperCase().equals("P") ){
                        InvDBColumn[7] = tmp.getItDCode();
                    }
                    else{
                        InvDBColumn[7] = "NA";
                    }
                    InvDBColumn[8] = tmp.getPayMeth();
                    tblModelInvDB.addRow(InvDBColumn);
                    jtblInvDB.setModel(tblModelInvDB);
                }
                sInvDat = "";
            }
        }
        System.out.println("Invoice Data Base loaded in the Invoice's JTable");
    }
    //</editor-fold>
    
    //Cleans the Teachers List JTable
    private void cleanInvoTable(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        int a = this.tblModelInvDB.getRowCount()-1;
        try
        {
            for ( int i=a; i >= 0; i--){tblModelInvDB.removeRow(i);}
            
        }
        catch (Exception e){JOptionPane.showMessageDialog(this, "Hubo un error al limpiar la tabla de Profesores\n" + e, "TCM MSG", JOptionPane.ERROR_MESSAGE);}
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
    
    
    //Obtains the total of "Efectivo" shown in the data base
    private void calculateTotEfec(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        double dTotEfec = 0;
        //Runs over the screen checking for those rows that have "Efectivo" in the PayMethod column
        for ( int i=0; i<jtblInvDB.getRowCount(); i++ ){
            if ( jtblInvDB.getValueAt(i, 8).toString().equals("Efectivo") && !jtblInvDB.getValueAt(i, 6).toString().equals("NA") ){
                dTotEfec = dTotEfec + Double.parseDouble(jtblInvDB.getValueAt(i, 6).toString());
            }
        }
        dTotEfec = roundValue(dTotEfec);
        sTotEfec = String.valueOf(dTotEfec);
    }
    //</editor-fold>
    
    //Obtains the total of "Tarjeta" shown in the data base
    private void calculateTotCard(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        double dTotCard = 0;
        //Runs over the screen checking for those rows that have "Tarjeta" in the PayMethod column
        for ( int i=0; i<jtblInvDB.getRowCount(); i++ ){
            if ( jtblInvDB.getValueAt(i, 8).toString().equals("Tarjeta") && !jtblInvDB.getValueAt(i, 6).toString().equals("NA") ){
                dTotCard = dTotCard + Double.parseDouble(jtblInvDB.getValueAt(i, 6).toString());
            }
        }
        dTotCard = roundValue(dTotCard);
        sTotCard = String.valueOf(dTotCard);
    }
    //</editor-fold>
    
    //Obtains the total of "Transferencia" shown in the data base
    private void calculateTotTran(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        double dTotTran = 0;
        //Runs over the screen checking for those rows that have "Tarjeta" in the PayMethod column
        for ( int i=0; i<jtblInvDB.getRowCount(); i++ ){
            if ( jtblInvDB.getValueAt(i, 8).toString().equals("Transferencia") && !jtblInvDB.getValueAt(i, 6).toString().equals("NA") ){
                dTotTran = dTotTran + Double.parseDouble(jtblInvDB.getValueAt(i, 6).toString());
            }
        }
        dTotTran = roundValue(dTotTran);
        sTotTran = String.valueOf(dTotTran);
    }
    //</editor-fold>
    
    //Obtains the total of "Cago Automático" shown in the data base
    private void calculateTotAuto(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        //Creates the necessary variables to store the totals
        double dTotAuto = 0;
        //Runs over the screen checking for those rows that have "Tarjeta" in the PayMethod column
        for ( int i=0; i<jtblInvDB.getRowCount(); i++ ){
            if ( jtblInvDB.getValueAt(i, 8).toString().equals("Cargo automático") && !jtblInvDB.getValueAt(i, 6).toString().equals("NA") ){
                dTotAuto = dTotAuto + Double.parseDouble(jtblInvDB.getValueAt(i, 6).toString());
            }
        }
        dTotAuto = roundValue(dTotAuto);
        sTotAuto = String.valueOf(dTotAuto);
    }
    //</editor-fold>
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblInvDB = new javax.swing.JTable();
        jpnlBot = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnExp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jpnlTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Doppio One", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LISTA DE FACTURACIÓN");

        javax.swing.GroupLayout jpnlTopLayout = new javax.swing.GroupLayout(jpnlTop);
        jpnlTop.setLayout(jpnlTopLayout);
        jpnlTopLayout.setHorizontalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnlTopLayout.setVerticalGroup(
            jpnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 258, Short.MAX_VALUE)
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
                            .addComponent(jcboxFinYea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnlMid1Layout.createSequentialGroup()
                        .addComponent(jlblSpec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboxSpec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtblInvDB.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtblInvDB.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblInvDB);

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
            .addGroup(jpnlBotLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnlBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnExit)
                    .addComponent(jbtnExp))
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
                    .addComponent(jpnlMid1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jpnlBot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpnlMid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    private void jbtnExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExpActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Confirme que desea exportar los valores en pantalla");
        if ( opt == 0 ){
            //Calculates the totals in the screen. These values will be exported as well
            calculateTotEfec();
            calculateTotCard();
            calculateTotTran();
            calculateTotAuto();
            //Creates a 2D Array according with the dimmensions on the screen
            String[][] aDataBase = new String[jtblInvDB.getRowCount()][jtblInvDB.getColumnCount()];
            //Fulfills the Array with the values on the screen
            for ( int i=0; i<jtblInvDB.getRowCount(); i++ ){
                for ( int j=0; j<jtblInvDB.getColumnCount(); j++ ){
                    aDataBase[i][j] = jtblInvDB.getValueAt(i, j).toString();
                }
            }
            //Sends the 2D Aray to be exported
            cls_Excel_Manager tmpEM = new cls_Excel_Manager();
            try{
                tmpEM.exportDBXLSFile(aDataBase, sTotEfec, sTotCard, sTotTran, sTotAuto);
            }
            catch (WriteException ex){
                Logger.getLogger(gui_InvoiceDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        
        
    }//GEN-LAST:event_jbtnExpActionPerformed

    private void jbtnLoadInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoadInvActionPerformed
        cleanInvoTable();
        cls_Date_Manager tmpDM = new cls_Date_Manager();
        switch ( this.jcboxSpec.getSelectedItem() ){
            case "Escoja un valor" :{
                String sIniDat = jcboxIniYea.getSelectedItem() + "-" + jcboxIniMon.getSelectedItem() + "-" + this.jcboxIniDay.getSelectedItem();
                String sFinDat = jcboxFinYea.getSelectedItem() + "-" + jcboxFinMon.getSelectedItem() + "-" + this.jcboxFinDay.getSelectedItem();
                loadInvoiceTable(sIniDat, sFinDat);
                break;
            }
            case "Hoy" :{
                String sIniDat = getCurrentDate();
                String sFinDat = getCurrentDate();
                loadInvoiceTable(sIniDat, sFinDat);                
                break;
            }
            case "Ayer":{
                String sIniDat = tmpDM.substractDate(1);
                String sFinDat = tmpDM.substractDate(1);
                loadInvoiceTable(sIniDat, sFinDat);
                break;
            }
            case "Última semana":{
                String sIniDat = tmpDM.substractDate(7);
                String sFinDat = getCurrentDate();
                loadInvoiceTable(sIniDat, sFinDat);
                break;
            }
            case "Último mes":{
                String sIniDat = tmpDM.substractDate(30);
                String sFinDat = getCurrentDate();
                loadInvoiceTable(sIniDat, sFinDat);
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
        cleanInvoTable();        
    }//GEN-LAST:event_jbtnResetActionPerformed


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
            java.util.logging.Logger.getLogger(gui_InvoiceDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui_InvoiceDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui_InvoiceDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui_InvoiceDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gui_InvoiceDB().setVisible(true);
            }
        });
    }

    //<editor-fold defaultstate="collapsed" desc="Variables declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jpnlBot;
    private javax.swing.JPanel jpnlMid1;
    private javax.swing.JPanel jpnlTop;
    private javax.swing.JTable jtblInvDB;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
