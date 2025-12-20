// Main.java — Students version
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
        if(from < 1 || to > DAYS || from > to) {
            return -99999;
        }
        int value = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                value = i;
            }
        }
        if (value == -1) {
            return -99999;
        }
        int totalProfit = 0;
        for (int i = 0; i < MONTHS; i++) {
            for (int j = from -1; j<= to - 1; j++) {
                totalProfit += profits[i][j][value];
            }
        }
        return totalProfit;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >+ MONTHS) {
            return -1;
        }
        int bestDay = 1;
        int highest = 0;
        for (int i = 0; i < DAYS ; i++) {
            int sum = 0;
            for (int j = 0; j < COMMS; j++) {
                sum += profits[month][i][j];
            }
            if (sum > highest) {
                highest = sum;
                bestDay = i + 1;
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int value = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                value = 0;
            }
            if ( value == 0) {
                return "INVALID COMMODITY";
            }
        }
        int bestMonth = 0;
        int highest = 0;
        for (int i = 0; i<MONTHS; i++) {
            int sum = 0;
            for(int j = 0; j < DAYS; j++) {
                sum += profits[i][j][value];
            }
            if (sum > highest) {
                highest = sum;
                bestMonth = i;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int value = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                value = i;
            }
        }
        if (value == -1) {
            return -1;
        }
        int totalStreak = 0;
        int currentStreak = 0;
        for (int i = 0; i <MONTHS; i++) {
            for(int j = 0; j< DAYS; j++ ) {
                if (profits[i][j][value] < 0) {
                    currentStreak = currentStreak +1;
                    if (currentStreak > totalStreak) {
                        totalStreak = currentStreak;
                    }
                } else {
                    currentStreak = 0;
                }
            }
        }

        return totalStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int value = -1;
        for (int i= 0; i < COMMS; i++) {
            if(commodities[i].equals(comm)) {
                value = i;
            }
        }
        if (value == -1) {
            return -1;
        }
        int days = 0;
        for (int i = 0; i <MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                if (profits[i][j][value] > threshold) {
                    days = days +1;
                }
            }
        }
        return days;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) {
            return -99999;
        }
        int biggestDifference = 0;
        for (int i = 0; i < DAYS; i++) {
            int today = 0;
            int yesterday = 0;
            for (int j = 0; j<COMMS; j++) {
                today += profits[month][i][j];
                yesterday += profits[month][i-1][j];
            }
            int difference = today -yesterday;
            if (difference<0) {
                difference = - difference;
            }
            if (difference> biggestDifference) {
                biggestDifference = difference;
            }
        }
        return biggestDifference;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int commodityValue1 = -1;
        int commodityValue2 = -1;
        for (int i = 0; i< COMMS; i++) {
            if ((commodities[i].equals(c1))) {
                commodityValue1 = i;
            }
            if (commodities[i].equals(c2)) {
                commodityValue2 = i;
            }
        }
        if (commodityValue1 == -1 || commodityValue2 == -1) {
            return "INVALID COMMODITY";
        }
        int profitValue1 = 0;
        int profitValue2 = 0;
        for (int i = 0; i< MONTHS; i++) {
            for (int j = 0; j<DAYS; j++) {
                profitValue1 = profits[i][j][commodityValue1];
                profitValue2 = profits[i][j][commodityValue2];
            }
        }
        if (profitValue1 > profitValue2) {
            return c1 + " is better by " + (profitValue1 - profitValue2);
        } else if (profitValue2 > profitValue1) {
            return c2 + " is better by " + (profitValue2-profitValue1);
        } else {
            return "Equal";
        }
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}