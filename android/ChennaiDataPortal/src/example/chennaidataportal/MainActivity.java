package example.chennaidataportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	WebView mwebview;
	Dialog progressBar;
	Button attendance, report;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		mwebview = (WebView) findViewById(R.id.webview);
		attendance = (Button) findViewById(R.id.attendance);
		report = (Button) findViewById(R.id.report);

		WebSettings settings = mwebview.getSettings();
		settings.setLoadsImagesAutomatically(true);
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);

		attendance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent attendanceScreen = new Intent(MainActivity.this,
						Attendance.class);
				startActivity(attendanceScreen);

			}
		});
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent attendanceScreen = new Intent(MainActivity.this,
						ImageUpload.class);
				startActivity(attendanceScreen);
			}
		});

		mwebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		progressBar = ProgressDialog.show(MainActivity.this, "Please wait...",
				"Loading...");

		mwebview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				

				view.loadUrl("http://192.168.120.192/cdp/map.html");
				return true;
			}

			public void onPageFinished(WebView view, String url) {

				if (progressBar.isShowing()) {
					progressBar.dismiss();
				}
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {

				Toast.makeText(MainActivity.this, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
				alertDialog.setTitle("Error");
				alertDialog.setMessage(description);
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				alertDialog.show();
			}
		});
		mwebview.loadUrl("https://klp.org.in");

	}

}
