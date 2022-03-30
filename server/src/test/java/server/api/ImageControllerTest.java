package server.api;

import commons.ImageUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
        String name = "test";
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(name.getBytes());

        var res = sut.addImage("group", "image", img);

        assertEquals(HttpStatus.OK, res.getStatusCode());

        assertTrue(repo.files.containsKey("group"));
        assertTrue(repo.files.get("group").containsKey("image"));
        assertArrayEquals(content, repo.files.get("group").get("image"));
    }

    @Test
    public void addImageError() {
        String name = "test";
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(name.getBytes());

        repo.shouldThrow = true;

        var res = sut.addImage("group", "image", img);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

    @Test
    public void removeImage() {
        String name = "test";
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(name.getBytes());

        sut.addImage("group", "image", img);

        var res = sut.removeImage("group", "image");

        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void removeImageError() {
        String name = "test";
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(name.getBytes());

        repo.shouldFail = true;

        sut.addImage("group", "image", img);

        var res = sut.removeImage("group", "image");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

    @Test
    public void removeImageNotExists() {
        String name = "test";
        byte[] content = {1, 2, 3};
        var img = new ImageUpload(name.getBytes());

        sut.addImage("group", "image", img);

        var res = sut.removeImage("group", "image");

        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}