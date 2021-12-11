package org.dubna.main.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public final class FileTwinsUtils {
    private static final Logger logger = Logger.getLogger("logger");
    private final File root1;
    private final File root2;
    private final List<CompareFile> fileList1;
    private final List<CompareFile> fileList2;

    public FileTwinsUtils(final String root1, final String root2) {
        this.root1 = new File(root1);
        this.root2 = new File(root2);
        fileList1 = searchAllFiles(this.root1, null);
        logger.info("found " + fileList1.size() + " files in folder " + root1 + " and its subfolders");
        fileList2 = searchAllFiles(this.root2, null);
        logger.info("found " + fileList2.size() + " files in folder " + root2 + " and its subfolders");
    }

    @Nonnull
    public List<CompareFile> searchAllFiles(@Nullable final File rootFile, @Nullable List<CompareFile> resultList) {
        if (rootFile == null) {
            return new ArrayList<>();
        }
        if (resultList == null) {
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
        if (first.isEmpty() || second.isEmpty()) {
            return new HashMap<>();
        }
        final Map<CompareFile, List<CompareFile>> result = new HashMap<>();
        for (CompareFile firstCompareFile : first) {
            List<CompareFile> tempList = null;
            for (CompareFile secondCompareFile : second) {
                if (secondCompareFile == firstCompareFile) {
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
        logger.info("found " +
                    result.size() +
                    " groupe(s) of dublicates include " +
                    result.values().stream().mapToInt(Collection::size).sum() +
                    " files");
        return result;
    }

    public static boolean deleteFile(final String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        final File file = new File(fileName);
//        return file.exists(); //for debug
        return file.delete();
    }

    public static void showFile(final String name) {
        if (name == null || name.isEmpty()) {
            return;
        }
        ProcessBuilder processBuilder;
        final String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win")) {
            processBuilder = new ProcessBuilder("cmd.exe", "/c", name);
        } else if (os.toLowerCase().contains("nix") || os.contains("nux")) {
            processBuilder = new ProcessBuilder("/bin/bash", "-c", "xdg-open", name);
        } else if (os.toLowerCase().contains("mac")) {
            processBuilder = new ProcessBuilder("/bin/bash", "-c", "open", name);
        } else {
            return;
        }
        try {
            final Process exec = processBuilder.start();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public List<CompareFile> getFileList1() {
        return fileList1;
    }

    public List<CompareFile> getFileList2() {
        return fileList2;
    }

    public static final class CompareFile {
        private File file;
        private String name;
        private String absPath;

        public CompareFile(final File file, final String name, final String absPath) {
            this.setFile(file);
            this.setName(name);
            this.setAbsPath(absPath);
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
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            return obj instanceof CompareFile
                   && !this.getAbsPath().equals(((CompareFile) obj).getAbsPath())
                   && this.getName().equals(((CompareFile) obj).getName())
                   && this.getFile().length() == ((CompareFile) obj).getFile().length();
        }

        @Override
        public String toString() {
            return "full path name: " + this.getAbsPath() + "\n" + "length: " + this.getFile().length();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getFile(), getName(), getAbsPath());
        }

        public void setFile(final File file) {
            this.file = file;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public void setAbsPath(final String absPath) {
            this.absPath = absPath;
        }
    }
}
