package com.hui10.app.common.pay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.exception.CommonException;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2017年09月28日 14:57
 */
public class CMBCUtil {

    public static SortedMap<String, String> CMBCMap = doXMLParse(Constants.CMBC);

    public static SortedMap<String, String> doXMLParse(String fileName) {

        if (StringUtils.isNotBlank(fileName)) {
            TreeMap map = new TreeMap();

            try {

                InputStream inputStream = CMBCUtil.class.getClassLoader().getResourceAsStream(fileName);
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(inputStream);
                Element root = doc.getRootElement();
                List list = root.getChildren();

                String key;
                String value;
                for(Iterator it = list.iterator(); it.hasNext(); map.put(key, value)) {
                    Element e = (Element)it.next();
                    key = e.getName();
                    value = "";
                    List children = e.getChildren();
                    if (children.isEmpty()) {
                        value = e.getTextNormalize();
                    } else {
                        for (Iterator it1 = children.iterator(); it1.hasNext();) {
                            Element e1 = (Element)it1.next();
                            if (e1.getName().equals("bankCode")) {
                                value = e1.getText();
                            } else if (e1.getName().equals("bankName")) {
                                key = e1.getText();
                            }
                        }
                    }
                }

            } catch (JDOMException var12) {
                var12.printStackTrace();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

            return map;
        } else {
            return null;
        }
    }

    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();

            while(it.hasNext()) {
                Element e = (Element)it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }

                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }


    public static void doesItExist(String key) {
        if (StringUtils.isNotBlank(key) && !CMBCUtil.CMBCMap.containsKey(key)) {
            throw new CommonException(ICommon.BANKCARD_NO_SUPPORT, String.format(PropertiesUtils.get(ICommon.BANKCARD_NO_SUPPORT), key));
        }
    }
}
