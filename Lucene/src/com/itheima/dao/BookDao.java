package com.itheima.dao;

import java.util.List;

import com.itheima.pojo.Book;

public interface BookDao {
	
	List<Book> queryBookList();
}
