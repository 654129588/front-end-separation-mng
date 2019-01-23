package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.dao.ProjectDao;
import com.wisdom.mng.entity.Article;
import com.wisdom.mng.entity.Project;
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
 * @create 2019-01-23 10:28
 * @desc
 */
@Service
public class ProjectService {
    @Autowired
    ProjectDao projectDao;

    @Autowired
    private Environment env;

    public Page<Project> findByAuto(Project project, Pageable pageable){
        return projectDao.findByAuto(project,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest, Project project){
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
                project.setBanner(banner);
            }
            if(StringUtils.isNotBlank(files)){
                project.setFile(files);
            }
            if(project.getId() == null){
                project.setCreateDate(new Date());
                project.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }
            if(project.getPostStatus() == 1){
                project.setPostDate(new Date());
                project.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
            }
            projectDao.saveAndFlush(project);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            projectDao.deleteById(id);
        }
    }
}
