package club_manager;

import jxl.Workbook;
import jxl.Sheet;
import jxl.Cell;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class cls_Excel_Manager
{
    //VARIABLES DECLARATION*********************************************
    private File file = null;
    private Workbook workbook = null;    
    private Sheet sheet = null;
    private Cell cell = null;
    private String[][] xlsMatrix;
//    private ArrayList<cls_PartDataReq> alPartsList = new ArrayList<>();
    
    private String sTotEfec;
    private String sTotCard;
    private String sTotTran;
    private String sTotAuto;
    private String sTeaName;
    private String sStuName;
    private String sIniDat;
    private String sFinDat;
    private String sTotPaym;
    
    private String sTotRes;
    private String sTotPay;
    private String sTotRec;
    private String sTotUnp;
    private String sTotPen;
    private String sTotFav;
    
    
    
    //CONSTRUCTORS SECTION**********************************************
    public cls_Excel_Manager(){}
    
    //METHODS SECTION***************************************************

    //<editor-fold defaultstate="collapsed" desc="Setters and getters">
    public File getFile(){return file;}
    public Workbook getWorkbook(){return workbook;}
    public Sheet getSheet(){return sheet;}
    public Cell getCell(){return cell;}

    public void setFile(File file){this.file = file;}
    public void setWorkbook(Workbook workbook){this.workbook = workbook;}
    public void setSheet(Sheet sheet){this.sheet = sheet;}
    public void setCell(Cell cell){this.cell = cell;}
    //</editor-fold>
    
    //Filters in order to accept .XLS file only
    private static class xlsFilter extends FileFilter
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        public boolean accept(File f)
        {
            return f.isDirectory() || f.getName().endsWith(".xls");
        }
        
        public String getDescription() {
            return "Excel 97-2003 Workbook XLS files";
        }
    }
    //</editor-fold>
    
    //Filters in order to accept .CSV file only
    private static class csvFilter extends FileFilter
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        public boolean accept(File f)
        {
            return f.isDirectory() || f.getName().endsWith(".csv");
        }
        
        public String getDescription() {
            return "CSV (Comma delimited) (*.csv)";
        }
    }
    //</editor-fold>

    //Opens a dialog window so the User can browse for a file then returns such file path
    public File importXLSFile(){
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
        File fl = null;
        try{
            //Opens a windows browser
            JFileChooser chooser = new JFileChooser();
            //Calls the Method that will filter for Excel 97-2003 Workbook XLS files 
            chooser.setFileFilter(new xlsFilter());
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION){fl = chooser.getSelectedFile();}
        }
        catch (Exception e){}
        return fl;
    }
    //</editor-fold>
    
    /* ***EXPORTING METHODS *** */
    
    //Exports a 2D-Array data base into an Excel file (.xls)
    public boolean exportDBXLSFile(String [][] arrDataBase, String sTotEfec, String sTotCard, String sTotTran, String sTotAuto) throws WriteException {
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        this.sTotEfec = sTotEfec;
        this.sTotCard = sTotCard;
        this.sTotTran = sTotTran;
        this.sTotAuto = sTotAuto;
        
        boolean flag = true;
        File filePath = null;
        //Opens a save dialog in order to set the full path for the file
        JFileChooser chooser = new JFileChooser();
        
        chooser.setFileFilter(new xlsFilter());
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile();
            //filePath = New File(filePath + ".xls");
            System.out.println("Exporting file to: " + filePath);
        }
        else {
            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
        }
        
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sh1 = wb.createSheet("Base de Datos de Facturación");
        
        // Creating Headers
        int ir = 0;
        HSSFRow rRow = sh1.createRow(ir);
        HSSFCell cCell = rRow.createCell(0);
        cCell.setCellValue("FECHA");
        cCell = rRow.createCell(1);
        cCell.setCellValue("ALUMNO");
        cCell = rRow.createCell(2);
        cCell.setCellValue("FACTURA");
        cCell = rRow.createCell(3);
        cCell.setCellValue("LÍNEA");
        cCell = rRow.createCell(4);
        cCell.setCellValue("DESCRIPCIÓN");
        cCell = rRow.createCell(5);
        cCell.setCellValue("CANT.");
        cCell = rRow.createCell(6);
        cCell.setCellValue("MONTO");
        cCell = rRow.createCell(7);
        cCell.setCellValue("CÓDIGO");
        cCell = rRow.createCell(8);
        cCell.setCellValue("FORMA DE PAGO");
        //Filling values
        int iR = 0;
        ir = 1;
        for ( iR = 0; iR<arrDataBase.length; iR++ ){
            HSSFRow rRi = sh1.createRow(ir);
            HSSFCell cC0 = rRi.createCell(0);
            cC0.setCellValue(arrDataBase[iR][0]);
            HSSFCell cC1 = rRi.createCell(1);
            cC1.setCellValue(arrDataBase[iR][1]);
            HSSFCell cC2 = rRi.createCell(2);
            cC2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cC2.setCellValue(Integer.parseInt(arrDataBase[iR][2]));
            HSSFCell cC3 = rRi.createCell(3);
            cC3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cC3.setCellType(CellType.NUMERIC);
            cC3.setCellValue(Integer.parseInt(arrDataBase[iR][3]));
            HSSFCell cC4 = rRi.createCell(4);
            cC4.setCellValue(arrDataBase[iR][4]);
            HSSFCell cC5 = rRi.createCell(5);
            cC5.setCellType(CellType.NUMERIC);
            cC5.setCellValue(Integer.parseInt(arrDataBase[iR][5]));
            HSSFCell cC6 = rRi.createCell(6);
            if ( arrDataBase[iR][6].equals("ANULADA") || arrDataBase[iR][6].equals("NA") ){
                cC6.setCellValue(arrDataBase[iR][6]);
            }
            else{
                cC6.setCellType(CellType.NUMERIC);
                cC6.setCellValue(Double.parseDouble(arrDataBase[iR][6]));
            }
            HSSFCell cC7 = rRi.createCell(7);
            cC7.setCellValue(arrDataBase[iR][7]);
            HSSFCell cC8 = rRi.createCell(8);
            cC8.setCellValue(arrDataBase[iR][8]);
            ir++;            
        }
        HSSFRow rEfecR = sh1.createRow(ir);
        HSSFCell cEfecC = rEfecR.createCell(0);
        cEfecC = rEfecR.createCell(1);
        cEfecC = rEfecR.createCell(2);
        cEfecC = rEfecR.createCell(3);
        cEfecC = rEfecR.createCell(4);
        cEfecC = rEfecR.createCell(5);
        cEfecC = rEfecR.createCell(6);
        cEfecC = rEfecR.createCell(7);
        cEfecC.setCellValue("Total Efectivo");
        cEfecC = rEfecR.createCell(8);
        cEfecC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cEfecC.setCellValue(Double.parseDouble(sTotEfec));
        ir++;
        HSSFRow rCardR = sh1.createRow(ir);
        HSSFCell cCardC = rCardR.createCell(0);
        cCardC = rCardR.createCell(1);
        cCardC = rCardR.createCell(2);
        cCardC = rCardR.createCell(3);
        cCardC = rCardR.createCell(4);
        cCardC = rCardR.createCell(5);
        cCardC = rCardR.createCell(6);
        cCardC = rCardR.createCell(7);
        cCardC.setCellValue("Total Tarjeta");
        cCardC = rCardR.createCell(8);
        cCardC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cCardC.setCellValue(Double.parseDouble(sTotCard));
        ir++;
        HSSFRow rTranR = sh1.createRow(ir);
        HSSFCell cTranC = rTranR.createCell(0);
        cTranC = rTranR.createCell(1);
        cTranC = rTranR.createCell(2);
        cTranC = rTranR.createCell(3);
        cTranC = rTranR.createCell(4);
        cTranC = rTranR.createCell(5);
        cTranC = rTranR.createCell(6);
        cTranC = rTranR.createCell(7);
        cTranC.setCellValue("Total Transferencia");
        cTranC = rTranR.createCell(8);
        cTranC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cTranC.setCellValue(Double.parseDouble(sTotTran));
        ir++;
        HSSFRow rTranA = sh1.createRow(ir);
        HSSFCell cTranA = rTranA.createCell(0);
        cTranA = rTranA.createCell(1);
        cTranA = rTranA.createCell(2);
        cTranA = rTranA.createCell(3);
        cTranA = rTranA.createCell(4);
        cTranA = rTranA.createCell(5);
        cTranA = rTranA.createCell(6);
        cTranA = rTranA.createCell(7);
        cTranA.setCellValue("Total Cargo Automático");
        cTranA = rTranA.createCell(8);
        cTranA.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cTranA.setCellValue(Double.parseDouble(sTotAuto));
        try{
            if ( !filePath.toString().contains(".xls") ){
                FileOutputStream file = new FileOutputStream(filePath + ".xls");
                wb.write(file);
                file.close();
            }
            else{
                FileOutputStream file = new FileOutputStream(filePath);
                wb.write(file);
                file.close();
            }
            JOptionPane.showMessageDialog(null, "La Base de Datos ha sido exportada correctamente");
        }
        catch (Exception e) {flag = false;}
        return flag;
    }
    //</editor-fold>
    
    //Exports a 2D-Array data base into an Excel file (.xls)
    public boolean exportProfRepXLSFile(String [][] arrDataBase, String sTeaName, String sIniDat, String sFinDat, String sTotPaym) throws WriteException {
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        this.sTeaName = sTeaName;
        this.sIniDat = sIniDat;
        this.sFinDat = sFinDat;
        this.sTotPaym = sTotPaym;
    
        boolean flag = true;
        File filePath = null;
        //Opens a save dialog in order to set the full path for the file
        JFileChooser chooser = new JFileChooser();
        
        chooser.setFileFilter(new xlsFilter());
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile();
            //filePath = New File(filePath + ".xls");
            System.out.println("Exporting file to: " + filePath);
        }
        else {
            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
        }
        
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sh1 = wb.createSheet("Reporte de Profesor");
        //CREATING DATA HEADER
        int ir = 0;
        HSSFRow rRow0 = sh1.createRow(ir);
        HSSFCell cCel01 = rRow0.createCell(0);
        cCel01.setCellValue(sTeaName.toUpperCase());
        ir = 1;
        HSSFRow rRow1 = sh1.createRow(ir);
        HSSFCell cCel11 = rRow1.createCell(0);
        cCel11.setCellValue("DESDE: ");
        HSSFCell cCel12 = rRow1.createCell(1);
        cCel12.setCellValue(sIniDat);
        HSSFCell cCel13 = rRow1.createCell(2);
        cCel13.setCellValue("HASTA: ");
        HSSFCell cCel14 = rRow1.createCell(3);
        cCel14.setCellValue(sFinDat);
        // Creating Headers
        ir = 2;
        HSSFRow rRow = sh1.createRow(ir);
        HSSFCell cCell = rRow.createCell(0);
        cCell.setCellValue("ALUMNO");
        cCell = rRow.createCell(1);
        cCell.setCellValue("TIPO DE LECCIÓN");
        cCell = rRow.createCell(2);
        cCell.setCellValue("FACTURA");
        cCell = rRow.createCell(3);
        cCell.setCellValue("FECHA");
        cCell = rRow.createCell(4);
        cCell.setCellValue("MES");
        cCell = rRow.createCell(5);
        cCell.setCellValue("TARIFA DIARIA");
        cCell = rRow.createCell(6);
        cCell.setCellValue("CANT. LECCIONES");
        cCell = rRow.createCell(7);
        cCell.setCellValue("% PROFESOR");
        cCell = rRow.createCell(8);
        cCell.setCellValue("LECC. PAGADAS");
        cCell = rRow.createCell(9);
        cCell.setCellValue("LECC. IMPARTIDAS");
        cCell = rRow.createCell(10);
        cCell.setCellValue("LECC. PENDIENTES");
        cCell = rRow.createCell(11);
        cCell.setCellValue("MONTO PROFESOR");
        //Filling values
        int iR = 0;
        ir = 3;
        for ( iR = 0; iR<arrDataBase.length; iR++ ){
            HSSFRow rRi = sh1.createRow(ir);
            HSSFCell cC0 = rRi.createCell(0);
            cC0.setCellValue(arrDataBase[iR][0]);
            HSSFCell cC1 = rRi.createCell(1);
            cC1.setCellValue(arrDataBase[iR][1]);
            HSSFCell cC2 = rRi.createCell(2);
            cC2.setCellType(CellType.NUMERIC);
            cC2.setCellValue(Integer.parseInt(arrDataBase[iR][2]));
            HSSFCell cC3 = rRi.createCell(3);
            cC3.setCellValue(arrDataBase[iR][3]);
            HSSFCell cC4 = rRi.createCell(4);
            cC4.setCellValue(arrDataBase[iR][4]);
            HSSFCell cC5 = rRi.createCell(5);
            cC5.setCellType(CellType.NUMERIC);
            cC5.setCellValue(Double.parseDouble(arrDataBase[iR][5]));
            HSSFCell cC6 = rRi.createCell(6);
            cC6.setCellType(CellType.NUMERIC);
            cC6.setCellValue(Integer.parseInt(arrDataBase[iR][6]));
            HSSFCell cC7 = rRi.createCell(7);
            cC7.setCellType(CellType.NUMERIC);
            cC7.setCellValue(Integer.parseInt(arrDataBase[iR][7]));
            HSSFCell cC8 = rRi.createCell(8);
            cC8.setCellType(CellType.NUMERIC);
            cC8.setCellValue(Integer.parseInt(arrDataBase[iR][8]));
            HSSFCell cC9 = rRi.createCell(9);
            cC9.setCellType(CellType.NUMERIC);
            cC9.setCellValue(Integer.parseInt(arrDataBase[iR][9]));
            HSSFCell cC10 = rRi.createCell(10);
            cC10.setCellValue(Integer.parseInt(arrDataBase[iR][10]));
            cC10.setCellType(CellType.NUMERIC);
            HSSFCell cC11 = rRi.createCell(11);
            cC11.setCellValue(Double.parseDouble(arrDataBase[iR][11]));
            ir++;            
        }
        HSSFRow rEfecR = sh1.createRow(ir);
        HSSFCell cEfecC = rEfecR.createCell(0);
        cEfecC = rEfecR.createCell(1);
        cEfecC = rEfecR.createCell(2);
        cEfecC = rEfecR.createCell(3);
        cEfecC = rEfecR.createCell(4);
        cEfecC = rEfecR.createCell(5);
        cEfecC = rEfecR.createCell(6);
        cEfecC = rEfecR.createCell(7);
        cEfecC = rEfecR.createCell(8);
        cEfecC = rEfecR.createCell(9);
        cEfecC = rEfecR.createCell(10);
        cEfecC.setCellValue("PAGO: ");
        cEfecC = rEfecR.createCell(11);
        cEfecC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cEfecC.setCellValue(Double.parseDouble(sTotPaym));
        try{
            if ( !filePath.toString().contains(".xls") ){
                FileOutputStream file = new FileOutputStream(filePath + ".xls");
                wb.write(file);
                file.close();
            }
            else{
                FileOutputStream file = new FileOutputStream(filePath);
                wb.write(file);
                file.close();
            }
            JOptionPane.showMessageDialog(null, "La Base de Datos ha sido exportada correctamente");
        }
        catch (Exception e) {flag = false;}
        return flag;
    }
    //</editor-fold>
    
    //Exports a 2D-Array data base into an Excel file (.xls)
    public boolean exportStudRepXLSFile(String [][] arrDataBase, String sStuName, String sTotRes, String sTotPay, String sTotRec, 
            String sTotUnp, String sTotPen, String sTotFav) throws WriteException {
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
        this.sStuName = sStuName;
        this.sTotRes = sTotRes;
        this.sTotPay = sTotPay;
        this.sTotRec = sTotRec;
        this.sTotUnp = sTotUnp;
        this.sTotPen = sTotPen;
        this.sTotFav = sTotFav;
        
        boolean flag = true;
        File filePath = null;
        //Opens a save dialog in order to set the full path for the file
        JFileChooser chooser = new JFileChooser();
        
        chooser.setFileFilter(new xlsFilter());
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile();
            //filePath = New File(filePath + ".xls");
            System.out.println("Exporting file to: " + filePath);
        }
        else {
            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
        }
        
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sh1 = wb.createSheet("Reporte de " + sStuName);
        //CREATING DATA HEADER
        int ir = 0;
        HSSFRow rRow0 = sh1.createRow(ir);
        HSSFCell cCel01 = rRow0.createCell(0);
        cCel01.setCellValue(sStuName.toUpperCase());
        // Creating Headers
        ir = 1;
        HSSFRow rRow = sh1.createRow(ir);
        HSSFCell cCell = rRow.createCell(0);
        cCell.setCellValue("ALUMNO");
        cCell = rRow.createCell(1);
        cCell.setCellValue("RESERVA");
        cCell = rRow.createCell(2);
        cCell.setCellValue("FECHA");
        cCell = rRow.createCell(3);
        cCell.setCellValue("MES");
        cCell = rRow.createCell(4);
        cCell.setCellValue("PROFESOR");
        cCell = rRow.createCell(5);
        cCell.setCellValue("FACTURA(S)");
        cCell = rRow.createCell(6);
        cCell.setCellValue("TARIFA DIARIA");
        cCell = rRow.createCell(7);
        cCell.setCellValue("LECC. RESERV");
        cCell = rRow.createCell(8);
        cCell.setCellValue("LECC. PAGADAS");
        cCell = rRow.createCell(9);
        cCell.setCellValue("LECC. RECIBID");
        cCell = rRow.createCell(10);
        cCell.setCellValue("LECC. ADEUDAD");
        cCell = rRow.createCell(11);
        cCell.setCellValue("SALDO PENDIENTE");
        cCell = rRow.createCell(12);
        cCell.setCellValue("LECC. A FAVOR");
        //Filling values
        int iR = 0;
        ir = 2;
        for ( iR = 0; iR<arrDataBase.length; iR++ ){
            HSSFRow rRi = sh1.createRow(ir);
            HSSFCell cC0 = rRi.createCell(0);
            cC0.setCellValue(arrDataBase[iR][0]);
            HSSFCell cC1 = rRi.createCell(1);
            cC1.setCellValue(arrDataBase[iR][1]);
            HSSFCell cC2 = rRi.createCell(2);
            cC2.setCellValue(arrDataBase[iR][2]);
            HSSFCell cC3 = rRi.createCell(3);
            cC3.setCellValue(arrDataBase[iR][3]);
            HSSFCell cC4 = rRi.createCell(4);
            cC4.setCellValue(arrDataBase[iR][4]);
            HSSFCell cC5 = rRi.createCell(5);
            cC5.setCellValue(arrDataBase[iR][5]);
            HSSFCell cC6 = rRi.createCell(6);
            cC6.setCellType(CellType.NUMERIC);
            cC6.setCellValue(Double.valueOf(arrDataBase[iR][6]));
            HSSFCell cC7 = rRi.createCell(7);
            cC7.setCellType(CellType.NUMERIC);
            cC7.setCellValue(Integer.parseInt(arrDataBase[iR][7]));
            HSSFCell cC8 = rRi.createCell(8);
            cC8.setCellType(CellType.NUMERIC);
            cC8.setCellValue(Integer.parseInt(arrDataBase[iR][8]));
            HSSFCell cC9 = rRi.createCell(9);
            cC9.setCellType(CellType.NUMERIC);
            cC9.setCellValue(Integer.parseInt(arrDataBase[iR][9]));
            HSSFCell cC10 = rRi.createCell(10);
            cC10.setCellType(CellType.NUMERIC);
            cC10.setCellValue(Integer.parseInt(arrDataBase[iR][10]));
            HSSFCell cC11 = rRi.createCell(11);
            cC11.setCellType(CellType.NUMERIC);
            cC11.setCellValue(Double.parseDouble(arrDataBase[iR][11]));
            HSSFCell cC12 = rRi.createCell(12);
            cC12.setCellType(CellType.NUMERIC);
            cC12.setCellValue(Integer.parseInt(arrDataBase[iR][12]));
            ir++;            
        }
        HSSFRow rResR = sh1.createRow(ir);
        HSSFCell cResC = rResR.createCell(11);
        cResC.setCellValue("TOTAL LECC. RESERVADAS: ");
        cResC = rResR.createCell(12);
        cResC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cResC.setCellValue(Integer.valueOf(sTotRes));
        ir++;
        HSSFRow rPayR = sh1.createRow(ir);
        HSSFCell cPayC = rPayR.createCell(11);
        cPayC.setCellValue("TOTAL LECC. PAGADAS: ");
        cPayC = rPayR.createCell(12);
        cPayC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cPayC.setCellValue(Integer.valueOf(sTotPay));
        ir++;
        HSSFRow rRecR = sh1.createRow(ir);
        HSSFCell cRecC = rRecR.createCell(11);
        cRecC.setCellValue("TOTAL LECC. RECIBIDAS: ");
        cRecC = rRecR.createCell(12);
        cRecC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cRecC.setCellValue(Integer.valueOf(sTotRec));
        ir++;
        HSSFRow rUnpR = sh1.createRow(ir);
        HSSFCell cUnpC = rUnpR.createCell(11);
        cUnpC.setCellValue("TOTAL LECC. ADEUDADAS: ");
        cUnpC = rUnpR.createCell(12);
        cUnpC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cUnpC.setCellValue(Integer.valueOf(sTotUnp));
        ir++;
        HSSFRow rPenR = sh1.createRow(ir);
        HSSFCell cPenC = rPenR.createCell(11);
        cPenC.setCellValue("SALDO PENDIENTE TOT.: ");
        cPenC = rPenR.createCell(12);
        cPenC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cPenC.setCellValue(Double.parseDouble(sTotPen));
        ir++;
        HSSFRow rFavR = sh1.createRow(ir);
        HSSFCell cFavC = rFavR.createCell(11);
        cFavC.setCellValue("TOTAL LECC. A FAVOR: ");
        cFavC = rFavR.createCell(12);
        cFavC.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cFavC.setCellValue(Double.parseDouble(sTotFav));
        try{
            if ( !filePath.toString().contains(".xls") ){
                FileOutputStream file = new FileOutputStream(filePath + ".xls");
                wb.write(file);
                file.close();
            }
            else{
                FileOutputStream file = new FileOutputStream(filePath);
                wb.write(file);
                file.close();
            }
            JOptionPane.showMessageDialog(null, "Los datos han sido exportados correctamente");
        }
        catch (Exception e) {flag = false;}
        return flag;
    }
    //</editor-fold>
    
    
    
    
