package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.dao.WisdomLifeDao;
import com.wisdom.mng.entity.SysUser;
import com.wisdom.mng.entity.WisdomLife;
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
                    if(key.equals("banner")){
                        banner += env.getProperty("upload.url")+filename+",";
                    }else if(key.equals("file")){
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
                wisdomLife.setCreateDate(new Date());
                wisdomLife.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }
            if(wisdomLife.getPostStatus() == 1){
                wisdomLife.setPostDate(new Date());
                wisdomLife.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
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

}
