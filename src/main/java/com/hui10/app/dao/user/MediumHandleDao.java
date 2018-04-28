package com.hui10.app.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.user.MediumHandle;


public interface MediumHandleDao {
	
	public int saveMediumHandle(MediumHandle handle);
	
	public List<MediumHandle> queryHandles(@Param("uid")String uid, @Param("orderid")String orderid);
	
	public int updateMediumHandle(MediumHandle handle);
	
	public MediumHandle getById(String id);
    
   
}