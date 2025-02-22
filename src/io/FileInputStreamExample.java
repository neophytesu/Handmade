package io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileInputStreamExample {
    public static void main(String[] args) {
        try (FileReader fis = new FileReader(System.getProperty("user.dir")+"/src/io/input.txt")) {
            int content;
            long skip=fis.skip(2);
            System.out.println("The actual number of bytes skipped: "+skip);
            System.out.println("The content read from file:");
            while ((content=fis.read())!=-1){
                System.out.print((char) content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
