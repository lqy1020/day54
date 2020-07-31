package com.lqy.mapper;

import com.lqy.entity.Qualification;
import com.lqy.entity.QualificationCondition;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/20
 * @Description
 */
public class QualificationSqlProvider {

    public String selectPage(QualificationCondition condition){

        StringBuilder sb=new StringBuilder();
        sb.append("SELECT " +
                "  q.*," +
                "uu.name upload_name," +
                "cu.name check_name " +
                "FROM " +
                "  qualification q " +
                "  LEFT JOIN sys_user uu " +
                "  on q.upload_user_id=uu.id " +
                "  LEFT JOIN sys_user cu " +
                "  on q.check_user_id=cu.id " +
                "where " +
                "q.del_flag=0 ");
        if (!StringUtils.isEmpty(condition.getType())){
            sb.append("and q.type=#{type} ");
        }
        if (!StringUtils.isEmpty(condition.getCheck())){
            sb.append("and q.check=#{check} ");

        }
        if (!StringUtils.isEmpty(condition.getStartDate())){
            sb.append("and q.create_date>=#{startDate} ");
        }
        if (!StringUtils.isEmpty(condition.getEndDate())){
            sb.append("and q.update_date<=#{endDate} ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
