package com.bruce.gogo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

public class XMLUtil {
	protected Log logger = LogFactory.getLog(getClass());
	private static List<Leaf> elemList = new ArrayList<Leaf>();
	
	@SuppressWarnings("unchecked")
	public String convertDataInfo(Document doc, Map<String, String> map) {
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			List list = doc.selectNodes(key);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i ++ ) {
					Element element = (Element) list.get(i);
					element.setText("");
					element.addCDATA(map.get(key));
//					element.setText("<![CDATA[" + map.get(key) + "]]>");
				}
			}
		}
		logger.info(doc.asXML());
		return doc.asXML().substring(doc.asXML().indexOf("<display>"));
	}
	
	public String parse4Input(Document doc) {
		StringBuffer buff = new StringBuffer();
		Element root = (Element) doc.getRootElement();
		elemList = new ArrayList<Leaf>();
		getElementList(root);
        String x = getListString(elemList);
        buff.append(x);
		return buff.toString();
	}
	
	public void getElementList(Element element) {
        List elements = element.elements();
        if (elements.size() == 0) {
            //没有子元素
            String xpath = element.getPath();
            String value = element.getTextTrim();
            elemList.add(new Leaf(xpath, value));
        } else {
            //有子元素
            for (Iterator it = elements.iterator(); it.hasNext();) {
                Element elem = (Element) it.next();
                //递归遍历
                getElementList(elem);
            }
        }
    }
    public static String getListString(List<Leaf> elemList) {
        StringBuffer sb = new StringBuffer();
        for (Iterator<Leaf> it = elemList.iterator(); it.hasNext();) {
            Leaf leaf = it.next();
//            sb.append(leaf.getXpath()).append(" = ").append(leaf.getValue()).append("<br/>");
            sb.append("<tr>");
            sb.append("<td align=\"right\">").append(leaf.getValue()).append(" : ").append("</td>");
            sb.append("<td align=\"left\">");
            sb.append("<input name=\"").append(leaf.getXpath()).append("\" class=\"inputLongTxt\" size=\"50\"/>");
            sb.append("</td>");
            sb.append("</tr>");
        }
        return sb.toString();
    }
	
	public class Leaf {
		private String xpath;         //
	    private String value;
	    public Leaf(String xpath, String value) {
	        this.xpath = xpath;
	        this.value = value;
	    }
	    public String getXpath() {
	        return xpath;
	    }
	    public void setXpath(String xpath) {
	        this.xpath = xpath;
	    }
	    public String getValue() {
	        return value;
	    }
	    public void setValue(String value) {
	        this.value = value;
	    }
	}
}
