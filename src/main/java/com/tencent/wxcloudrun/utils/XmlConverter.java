package com.tencent.wxcloudrun.utils;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class XmlConverter {

    public String convertToXml(Object object) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(object, stringWriter);
            
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}