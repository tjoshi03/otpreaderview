package govirtual360.otpreaderview.Reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import govirtual360.otpreaderview.Interface.OTPListener;

/**
 * Created by Saurabh Pavashe on 9/25/2017.
 */

public class OtpReaderBroadcast extends BroadcastReceiver {

    private static final String TAG = "OtpReader";
    private static String receiverString;
    private static OTPListener otpListener;

    public static void bind(OTPListener listener, String sender) {
        otpListener = listener;
        receiverString = sender;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {

            final Object[] pdusArr = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdusArr.length; i++) {

                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusArr[i]);
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();
                Log.i(TAG, "senderNum: " + senderNum + " message: " + message);

                if (!TextUtils.isEmpty(receiverString) && senderNum.contains(receiverString)) { //If message received is from required number.
                    //If bound a listener interface, callback the overriden method.
                    if (otpListener != null) {
                        otpListener.otpReceive(message);
                    }
                }
            }
        }
    }
    public static void unbind() {
        otpListener = null;
        receiverString = null;
    }

}
