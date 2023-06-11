package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.tencent.wxcloudrun.TextMsgEntity;
import com.tencent.wxcloudrun.config.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
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
        log.info("客服消息结果",jsonObject);
        return "SUCCESS";
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

}
