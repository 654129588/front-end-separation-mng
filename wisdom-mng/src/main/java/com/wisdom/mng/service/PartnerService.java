package com.wisdom.mng.service;

import com.wisdom.mng.dao.BannerDao;
import com.wisdom.mng.dao.PartnerDao;
import com.wisdom.mng.entity.Banner;
import com.wisdom.mng.entity.Partner;
import com.wisdom.mng.entity.Result;
import com.wisdom.mng.entity.SysUser;
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
 * @create 2019-01-29 9:28
 * @desc
 */
@Service
public class PartnerService {
    @Autowired
    PartnerDao partnerDao;

    @Autowired
    private Environment env;

    public Page<Partner> findByAuto(Partner partner, Pageable pageable){
        return partnerDao.findByAuto(partner,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest, Partner partner){
        try{
            String partners = "";
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
                String key = (String) it.next();
                MultipartFile file = multipartRequest.getFile(key);
                if(file.getOriginalFilename().length() > 0){
                    String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String filename = System.currentTimeMillis()+suffix;
                    IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                    if(key.equals("uploadPartner")){
                        partners = env.getProperty("upload.url")+filename;
                    }
                }
            }
            if(StringUtils.isNotBlank(partners)){
                partner.setPartner(partners);
            }
            if(partner.getId() == null){
                partner.setPostStatus((short) 0);
                partner.setCreateDate(new Date());
                partner.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }else{
                Partner p = partnerDao.getOne(partner.getId());
                partner.setPostStatus(p.getPostStatus());
                UpdateTool.copyNullProperties(p, partner);
            }
            partnerDao.saveAndFlush(partner);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            partnerDao.deleteById(id);
        }
    }

    @Transactional
    public Result post(Short postStatus, Long partnerId) {
        Partner partner = partnerDao.getOne(partnerId);
        if(postStatus == 1){
            partner.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
            partner.setPostDate(new Date());
        }
        partner.setPostStatus(postStatus);
        partnerDao.saveAndFlush(partner);
        return ResultUtils.SUCCESS();
    }

    public List<Partner> findByPostStatus(Short postStatus){
        return partnerDao.findByPostStatus(postStatus);
    }
}
