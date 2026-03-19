package com.ontariotechu.sofe3980U;

import java.io.FileReader;
import java.util.List;
import com.opencsv.*;

/**
 * Evaluate Single Variable Continuous Regression
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        double[] m1 = evaluateModel("model_1.csv");
        double[] m2 = evaluateModel("model_2.csv");
        double[] m3 = evaluateModel("model_3.csv");

        // Compare MSE
        if (m1[0] < m2[0] && m1[0] < m3[0])
            System.out.println("According to MSE, The best model is model_1.csv");
        else if (m2[0] < m1[0] && m2[0] < m3[0])
            System.out.println("According to MSE, The best model is model_2.csv");
        else
            System.out.println("According to MSE, The best model is model_3.csv");

        // Compare MAE
        if (m1[1] < m2[1] && m1[1] < m3[1])
            System.out.println("According to MAE, The best model is model_1.csv");
        else if (m2[1] < m1[1] && m2[1] < m3[1])
            System.out.println("According to MAE, The best model is model_2.csv");
        else
            System.out.println("According to MAE, The best model is model_3.csv");

        // Compare MARE
        if (m1[2] < m2[2] && m1[2] < m3[2])
            System.out.println("According to MARE, The best model is model_1.csv");
        else if (m2[2] < m1[2] && m2[2] < m3[2])
            System.out.println("According to MARE, The best model is model_2.csv");
        else
            System.out.println("According to MARE, The best model is model_3.csv");
    }

    public static double[] evaluateModel(String filePath)
    {
        FileReader filereader;
        List<String[]> allData;

        try {
            filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            allData = csvReader.readAll();
        }
        catch(Exception e){
            System.out.println("Error reading the CSV file: " + filePath);
            return new double[]{0,0,0};
        }

        double mse = 0;
        double mae = 0;
        double mare = 0;
        double epsilon = 1e-10;

        int count = 0;

        for (String[] row : allData) {
            double y_true = Double.parseDouble(row[0]);
            double y_predicted = Double.parseDouble(row[1]);

            double error = y_true - y_predicted;

            mse += Math.pow(error, 2);
            mae += Math.abs(error);
            mare += Math.abs(error) / (Math.abs(y_true) + epsilon);

            count++;
        }

        mse /= count;
        mae /= count;
        mare /= count;

        System.out.println("for " + filePath);
        System.out.println("\tMSE = " + mse);
        System.out.println("\tMAE = " + mae);
        System.out.println("\tMARE = " + mare);

        return new double[]{mse, mae, mare};
    }
}