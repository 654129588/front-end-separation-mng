package com.wisdom.mng.dao;

import com.wisdom.mng.entity.About;
import com.wisdom.mng.entity.Banner;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

public interface BannerDao extends CustomRepository<Banner,Long> {
    List<Banner> findByPostStatus(Short status);
}
