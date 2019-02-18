package com.wisdom.mng.dao;

import com.wisdom.mng.entity.Partner;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

public interface PartnerDao extends CustomRepository<Partner,Long> {
    List<Partner> findByPostStatus(Short status);
}
