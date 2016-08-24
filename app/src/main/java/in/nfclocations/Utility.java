package in.nfclocations;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Brekkishhh on 11-08-2016.
 */
public class Utility {

    private static final String TAG = "Utility";


    @Nullable
    public static Boolean checkNFCStatus(Context context){
        NfcManager nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        NfcAdapter nfcAdapter = nfcManager.getDefaultAdapter();

        if (nfcAdapter == null){
            return null;
        }

        return nfcAdapter.isEnabled();
    }

    public static void toastL(Context context,String string){
        Toast.makeText(context,string, Toast.LENGTH_LONG).show();
    }
    public static void toastS(Context context,String string){
        Toast.makeText(context,string, Toast.LENGTH_SHORT).show();
    }

    public static Boolean checkNetworkConnection(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
