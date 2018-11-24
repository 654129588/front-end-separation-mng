package com.wisdom.mng.service;

import com.wisdom.mng.dao.SysUserDao;
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
public class SysUserService implements UserDetailsService {
    @Autowired
    SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String username){
        SysUser user = sysUserDao.findByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException("用户名不存在");
        return user;
    }

    public Page<SysUser> findByAuto(SysUser sysUser, Pageable pageable){
        return sysUserDao.findByAuto(sysUser,pageable);
    }

    @Transactional
    public void saveOrUpdate(SysUser sysUser) {
        /*if(sysUser.getId() != null)
            sysUserDao.update(sysUser.getId(),sysUser.getUsername(),sysUser.getPassword());
        else*/
            sysUserDao.saveAndFlush(sysUser);
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            sysUserDao.deleteById(id);
        }

    }
}
