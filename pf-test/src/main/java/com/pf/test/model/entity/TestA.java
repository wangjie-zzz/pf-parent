package com.pf.test.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author pf
 * @since 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TestA对象", description="")
public class TestA implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String test1;

    private String test2;

    private String test3;

    private String test4;

    private String test5;


}
