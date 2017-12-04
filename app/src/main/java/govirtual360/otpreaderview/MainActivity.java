package govirtual360.otpreaderview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import govirtual360.otpreaderview.Interface.OTPListener;
import govirtual360.otpreaderview.Reciver.OtpReaderBroadcast;

public class MainActivity extends AppCompatActivity implements OTPListener {
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS=100;
    OtpView otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otp=(OtpView)findViewById(R.id.otp_view);
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
        }
        else {
            OtpReaderBroadcast.bind(this,"8291263326");
        }


    }

    @Override
    public void otpReceive(String message) {
        Toast.makeText(this,"Got "+message, Toast.LENGTH_LONG).show();
        Log.d("Otp",message);
      otp.setOTP(message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this,"Permission Granted ",Toast.LENGTH_LONG).show();
                    OtpReaderBroadcast.bind(this,"8291263326");

                } else {
                        Toast.makeText(this,"Permission Not Granted ",Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }
}
}
