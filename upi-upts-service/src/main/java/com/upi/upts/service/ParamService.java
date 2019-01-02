package com.upi.upts.service;

import java.util.List;

import com.upi.upts.model.Param;

public interface ParamService {
	public void insert(Param param);
	public void update(Param param);
	public List<Param> getAllParam();
}
