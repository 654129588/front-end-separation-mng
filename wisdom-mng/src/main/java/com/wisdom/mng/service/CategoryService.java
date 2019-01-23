package com.wisdom.mng.service;

import com.wisdom.mng.dao.CategoryDao;
import com.wisdom.mng.entity.Category;
import com.wisdom.mng.entity.SysFunction;
import com.wisdom.mng.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/***
 * @author CHENWEICONG
 * @create 2019-01-21 10:22
 * @desc
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private Environment env;

    /**
     * 查出所有一二级栏目
     * @param
     * @return
     */
    public List<Category> getCategorys(){
        List<Category> oneCategorys = categoryDao.findAllByParentIdOrderByOrdersAsc((long) 0);
        for (Category oneCategory:oneCategorys) {
            List<Category> twoCategorys = categoryDao.findAllByParentIdOrderByOrdersAsc(oneCategory.getId());
            oneCategory.setChildCategory(twoCategorys);
        }
        return oneCategorys;
    }

    public Page<Category> findByAuto(Category category, Pageable pageable){
        return categoryDao.findByAuto(category,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest, Category category) {
        try {
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
                String key = (String) it.next();
                MultipartFile file = multipartRequest.getFile(key);
                if(file.getOriginalFilename().length() > 0){
                    String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String filename = System.currentTimeMillis()+suffix;
                    IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                    category.setLogo(env.getProperty("upload.url")+filename);
                }
            }
            if(category.getId() == null && category.getParentId() == 0){
                return;
            }
            categoryDao.saveAndFlush(category);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            Category category = categoryDao.getOne(id);
            if(category.getParentId() != 0)
                categoryDao.deleteById(id);
        }
    }
}
