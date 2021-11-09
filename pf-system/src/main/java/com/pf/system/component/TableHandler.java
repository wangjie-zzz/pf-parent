package com.pf.system.component;

import com.pf.system.constants.SystemConstants;
import com.pf.system.constants.enums.TableNameEnum;
import com.pf.system.constants.enums.TableNameEnum;
import com.pf.system.model.dto.FormFieldDto;
import com.pf.system.model.dto.TableInfoDto;
import com.pf.system.model.dto.TableFieldDto;
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
    public abstract List<TableInfoDto> getByNamesInDb(List<TableNameEnum> tableNameEnums);

    /*
     * TODO 线程安全问题
     * */
    public List<TableInfoDto> getByNames(List<TableNameEnum> tableNames) {
        if(!CollectionUtils.isEmpty(tableNames)) {
            List<TableInfoDto> dtos = new ArrayList<>();
            List<TableNameEnum> nullDtos = new ArrayList<>();
            for (TableNameEnum tableNameEnum : tableNames) {
                if(this.boundHashOperations.hasKey(tableNameEnum.getName())) {
                    dtos.add(this.boundHashOperations.get(tableNameEnum.getName()));
                } else {
                    nullDtos.add(tableNameEnum);
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
    public Long removeByNames(List<TableNameEnum> tableNameEnums) {
        if(!CollectionUtils.isEmpty(tableNameEnums)) {
            return this.boundHashOperations.delete(tableNameEnums.stream().map(tableNameEnum -> tableNameEnum.getName()).collect(Collectors.toList()));
        }
        return 0l;
    }
}
