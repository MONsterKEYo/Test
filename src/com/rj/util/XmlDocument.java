package com.rj.util;

import java.util.List;

import com.rj.bean.AirData;

public interface XmlDocument {
	 /**
     * ����XML�ĵ�
     * 
     * @param fileName
     *            �ļ�ȫ·������
     */
    public List<AirData> parserXml(String fileName);
}
