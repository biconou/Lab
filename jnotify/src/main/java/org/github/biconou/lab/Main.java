package org.github.biconou.lab;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by remi on 24/06/16.
 */
public class Main {

    private static class Listener implements JNotifyListener {

        @Override
        public void fileCreated(int i, String s, String s1) {
            System.out.println("fileCreated : "+i+" "+s+" "+s1);
        }

        @Override
        public void fileDeleted(int i, String s, String s1) {
            System.out.println("fileDeleted : "+i+" "+s+" "+s1);
        }

        @Override
        public void fileModified(int i, String s, String s1) {
            System.out.println("fileModified : "+i+" "+s+" "+s1);
        }

        @Override
        public void fileRenamed(int i, String s, String s1, String s2) {
            System.out.println("fileRenamed : "+i+" "+s+" "+s1);
        }

    }


    public static void main(String[] args) {
        int mask = JNotify.FILE_CREATED  |
                JNotify.FILE_DELETED  |
                JNotify.FILE_MODIFIED |
                JNotify.FILE_RENAMED;

        String dirPath = Main.class.getClass().getResource("/").getPath();

        try {
            int watchID = JNotify.addWatch(dirPath, mask, true, new Listener());
        } catch (JNotifyException e) {
            e.printStackTrace();
        }

        try {
            File file = new File(dirPath+"/test.txt");

            FileOutputStream fos = new FileOutputStream(file);
            fos.write("toto li toto".getBytes());
            fos.flush();
            fos.close();

            File destFile = new File(dirPath+"/test2.txt");

            FileUtils.copyFile(file,destFile);


            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
