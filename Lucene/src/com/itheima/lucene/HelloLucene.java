package com.itheima.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.itheima.dao.BookDao;
import com.itheima.dao.BookDaoImpl;
import com.itheima.pojo.Book;

public class HelloLucene {
	
	@Test
	public void testCreateIndex() throws IOException{
//		1.采集数据
		BookDao bookDao = new BookDaoImpl();
		List<Book> bookList = bookDao.queryBookList();
//		2.创建Document文档对象
		List<Document> documents = new ArrayList<>();
		for (Book book : bookList) {
			Document document = new Document();
			//在document中添加field域
			//图书Id
			//Store.YES:表示存储到文档域中
			Field idField = new TextField("id",book.getId().toString(), Store.YES);
			//图书名称
//			private String name;
			Field nameField = new TextField("name",book.getName(), Store.YES);
			// 图书价格
//			private Float price;
			Field priceField = new TextField("price",book.getPrice().toString(), Store.YES);
			// 图书图片
//			private String pic;
			Field picField = new TextField("pic",book.getPic(), Store.YES);
//			 图书描述
//			private String desc;
			Field descField = new TextField("desc",book.getDesc(), Store.NO);
			//将域添加到文档中
			document.add(idField);
			document.add(nameField);
			document.add(priceField);
			document.add(picField);
			document.add(descField);
			//把document放到list中
			documents.add(document);
		}
//		3.创建分析器（分词器）
//		Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
//		4.创建IndexWriterConfig配置信息类,写入索引需要的配置
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
//		5.创建Directory对象(目录)，声明索引库存储位置
		Directory directory = FSDirectory.open(new File("D:\\temp\\index"));
//		6.创建IndexWriter写入对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
//		7.把Document写入到索引库中
		for (Document document : documents) {
			indexWriter.addDocument(document);
		}
//		8.释放资源
		indexWriter.close();
	}
//	 1. 创建Query搜索对象
//	 2. 创建Directory流对象,声明索引库位置
//	 3. 创建索引读取对象IndexReader
//	 4. 创建索引搜索对象IndexSearcher
//	 5. 使用索引搜索对象，执行搜索，返回结果集TopDocs
//	 6. 解析结果集
//	 7. 释放资源
	@Test
	public void testSearchIndex() throws Exception {
		//创建索引
		Query query = new TermQuery(new Term("name","lucene"));
		
		//索引库 位置对象
		Directory directory = FSDirectory.open(new File("D:\\temp\\index"));
		//执行查询
		IndexReader indexReader = DirectoryReader.open(directory);
		//搜索
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//执行  5条的文档ID
		TopDocs topDocs = indexSearcher.search(query, 5);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docID = scoreDoc.doc;
			Document doc = indexSearcher.doc(docID);
			System.out.println("ID:" + doc.get("id"));
			System.out.println("名称:" + doc.get("name"));
			System.out.println("价格:" + doc.get("price"));
			System.out.println("图片:" + doc.get("pic"));
			System.out.println("描述:" + doc.get("desc"));
		}
		indexReader.close();
	}
}
