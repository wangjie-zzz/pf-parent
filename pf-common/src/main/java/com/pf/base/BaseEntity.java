package com.pf.base;

import com.pf.aop.context.UserContext;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.model.UserDto;
import com.pf.util.JacksonsUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName : BaseEntity
 * @Description :
 * @Author : wangjie
 * @Date: 2021/11/9-12:00
 */
@Data
public class BaseEntity<T extends BaseEntity<T>> implements Serializable {
    private static final long serialVersionUID=1L;

    private Integer useState;

    private Long updateUser;

    private LocalDateTime updateTime;

    private Long createUser;

    private LocalDateTime createTime;
    
    public T build() {
        return build(UserContext.getSysUserHolder(true), false);
    }
    public T build(UserDto userDto) {
        return build(userDto, false);
    }
    public T build(UserDto userDto, boolean update) {
        this.setUpdateUser(userDto.getUserId());
        this.setUpdateTime(LocalDateTime.now());
        this.setUseState(UseStateEnum.EFFECTIVE.getCode());
        if(update) {
            this.setCreateUser(null);
            this.setCreateTime(null);
        } else {
            this.setCreateUser(userDto.getUserId());
            this.setCreateTime(LocalDateTime.now());
        }
        return (T) this;
    }
    
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> T buildByVo (P vo, Class<T> clazz) {
        return buildByVo(vo, clazz, false);
    }
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> T buildByVo (P vo, Class<T> clazz, boolean update) {
        UserDto userDto = UserContext.getSysUserHolder(true);
        T t = JacksonsUtils.copy(vo, clazz);
        return t.build(userDto, update);
    }
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> List<T> buildsByVo (List<P> vos, Class<T> clazz) {
        return buildsByVo(vos, clazz, false);
    }
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> List<T> buildsByVo (List<P> vos, Class<T> clazz, boolean update) {
        UserDto userDto = UserContext.getSysUserHolder(true);
        return vos.stream().map(obj -> {
            T t = JacksonsUtils.copy(obj, clazz);
            return t.build(userDto, update);
        }).collect(Collectors.toList());
    }


    public static <P extends BaseDto<P>,T extends BaseEntity<T>> T buildByDto (P vo, Class<T> clazz) {
        return buildByDto(vo, clazz, false);
    }
    public static <P extends BaseDto<P>,T extends BaseEntity<T>> T buildByDto (P vo, Class<T> clazz, boolean update) {
        UserDto userDto = UserContext.getSysUserHolder(true);
        T t = JacksonsUtils.copy(vo, clazz);
        return t.build(userDto, update);
    }
    public static <P extends BaseDto<P>,T extends BaseEntity<T>> List<T> buildsByDto (List<P> vos, Class<T> clazz) {
        return buildsByDto(vos, clazz, false);
    }
    public static <P extends BaseDto<P>,T extends BaseEntity<T>> List<T> buildsByDto (List<P> vos, Class<T> clazz, boolean update) {
        UserDto userDto = UserContext.getSysUserHolder(true);
        return vos.stream().map(obj -> {
            T t = JacksonsUtils.copy(obj, clazz);
            return t.build(userDto, update);
        }).collect(Collectors.toList());
    }

}
