package com.wisdom.mng.service;

import com.wisdom.mng.dao.ArticleDao;
import com.wisdom.mng.dao.ProductDao;
import com.wisdom.mng.entity.Product;
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
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
                String key = (String) it.next();
                MultipartFile file = multipartRequest.getFile(key);
                if(file.getOriginalFilename().length() > 0){
                    String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String filename = System.currentTimeMillis()+suffix;
                    IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                    if(key.equals("banner")){
                        banner += env.getProperty("upload.url")+filename+",";
                    }
                }
            }
            if(StringUtils.isNotBlank(banner)){
                product.setBanner(banner);
            }
            if(product.getId() == null){
                product.setCreateDate(new Date());
                product.setCreateUser(new SysUser(UserUtils.getSysUser().getId()));
            }
            if(product.getPostStatus() == 1){
                product.setPostDate(new Date());
                product.setPostUser(new SysUser(UserUtils.getSysUser().getId()));
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
}
