package in.nfclocations.NetworkRequests;

import java.io.IOException;
import in.nfclocations.Callbacks.MessageReceived;
import in.nfclocations.Models.ChatMessage;
import in.nfclocations.Utilities.Constants;
import in.nfclocations.Utilities.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Brekkishhh on 25-08-2016.
 */
public class SendMessage {

    private OkHttpClient httpClient;

    public SendMessage() {

        this.httpClient =  new OkHttpClient();
    }

    public void sendMessageToBot(String messageBody,final MessageReceived messageReceived){

        Request request = new Request.Builder()
                .url("http://www.google.com")
                .get()
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageReceived.onMessageReceived(new ChatMessage("Unable to fetch message...bot is currently down",Utility.getCurrentTime(), Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                messageReceived.onMessageReceived(new ChatMessage(response.body().string(), Utility.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
            }
        });


    }
}
