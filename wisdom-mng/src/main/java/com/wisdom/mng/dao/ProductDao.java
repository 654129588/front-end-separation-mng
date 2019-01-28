package com.wisdom.mng.dao;

import com.wisdom.mng.entity.About;
import com.wisdom.mng.entity.Product;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

public interface ProductDao extends CustomRepository<Product,Long> {
    List<Product> findByPostStatus(Short status);
    List<Product> findByPushStatus(Short push);
}
