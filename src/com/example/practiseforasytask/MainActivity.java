package com.example.practiseforasytask;

import java.security.PublicKey;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView text;
	private Button button;
	private Button Tbutton;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(Message msg){
			switch (msg.what) {
			case 1:
				text.setText("nice to meet you");
				break;

			case 2:
				text.setText("hello world");
			default:
				break;
			}
		}
	};
	
	public class MyHandler extends Handler{
		private Looper myLooper;
		
		public MyHandler(Looper myLooper){
			this.myLooper = myLooper;
		}
		
		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {
			case 2:
				Log.e("threadmsg",""+msg.what);
				Message message = handler.obtainMessage();
				message.what = 2;
				message.sendToTarget();
				myLooper.quitSafely();
				break;

			default:
				super.handleMessage(msg);
				break;
			}
		}
	}
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.text);
        button = (Button)findViewById(R.id.change);
        Tbutton = (Button)findViewById(R.id.change2);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		});
        
        Tbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Looper.prepare();
						MyHandler myHandler = new MyHandler(Looper.myLooper());
						Message msg = new Message();
						msg.what = 2;
						myHandler.sendMessage(msg);
						Looper.loop();
					}
				}).start();
			}
		});
        
        
        
    }


   
    
}
