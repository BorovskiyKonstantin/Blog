package main.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ImageService {

    @Value("${upload.dir}")
    private String uploadDirPath;
    @Value("${upload.image-max-size}")
    private Integer IMAGE_MAX_SIZE;

    //Сохранение изображения
    public ResponseEntity<Object> uploadImage(MultipartFile image) {

        String fileName = image.getOriginalFilename();  //Имя файла
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));   //Расширение файла
        String hashCode = generateCode(15);     //Случайный кэш
        double sizeMB = (double) image.getSize() / 1024 / 1024;  //Размер файла в MB

        //Поиск ошибок в запросе
        Map<String, Object> errors = new LinkedHashMap<>();     //Лог ошибок
        //Проверка размера файла
        if (sizeMB > IMAGE_MAX_SIZE)
            errors.put("image", "Размер файла превышает допустимый размер");

        //Проверка расширения файла
        if (!fileExtension.equals(".jpg") && !fileExtension.equals(".png"))
            errors.put("extension", "Формат изображения должен быть .jpg или .png");

        //Возврат ответа 400, если обнаружены ошибки
        if (errors.size() > 0) {
            Map<String, Object> errorDTO = new LinkedHashMap<>();
            errorDTO.put("result", false);
            errorDTO.put("errors", errors);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        //Сохранение в папку upload
        fileName = hashCode + fileExtension;    //Новое имя файла
        String subDirsPath = //Путь 3 подпапок
                hashCode.substring(0, 5) + "/"
                        + hashCode.substring(5, 10) + "/"
                        + hashCode.substring(10, 15) + "/";
        String uploadFilePath = uploadDirPath + subDirsPath + fileName;
        File uploadFile = new File(uploadFilePath);
        uploadFile.getParentFile().mkdirs();  //создание подпапок для файла
        writeImage(image, uploadFile);

        return new ResponseEntity<>(subDirsPath + fileName, HttpStatus.OK);
    }

    private String generateCode(int length) {
        final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int symbolNumb = random.nextInt(symbols.length());
            char symbol = symbols.charAt(symbolNumb);
            builder.append(symbol);
        }
        return builder.toString();
    }

    private void writeImage(MultipartFile image, File dstFile) {
        try {
            byte[] bytes = image.getBytes();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(dstFile));
            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
