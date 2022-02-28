package server.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageRepository {
    private final String pathBase = "images";

    public ImageRepository() {}

    public void addImage(String folder, String file, byte[] content) throws IOException {
        File directory = Paths.get(pathBase, folder).toFile();
        directory.mkdirs();

        File imageFile = Paths.get(pathBase, folder, file).toFile();
        imageFile.createNewFile();

        System.out.println(imageFile.getAbsolutePath());


        try (FileOutputStream image = new FileOutputStream(imageFile)) {
            image.write(content);
        } catch (IOException e) {
            throw e;
        }
    }
}
