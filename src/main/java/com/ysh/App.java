package com.ysh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Echarts4Java Demo Application
 * 
 * This class demonstrates how to use the Echarts4Java library to generate charts
 * in different formats (PNG, SVG, JPEG).
 * 
 * @author Ysh
 * @version 1.0
 */
public class App {
    public static void main(String[] args) throws IOException {
        // Sample Echarts option JSON (Nightingale Chart)
        String json = "{\n" +
                "  legend: {\n" +
                "    top: 'bottom'\n" +
                "  },\n" +
                "  toolbox: {\n" +
                "    show: true,\n" +
                "    feature: {\n" +
                "      mark: { show: true },\n" +
                "      dataView: { show: true, readOnly: false },\n" +
                "      restore: { show: true },\n" +
                "      saveAsImage: { show: true }\n" +
                "    }\n" +
                "  },\n" +
                "  series: [\n" +
                "    {\n" +
                "      name: 'Nightingale Chart',\n" +
                "      type: 'pie',\n" +
                "      radius: [50, 250],\n" +
                "      center: ['50%', '50%'],\n" +
                "      roseType: 'area',\n" +
                "      itemStyle: {\n" +
                "        borderRadius: 8\n" +
                "      },\n" +
                "      data: [\n" +
                "        { value: 40, name: 'rose 1' },\n" +
                "        { value: 38, name: 'rose 2' },\n" +
                "        { value: 32, name: 'rose 3' },\n" +
                "        { value: 30, name: 'rose 4' },\n" +
                "        { value: 28, name: 'rose 5' },\n" +
                "        { value: 26, name: 'rose 6' },\n" +
                "        { value: 22, name: 'rose 7' },\n" +
                "        { value: 18, name: 'rose 8' }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Example 1: Generate PNG chart using direct API
        System.out.println("Generating PNG chart...");
        String pngPath = "charts.png";
        Echarts.save(1000, 1000, pngPath, json);
        System.out.println("PNG chart saved to: " + Paths.get(pngPath).toAbsolutePath());

        // Example 2: Generate SVG chart using direct API
        System.out.println("Generating SVG chart...");
        byte[] svgBytes = Echarts.render(1000, 1000, "svg", json);
        Path svgPath = Paths.get("charts.svg");
        Files.write(svgPath, svgBytes);
        System.out.println("SVG chart saved to: " + svgPath.toAbsolutePath());

        // Example 3: Generate PNG chart using utility method
        System.out.println("Generating PNG chart using utility...");
        byte[] pngBytes = EchartsUtil.getImageByte(json, 1000, 1000, Echarts.ImageType.PNG);
        Path pngUtilPath = Paths.get("charts_util.png");
        Files.write(pngUtilPath, pngBytes);
        System.out.println("PNG chart (via utility) saved to: " + pngUtilPath.toAbsolutePath());

        // Example 4: Generate JPEG chart using utility method
        System.out.println("Generating JPEG chart using utility...");
        byte[] jpegBytes = EchartsUtil.getImageByte(json, 1000, 1000, Echarts.ImageType.JPEG);
        Path jpegPath = Paths.get("charts.jpeg");
        Files.write(jpegPath, jpegBytes);
        System.out.println("JPEG chart saved to: " + jpegPath.toAbsolutePath());

        // Example 5: Generate chart with default settings (PNG, 1280x720)
        System.out.println("Generating chart with default settings...");
        byte[] defaultBytes = EchartsUtil.getImageByte(json);
        Path defaultPath = Paths.get("charts_default.png");
        Files.write(defaultPath, defaultBytes);
        System.out.println("Default chart saved to: " + defaultPath.toAbsolutePath());
        
        System.out.println("All charts generated successfully!");
    }
}
