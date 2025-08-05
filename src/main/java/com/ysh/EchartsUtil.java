package com.ysh;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.geometry.size.FloatSize;
import com.github.weisj.jsvg.parser.SVGLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * EchartsUtil 工具类
 * 
 * @author Ysh
 * @since 2025-08-05
 * @version 1.0
 */
public class EchartsUtil {


    /**
     * 生成图片byte数组
     *
     * @param option echarts配置option
     * @return
     */
    public static byte[] getImageByte(String option) {
        return getImageByte(option, 1280, 720, Echarts.ImageType.PNG);
    }

    /**
     * 生成图片byte数组
     *
     * @param option echarts配置option
     * @param width  图片宽度
     * @param height 图片高度
     * @return
     */
    public static byte[] getImageByte(String option, float width, float height) {
        return getImageByte(option, width, height, Echarts.ImageType.PNG);
    }

    /**
     * 生成图片byte数组
     *
     * @param option echarts配置option
     * @param width  图片宽度
     * @param height 图片高度
     * @param type   图片类型
     * @return
     */
    public static byte[] getImageByte(String option, float width, float height, Echarts.ImageType type) {
        byte[] bytes = Echarts.render((int) width, (int) height, Echarts.ImageType.SVG.getType(), option);
        if (Echarts.ImageType.SVG.getType().equals(type.getType())) {
            return bytes;
        } else {
            byte[] outs = new byte[0];
            try (InputStream is = new ByteArrayInputStream(bytes); ByteArrayOutputStream bos = new ByteArrayOutputStream(8192)) {
                SVGDocument document = new SVGLoader().load(is);
                // 获取SVG尺寸
                FloatSize size = document.size();
                // 创建BufferedImage
                BufferedImage image = new BufferedImage(
                        (int) size.width,
                        (int) size.height,
                        Echarts.ImageType.PNG.getType().equals(type.getType()) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g = image.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                // Set transparent background for PNG or white background for JPEG
                if (Echarts.ImageType.PNG.getType().equals(type.getType())) {
                    g.setBackground(new Color(0, 0, 0, 0));
                    g.setColor(new Color(0, 0, 0, 0));
                    g.clearRect(0, 0, image.getWidth(), image.getHeight());
                } else {
                    g.setBackground(Color.WHITE);
                    g.fillRect(0, 0, image.getWidth(), image.getHeight());
                }
                // 渲染SVG到BufferedImage
                document.render(null, g);
                g.dispose();
                // 保存为PNG文件
                ImageIO.write(image, type.getType(), bos);
                outs = bos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return outs;
        }
    }
}
