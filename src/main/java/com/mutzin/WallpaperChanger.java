package com.mutzin;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class WallpaperChanger {

    public static boolean change(String imageUrl) {
        try {
            Path currentDir = Path.of("").toAbsolutePath();
            System.out.println("실행 폴더: " + currentDir);

            // create downloadImg directory
            Path downloadDir = currentDir.resolve("downloadImg");
            if (!Files.exists(downloadDir)) {
                Files.createDirectories(downloadDir);
            }

            // change image name
            Path savePath = downloadDir.resolve(
                    "wallpaper_" + System.currentTimeMillis() + ".jpg"
            );

            // download image
            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
            }

            Thread.sleep(500);

            System.out.println("저장 완료: " + savePath);

            // script for mac
            String script =
                    "delay 0.5\n" +
                            "tell application \"System Events\"\n" +
                            "    repeat with d in desktops\n" +
                            "        set picture of d to POSIX file \"" +
                            savePath.toAbsolutePath().toString() +
                            "\"\n" +
                            "    end repeat\n" +
                            "end tell";

            ProcessBuilder pb = new ProcessBuilder("osascript", "-e", script);
            Process process = pb.start();
            process.waitFor();

            String error = new String(process.getErrorStream().readAllBytes());
            if (!error.isEmpty()) {
                System.out.println("AppleScript Error: " + error);
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}