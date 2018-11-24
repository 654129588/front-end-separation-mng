package com.wisdom.mng.dao;

import com.wisdom.mng.entity.SysFunction;
import com.wisdom.mng.entity.SysRole;
import com.wisdom.mng.support.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysFunctionDao extends CustomRepository<SysFunction,Long>{

    @Query(nativeQuery = true,value="SELECT f.* FROM sys_function f,sys_role_functions rf,sys_user_roles ur WHERE f.id = rf.functions_id and rf.sys_role_id = ur.roles_id and ur.sys_user_id=:sysUserId and f.parent_id =:parentId GROUP BY f.id order by f.parent_id asc,f.orders asc")
    List<SysFunction> withSysUserIdAndParentIdQuery(@Param("sysUserId") Long sysUserId, @Param("parentId") Long parentId);

    @Query("update SysFunction s set s.functionName = ?2,s.functionLevel = ?3,s.functionUrl = ?4,s.orders = ?5,s.parentId = ?6 where s.id = ?1")
    @Modifying
    void update(Long id, String functionName, Integer functionLevel, String functionUrl, Integer orders, Long parentId);

    List<SysFunction> findAllByParentIdOrderByOrders(Long parentId);
}
