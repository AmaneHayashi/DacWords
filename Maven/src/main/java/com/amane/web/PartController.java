package com.amane.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amane.dto.Execution;
import com.amane.dto.Result;
import com.amane.entity.Collect;
import com.amane.entity.User;
import com.amane.enums.NormalStateEnum;
import com.amane.exception.DuplicateException;
import com.amane.exception.EmptyFieldException;
import com.amane.service.PartService;
import com.amane.service.impl.PartServiceImpl;
import com.amane.utils.BeanTools;

@Controller
@RequestMapping("/amane/part") // url:/模块/资源/{id}/细分 /seckill/list
public class PartController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//typeMap
	private final static Map<String, String> TYPE_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("210", "Test");
			put("220", "Question");
		}
	};

	@Autowired
	private PartService partService;
	
	private static List<JSONObject> queryList;
	/*
	 * @RequestMapping 来映射请求，也就是通过它来指定控制器可以处理哪些URL请求,类似于struts的action请求
	 * RequestMapping注解有六个属性，下面我们把她分成三类进行说明。 1、 value， method；
	 * value：指定请求的实际地址，指定的地址可以是URI Template 模式[/a为常数，/{a}中a为参数]； method：
	 * 指定请求的method类型，GET、POST、PUT、DELETE等；
	 * 
	 * 2、 consumes，produces；
	 * consumes：指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	 * produces:指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
	 * 
	 * 3、 params，headers； params： 指定request中必须包含某些参数值是，才让该方法处理请求。
	 * headers：指定request中必须包含某些指定的header值，才能让该方法处理请求。
	 * 
	 * @ResponseBody表示该方法的返回结果直接写入HTTP response body中
	 * 
	 * @PathVariable是用来获得请求url中的动态参数的(方法GET)
	 * 
	 * @RequestParam相当于request.getParameter()(方法POST)
	 */
	
	/**创作第一第二步的通用处理方式*/
	@RequestMapping(value = "/creation/step1", method = RequestMethod.POST, produces = {
	"application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> setStep1(@RequestParam("creationType") String creationType, HttpServletRequest request, 
			HttpSession session) throws Exception {
		try {
			//获得type
			String entityType = TYPE_MAP.get(creationType);
			//反射构造实例
			Class<?> clazz = Class.forName("com.amane.entity." + entityType);
			Object entity = clazz.newInstance();
			//添加属性值
			Map<String, String> map = BeanTools.mappedRequestParams(request);
			BeanTools.populate(entity, map);
			session.setAttribute("creator", entity);
			session.setAttribute("creationType", entityType.toLowerCase());
			return new Result<Execution<Integer>>(true, new Execution<Integer>(NormalStateEnum.SUCCESS, 1));
		} catch (Exception e) {
			//e.printStackTrace();
			return new Result<Execution<Integer>>(false, new Execution<Integer>(NormalStateEnum.INNER_ERROR, 0));
		}
	}
	
	@RequestMapping(value = "/creation/step2", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> setStep2(HttpServletRequest request, HttpSession session) throws Exception {
		Execution<Integer> execution = null;
		try {
			Map<String, String> map = BeanTools.mappedRequestParams(request);
			Object creator = session.getAttribute("creator");
			Object obj = creator;
			BeanTools.populate(obj, map);
			BeanUtils.copyProperties(creator, obj, BeanTools.getFitPropertyNames(creator, obj));
			System.out.println(creator);
			session.setAttribute("creator", creator);
			return new Result<Execution<Integer>>(true, new Execution<Integer>(NormalStateEnum.SUCCESS, 1));
		} catch (DuplicateException e1) {
			execution = new Execution<Integer>(NormalStateEnum.DUPLICATION);
		} catch (Exception e) {
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/creation/step2/pretask")
	private ModelAndView getInfos(HttpSession session, @RequestParam("kind") String kind, 
			@RequestParam("type") String type) throws Exception {
		ModelAndView mavs = new ModelAndView();
		//包括领域与学科,简写,规则
		Map<String, String> abbrMap = partService.getAbbr();
		mavs.addObject("abbr", JSON.toJSON(abbrMap));
		Map<Integer, String> qtMap = partService.getQTNavMap(kind);
		mavs.addObject("qtMap", JSON.toJSON(qtMap));
		Map<String, String> formatterMap = partService.getFormatter(kind);
		mavs.addObject("formatterMap", JSON.toJSON(formatterMap));
		mavs.setViewName("/creation/step2_template/" + type);
		session.setAttribute("qtMap", JSON.toJSON(qtMap));//用于addModal
		/**全局变量是否可以相互使用?*/
		return mavs;
	}
	
	/**查看菜单下栏目列表与详细条目的通用*/
	@RequestMapping(value = "/list")
	private ModelAndView getFirstList(@RequestParam("uid") String uid, @RequestParam("type") String type, 
			@RequestParam("kind") String kind, @RequestParam("page") int page, HttpServletResponse response) throws Exception {
		try {
			getList(uid, type, kind, page);
			ModelAndView mav = new ModelAndView();
			mav.addObject("list", queryList);
			mav.addObject("hasNext", queryList.size() == PartServiceImpl.LIST_LIMIT_MAP.get(type));
			mav.addObject("templateType", type);
			mav.setViewName("dtl/list_template/" + type);
			return mav;
		} catch(EmptyFieldException e1) {
			response.sendError(500);
		} catch(Exception e) {
			response.sendError(404);
		}
		return null;
	}

	@RequestMapping(value = "/detail")
	private ModelAndView getDetail(@RequestParam("type") String type, @RequestParam("id") String id, 
			HttpServletResponse response) throws Exception {
		ModelAndView mavs = new ModelAndView();
		try {
			mavs.addObject("dtl", queryList	.parallelStream()
											.filter(l -> l.get("id").toString().contentEquals(id))
											.findFirst()
											.get());
		} catch(Exception e) {
			try {
				mavs.addObject("dtl", new JSONObject(partService.queryDetail(type, id)));
			} catch(Exception e1) {
				response.sendError(404);
			}
		}
		mavs.setViewName("dtl/dtl_template/" + type);
		return mavs;
	}
	
	@RequestMapping(value = "/getNav", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Map<Integer, String>>> getNav() {
		Execution<Map<Integer, String>> execution = null;
		try {
			execution = partService.getNavMap();
			return new Result<Execution<Map<Integer, String>>>(true, execution);
		} catch (Exception e) {
			execution = new Execution<Map<Integer, String>>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Map<Integer, String>>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/getType", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<String>> getType(@RequestParam("listType") String listType) {
		Execution<String> execution = null;
		try {
			execution = partService.getType(listType);//此处传回的type是test不是t
			return new Result<Execution<String>>(true, execution);
		} catch(EmptyFieldException e) {
			execution = new Execution<String>(NormalStateEnum.EMPTY_FIELD);
		} catch (Exception e) {
			execution = new Execution<String>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<String>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/getItems", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> queryList(@RequestParam("kind") String kind, @RequestParam("originalTableName") String oTableName) {
		Execution<Integer> execution = null;
		String tableName = "c_" + oTableName;
		try {
			execution = partService.getItems(tableName, kind);//此处传回的type是test不是t
			return new Result<Execution<Integer>>(true, execution);
		} catch(EmptyFieldException e) {
			execution = new Execution<Integer>(NormalStateEnum.EMPTY_FIELD);
		} catch (Exception e) {
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/collect", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Integer>> doCollect(@RequestParam("type") String type, Collect c) {
		Execution<Integer> execution = null;
		try {
			execution = partService.collect(type, c);
			return new Result<Execution<Integer>>(true, execution);
		} catch(EmptyFieldException e) {
			execution = new Execution<Integer>(NormalStateEnum.EMPTY_FIELD);
		} catch (Exception e) {
			execution = new Execution<Integer>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Integer>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/myspace/creations", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Object>> getCreations(@RequestParam("name") String name) throws Exception {
		Execution<Object> execution = null;
		try {
			execution = partService.getCreations(name);
			return new Result<Execution<Object>>(true, execution);
		} catch(EmptyFieldException e) {
			execution = new Execution<Object>(NormalStateEnum.EMPTY_FIELD);
		}
		catch (Exception e) {
			execution = new Execution<Object>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Object>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/myspace/collections", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Object>> getCollections(@RequestParam("name") String name) throws Exception {
		Execution<Object> execution = null;
		try {
			execution = partService.getCollections(name);
			return new Result<Execution<Object>>(true, execution);
		} catch(EmptyFieldException e) {
			execution = new Execution<Object>(NormalStateEnum.EMPTY_FIELD);
		}
		catch (Exception e) {
			execution = new Execution<Object>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Object>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/blockquote", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Object>> getBlockquotes() throws Exception {
		Execution<Object> execution = null;
		try {
			execution = partService.getBlockQuotes();
			return new Result<Execution<Object>>(true, execution);
		} catch (Exception e) {
			execution = new Execution<Object>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Object>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/hotspot", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Object>> getHotspot() throws Exception {
		Execution<Object> execution = null;
		try {
			execution = partService.getHotspot();
			return new Result<Execution<Object>>(true, execution);
		} catch (Exception e) {
			execution = new Execution<Object>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Object>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/alert", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result<Execution<Object>> getAlerts(HttpSession hs) throws Exception {
		Execution<Object> execution = null;
		try {
			execution = partService.getAlerts((User)hs.getAttribute("user"));
			return new Result<Execution<Object>>(true, execution);
		} catch (Exception e) {
			execution = new Execution<Object>(NormalStateEnum.INNER_ERROR);
		}
		return new Result<Execution<Object>>(false, execution.getStateInfo());
	}
	
	@RequestMapping(value = "/error/{errorcode}", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	private ModelAndView getErrors(@PathVariable("errorcode") String code, HttpSession hs, HttpServletResponse resp) throws Exception {
		System.out.println("发生异常：" + code);
		ModelAndView mav = new ModelAndView();
		Object errors = hs.getAttribute("errors");
		if(errors == null){
			errors = partService.getErrors();
			hs.setAttribute("errors", errors);	
		} 
		mav.addObject("errorcode", code);
		mav.addObject("errors", errors);
		mav.setViewName("error/error");
		return mav;
	}
	
	//种类确定表名,类型确定哪些条目,偏置确定从哪开始取值
	private void getList(String uid, String type, String kind, int page) throws Exception {
		queryList = partService.getList(uid, type, kind, page);
	}
}