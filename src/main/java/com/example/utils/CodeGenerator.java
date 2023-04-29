package com.example.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/carbon", "root", "1234")
                .globalConfig(builder -> {
                    builder.author("lzq")
                            .fileOverride()
                            .outputDir("D:\\Code\\workplace\\ccer-trading-system\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.example")
                            .moduleName(null)
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\Code\\workplace\\ccer-trading-system\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableHyphenStyle()  // 驼峰 -
                            .enableRestStyle();
                    builder.addInclude("sys_now")
                            .addTablePrefix("sys_");
                })
                .execute();

    }
}
