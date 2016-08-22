package in.nfclocations;

import android.annotation.TargetApi;
import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by Brekkishhh on 13-08-2016.
 */

public class ReadTagAsyncTask extends AsyncTask<String,Void,JSONObject> {

    private Tag tag;
    private Context context;
    private static final String TAG = "ReadTagAsyncTask";

    public ReadTagAsyncTask(Context context, Tag tag) {
        this.context = context;
        this.tag = tag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override

    protected JSONObject doInBackground(String... params) {

        String [] techList = tag.getTechList();
        JSONObject jsonObject = new JSONObject();

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
