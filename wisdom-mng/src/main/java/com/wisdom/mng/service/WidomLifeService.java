package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.dao.WisdomLifeDao;
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
 * @create 2019-01-22 11:29
 * @desc
 */
@Service
public class WidomLifeService {
    @Autowired
    WisdomLifeDao wisdomLifeDao;

    @Autowired
    private Environment env;

    public Page<WisdomLife> findByAuto(WisdomLife wisdomLife, Pageable pageable){
        return wisdomLifeDao.findByAuto(wisdomLife,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest, WisdomLife wisdomLife){
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
                    if(key.equals("uploadBanner")){
                        banner += env.getProperty("upload.url")+filename+",";
                    }else if(key.equals("uploadFile")){
                        files += env.getProperty("upload.url")+filename+",";
                    }
                }
            }
            if(StringUtils.isNotBlank(banner)){
                wisdomLife.setBanner(banner);
            }
            if(StringUtils.isNotBlank(files)){
                wisdomLife.setFile(files);
            }
            if(wisdomLife.getId() == null){
                wisdomLife.setVisits(0);
                wisdomLife.setPostStatus((short) 0);
                wisdomLife.setPushStatus((short) 0);
                wisdomLife.setCreateDate(new Date());
                wisdomLife.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }else{
                WisdomLife w = wisdomLifeDao.getOne(wisdomLife.getId());
                wisdomLife.setPostStatus(w.getPostStatus());
                wisdomLife.setPushStatus(w.getPushStatus());
                UpdateTool.copyNullProperties(w, wisdomLife);
            }
            wisdomLifeDao.saveAndFlush(wisdomLife);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            wisdomLifeDao.deleteById(id);
        }
    }

    @Transactional
    public Result post(Short postStatus, Long wisdomLifeId) {
        WisdomLife wisdomLife = wisdomLifeDao.getOne(wisdomLifeId);
        if(postStatus == 1){
            Category category = wisdomLife.getCategory();
            if(category == null){
                return ResultUtils.DATA("该文章未选择栏目",ResultUtils.RESULT_ERROR_CODE,null);
            }
            List<WisdomLife> byPostStatus = wisdomLifeDao.findByPostStatusAndCategory(postStatus,category);
            if(byPostStatus != null && byPostStatus.size() > 0){
                return ResultUtils.DATA(category.getName()+"栏目只能发布一篇文章",ResultUtils.RESULT_ERROR_CODE,null);
            }
            wisdomLife.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
            wisdomLife.setPostDate(new Date());
        }
        wisdomLife.setPostStatus(postStatus);
        wisdomLifeDao.saveAndFlush(wisdomLife);
        return ResultUtils.SUCCESS();
    }

    public List<WisdomLife> findAllByAuto(WisdomLife wisdomLife) {
        return wisdomLifeDao.findAllByAuto(wisdomLife);
    }

    @Transactional
    public void saveVisits(Long id){
        WisdomLife wisdomLife = wisdomLifeDao.getOne(id);
        wisdomLife.setVisits(wisdomLife.getVisits()+1);
        wisdomLifeDao.saveAndFlush(wisdomLife);
    }

    public WisdomLife getOne(Long id){
        return wisdomLifeDao.getOne(id);
    }
}
