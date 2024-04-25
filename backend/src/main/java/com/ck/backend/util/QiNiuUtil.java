package com.ck.backend.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 02
 * &#064;date  2024/3/20-8:49
 * @version 1.0
 * &#064;description
 */
@Service
public class QiNiuUtil {

    //...生成上传凭证，然后准备上传
    static String accessKey = "QAer-QL4T_p0RTLGZmaNgytdZaRk8zf-ajmXnm2J";
    static String secretKey = "fmpaLoSPE2wJdY2D2upI6rgPz38nf4f7LRhaPrdt";
    public static Boolean upload(MultipartFile file) {


        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);


        String bucket = "cwf-ck";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //String localFilePath = "D:\\geek.exe";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        //String key = "geek.exe";
        System.out.println(file.getOriginalFilename());
        System.out.println("----------------------------");
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getInputStream(), file.getOriginalFilename(), upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);
                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static String download(String key) throws QiniuException {
        String domain = "store.sherry.games";
        boolean useHttps = false;
//        String key = "geek.exe";
        DownloadUrl url = new DownloadUrl(domain, useHttps, key);
        return url.buildURL();
    }
}
