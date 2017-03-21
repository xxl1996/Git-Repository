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

public class XlsMain {
	int preIndentionCount=0;
	int fifthIndentionCount;
	int fourthIndentionCount;
	int thirdIndentionCount;
    int twiceIndentionCount;
    int firstIndentionCount;
    int list_id;
    int level=1;
    int preLevel=0;
    int parent_id;
    int preParent_id;
    int fifth_ID;
    int fourth_ID;
    int third_ID;
    int twice_ID;
    int first_ID;

    public static void main(String[] args) throws IOException {
    	 
        XlsMain xlsMain = new XlsMain();

        XlsDto xls = null;

        List<XlsDto> list = xlsMain.readXls();
         
        for (int i = 0; i < list.size(); i++) {

            xls = (XlsDto) list.get(i);

        }

 

    }


    private List<XlsDto> readXls() throws IOException {

        InputStream is = new FileInputStream("d://诊断代码_v0.3.xls");

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        XlsDto xlsDto = null;
        
        BufferedWriter writer = null;

        List<XlsDto> list = new ArrayList<XlsDto>();
        List<Integer> count = new ArrayList<Integer>();

        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {

            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

            if (hssfSheet == null) {

                continue;

            }
            
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                
                if (hssfRow == null) {

                    continue;

                }

                xlsDto = new XlsDto();
                
                HSSFCell xm = hssfRow.getCell((short) 1);

                if (xm == null) {
                    continue;
                }
                xm.setEncoding((short) HSSFCell.ENCODING_UTF_16);
                xlsDto.setXm(getValue(xm));
                HSSFCell xh = hssfRow.getCell((short) 0);
                
                HSSFCellStyle style=xh.getCellStyle();
                int curIndentionCount=0;
                
                if (xh == null) {
                    continue;

                }
                xh.setEncoding((short) HSSFCell.ENCODING_UTF_16);
                xlsDto.setXh(getValue(xh));
                curIndentionCount=style.getIndention();
                if(curIndentionCount==0){
                	preIndentionCount=curIndentionCount;
                	fifthIndentionCount=0;
                	fourthIndentionCount=0;
                	thirdIndentionCount=0;
                	twiceIndentionCount=0;
                    firstIndentionCount=curIndentionCount;
                    level=1;
                    fourth_ID=0;
                    third_ID=0;
                    twice_ID=0;                    
                    list_id=rowNum;  
                    first_ID=list_id;
                    System.out.println("insert into T_DIAGNOSIS (LIST_ID, DIAGNO_DESC, PARENT_ID, DIAGNO_CATEGORY, DIAGNO_NAME) values ("+list_id+","+"'"+getValue(xm)+"', null , "+level+",'"+getValue(xh)+"'"+");");
                }
                else if(curIndentionCount>preIndentionCount&&fourthIndentionCount==0&&thirdIndentionCount==0&&twiceIndentionCount==0){
                	fourthIndentionCount=0;
                	thirdIndentionCount=0;
                	preIndentionCount=curIndentionCount;
                	twiceIndentionCount=curIndentionCount;
                	thirdIndentionCount=0;
                	fifthIndentionCount=0;
                	if(list_id==1){
                	parent_id=list_id;
                	preParent_id=0;
                	}else{
                	preParent_id=parent_id;
                	parent_id=list_id-1;             	
                	}
                	list_id=rowNum;
                	twice_ID=list_id;
                	preLevel=level;
                	level=2;
                	if(preLevel==level){
                		parent_id=preParent_id;
                	}else{
                		preParent_id=parent_id;
                    	parent_id=list_id-1; 
                	}
                    System.out.println("insert into T_DIAGNOSIS (LIST_ID, DIAGNO_DESC, PARENT_ID, DIAGNO_CATEGORY, DIAGNO_NAME) values ("+list_id+","+"'"+getValue(xm)+"', "+parent_id+", "+level+",'"+getValue(xh)+"'"+");");
                }
                else if(curIndentionCount>twiceIndentionCount&&thirdIndentionCount==0&&fourthIndentionCount==0){
                	preIndentionCount=curIndentionCount;
                	thirdIndentionCount=curIndentionCount;   
                	fourthIndentionCount=0;
                	fifthIndentionCount=0;
                	list_id=rowNum;           	
                	parent_id=list_id-1;
                	preLevel=level;
                	level=3;
                	third_ID=list_id;
                	if(preLevel==level){
                		parent_id=preParent_id;
                	}else{
                		preParent_id=parent_id;
                    	parent_id=list_id-1; 
                	}
                    System.out.println("insert into T_DIAGNOSIS (LIST_ID, DIAGNO_DESC, PARENT_ID, DIAGNO_CATEGORY, DIAGNO_NAME) values ("+list_id+", '"+getValue(xm)+"', "+parent_id+","+level+", "+"'"+getValue(xh)+"'"+");");
                }  
                
                else if(curIndentionCount>thirdIndentionCount&&thirdIndentionCount!=0&&fourthIndentionCount==0){          	
                	preIndentionCount=curIndentionCount;
                	fourthIndentionCount=curIndentionCount;
                	list_id=rowNum;
                	parent_id=list_id-1;
                	preLevel=level;
                	level=4;
                	fourth_ID=list_id;
                	if(preLevel==level){
                		parent_id=preParent_id;
                	}else{
                		preParent_id=parent_id;
                    	parent_id=list_id-1; 
                	}
                    System.out.println("insert into T_DIAGNOSIS (LIST_ID, DIAGNO_DESC, PARENT_ID, DIAGNO_CATEGORY, DIAGNO_NAME) values ("+list_id+", '"+getValue(xm)+"', "+parent_id+", "+level+", "+"'"+getValue(xh)+"'"+");");
                }
                else if(curIndentionCount>fourthIndentionCount&&fourthIndentionCount!=0){          	
                	preIndentionCount=curIndentionCount;
                	fifthIndentionCount=curIndentionCount;
                	list_id=rowNum;
                	parent_id=list_id-1;
                	preLevel=level;
                	level=5;
                	fifth_ID=list_id;
                	if(preLevel==level){
                		parent_id=preParent_id;
                	}else{
                		preParent_id=parent_id;
                    	parent_id=list_id-1; 
                	}
                    System.out.println("insert into T_DIAGNOSIS (LIST_ID, DIAGNO_DESC, PARENT_ID, DIAGNO_CATEGORY, DIAGNO_NAME) values ("+list_id+", '"+getValue(xm)+"', "+parent_id+", "+level+", "+"'"+getValue(xh)+"'"+");");
                }
                else if(curIndentionCount<preIndentionCount){          	
                	preIndentionCount=curIndentionCount;  
                	
                	list_id=rowNum;
                	if(level==5){       		
                		fifthIndentionCount=0;
                    	if(curIndentionCount==fourthIndentionCount){
                    		parent_id=third_ID;
                    		fourth_ID=list_id;
                     		level=level-1;
                     		fourthIndentionCount=curIndentionCount;
                     	}else if(curIndentionCount==thirdIndentionCount){
                     		parent_id=twice_ID;
                     		third_ID=list_id;
                    		level=level-2;
                    		thirdIndentionCount=curIndentionCount;
                    		fourthIndentionCount=0;
                    	}else if(curIndentionCount==twiceIndentionCount){
                    		parent_id=first_ID;
                    		twice_ID=list_id;
                    		level=level-3;
                    		twiceIndentionCount=curIndentionCount;
                    		thirdIndentionCount=0;
                    		fourthIndentionCount=0;
                    	}else if(curIndentionCount==firstIndentionCount){
                    		parent_id=0;
                    		first_ID=list_id;
                    		level=level-4;
                    		firstIndentionCount=curIndentionCount;
                    		twiceIndentionCount=0;
                    		thirdIndentionCount=0;
                    		fourthIndentionCount=0;
                    	}
                    	            	 
                    }else if(level==4){
                	
                	if(curIndentionCount==thirdIndentionCount){
                		parent_id=twice_ID;
                		third_ID=list_id;
                		level=level-1;
                		thirdIndentionCount=curIndentionCount;
                		fourthIndentionCount=0;
                	}else if(curIndentionCount==twiceIndentionCount){
                		parent_id=first_ID;
                		twice_ID=list_id;
                		level=level-2;
                		twiceIndentionCount=curIndentionCount;
                		thirdIndentionCount=0;
                		fourthIndentionCount=0;
                	}else if(curIndentionCount==firstIndentionCount){
                		parent_id=0;
                		first_ID=list_id;
                		level=level-3;
                		firstIndentionCount=curIndentionCount;
                		twiceIndentionCount=0;
                		thirdIndentionCount=0;
                		fourthIndentionCount=0;
                	}
                	}else if(level==3){
                	if(curIndentionCount==twiceIndentionCount){
                		parent_id=first_ID;
                		twice_ID=list_id;
                		twiceIndentionCount=curIndentionCount;
                		thirdIndentionCount=0;
                		fourthIndentionCount=0;
                		level=level-1;
                	}else if(curIndentionCount==firstIndentionCount){
                		parent_id=0;
                		first_ID=list_id;
                		level=level-2;
                		firstIndentionCount=curIndentionCount;
                		twiceIndentionCount=0;
                		thirdIndentionCount=0;
                		fourthIndentionCount=0;
                	}
                	}else if(level==2){
                		parent_id=0;
                		first_ID=list_id;
                    	twiceIndentionCount=curIndentionCount;
                    	thirdIndentionCount=0;
                    	fourthIndentionCount=0;
                    	level=level-1; 
                    	}    
                    System.out.println("insert into T_DIAGNOSIS (LIST_ID, DIAGNO_DESC, PARENT_ID, DIAGNO_CATEGORY, DIAGNO_NAME) values ("+list_id+", '"+getValue(xm)+"', "+parent_id+", "+level+", "+"'"+getValue(xh)+"'"+");");
                }
                else if(curIndentionCount==preIndentionCount){
                	preIndentionCount=curIndentionCount;
                	list_id=rowNum;
                	if(level==1){
                		first_ID=list_id;
                	}else if(level==2){
                		twice_ID=list_id;
                	}else if(level==3){
                		third_ID=list_id;
                	}else if(level==4){
                		fourth_ID=list_id;
                	}else if(level==5){
                		fifth_ID=list_id;
                	}
                    System.out.println("insert into T_DIAGNOSIS (LIST_ID, DIAGNO_DESC, PARENT_ID, DIAGNO_CATEGORY, DIAGNO_NAME) values ("+list_id+", '"+getValue(xm)+"', "+parent_id+", "+level+", "+"'"+getValue(xh)+"'"+");");
                }
                     

                list.add(xlsDto);
                

            }
            
        }
        return list;

    }

 

    @SuppressWarnings("static-access")

    private String getValue(HSSFCell hssfCell) {

        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {

            // ���ز������͵�ֵ

            return String.valueOf(hssfCell.getBooleanCellValue());

        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {

            // ������ֵ���͵�ֵ

            return String.valueOf(hssfCell.getNumericCellValue());

        } else {

            // �����ַ����͵�ֵ
            return String.valueOf(hssfCell.getStringCellValue());

        }

    }

 

}