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
    
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> T buildByVo (P vo, Class<T> clazz) {
        return buildsByVo(Arrays.asList(vo), clazz, false).get(0);
    }
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> T buildByVo (P vo, Class<T> clazz, boolean update) {
        return buildsByVo(Arrays.asList(vo), clazz, update).get(0);
    }
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> List<T> buildsByVo (List<P> vos, Class<T> clazz) {
        return buildsByVo(vos, clazz, false);
    }
    public static <P extends BaseVo<P>,T extends BaseEntity<T>> List<T> buildsByVo (List<P> vos, Class<T> clazz, boolean update) {
        return build(vos, clazz, update);
    }


    public static <P extends BaseDto<P>,T extends BaseEntity<T>> T buildByDto (P vo, Class<T> clazz) {
        return buildsByDto(Arrays.asList(vo), clazz, false).get(0);
    }
    public static <P extends BaseDto<P>,T extends BaseEntity<T>> T buildByDto (P vo, Class<T> clazz, boolean update) {
        return buildsByDto(Arrays.asList(vo), clazz, update).get(0);
    }
    public static <P extends BaseDto<P>,T extends BaseEntity<T>> List<T> buildsByDto (List<P> vos, Class<T> clazz) {
        return buildsByDto(vos, clazz, false);
    }
    public static <P extends BaseDto<P>,T extends BaseEntity<T>> List<T> buildsByDto (List<P> vos, Class<T> clazz, boolean update) {
        return build(vos, clazz, update);
    }
    
    private static <Obj,T extends BaseEntity<T>> List<T> build (List<Obj> objs, Class<T> clazz, boolean update) {
        UserDto userDto = UserContext.getSysUserHolder(true);
        return objs.stream().map(obj -> {
            T t = JacksonsUtils.copy(obj, clazz);
            t.setUpdateUser(userDto.getUserId());
            t.setUpdateTime(LocalDateTime.now());
            t.setUseState(UseStateEnum.EFFECTIVE.getCode());
            if(update) {
                t.setCreateUser(null);
                t.setCreateTime(null);
            } else {
                t.setCreateUser(userDto.getUserId());
                t.setCreateTime(LocalDateTime.now());
            }
            return t;
        }).collect(Collectors.toList());
    }

}
