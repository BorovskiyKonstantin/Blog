package main.web.api;

import main.domain.user.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadController {
    @Autowired
    ImageService imageService;

    //    Получение изображения  -   GET   /upload/ и /post/upload/
    @GetMapping(value = {
            "/upload/{firstDir}/{secondDir}/{thirdDir}/{fileName:.+}",
            "/post/upload/{firstDir}/{secondDir}/{thirdDir}/{fileName:.+}",
            "/posts/upload/{firstDir}/{secondDir}/{thirdDir}/{fileName:.+}"
    })
    public ResponseEntity<byte[]> getImage(@PathVariable("firstDir") String firstDir,
                                           @PathVariable("secondDir") String secondDir,
                                           @PathVariable("thirdDir") String thirdDir,
                                           @PathVariable("fileName") String fileName) {
        String path = "/" + firstDir + "/" + secondDir + "/" + thirdDir + "/" + fileName;
        byte[] image = imageService.getImage(path);
        if (image.length == 0) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
