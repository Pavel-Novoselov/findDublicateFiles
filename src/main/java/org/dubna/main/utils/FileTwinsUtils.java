package org.dubna.main.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FileTwinsUtils {
    private static final Logger logger = Logger.getLogger("logger");
    private final File root1;// = new File("C:\\Users\\pavel.novoselov");
    private final File root2;
    private final List<CompareFile> fileList1;
    private final List<CompareFile> fileList2;

    public FileTwinsUtils(String root1, String root2) {
        this.root1 = new File(root1);
        this.root2 = new File(root2);
        fileList1 = searchAllFiles(this.root1, null);
        logger.info("found " + fileList1.size() + "files in folder " + root1 + " and its subfolders");
        fileList2 = searchAllFiles(this.root2, null);
        logger.info("found " + fileList2.size() + "files in folder " + root2 + " and its subfolders");
    }

    @Nonnull
    public List<CompareFile> searchAllFiles(@Nullable final File rootFile, @Nullable List<CompareFile> resultList) {
        if (rootFile == null) {
            return new ArrayList<>();
        }
        if (resultList == null){
            resultList = new ArrayList<>();
        }
        if (rootFile.isDirectory()) {
            logger.info("folder: " + rootFile.getAbsolutePath());
            final File[] files = rootFile.listFiles();
            if (files != null && files.length != 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        searchAllFiles(file, resultList);
                    } else {
                        resultList.add(new CompareFile(file, file.getName(), file.getAbsolutePath()));
                        logger.info("added file: " + file.getName());
                    }
                }
            }
        }
        return resultList;
    }

    @Nonnull
    public Map<CompareFile, List<CompareFile>> compareFiles() {
        final List<CompareFile> first = new ArrayList<>(fileList1);
        final List<CompareFile> second = new ArrayList<>(fileList2);
        if (first.isEmpty() || second.isEmpty()){
            return new HashMap<>();
        }
        final Map<CompareFile, List<CompareFile>> result = new HashMap<>();
        for (CompareFile firstCompareFile : first) {
            List<CompareFile> tempList = null;
            for (CompareFile secondCompareFile : second) {
                if (secondCompareFile == firstCompareFile){
                    continue;
                }
                if (secondCompareFile.equals(firstCompareFile)) {
                    tempList = result.computeIfAbsent(firstCompareFile, k -> new ArrayList<>());
                    tempList.add(secondCompareFile);
                }
            }
            if (tempList != null) {
                second.remove(firstCompareFile);
                second.removeAll(tempList);
            }
        }
        return result;
    }

    public static boolean deleteFiles(String fileName){
        if(fileName == null || fileName.isEmpty()){
            return false;
        }
        File file = new File(fileName);
//        return file.exists();
        return file.delete();
    }

    public static void showFile(String name){
        if (name == null || name.isEmpty()){
            return;
        }
        ProcessBuilder processBuilder;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win")){
            processBuilder = new ProcessBuilder("cmd.exe", "/c", name);
        } else if(os.toLowerCase().contains("nix") || os.contains("nux")) {
            processBuilder = new ProcessBuilder("gedit", "-c", name); // gedit fname  imagereader
//        } else if (os.toLowerCase().contains("mac")){
//            processBuilder = new ProcessBuilder("/bin/bash", "-c", name);
        } else {
            return;
        }
        try {
            final Process exec = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class CompareFile {
        File file;
        String name;
        String absPath;

        public CompareFile(File file, String name, String absPath) {
            this.file = file;
            this.name = name;
            this.absPath = absPath;
        }

        public File getFile() {
            return file;
        }

        public String getName() {
            return name;
        }

        public String getAbsPath() {
            return absPath;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null){
                return false;
            }
            return obj instanceof CompareFile
                    && !this.absPath.equals(((CompareFile) obj).absPath)
                    && this.name.equals(((CompareFile) obj).name)
                    && this.file.length() == ((CompareFile) obj).file.length();
        }

        @Override
        public String toString() {
            return "full path name: " + this.absPath + "\n" + "length: " + this.file.length();
        }
    }
}
