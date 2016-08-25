package in.nfclocations.Adapter;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Collections;
import java.util.List;

import in.nfclocations.Models.ChatMessage;
import in.nfclocations.R;
import in.nfclocations.Utilities.Constants;

/**
 * Created by Brekkishhh on 24-08-2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ChatMessage> messages;

    public ChatAdapter(List<ChatMessage> messages) {
        Collections.reverse(messages);
        this.messages = messages;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SendChatViewHolder){

            TextView messageTextView = ((SendChatViewHolder) holder).messageTextView;
            ImageView messageImageView = ((SendChatViewHolder) holder).messageImageView;
            VideoView messageVideoView = ((SendChatViewHolder) holder).messageVideoView;
            MediaController mediaController = new MediaController(((SendChatViewHolder) holder).messageVideoView.getContext());
            mediaController.setAnchorView(messageVideoView);
            mediaController.setMediaPlayer(messageVideoView);
            messageVideoView.setMediaController(mediaController);
            messageTextView.setVisibility(View.GONE);
            messageVideoView.setVisibility(View.GONE);
            messageImageView.setVisibility(View.GONE);


            if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_TEXT)){
                messageTextView.setVisibility(View.VISIBLE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.GONE);
                messageTextView.setText(messages.get(position).getMessage());
            }

            else if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_IMAGE)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.VISIBLE);
                messageVideoView.setVisibility(View.GONE);
            }

            else if ( messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_VIDEO)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.VISIBLE);
                messageVideoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
                messageVideoView.start();
            }

        }else if (holder instanceof ReceivedChatViewHolder){

            TextView messageTextView = ((ReceivedChatViewHolder) holder).messageTextView;
            ImageView messageImageView = ((ReceivedChatViewHolder) holder).messageImageView;
            VideoView messageVideoView = ((ReceivedChatViewHolder) holder).messageVideoView;
            MediaController mediaController = new MediaController(((ReceivedChatViewHolder) holder).messageVideoView.getContext());
            mediaController.setAnchorView(messageVideoView);
            mediaController.setMediaPlayer(messageVideoView);
            messageVideoView.setMediaController(mediaController);
            messageVideoView.setVisibility(View.GONE);
            messageImageView.setVisibility(View.GONE);
            messageTextView.setVisibility(View.GONE);
            mediaController.setVisibility(View.VISIBLE);

            if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_TEXT)){
                messageTextView.setVisibility(View.VISIBLE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.GONE);
                messageTextView.setText(messages.get(position).getMessage());

            }

            else if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_IMAGE)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.VISIBLE);
                messageVideoView.setVisibility(View.GONE);
            }

            else if ( messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_VIDEO)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.VISIBLE);
                messageVideoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
                messageVideoView.start();
            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (messages.get(position).isSended().equals(Constants.IS_SENDED)){
            return Constants.SENDER;
        }
        return Constants.RECEIVED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.SENDER){
            return new SendChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message,parent,false));
        }

        else if (viewType == Constants.RECEIVED){
            return new ReceivedChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recieved_message,parent,false));
        }

        return null;
    }

    protected class SendChatViewHolder extends RecyclerView.ViewHolder{

        private TextView messageTextView;
        private ImageView messageImageView;
        private VideoView messageVideoView;


        public SendChatViewHolder(View itemView) {
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.sendMessageTextView);
            messageImageView = (ImageView) itemView.findViewById(R.id.sendMessageImageView);
            messageVideoView = (VideoView) itemView.findViewById(R.id.sendMessageVideoView);

        }
    }

    protected class ReceivedChatViewHolder extends RecyclerView.ViewHolder{

        private TextView messageTextView;
        private ImageView messageImageView;
        private VideoView messageVideoView;


        public ReceivedChatViewHolder(View itemView) {
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.receivedMessageTextView);
            messageImageView = (ImageView) itemView.findViewById(R.id.receivedMessageImageView);
            messageVideoView = (VideoView) itemView.findViewById(R.id.receivedMessageVideoView);
        }
    }


    public void addMessage(ChatMessage message){
        Collections.reverse(messages);
        messages.add(message);
        Collections.reverse(messages);
        this.notifyDataSetChanged();
    }

    public void changeChatList(List<ChatMessage> messages){
        Collections.reverse(messages);
        this.messages  = messages;
        this.notifyDataSetChanged();
    }

    public int getChatListSize(){
        return this.messages.size();
    }

}
