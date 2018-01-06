package com.itheima.crm.mapper;

import java.util.List;

import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;

public interface CustomerMapper {

	int countCustomerByQueryVo(QueryVo queryVo);

	List<Customer> findCustomerListByQueryVo(QueryVo queryVo);

	void findCustomerByIdDelete(Integer id);

	Customer findCustomerByIdEdit(Integer id);

	void updateCustomer(Customer customer);

}
