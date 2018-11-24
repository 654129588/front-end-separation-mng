package com.wisdom.mng.service;

import com.wisdom.mng.dao.SysRoleDao;
import com.wisdom.mng.dao.SysUserDao;
import com.wisdom.mng.entity.SysRole;
import com.wisdom.mng.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SysRoleService {
    @Autowired
    SysRoleDao sysRoleDao;

    public Page<SysRole> findByAuto(SysRole sysRole, Pageable pageable){
        return sysRoleDao.findByAuto(sysRole,pageable);
    }

    @Transactional
    public void saveOrUpdate(SysRole sysRole) {
        sysRoleDao.saveAndFlush(sysRole);
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            sysRoleDao.deleteById(id);
        }
    }

    public SysRole findById(Long id){
        return sysRoleDao.findById(id).get();
    }

    public List<SysRole> findAll(){
        return sysRoleDao.findAll();
    }
}
