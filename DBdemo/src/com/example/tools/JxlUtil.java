package com.example.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import android.os.Environment;

import com.example.bean.Person;

public class JxlUtil {

	/**
	 * ��������excel�ļ��������SD����
	 * @author smart *
	 */
	private List<Person> list;
	
	public JxlUtil(List<Person> list){
		this.list = list;
	}
	
	
	public boolean toExcel() {
		// ׼������excel������ı���
		String[] title = { "���", "����", "�Ա�" };
		try {
			// ��ÿ�ʼʱ��
			long start = System.currentTimeMillis();
			
			
			//�ж�SD���Ƿ����
			if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
				return false;
			}
			
			String SDdir =  Environment.getExternalStorageDirectory().toString();  //��ȡSD���ĸ�Ŀ¼
			// ����Excel������
			WritableWorkbook wwb;
			// ��SD���У��½���һ����Ϊperson��jxl�ļ�
			wwb = Workbook.createWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/person.xls"));
			// ��ӵ�һ�����������õ�һ��Sheet������
			WritableSheet sheet = wwb.createSheet("Ա���嵥", 0);
			Label label;
			for (int i = 0; i < title.length; i++) {
				label = new Label(i, 0, title[i]);
				// ������õĵ�Ԫ����ӵ���������
				sheet.addCell(label);
			}
			/*
			 * �������ֵ���Ԫ����Ҫʹ��jxl.write.Number ����ʹ��������·�����������ִ���
			 */
			for(int i = 0 ; i < list.size(); i++){
				Person person = list.get(i);
				//��ӱ��
				jxl.write.Number number = new jxl.write.Number(0, i+1, person.getId());
				sheet.addCell(number);
				//�������
				label = new Label(1,i+1,person.getName());
				sheet.addCell(label);
				//����Ա�
				label = new Label(2,i+1,person.getSex());
				sheet.addCell(label);
			}
			
			wwb.write(); //д������
			wwb.close(); //�ر��ļ�
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
