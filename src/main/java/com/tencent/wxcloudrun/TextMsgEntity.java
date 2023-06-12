package com.tencent.wxcloudrun;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class TextMsgEntity {
    String toUser ;
    String msgtype;
    JSONObject text;
    String content;
}

//<xml>
//  <ToUserName><![CDATA[toUser]]></ToUserName>
//  <FromUserName><![CDATA[fromUser]]></FromUserName>
//  <CreateTime>12345678</CreateTime>
//  <MsgType><![CDATA[text]]></MsgType>
//  <Content><![CDATA[你好]]></Content>
//</xml>