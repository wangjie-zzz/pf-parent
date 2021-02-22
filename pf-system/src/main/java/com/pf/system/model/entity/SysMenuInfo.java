package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @TableId
    private String menuId;

    private String menuName;

    private String menuAppId;

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

    @TableField(exist = false)
    private List<SysMenuInfo> children;

    public void addChildren(List<SysMenuInfo> roots) {
        for (SysMenuInfo root : roots) {
            if(root.getMenuId().equals(this.getMenuSupMenuId())) {
                if(root.getChildren() == null)
                    root.setChildren(new ArrayList<>());
                root.getChildren().add(this);
                return;
            } else if(root.getChildren() != null && root.getChildren().size() > 0) {
                this.addChildren(root.getChildren());
            }
        }
    }
}
