package com.nabaci.me.multinotes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public TextView tvText;
    public TextView tvTime;

    public NoteViewHolder(View view) {
        super(view);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvText = (TextView) view.findViewById(R.id.tv_text);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
    }
}
