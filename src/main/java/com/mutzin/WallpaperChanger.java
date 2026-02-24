package com.mutzin;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class WallpaperChanger {

    public static void change(String imageUrl) {
        try {
            Path savePath = Path.of(System.getProperty("user.home"), "wallpaper.jpg");

            // img download
            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // apple script version 1
//            String script = "tell application \"Finder\" to set desktop picture to POSIX file \""
//                    + savePath.toAbsolutePath().toString()
//                    + "\"";

            // apple script version 2
            String script =
                    "tell application \"System Events\"\n" +
                            "set picture of every desktop to POSIX file \"" +
                            savePath.toAbsolutePath().toString() +
                            "\"\n" +
                            "end tell";
            ProcessBuilder pb = new ProcessBuilder("osascript", "-e", script);
            Process process = pb.start();
            process.waitFor();

            System.out.println("🎉 macOS 배경화면 변경 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}