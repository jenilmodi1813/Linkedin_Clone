package com.linkedin.Post_Service.config;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Component
public class FileUploaderHelper {

    public final String UPLOAD_DIR="C:\\Spring_boot\\New_LinkedIn_clone\\UserPost";

    public boolean uploadFile(MultipartFile file){
        boolean flag = false;
        try{
            InputStream inputStream = file.getInputStream();
            byte data[] = new byte[inputStream.available()];
            inputStream.read(data);

            FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+ File.separator+file.getOriginalFilename());
            fos.write(data);
            fos.flush();
            fos.close();

//            Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            flag = true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
