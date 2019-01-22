package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.entity.Article;
import com.wisdom.mng.entity.SysUser;
import com.wisdom.mng.utils.IOUtils;
import com.wisdom.mng.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-18 9:35
 * @desc chenweicong
 */
@Service
public class ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Autowired
    private Environment env;

    public Page<Article> findByAuto(Article article,Pageable pageable){
        return articleDao.findByAuto(article,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest,Article article){
        try{
            String banner = "";
            String files = "";
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
                String key = (String) it.next();
                MultipartFile file = multipartRequest.getFile(key);
                if(file.getOriginalFilename().length() > 0){
                    String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String filename = System.currentTimeMillis()+suffix;
                    IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                    if(key.equals("banner")){
                        banner += env.getProperty("upload.url")+filename+",";
                    }else if(key.equals("file")){
                        files += env.getProperty("upload.url")+filename+",";
                    }
                }
            }
            if(StringUtils.isNotBlank(banner)){
                article.setBanner(banner);
            }
            if(StringUtils.isNotBlank(files)){
                article.setFile(files);
            }
            if(article.getId() == null){
                article.setCreateDate(new Date());
                article.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }
            if(article.getPostStatus() == 1){
                article.setPostDate(new Date());
                article.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
            }
            articleDao.saveAndFlush(article);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            articleDao.deleteById(id);
        }
    }
}
