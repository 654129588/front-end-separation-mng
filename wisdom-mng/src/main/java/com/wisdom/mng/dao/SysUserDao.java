package com.wisdom.mng.dao;

import com.wisdom.mng.entity.SysUser;
import com.wisdom.mng.support.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SysUserDao extends CustomRepository<SysUser,Long> {
    SysUser findByUsername(String username);

    @Query("update SysUser s set s.username = ?2,s.password = ?3 where s.id = ?1")
    @Modifying
    void update(Long id, String username, String password);
}
