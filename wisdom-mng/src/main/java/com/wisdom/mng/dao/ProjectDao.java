package com.wisdom.mng.dao;

import com.wisdom.mng.entity.About;
import com.wisdom.mng.entity.Article;
import com.wisdom.mng.entity.Category;
import com.wisdom.mng.entity.Project;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-23 10:27
 * @desc
 */
public interface ProjectDao extends CustomRepository<Project,Long> {
    List<Project> findByPostStatus(Short status);
    List<Project> findByPushStatus(Short push);

    List<Project> findByPostStatusAndCategory(Short postStatus, Category category);
}
