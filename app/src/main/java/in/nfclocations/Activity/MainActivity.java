package in.nfclocations.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import in.nfclocations.Adapter.ChatAdapter;
import in.nfclocations.Models.ChatMessage;
import in.nfclocations.Utilities.Constants;
import in.nfclocations.Db.DbHelper;
import in.nfclocations.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendMessageButton;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagesRecyclerView = (RecyclerView) findViewById(R.id.chatMessagesRecyclerView);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendMessageButton = (ImageButton) findViewById(R.id.sendMessageButton);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        dbHelper = new DbHelper(MainActivity.this);
        linearLayoutManager.setReverseLayout(true);

        chatAdapter = new ChatAdapter(dbHelper.getCompleteChat());
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesRecyclerView.setAdapter(chatAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageEditText.getText().toString();

                if (message.length()!=0 && message.startsWith("#")){
                    chatAdapter.addMessage(new ChatMessage(message,"19:40", Constants.IS_SENDED));
                    dbHelper.addMessageToDb(new ChatMessage(message,"19:40",Constants.IS_SENDED));
                }
            }
        });

    }
}
