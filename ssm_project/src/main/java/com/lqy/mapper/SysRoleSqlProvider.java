package com.lqy.mapper;

import com.lqy.entity.SysOffice;
import com.lqy.entity.SysResource;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @auth lqy
 * @date 2020/8/3
 * @Description
 */
public class SysRoleSqlProvider {

  public String selectPage(Map<String,Object> params){
      StringBuilder sb=new StringBuilder();
      sb.append("SELECT " +
              "  sr.*, " +
              "  so.NAME office_name  " +
              "FROM " +
              "  sys_role sr, " +
              "  sys_office so  " +
              "WHERE " +
              "  sr.del_flag = 0 ");
      if (params.containsKey("dataScope")&&!StringUtils.isEmpty(params.get("dataScope"))){
          sb.append(" AND sr.data_scope = #{dataScope} ");
      }
      if (params.containsKey("oid")&&!StringUtils.isEmpty(params.get("oid"))){
          sb.append(" AND so.id=#{oid} ");
      }
      if (params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
          sb.append(" AND sr.NAME LIKE concat ( '%',#{name}, '%' ) ");
      }
      sb.append(" AND sr.office_id = so.id ");
      return sb.toString();
  }

   public String deleteBatch(@Param("rid") long rid,@Param("ids") long[] ids){
      StringBuilder sb=new StringBuilder();
       sb.append("DELETE  from sys_user_role where role_id=#{rid} and user_id in ");
       sb.append("(");
       for (int i = 0; i < ids.length; i++) {
           sb.append("#{ids["+i+"]},");
       }
       sb.deleteCharAt(sb.length()-1);
       sb.append(")");
       return sb.toString();
   }

  public String insertBatch(@Param("rid") long rid, @Param("cids") List<Long> cids){
      StringBuilder sb=new StringBuilder();
      sb.append("INSERT INTO `guguanjia`.`sys_user_role`(`role_id`, `user_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");
      for (int i = 0; i < cids.size(); i++) {
          sb.append("(#{rid},#{cids["+i+"]},null,now(),null,now(),0),");
      }
      sb.deleteCharAt(sb.length()-1);
      return sb.toString();
  }

  public String insertRoleResources(@Param("rid") long rid,@Param("resources") List<SysResource> resources){
      StringBuilder sb=new StringBuilder();
      sb.append("INSERT INTO `guguanjia`.`sys_role_resource`(`role_id`, `resource_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");
      for (int i = 0; i <resources.size() ; i++) {
          sb.append("(#{rid}, #{resources["+i+"].id}, NULL, now(), NULL, now(), '0'),");
      }
      sb.deleteCharAt(sb.length()-1);

      return sb.toString();
  }

  public String insertRoleOffices(@Param("rid") long rid,@Param("offices") List<SysOffice> offices){
      StringBuilder sb = new StringBuilder();
      sb.append("INSERT INTO `guguanjia`.`sys_role_office`(`role_id`, `office_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");

      for (int i = 0; i <offices.size() ; i++) {
          sb.append("(#{rid}, #{offices["+i+"].id}, NULL,NOW(), NULL, NOW(),0),");
      }
      sb.deleteCharAt(sb.length()-1);

      return sb.toString();
  }


}
