package example.chennaidataportal;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import example.chennaidata.api.ApiDataAccess;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Attendance extends Activity {
	ListView list;
	ArrayList<String> schoolNames, schoolId, school, schoolArea, schoolCity;
	String resultdata;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.studentattendance);
		list = (ListView) findViewById(R.id.schoollist);

		dialog = ProgressDialog.show(Attendance.this, "Loading...",
				"Please wait...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ApiDataAccess data = new ApiDataAccess();
				try {
					resultdata = data.sendGet("http://192.168.120.192/api/schools/list");
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showcustomDialog(arg2);
			}
		});
	}

	public void showcustomDialog(final int posotion) {
		final Dialog opendialog = new Dialog(Attendance.this);
		opendialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		opendialog.setContentView(R.layout.activity_alert);
		opendialog.show();
		TextView heading_txt = (TextView) opendialog
				.findViewById(R.id.heading_txt);
		TextView result_txt = (TextView) opendialog
				.findViewById(R.id.result_txt);
		final Button ok_btn = (Button) opendialog.findViewById(R.id.ok_btn);
		final Button cancel_btn = (Button) opendialog
				.findViewById(R.id.cancel_btn);

		heading_txt.setText("Confirmation");
		result_txt.setText("Do you want to take attendance for?");
		ok_btn.setText("Student");
		cancel_btn.setText("Teacher");
		ok_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// ok_btn.startAnimation(clickAnimation());
				opendialog.dismiss();
				Intent attendanceScreen = new Intent(Attendance.this,
						AttendanceScreen.class);
				attendanceScreen.putExtra("type", "student");
				attendanceScreen.putExtra("schoolid", schoolId.get(posotion));
				startActivity(attendanceScreen);

			}
		});

		cancel_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// ok_btn.startAnimation(clickAnimation());
				opendialog.dismiss();

				Intent attendanceScreen = new Intent(Attendance.this,
						AttendanceScreen.class);
				attendanceScreen.putExtra("type", "teacher");
				attendanceScreen.putExtra("schoolid", schoolId.get(posotion));
				startActivity(attendanceScreen);
			}
		});
	}

	public class DataAdapter extends BaseAdapter {
		ArrayList<String> namedata;

		public DataAdapter(ArrayList<String> namelist) {
			namedata = namelist;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return namedata.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return namedata.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		private class ViewHolder {
			TextView name;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.textlayout, null);

				holder = new ViewHolder();

				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(namedata.get(position));

			return convertView;
		}

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int res = msg.what;
			dialog.dismiss();
			if (res == 1) {
				schoolId = new ArrayList<String>();
				school = new ArrayList<String>();
				schoolNames = new ArrayList<String>();
				schoolArea = new ArrayList<String>();
				schoolCity = new ArrayList<String>();

				try {

					JSONArray array = new JSONArray(resultdata);
					System.out.println("Data out" + array.length());
					for (int i = 0; i < array.length(); i++) {
						JSONObject innerobj = array.getJSONObject(i);
						schoolId.add(innerobj.getString("school_id"));
						schoolNames.add(innerobj.getString("school_name"));
						schoolArea.add(innerobj.getString("area"));
						schoolCity.add(innerobj.getString("city"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DataAdapter adapter = new DataAdapter(schoolNames);
				list.setAdapter(adapter);

			}
		}
	};
}
