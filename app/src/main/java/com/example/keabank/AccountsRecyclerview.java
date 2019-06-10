package com.example.keabank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keabank.Model.Accounts;

import java.util.List;

public class AccountsRecyclerview extends RecyclerView.Adapter<AccountsRecyclerview.ViewHolder> {

    private List<Accounts> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int Activityname;
    private int layoutplace;

    // data is passed into the constructor
    AccountsRecyclerview(Context context,int Activityname,int layoutplace, List<Accounts> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.Activityname=Activityname;
        this.layoutplace=layoutplace;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutplace, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(AccountsRecyclerview.ViewHolder holder, int position) {
        Accounts accounts = mData.get(position);
        holder.accountName.setText(accounts.getAccountName());
        holder.reg.setText(accounts.getRegistrationnumber().toString());
        holder.accountnumber.setText(accounts.getAccountNumber().toString());
        holder.currentdeposit.setText(accounts.getCurrentDeposit().toString());
        holder.moneyAvailable.setText("avaliable" + accounts.getCurrentDeposit());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView accountName;
        TextView reg;
        TextView accountnumber;
        TextView currentdeposit;
        TextView moneyAvailable;

        ViewHolder(View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.Accounts_accountname);
            reg=itemView.findViewById(R.id.reg);
            currentdeposit=itemView.findViewById(R.id.accounts_currentdeposit);
            moneyAvailable=itemView.findViewById(R.id.accounts_depositavailable);
            accountnumber=itemView.findViewById(R.id.Accounts_accountnumber);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    Accounts getItem(int id) {

        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(AccountsRecyclerview.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}