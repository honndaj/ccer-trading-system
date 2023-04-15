package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@TableName(value="user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String role;
    @TableField("company_name")
    private String companyName;
    private String address;
    @TableField("legal_represent")
    private String legalRepresent;
    @TableField("company_code")
    private String companyCode;
    @TableField("trade_represent")
    private String tradeRepresent;
    private String phone;
    private String email;
    @TableField("express_code")
    private String expressCode;
    private String tel;
    private String fax;

}
