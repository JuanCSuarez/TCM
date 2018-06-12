package club_manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class cls_Date_Manager
{
    //VARIABLES DECLARATION*********************************************
    
    private static long MILLIS_OF_WEEK = TimeUnit.DAYS.toMillis(7);
    private static long MILLIS_OF_WORKWEEK = TimeUnit.DAYS.toMillis(5);
    
    //CONSTRUCTORS SECTION**********************************************
    
    
    //METHODS SECTION***************************************************
    
    //Gets the current date and time from the System's clock with format "mm/dd/yy HH:mm"
    public String getCurrentFullDate(String sTimeZone)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        String sCurrentTime = "";
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone(sTimeZone));
        //Creating time variables for the clock. Obtaining values form the System's clock
        String sHrs = String.valueOf(cal.get(Calendar.HOUR));
        String sMns = String.valueOf(cal.get(Calendar.MINUTE));
        int AM_PM = cal.get(Calendar.AM_PM);
        //Creating time variables for the date. Obtaining values form the System's clock
        String sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String sMon = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String sYea = String.valueOf(cal.get(Calendar.YEAR));
        if ( AM_PM == 1  ){sHrs = String.valueOf(Integer.valueOf(sHrs)+ 12);}
        //Prepares the text to be displayed in the clock label on the main screen
        sCurrentTime = sMon + "/" + sDay + "/" + sYea + " " + sHrs + ":" + sMns;
        return sCurrentTime;
    }
    //</editor-fold>
    
    //Converts a String format date ("mm/dd/yy HH:mm" or "yyyy-MMM-dd HH:mm") from the Excel file into a Date format variable
    public Date string_toDate(String st)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        System.out.println("Fecha a reformatear: " + st);
        Date date = null;
        Date formatteddate = null;
        //The two possible date formats received from the Excel sheet
        DateFormat df1 = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
        DateFormat df2 = new SimpleDateFormat("MM/dd/yy HH:mm");
        try
        {
            formatteddate = df1.parse(st);
        }
        catch ( Exception ex )
        {
            System.out.println(ex);
            //If the first format doesn't work, it tries with the second one then
            System.out.println("Attempting to convert from format MM/dd/yy HH:mm");
            try
            {
                formatteddate = df2.parse(st);
                System.out.println("Success!");
            }
            catch ( Exception ey )
            {
                System.out.println(ey);
            }
        }
        System.out.println("Fecha reformteada: " + formatteddate);
        return formatteddate;
    }
    //</editor-fold>
    
    //Provides the current Date in format dd-MMM-yyyy 
    public String getCurrentDate()
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        String sDate = "";
        Calendar cal = new GregorianCalendar();
        String sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String sMon = getMonthName(cal.get(Calendar.MONTH) + 1);
        String sYea = String.valueOf(cal.get(Calendar.YEAR));
        sDate = sDay + "-" + sMon + "-" + sYea;
        return sDate;
    }
    //</editor-fold>
    
    //Provides the current Time in format HH:mm
    public String getCurrentTime(String sTimeZone)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        String sTime = "";
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone(sTimeZone));
        String sHrs = String.valueOf(cal.get(Calendar.HOUR));
        String sMns = String.valueOf(cal.get(Calendar.MINUTE));
        int AM_PM = cal.get(Calendar.AM_PM);
        if ( Integer.valueOf(sHrs) < 10 ){sHrs = "0" + sHrs;}
        if ( Integer.valueOf(sMns) < 10 ){sMns = "0" + sMns;}
        if ( AM_PM == 1  ){sHrs = String.valueOf(Integer.valueOf(sHrs)+ 12);}
        sTime = sHrs + ":" + sMns + ", " + sTimeZone;
        return sTime;
    }
    //</editor-fold>
    
    //Provides the difference in minutes between two dates
    public int getMinsBetween(Date d1, Date d2, boolean onlyBusinessDays)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        long duration = d2.getTime() - d1.getTime();
        if (onlyBusinessDays) {
            Date sat = toSaturdayMidnight(d1);
            long timeBeforeWeekend = Math.max(sat.getTime() - d1.getTime(), 0);
            if (duration > timeBeforeWeekend) {
                Date mon = toMondayMidnight(d2);
                long timeAfterWeekend = Math.max(d2.getTime() - mon.getTime(), 0);
                long numberOfWeekends = Math.max((duration / MILLIS_OF_WEEK) - 1, 0);
                duration = numberOfWeekends * MILLIS_OF_WORKWEEK + timeBeforeWeekend + timeAfterWeekend;
            }
        }
        return (int) TimeUnit.MILLISECONDS.toMinutes(duration);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sub-Methods used only by getMinsBetween Method">
    private static Date toMondayMidnight(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                cal.add(Calendar.DAY_OF_MONTH, 7);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        toMidnight(cal);
        return cal.getTime();
    }
    
    private static Date toSaturdayMidnight(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        toMidnight(cal);
        return cal.getTime();
    }
    
    private static void toMidnight(Calendar cal)
    {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
    //</editor-fold>
    
    //Provides the amount of Hours from a given quantity of minutes
    public int get_Hours(int Minutes)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        int iHrs = Minutes/60;
        return iHrs;
    }
    //</editor-fold>
    
    //Provides the amount of remaining minutes from a given quantity of minutes (after removing groups of complete hours -60m-)
    public int get_Minutes(int Minutes)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        int iMns = (Minutes%60);
        return iMns;
    }
    //</editor-fold>
    
    //Converts a String with format "mm/dd/yy HH:mm" into a String with format "yyyy-MMM-dd HH:mm"
    public String formatDate(String sDate)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        String newDate="";
        String sMM="", sDD="", sYY="20", sHr="", sMn="";
        int i=0;
        do
        {
            sMM = sMM + sDate.charAt(i);
            i++;
        }
        while ( sDate.charAt(i)!='/' );
        i++;
        do
        {
            sDD = sDD + sDate.charAt(i);
            i++;
        }
        while ( sDate.charAt(i)!='/' );
        i++;
        do
        {
            sYY = sYY + sDate.charAt(i);
            i++;
        }
        while ( sDate.charAt(i)!=' ' );
        i++;
        do
        {
            sHr = sHr + sDate.charAt(i);
            i++;
        }
        while ( sDate.charAt(i)!=':' );
        i++;
        do
        {
            sMn = sMn + sDate.charAt(i);
            i++;
        }
        while ( i < sDate.length() );
        //Converting month from number to characters
        switch ( sMM )
        {
            case "1" : {sMM = "JAN";break;}
            case "2" : {sMM = "FEB";break;}
            case "3" : {sMM = "MAR";break;}
            case "4" : {sMM = "APR";break;}
            case "5" : {sMM = "MAY";break;}
            case "6" : {sMM = "JUN";break;}
            case "7" : {sMM = "JUL";break;}
            case "8" : {sMM = "AGO";break;}
            case "9" : {sMM = "SEP";break;}
            case "10" : {sMM = "OCT";break;}
            case "11" : {sMM = "NOV";break;}
            case "12" : {sMM = "DEC";break;}
        }
        //Formatting day and hour numbers to two digits format if necessary
        if ( Integer.valueOf(sDD)<10 ){sDD = "0" + sDD;}
        if ( Integer.valueOf(sHr)<10 ){sHr = "0" + sHr;}
        //Creating the file String with the whole date and hour
        newDate = sYY + "-" + sMM + "-" + sDD + " " + sHr + ":" + sMn;
        return newDate;
    }
    //</editor-fold>
    
    //Converts a given amount of minutes into a String with format HH:mm
    public String minutes_toString(int Minutes)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        String sLapse;
        String sHrs = String.valueOf(get_Hours(Minutes));
        String sMns = String.valueOf(get_Minutes(Minutes));
        if ( Integer.valueOf(sHrs) < 10  ){sHrs = "0" + sHrs;}
        if ( Integer.valueOf(sMns) < 10  ){sMns = "0" + sMns;}
        sLapse = sHrs + ":" + sMns;
        return sLapse;        
    }
    //</editor-fold>
    
    //Converts from "HH:mm" string format to an integer amount of minutes
    public int HHmm_toMins(String sTime)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        int iHrs = Integer.valueOf(sTime.substring(0, 2));
        int iMns = Integer.valueOf(sTime.substring(3));
        int iMinutes = (iHrs * 60) + iMns;
        return iMinutes;
    }
    //</editor-fold>
    
    //Converts Month number to Month name in format MMM
    private String getMonthName(int iMonth)
    //<editor-fold defaultstate="collapsed" desc="Method Source Code">
    {
        String sMonth = "";
        switch ( iMonth )
        {
            case 1 : {sMonth = "JAN";break;}
            case 2 : {sMonth = "FEB";break;}
            case 3 : {sMonth = "MAR";break;}
            case 4 : {sMonth = "APR";break;}
            case 5 : {sMonth = "MAY";break;}
            case 6 : {sMonth = "JUN";break;}
            case 7 : {sMonth = "JUL";break;}
            case 8 : {sMonth = "AGO";break;}
            case 9 : {sMonth = "SEP";break;}
            case 10 : {sMonth = "OCT";break;}
            case 11 : {sMonth = "NOV";break;}
            case 12 : {sMonth = "DEC";break;}
        }
        return sMonth;       
    }
    //</editor-fold>
    
    //Gets the date N amount of days ago in format yyyy-MMM-dd
    public String substractDate(int iN){
        LocalDate xDaysAgo = LocalDate.now().minusDays(iN);
        String sDifDate = xDaysAgo.toString();
        String sMonNum =  sDifDate.substring(5,7);
        switch ( sMonNum ){
            case "01":{
                sDifDate = sDifDate.replaceAll("-01-", "-Ene-"); break;}
            case "02":{
                sDifDate = sDifDate.replaceAll("-02-", "-Feb-"); break;}
            case "03":{
                sDifDate = sDifDate.replaceAll("-03-", "-Mar-"); break;}
            case "04":{
                sDifDate = sDifDate.replaceAll("-04-", "-Abr-"); break;}
            case "05":{
                sDifDate = sDifDate.replaceAll("-05-", "-May-"); break;}
            case "06":{
                sDifDate = sDifDate.replaceAll("-06-", "-Jun-"); break;}
            case "07":{
                sDifDate = sDifDate.replaceAll("-07-", "-Jul-"); break;}
            case "08":{
                sDifDate = sDifDate.replaceAll("-08-", "-Ago-"); break;}
            case "09":{
                sDifDate = sDifDate.replaceAll("-09-", "-Sep-"); break;}
            case "10":{
                sDifDate = sDifDate.replaceAll("-10-", "-Oct-"); break;}
            case "11":{
                sDifDate = sDifDate.replaceAll("-11-", "-Nov-"); break;}
            case "12":{
                sDifDate = sDifDate.replaceAll("-12-", "-Dic-"); break;}
        }
        return sDifDate;
    }
}
