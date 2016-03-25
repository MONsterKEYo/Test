package com.rj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.rj.util.ExcelUtil;

/**
 * ����Excel����Ĺ�����
 */
public class ExcelUtil {
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    /**
     * ��ȡExcel�����ͷ������
     * @param InputStream
     * @return String ��ͷ���ݵ�����
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // ����������
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * ��ȡExcel��������
     * @param InputStream
     * @return Map ������Ԫ���������ݵ�Map����
     */
    public Map<Integer, String> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // �õ�������
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // ��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // ÿ����Ԫ�������������"-"�ָ���Ժ���Ҫʱ��String���replace()������ԭ����
                // Ҳ���Խ�ÿ����Ԫ����������õ�һ��javabean�������У���ʱ��Ҫ�½�һ��javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                str += getCellFormatValue(row.getCell((short) j)).trim() + "@!#";
                j++;
            }
            content.put(i, str);
            str = "";
        }
        return content;
    }

    /**
     * ��ȡ��Ԫ����������Ϊ�ַ������͵�����
     * 
     * @param cell Excel��Ԫ��
     * @return String ��Ԫ����������
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * ��ȡ��Ԫ����������Ϊ�������͵�����
     * 
     * @param cell
     *            Excel��Ԫ��
     * @return String ��Ԫ����������
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[����]", "-").replace("��", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("���ڸ�ʽ����ȷ!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ����HSSFCell������������
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // �жϵ�ǰCell��Type
            switch (cell.getCellType()) {
            // �����ǰCell��TypeΪNUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // �жϵ�ǰ��cell�Ƿ�ΪDate
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // �����Date������ת��ΪData��ʽ
                    
                    //����1�������ӵ�data��ʽ�Ǵ�ʱ����ģ�2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //����2�������ӵ�data��ʽ�ǲ�����ʱ����ģ�2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // ����Ǵ�����
                else {
                    // ȡ�õ�ǰCell����ֵ
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // �����ǰCell��TypeΪSTRIN
            case HSSFCell.CELL_TYPE_STRING:
                // ȡ�õ�ǰ��Cell�ַ���
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // Ĭ�ϵ�Cellֵ
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

	public static void FileMove(String file,String newFilepath) {
		//�ļ�ԭ��ַ 
		File oldFile = new File(file); 
		//�ļ��£�Ŀ�꣩��ַ 
		String newPath = newFilepath; 
		//newһ�����ļ��� 
		File fnewpath = new File(newPath); 
		//�ж��ļ����Ƿ���� 
		if(!fnewpath.exists()) 
		fnewpath.mkdirs(); 
		//���ļ��Ƶ����ļ��� 
		//����ļ������Ѿ��и��ļ�
		File ifFile=new File(newPath +oldFile.getName()); 
		if(ifFile.exists()){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//�������ڸ�ʽ
			String strT = df.format(new Date());
			//newһ�����ļ��� 
			File fnewpathT = new File(newPath+strT+"/"); 
			if(!fnewpathT.exists()) 
				fnewpathT.mkdirs(); 
			File fnew = new File(newPath+strT+"/" +oldFile.getName()); 
			oldFile.renameTo(fnew); 
			
		}else{
			
			File fnew = new File(newPath +oldFile.getName()); 
			oldFile.renameTo(fnew); 
			
		}
	}
}