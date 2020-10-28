package com.shenrubot;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Util {
    //get current timestamp for footer
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getToken(String keyloc) {
        // pass the path to the file as a parameter

        File file = new File(keyloc);
        try {
            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) {
                return sc.nextLine();
            }
        } catch (FileNotFoundException e) {

            System.out.println("cannot find api key file, Assuming running through heroku");
            return System.getenv("KEY");
        }
        return null;
    }
}
