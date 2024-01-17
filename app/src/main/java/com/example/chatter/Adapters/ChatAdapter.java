package com.example.chatter.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatter.Interfaces.ChatClickEvents;
import com.example.chatter.Models.Message;
import com.example.chatter.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CHAT_ME = 100;
    private static final int CHAT_YOU = 200;

    private Context context;
    private List<Message>messageList = new ArrayList<>();
    private ChatClickEvents clickListener;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(final ChatClickEvents clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        if (viewType == CHAT_ME) {

            return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.send_message_design, parent, false));

        } else {

            return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.receive_message_design, parent, false));

        }

    }

    @Override
    public int getItemViewType(int position) {

        if (messageList.get(position).isFromMe()) {

            return CHAT_ME;

        } else {

            return CHAT_YOU;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (holder instanceof ItemViewHolder) {

            final Message message = messageList.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.messageText.setText(message.getBody());
            itemViewHolder.timeText.setText(message.getSendTime());

            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (clickListener != null) {

                        clickListener.onClickItem(view, message, position);

                    }

                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void insertItem(Message message) {

        messageList.add(message);
        notifyItemInserted(getItemCount());

    }

    public void setItems(List<Message>messageList) {

        this.messageList = messageList;

    }

    public void updateItems(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);

        }
    }


}
