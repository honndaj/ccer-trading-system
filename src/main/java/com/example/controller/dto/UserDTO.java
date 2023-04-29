package com.example.controller.dto;

import com.example.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String token;
    private String uniqueKey;
    private List<Menu> menus;
}