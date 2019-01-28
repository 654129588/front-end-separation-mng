package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.dao.ProductDao;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-22 9:43
 * @desc
 */
@Service
public class ProductService {
    @Autowired
    ProductDao productDao;

    @Autowired
    private Environment env;

    public Page<Product> findByAuto(Product product, Pageable pageable){
        return productDao.findByAuto(product,pageable);
    }

    @Transactional
    public void saveOrUpdate(MultipartHttpServletRequest multipartRequest, Product product){
        try{
            String banner = "";
            String machine = "";
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
                String key = (String) it.next();
                MultipartFile file = multipartRequest.getFile(key);
                if(file.getOriginalFilename().length() > 0){
                    String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String filename = System.currentTimeMillis()+suffix;
                    IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                    if(key.equals("uploadMachine")){
                        machine += env.getProperty("upload.url")+filename+",";
                    }
                    if(key.equals("uploadBanner")){
                        banner += env.getProperty("upload.url")+filename+",";
                    }
                }
            }
            if(StringUtils.isNotBlank(banner)){
                product.setBanner(banner);
            }
            if(StringUtils.isNotBlank(machine)){
                product.setMachine(machine);
            }
            if(product.getId() == null){
                product.setPostStatus((short) 0);
                product.setPushStatus((short) 0);
                product.setCreateDate(new Date());
                product.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }else{
                Product p = productDao.getOne(product.getId());
                product.setPostStatus(p.getPostStatus());
                product.setPushStatus(p.getPushStatus());
                UpdateTool.copyNullProperties(p, product);
            }
            productDao.saveAndFlush(product);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        for (Long id:ids) {
            productDao.deleteById(id);
        }
    }

    @Transactional
    public Result post(Short postStatus, Long productId) {
        Product product = productDao.getOne(productId);
        if(postStatus == 1){
            Category category = product.getCategory();
            if(category == null){
                return ResultUtils.DATA("该文章未选择栏目",ResultUtils.RESULT_ERROR_CODE,null);
            }
            product.setPostDate(new Date());
            product.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
        }else{
            product.setPushStatus(postStatus);
        }
        product.setPostStatus(postStatus);
        productDao.saveAndFlush(product);
        return ResultUtils.SUCCESS();
    }

    @Transactional
    public Result push(Short pushStatus, Long productId) {
        Product product = productDao.getOne(productId);
        if(pushStatus == 1){
            List<Product> byPushStatus = productDao.findByPushStatus(pushStatus);
            if(byPushStatus != null && byPushStatus.size() >= 4){
                return ResultUtils.DATA("本栏目推送首页文章不能超过4篇",ResultUtils.RESULT_ERROR_CODE,null);
            }
        }
        product.setPushStatus(pushStatus);
        productDao.saveAndFlush(product);
        return ResultUtils.SUCCESS();
    }

    public List<Product> findAllByAuto(Product product){
        return productDao.findAllByAuto(product);
    }
}
