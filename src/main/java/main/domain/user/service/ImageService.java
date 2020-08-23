package main.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ImageService {
    @Value("${upload.dir}")
    private String UPLOAD_DIR_FULL_PATH;
    private String UPLOAD_DIR_ROOT;
    private String UPLOAD_DIR_NAME;

    @Value("${upload.image-max-size}")
    private Integer IMAGE_MAX_SIZE;

    @PostConstruct
    private void postConstruct() {
        this.UPLOAD_DIR_ROOT = UPLOAD_DIR_FULL_PATH.replaceAll("[^/]+/$", "");
        this.UPLOAD_DIR_NAME = UPLOAD_DIR_FULL_PATH.replaceAll("^" + UPLOAD_DIR_ROOT, "");
    }

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
        File uploadFile = new File(UPLOAD_DIR_FULL_PATH + subDirsPath + hashCode + "." + fileFormat);
        uploadFile.getParentFile().mkdirs();  //создание подпапок для файла
        try {
            //чтение и запись в файл
            ImageIO.write(ImageIO.read(image.getInputStream()), fileFormat, uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(UPLOAD_DIR_NAME + subDirsPath + hashCode + "." + fileFormat, HttpStatus.OK);
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
    public String uploadProfilePhoto(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();  //Имя файла
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);   //Расширение файла без точки
        String hashCode = generateCode(15);     //Случайный хэш
        String subDirsPath = //Путь 3 подпапок xx/yy/zz/
                hashCode.substring(0, 5) + "/" + hashCode.substring(5, 10) + "/" + hashCode.substring(10, 15) + "/";
        try {
            //прочитать изображение и обрезать до 36x36 пикселей
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            //сжать изображение до 36х36 пикселей
            Image img = image.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(36, 36, BufferedImage.TYPE_INT_RGB);
            resizedImage.getGraphics().drawImage(img, 0, 0, null);

            //запись в файл
            File uploadFile = new File(UPLOAD_DIR_FULL_PATH + subDirsPath + hashCode + "." + fileExtension);
            uploadFile.getParentFile().mkdirs();  //создание подпапок для файла
            ImageIO.write(resizedImage, fileExtension, uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return UPLOAD_DIR_NAME + subDirsPath + hashCode + "." + fileExtension;
    }

    public void deleteImage(String filePath) {
        String photoPath = UPLOAD_DIR_ROOT + filePath;
        clean(new File(photoPath), UPLOAD_DIR_FULL_PATH);
    }

    private static void clean(File file, String rootDir) {
        if (file.isDirectory())
            if (file.listFiles().length != 0) return;
        if (file.getName().equals(rootDir)) return;

        file.delete();
        File parentFile = file.getParentFile();
        clean(parentFile, rootDir);
    }

    public byte[] getImage(String filePath) {
        File file = new File(UPLOAD_DIR_FULL_PATH + filePath);
        byte[] image = new byte[0];
        if (file.exists()) {
            try (FileInputStream is = new FileInputStream(file)) {
                image = is.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
                image = new byte[0];
            }
        }
        return image;
    }

}
