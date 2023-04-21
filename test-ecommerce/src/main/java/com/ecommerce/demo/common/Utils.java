package com.ecommerce.demo.common;

public class Utils {

	public static Boolean CheckNullString(Object obj) {
		if (obj != null && obj.toString() != "")
			return true;
		return false;
	}
	
	public static String likeText(String obj) {
		if(obj == "" || obj == null) return "";
		return "%" + obj.toUpperCase() + "%";
	}

	public Utils() {
		// TODO Auto-generated constructor stub
	}

}
