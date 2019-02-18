package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.dao.ProjectDao;
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
                    if(key.equals("uploadBanner")){
                        banner += env.getProperty("upload.url")+filename+",";
                    }else if(key.equals("uploadFile")){
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
                project.setVisits(0);
                project.setPostStatus((short) 0);
                project.setPushStatus((short) 0);
                project.setCreateDate(new Date());
                project.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }else{
                Project p = projectDao.getOne(project.getId());
                project.setPostStatus(p.getPostStatus());
                project.setPushStatus(p.getPushStatus());
                UpdateTool.copyNullProperties(p, project);
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

    @Transactional
    public Result post(Short postStatus, Long projectId) {
        Project project = projectDao.getOne(projectId);
        if(postStatus == 1){
            Category category = project.getCategory();
            if(category == null){
                return ResultUtils.DATA("该文章未选择栏目",ResultUtils.RESULT_ERROR_CODE,null);
            }
            List<Project> byPostStatus = projectDao.findByPostStatusAndCategory(postStatus,category);
            if(byPostStatus != null && byPostStatus.size() > 0){
                return ResultUtils.DATA(category.getName()+"栏目只能发布一篇文章",ResultUtils.RESULT_ERROR_CODE,null);
            }
            project.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
            project.setPostDate(new Date());
        }
        project.setPostStatus(postStatus);
        projectDao.saveAndFlush(project);
        return ResultUtils.SUCCESS();
    }

    public List<Project> findAllByAuto(Project project) {
        return projectDao.findAllByAuto(project);
    }

    @Transactional
    public void saveVisits(Long id){
        Project project = projectDao.getOne(id);
        project.setVisits(project.getVisits()+1);
        projectDao.saveAndFlush(project);
    }

    public Project getOne(Long id){
        return projectDao.getOne(id);
    }
}
