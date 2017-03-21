package com.ok.test;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;  
import java.util.ArrayList;  
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  

public class XlsDoc {
	String cur;
	int two_id;
	int one_id;
	int zero_id=0;
	int two;
	int one;
	int zero=0;
	int list_id;
	int parent_id;
	int preParent_id;
	int curPosition;
	int prePosition;
	int level;

    public static void main(String[] args) throws IOException {
    	 
    	XlsDoc xlsDoc = new XlsDoc();

        XlsDto xls = null;

        List<XlsDto> list = xlsDoc.readXls();


        for (int i = 0; i < list.size(); i++) {

            xls = (XlsDto) list.get(i);

        }

 

    }



    /**

     * ��ȡxls�ļ�����

     * 

     * @return List<XlsDto>����

     * @throws IOException

     *             ����/���(i/o)�쳣

     */

    private List<XlsDto> readXls() throws IOException {

        InputStream is = new FileInputStream("d://�� �� ����_����_201500176_201600147_�������������Ż�_v0.3.xls");

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        XlsDto xlsDto = null;
        
        List<XlsDto> list = new ArrayList<XlsDto>();
        // ѭ��������Sheet

        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {

            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

            if (hssfSheet == null) {

                continue;

            }

            // ѭ����Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                
                if (hssfRow == null) {

                    continue;

                }

                xlsDto = new XlsDto();

                // ѭ����Cell

                // 0ѧ�� 1���� 2ѧԺ 3�γ��� 4 �ɼ�

                // for (int cellNum = 0; cellNum <=4; cellNum++) {
                
                HSSFCell xm = hssfRow.getCell((short) 1);

                if (xm == null) {

                    continue;

                }

                xlsDto.setXm(getValue(xm));
                HSSFCell xh = hssfRow.getCell((short) 0);
                HSSFCellStyle style=xh.getCellStyle();
                
                if (xh == null) {

                    continue;

                }
                cur=getValue(xh);
                list_id=rowNum;
                String s = cur + "";
                if(s.indexOf(".")==-1){
                	curPosition=-1;
                }else{
                curPosition = s.length() - s.indexOf(".")-1;
                }
                if(curPosition==2){
                	level=curPosition+2;
                }else{
                level=curPosition+1;
                }
                if(curPosition==-1){
                	prePosition=curPosition;
                	zero=curPosition;
                	two=0;
                	one=0;
                	level=1;
                	zero_id=list_id;
                	parent_id=0;
               	    preParent_id=0;
               	 System.out.println("insert into t_surgery (SURGERY_CATEGORY, BENE_AMOUNT, SURGERY_ID, PARENT_ID, SURGERY_NAME, SURGERY_DESC, SURGEONS_LEVEL, ANAE_LEVEL)values ("+level+", null, "+list_id+", null ,'"+getValue(xh)+"'"+",'"+getValue(xm)+"', null, null);");
                }else if(curPosition>prePosition&&one==0&&two==0){
                	if(curPosition==prePosition){
                    	prePosition=curPosition;
                    }
                	parent_id=list_id-1;
                	prePosition=curPosition;
                	one=curPosition;
                	one_id=list_id;
                	level=2;
                	two=0;
                	System.out.println("insert into t_surgery (SURGERY_CATEGORY, BENE_AMOUNT, SURGERY_ID, PARENT_ID, SURGERY_NAME, SURGERY_DESC, SURGEONS_LEVEL, ANAE_LEVEL)values ("+level+", null, "+list_id+", "+parent_id+",'"+getValue(xh)+"'"+",'"+getValue(xm)+"', null, null);");
                }else if(curPosition>prePosition&&two==0){
                	if(curPosition==prePosition){
                    	prePosition=curPosition;
                    }
                	level=4;
                	parent_id=list_id-1;
                	two_id=list_id;
                	prePosition=curPosition;
                	two=curPosition;
                	System.out.println("insert into t_surgery (SURGERY_CATEGORY, BENE_AMOUNT, SURGERY_ID, PARENT_ID, SURGERY_NAME, SURGERY_DESC, SURGEONS_LEVEL, ANAE_LEVEL)values ("+level+", null, "+list_id+", "+parent_id+",'"+getValue(xh)+"'"+",'"+getValue(xm)+"', null, null);");
                }
                else if(curPosition<prePosition){
                	prePosition=curPosition;
                	if(curPosition==-1){
                	  zero=curPosition;
                	  zero_id=list_id;
                	  one=0;
                	  two=0;
                	  level=1;
                		}
                	else if(curPosition==1){
                		parent_id=zero_id;
                		one_id=list_id;
                		level=2;
                		one=curPosition;
                  	    two=0;
                	}else if(curPosition==2){
                		if(level==1){
                		 parent_id=0;
                		 zero_id=list_id;
                		}else if(level==2){
                			parent_id=one_id;
                			one_id=list_id;
                		}else if(level==4){
                			parent_id=two_id;
                			one_id=list_id;
                		}
                		level=4;
                  	    two=curPosition;
                	}
                	System.out.println("insert into t_surgery (SURGERY_CATEGORY, BENE_AMOUNT, SURGERY_ID, PARENT_ID, SURGERY_NAME, SURGERY_DESC, SURGEONS_LEVEL, ANAE_LEVEL)values ("+level+", null, "+list_id+", "+parent_id+",'"+getValue(xh)+"'"+",'"+getValue(xm)+"', null, null);");
                }
                else if(curPosition==prePosition){
                	if(level==1){
               		 zero_id=list_id;
               		}else if(level==2){
               			one_id=list_id;
               		}else if(level==4){
               			two_id=list_id;
               		}
                	prePosition=curPosition;
                	System.out.println("insert into t_surgery (SURGERY_CATEGORY, BENE_AMOUNT, SURGERY_ID, PARENT_ID, SURGERY_NAME, SURGERY_DESC, SURGEONS_LEVEL, ANAE_LEVEL)values ("+level+", null, "+list_id+", "+parent_id+",'"+getValue(xh)+"'"+",'"+getValue(xm)+"', null, null);");
                }
            }
                
                list.add(xlsDto);

            }
        return list;

        }

        


 

    /**

     * �õ�Excel���е�ֵ

     * 

     * @param hssfCell

     *            Excel�е�ÿһ������

     * @return Excel��ÿһ�������е�ֵ

     */

    @SuppressWarnings("static-access")

    private String getValue(HSSFCell hssfCell) {

        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {

            // ���ز������͵�ֵ

            return String.valueOf(hssfCell.getBooleanCellValue());

        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {

            // ������ֵ���͵�ֵ

            return String.valueOf(hssfCell.getNumericCellValue());

        } else {

            // �����ַ������͵�ֵ

            return String.valueOf(hssfCell.getStringCellValue());

        }

    }

 

}