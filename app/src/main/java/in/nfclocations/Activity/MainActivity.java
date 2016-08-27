package in.nfclocations.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

import in.nfclocations.Adapter.ChatAdapter;
import in.nfclocations.Callbacks.MessageReceived;
import in.nfclocations.Db.DbHelper;
import in.nfclocations.Models.ChatMessage;
import in.nfclocations.NetworkRequests.SendMessage;
import in.nfclocations.R;
import in.nfclocations.Utilities.Constants;
import in.nfclocations.Utilities.Utility;

public class MainActivity extends AppCompatActivity implements MessageReceived{

    private static final String TAG = "MainActivity";
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendMessageButton;
    private ImageButton sendImageButton;
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
        sendImageButton = (ImageButton) findViewById(R.id.sendImageButton);
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

                    chatAdapter.addMessage(new ChatMessage(message, Utility.getCurrentTime(), Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                    dbHelper.addMessageToDb(new ChatMessage(message,Utility.getCurrentTime(),Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                    sendMessage.sendMessageToBot(message , MainActivity.this);

                }
            }
        });

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(imageIntent,"Select Image To Send"),Constants.RQ_GALLERY_IMAGE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.RQ_GALLERY_IMAGE && resultCode == RESULT_OK  && data.getData()!=null){

            Uri imageUri = data.getData();

            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                chatAdapter.addMessage(new ChatMessage(imageUri.toString(),Utility.getCurrentTime(),Constants.IS_SENDED,Constants.TYPE_MESSAGE_IMAGE));
                dbHelper.addMessageToDb(new ChatMessage(imageUri.toString(),Utility.getCurrentTime(),Constants.IS_SENDED,Constants.TYPE_MESSAGE_IMAGE));
                sendMessage.sendImageToBot(Utility.generateBase64String(image),MainActivity.this);

                //Now we have to send the image to the servers for identification and get a response
            }catch (IOException ex){
                ex.printStackTrace();
            }

        }
    }
}
