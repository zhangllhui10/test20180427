package com.hui10.app.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.system.Picture;



/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2017年10月26日 17:57
 */
public interface PictureDao {

    int insertPictureInfo(Picture picture);

    String selectPictureId(@Param("md5") String md5);
    
    List<Picture> selectPictureInfoById(@Param("ids")String[] ids);
    
    Integer insertPictureList(List<Picture> list);
    //根据Picture查询列表
    List<Picture> queryListById(List<Picture> list );
}
