package com.wisdom.mng.dao;

import com.wisdom.mng.entity.Category;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

public interface CategoryDao extends CustomRepository<Category,Long> {
    List<Category> findAllByParentIdOrderByOrdersAsc(Long parentId);
}
