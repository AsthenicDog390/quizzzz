package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

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
        MultipartFile mockFile = new MockMultipartFile(name, content);

        sut.addImage("group", "image", mockFile);

        assertTrue(repo.files.containsKey("group"));
        assertTrue(repo.files.get("group").containsKey("image"));
        assertArrayEquals(content, repo.files.get("group").get("image"));
    }

}