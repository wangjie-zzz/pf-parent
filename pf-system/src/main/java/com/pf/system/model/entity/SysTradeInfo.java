package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysTradeInfo对象", description="")
public class SysTradeInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String menuId;

    @TableId
    private String tradeId;

    private String appId;

    private String tradeUrl;

    private String tradeName;

    private Integer tradeSortNo;

    private String tradeUseState;

    private String tradeIntUser;

    private LocalDateTime tradeIntDate;

    private String tradeUpdUser;

    private LocalDateTime tradeUpdDate;


}
