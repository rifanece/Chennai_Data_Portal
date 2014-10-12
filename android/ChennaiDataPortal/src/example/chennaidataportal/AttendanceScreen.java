package example.chennaidataportal;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import example.chennaidata.api.ApiDataAccess;

public class AttendanceScreen extends Activity {
	String type, schoolid;
	LinearLayout layout;
	Spinner spin;
	ListView list;
	ApiDataAccess dataaccess;
	String result;
	ArrayList<String> classes, studentname, staffname, studentid, staffid;
	ProgressDialog dialog;
	ArrayList<SchoolData> teacherdata, studentdata;
	DatePickerDialog myDialog;
	TextView date;
	private int myYear, myMonth, myDay;
	Button submit,cancel;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.attendancescreen);
		list = (ListView) findViewById(R.id.screenlist);
		layout = (LinearLayout) findViewById(R.id.spinlay);
		spin = (Spinner) findViewById(R.id.classspin);
		date=(TextView)findViewById(R.id.datepin);
		submit=(Button)findViewById(R.id.submit);
		cancel=(Button)findViewById(R.id.cancel);
		type = getIntent().getStringExtra("type");
		schoolid = getIntent().getStringExtra("schoolid");
		dataaccess = new ApiDataAccess();
		if (type.equalsIgnoreCase("teacher")) {
			layout.setVisibility(View.GONE);
			spin.setVisibility(View.GONE);
			getStaffData();
		} else {
			layout.setVisibility(View.VISIBLE);
			spin.setVisibility(View.VISIBLE);
			getStudentclassData();
		}
		Calendar c = Calendar.getInstance();
		myYear = c.get(Calendar.YEAR);
		myMonth = c.get(Calendar.MONTH);
		myDay = c.get(Calendar.DAY_OF_MONTH);
		String date_1 = String.valueOf(myMonth + 1) + "-"
				+ String.valueOf(myDay) + "-" + String.valueOf(myYear);
		
		date.setText(date_1);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				postStudentAttendance("{\"Attendance\" : {\"student_id\": \"1\"}, {\"turnout_dt\": \"2014-10-14\"},{\"ispresent\": \"Y\"},{\"absent_reason\": \"test\"}}");
			}
		});
		
		date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				myYear = c.get(Calendar.YEAR);
				myMonth = c.get(Calendar.MONTH);
				myDay = c.get(Calendar.DAY_OF_MONTH);
				showDialog(0);
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				SchoolData country = (SchoolData) parent
						.getItemAtPosition(position);

			}

		});

		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getStudentdata();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case 0:
			// Toast.makeText(Add_Booking.this, "- onCreateDialog -",
			// Toast.LENGTH_LONG).show();
			myDialog = new DatePickerDialog(this, myDateSetListener, myYear,
					myMonth, myDay);

			break;
		default:
			return myDialog;
		}
		return myDialog;
	}
	
	
	private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker d_p, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			// String date = String.valueOf(year)
			// +"/"+String.valueOf(monthOfYear+1) +"/"+
			// String.valueOf(dayOfMonth);

			/*
			 * Toast.makeText(Add_Booking.this, date, Toast.LENGTH_LONG).show();
			 */

			String date_1 = String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(dayOfMonth) + "-" + String.valueOf(year);
			
			String mon;
			if (monthOfYear + 1 <= 9) {
				mon = "0" + String.valueOf(monthOfYear + 1);
			} else {
				mon = String.valueOf(monthOfYear + 1);
			}

			String day;
			if (dayOfMonth <= 9) {
				day = "0" + String.valueOf(dayOfMonth);
			} else {
				day = String.valueOf(dayOfMonth);
			}

			 date_1= String.valueOf(year) + "-" + mon + "-" + day;
			

			// /populateSetDate(yy, mm + 1, dd);

			// myDateSetListener.setButton(DatePickerDialog.BUTTON_POSITIVE,
			// "Button Text", d_p);

		}
	};
	
	public void postStudentAttendance(final String json) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dataaccess.postjson(json, "http://192.168.120.192/api/student/attendance/add");
				handler.sendEmptyMessage(5);
			}
		}).start();
	}

	
	public void postStaffAttendance() {
		
	}

	public void getStaffData() {
		dialog = ProgressDialog.show(AttendanceScreen.this, "Please wait...",
				"Loading...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					result = dataaccess
							.sendGet("http://192.168.120.192/api/staffs/"
									+ schoolid);
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void getStudentdata() {

		dialog = ProgressDialog.show(AttendanceScreen.this, "Please wait...",
				"Loading...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					result = dataaccess
							.sendGet("http://192.168.120.192/api/students/1/SSLC");
					handler.sendEmptyMessage(2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	public void getStudentclassData() {
		dialog = ProgressDialog.show(AttendanceScreen.this, "Please wait...",
				"Loading...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					result = dataaccess
							.sendGet("http://192.168.120.192/api/classes/"
									+ schoolid);
					handler.sendEmptyMessage(3);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int res = msg.what;
			dialog.dismiss();
			if (res == 1) {

				teacherdata = new ArrayList<SchoolData>();

				try {

					JSONArray array = new JSONArray(result);
					System.out.println("Data out" + array.length());
					for (int i = 0; i < array.length(); i++) {
						SchoolData data = new SchoolData();

						JSONObject innerobj = array.getJSONObject(i);
						data.setStaffId(innerobj.getString("staff_id"));
						data.setStaffName(innerobj.getString("staff_name"));
						teacherdata.add(data);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MyCustomAdapter adapter = new MyCustomAdapter(
						AttendanceScreen.this, R.layout.textwithcheck,
						teacherdata,type);
				list.setAdapter(adapter);
			} else if (res == 2) {
				studentdata = new ArrayList<SchoolData>();

				try {

					// JSONArray array = new JSONArray(result);
					// System.out.println("Data out" + array.length());
					for (int i = 0; i < 5; i++) {
						SchoolData data = new SchoolData();

						JSONObject innerobj = new JSONObject(result);
						data.setStudentID(innerobj.getString("student_id"));
						data.setStudentName(innerobj.getString("student_name"));
						studentdata.add(data);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MyCustomAdapter adapter = new MyCustomAdapter(
						AttendanceScreen.this, R.layout.textwithcheck,
						studentdata,type);
				list.setAdapter(adapter);

			} else if (res == 3) {
				classes = new ArrayList<String>();
				classes.add("SSLC");
				classes.add("SSLC");
				classes.add("SSLC");

				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
						AttendanceScreen.this,
						android.R.layout.simple_spinner_item, classes);
				spin.setAdapter(dataAdapter);

			}
			else if(res==5){
				finish();
			}
		}
	};

	private class MyCustomAdapter extends ArrayAdapter<SchoolData> {

		private ArrayList<SchoolData> countryList;
		String typeoflist;

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<SchoolData> countryList, String type) {
			super(context, textViewResourceId, countryList);
			this.countryList = new ArrayList<SchoolData>();
			this.countryList.addAll(countryList);
			typeoflist = type;
		}

		private class ViewHolder {
			TextView code;
			CheckBox name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.textwithcheck, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView.findViewById(R.id.code);
				holder.name = (CheckBox) convertView
						.findViewById(R.id.checkBox1);
				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						SchoolData country = (SchoolData) cb.getTag();
						country.setSelected(cb.isChecked());
					}
				});
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			SchoolData country = countryList.get(position);
			if (typeoflist.equalsIgnoreCase("student")) {
				holder.code.setText(country.getStudentName());
				holder.name.setChecked(country.isSelected());
				holder.name.setTag(country);

			} else {
				holder.code.setText(country.getStaffName());
				holder.name.setChecked(country.isSelected());
				holder.name.setTag(country);

			}
			return convertView;

		}

	}
}
