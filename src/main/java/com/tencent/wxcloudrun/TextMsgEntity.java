package com.tencent.wxcloudrun;

import lombok.Data;

@Data
public class TextMsgEntity {
    String toUser ;
    String fromUser;
    String createTime;
    String MsgType;
    String Content;
}

//<xml>
//  <ToUserName><![CDATA[toUser]]></ToUserName>
//  <FromUserName><![CDATA[fromUser]]></FromUserName>
//  <CreateTime>12345678</CreateTime>
//  <MsgType><![CDATA[text]]></MsgType>
//  <Content><![CDATA[你好]]></Content>
//</xml>