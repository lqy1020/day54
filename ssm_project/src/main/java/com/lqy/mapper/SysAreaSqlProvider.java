package com.lqy.mapper;

import com.lqy.entity.SysArea;
import net.sf.jsqlparser.expression.WhenClause;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/29
 * @Description
 */
public class SysAreaSqlProvider {

    public String selectByName(String name){
        return new SQL(){{
            SELECT("sub.*,parent.name parent_name");
            FROM("sys_area sub,sys_area parent ");
            WHERE("sub.parent_id=parent.id ");
            if (!StringUtils.isEmpty(name)){
                WHERE("sub.name like concat('%',#{name},'%')");
            }
            WHERE("sub.del_flag='0'");
        }}.toString();
    }

    public String insertBatch(@Param("areas") List<SysArea> areas){
        return new SQL(){{
            INSERT_INTO("sys_area");
            INTO_COLUMNS("`parent_id`, `parent_ids`, `code`, `name`, `type`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `icon`");
            for (int i = 0; i < areas.size(); i++) {
                INTO_VALUES("#{areas["+i+"].parentId}, #{areas["+i+"].parentIds}, #{areas["+i+"].code}, " +
                        "#{areas["+i+"].name}, #{areas["+i+"].type}, #{areas["+i+"].createBy}, " +
                        "now(), #{areas["+i+"].updateBy}, now(), " +
                        "#{areas["+i+"].remarks}, #{areas["+i+"].delFlag}, #{areas["+i+"].icon}");
                ADD_ROW();
            }

        }}.toString();
    }
}
