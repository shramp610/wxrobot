package com.tencent.wxcloudrun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextRespEntity {
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String content;
}
