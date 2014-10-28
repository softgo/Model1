package com.bruce.gogo.utils;

import java.util.LinkedList;

/**
 * @author chenbw
 */
public class StringUtil {


	/**
	 * 使HTML的标签失去作用
	 *
	 * @param input
	 *            被操作的字符串
	 * @return String
	 */
	public static final String escapeHTMLTag(String input) {
		if (input == null) {
			return "";
		}
		input = input.trim().replaceAll("&", "&amp;");
		input = input.trim().replaceAll("<", "&lt;");
		input = input.trim().replaceAll(">", "&gt;");
		input = input.trim().replaceAll("\t", "    ");
		input = input.trim().replaceAll("\r\n", "\n");
		input = input.trim().replaceAll("\n", "<br>");
		input = input.trim().replaceAll("  ", " &nbsp;");
		input = input.trim().replaceAll("'", "&#39;");
		input = input.trim().replaceAll("\\\\", "&#92;");
		input = input.trim().replaceAll("<br>", "\n");
		return input;
	}

	public static String cleanHtmlTag(String htmlText) {
		String reg = "</?[a-z][a-z0-9]*[^<>]*>?";
		return htmlText.replaceAll(reg, "");
	}

	private StringUtil() {

	}
	//求两个数组的差集      
    public static String minus(String[] arr1, String[] arr2) {      
        LinkedList<String> list = new LinkedList<String>();      
        for (String str : arr2) {      
                list.add(str);      
        }      
        for (String str : arr1) {      
            if (list.contains(str)) {      
                list.remove(str);      
            }     
        } 
        if (list.size() == 0) {
        	return "";
        } else {
        	StringBuffer buf = new StringBuffer();
        	for (String str : list) {
        		buf.append(",");
        		buf.append(str);
        	}
        	return buf.toString();
        }
    } 
  //求两个数组的差集      
    public static String minus(String[] arr1, String[] arr2,String split) {      
        LinkedList<String> list = new LinkedList<String>();      
        for (String str : arr2) {      
                list.add(str);      
        }      
        for (String str : arr1) {      
            if (list.contains(str)) {      
                list.remove(str);      
            }     
        } 
        if (list.size() == 0) {
        	return "";
        } else {
        	StringBuffer buf = new StringBuffer();
        	for (String str : list) {
        		buf.append(split);
        		buf.append(str);
        	}
        	return buf.toString();
        }
    } 
    
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
    	if ( str != null && str.trim().length() > 0 && !str.equals("") && !str.equals("“”")) {
    		str = str.trim();
    		if (!str.equalsIgnoreCase("null")) {
    			return true;
			}else {
				return false;
			}
    	}
    	return false;
    }
    
    
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotBlank1(String str) {
    	if (str==null ) {
			return false;
		}else if ( str.trim().length()>0) {
    		return true;
    	}else if (str.equals(" ")) {
			return true;
		}else {
			return false;
		}
    }
    
    
    public static String removeSpaceAndComma(String str){
    	StringBuffer buf = new StringBuffer();
    	if (isNotBlank(str)) {
    		while(true){
    			if (str.length() >= 1) {
    				if (str.charAt(str.length()-1) == ',') {
    					str = str.substring(0,str.length()-1);
    				} else {
    					break;
    				}
    			} else {
    				break;
    			}
    		}
    		buf.append(str);
    	}  else {
    		buf.append("");
    	}
    	return buf.toString();
    }
    public static String removeSpaceAndSemicolon(String str){
    	StringBuffer buf = new StringBuffer();
    	if (isNotBlank(str)) {
    		while(true){
    			if (str.length() >= 1) {
    				if (str.charAt(str.length()-1) == ';') {
    					str = str.substring(0,str.length()-1);
    				} else {
    					break;
    				}
    			} else {
    				break;
    			}
    		}
    		buf.append(str);
    	}  else {
    		buf.append("");
    	}
    	return buf.toString();
    }
//    /**
//     * 映射表关系使用，将拼好的关系重新打乱，并把当同key的value组装
//     * @param oldStr 原字符串
//     * @param sp 分隔符，原值为\r\n
//     * @param sp_f 分号分隔符 ；
//     * @param sp_e 等号 =
//     * @return
//     */
//    public static String convert_mapRelation_str(String oldStr,String sp,String sp_f,String sp_e) {
//    	String[] arr = oldStr.split(sp);
//    	List<String> list = new ArrayList<String>();
//    	Map<String,String> map = new HashMap<String,String>();
//    	for (int i = 0; i < arr.length; i++) {
//    		list.add(arr[i]);
//    	}
//    	for (String str : list) {
//    		String[] record = str.split("=");
//    		String key = record[0];
//    		String value= record[1];
//    		if (map.containsKey(key)) {
//    			value += sp_f + map.get(key);
//    		} 
//    		map.put(key, value);
//    	}
//    	Set<String> s = map.keySet();
//    	String newStr = "";
//    	for (String key : s) {
//    		newStr += key + sp_e + map.get(key) + sp;
//    	}
//    	return newStr;
//    }
    public static void main(String[] args) {
//		String str = "aa,";
//		str = removeSpaceAndComma(str);
//		System.out.println(str);
//    	String str = "a=a\r\na=b\r\nb=a";
//    	String newStr = convert_mapRelation_str(str,"\r\n",";","=");
//    	System.out.println(newStr);
	}
}
