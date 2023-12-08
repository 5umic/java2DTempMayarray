import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        int[][] weatherData = new int[31][2];

        // Fill the arrays with temperature and day pairs
        for (int i = 0; i < 31; i++) {
            weatherData[i][0] = random.nextInt(20) + 10; // Temperature
            weatherData[i][1] = i + 1; // Day
        }

        // Display menu and handle user input
        while (true) {
            System.out.println("\nMeny, välj mellan 0-9 och X för att avsluta :");
            System.out.println("1. Lista temperaturdata");
            System.out.println("2. Beräkna medeltemperaturen");
            System.out.println("3. Hitta dagen med högsta temperaturen");
            System.out.println("4. Hitta dagen med lägsta temperaturen");
            System.out.println("5. Beräkna mediantemperaturen");
            System.out.println("6. Sortera temperaturerna lägsta först");
            System.out.println("7. Sortera temperaturerna högsta först");
            System.out.println("8. Filtrera temperaturdata med tröskelvärde");
            System.out.println("9. Visa temperaturer för en specifik dag");
            System.out.println("0. Visa vanligaste temperaturen");
            System.out.println("X. Avsluta programmet");
            String choice = input.next();

            switch (choice) {
                case "1":
                    listTemperatureData(weatherData);
                    break;
                case "2":
                    calcAvgTemp(weatherData);
                    break;
                case "3":
                    highestTemp(weatherData);
                    break;
                case "4":
                    lowestTemp(weatherData);
                    break;
                case "5":
                    calcMedianTemp(weatherData);
                    break;
                case "6":
                    sortTemperaturesAsc(weatherData);
                    break;
                case "7":
                    sortTemperaturesDesc(weatherData);
                    break;
                case "8":
                    filterTemperaturesOverThreshold(weatherData, input);
                    break;
                case "9":
                    showTemperaturesForSpecificDay(weatherData, input);
                    break;
                case "0":
                    showMostCommonTemp(weatherData);
                    break;
                case "x":
                    System.out.println("Programmet avslutas.");
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Ogiltigt val. Vänligen försök igen.");
            }
        }
    }

    private static void listTemperatureData(int[][] weatherData) {
        System.out.println("Temperatur för dagarna i maj");
        for (int[] weatherDatum : weatherData) {
            String dayString = addSuffixToDay(weatherDatum[1]);
            System.out.println(dayString + " - var det " + weatherDatum[0] + " grader");
        }
    }

    private static void calcAvgTemp(int[][] weatherData) {
        int total = 0;
        for (int[] data : weatherData) {
            total += data[0];
        }
        double averageTemp = (double) total / weatherData.length;
        System.out.println("Medeltemperaturen under månaden maj var: " + averageTemp);
    }

    private static void highestTemp(int[][] weatherData) {
        int highestTemp = weatherData[0][0];
        int dayIndex = 0;

        for (int i = 1; i < weatherData.length; i++) {
            if (weatherData[i][0] > highestTemp) {
                highestTemp = weatherData[i][0];
                dayIndex = i;
            }
        }
        String dayString = addSuffixToDay(weatherData[dayIndex][1]);
        System.out.println("Högsta temperaturen i maj var " + highestTemp + " den " + dayString);
    }

    private static void lowestTemp(int[][] weatherData) {
        int lowestTemp = weatherData[0][0];
        int dayIndex = 0;

        for (int i = 1; i < weatherData.length; i++) {
            if (weatherData[i][0] < lowestTemp) {
                lowestTemp = weatherData[i][0];
                dayIndex = i;
            }
        }
        String dayString = addSuffixToDay(weatherData[dayIndex][1]);
        System.out.println("Lägsta temperaturen i maj var " + lowestTemp + " den " + dayString);
    }

    private static void sortTemperaturesAsc(int[][] weatherData) {
        Arrays.sort(weatherData, Comparator.comparingInt(data -> data[0]));

        System.out.println("Temperaturerna sorterade i stigande ordning:");
        for (int i = 0; i < weatherData.length; i++) {
            String dayString = addSuffixToDay(weatherData[i][1]);
            System.out.println(dayString + " - " + weatherData[i][0] + " grader");
        }
    }

    private static void sortTemperaturesDesc(int[][] weatherData) {
        Arrays.sort(weatherData, Comparator.comparingInt((int[] data) -> data[0]).reversed());

        System.out.println("Temperaturerna sorterade i fallande ordning:");
        for (int i = 0; i < weatherData.length; i++) {
            String dayString = addSuffixToDay(weatherData[i][1]);
            System.out.println(dayString + " - " + weatherData[i][0] + " grader");
        }
    }

    private static void calcMedianTemp(int[][] weatherData) {
        Arrays.sort(weatherData, Comparator.comparingInt(data -> data[0]));
        double median;

        if (weatherData.length % 2 == 0) {
            median = (weatherData[weatherData.length / 2 - 1][0] + weatherData[weatherData.length / 2][0]) / 2.0;
        } else {
            median = weatherData[weatherData.length / 2][0];
        }
        System.out.println("Mediantemperaturen för maj är: " + median + " grader");
    }

    private static void filterTemperaturesOverThreshold(int[][] weatherData, Scanner scanner) {
        System.out.print("Ange tröskelvärde för temperatur: ");
        int threshold = scanner.nextInt();

        for (int i = 0; i < weatherData.length; i++) {
            if (weatherData[i][0] > threshold) {
                String dayString = addSuffixToDay(weatherData[i][1]);
                System.out.println(dayString + " - " + weatherData[i][0] + " grader");
            }
        }
    }

    private static void showTemperaturesForSpecificDay(int[][] weatherData, Scanner scanner) {
        System.out.print("Ange dagen i maj (1-31): ");
        int selectedDay = scanner.nextInt();

        if (selectedDay < 1 || selectedDay > weatherData.length) {
            System.out.println("Ogiltigt dagnummer. Vänligen försök igen.");
        } else {
            String dayString = addSuffixToDay(selectedDay);
            System.out.println("Dag " + dayString + " - " + weatherData[selectedDay - 1][0] + " grader");
        }
    }

    private static void showMostCommonTemp(int[][] weatherData) {
        if (weatherData.length == 0) {
            System.out.println("Ingen temperaturdata tillgänglig.");
            return;
        }

        int mostCommonTemperature = weatherData[0][0];
        int maxFrequency = 1;

        for (int i = 0; i < weatherData.length; i++) {
            int currentTemperature = weatherData[i][0];
            int currentFrequency = 1;

            for (int j = i + 1; j < weatherData.length; j++) {
                if (weatherData[j][0] == currentTemperature) {
                    currentFrequency++;
                }
            }

            if (currentFrequency > maxFrequency) {
                maxFrequency = currentFrequency;
                mostCommonTemperature = currentTemperature;
            }
        }

        System.out.println("Den vanligaste temperaturen i maj är: " + mostCommonTemperature + " grader");
    }

    private static String addSuffixToDay(int day) {
        if (day <= 0) {
            return " - ";
        }
        int lastDigit = day % 10;
        int secondToLastDigit = (day / 10) % 10;

        if ((lastDigit == 1 || lastDigit == 2) && secondToLastDigit != 1) {
            return day + ":a maj";
        } else {
            return day + ":e maj";
        }
    }
}
