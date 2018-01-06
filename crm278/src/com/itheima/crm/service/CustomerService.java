package com.itheima.crm.service;

import java.util.List;

import com.itheima.crm.pojo.BaseDict;
import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.utils.Page;

public interface CustomerService {

	//查询字典表  通过类型编号
	public List<BaseDict> findBaseDictListByTypeCode(String typeCode);

	public Page<Customer> findCustomerByQueryVo(QueryVo queryVo);

	public void findCustomerByIdDelete(Integer id);

	public Customer findCustomerByIdEdit(Integer id);

	public void updateCustomer(Customer customer);


}
