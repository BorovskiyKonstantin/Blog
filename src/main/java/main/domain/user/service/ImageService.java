package main.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

@Service
public class ImageService {

    @Value("${upload.dir}")
    private String uploadDirPath;

    //Сохранение изображения
    public String uploadImage(MultipartFile image) {
        //Имя файла
        String fileName = image.getOriginalFilename();

        //Расширение файла
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        //Случайный кэш
        String hashCode = generateCode(15);

        //Новое имя файла
        fileName = hashCode + fileExtension;

        //Сохранение в папку upload
        String uploadPath = uploadDirPath + fileName;
        writeImage(image, new File(uploadPath));

        //Сохранения изображения в 3 подпапки
        File[] copyFiles = new File[]{
                new File(uploadDirPath + hashCode.substring(0, 5) + "/" + fileName),
                new File(uploadDirPath + hashCode.substring(5, 10) + "/" + fileName),
                new File(uploadDirPath + hashCode.substring(10, 15) + "/" + fileName)
        };

        for (File copyFile : copyFiles){
                copyFile.getParentFile().mkdirs();
                writeImage(image, copyFile);
        }

        return uploadPath;
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

    private void writeImage(MultipartFile image, File dstFile){
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
