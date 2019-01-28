package com.wisdom.mng.dao;

import com.wisdom.mng.entity.About;
import com.wisdom.mng.entity.Category;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

public interface AboutDao extends CustomRepository<About,Long> {
    List<About> findByPostStatus(Short status);
    List<About> findByPostStatusAndCategory(Short status,Category category);
    List<About> findByPushStatus(Short push);
}
