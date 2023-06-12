package com.tencent.wxcloudrun.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSON;


public class XmlToJsonConverter {

    public static String convertXmlToJson(String xmlString) {
        JSONObject jsonObject = XML.toJSONObject(xmlString);
        return jsonObject.toString();
    }
}

