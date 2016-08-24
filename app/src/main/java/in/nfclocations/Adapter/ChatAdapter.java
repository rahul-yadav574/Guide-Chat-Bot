package in.nfclocations.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import in.nfclocations.Models.ChatMessage;
import in.nfclocations.Utilities.Constants;
import in.nfclocations.R;

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

            ((SendChatViewHolder) holder).messageTextView.setText(messages.get(position).getMessage());

        }else if (holder instanceof ReceivedChatViewHolder){

            ((ReceivedChatViewHolder) holder).messageTextView.setText(messages.get(position).getMessage());

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

        public SendChatViewHolder(View itemView) {
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.sendMessageTextView);
        }
    }

    protected class ReceivedChatViewHolder extends RecyclerView.ViewHolder{

        private TextView messageTextView;

        public ReceivedChatViewHolder(View itemView) {
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.receivedMessageTextView);
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
