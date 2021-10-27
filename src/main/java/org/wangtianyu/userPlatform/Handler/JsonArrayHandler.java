package org.wangtianyu.userPlatform.Handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({List.class})
public class JsonArrayHandler extends JacksonTypeHandler {
    public JsonArrayHandler() {
        super(List.class);
    }

}
