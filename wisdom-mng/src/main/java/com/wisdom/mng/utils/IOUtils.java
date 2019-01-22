package com.wisdom.mng.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/***
 * @author CHENWEICONG
 * @create 2019-01-21 11:44
 * @desc IO工具类
 */
public class IOUtils {

    /**
     * 保存文件
     * @param stream stream
     * @param path  路径
     * @param filename  文件名
     * @param buff byte[]
     * @return
     * @throws IOException
     */
    public static File saveFileFromInputStream(InputStream stream, String path, String filename, byte[] buff)
            throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + filename);
        FileOutputStream fs = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 1024];
        int byteread = 0;
        if (null == buff || buff.length <= 0) {
            while ((byteread = stream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } else {
            fs.write(buff);
        }
        fs.flush();
        fs.close();
        if (null != stream) {
            stream.close();
        }
        return file;
    }
}
