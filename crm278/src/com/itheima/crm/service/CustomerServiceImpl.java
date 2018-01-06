 package com.itheima.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.crm.mapper.BaseDictMapper;
import com.itheima.crm.mapper.CustomerMapper;
import com.itheima.crm.pojo.BaseDict;
import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.utils.Page;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private BaseDictMapper baseDictMapper;
	@Autowired
	private CustomerMapper customerMapper;
	
	public List<BaseDict> findBaseDictListByTypeCode(String typeCode) {
		return baseDictMapper.findBaseDictListByTypeCode(typeCode);
	}
	
	@Override
	public Page<Customer> findCustomerByQueryVo(QueryVo queryVo) {
		//判断name是否为空
		if (queryVo.getCustName() != null) {
			queryVo.setCustName(queryVo.getCustName().trim());
		}
		Page<Customer> page = new Page<Customer>();
		//设置当前页
		page.setPage(queryVo.getPage());
		//每页显示的条数
		page.setSize(5);
		//总条数
		page.setTotal(customerMapper.countCustomerByQueryVo(queryVo));
		//设置queryVo显示的条数
		queryVo.setSize(page.getSize());
		//计算QueryVo中开始行
		queryVo.setStartRow((queryVo.getPage()-1)*queryVo.getSize());
		//结果集
		page.setRows(customerMapper.findCustomerListByQueryVo(queryVo));
		return page;
	}
	/**
	 * 删除
	 */
	@Override
	public void findCustomerByIdDelete(Integer id) {
		customerMapper.findCustomerByIdDelete(id);
	}

	@Override
	public Customer findCustomerByIdEdit(Integer id) {
		return customerMapper.findCustomerByIdEdit(id);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerMapper.updateCustomer(customer);
	}
}
