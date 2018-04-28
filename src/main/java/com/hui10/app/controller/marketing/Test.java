package com.hui10.app.controller.marketing;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.common.utils.StringUtils;

/**
 * @ClassName: Test.java
 * @Description:
 * @author zhangll
 * @date 2018年2月26日 下午3:08:09
 */
public class Test {

	public static void main(String[] args) {
		/*
		 * String a = ""; String b = new String(); string_equal(a,b);
		 */
		//generate_json();
		//test_arrayList_subList();
		//test_list_to_array();
		array_copy();
		
	}
	static void array_copy(){
		String[] str = new String[]{"1","2","4"};
		String[] str1 = new String[str.length];
		System.arraycopy(str, 0, str1, 0, str.length);
		for(String s:str1){
			System.out.println(s);
		}
		
	}
	
	static void test_list_to_array(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("vd");
		list.add("qwe3");
		list.add("d3");
		String[] str = new String[list.size()];
		str = list.toArray(str);
		for(String s:str){
			System.out.println(s);
		}
	}
	
	public static void test_arrayList_subList() {
		List<String> list = new ArrayList<String>();
		List<String> subList = new ArrayList<String>();
		list.add("a");
		list.add("vd");
		list.add("qwe3");
		list.add("d3");

		list.subList(0, 2);
	}

	// 营销活动接入方式的json格式
	private static void generate_json() {

		JSONObject object = new JSONObject();
		object.put("gatewayid", "201803100446287615360000005264");
		object.put("channelid", "201803100446287615360000005270");
		object.put("merchantname", "POS机售彩-POS机售彩");
		object.put("money", 20);
		object.put("betnumber", 1);
		object.put("citys", "1201,1301");
		object.put("merchants", "UH99111371,UH99708520");

		JSONObject object_2 = new JSONObject();
		object_2.put("gatewayid", "201803100446287615360000005265");
		object_2.put("channelid", "201803100446287615360000005271");
		object_2.put("merchantname", "汇彩宝售彩-汇彩宝售彩");
		object_2.put("money", 20);
		object_2.put("betnumber", 1);
		object_2.put("citys", "");
		object_2.put("merchants", "");

		JSONArray array = new JSONArray();
		array.add(object);
		array.add(object_2);
		System.out.println(array.toJSONString());
	}

	private static void aa() {
		String groupcode_letter = "0000003587";
		String str = null;
		char gp = groupcode_letter.charAt(0);
		if (gp == '0') {
			str = groupcode_letter.replaceFirst("0", "A");
		}
		System.out.println(groupcode_letter);
		System.out.println(str);
	}

	private static void string_equal(String a, String b) {
		System.out.println(StringUtils.equals(a, b));
	}

}
