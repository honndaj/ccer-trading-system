package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_icon_map")
@Data
public class IconMap {
    private String name;
    private String value;
}