//    //Exports an ArrayList data base into an Excel file (.xls)
//    public boolean exportDBXLSFile(ArrayList<cls_Invoice> alDataBase) throws WriteException {
//    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
//        boolean flag = true;
//        File filePath = null;
//        //Opens a save dialog in order to set the full path for the file
//        JFileChooser chooser = new JFileChooser();
//        
//        chooser.setFileFilter(new xlsFilter());
//        int returnVal = chooser.showSaveDialog(null);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            filePath = chooser.getSelectedFile();
//            //filePath = New File(filePath + ".xls");
//            System.out.println("Exporting file to: " + filePath);
//        }
//        else {
//            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
//        }
//        
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sh1 = wb.createSheet("Consults Data Base");
//        
//        // Creating Headers
//        HSSFRow r = sh1.createRow(0);
//        HSSFCell c = r.createCell(0);
//        c.setCellValue("TIER");
//        c = r.createCell(1);
//        c.setCellValue("REGION");
//        c = r.createCell(2);
//        c.setCellValue("COUNTRY");
//        c = r.createCell(3);
//        c.setCellValue("ORG");
//        c = r.createCell(4);
//        c.setCellValue("PART");
//        c = r.createCell(5);
//        c.setCellValue("QTY");
//        c = r.createCell(6);
//        c.setCellValue("Activity");
//        c = r.createCell(7);
//        c.setCellValue("Good OnHand");
//        c = r.createCell(8);
//        c.setCellValue("Good Excess");
//        c = r.createCell(9);
//        c.setCellValue("Consult Date");
//        c = r.createCell(10);
//        c.setCellValue("DOM");
//        c = r.createCell(11);
//        c.setCellValue("Part Moved");
//        c = r.createCell(12);
//        c.setCellValue("Tracking");
//        //Filling values
//        int i = 1;
//        for ( cls_PartDataReq tmp: alDataBase ){
//            HSSFRow ri = sh1.createRow(i);
//            HSSFCell c0 = ri.createCell(0);
//            
//            c0.setCellValue(tmp.getTier());
//            HSSFCell c1 = ri.createCell(1);
//            c1.setCellValue(tmp.getRegion());
//            HSSFCell c2 = ri.createCell(2);
//            c2.setCellValue(tmp.getCountryName());
//            HSSFCell c3 = ri.createCell(3);
//            c3.setCellValue(tmp.getOrgCode());
//            HSSFCell c4 = ri.createCell(4);
//            c4.setCellValue(tmp.getPartNumber());
//            HSSFCell c5 = ri.createCell(5);
//            c5.setCellType(CellType.NUMERIC);
//            c5.setCellValue(Integer.valueOf(tmp.getQTY()));
//            HSSFCell c6 = ri.createCell(6);
//            c6.setCellValue(tmp.getActivity());
//            HSSFCell c7 = ri.createCell(7);
//            c7.setCellType(CellType.NUMERIC);
//            c7.setCellValue(Integer.valueOf(tmp.getTotalOH()));
//            HSSFCell c8 = ri.createCell(8);
//            c8.setCellType(CellType.NUMERIC);
//            c8.setCellValue(Integer.valueOf(tmp.getTotalXS()));
//            HSSFCell c9 = ri.createCell(9);
//            c9.setCellValue(tmp.getCurrentDate());
//            HSSFCell c10 = ri.createCell(10);
//            c10.setCellValue(tmp.getDOM());
//            HSSFCell c11 = ri.createCell(11);
//            c11.setCellValue(tmp.getPartMoved());
//            HSSFCell c12 = ri.createCell(12);
//            c12.setCellValue(tmp.getTracking());
//            i++;
//        }
//        
//        try {
//            if ( !filePath.toString().contains(".xls") ){
//                FileOutputStream file = new FileOutputStream(filePath + ".xls");
//                wb.write(file);
//                file.close();
//            }
//            else {
//                FileOutputStream file = new FileOutputStream(filePath);
//                wb.write(file);
//                file.close();
//            }
//            JOptionPane.showMessageDialog(null, "The Data Base has been successfully exported!");
//        }
//        catch (Exception e) {flag = false;}
//        return flag;
//    }
//    //</editor-fold>
//    
//    //Exports an ArrayList data base into an Excel file (.csv)
//    public boolean exportConsultsDBtoCSVFile(ArrayList<cls_PartDataReq> alDataBase) throws WriteException {
//    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
//        boolean flag = true;
//        File filePath = null;
//        //Opens a save dialog in order to set the full path for the file
//        JFileChooser chooser = new JFileChooser();
//        
//        
//        chooser.setFileFilter(new csvFilter());
//        int returnVal = chooser.showSaveDialog(null);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            filePath = chooser.getSelectedFile();
//            //filePath = New File(filePath + ".xls");
//            System.out.println("Exporting file to: " + filePath);
//        }
//        else {
//            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
//        }
//        
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sh1 = wb.createSheet("Consults Data Base");
//        
//        // Creating Headers
//        HSSFRow r = sh1.createRow(0);
//        HSSFCell c = r.createCell(0);
//        c.setCellValue("TIER,REGION,COUNTRY,ORG,PART,QTY,Activity,Good OnHand,Good Excess,Consult Date,DOM,Part Moved,Task,Tracking");
//        //Filling values
//        int i = 1;
//        for ( cls_PartDataReq tmp: alDataBase ){
//            HSSFRow ri = sh1.createRow(i);
//            HSSFCell c0 = ri.createCell(0);
//            c0.setCellValue(tmp.getTier()+","+
//                    tmp.getRegion()+","+
//                    tmp.getCountryName()+","+
//                    tmp.getOrgCode()+","+
//                    tmp.getPartNumber()+","+
//                    tmp.getQTY()+","+
//                    tmp.getActivity()+","+
//                    tmp.getTotalOH()+","+
//                    tmp.getTotalXS()+","+
//                    tmp.getCurrentDate()+","+
//                    tmp.getDOM()+","+
//                    tmp.getPartMoved()+","+
//                    tmp.getTask()+","+
//                    tmp.getTracking());
//            i++;
//        }
//        
//        try {
//            if ( !filePath.toString().contains(".csv") ){
//                FileOutputStream file = new FileOutputStream(filePath + ".csv");
//                wb.write(file);
//                file.close();
//            }
//            else {
//                FileOutputStream file = new FileOutputStream(filePath);
//                wb.write(file);
//                file.close();
//            }
//            JOptionPane.showMessageDialog(null, "The Data has been successfully exported!");
//        }
//        catch (Exception e) {flag = false;}
//        return flag;
//    }
//    //</editor-fold>
//    
//    //Exports an WebADI ArrayList data base into an Excel file (.csv)
//    public boolean exportWebADIDBtoCSVFile(ArrayList<cls_WebADI_Data> alDataBase) throws WriteException {
//    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
//        boolean flag = true;
//        File filePath = null;
//        //Opens a save dialog in order to set the full path for the file
//        JFileChooser chooser = new JFileChooser();
//        
//        chooser.setFileFilter(new csvFilter());
//        int returnVal = chooser.showSaveDialog(null);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            filePath = chooser.getSelectedFile();
//            //filePath = New File(filePath + ".xls");
//            System.out.println("Exporting file to: " + filePath);
//        }
//        else {
//            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
//        }
//        
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sh1 = wb.createSheet("WebADI Data Base");
//        
//        // Creating Headers
//        HSSFRow r = sh1.createRow(0);
//        HSSFCell c = r.createCell(0);
//        c.setCellValue("Creation Date,Item,QTY,From,Dest,Shipping Method,Ref,Order #,Airwaybill,Status,Activity,Task #,SIMI,Comments");
//        //Filling values
//        int i = 1;
//        for ( cls_WebADI_Data tmp: alDataBase ){
//            HSSFRow ri = sh1.createRow(i);
//            HSSFCell c0 = ri.createCell(0);
//            c0.setCellValue(tmp.getDat()+","+
//                    tmp.getItm()+","+
//                    tmp.getQTY()+","+
//                    tmp.getFrm()+","+
//                    tmp.getDst()+","+
//                    tmp.getShpMet()+","+
//                    tmp.getRef()+","+
//                    tmp.getISO()+","+
//                    tmp.getAwb()+","+
//                    tmp.getSta()+","+
//                    tmp.getAct()+","+
//                    tmp.getTsk()+","+
//                    tmp.getSMI()+","+
//                    tmp.getCom());
//            i++;
//        }
//        
//        try {
//            if ( !filePath.toString().contains(".csv") ){
//                FileOutputStream file = new FileOutputStream(filePath + ".csv");
//                wb.write(file);
//                file.close();
//            }
//            else {
//                FileOutputStream file = new FileOutputStream(filePath);
//                wb.write(file);
//                file.close();
//            }
//            JOptionPane.showMessageDialog(null, "The WebADI Data has been successfully exported!");
//        }
//        catch (Exception e) {flag = false;}
//        return flag;
//    }
//    //</editor-fold>
//    
//    //Exports a Backorders ArrayList data base into an Excel file (.csv)
//    public boolean exportBackordersDBtoCSVFile(ArrayList<cls_BO_Data> alDataBase) throws WriteException {
//    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
//        boolean flag = true;
//        File filePath = null;
//        //Opens a save dialog in order to set the full path for the file
//        JFileChooser chooser = new JFileChooser();
//        
//        chooser.setFileFilter(new csvFilter());
//        int returnVal = chooser.showSaveDialog(null);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            filePath = chooser.getSelectedFile();
//            //filePath = New File(filePath + ".xls");
//            System.out.println("Exporting file to: " + filePath);
//        }
//        else {
//            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
//        }
//        
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sh1 = wb.createSheet("Backorders Data Base");
//        
//        // Creating Headers
//        HSSFRow r = sh1.createRow(0);
//        HSSFCell c = r.createCell(0);
//        c.setCellValue("BO Status,BO Req Date,Service Req,Task Number,Order Number,Part Number,QTY,Description,Task Status,PLC,"
//                + "Part Criticality,Part Condition,Good New Search Assumption,Alternatives,Comments,ISO 1,AWB 1,ISO 2,AWB 2,ISO 3,AWB 3,"
//                + "ISO (MI2>BUE),AWB (MI2>BUE),SIMI(DJAI),GSI Task Notes,Backorder E-mail Title,E-mail Tracking #");
//        //Filling values
//        int i = 1;
//        for ( cls_BO_Data tmp: alDataBase ){
//            HSSFRow ri = sh1.createRow(i);
//            HSSFCell c0 = ri.createCell(0);
//            c0.setCellValue(tmp.getBSta()+","+
//                    tmp.getDate()+","+
//                    tmp.getSvRq()+","+
//                    tmp.getTask()+","+
//                    tmp.getISO()+","+
//                    tmp.getItem()+","+
//                    tmp.getQty()+","+
//                    tmp.getDesc().replaceAll(",", " ")+","+
//                    tmp.getTkSt()+","+
//                    tmp.getPLC()+","+
//                    tmp.getCrit()+","+
//                    tmp.getCond()+","+
//                    tmp.getSrAs()+","+
//                    tmp.getAlts()+","+
//                    tmp.getComm().replaceAll(",", " ")+","+
//                    tmp.getISO1()+","+
//                    tmp.getAwb1()+","+
//                    tmp.getISO2()+","+
//                    tmp.getAwb2()+","+
//                    tmp.getISO3()+","+
//                    tmp.getAwb3()+","+
//                    tmp.getIsMB()+","+
//                    tmp.getAwMB()+","+
//                    tmp.getSIMI()+","+
//                    tmp.getTkNt()+","+
//                    tmp.getBOMT()+","+
//                    tmp.getTrak());
//            i++;
//        }
//        
//        try {
//            if ( !filePath.toString().contains(".csv") ){
//                FileOutputStream file = new FileOutputStream(filePath + ".csv");
//                wb.write(file);
//                file.close();
//            }
//            else {
//                FileOutputStream file = new FileOutputStream(filePath);
//                wb.write(file);
//                file.close();
//            }
//            JOptionPane.showMessageDialog(null, "The Backorder Data has been successfully exported!");
//        }
//        catch (Exception e) {flag = false;}
//        return flag;
//    }
//    //</editor-fold>
//    
//    //Exports a Backorders ArrayList data base into an Excel file (.csv)
//    public boolean exportBackordersPlDBtoCSVFile(ArrayList<cls_BO_Data> alDataBase) throws WriteException {
//    //<editor-fold defaultstate="collapsed" desc="Method Source Code">     
//        boolean flag = true;
//        File filePath = null;
//        //Opens a save dialog in order to set the full path for the file
//        JFileChooser chooser = new JFileChooser();
//        
//        chooser.setFileFilter(new csvFilter());
//        int returnVal = chooser.showSaveDialog(null);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            filePath = chooser.getSelectedFile();
//            //filePath = New File(filePath + ".xls");
//            System.out.println("Exporting file to: " + filePath);
//        }
//        else {
//            JOptionPane.showMessageDialog(chooser, "File was not exported");// no file has been chosen
//        }
//        
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sh1 = wb.createSheet("Backorders Planning DB");
//        
//        // Creating Headers
//        HSSFRow r = sh1.createRow(0);
//        HSSFCell c = r.createCell(0);
//        c.setCellValue("Task Status,BO Planner,Last Review Date,BO Req Date,Service Req,"
//                + "Task Number,Order Number,Part Number,QTY,Description,Alternatives,"
//                + "Revised ETA,Path,Improved ETA,BO Status,Comments,ISO 1,AWB 1,ISO 2,AWB 2,ISO 3,AWB 3,"
//                + "Backorder E-mail Title,E-mail Tracking #");
//        //Filling values
//        int i = 1;
//        for ( cls_BO_Data tmp: alDataBase ){
//            HSSFRow ri = sh1.createRow(i);
//            HSSFCell c0 = ri.createCell(0);
//            c0.setCellValue(tmp.getTkSt()+","+
//                    tmp.getPlan()+","+
//                    tmp.getRvDt()+","+
//                    tmp.getDate().replaceAll(" / ", " ")+","+
//                    tmp.getSvRq()+","+
//                    tmp.getTask()+","+
//                    tmp.getISO()+","+
//                    tmp.getItem()+","+
//                    tmp.getQty()+","+
//                    tmp.getDesc().replaceAll(",", " ")+","+
//                    tmp.getAlts()+","+
//                    tmp.getReta()+","+
//                    tmp.getPath()+","+
//                    tmp.getIeta()+","+
//                    tmp.getBSta()+","+
//                    tmp.getComm().replaceAll(",", " ")+","+
//                    tmp.getISO1()+","+
//                    tmp.getAwb1()+","+
//                    tmp.getISO2()+","+
//                    tmp.getAwb2()+","+
//                    tmp.getISO3()+","+
//                    tmp.getAwb3()+","+
//                    tmp.getBOMT()+","+
//                    tmp.getTrak());
//            i++;
//        }
//        
//        try {
//            if ( !filePath.toString().contains(".csv") ){
//                FileOutputStream file = new FileOutputStream(filePath + ".csv");
//                wb.write(file);
//                file.close();
//            }
//            else {
//                FileOutputStream file = new FileOutputStream(filePath);
//                wb.write(file);
//                file.close();
//            }
//            JOptionPane.showMessageDialog(null, "The Backorder Planning Data has been successfully exported!");
//        }
//        catch (Exception e) {flag = false;}
//        return flag;
//    }
//    //</editor-fold>
    
    
    
    /* ***IMPORTING METHODS *** */
           
    //Reads given excel file (Version 97 or 2003) and returns its first sheet.
    //Returns First sheet or null if file can't be read or have no sheets
    public Sheet createExcelSheet(File fl)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        Workbook wb = null;
        Sheet sh = null;
        //Opens Excel file (workbook) for reading
        try
        {
            wb = Workbook.getWorkbook(fl);
        }
        catch ( Exception ex )
        {
            JOptionPane message = new JOptionPane("Can't read Excel file " + fl.getPath(),JOptionPane.ERROR_MESSAGE);
        }
        if ( wb.getNumberOfSheets() <= 0 )
        {
            JOptionPane message = new JOptionPane("Excel file doesn't have any sheets.",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            
            sh = wb.getSheet(0);
        }
        return sh;
    }
    //</editor-fold>
    
    public HSSFSheet extractExcelSheet (File fl) throws FileNotFoundException, IOException {
    //<editor fold defaultstate="collapsed" desc="Method Source Code">    
        FileInputStream inputStream = new FileInputStream(fl);
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sh = wb.getSheet("Sheet");
        
        
        /*HSSFRow row = sh.getRow(0);
        HSSFCell cellA1 = row.getCell((short) 0);
        String a1Val = cellA1.getStringCellValue();
        HSSFCell cellB1 = row.getCell((short) 1);
        String b1Val = cellB1.getStringCellValue();
        HSSFCell cellC1 = row.getCell((short) 2);
        boolean c1Val = cellC1.getBooleanCellValue();
        HSSFCell cellD1 = row.getCell((short) 3);
        Date d1Val = cellD1.getDateCellValue();*/

        
        
        
        
        
        /*while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                 
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }*/
        
        wb.close();
        inputStream.close();
        return sh;
        
        // http://www.codejava.net/coding/how-to-read-excel-files-in-java-using-apache-poi
    }
    //</editor-fold>
    
    
    
    //Loads an Excel Sheet into a Bidimentional Array
    public String[][] loadXLSsheet_toArray(Sheet sh)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        if ( sh == null ){
            System.out.println("La hoja importada está vacía");
        }
        else{
            try{
                //Prepares a global Array in order to store all the data Sheet in String format
                int iRow = sh.getRows();
                int iCol = sh.getColumns();
                int i=0;//Row
                int j=0;//Col
                //Creates a 2-D Matrix with the necessary dimmensions
                xlsMatrix = new String[iRow][iCol];
                String tmp;
                //Loads the LV cost data from row 4 to row 7
                for ( int r=4; r<8; r++ ){
                    j = 0;//from the 1st Column
                    tmp = sh.getCell(0,r).getContents();
                    //If the Sheet's cell is empty then it assigns a String "NA"
                    if ( tmp.equals("") ){tmp="NA";}
                    xlsMatrix[i][j]=tmp;
                    j++;
                    tmp = sh.getCell(1,r).getContents();
                    //If the Sheet's cell is empty then it assigns a String "NA"
                    if ( tmp.equals("") ){tmp="NA";}
                    xlsMatrix[i][j]=tmp;
                    j++;
                    for ( int c=3; c<iCol; c=c+2 ){
                        tmp = sh.getCell(c,r).getContents();
                        //If the Sheet's cell is empty then it assigns a String "NONE"
                        if ( tmp.equals("") ){tmp="NA";}
                        xlsMatrix[i][j]=tmp;
                        j++;
                    }
                    i++;
                }
                //Loads the FS cost data from row 12 to row 15
                i=4;
                j=0;
                for ( int r=12; r<16; r++ ){
                    j = 0;//from the 1st Column
                    tmp = sh.getCell(0,r).getContents();
                    //If the Sheet's cell is empty then it assigns a String "NA"
                    if ( tmp.equals("") ){tmp="NA";}
                    xlsMatrix[i][j]=tmp;
                    j++;
                    tmp = sh.getCell(1,r).getContents();
                    //If the Sheet's cell is empty then it assigns a String "NA"
                    if ( tmp.equals("") ){tmp="NA";}
                    xlsMatrix[i][j]=tmp;
                    j++;
                    for ( int c=3; c<iCol; c=c+2 ){
                        tmp = sh.getCell(c,r).getContents();
                        //If the Sheet's cell is empty then it assigns a String "NONE"
                        if ( tmp.equals("") ){tmp="NA";}
                        xlsMatrix[i][j]=tmp;
                        j++;
                    }
                    i++;
                }
                //Loads the MT cost data from row 12 to row 15
                i=8;
                j=0;
                for ( int r=20; r<iRow; r++ ){
                    j = 0;//from the 1st Column
                    tmp = sh.getCell(0,r).getContents();
                    //If the Sheet's cell is empty then it assigns a String "NA"
                    if ( tmp.equals("") ){tmp="NA";}
                    xlsMatrix[i][j]=tmp;
                    j++;
                    tmp = sh.getCell(1,r).getContents();
                    //If the Sheet's cell is empty then it assigns a String "NA"
                    if ( tmp.equals("") ){tmp="NA";}
                    xlsMatrix[i][j]=tmp;
                    j++;
                    for ( int c=3; c<iCol; c=c+2 ){
                        tmp = sh.getCell(c,r).getContents();
                        //If the Sheet's cell is empty then it assigns a String "NONE"
                        if ( tmp.equals("") ){tmp="NA";}
                        xlsMatrix[i][j]=tmp;
                        j++;
                    }
                    i++;
                }
            }
            catch ( Exception e ){}
        }
        return xlsMatrix;
    }
    //</editor-fold>
    
    
    
