package in.nfclocations.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import in.nfclocations.Adapter.ChatAdapter;
import in.nfclocations.Callbacks.MessageReceived;
import in.nfclocations.Models.ChatMessage;
import in.nfclocations.NetworkRequests.SendMessage;
import in.nfclocations.Utilities.Constants;
import in.nfclocations.Db.DbHelper;
import in.nfclocations.R;
import in.nfclocations.Utilities.Utility;
import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity implements MessageReceived{

    private static final String TAG = "MainActivity";
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendMessageButton;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DbHelper dbHelper;
    private SendMessage sendMessage;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagesRecyclerView = (RecyclerView) findViewById(R.id.chatMessagesRecyclerView);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendMessageButton = (ImageButton) findViewById(R.id.sendMessageButton);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        dbHelper = new DbHelper(MainActivity.this);
        linearLayoutManager.setReverseLayout(true);

        chatAdapter = new ChatAdapter(MainActivity.this,dbHelper.getCompleteChat());
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesRecyclerView.setAdapter(chatAdapter);
        sendMessage = new SendMessage();

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageEditText.getText().toString();
                messageEditText.setText(null);

                if (message.length()!=0 && message.startsWith("#")){

                    if (message.length()==4){
                        chatAdapter.addMessage(new ChatMessage(message, Utility.getCurrentTime(), Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                        dbHelper.addMessageToDb(new ChatMessage(message,Utility.getCurrentTime(),Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                        sendMessage.sendMessageToBot(message , MainActivity.this);
                    }
                    else if (message.length()==3) {
                        chatAdapter.addMessage(new ChatMessage(message, Utility.getCurrentTime(), Constants.IS_SENDED, Constants.TYPE_MESSAGE_IMAGE));
                        dbHelper.addMessageToDb(new ChatMessage(message, Utility.getCurrentTime(), Constants.IS_SENDED, Constants.TYPE_MESSAGE_IMAGE));
                        sendMessage.sendMessageToBot(message, MainActivity.this);
                    }

                    else{
                        chatAdapter.addMessage(new ChatMessage(message,Utility.getCurrentTime(), Constants.IS_SENDED,Constants.TYPE_MESSAGE_VIDEO));
                        dbHelper.addMessageToDb(new ChatMessage(message,Utility.getCurrentTime(),Constants.IS_SENDED,Constants.TYPE_MESSAGE_VIDEO));
                        sendMessage.sendMessageToBot(message , MainActivity.this);
                    }
                }
            }
        });

    }

    @Override
    public void onMessageReceived(final ChatMessage message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatAdapter.addMessage(message);
            }
        });

        dbHelper.addMessageToDb(message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
