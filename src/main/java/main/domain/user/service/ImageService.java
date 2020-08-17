package main.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ImageService {

    @Value("${upload.dir}")
    private String uploadRootDirPath;
    @Value("${upload.image-max-size}")
    private Integer IMAGE_MAX_SIZE;

    //Сохранение изображения
    public ResponseEntity<Object> uploadImage(MultipartFile image) {

        String fileName = image.getOriginalFilename();  //Имя файла
        String fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1);   //Расширение файла без точки
        String hashCode = generateCode(15);     //Случайный хэш
        double sizeMB = (double) image.getSize() / 1024 / 1024;  //Размер файла в MB

        //Поиск ошибок в запросе
        Map<String, Object> errors = new LinkedHashMap<>();     //Лог ошибок
        //Проверка размера файла
        if (sizeMB > IMAGE_MAX_SIZE)
            errors.put("image", "Размер файла превышает допустимый размер");

        //Проверка расширения файла
        if (!fileFormat.equals("jpg") && !fileFormat.equals("png"))
            errors.put("extension", "Формат изображения должен быть .jpg или .png");

        //Возврат ответа 400, если обнаружены ошибки
        if (errors.size() > 0) {
            Map<String, Object> errorDTO = new LinkedHashMap<>();
            errorDTO.put("result", false);
            errorDTO.put("errors", errors);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        //Сохранение в папку upload
        String subDirsPath = //Путь 3 подпапок
                hashCode.substring(0, 5) + "/" + hashCode.substring(5, 10) + "/" + hashCode.substring(10, 15) + "/";
        String uploadFilePath = uploadRootDirPath + subDirsPath + hashCode + "." + fileFormat;
        File uploadFile = new File(uploadFilePath);
        uploadFile.getParentFile().mkdirs();  //создание подпапок для файла
        try {
            //чтение и запись в файл
            ImageIO.write(ImageIO.read(image.getInputStream()), fileFormat, uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(subDirsPath + hashCode + "." + fileFormat, HttpStatus.OK);
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

    //обрезка и сохранение фото профиля
    public String uploadProfilePhoto(MultipartFile image)  {
        String fileName = image.getOriginalFilename();  //Имя файла
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);   //Расширение файла без точки
        String hashCode = generateCode(15);     //Случайный хэш
        String subDirsPath = //Путь 3 подпапок xx/yy/zz/
                hashCode.substring(0, 5) + "/" + hashCode.substring(5, 10) + "/" + hashCode.substring(10, 15) + "/";

        try {
            //прочитать изображение и обрезать до 36x36 пикселей
            BufferedImage img = ImageIO.read(image.getInputStream());
            img = img.getSubimage(0, 0, 36, 36);

            //запись в файл
            File uploadFile = new File(uploadRootDirPath + subDirsPath + hashCode + "." + fileExtension);
            uploadFile.getParentFile().mkdirs();  //создание подпапок для файла
            ImageIO.write(img, fileExtension, uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subDirsPath + hashCode + "." + fileExtension;
    }

    public void deleteImage(String filePath) {
        String photoPath = uploadRootDirPath + filePath;
        clean(new File(photoPath), uploadRootDirPath);
    }
    private static void clean(File file, String rootDir){
        if (file.isDirectory())
        if (file.listFiles().length != 0) return;
        if (file.getName().equals(rootDir)) return;

        file.delete();
        File parentFile = file.getParentFile();
        clean(parentFile, rootDir);
    }

}
