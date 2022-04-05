package server.api;

import commons.ImageUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ImageRepository;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageRepository imageRepo;

    public ImageController(ImageRepository imageRepo) {
        this.imageRepo = imageRepo;
    }

    @PutMapping("{group}/{image}")
    public ResponseEntity addImage(@PathVariable("group") String group, @PathVariable("image") String image, @RequestBody ImageUpload data) {
        try {
            imageRepo.addImage(group, image, data.getData());
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
