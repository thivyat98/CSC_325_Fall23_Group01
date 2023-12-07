package com.example.csc325.csc325;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * 
 */
public class FileManipulatorNote {
     public static String fileFullPath = "src/main/resources/com/mycompany/loginauthproject/";

     public static String readFile(String fileName, int lineNumber) {
        try {
            System.out.println(fileFullPath+fileName);
            File file = new File(fileFullPath+fileName);
            try (Scanner myReader = new Scanner(file)) {
                int lineCounter = 0;
                String data ="";
                while (myReader.hasNextLine() && lineCounter < lineNumber) {
                    data = myReader.nextLine();
                    System.out.println(data);
                    lineCounter++;
                }
                if(lineCounter < lineNumber)
                    return "";
                return data;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return "";
        }
    }

    public  void writeToFile(String fileName, String myText) {
        try {
            FileWriter myWriter = new FileWriter(fileFullPath+fileName);
            //myWriter.write();
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.getMessage();
        }
    }

    public static void getFileInfo(String fileName) {
        File file = new File(fileFullPath+fileName);
        if (file.exists()) {
            System.out.println("File name: " + file.getName());
            System.out.println("Absolute path: " + file.getAbsolutePath());
            System.out.println("Writeable: " + file.canWrite());
            System.out.println("Readable " + file.canRead());
            System.out.println("File size in bytes " + file.length());
        } else {
            System.out.println("The file does not exist.");
        }
    }

    public static void createFile(String fileName) {
        try {
            File file = new File(fileFullPath+fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void createDirectory(String fileName) {
        String directoryName = "";
        try {

            System.out.println("Enter the path to create a directory: ");
            Scanner sc = new Scanner(System.in);
            //Creating a File object
            directoryName = sc.next();
            File file = new File(directoryName);
            //Creating the directory
            boolean bool = file.mkdir();
            if (bool) {
                System.out.println("Directory created successfully");
            } else {
                System.out.println("Sorry couldnâ€™t create specified directory");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.getMessage();
        } finally {
            fileFullPath = directoryName + "/" + "text.txt";
            createFile(fileName);
        }

    }
}