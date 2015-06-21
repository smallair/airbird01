package com.example.dbdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.bean.Person;
import com.example.db.DatabaseUtil;

public class DelAndUpdate extends Activity {
	
	private EditText name;
	private RadioButton male;
	private RadioButton female;
	private RadioGroup  mRadioGroup;
	private Button save_update;
	private Button delete;
	
	private int id;
	
	private DatabaseUtil mUtil;
	public  int sex ;  
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.del_update);
		//初始化
		initview();
		setListener();
	}
	
	private void initview(){
		name = (EditText)findViewById(R.id.name_edt_update);
		male = (RadioButton)findViewById(R.id.radio_male_update);
		female = (RadioButton)findViewById(R.id.radio_female_update);
		mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup_selectSex_update);
		save_update = (Button)findViewById(R.id.update_enter_btn);
		delete = (Button)findViewById(R.id.update_delete_btn);
		//获得从ListView传过来的id
		Intent intent = getIntent();
		id = Integer.parseInt(intent.getStringExtra("id")); //获取id
		Log.e("aaaa", id+"");
		//获取数据库操作对象
		mUtil = new DatabaseUtil(DelAndUpdate.this);
		//获取选择对象
		Person p = mUtil.queryByid(id);
//		Log.e("name", p.getName());
		//把对象信息显示
		name.setText(p.getName());
		if("男".equals(p.getSex())){
			male.setChecked(true);
			sex = 1;
		}else{
			female.setChecked(true);
			sex = 0;
		}
		
		//修改保存
		save_update.setOnClickListener(new myOnClick());
		//删除信息
		delete.setOnClickListener(new myOnClick());
	}
	
	//性别监听
		public void setListener(){
			mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					Log.e("radio", "radio");
					if(checkedId == R.id.radio_male_update){
						sex = 1;
						Log.e("male", "male");
					}else if(checkedId == R.id.radio_female_update){
						sex = 0;
						Log.e("male", "male");
					}
				}
			});
		}
	
	
	//按钮监听
	private class myOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.update_enter_btn:  //修改并保存
				Person person = new Person();
				person.setId(id);
				person.setName(name.getText().toString());
				
				if(sex == 1){
					person.setSex(male.getText().toString());
				}else if(sex == 0){
					person.setSex(female.getText().toString());
				}
				Log.e("sex", person.getSex());
				mUtil.Update(person, id);
				Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
				
				break;
			case R.id.update_delete_btn:  //删除
				mUtil.Delete(id);
				Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
		
	}
}
