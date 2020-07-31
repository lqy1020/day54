package com.lqy.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @auth lqy
 * @date 2020/7/22
 * @Description
 */
public class WorkOrderSqlProvider {

    public String selectPage(Map<String,Object> params) {
        return new SQL() {{
            SELECT("wo.*,cu.name create_user_name,co.name create_office_name,tu.name transport_user_name, ru.name recipient_user_name");
            FROM("work_order wo");
            LEFT_OUTER_JOIN("sys_user cu ON wo.create_user_id = cu.id " +
                    "  LEFT JOIN sys_office co ON cu.office_id = co.id " +
                    "  LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id " +
                    "  LEFT JOIN sys_office `to` ON tu.office_id = `to`.id " +
                    "  LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id " +
                    "  LEFT JOIN sys_office ro ON ru.office_id = ro.id" );
            WHERE("wo.del_flag = 0   " +
                    " AND cu.del_flag = 0   " +
                    " AND co.del_flag = 0 ");
            if (params.containsKey("status")&& !StringUtils.isEmpty(params.get("status"))){
                WHERE(" wo.STATUS = #{status}");
            }
            if(params.containsKey("startDate")&& !StringUtils.isEmpty(params.get("startDate"))){
                WHERE("wo.create_date >= #{startDate}");
            }
            if(params.containsKey("endDate")&& !StringUtils.isEmpty(params.get("endDate"))){
                WHERE("wo.create_date <= #{endDate}");
            }
            if(params.containsKey("officeId")&& !StringUtils.isEmpty(params.get("officeId"))){
                WHERE("( co.id = #{officeId} OR `to`.id =  #{officeId} OR ro.id =  #{officeId} )");
            }
        }}.toString();
    }
}
