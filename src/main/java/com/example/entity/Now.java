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
  @TableName("sys_now")
@ApiModel(value = "Now对象", description = "")
public class Now implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String buySell;

    private String area;

    private String kind;

    private String method;

    private BigDecimal price;

    private Integer count;

    private String period;

    private BigDecimal bestBid;

    private LocalDateTime createTime;


}
