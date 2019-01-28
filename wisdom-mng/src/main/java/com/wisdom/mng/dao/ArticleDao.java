package com.wisdom.mng.dao;

import com.wisdom.mng.entity.About;
import com.wisdom.mng.entity.Article;
import com.wisdom.mng.support.CustomRepository;

import java.util.List;

public interface ArticleDao extends CustomRepository<Article,Long> {
    List<Article> findByPostStatus(Short status);
    List<Article> findByPushStatus(Short push);
}
