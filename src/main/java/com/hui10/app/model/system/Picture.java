package com.hui10.app.model.system;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2017年10月26日 18:02
 */
public class Picture {

    private String id;

    private byte[] picture;

    @NotBlank
    private String md5;

    private MultipartFile picturefile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public MultipartFile getPicturefile() {
        return picturefile;
    }

    public void setPicturefile(MultipartFile picturefile) {
        this.picturefile = picturefile;
    }
}
