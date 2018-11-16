package com.avenview.lib;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MyClass {

    private final static String
            CSV_FILE = "C:\\Users\\alex.p\\Desktop\\ishita\\data.csv",
            SPLIT_BY = ",",
            HOME_DIR = "C:\\Users\\alex.p\\Desktop\\ishita\\";

    private static final String
            COLUMN_REPO_PATH = "\"Repository Path\"",
            COLUMN_COPY_PATH = "\"Copy Path\"",
            COLUMN_CORRECTED_FILE_PATH = "\"Corrected File Path\"",
            COLUMN_DUPLICATE_FILES = "\"Duplicate_Files\"",
            COLUMN_FILE_SIZE = "\"File Size\"",
            COLUMN_DATE_O_CREATION = "\"Date of File Creation\"",
            COLUMN_LINE_O_PX = "\"Line / Pixel Count\"",
            COLUMN_PAGE_NO = "\"Page Number\"";

    public static void main(String[] args) {
        BufferedReader br = null;
        String line;
        try {

            br = new BufferedReader(new FileReader(CSV_FILE));
            boolean first = true;
            ArrayList<String> REPO_PATHS = new ArrayList<>();

            StringBuilder CSVstring = new StringBuilder();
            CSVstring.append(COLUMN_REPO_PATH);
            CSVstring.append(',');
            CSVstring.append(COLUMN_COPY_PATH);
            CSVstring.append(',');
            CSVstring.append(COLUMN_CORRECTED_FILE_PATH);
            CSVstring.append(',');
            CSVstring.append(COLUMN_DUPLICATE_FILES);
            CSVstring.append(',');
            CSVstring.append(COLUMN_FILE_SIZE);
            CSVstring.append(',');
            CSVstring.append(COLUMN_DATE_O_CREATION);
            CSVstring.append(',');
            CSVstring.append(COLUMN_LINE_O_PX);
            CSVstring.append(',');
            CSVstring.append(COLUMN_PAGE_NO);
            CSVstring.append('\n');

            while ((line = br.readLine()) != null) {

                if (first){
                    first = false;
                    continue;
                }

                String
                       REPO_PATH = line.split(SPLIT_BY)[0],
                       COPY_PATH = line.split(SPLIT_BY)[1],
                       CORRECTED_FILE_PATH = "",
                       DUPLICATE_FILE,
                       FILE_SIZE,
                       DATE_O_CREATION,
                       LINE_O_PX,
                       PAGE_NO;

                String source = HOME_DIR + REPO_PATH.substring(1),
                        destination = HOME_DIR + COPY_PATH.substring(1);

                REPO_PATHS.add(source);

                FileReaderUtils fileReaderUtils = new FileReaderUtils(source);

                //PROGRAMMING TASK 1.1
                CORRECTED_FILE_PATH = fileReaderUtils.getCorrectFileName(destination);

                //PROGRAMMING TASK 1.2
                DUPLICATE_FILE = fileReaderUtils.isDuplicate(REPO_PATHS);

                //PROGRAMMING TASK 1.3
                FILE_SIZE = Long.toString(fileReaderUtils.getFileSize());

                //PROGRAMMING TASK 1.4
                DATE_O_CREATION = fileReaderUtils.getCreationTime();

                //PROGRAMMING TASK 1.5
                LINE_O_PX = Integer.toString(fileReaderUtils.getLineOrPx());

                //PROGRAMMING TASK 1.6
                PAGE_NO = fileReaderUtils.getPageNumber();

                CSVstring.append(REPO_PATH);
                CSVstring.append(',');
                CSVstring.append(COPY_PATH);
                CSVstring.append(',');
                CSVstring.append(CORRECTED_FILE_PATH);
                CSVstring.append(',');
                CSVstring.append(DUPLICATE_FILE);
                CSVstring.append(',');
                CSVstring.append(FILE_SIZE);
                CSVstring.append(',');
                CSVstring.append(DATE_O_CREATION);
                CSVstring.append(',');
                CSVstring.append(LINE_O_PX);
                CSVstring.append(',');
                CSVstring.append(PAGE_NO);
                CSVstring.append('\n');

                System.out.println(CORRECTED_FILE_PATH +
                " " + DUPLICATE_FILE + " FileSize: " + FILE_SIZE + " Date:" + DATE_O_CREATION + "Lines: " + LINE_O_PX + " Page no: " + PAGE_NO);
            }

            PrintWriter pw = new PrintWriter(new File(HOME_DIR + "results.csv"));
            pw.write(CSVstring.toString());
            pw.close();
            System.out.println("done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
