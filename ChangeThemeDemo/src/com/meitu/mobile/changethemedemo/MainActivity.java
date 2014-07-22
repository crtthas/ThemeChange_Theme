package com.meitu.mobile.changethemedemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Adapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	private ListView mlistView;
	private Handler mHandler;
	private Context context;
	private MyListViewAdapter madapter;
	private ProgressDialog pDialog;
	private SharedPreferences sp;
	private static final int MESSAGE_SEARCHED_SKIP = 0;
	private static final int MESSAGE_SEARCHING_SKIP = 1;
	private static final int MESSAGE_SEARCHED_SKIP_GET_NOTHING = 2;
	
	//包名 org.leepood.skin.
	private static final String PACKAGE_NAME_OF_SKIP = "org.leepood.skin.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		
	}
	
	private void init(){
		
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				switch (msg.what) {
				case MESSAGE_SEARCHED_SKIP:
					ArrayList<Object>  arrylist = (ArrayList<Object>) msg.obj;
					madapter = new MyListViewAdapter(context, arrylist);
					mlistView.setAdapter(madapter);
					Toast.makeText(context, "已经查找到安装的皮肤", Toast.LENGTH_SHORT);
					pDialog.dismiss();
					break;
				
				case MESSAGE_SEARCHING_SKIP:
					Toast.makeText(context, "未找到任何皮肤", Toast.LENGTH_SHORT);
					break;

				default:
					break;
				}
			}
		};
		sp = this.getSharedPreferences("config", Context.MODE_WORLD_WRITEABLE);
		sp.registerOnSharedPreferenceChangeListener(sharedPreListner);
		
		mlistView=(ListView) findViewById(R.id.listview_showtheme);
		mlistView.setItemsCanFocus(false);
		mlistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		mlistView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu contextMenu, View arg1,
					ContextMenuInfo contextMenuInfo) {
				contextMenu.add("使用该主题");
			}
		});
		
	}
	
	Runnable sreachShin = new Runnable() {
		
		@Override
		public void run() {
			PackageManager packageManager = context.getPackageManager();
			List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.PERMISSION_GRANTED);
			ArrayList<Object> listSkins = new ArrayList<Object>();
			for(PackageInfo info: packages){
				if(info.packageName.startsWith(PACKAGE_NAME_OF_SKIP));
			}
			
			if(listSkins.size() > 0){
				Message msg = mHandler.obtainMessage();
				msg.obj = listSkins;
				msg.what = MESSAGE_SEARCHED_SKIP;
				mHandler.sendMessage(msg);
			}else{
				mHandler.sendEmptyMessage(MESSAGE_SEARCHED_SKIP_GET_NOTHING);
			}
		}
		
	};
	
	public void onThemeChanged(String newThemePackageName){
		try{
			Context themeContext = this.createPackageContext(newThemePackageName, CONTEXT_IGNORE_SECURITY);
			Resources res = themeContext.getResources();
			setControlsStyle(res);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setControlsStyle(Resources res){
		mlistView.setBackgroundColor(Color.YELLOW);
	}
	
	SharedPreferences.OnSharedPreferenceChangeListener sharedPreListner 
	= new SharedPreferences.OnSharedPreferenceChangeListener(){

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String string) {
			System.out.println("theme change");
			onThemeChanged(sharedPreferences.getString(string, "")); 
		}
		
	};
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		PackageInfo info = (PackageInfo) madapter.getItem(menuInfo.position);
		
		sp.edit().putString("themePackage", info.packageName).commit();
		return super.onContextItemSelected(item);
	};
	
	public class MyListViewAdapter extends BaseAdapter{
		
		LayoutInflater mInflater;   
        ArrayList mdatas;
        ViewHolder mViewHodler;
        PackageManager mPackagemanager;
		
		public MyListViewAdapter(Context context,ArrayList<Object> list) {
			mInflater = LayoutInflater.from(context);
			mdatas = list;
			mPackagemanager = context.getPackageManager();
		}
		
		class ViewHolder{
			public ImageView mImg;
			public TextView mtextView;
		}
		
		@Override
		public int getCount() {
			return mdatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mdatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.listview_item, null);
			}
			mViewHodler.mImg = (ImageView) convertView.findViewById(R.id.imgview_listview_item);
			mViewHodler.mtextView = (TextView) convertView.findViewById(R.id.textview_listview_item);
			PackageInfo packageInfo = (PackageInfo) mdatas.get(position);
			mViewHodler.mImg.setImageDrawable(packageInfo.applicationInfo.loadIcon(mPackagemanager));
			mViewHodler.mtextView.setText(packageInfo.applicationInfo.loadLabel(mPackagemanager));
			return convertView;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
