package com.vo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class test {

    public static void main(String args[]) {
//        URL url = ClassLoader.getSystemResource("log4j2.properties");
//        if (url == null) {
//            System.out.println("log4j.properties not found");
//        } else {
//            System.out.println("log4j.properties found at " + url);
//        }
        
        
        String filepath=""; ClassLoader cl; cl = Thread.currentThread().getContextClassLoader(); if( cl == null )cl = ClassLoader.getSystemClassLoader(); URL url = cl.getResource( "log4j2.properties" ); if (url == null)System.out.println("null"); else {filepath = url.getFile(); System.out.println("path :" + filepath); }

        Properties props = new Properties(); try {props.load(new InputStreamReader(new FileInputStream(filepath))); } catch (IOException e) {e.printStackTrace(); }String excel_root_path = props.getProperty("excel_root_path"); System.out.println("file path=" + excel_root_path);

    }
}
