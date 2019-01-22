package com.wisdom.mng.service;

import com.wisdom.mng.dao.SysFunctionDao;
import com.wisdom.mng.entity.SysFunction;
import com.wisdom.mng.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysFunctionService {

    @Autowired
    SysFunctionDao sysFunctionDao;

    /**
     * 查出用户一二级菜单权限，按钮权限
     * @param sysUserId
     * @return
     */
    public List<SysFunction> getMenu(Long sysUserId){
        List<SysFunction> oneFunctions = sysFunctionDao.withSysUserIdAndParentIdQuery(sysUserId, (long) 0);
        for (SysFunction oneFunction:oneFunctions) {
            List<SysFunction> twoFunctions = sysFunctionDao.withSysUserIdAndParentIdQuery(sysUserId, oneFunction.getId());
            for (SysFunction twoFunction : twoFunctions) {
                List<SysFunction> threeFunctions = sysFunctionDao.withSysUserIdAndParentIdQuery(sysUserId, twoFunction.getId());
                twoFunction.setChildSysFunction(threeFunctions);
            }
            oneFunction.setChildSysFunction(twoFunctions);
        }
        return oneFunctions;
    }

    public List<SysFunction> getAllMenu(){
        List<SysFunction> oneFunctions = sysFunctionDao.findAllByParentIdOrderByOrders((long) 0);
        for (SysFunction oneFunction:oneFunctions) {
            List<SysFunction> twoFunctions = sysFunctionDao.findAllByParentIdOrderByOrders(oneFunction.getId());
            for (SysFunction twoFunction : twoFunctions) {
                List<SysFunction> threeFunctions = sysFunctionDao.findAllByParentIdOrderByOrders(twoFunction.getId());
                twoFunction.setChildSysFunction(threeFunctions);
            }
            oneFunction.setChildSysFunction(twoFunctions);
        }
        return oneFunctions;
    }

    public Page<SysFunction> findByAuto(SysFunction sysFunction, Pageable pageable){
        return sysFunctionDao.findByAuto(sysFunction,pageable);
    }

    public List<SysFunction> findAllByParentIdOrderByOrders(Long parentId){
        if(parentId == null)
            return sysFunctionDao.findAllByParentIdOrderByOrders((long) 0);
        else
            return sysFunctionDao.findAllByParentIdOrderByOrders(parentId);
    }

    public List<SysFunction> findAll(){
            return sysFunctionDao.findAll();
    }

    @Transactional
    public void saveOrUpdate(SysFunction sysFunction) {
        if(sysFunction.getId() != null)
            sysFunctionDao.update(sysFunction.getId(),sysFunction.getFunctionName(),sysFunction.getFunctionLevel(),sysFunction.getFunctionUrl(),sysFunction.getOrders(),sysFunction.getParentId());
        else
            sysFunctionDao.saveAndFlush(sysFunction);
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            sysFunctionDao.deleteById(id);
        }

    }
}
