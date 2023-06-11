//package com.tencent.wxcloudrun.service;
//
//import com.mini.wechatbot.entity.WechatMsgTypeConstant;
//import com.mini.wechatbot.utils.WechatMessageUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.UnsupportedEncodingException;
//import java.util.Map;
//
///**
// * @author C.W
// * @date 2022/5/18 7:32
// * @desc 微信
// */
//
//@Slf4j
//@Service
//public class WechatService {
//
//    @Autowired
//    private TextReplyService textReplyService;
//
//    /**
//     * 微信回复
//     *
//     * @param request
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public String callback(HttpServletRequest request) throws UnsupportedEncodingException {
//        request.setCharacterEncoding("UTF-8");
//
//        try {
//            Map<String, String> requestMap = WechatMessageUtils.parseXml(request);
//            log.info("微信接收到消息:{}", GsonUtils.toJson(requestMap));
//            // 消息类型
//            String msgType = requestMap.get("MsgType");
////             处理其他消息，暂时不做回复
//            switch (msgType) {
//                case WechatMsgTypeConstant.MESSAGE_TYPE_TEXT:
//                    // 文本消息处理
//                    return textReplyService.reply(requestMap);
//                default:
//                    return textReplyService.reply(requestMap);
//            }
//        } catch (Throwable e) {
//            log.error("回复消息错误", e);
//        }
//        // 不做回复
//        return null;
//    }
//
//}