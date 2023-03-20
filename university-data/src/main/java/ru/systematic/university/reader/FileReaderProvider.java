package ru.systematic.university.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FileReaderProvider {
    private static final String DELIMITER = "\n";
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderProvider.class);

    public String read(String fileName) {
        try {
            InputStream fileStream = this.getClass().getResourceAsStream(fileName);
            LOGGER.trace("File read: " + fileName);
            return new BufferedReader(new InputStreamReader(fileStream))
                    .lines()
                    .collect(Collectors.joining(DELIMITER));
        } catch (Exception e) {
            LOGGER.error("File not found: " + fileName);
            throw new IllegalArgumentException("File not found");
        }
    }
}
