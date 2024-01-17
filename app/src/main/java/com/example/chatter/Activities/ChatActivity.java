package com.example.chatter.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.chatter.Adapters.ChatAdapter;
import com.example.chatter.Database.DatabaseRepository;
import com.example.chatter.Models.Message;
import com.example.chatter.Models.MessageData;
import com.example.chatter.Models.ServerMessage;
import com.example.chatter.Models.UserModel;
import com.example.chatter.R;
import com.example.chatter.RespondModels.MessageResponse;
import com.example.chatter.Tools;
import com.example.chatter.Web.RetroServer;
import com.example.chatter.Web.WebInterface;
import com.example.chatter.databinding.ActivityChatBinding;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatAdapter adapter;

    //sender & receiver objects
    private String receiverID;
    private String senderID;
    private UserModel receiverObject;

    private DatabaseRepository databaseRepository;
    private List<Message>messageList;
    String minTime = "0";
    boolean chatsInitialized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //for customize toolBar
        setToolBar();

        databaseRepository = new DatabaseRepository(this);
        messageList = new ArrayList<>();

        //for get receiver & user is
        getSenderAndUserIDS();

        //for change fabIcon
        changeFabIcon();

        //for customize recyclerView
        initRecyclerView();

        getChatsFromLocaleDatabase();

        binding.fabIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessageProcess();

            }
        });

    }

    private void getChatsFromLocaleDatabase() {

        databaseRepository.getChat(Integer.parseInt(senderID), Integer.parseInt(receiverID)).observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messageList) {

                if (!messageList.isEmpty()) {

                    minTime = messageList.get(messageList.size()-1).getLastUpdate();

                }

                if (minTime.length() < 2) {

                    minTime = "0";

                }

                if (!chatsInitialized) {

                    getChatsFromServer();

                }

                adapter.updateItems(messageList);
                //binding.chatRecyclerView.scrollToPosition(messageList.size()-1);

            }
        });

    }

    //for get receiver & user is
    private void getSenderAndUserIDS() {

        Intent intent = getIntent();
        receiverID = intent.getStringExtra("userID");
        senderID = intent.getStringExtra("senderID");
        databaseRepository.getUserByID(Integer.parseInt(receiverID)).observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {

                if (userModel != null) {

                    receiverObject = userModel;
                    updateUI();

                }

            }
        });

    }

    private void getChatsFromServer() {

        if (chatsInitialized) {
            return;
        }

        chatsInitialized = true;

        WebInterface webInterface = RetroServer.connectRetrofit().create(WebInterface.class);
        Call<List<ServerMessage>>getMessage = webInterface.get_chats(senderID, minTime);
        getMessage.enqueue(new Callback<List<ServerMessage>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerMessage>> call, @NonNull Response<List<ServerMessage>> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        System.out.println("GOT");
                        List<Message>messageList = new ArrayList<>();

                        for (ServerMessage serverMessage : response.body()) {

                            System.out.println(serverMessage.getBODY());
                            Message message = new Message(Long.parseLong(serverMessage.getMESSAGE_ID()), serverMessage.getSENDER(), serverMessage.getRECEIVER(), serverMessage.getBODY(), serverMessage.getSENT_TIME(), serverMessage.getRECEIVE_TIME(), serverMessage.getCHAT_THREAD(), serverMessage.getNAME(), serverMessage.getPHONE_NUMBER(), serverMessage.getREGISTRATION_DATE(), serverMessage.getLAST_SEEN(), serverMessage.getPROFILE_PICTURE(), getSeenOrNot(Integer.parseInt(serverMessage.getSEEN())), serverMessage.getMESSAGE_TYPE(), serverMessage.getLAST_UPDATE(), true, "none");
                            messageList.add(message);
                            databaseRepository.saveMessages(messageList);

                        }

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ServerMessage>> call, @NonNull Throwable t) {

                Toast.makeText(ChatActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void updateUI() {

        binding.userNameText.setText(receiverObject.getName());
        binding.statusText.setText(String.valueOf(receiverObject.getLastSeen()));

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        hideKeyboard();

    }

    //for hide keyboard
    private void hideKeyboard() {

        if (getCurrentFocus() != null) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }


    //method for send message
    private void sendMessageProcess() {

        String message = binding.messageText.getText().toString();

        if (message.isEmpty()) {

            MediaPlayer.create(getApplicationContext(), R.raw.beep).start();

        } else {
            Message messageObject = new Message(adapter.getItemCount(), Tools.getFormattedTimeEvent(System.currentTimeMillis()),
                    message, true, adapter.getItemCount() % 5 == 0);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    adapter.insertItem(new Message(adapter.getItemCount(), Tools.getFormattedTimeEvent(System.currentTimeMillis()),
                           message, false, adapter.getItemCount() % 5 == 0));
                   binding.chatRecyclerView.scrollToPosition(adapter.getItemCount()-1);

                }
            }, 1000);

//            WebInterface webInterface = RetroServer.connectRetrofit().create(WebInterface.class);
//            Call<MessageResponse>sendMessage = webInterface.sendMessage(senderID, receiverID, message);
//            sendMessage.enqueue(new Callback<MessageResponse>() {
//                @Override
//                public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
//
//                    if (response.isSuccessful()) {
//
//                        if (response.body() != null) {
//
//                            if (response.body().getCode() == 1) {
//
//                               Message messageObject = new Message(adapter.getItemCount(), Tools.getFormattedTimeEvent(System.currentTimeMillis()),
//                                       message, true, adapter.getItemCount() % 5 == 0);
//
////                                MessageData messageData = response.body().getData();
////                                Message messageObject = new Message();
////                                messageObject.setMessageID(Long.parseLong(messageData.getMESSAGE_ID()));
////                                messageObject.setReceiver(messageData.getRECEIVER());
////                                messageObject.setBody(messageData.getBODY());
////                                messageObject.setSendTime(messageData.getSENT_TIME());
////                                messageObject.setReceiveTime(messageData.getRECEIVE_TIME());
////                                messageObject.setChatThread(messageData.getCHAT_THREAD());
////                                messageObject.setName(receiverObject.getName());
////                                messageObject.setPhoneNumber(receiverObject.getPhoneNumber());
////                                messageObject.setRegistrationDate(receiverObject.getRegistrationDate());
////                                messageObject.setLastSeen(String.valueOf(receiverObject.getLastSeen()));
////                                messageObject.setProfilePicture(receiverObject.getProfilePicture());
////                                messageObject.setSeen(getSeenOrNot(Integer.parseInt(messageData.getSEEN())));
////                                messageObject.setMessageType(messageData.getMESSAGE_TYPE());
////                                messageObject.setLastUpdate(messageData.getLAST_UPDATE());
////                                messageObject.setFromMe(true);
////                                messageObject.setMediaLink("No");
//
//                                messageList.add(messageObject);
//                                databaseRepository.saveMessages(messageList);
//
//                                binding.messageText.getText().clear();
//                                adapter.insertItem(messageObject);
//                                binding.chatRecyclerView.scrollToPosition(adapter.getItemCount()-1);
//
//                            } else {
//
//                                Toast.makeText(ChatActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        } else {
//
//                            Toast.makeText(ChatActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    } else {
//
//                        assert response.errorBody() != null;
//                        Toast.makeText(ChatActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
//
//                    Toast.makeText(ChatActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    System.out.println(t.getLocalizedMessage());
//
//                }
//            });

        }

    }

    private boolean getSeenOrNot(int value) {
        return value == 1;
    }

    //for customize recyclerView
    private void initRecyclerView() {

        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRecyclerView.setHasFixedSize(true);
        adapter = new ChatAdapter(this);
        binding.chatRecyclerView.setAdapter(adapter);

//        adapter.insertItem(new Message(adapter.getItemCount(), Tools.getFormattedTimeEvent(System.currentTimeMillis()), "Hey", true, adapter.getItemCount() % 5 == 0));
//        adapter.insertItem(new Message(adapter.getItemCount(), Tools.getFormattedTimeEvent(System.currentTimeMillis()), "Test", false, adapter.getItemCount() % 5 == 0));


    }

    //for change fabIcon
    private void changeFabIcon() {

        binding.messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do-nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                binding.fabIcon.setImageResource(R.drawable.send_icon);

                if (binding.messageText.getText().toString().isEmpty()) {

                    binding.fabIcon.setImageResource(R.drawable.mice_icon);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //do-nothing
            }
        });

    }

    //for customize toolBar
    private void setToolBar() {

        setSupportActionBar(binding.toolBar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            Tools.setSystemBarColorInt(this, Color.parseColor("#054D44"));

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}