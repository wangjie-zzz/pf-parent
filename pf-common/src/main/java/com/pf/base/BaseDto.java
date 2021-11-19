package com.pf.base;

import com.pf.util.JacksonsUtils;
import lombok.Data;

import java.io.Serializable;
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
public class BaseDto<T extends BaseDto<T>> implements Serializable {
    private static final long serialVersionUID=1L;

    public static <P extends BaseEntity<P> ,T extends BaseDto<T>> T buildBy (P po, Class<T> clazz) {
        return buildsBy(Arrays.asList(po), clazz).get(0);
    }
    public static <P extends BaseEntity<P> ,T extends BaseDto<T>> List<T> buildsBy (List<P> pos, Class<T> clazz) {
        return pos.stream().map(po -> {
            return JacksonsUtils.copy(po, clazz);
        }).collect(Collectors.toList());
    }

}
