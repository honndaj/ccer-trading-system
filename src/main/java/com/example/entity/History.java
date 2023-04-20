package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
  @TableName("sys_history")
@ApiModel(value = "History对象", description = "")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer from;

    private Integer to;

    private String area;

    private String kind;

    private BigDecimal price;

    private Integer count;

    private LocalDateTime createTime;


}
