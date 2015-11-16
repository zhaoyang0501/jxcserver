package com.pzy.controller.front;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.Category;
import com.pzy.entity.Record;
import com.pzy.service.CategoryService;
import com.pzy.service.RecordService;
/***
 * @author 263608237@qq.com
 *
 */
@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RecordService recordService;
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	@RequestMapping(value = "categorys")
	@ResponseBody
	public List<Category> categorys( ) {
		return categoryService.findAll();
	}
	
	/***
	 * 入库操作
	 * @param cid
	 * @param num
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "buy")
	@ResponseBody
	public Map<String,String> buy(Long cid,Integer num,Double price ) {
		Category category=categoryService.find(cid);
		category.setNum(category.getNum()+num);
		categoryService.save(category);
		Record record=new Record();
		record.setCategory(category);
		record.setNum(num);
		record.setPrice(price);
		record.setType("入库");
		record.setCreateDate(new Date());
		recordService.save(record);
		Map<String,String> map=new HashMap<String,String>();
		map.put("tip", "入库成功！");
		return  map;
	}
	
	
	/***
	 * 出库操作
	 * @param cid
	 * @param num
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "sell")
	@ResponseBody
	public Map<String,String> sell(Long cid,Integer num,Double price ) {
		Category category=categoryService.find(cid);
		Map<String,String> map=new HashMap<String,String>();
		if(category.getNum()<num){
			map.put("tip", "库存不足！");
			return map;
		}
		category.setNum(category.getNum()-num);	
		categoryService.save(category);
		Record record=new Record();
		record.setCategory(category);
		record.setNum(num);
		record.setPrice(price);
		record.setType("出库");
		record.setCreateDate(new Date());
		recordService.save(record);
		map.put("tip", "出库成功！");
		return  map;
	}
	/***
	 * 查交易明细
	 * @param s
	 * @param e
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public List<Record> list(String s,String e ) {
		return recordService.findAll();
	}
}

