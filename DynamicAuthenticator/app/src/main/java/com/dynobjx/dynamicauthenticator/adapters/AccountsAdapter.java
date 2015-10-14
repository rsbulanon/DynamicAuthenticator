package com.dynobjx.dynamicauthenticator.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynobjx.dynamicauthenticator.MainActivity;
import com.dynobjx.dynamicauthenticator.R;
import com.dynobjx.dynamicauthenticator.models.Account;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.RealmResults;

/**
 * Created by madeveloper on 10/13/15.
 */
public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {

    private RealmResults<Account> accounts;
    private Context context;
    private MainActivity activity;

    public AccountsAdapter(Context context, RealmResults<Account> accounts) {
        this.context = context;
        this.accounts = accounts;
        this.activity = (MainActivity)context;
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
        DonutProgress donutProgress;

        ViewHolder (View view) {
            super(view);
            tvEmail = (TextView)view.findViewById(R.id.tvEmail);
            tvTOTP = (TextView)view.findViewById(R.id.tvTOTP);
            donutProgress = (DonutProgress)view.findViewById(R.id.donutProgress);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final Account account = accounts.get(i);
        holder.tvEmail.setText(account.getEmail());
        String secret = Base32.random();
        holder.tvTOTP.setText((new Totp(secret)).now());
        holder.donutProgress.setMax(5);
        //TOTPTimer(holder.tvTOTP, holder.donutProgress);
        Timer timer = new Timer();
        timer.schedule(new CustomTimer(holder.tvTOTP,holder.donutProgress),1000,1000);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void TOTPTimer(final TextView textView, final DonutProgress donutProgress) {
        final CountDownTimer timer = new CountDownTimer((1000 * 5),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                donutProgress.setProgress((int)(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                String secret = Base32.random();
                textView.setText((new Totp(secret)).now());
                donutProgress.setProgress(0);
                TOTPTimer(textView, donutProgress);
            }
        };
        timer.start();
    }

    class CustomTimer extends TimerTask {

        TextView textView;
        DonutProgress donutProgress;
        int prog;
        int ctr = 5;

        public CustomTimer(TextView textView,DonutProgress donutProgress) {
            this.textView = textView;
            this.donutProgress = donutProgress;
        }

        @Override
        public void run() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ctr -= 1;
                    prog += 1;

                    if (ctr < 0) {
                        ctr = 5;
                        prog = 0;
                    } else if (ctr == 0) {
                        String secret = Base32.random();
                        textView.setText((new Totp(secret)).now());
                    }
                    donutProgress.setProgress(prog);
                }
            });
        }
    };
}
