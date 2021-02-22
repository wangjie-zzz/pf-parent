package com.pf.test;/**
 * @ClassName : User
 * @Description :
 * @Author : wangjie
 * @Date: 2020/12/18-16:47
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName : User
 * @Description :
 * @Author : wangjie
 * @Date: 2020/12/18-16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor()
public class User{

    private String userId;
    private String userName;
    private transient Integer userAge; // transient
}
