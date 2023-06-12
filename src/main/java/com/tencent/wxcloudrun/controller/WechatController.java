package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.TextMsgEntity;
import com.tencent.wxcloudrun.TextRespEntity;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.utils.XmlConverter;
import com.tencent.wxcloudrun.utils.XmlToJsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
public class WechatController {

//
//    @Autowired
//    private WechatService wechatService;
//    /**
//     * 此接口用于微信验证
//     * @param signature
//     * @param timestamp
//     * @param nonce
//     * @param echostr
//     * @param response
//     */
//    @GetMapping
//    public  void callback(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response){
//        PrintWriter printWriter = null;
//
//    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private XmlConverter xmlConverter;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    /**
     * 微信消息回调
     *
     * @param request
     * @param response
     */
    @PostMapping("callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;

        try {
            String respMessage = "123";
            if (null == respMessage || " ".equals(respMessage)) {
                System.out.println("不回复消息");
                return;
            }
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.write(respMessage);
        } catch (Throwable e) {
            System.out.println("微信发送消息失败");
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 接收消息
     *
     * @param request
     * @param response
     */
    @PostMapping("/message/post")
    public ApiResponse getMsg(HttpServletRequest request, HttpServletResponse response) {
        try {
            String jsonString = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);

            log.info("请求为：" + jsonString);
            postMsg(jsonString);
        } catch (IOException e) {
            log.error("出错误了");
            throw new RuntimeException(e);
        }

        return ApiResponse.ok("SUCCESS");
    }


    private String postMsg(String msg) {
//        该项目代码使用微信云托管,并且配置了开放接口服务,可以免于校验accessToken

        String accessToken = getAsscesToken();
        // 因为客服消息接口无法授权，所以先注释
//        postCustomerMsg(accessToken);

        TextRespEntity textRespEntity = new TextRespEntity();
        textRespEntity.setMsgType("text");
        textRespEntity.setContent("测试内容");
        textRespEntity.setFromUserName("321");
        textRespEntity.setToUserName("123");
        textRespEntity.setCreateTime(String.valueOf(LocalDateTime.now()));
        String xml = msgToXml(textRespEntity);
        String json = XmlToJsonConverter.convertXmlToJson(xml);
        log.info(json);
        return json;
    }

    private String getAsscesToken(){
        String appId = "wx2bad489b5580a308";
        String appSecret = "c41d4a72da0f26876ff4d440ba9f2115";
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId +"&secret=" + appSecret;

        String resultJson = restTemplate.getForObject(tokenUrl, String.class);
        Map<String, String> map = JSONObject.parseObject(resultJson, Map.class);
        String accessToken = map.get("access_token");
        return accessToken;
    }

    /**
     * 异步发送客服消息
     * @param accessToken
     */
    private void postCustomerMsg(String accessToken,String msg){
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
//        String myId = "gh_95a9375e3a65";
//        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
        Map<String, String> map = JSONObject.parseObject(msg, Map.class);
        String content = map.get("Content");
        String fromUserName = map.get("FromUserName");
        TextMsgEntity requst = new TextMsgEntity();
        requst.setMsgtype("text");
        JSONObject text = new JSONObject();
        text.put("content","重复你说的话：" + content);
        requst.setText(text);
        requst.setToUser(fromUserName);
        String jsonObject = restTemplate.postForObject(url, requst, String.class);
        log.info("客服消息结果{}",jsonObject);
    }

    private String msgToXml(TextRespEntity textRespEntity){
        String convertToXml = xmlConverter.convertToXml(textRespEntity);
        log.info(convertToXml);
        return convertToXml;
    }
}
