package com.example.chatter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatter.Interfaces.ListClickEvents;
import com.example.chatter.Models.User;
import com.example.chatter.Models.UserModel;
import com.example.chatter.R;
import com.example.chatter.databinding.AllUsersDesignBinding;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private final Context context;
    private final List<UserModel>userList;
    private final ListClickEvents listClickEvents;

    public UsersAdapter(Context context, List<UserModel> userList, ListClickEvents listClickEvents) {
        this.context = context;
        this.userList = userList;
        this.listClickEvents = listClickEvents;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.all_users_design, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        UserModel user = userList.get(position);
        holder.binding.userNameText.setText(user.getName());
        holder.binding.phoneNumberText.setText(user.getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        public AllUsersDesignBinding binding;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = AllUsersDesignBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listClickEvents.onClickUserItem(getAdapterPosition());

                }
            });

        }
    }

}
