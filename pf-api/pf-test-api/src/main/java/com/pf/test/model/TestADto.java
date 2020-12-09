package com.pf.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName : TestADto
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/6-20:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestADto implements Serializable {
    private static final long serialVersionUID=1L;

    private String test1;

    private String test2;

    private String test3;

    private String test4;

    private String test5;
}
