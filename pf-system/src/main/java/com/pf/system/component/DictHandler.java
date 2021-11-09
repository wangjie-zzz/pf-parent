package com.pf.system.component;

import com.pf.system.constants.SystemConstants;
import com.pf.system.constants.enums.DictFieldEnum;
import com.pf.system.model.dto.DictInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName : DictHanlder
 * @Description :
 * @Author : wangjie
 * @Date: 2021/11/9-9:44
 */
public abstract class DictHandler {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    private BoundHashOperations<String, String, List<DictInfoDto>> boundHashOperations;
    public DictHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.boundHashOperations = redisTemplate.boundHashOps(SystemConstants.REDIS_KEY.dict);
        Map<String, List<DictInfoDto>> fieldMap = this.init().stream()
                .collect(Collectors.groupingBy(DictInfoDto::getDictField));
        this.boundHashOperations.putAll(fieldMap);
    }
    
    public abstract List<DictInfoDto> init();
    
    public List<DictInfoDto> getByField(DictFieldEnum fieldEnum) {
        String field = fieldEnum.getField();
        return this.boundHashOperations.get(field);
    }
    public String toName(DictFieldEnum fieldEnum, Integer dictKey) {
        Optional<DictInfoDto> optional = this.getByField(fieldEnum).stream()
                .filter(dictInfoDto -> dictKey.toString().equals(dictInfoDto.getDictKey().toString()))
                .findFirst();
        if(optional.isPresent()) {
            return optional.get().getDictName();
        }
        return null;
    }
    public Integer toKey(DictFieldEnum fieldEnum, String dictName) {
        Optional<DictInfoDto> optional = this.getByField(fieldEnum).stream()
                .filter(dictInfoDto -> dictName.equals(dictInfoDto.getDictName()))
                .findFirst();
        if(optional.isPresent()) {
            return optional.get().getDictKey();
        }
        return null;
    }
    
}
