## java后端生成echarts

<font color=red>该项目在[**echarts4java**](https://github.com/bajie-git/echarts4java)项目基础上进行二次开发，并将jar包上传到maven中央仓库</font>

#### 一、介绍

该项目可以根据Echarts图表的option配置项内容生成Echarts图表

#### 二、注意事项（V2.0.0）
* 项目依赖于jsvg:1.7.0，将svg转成png/jpeg
* 在linux环境运行时，系统libc库版本必须大于等于2.17，可使用`ldd --version`查看
* 与v1版本相比，v2版本项目包名发生变化，使用时需要注意

#### 三、使用方式

* 进入[mavne中央仓库](https://central.sonatype.com/)

* 在`pom.xml`中添加以下坐标

```xml
<dependency>
    <groupId>io.github.green-ysh</groupId>
    <artifactId>echarts4java</artifactId>
    <version>2.0.0</version>
</dependency>
```

* 编写如下代码，生成Echarts图表png图表

```java
// 读取echarts下option配置内容的txt文文本
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
// 生成echarts图表的png byte数组
byte[] bytes = EchartsUtil.getImageByte(content.toString(), 1280, 720, Echarts.ImageType.PNG);
// 生成png图片
Files.write(Paths.get("test.png"), bytes);
```

