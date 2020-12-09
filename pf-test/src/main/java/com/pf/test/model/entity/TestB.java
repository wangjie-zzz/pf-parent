package com.pf.test.model.entity;

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
@ApiModel(value="TestB对象", description="")
public class TestB implements Serializable {

    private static final long serialVersionUID=1L;

    private String test1;

    private String test2;

    private String test3;

    private String test4;

    private String test5;


}
