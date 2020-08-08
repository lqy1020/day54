package com.lqy.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @auth lqy
 * @date 2020/8/6
 * @Description
 */
public class SysLogSqlProvider {

    public String selectPage(Map<String,Object>params){
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT     " +
                "  *      " +
                "FROM     " +
                "  sys_log      " +
                "WHERE  " +
                "  1=1  ");
        if (params.containsKey("type")&& !StringUtils.isEmpty(params.get("type"))){
            sb.append(" and type=#{type}");
        }
        if (params.containsKey("description")&&!StringUtils.isEmpty(params.get("description"))){
            sb.append("AND description LIKE CONCAT('%',#{description},'%')");
        }
        return sb.toString();
    }

}
