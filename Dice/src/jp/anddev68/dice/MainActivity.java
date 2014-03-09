package jp.anddev68.dice;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;


public class MainActivity extends Activity{

	Bitmap[] mImg;
	
	Handler mHandler;
	Timer mTimer;
	
	boolean isPlaying;
	
	ImageView mImageView;
	Button mButton;
	
	AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		mImg = new Bitmap[6];
		Resources r = this.getResources();
		mImg[0] = BitmapFactory.decodeResource(r, R.drawable.s1);
		mImg[1] = BitmapFactory.decodeResource(r, R.drawable.s2);
		mImg[2] = BitmapFactory.decodeResource(r, R.drawable.s3);
		mImg[3] = BitmapFactory.decodeResource(r, R.drawable.s4);
		mImg[4] = BitmapFactory.decodeResource(r, R.drawable.s5);
		mImg[5] = BitmapFactory.decodeResource(r, R.drawable.s6);
		
		mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageView.setImageBitmap(mImg[0]);
		mButton = (Button) findViewById(R.id.button1);
		
		mHandler = new Handler();
		mTimer = new Timer();
	
		isPlaying = false;
		
		// Create the adView
		 // AdView をリソースとしてルックアップしてリクエストを読み込む
		adView = new AdView(this, AdSize.BANNER, "a1531c574d7c4d8");
		
	    // 属性 android:id="@+id/mainLayout" が与えられているものとして
	    // LinearLayout をルックアップする
	    LinearLayout layout = (LinearLayout)findViewById(R.id.mainView);

	    // adView を追加する
	    layout.addView(adView);

	    // 一般的なリクエストを行って広告を読み込む
	    adView.loadAd(new AdRequest());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onPush(View v){
		if(isPlaying){		//	とめたとき
			isPlaying = false;
			mTimer.cancel();
			mButton.setText("Start");
			
			
			
		}else{				//	振ったとき
			mButton.setText("Stop");
			isPlaying = true;
			mTimer = new Timer();
			
			mTimer.schedule(new TimerTask(){

				@Override
				public void run() {
					mHandler.post(new Runnable(){

						@Override
						public void run() {
							Random r = new Random();
							mImageView.setImageBitmap(mImg[r.nextInt(6)]);
							mImageView.invalidate();
						}
						
						
					});
				}
				
			}, 0, 100);
		}
	
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	
	
}
