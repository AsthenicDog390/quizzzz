package server.api;

import server.database.ImageRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestImageRepository extends ImageRepository {
    public Map<String, Map<String, byte[]>> files;
    public boolean shouldThrow;

    public TestImageRepository() {
        files = new HashMap<>();
    }

    public void addImage(String folder, String file, byte[] content) throws IOException {
        if (shouldThrow) {
            throw new IOException();
        }

        if (!files.containsKey(folder)) {
            files.put(folder, new HashMap<>());
        }

        files.get(folder).put(file, content);
    }
}
