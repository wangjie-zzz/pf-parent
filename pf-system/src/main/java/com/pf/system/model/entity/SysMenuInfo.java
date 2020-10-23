package com.pf.system.model.entity;

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
@ApiModel(value="SysMenuInfo对象", description="")
public class SysMenuInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String menuId;

    private String menuName;

    private String appId;

    private String menuUrl;

    private String menuIcon;

    private String menuBgImg;

    private String menuBgColor;

    private String menuSupMenuId;

    private String menuLevel;

    private Boolean menuIsLeaf;

    private Integer menuRowSpan;

    private Integer menuColSpan;

    private Integer menuSortNo;

    private String menuUserState;

    private String menuIntUser;

    private LocalDateTime menuIntDate;

    private String menuUpdUser;

    private LocalDateTime menuUpdDate;


}
