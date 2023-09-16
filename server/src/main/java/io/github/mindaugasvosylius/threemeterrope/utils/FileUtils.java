package io.github.mindaugasvosylius.threemeterrope.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            final Path blackCardsPath = Path.of(FileUtils.class.getResource("/" + filePath).toURI());
            try (BufferedReader br = new BufferedReader(new FileReader(blackCardsPath.toFile()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public static String readLine() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String readLine(String message) {
        Messenger.send(message);
        return readLine();
    }
}
