package server.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageRepository {
    private final String pathBase = Paths.get("resources", "images").toString();

    public ImageRepository() {
    }

    /**
     * addImage stores an image on disk.
     *
     * @param folder  the folder where the image should be stored.
     * @param file    the filename of the image.
     * @param content the image itself.
     * @throws IOException gets thrown iff the image can not be persisted on disk.
     */
    public void addImage(String folder, String file, byte[] content) throws IOException {
        File directory = Paths.get(pathBase, folder).toFile();
        directory.mkdirs();

        File imageFile = Paths.get(pathBase, folder, file).toFile();
        imageFile.createNewFile();

        try (FileOutputStream image = new FileOutputStream(imageFile)) {
            image.write(content);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * removeImage removes a stored image.
     *
     * @param folder the folder of the image to remove.
     * @param file   the image to remove.
     * @return true iff the image was successfully removed or does not exist.
     */
    public boolean removeImage(String folder, String file) {
        File directory = Paths.get(pathBase, folder).toFile();
        if (!directory.exists()) {
            return true;
        }

        File imageFile = Paths.get(pathBase, folder, file).toFile();
        if (!imageFile.exists()) {
            return true;
        }

        return imageFile.delete();
    }
}
