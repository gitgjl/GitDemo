package com.itheima.crm.mapper;

import java.util.List;

import com.itheima.crm.pojo.BaseDict;

public interface BaseDictMapper {

	//查询字典表  通过类型编号
	public List<BaseDict> findBaseDictListByTypeCode(String typeCode);
}
