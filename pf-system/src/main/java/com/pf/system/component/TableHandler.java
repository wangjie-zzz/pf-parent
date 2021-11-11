package com.pf.system.component;

import com.pf.system.constants.SystemConstants;
import com.pf.system.model.dto.TableInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName : DictHanlder
 * @Description :
 * @Author : wangjie
 * @Date: 2021/11/9-9:44
 */
@Slf4j
public abstract class TableHandler {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    private BoundHashOperations<String, String, TableInfoDto> boundHashOperations;
    public TableHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.boundHashOperations = this.redisTemplate.boundHashOps(SystemConstants.REDIS_KEY.table);
        Map<String, TableInfoDto> fieldMap = this.init().stream()
                .collect(Collectors.toMap(TableInfoDto::getName, Function.identity())); // name不可重复
        this.boundHashOperations.putAll(fieldMap);
    }
    
    public abstract List<TableInfoDto> init();
    public abstract List<TableInfoDto> getByNamesInDb(List<String> tableNameEnums);

    /*
     * TODO 线程安全问题
     * */
    public List<TableInfoDto> getByNames(List<String> tableNames) {
        if(!CollectionUtils.isEmpty(tableNames)) {
            List<TableInfoDto> dtos = new ArrayList<>();
            List<String> nullDtos = new ArrayList<>();
            for (String tableName : tableNames) {
                if(this.boundHashOperations.hasKey(tableName)) {
                    dtos.add(this.boundHashOperations.get(tableName));
                } else {
                    nullDtos.add(tableName);
                }
            }
            if(!nullDtos.isEmpty()) {
                log.info("准备在db中加载表格：{}", nullDtos);
                Map<String, TableInfoDto> map = this.getByNamesInDb(nullDtos).stream().collect(Collectors.toMap(TableInfoDto::getName, Function.identity()));
                dtos.addAll(map.values());
                this.boundHashOperations.putAll(map);
            }
            return dtos;
        }
        return null;
    }
    /*返回：被成功删除字段的数量，不包括被忽略的字段。*/
    public Long removeByNames(String... tableNames) {
        if(tableNames.length > 0) {
            return this.boundHashOperations.delete(tableNames);
        }
        return 0l;
    }
}