//    //Loads an Excel Sheet into an ArrayList
//    public ArrayList<cls_PartDataReq> loadXLSsheet_toArrayList(Sheet sh)
//    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
//    {
//        if ( sh == null )
//        {
//            System.out.println("The imported sheet is empty");
//        }
//        else
//        {
//            try
//            {
//                //Prepares a global Array in order to store all the data Sheet in String format
//                int iRow = sh.getRows();
//                int iCol = sh.getColumns();
//                String tmp;
//                for ( int r=0; r<iRow; r++ )
//                {
//                    for ( int c=0; c<iCol; c++ )
//                    {
//                        tmp = sh.getCell(c,r).getContents();
//                        //If the Sheet's cell is empty then it assigns a String "NONE"
//                        if ( tmp.equals("") ){tmp="NONE";}
//                        
//                    }
//                }
//            }
//            catch ( Exception e ){}
//        }
//        return alPartsList;
//    }
//    //</editor-fold>
    
    
    
    /*
    //Imports an MS Excel Worksheet into a Sheet variable
    public Sheet createXLSSheet(File fl)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        Sheet sh=null;
        //Generates a File variable and calls the method that opens a dialog window so the User can browse for such file
        if (fl != null)
        {
            //Creates a Sheet variable and calls the Method that will read and return its first sheet only
            sh = readExcelSheet(file);
        }
        return sh;
    }
    //</editor-fold>
    */


    

    
    
    
 
    

    
    
}
