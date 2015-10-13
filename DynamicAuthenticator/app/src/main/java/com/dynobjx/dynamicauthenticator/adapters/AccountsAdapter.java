package com.dynobjx.dynamicauthenticator.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynobjx.dynamicauthenticator.MainActivity;
import com.dynobjx.dynamicauthenticator.R;
import com.dynobjx.dynamicauthenticator.models.Account;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by madeveloper on 10/13/15.
 */
public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {

    private RealmResults<Account> accounts;
    private Context context;
    private MainActivity activity;
    private CountDownTimer[] timer;

    public AccountsAdapter(Context context, RealmResults<Account> accounts) {
        this.context = context;
        this.accounts = accounts;
        this.activity = (MainActivity)context;
        this.timer = new CountDownTimer[accounts.size()];
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_accounts, parent, false);
        ViewHolder  holder = new ViewHolder (v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail;
        TextView tvTOTP;

        ViewHolder (View view) {
            super(view);
            tvEmail = (TextView)view.findViewById(R.id.tvEmail);
            tvTOTP = (TextView)view.findViewById(R.id.tvTOTP);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final Account account = accounts.get(i);
        holder.tvEmail.setText(account.getEmail());
        String secret = Base32.random();
        holder.tvTOTP.setText((new Totp(secret)).now());
        timer[i] = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                String secret = Base32.random();
                holder.tvTOTP.setText((new Totp(secret)).now());
                timer[i].start();
            }
        };
        timer[i].start();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
