package org.wangtianyu.userPlatform.Utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@PropertySource("classpath:Secret/OssSecret.properties")
public class FileOSSUploadUtil {
    @Value("${oss.endpoint}")
    private String endPoint;
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secret}")
    private String accessKeySecret;


    public void uploadFile(MultipartFile file) {
        String endpoint = this.endPoint;
        String accessKeyId = this.accessKey;
        String accessKeySecret = this.accessKeySecret;

        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            if (file.getSize() != 0 && !"".equals(file.getName())) {
                // getInputStream()返回一个InputStream以从中读取文件的内容。通过此方法就可以获取到流
                InputStream multipartFileInputStream = file.getInputStream();
                PutObjectRequest putObjectRequest = new PutObjectRequest("gench-donate-platform", "img/"+file.getOriginalFilename(), multipartFileInputStream);
                oss.putObject(putObjectRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            oss.shutdown();
        }
    }
}
