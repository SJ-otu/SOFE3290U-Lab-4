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
        String filePath = "model.csv";
        FileReader filereader;
        List<String[]> allData;

        try {
            filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            allData = csvReader.readAll();
        } catch(Exception e) {
            System.out.println("Error reading the CSV file");
            return;
        }

        int numClasses = 5;
        int[][] confusionMatrix = new int[numClasses][numClasses];
        double crossEntropy = 0.0;
        int n = allData.size();

        for (String[] row : allData) {
            int y_true = Integer.parseInt(row[0]); // actual class
            float[] y_predicted = new float[numClasses];
            int predictedClass = 0;
            float maxProb = -1.0f;

            for (int i = 0; i < numClasses; i++) {
                y_predicted[i] = Float.parseFloat(row[i+1]);
                if (y_predicted[i] > maxProb) {
                    maxProb = y_predicted[i];
                    predictedClass = i + 1; // classes are 1-indexed
                }
            }

            // update confusion matrix
            confusionMatrix[predictedClass-1][y_true-1]++;

            // accumulate cross entropy
            crossEntropy += -Math.log(y_predicted[y_true-1]);
        }

        crossEntropy /= n;

        // print CE
        System.out.println("CE = " + crossEntropy);

        // print confusion matrix
        System.out.println("\nConfusion matrix");
        System.out.print("\t");
        for (int j = 1; j <= numClasses; j++) {
            System.out.print("y=" + j + "\t");
        }
        System.out.println();
        for (int i = 0; i < numClasses; i++) {
            System.out.print("y^=" + (i+1) + "\t");
            for (int j = 0; j < numClasses; j++) {
                System.out.print(confusionMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}