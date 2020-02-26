package com.app.services.dataGenerator;

import com.app.model.exception.AppException;

import java.util.Scanner;


public class DataManager {

    private DataManager() {}

    private final static Scanner sc = new Scanner(System.in);

    public static Integer getInt(String message) {
        System.out.println(message);

        String line = sc.nextLine();
        if (line == null || !line.matches("\\d+")) {
            throw new AppException(" WRONG DATA ");
        }

        return Integer.parseInt(line);
    }

    public static Integer getDouble(String message) {
        System.out.println(message);

        String line = sc.nextLine();
        if (line == null || !line.matches("\\d+\\.\\d{2}")) {
            throw new AppException(" WRONG DATA ");
        }

        return Integer.parseInt(line);
    }

    public static String getLine(String message) {
        System.out.println(message);
        return sc.nextLine();
    }


    public static boolean getBoolean(String message) {
        System.out.println(message + "[y/n]?");
        return sc.nextLine().toUpperCase().charAt(0) == 'Y';
    }

    public void close() {
        if (sc != null) {
            sc.close();
        }
    }

}
