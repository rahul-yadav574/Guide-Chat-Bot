package in.nfclocations.NetworkRequests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import in.nfclocations.Callbacks.MessageReceived;
import in.nfclocations.Models.ChatMessage;
import in.nfclocations.Utilities.Constants;
import in.nfclocations.Utilities.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Brekkishhh on 25-08-2016.
 */
public class SendMessage {

    private OkHttpClient httpClient;
    private static final String TAG = "SendMessage";

    public SendMessage() {
        this.httpClient =  new OkHttpClient();
    }

    public void sendMessageToBot(final String messageBody, final MessageReceived messageReceived){

        Request request = new Request.Builder()
                .url("http://drivesmart.herokuapp.com/guidebot?q="+messageBody.substring(1))
                .get()
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageReceived.onMessageReceived(new ChatMessage("Unable to fetch message...bot is currently down",Utility.getCurrentTime(), Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.has("status")){

                        String data = jsonObject.getString("data");

                        if (jsonObject.getBoolean("status")){
                            String type = jsonObject.getString("type");
                            messageReceived.onMessageReceived(new ChatMessage(data,Utility.getCurrentTime(),Constants.IS_RECEIVED,type));
                        }
                        else
                        {
                            messageReceived.onMessageReceived(new ChatMessage(data, Utility.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                        }
                    }

                    else {
                        messageReceived.onMessageReceived(new ChatMessage("Check Internet Connection",Utility.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    }
                }catch (JSONException e){
                    messageReceived.onMessageReceived(new ChatMessage("Problem Contacting With Bot Servers",Utility.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    Log.e(TAG,e.getMessage());
                }

            }
        });


    }


    public void sendImageToBot(String base64Image, final MessageReceived messageReceived){

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("q",base64Image);
        }catch (JSONException ex){
            ex.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(Constants.TYPE_JSON,base64Image);

        Request request = new Request.Builder()
                .url("http://drivesmart.herokuapp.com/guidebot")
                .post(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageReceived.onMessageReceived(new ChatMessage("Unable to fetch message...bot is currently down",Utility.getCurrentTime(), Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseString = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.has("status")){

                        String data = jsonObject.getString("data");

                        if (jsonObject.getBoolean("status")){
                            String type = jsonObject.getString("type");
                            messageReceived.onMessageReceived(new ChatMessage(data,Utility.getCurrentTime(),Constants.IS_RECEIVED,type));
                        }
                        else
                        {
                            messageReceived.onMessageReceived(new ChatMessage(data, Utility.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                        }
                    }

                    else {
                        messageReceived.onMessageReceived(new ChatMessage("Check Internet Connection",Utility.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    }


                }catch (JSONException e){
                    Log.e(TAG,e.getMessage());
                }
            }
        });
    }
}
