package com.pzy.controller.front;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pzy.entity.Category;
import com.pzy.entity.Record;
import com.pzy.entity.User;
import com.pzy.service.CategoryService;
import com.pzy.service.RecordService;
import com.pzy.service.UserService;
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
	@Autowired
	private UserService userService;
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	@RequestMapping(value = "categorys")
	@ResponseBody
	public List<Category> categorys( ) {
		return categoryService.findAll();
	}
	@RequestMapping(value = "categorys/{id}")
	@ResponseBody
	public List<Category> categorys( @PathVariable Long id) {
		List list= new ArrayList<Category>();
		if(id==null)
			return categoryService.findAll();
		else {
			list.add(categoryService.find(id));
			return list;
		}
	}
	@RequestMapping(value = "category/{id}")
	@ResponseBody
	public Category category( @PathVariable Long id) {
		if(id==null)
			return null;
		else {
			return categoryService.find(id);
		}
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
	public Map<String,String> buy(Long cid,Integer num,Double price,Long userid ) {
		Category category=categoryService.find(cid);
		User user=userService.find(userid);
		category.setNum(category.getNum()+num);
		categoryService.save(category);
		Record record=new Record();
		record.setUser(user);
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
	public Map<String,String> sell(Long cid,Integer num,Double price,Long userid  ) {
		Category category=categoryService.find(cid);
		User user=userService.find(userid);
		Map<String,String> map=new HashMap<String,String>();
		if(category.getNum()<num){
			map.put("tip", "库存不足！");
			return map;
		}
		category.setNum(category.getNum()-num);	
		categoryService.save(category);
		Record record=new Record();
		record.setUser(user);
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
	 * @throws ParseException 
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public List<Record> list(String b,String e,String t ) throws ParseException {
		Date sdate=DateUtils.parseDate(b, "yyyy-MM-dd");
		Date edate=DateUtils.parseDate(e, "yyyy-MM-dd");
		String type=null;
		
		if("1".equals(t)) type="入库";
		if("2".equals(t)) type="出库";
		
		List<Record> list= recordService.findAll(sdate,edate, type);
		return list;
	}
	
	@RequestMapping(value = "login")
	@ResponseBody
	public Map<String,Object> login(String username,String password ) {
		Map<String,Object> map= new HashMap<String,Object>();
		User  user= userService.find(username, password);
		if(user==null){
			map.put("code", "404");
		}else{
			map.put("code", "200");
			map.put("user", user);
		}
		return map;
		
	}
}

