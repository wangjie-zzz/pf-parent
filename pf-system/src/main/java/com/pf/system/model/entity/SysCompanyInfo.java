package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

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
@ApiModel(value="SysCompanyInfo对象", description="")
public class SysCompanyInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String comId;

    private Integer comLevel;

    private String comName;

    private String comTenId;

    private String comSupComId;

    private String comTelMan;

    private String comOrgPhone;

    private String comProviceNo;

    private String comCityNo;

    private String comCutNo;

    private String comAddress;

    private String comDiIngo;

    private String comOngInfo;

    private Integer comSortNo;

    private String comUserState;

    private String comIntUser;

    private LocalDateTime comIntDate;

    private String comUpdUser;

    private LocalDateTime comUpdDate;

    @TableField(exist = false)
    private List<SysDeptInfo> sysDeptInfos;

    @TableField(exist = false)
    private List<SysCompanyInfo> children;

    public void addChildren(List<SysCompanyInfo> roots){
        for (SysCompanyInfo root : roots) {
            if(root.getComId().equals(this.comSupComId)){
                if(root.getChildren()==null)root.setChildren(new ArrayList<>());
                root.getChildren().add(this);
                return;
            }else if(!CollectionUtils.isEmpty(root.getChildren())){
                this.addChildren(root.getChildren());
            }
        }
    }
}
