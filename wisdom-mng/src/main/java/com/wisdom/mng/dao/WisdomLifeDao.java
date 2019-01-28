package com.wisdom.mng.dao;

import com.wisdom.mng.entity.About;
import com.wisdom.mng.entity.Category;
import com.wisdom.mng.entity.SysUser;
import com.wisdom.mng.entity.WisdomLife;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

public interface WisdomLifeDao extends CustomRepository<WisdomLife,Long> {
    List<WisdomLife> findByPostStatus(Short status);
    List<WisdomLife> findByPushStatus(Short push);

    List<WisdomLife> findByPostStatusAndCategory(Short postStatus, Category category);
}
