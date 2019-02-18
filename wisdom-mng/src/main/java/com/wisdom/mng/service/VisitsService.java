package com.wisdom.mng.service;

import com.wisdom.mng.dao.VisitsDao;
import com.wisdom.mng.entity.Visits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/***
 * @author CHENWEICONG
 * @create 2019-01-29 11:33
 * @desc
 */
@Service
public class VisitsService {
    @Autowired
    private VisitsDao visitsDao;

    @Transactional
    public void saveOrUpdate(Visits visits){
        visitsDao.saveAndFlush(visits);
    }
}
