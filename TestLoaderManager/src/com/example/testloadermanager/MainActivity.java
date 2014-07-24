package com.example.testloadermanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor> {
	//url
		 public static final String AUTHORITY = "com.cyanogenmod.themes";
		 public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
		 public static final Uri THEME_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "themes");
		 
		 Cursor mCursor;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			getLoaderManager().initLoader(0, null, this);
			
			System.out.println(THEME_CONTENT_URI);
			System.out.println(THEME_CONTENT_URI);
			System.out.println(THEME_CONTENT_URI);
			System.out.println(THEME_CONTENT_URI);
			
			System.out.println("-------go----------");
			System.out.println("-------go----------");
			System.out.println("-------go----------");
			System.out.println("-------go----------");
			System.out.println("-------go----------");
			
			
	        
	        //TextView tv = findViewById(R.id.t);
			
		}
		
		/*private String getSelection(){
			return null;
		}*/

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

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

		@Override
		public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
			// TODO Auto-generated method stub
			ArrayList<String> mComponentFilters = new ArrayList<String>();
			mComponentFilters.add("mods_icons");
			
			String selection;
	        String selectionArgs[] = null;
	        if (mComponentFilters.isEmpty()) {
	            selection = "present_as_theme=?";
	            selectionArgs = new String[] {"1"};
	        } else {
	            StringBuffer sb = new StringBuffer();
	            for(int i=0; i < mComponentFilters.size(); i++) {
	                sb.append(mComponentFilters.get(i));
	                sb.append("=1");
	                if (i !=  mComponentFilters.size()-1) {
	                    sb.append(" OR ");
	                }
	            }
	            selection = sb.toString();
	        }

	        // sort in ascending order but make sure the "default" theme is always first
	        String sortOrder = "(is_default_theme=1) DESC, "
	                + "title ASC";
			
			System.out.println("------------create--------------");
			System.out.println("------------create--------------");
			System.out.println("------------create--------------");
			System.out.println("------------create--------------");
			System.out.println("------------create--------------");
			System.out.println("------------create--------------");
			String[] agrs = new String[]{"1"};
			return new CursorLoader(this, THEME_CONTENT_URI, null, selection, null, null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
			mCursor = arg1;
			
			System.out.println(mCursor.getColumnCount());
			System.out.println(mCursor.getColumnCount());
			System.out.println(mCursor.getColumnCount());
			System.out.println(mCursor.getColumnCount());
			System.out.println(mCursor.getColumnCount());
			System.out.println(mCursor.getColumnCount());
			
			String[] strings = mCursor.getColumnNames();
			for(String str:strings){
				System.out.println(str);
				System.out.println(mCursor.getString(mCursor.getColumnIndex(str)));
			}
			
			/*int pkgIdx = mCursor.getColumnIndex("pkg_name");
	        String pkgName = mCursor.getString(pkgIdx);
	        
	        System.out.println(pkgName);
	        System.out.println(pkgName);
	        System.out.println(pkgName);
	        System.out.println(pkgName);
	        System.out.println(pkgName);*/
	        
	        
			System.out.println("------------finish--------------");
			System.out.println("------------finish--------------");
			System.out.println("------------finish--------------");
			System.out.println("------------finish--------------");
			System.out.println("------------finish--------------");
			System.out.println("------------finish--------------");
		}

		@Override
		public void onLoaderReset(Loader<Cursor> arg0) {
			// TODO Auto-generated method stub
			mCursor = null;
		}
}