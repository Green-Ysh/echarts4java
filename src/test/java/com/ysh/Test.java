package com.ysh;

import com.ysh.Echarts;
import com.ysh.EchartsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author: Ysh
 * @CreateTime: 2025-08-05 08:57
 * @Description:
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) throws IOException {
        InputStream is = Test.class.getResourceAsStream("/option.txt");
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from InputStream", e);
        }
        System.out.println(content);

        byte[] bytes = EchartsUtil.getImageByte(content.toString(), 1280, 720, Echarts.ImageType.PNG);
        Files.write(Paths.get("test.png"), bytes);
    }
}
