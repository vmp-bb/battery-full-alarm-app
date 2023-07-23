package com.example.mybatt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.mybatt.R.raw.iphone;

public class MainActivity extends AppCompatActivity {

    private TextView text,txt;

    private MediaPlayer mysong;
    private ImageButton imageButton;
    private static Uri alam;

    private final BroadcastReceiver battery = new BroadcastReceiver() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra( BatteryManager.EXTRA_LEVEL, 0 );
            text.setText( String.valueOf( level ) + "%" );
            Random r=new Random();
            int x= (int) (level*0.08);
            txt.setText( x+" hrs "+r.nextInt(60)+" mins" );
            if (level == 92) {
                mysong.start();
                imageButton.setImageResource( R.drawable.onsound );
                Toast.makeText( MainActivity.this, "click the mute to stop the song", Toast.LENGTH_LONG ).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel( "my notification",
                            "my notification", NotificationManager.IMPORTANCE_DEFAULT );
                    NotificationManager manager = getSystemService( NotificationManager.class );
                    manager.createNotificationChannel( channel );
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder( MainActivity.this, "my notification" );
                builder.setContentTitle( "HI DUDE.\uD83D\uDD0B⚡\uD83E\uDDD0" );
                builder.setContentText( "battery is FULL.." );
                builder.setSmallIcon( R.drawable.ic_launcher_foreground );
                builder.setAutoCancel( true );
                builder.setNumber( 1 );
                builder.setChronometerCountDown( true );
                

               /* Intent intent1 = new Intent();
                intent1.setFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                intent1.setFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                intent1.setDataAndType( FileProvider.getUriForFile( MainActivity.this, BuildConfig.APPLICATION_ID, mysong.stop() ) );
                PendingIntent go = PendingIntent.getActivity( MainActivity.this, 0, intent1, PendingIntent.FLAG_ONE_SHOT );


                builder.addAction( R.drawable.music_off_24, "yes", null );
                */



                //Intent intent1=new Intent(MainActivity.);
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //  PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                // builder.setContentIntent(pendingIntent);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from( MainActivity.this );
                managerCompat.notify( 1, builder.build() );

            } else {
                text.setTextColor( R.color.purple_700 );
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text=findViewById(R.id.text);

        imageButton=findViewById(R.id.imageButton);
        txt=findViewById( R.id.txt );
        mysong=MediaPlayer.create(MainActivity.this, R.raw.remixiphone);

        this.registerReceiver(this.battery,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        imageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysong.stop();
                imageButton.setImageResource( R.drawable.offsound );
            }
        } );

    }
}