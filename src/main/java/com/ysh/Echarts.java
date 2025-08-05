package com.ysh;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Echarts class for generating charts
 * 
 * @author Ysh
 * @since 2025-08-05
 * @version 1.0
 */

public class Echarts {
    static {
        String fileName;
        Path path;
        
        // Get OS and architecture information
        String osArch = System.getProperty("os.arch");
        boolean isX86 = (!osArch.contains("aarch64") && !osArch.contains("arm"));
        
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows") && isX86) {
            // Windows x86 system
            fileName = "win.dll";
        } else if (os.startsWith("Linux") && isX86) {
            // Linux x86 system
            fileName = "linux.so";
        } else if (os.startsWith("Mac") && !isX86) {
            // Mac ARM system (M1/M2)
            fileName = "mac_arm.dylib";
        } else if (os.startsWith("Mac") && isX86) {
            // Mac Intel system
            fileName = "mac_x86.dylib";
        } else {
            // Unsupported system or architecture
            throw new RuntimeException("Unsupported Operating System (" + os + ") or CPU architecture (" + osArch + ")");
        }
        
        try {
            // Create a temporary file that will be deleted on JVM exit
            path = Files.createTempFile("echarts4java", ".tmp");
            try (InputStream resourceAsStream = Echarts.class.getResourceAsStream("/lib/" + fileName);
                 OutputStream outputStream = Files.newOutputStream(path)) {
                Objects.requireNonNull(resourceAsStream, "Unable to load dynamic link library: " + fileName);
                copyStream(resourceAsStream, outputStream);
            }
            // Mark the file for deletion when the JVM exits
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract native library: " + e.getMessage(), e);
        }
        
        // Load the library from the temporary file
        System.load(path.toAbsolutePath().toString());
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        // 定义缓冲区大小
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            // 将读取的字节写入 OutputStream
            out.write(buffer, 0, bytesRead);
        }
    }

    /**
     * 渲染图表到指定路径
     *
     * @param width  图表宽度 px
     * @param height 图表高度 px
     * @param path   文件生成路径，根据文件扩展名生成对应格式
     * @param option 图表渲染使用的json数据，参考echarts官网
     */
    public static native void save(int width, int height, String path, String option);

    /**
     * 渲染图表到指定路径
     *
     * @param width  图表宽度 px
     * @param height 图表高度 px
     * @param type   图片格式
     * @param option 图表渲染使用的json数据，参考echarts官网
     * @return
     */
    public static native byte[] render(int width, int height, String type, String option);

    public enum ImageType {
        SVG("svg"),
        PNG("png"),
        JPEG("jpeg");

        private String type;

        ImageType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

    }
}
