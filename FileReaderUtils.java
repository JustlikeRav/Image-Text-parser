package com.avenview.lib;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

public class FileReaderUtils {

    private String FILE_PATH;
    private File mFile;
    private Boolean isImage;

    public FileReaderUtils(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
        mFile = new File(FILE_PATH);
    }

    String getCorrectFileName(String destination){
        String destExt = destination.substring(destination.length() - 3);
        Boolean isMislabel = true;
        try {
            if(ImageIO.read(mFile) == null){
                isImage = false;
                isMislabel = destExt.equals("jpg");
            }else{
                isImage = true;
                isMislabel = !destExt.equals("jpg");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isMislabel){
            if(destExt.equals("jpg")) return destination.substring(0, destination.length() - 3) + "txt";
            else return destination.substring(0, destination.length() - 3) + "jpg";
        } else return "";
    }

    long getFileSize(){
        return mFile.length();
    }

    String getCreationTime(){
        try {
            String timeOCreation = Files.readAttributes(mFile.toPath(), BasicFileAttributes.class).creationTime().toString();
            return timeOCreation.substring(0, Math.min(timeOCreation.length(), 10));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    int getLineOrPx(){
        try {
            if(this.isImage) return (ImageIO.read(mFile).getHeight() * ImageIO.read(mFile).getWidth());
            else return countLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    String getPageNumber(){
        if(isImage) return "N/A";
        else {
            InputStream is = null;
            try {
                is = new FileInputStream(FILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert is != null;
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            try {
                String line = buf.readLine();

                while (line != null){
                    if(line.contains("[Pg ")){
                        int index = line.indexOf("[Pg ");
                        String omitLeft = line.substring(index + 4);
                        return omitLeft.substring(0, omitLeft.indexOf("]"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Page number not found";
    }

    private int countLines() throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(FILE_PATH))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        }
    }

    public String isDuplicate(ArrayList<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++){
            File oldFile = new File(arrayList.get(0));
            try {
                byte[] f1 = Files.readAllBytes(oldFile.toPath());
                byte[] f2 = Files.readAllBytes(mFile.toPath());

                if (Arrays.equals(f1, f2)) return "Duplicate of: " + arrayList.get(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Original";
    }
}
