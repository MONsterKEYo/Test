package com.rj.util;

import java.util.List;

import com.rj.bean.AirData;

public interface XmlDocument {
	 /**
     * 解析XML文档
     * 
     * @param fileName
     *            文件全路径名称
     */
    public List<AirData> parserXml(String fileName);
}
