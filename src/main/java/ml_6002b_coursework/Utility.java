package ml_6002b_coursework;

public class Utility {
    public static void printMeasure(String measure, String cls){
        System.out.println("measure " + measure + " for headache splitting diagnosis = " + cls);
    }

    public static void printSplit(String measure, String attribute, String cls){
        System.out.println("measure " + measure + " for attribute" + attribute + " splitting diagnosis = " + cls);
    }

    public static void printMeasureAccuracy(String measure, String problem, double accuracy){
        System.out.println("Id3 using measure " + measure + " on " + problem + " Problem has test accuracy = " + accuracy);
    }
}
