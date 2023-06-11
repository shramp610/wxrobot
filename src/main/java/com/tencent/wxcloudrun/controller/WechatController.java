package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
            String respMessage ="123";
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
        return ApiResponse.ok("收到消息");
    }


}
