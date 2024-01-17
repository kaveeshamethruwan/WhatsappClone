package com.example.chatter.Interfaces;

import android.view.View;

import com.example.chatter.Models.Message;

public interface ChatClickEvents {

    void onClickItem(View view , Message message, int position);

}
