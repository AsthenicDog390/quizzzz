package server.api;

import commons.ImageUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ImageControllerTest {
    private TestImageRepository repo;

    private ImageController sut;

    @BeforeEach
    public void setup() {
        repo = new TestImageRepository();
        sut = new ImageController(repo);
    }

    @Test
    public void addImage() {
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(content);

        var res = sut.addImage("group", "image", img);

        assertEquals(HttpStatus.OK, res.getStatusCode());

        assertTrue(repo.files.containsKey("group"));
        assertTrue(repo.files.get("group").containsKey("image"));
        assertArrayEquals(content, repo.files.get("group").get("image"));
    }

    @Test
    public void addImageError() {
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(content);

        repo.shouldThrow = true;

        var res = sut.addImage("group", "image", img);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

    @Test
    public void removeImage() {
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(content);

        sut.addImage("group", "image", img);

        var res = sut.removeImage("group", "image");

        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void removeImageError() {
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(content);

        repo.shouldFail = true;

        sut.addImage("group", "image", img);

        var res = sut.removeImage("group", "image");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

    @Test
    public void removeImageNotExists() {
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(content);

        sut.addImage("group", "image", img);

        var res = sut.removeImage("group", "image");

        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}