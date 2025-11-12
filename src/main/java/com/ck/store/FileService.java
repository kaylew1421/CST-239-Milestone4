package com.ck.store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileService {

    public String readFile(Path path) throws FileServiceException {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            throw new FileServiceException("Failed to read file: " + path, ioe);
        }
    }

    public void writeFile(Path path, String contents) throws FileServiceException {
        try {
            if (path.getParent() != null) Files.createDirectories(path.getParent());
            Files.writeString(path, contents, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            throw new FileServiceException("Failed to write file: " + path, ioe);
        }
    }
}
