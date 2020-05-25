package main.domain.captchacode.usecase;

import main.domain.captchacode.entity.CaptchaCode;
import main.domain.captchacode.model.CaptchaCodeResponseDTO;
import main.domain.captchacode.port.CaptchaCodeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Random;

@Component
public class CaptchaUseCase {
    @Autowired
    CaptchaCodeRepositoryPort captchaCodeRepositoryPort;

    public CaptchaCodeResponseDTO generateCaptcha() {
        CaptchaCode captcha = new CaptchaCode();

        //генерация code
        String code = generateCode(5);
        //генерация secret_code
        String secretCode;
        do {
            secretCode = generateCode(12);
        } while (captchaCodeRepositoryPort.findBySecretCode(secretCode).isPresent());

        Timestamp time = new Timestamp(System.currentTimeMillis());

        //сохранение captcha в бд
        captcha.setCode(code);
        captcha.setSecretCode(secretCode);
        captcha.setTime(time);

        BufferedImage captchaImg = createCaptchaImage(code);
        String encodedImg = "data:image/png;base64, " + encodeImage(captchaImg);
        captchaCodeRepositoryPort.save(captcha);

        //TODO: delete old captcha
        System.out.println(encodedImg);
        deleteOldCaptcha();
        return new CaptchaCodeResponseDTO(secretCode, encodedImg);
    }

    private void deleteOldCaptcha() {
        /**
         * TODO: удаление капч
         */
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

    private BufferedImage createCaptchaImage(String code) {
        final int SYMBOLS_COUNT = code.length();
        final int FONT_SIZE = 20;
        final int IMAGE_WIDTH = 100;
        final int IMAGE_HEIGHT = 30;
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.ITALIC, FONT_SIZE));
        for (int i = 0; i < code.length(); i++) {
            g.drawString(
                    String.valueOf(code.charAt(i)),
                    2 + i * IMAGE_WIDTH/SYMBOLS_COUNT,
                    IMAGE_HEIGHT - (IMAGE_HEIGHT-FONT_SIZE)/2
            );
        }
        return image;
    }

    private static String encodeImage(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String encodedImage = null;
        try {
            ImageIO.write(image, "png", os);
            byte[] byteArray = os.toByteArray();
            encodedImage = Base64.getEncoder().encodeToString(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedImage;
    }

}
