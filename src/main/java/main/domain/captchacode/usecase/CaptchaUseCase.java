package main.domain.captchacode.usecase;

import main.domain.captchacode.entity.CaptchaCode;
import main.domain.captchacode.model.CaptchaCodeResponseDTO;
import main.domain.captchacode.port.CaptchaCodeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Service
@Transactional
public class CaptchaUseCase {
    private CaptchaCodeRepositoryPort captchaCodeRepositoryPort;
    @Value("${captcha.duration}")
    private Duration captchaDuration;

    @Autowired
    public CaptchaUseCase(CaptchaCodeRepositoryPort captchaCodeRepositoryPort) {
        this.captchaCodeRepositoryPort = captchaCodeRepositoryPort;
    }

    public CaptchaCodeResponseDTO generateCaptcha() {
        //Удаление старых капч из БД
        deleteOldCaptcha();

        //генерация кода картинки капчи - code
        String code = generateCode(5);
        //генерация secret_code
        String secretCode;
        do {
            secretCode = generateCode(12);
        } while (captchaCodeRepositoryPort.findBySecretCode(secretCode).isPresent());

        //сохранение captcha в бд
        CaptchaCode captcha = new CaptchaCode();
        captcha.setCode(code);
        captcha.setSecretCode(secretCode);
        captcha.setTime(new Timestamp(System.currentTimeMillis()));
        captchaCodeRepositoryPort.save(captcha);

        //Создание картинки капчи и кодирование в Base64
        BufferedImage captchaImg = createCaptchaImage(code);
        String encodedImg = "data:image/png;base64, " + encodeImage(captchaImg);

        return new CaptchaCodeResponseDTO(secretCode, encodedImg);
    }

    private void deleteOldCaptcha() {
        //Время просроченных капч на данный момент
        Timestamp expiredTime = new Timestamp(System.currentTimeMillis() - captchaDuration.toMillis());
        captchaCodeRepositoryPort.deleteOld(expiredTime);
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
                    2 + i * IMAGE_WIDTH / SYMBOLS_COUNT,
                    IMAGE_HEIGHT - (IMAGE_HEIGHT - FONT_SIZE) / 2
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
