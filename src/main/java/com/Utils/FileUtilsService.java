package com.Utils;

import com.config.BeanConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

public class FileUtilsService implements WebMvcConfigurer {

    public static String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().replaceAll("\\s{2,}", " ");
        StringBuffer folder = new StringBuffer(BeanConfiguration.FOLDER_SYS);

        File refreshFolder = new File(folder.toString());
        if (!refreshFolder.exists())
            refreshFolder.mkdirs();
        String uploadFile = folder + fileName;
        int i = 0;
        while (true) {
            if (!new File(uploadFile).exists())
                break;
            else
                uploadFile = folder + String.valueOf(i++).concat("-").concat(fileName);
        }
        file.transferTo(new File(uploadFile));

        uploadFile = uploadFile.replace(BeanConfiguration.FOLDER_SYS, "");
        System.out.println("uploadFile");
        System.out.println(uploadFile);
        return BeanConfiguration.HOST + uploadFile;
    }

    public static void deleteFile(String file) {
        if (file == null) return;
        new File(file.replace(BeanConfiguration.HOST, BeanConfiguration.FOLDER_SYS)).delete();
    }

}
