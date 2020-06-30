package com.amane.utils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.amane.entity.Test;

public class BeanTools {

    public static List<String> getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
								.map(PropertyDescriptor::getName)
								.filter(s -> isZero(src.getPropertyValue(s)))
								.collect(Collectors.toList());
    }
    
    public static List<String> getNotNullPropertyNames(Object target) {
        final BeanWrapper src = new BeanWrapperImpl(target);
        return Arrays.stream(src.getPropertyDescriptors())
								.map(PropertyDescriptor::getName)
								.filter(s -> !isZero(src.getPropertyValue(s)))
								.collect(Collectors.toList());
    }

    public static String[] getFitPropertyNames(Object source, Object target) {
    	List<String> propList = getNullPropertyNames(source);
    	propList.addAll(getNotNullPropertyNames(target));
    	return propList	.parallelStream()
    					.distinct()
    					.collect(Collectors.toList())
    					.toArray(new String[propList.size()]);
    }
    
    private static boolean isZero(Object src) {
    	try {
    		return src == null || Double.parseDouble(src.toString()) == 0;
    	} catch(Exception e) {
    	//	//e.printStackTrace();
    		return false;
    	}
    }
    
    public static <T> List<T> getSpecialProperties(Map<T, ?> map, Class<?>... requiredType){
		List<T> list = new ArrayList<T>();
		map.entrySet().forEach(e -> {
			if(Arrays.stream(requiredType).anyMatch(c -> c.isInstance(e.getValue()))) {
				list.add(e.getKey());
			}
		});
		return list;
    }
    
    public static Map<String, String> mappedRequestParams(HttpServletRequest request) throws UnsupportedEncodingException{
    	Map<String, String> paramMap = new HashMap<String, String>();
		Enumeration<String> paramsEnum = request.getParameterNames();
		while(paramsEnum.hasMoreElements()) {
			String param = paramsEnum.nextElement();
			paramMap.put(param, URLDecoder.decode(request.getParameter(param), "utf-8"));
		}
		return paramMap;
    }
    
    public static void populate(final Object bean, final Map<String, ? extends Object> properties) 
    		throws IllegalAccessException, InvocationTargetException {
    	BeanUtils.populate(bean, properties);
    }
    
    /**
     * @
     * @param <T>
     * @param mapList
     * @return
     */
	public static <T> Object datify(List<Map<T, Object>> mapList) {
		List<T> dateList = BeanTools.getSpecialProperties(mapList.get(0), Date.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapList.forEach(l -> {
			dateList.forEach(dl -> {
				l.put(dl, dateFormat.format(l.get(dl)));
			});
		});
		//assert dateList.isEmpty();
		return JSON.toJSON(mapList);
	}
    
    public static void main(String[] args) {
    	Test test = new Test();
    	getNullPropertyNames(test);
    }
}
