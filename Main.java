// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int [][][] profits = new int[MONTHS][DAYS][COMMS];
    //I had to change main because I couldn't use it outside of loaddata.

    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for(int i = 0; i<MONTHS; i++) {
            Scanner reader = null;
            try {
                reader = new Scanner(java.nio.file.Paths.get("Data_Files/" + months[i] + ".txt"));
                //Instead of importing it I used java.nio.file.paths this way because I'm trying to change main as
                //least as possible.
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] parts = line.split(",");
                    int day = Integer.parseInt(parts[0]) - 1;
                    String commodity = parts[1];
                    int profit = Integer.parseInt(parts[2]);
                    for (int j = 0; j < COMMS; j++) {
                        if (commodities[j].equals(commodity)) {
                            profits[i][day][j] = profit;
                        }
                    }
                }
            }
            catch (Exception e) {
            } finally {
                if (reader !=null) {
                    reader.close();
                }
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month <0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        int highestSum = 0;
        int highestIndex = 0;
        for(int i = 0; i< COMMS; i++) {
            int sum = 0;
            for (int j = 0; j < DAYS; j++) {
                sum += profits[month][j][i];
            }
            if (sum > highestSum) {
                highestSum = sum;
                highestIndex = i;
            }
        }
        return commodities[highestIndex] + " " + highestSum;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;
        }
        int sum = 0;
        for (int i = 0 ; i < COMMS; i++) {
            sum += profits[month][day-1][i];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}