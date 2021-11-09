package com.pf.system.component;

import com.pf.system.constants.SystemConstants;
import com.pf.system.model.dto.FormInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName : DictHanlder
 * @Description :
 * @Author : wangjie
 * @Date: 2021/11/9-9:44
 */
@Slf4j
public abstract class FormHandler {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    private BoundHashOperations<String, String, FormInfoDto> boundHashOperations;
    public FormHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.boundHashOperations = this.redisTemplate.boundHashOps(SystemConstants.REDIS_KEY.form);
        Map<String, FormInfoDto> fieldMap = this.init().stream()
                .collect(Collectors.toMap(FormInfoDto::getName, Function.identity())); // name不可重复
        this.boundHashOperations.putAll(fieldMap);
    }
    
    public abstract List<FormInfoDto> init();
    public abstract List<FormInfoDto> getByNamesInDb(List<String> formNameEnums);
    
    /*
    * TODO 线程安全问题
    * */
    public List<FormInfoDto> getByNames(List<String> formNames) {
        if(!CollectionUtils.isEmpty(formNames)) {
            List<FormInfoDto> dtos = new ArrayList<>();
            List<String> nullDtos = new ArrayList<>();
            for (String formEnum : formNames) {
                if(this.boundHashOperations.hasKey(formEnum)) {
                    dtos.add(this.boundHashOperations.get(formEnum));
                } else {
                    nullDtos.add(formEnum);
                }
            }
            if(!nullDtos.isEmpty()) {
                log.info("准备在db中加载表单：{}", nullDtos);
                Map<String, FormInfoDto> map = this.getByNamesInDb(nullDtos).stream().collect(Collectors.toMap(FormInfoDto::getName, Function.identity()));
                dtos.addAll(map.values());
                this.boundHashOperations.putAll(map);
            }
            return dtos;
        }
        return null;
    }
    /*返回：被成功删除字段的数量，不包括被忽略的字段。*/
    public Long removeByNames(String... formNames) {
        if(formNames.length > 0) {
            return this.boundHashOperations.delete(formNames);
        }
        return 0l;
    }
}
