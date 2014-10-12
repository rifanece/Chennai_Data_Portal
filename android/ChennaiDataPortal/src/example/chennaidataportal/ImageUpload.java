package example.chennaidataportal;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageUpload extends Activity {
	EditText result;
	String resultdata, beforePictureFile, image_filepath;
	ProgressDialog dialog;
	ImageView image;
	Dialog custom_dialog;
	Bitmap bitmap, beforeBitmap;
	Uri mCapturedImageURI;
	Button share;
	File file = new File(Environment.getExternalStorageDirectory()
			+ File.separator + "image.jpg");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadimage);
		result = (EditText) findViewById(R.id.cus_name);
		image = (ImageView) findViewById(R.id.image);
		share = (Button) findViewById(R.id.submit);

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent share = new Intent(Intent.ACTION_SEND);

				// If you want to share a png image only, you can do:
				// setType("image/png"); OR for jpeg: setType("image/jpeg");
				share.setType("image/*");

				// Make sure you put example png image named myImage.png in your
				// directory

				// File imageFileToShare = new File(imagePath);

				Uri uri = Uri.fromFile(file);
				share.putExtra(Intent.EXTRA_STREAM, uri);

				startActivity(Intent.createChooser(share, "Share Image!"));
			}
		});
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				custom_dialog = new Dialog(ImageUpload.this);

				custom_dialog.setContentView(R.layout.custom_dialog);
				custom_dialog.setTitle("Choose picture..");
				ImageButton camera_btn = (ImageButton) custom_dialog
						.findViewById(R.id.camera);

				camera_btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String fileName = "temp.jpg";

						Intent intent = new Intent(
								"android.media.action.IMAGE_CAPTURE");
						File file = new File(Environment
								.getExternalStorageDirectory()
								+ File.separator
								+ "image.jpg");
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(file));
						startActivityForResult(intent, 2);
						custom_dialog.dismiss();
					}
				});

				ImageButton gallery_btn = (ImageButton) custom_dialog
						.findViewById(R.id.gallery);

				gallery_btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, 3);

						custom_dialog.dismiss();
					}
				});
				custom_dialog.show();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (requestCode == 3 & data != null) {

				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				file = new File(picturePath);
				image.setImageBitmap(decodeSampledBitmapFromFile(picturePath,
						480, 320));

			}

			if (requestCode == 2) {

				File file = new File(Environment.getExternalStorageDirectory()
						+ File.separator + "image.jpg");

				image.setImageBitmap(decodeSampledBitmapFromFile(
						file.getAbsolutePath(), 480, 320));
			}

		}
	}

	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
			int reqHeight) { // BEST QUALITY MATCH

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		int inSampleSize = 1;

		if (height > reqHeight) {
			inSampleSize = Math.round((float) height / (float) reqHeight);
		}

		int expectedWidth = width / inSampleSize;

		if (expectedWidth > reqWidth) {
			// if(Math.round((float)width / (float)reqWidth) > inSampleSize) //
			// If bigger SampSize..
			inSampleSize = Math.round((float) width / (float) reqWidth);
		}

		options.inSampleSize = inSampleSize;

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(path, options);
	}

	/*
	 * public String getPath(Uri contentUri) {
	 * 
	 * // can post image String[] proj = { MediaColumns.DATA }; Cursor cursor =
	 * managedQuery(contentUri, proj, // Which columns to // return null, //
	 * WHERE clause; which rows to return (all rows) null, // WHERE clause
	 * selection arguments (none) null); // Order-by clause (ascending by name)
	 * int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
	 * cursor.moveToFirst();
	 * 
	 * return cursor.getString(column_index); }
	 */
}
