package commons;

public class ImageUpload {
    private byte[] data;

    @SuppressWarnings("unused")
    private ImageUpload() {
        // for object mapper
    }

    public ImageUpload(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }
}
