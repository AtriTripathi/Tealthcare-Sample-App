package com.atritripathi.tealthcaresampleapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final LayoutInflater mInflater;
    private List<Message> mMessages;

    public MessageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageItemView;

        private MessageViewHolder(final View itemView) {
            super(itemView);
            messageItemView = itemView.findViewById(R.id.tv_message);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(itemView.getContext(), MantraDetailsActivity.class);
//                    intent.putExtra("mantra_position", getAdapterPosition() + 1);
//                    itemView.getContext().startActivity(intent);
//                }
//            });
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
        } else {
            holder.messageItemView.setText("No Messages");
        }
    }


    /**
     * To assign the mantras to the mantra list
     * @param messages is the list of mantras
     */
    void setMessages(List<Message> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }


    /**
     * To handle the no of views to create on the screen.
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
