package com.ck.backend;

import com.ck.backend.util.ImageCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

    @Autowired
    public PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("1234"));
        System.out.println("passwordEncoder.matches(\"1234\",\"$2a$10$VsD0bPZpO8jyyDu.PPRSt.f8NQJb7XknJcDb8WdFCuuODZqhgOREi\") = " + passwordEncoder.matches("12342", "$2a$10$VsD0bPZpO8jyyDu.PPRSt.f8NQJb7XknJcDb8WdFCuuODZqhgOREi"));
    }
    @Test
    void base64Test() {
        String s;
        s = Base64.getEncoder().encodeToString("1234".getBytes(StandardCharsets.UTF_8));
        System.out.println("s = " + s);
        System.out.println(new String(Base64.getDecoder().decode("eyJzdWIiOiJ7XCJ1c2VySWRcIjoxLFwidXNlckVtYWlsXCI6XCIxQHFxLmNvbVwiLFwidXNlcm5hbWVcIjpcInJvb3RcIixcInBhc3N3b3JkXCI6bnVsbCxcImdyYWRlXCI6MCxcInVzZXJJbWdcIjpcIm5vXCIsXCJkZWZhdWx0QWRkcmVzc1wiOlwiYWRkcmVzc1wiLFwiaWRlbnRpdHlcIjowfSIsImlhdCI6MTcwMzAzODY5M30".getBytes())));
    }
    @Test
    public void t1() throws IOException {
        ImageCode imageCode = createImageCode();

//        写到控制台乱码
//        ImageIO.write(imageCode.getImage(), "jpeg", System.out);

        ImageIO.write(imageCode.getImage(), "jpeg", Files.newOutputStream(new File("d:/ee.jpeg").toPath()));
    }

    private ImageCode createImageCode() {

        int width = 120; // 验证码图片宽度
        int height = 45; // 验证码图片长度
        int length = 4; // 验证码位数
        int expireIn = 60; // 验证码有效时间 60s

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(185, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 35));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 25 * i + 12, 32);
        }

        g.dispose();

        return new ImageCode(image, sRand.toString(), expireIn);
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;

        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }


}
