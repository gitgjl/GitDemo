package com.itheima.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itheima.crm.pojo.BaseDict;
import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.service.CustomerService;
import com.itheima.crm.utils.Page;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	//获取配置文件的参数
	@Value(value="${crm.fromType}")
	private String ft;
	@Value(value="${crm.industryType}")
	private String it;
	@Value(value="${crm.levelType}")
	private String lt;
	/**
	 * 分页查询
	 * @param model
	 * @param queryVo
	 * @return
	 */
	@RequestMapping(value="/customer/list.action")
	public String list(Model model,QueryVo queryVo){
		//客户来源
		List<BaseDict> fromType = customerService.findBaseDictListByTypeCode(ft);
		//客户行业
		List<BaseDict> industryType = customerService.findBaseDictListByTypeCode(it);
		//所属级别
		List<BaseDict> levelType = customerService.findBaseDictListByTypeCode(lt);
		//把属性放入域对象中
		model.addAttribute("fromType", fromType);
		model.addAttribute("industryType", industryType);
		model.addAttribute("levelType", levelType);
		
		//更据queryVo里面的条件查询客户
		Page<Customer> page = customerService.findCustomerByQueryVo(queryVo);
		model.addAttribute("page", page);
		//回显条件
		model.addAttribute("custName", queryVo.getCustName());
		model.addAttribute("custSource", queryVo.getCustSource());
		model.addAttribute("custIndustry", queryVo.getCustIndustry());
		model.addAttribute("custLevel", queryVo.getCustLevel());
		return "customer";
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/customer/delete.action")
	public @ResponseBody
	String delete(Integer id){
		customerService.findCustomerByIdDelete(id);
		return "ok";
	}
	
	@RequestMapping(value="customer/edit.action")
	public @ResponseBody
	Customer edit(Integer id){
		Customer customer = customerService.findCustomerByIdEdit(id);
		return customer;
	}
	
	@RequestMapping(value="customer/update.action")
	public @ResponseBody 
	String update(Customer customer){
		customerService.updateCustomer(customer);
		return "ok";
	}
}
