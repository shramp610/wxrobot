//package com.tencent.wxcloudrun.service;
//
//import com.mini.wechatbot.entity.TextMessage;
//import com.mini.wechatbot.utils.WechatMessageUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Map;
//
///**
// * @author C.W
// * @date 2022/5/18 9:57
// * @desc 文本回复
// */
//@Service
//public class TextReplyService {
//
//    private static final String FROM_USER_NAME = "FromUserName";
//    private static final String TO_USER_NAME = "ToUserName";
//    private static final String CONTENT = "Content";
//
//    @Autowired
//    private WechatKeywordDao wechatKeywordDao;
//
//    @Autowired
//    private WechatMsgRecordDao wechatMsgRecordDao;
//
//    /**
//     * 自动回复文本内容
//     *
//     * @param requestMap
//     * @return
//     */
//    public String reply(Map<String, String> requestMap) {
//        String wechatId = requestMap.get(FROM_USER_NAME);
//        String gongzhonghaoId = requestMap.get(TO_USER_NAME);
//
//        TextMessage textMessage = WechatMessageUtils.getDefaultTextMessage(wechatId, gongzhonghaoId);
//
//        String content = requestMap.get(CONTENT);
//        if (content == null) {
//            textMessage.setContent(WechatConstants.DEFAULT_MSG);
//        } else {
//            Example example = new Example(WechatKeywordPO.class);
//            example.createCriteria().andEqualTo("wechatId", gongzhonghaoId).andEqualTo("keyword", content);
//            List<WechatKeywordPO> keywordPOList = wechatKeywordDao.selectByExample(example);
//            if (CollectionUtils.isEmpty(keywordPOList)) {
//                textMessage.setContent(WechatConstants.DEFAULT_MSG);
//            } else {
//                textMessage.setContent(keywordPOList.get(0).getReplyContent());
//            }
//        }
//        // 记录消息记录
//        wechatMsgRecordDao.insertSelective(WechatMsgRecordPO.builder()
//                .fromUser(wechatId)
//                .wechatId(gongzhonghaoId)
//                .content(content)
//                .replyContent(textMessage.getContent())
//                .build()
//        );
//
//        return WechatMessageUtils.textMessageToXml(textMessage);
//    }
//
//}
