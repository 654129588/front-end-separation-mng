package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.dao.BannerDao;
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
 * @create 2019-01-22 11:09
 * @desc
 */
@Service
public class BannerService {
    @Autowired
    BannerDao bannerDao;

    @Autowired
    private Environment env;

    public Page<Banner> findByAuto(Banner banner, Pageable pageable){
        return bannerDao.findByAuto(banner,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest, Banner banner){
        try{
            String banners = "";
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
                String key = (String) it.next();
                MultipartFile file = multipartRequest.getFile(key);
                if(file.getOriginalFilename().length() > 0){
                    String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String filename = System.currentTimeMillis()+suffix;
                    IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                    if(key.equals("uploadBanner")){
                        banners += env.getProperty("upload.url")+filename+",";
                    }
                }
            }
            if(StringUtils.isNotBlank(banners)){
                banner.setBanner(banners);
            }
            if(banner.getId() == null){
                banner.setPostStatus((short) 0);
                banner.setCreateDate(new Date());
                banner.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }else{
                Banner b = bannerDao.getOne(banner.getId());
                banner.setPostStatus(b.getPostStatus());
                UpdateTool.copyNullProperties(b, banner);
            }
            bannerDao.saveAndFlush(banner);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            bannerDao.deleteById(id);
        }
    }

    @Transactional
    public Result post(Short postStatus, Long bannerId) {
        Banner banner = bannerDao.getOne(bannerId);
        if(postStatus == 1){
            List<Banner> byPostStatus = bannerDao.findByPostStatus(postStatus);
            if(byPostStatus != null && byPostStatus.size() >= 5){
                return ResultUtils.DATA("只能发布五张轮播图",ResultUtils.RESULT_ERROR_CODE,null);
            }
            banner.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
            banner.setPostDate(new Date());
        }
        banner.setPostStatus(postStatus);
        bannerDao.saveAndFlush(banner);
        return ResultUtils.SUCCESS();
    }

    public List<Banner> findByPostStatus(Short postStatus){
        return bannerDao.findByPostStatus(postStatus);
    }
}
