package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.database.ImageRepository;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private ImageRepository imageRepo;

    public ImageController(ImageRepository imageRepo) {
        this.imageRepo = imageRepo;
    }

    @PutMapping("{group}/{image}")
    public ResponseEntity addImage(@PathVariable("group") String group, @PathVariable("image") String image, @RequestParam("image") MultipartFile file) {
        try {
            imageRepo.addImage(group, image, file.getBytes());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{group}/{image}")
    public ResponseEntity removeImage(@PathVariable("group") String group, @PathVariable("image") String image) {
        if (!imageRepo.removeImage(group, image)) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }
}
