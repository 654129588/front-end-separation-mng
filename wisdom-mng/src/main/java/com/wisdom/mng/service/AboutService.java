package com.wisdom.mng.service;

import com.wisdom.mng.dao.AboutDao;
import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.entity.*;
import com.wisdom.mng.utils.IOUtils;
import com.wisdom.mng.utils.ResultUtils;
import com.wisdom.mng.utils.UpdateTool;
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
 * @create 2019-01-22 13:43
 * @desc
 */
@Service
public class AboutService {
    @Autowired
    AboutDao aboutDao;

    @Autowired
    private Environment env;

    public Page<About> findByAuto(About about, Pageable pageable){
        return aboutDao.findByAuto(about,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest, About about){
        try{
            String banner = "";
            String files = "";
            String video = "";
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
                String key = (String) it.next();
                MultipartFile file = multipartRequest.getFile(key);
                if(file.getOriginalFilename().length() > 0){
                    String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String filename = System.currentTimeMillis()+suffix;
                    IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                    if(key.equals("uploadBanner")){
                        banner += env.getProperty("upload.url")+filename+",";
                    }else if(key.equals("uploadFile")){
                        files += env.getProperty("upload.url")+filename+",";
                    }else if(key.equals("uploadVideo")){
                        video += env.getProperty("upload.url")+filename+",";
                    }
                }
            }
            if(StringUtils.isNotBlank(banner)){
                about.setBanner(banner);
            }
            if(StringUtils.isNotBlank(files)){
                about.setFile(files);
            }
            if(about.getId() == null){
                about.setVisits(0);
                about.setPostStatus((short) 0);
                about.setPushStatus((short) 0);
                about.setCreateDate(new Date());
                about.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }else{
                About a = aboutDao.getOne(about.getId());
                about.setPostStatus(a.getPostStatus());
                about.setPushStatus(a.getPushStatus());
                UpdateTool.copyNullProperties(a, about);
            }
            aboutDao.saveAndFlush(about);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            aboutDao.deleteById(id);
        }
    }

    @Transactional
    public Result post(Short postStatus, Long aboutId) {
        About about = aboutDao.getOne(aboutId);
        if(postStatus == 1){
            Category category = about.getCategory();
            if(category == null){
                return ResultUtils.DATA("该文章未选择栏目",ResultUtils.RESULT_ERROR_CODE,null);
            }
            List<About> byPostStatus = aboutDao.findByPostStatusAndCategory(postStatus,category);
            if(byPostStatus != null && byPostStatus.size() > 0){
                return ResultUtils.DATA(category.getName()+"栏目只能发布一篇文章",ResultUtils.RESULT_ERROR_CODE,null);
            }
            about.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
            about.setPostDate(new Date());
        }else{
            about.setPushStatus(postStatus);
        }
        about.setPostStatus(postStatus);
        aboutDao.saveAndFlush(about);
        return ResultUtils.SUCCESS();
    }

    @Transactional
    public Result push(Short pushStatus, Long aboutId) {
        About about = aboutDao.getOne(aboutId);
        if(pushStatus == 1){
            List<About> byPushStatus = aboutDao.findByPushStatus(pushStatus);
            if(byPushStatus != null && byPushStatus.size() >= 2){
                return ResultUtils.DATA("本栏目推送首页文章不能超过2篇",ResultUtils.RESULT_ERROR_CODE,null);
            }
        }
        about.setPushStatus(pushStatus);
        aboutDao.saveAndFlush(about);
        return ResultUtils.SUCCESS();
    }

    public List<About> findAllByAuto(About about) {
        return aboutDao.findAllByAuto(about);
    }

    @Transactional
    public void saveVisits(Long id){
        About about = aboutDao.getOne(id);
        about.setVisits(about.getVisits()+1);
        aboutDao.saveAndFlush(about);
    }

    public About getOne(Long id){
        return aboutDao.getOne(id);
    }
}
