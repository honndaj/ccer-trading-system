package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
@Getter
@Setter
  @TableName("sys_ccer")
@ApiModel(value = "Ccer对象", description = "")
public class Ccer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private String area;

    private String kind;

    private BigDecimal count;


}
