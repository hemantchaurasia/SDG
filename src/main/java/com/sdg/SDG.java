package com.sdg;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.util.TimeZone;

public class SDG {
    public static void main(String args[]){

        // Read input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the size of your Synthetic data. (For example: " +
                "If you want one million records enter: 1M. " +
                "If you want two millions enter: 2M. etc..) : ");
        String numberOfRecords = scanner.next();
        System.out.println("You entered:"+numberOfRecords);

        ////Set interval for bad data and timestamp increament
        System.out.println("Please enter the interval for bad record and increment of timestamp by 1 sec : ");
        String interval = scanner.next();
        System.out.println("You entered interval :" + interval + ". This means in every" + interval + "records, there will be a bad data. And after every" + interval + "timestamp will be increment by 1 sec");

        // File input path
        System.out.println("Writing file....");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);

        File file1 = new File("output.csv");
        File file2 = new File("badDataCheatSheet.csv");
        try {
            FileWriter output = new FileWriter(file1);
            CSVWriter write = new CSVWriter(output);

            // Header column value
            String[] header = { "Seq Num", "Ticker", "Day", "Open", "High", "Low", "Close", "Volume" };
            write.writeNext(header);

            List<String> TickerFor80PercentRecord = Arrays.asList("AAPL", "AMZN", "FB", "GOOG");
            List<String> TickerFor20PercentRecord = Arrays.asList("SMSN", "JNJ", "MSFT", "PG", "V", "WMT");
            List<String> TickerForBadData = Arrays.asList("ABCD", "KJLM", "FLAG", "BRLM", "RFVC", "ASDF","CVBN");

            // 80% of numberOfRecords
            int ticker80PercentCount = calculatePercentage(80,Integer. parseInt(numberOfRecords));
            // 20% of numberOfRecords
            int ticker20PercentCount = calculatePercentage(20,Integer. parseInt(numberOfRecords));

            // Values for loop
            int i=0;
            int j=0;

            // Time stamp settings
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss(z)");
            SimpleDateFormat format2=new SimpleDateFormat("EEEE");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = cal.getTime();
            cal.setTime(date);

            Calendar calMinTimeStamp = Calendar.getInstance();
            calMinTimeStamp.set(Calendar.HOUR_OF_DAY,10);
            calMinTimeStamp.set(Calendar.MINUTE,00);
            calMinTimeStamp.set(Calendar.SECOND,0);
            calMinTimeStamp.set(Calendar.MILLISECOND,0);
            calMinTimeStamp.setTimeZone(TimeZone.getTimeZone("UTC"));

            Calendar calMaxTimeStamp = Calendar.getInstance();
            calMaxTimeStamp.set(Calendar.HOUR_OF_DAY,18);
            calMaxTimeStamp.set(Calendar.MINUTE,00);
            calMaxTimeStamp.set(Calendar.SECOND,0);
            calMaxTimeStamp.set(Calendar.MILLISECOND,0);
            calMaxTimeStamp.setTimeZone(TimeZone.getTimeZone("UTC"));

            if(cal.getTime().before(calMinTimeStamp.getTime())) {
                cal.set(Calendar.HOUR_OF_DAY, 10);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            if(cal.getTime().after(calMaxTimeStamp.getTime())) {
                date = getValidDay(cal.getTime());
            }

            int secToIncreament = 1;

            // variables for bad data
            Boolean isBadDataSet = false;
            int badDataRow = 0;
            String[] csvData = null;

            for(i=0; i<ticker80PercentCount;) {
                if(isPositionAt20(i, Integer.parseInt(interval))) {
                    cal.add(Calendar.SECOND, secToIncreament);
//                   date = cal.getTime();
                    date = getValidDay(cal.getTime());

                    //Set bad data row
                    badDataRow = getIntegerRandomInRange(i,i+Integer.parseInt(interval),Integer.parseInt(interval));//i=6
//                   System.out.println("Bad data row: " + badDataRow);
                    isBadDataSet = true;
                }
                if(i == badDataRow){
                    int badDataColumn = getIntegerRandomInRange(1,7, Integer.parseInt(interval));
//                    System.out.println("Bad data column: " + badDataColumn);
                    if (badDataColumn == 1) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerForBadData),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 2) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                new SimpleDateFormat("yy-MMM-dd:hh:mm:ss(z)").format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 3) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen() + 25),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 4) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh() + 25),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 5) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow() + 25),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 6) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose() + 25),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 7) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume() + 999999)};
                    }
                    isBadDataSet = false;
                }

                else {
                    csvData = new String[]{String.valueOf(i),
                            getRandomTicker(TickerFor80PercentRecord),
                            dateFormat.format(date),
                            String.valueOf(getOpen()),
                            String.valueOf(getHigh()),
                            String.valueOf(getLow()),
                            String.valueOf(getClose()),
                            String.valueOf(getVolume())};
                }
                write.writeNext(csvData);
                i++;
            }

            for(j=0; j<ticker20PercentCount;) {
                if(isPositionAt20(i,Integer.parseInt(interval))){
                    cal.add(Calendar.SECOND, secToIncreament);
//                   date = cal.getTime();
                    date = getValidDay(cal.getTime());

                    //Set bad data row
                    badDataRow = getIntegerRandomInRange(i,i+Integer.parseInt(interval),Integer.parseInt(interval));//i=6
//                    System.out.println("Bad data row: " + badDataRow);
                    isBadDataSet = true;
                }
                if(i == badDataRow){
                    int badDataColumn = getIntegerRandomInRange(1,7, Integer.parseInt(interval));
//                    System.out.println("Bad data column: " + badDataColumn);
                    if (badDataColumn == 1) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerForBadData),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 2) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                new SimpleDateFormat("yy-MMM-dd:hh:mm:ss(z)").format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 3) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen() + 25),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 4) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh() + 25),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 5) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow() + 25),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 6) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose() + 25),
                                String.valueOf(getVolume())};
                    }
                    else if (badDataColumn == 7) {
                        csvData = new String[]{String.valueOf(i),
                                getRandomTicker(TickerFor80PercentRecord),
                                dateFormat.format(date),
                                String.valueOf(getOpen()),
                                String.valueOf(getHigh()),
                                String.valueOf(getLow()),
                                String.valueOf(getClose()),
                                String.valueOf(getVolume() + 999999)};
                    }
                    isBadDataSet = false;
                }

                else {
                    csvData = new String[]{String.valueOf(i),
                            getRandomTicker(TickerFor20PercentRecord),
                            dateFormat.format(date),
                            String.valueOf(getOpen()),
                            String.valueOf(getHigh()),
                            String.valueOf(getLow()),
                            String.valueOf(getClose()),
                            String.valueOf(getVolume())};
                }
                write.writeNext(csvData);
                j++;i++;
            }
            write.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        System.out.println("File writing done!");
        timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
    }

    public static String getTicker() {
        return "Ticker";
    }

    public static String getDay() {
        return "Day";
    }

    public static float getOpen() {
        return getFloatRandomInRange((float)1.01,(float)19.09);
    }

    public static float getHigh() {
        return getFloatRandomInRange((float)1.01,(float)19.09);
    }

    public static float getLow() {
        return getFloatRandomInRange((float)1.01,(float)19.09);
    }

    public static float getClose() {
        return getFloatRandomInRange((float)1.01,(float)20.09);
    }

    public static int getVolume() {
        return getIntegerRandomInRange(10000,999999, 20);
    }

    public static int calculatePercentage(int selected, int total){
        return selected*total/100;
    }

    public static String getRandomTicker(List<String> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static Boolean isPositionAt20 (int position, int interval){
        return position % interval == 0;
    }

    public static float getFloatRandomInRange (float min, float max){
        Random rand = new Random();
        return min + rand.nextFloat() * (max - min);
    }

    public static int getIntegerRandomInRange (int min, int max, int interval){
        Random rand = new Random();
        int intRandom = (int)(Math.random() * (max - min + 1) + min);
        return (intRandom % interval == 0) ? getIntegerRandomInRange(min, max, interval) : intRandom;
    }

    public static Date getValidDay (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss(z)");
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
        String day = format2.format(date);
//        System.out.println("Current Day: " + day);
//        System.out.println("Current Time: " + cal.getTime());

        //To verify hard coded any day logic
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//        cal.set(Calendar.HOUR_OF_DAY,20);
//        day = "Saturday";

        if (day.equals("Sunday")) {
            cal.add(Calendar.DATE, +1);
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        else if (day.equals("Saturday") && cal.get(Calendar.HOUR_OF_DAY) > 18) {
            cal.add(Calendar.DATE, +2);
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        else if (cal.get(Calendar.HOUR_OF_DAY) <10 || cal.get(Calendar.HOUR_OF_DAY) > 18) {
            cal.add(Calendar.DATE, +1);
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
//        System.out.println("If any other day then Incremented Day: " + format2.format(cal.getTime()));
//        System.out.println("If any other day then Incremented DATE: " + dateFormat.format(cal.getTime()));

        return date;
    }
}
