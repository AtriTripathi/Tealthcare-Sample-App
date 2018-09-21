package com.atritripathi.tealthcaresampleapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final LayoutInflater mInflater;
    private List<Message> mMessages;
    private static CardView messageCardHolder;
    private static RelativeLayout.LayoutParams layoutParams;


    public MessageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageItemView;
        private final TextView statusTextView;
        private final ConstraintLayout messageCardContent;



        private MessageViewHolder(final View itemView) {
            super(itemView);
            messageItemView = itemView.findViewById(R.id.tv_message);
            statusTextView = itemView.findViewById(R.id.tv_status);
            messageCardContent = itemView.findViewById(R.id.message_card_content);
            messageCardHolder = itemView.findViewById(R.id.message_card_holder);
            layoutParams = (RelativeLayout.LayoutParams) messageCardHolder.getLayoutParams();

        }
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int position) {
        final View itemView = mInflater.inflate(R.layout.message_layout, viewGroup, false);
        return new MessageViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (mMessages != null) {

            holder.messageItemView.setText(mMessages.get(position).getMessageText());

            if (mMessages.get(position).getIsSentMessage()) {
                holder.statusTextView.setText(mMessages.get(position).getStatus());
                holder.messageCardContent.setBackgroundResource(R.color.light_yellow);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                messageCardHolder.setLayoutParams(layoutParams);
            } else {
                holder.statusTextView.setText(mMessages.get(position).getTime());
                holder.messageCardContent.setBackgroundResource(R.color.sky_blue);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                messageCardHolder.setLayoutParams(layoutParams);
            }

        } else {
            holder.messageItemView.setText("No Messages");
        }
    }


    /**
     * To assign the mantras to the mantra list
     *
     * @param messages is the list of mantras
     */
    void setMessages(List<Message> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }


    /**
     * To handle the no of views to create on the screen.
     *
     * @return List size is returned back to the recycler view.
     */
    @Override
    public int getItemCount() {
        if (mMessages != null)
            return mMessages.size();
        else
            return 0;
    }

}
