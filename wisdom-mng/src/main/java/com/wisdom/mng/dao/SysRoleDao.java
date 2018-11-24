package com.wisdom.mng.dao;

import com.wisdom.mng.entity.SysRole;
import com.wisdom.mng.support.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysRoleDao extends CustomRepository<SysRole,Long> {

    @Query("update SysRole s set s.name = ?2 where s.id = ?1")
    @Modifying
    void update(Long id, String name);

    @Query(nativeQuery = true,value="SELECT r.* FROM sys_role_functions rf,sys_role r WHERE  rf.sys_role_id = r.id and rf.functions_id=:functionsId")
    List<SysRole> findSysRoleByFunctionId(@Param("functionsId") Long functionsId);
}
