package in.nfclocations;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendMessageButton;
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagesRecyclerView = (RecyclerView) findViewById(R.id.chatMessagesRecyclerView);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendMessageButton = (ImageButton) findViewById(R.id.sendMessageButton);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        chatAdapter = new ChatAdapter(new ArrayList<ChatMessage>());
        messagesRecyclerView.setAdapter(chatAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageEditText.getText().toString();

                if (message.length()!=0 && message.startsWith("#")){
                    chatAdapter.addMessage(new ChatMessage(message,"19:40",Constants.IS_SENDED));
                }
            }
        });


        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("i want to know about shahjahan","19:30",Constants.IS_SENDED));
        messages.add(new ChatMessage("i dont know what is this","19:30",Constants.IS_RECEIVED));
        messages.add(new ChatMessage("this is the well knwn thing of the stuff","19:30",Constants.IS_RECEIVED));
        messages.add(new ChatMessage("weel  what is thsi","19:30",Constants.IS_SENDED));
        messages.add(new ChatMessage("we are working on this","19:30",Constants.IS_RECEIVED));
        messages.add(new ChatMessage("what is this what you all about","19:30",Constants.IS_RECEIVED));
        messages.add(new ChatMessage("i dont knnow about anything","19:30",Constants.IS_RECEIVED));
        messages.add(new ChatMessage("i want to know about shahjahan","19:30",Constants.IS_SENDED));
        messages.add(new ChatMessage("this is something good i dont know","19:30",Constants.IS_SENDED));

        chatAdapter.changeChatList(messages);

    }
}
