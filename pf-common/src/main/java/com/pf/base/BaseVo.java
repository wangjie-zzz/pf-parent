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
public class BaseVo<T extends BaseVo<T>> implements Serializable {
    private static final long serialVersionUID=1L;

    public static <P extends BaseEntity<P> ,T extends BaseVo<T>> T buildBy (P po, Class<T> clazz) {
        return buildsBy(Arrays.asList(po), clazz).get(0);
    }
    public static <P extends BaseEntity<P> ,T extends BaseVo<T>> List<T> buildsBy (List<P> pos, Class<T> clazz) {
        return pos.stream().map(po -> {
            return JacksonsUtils.copy(po, clazz);
        }).collect(Collectors.toList());
    }

}
