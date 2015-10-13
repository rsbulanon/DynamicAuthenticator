package com.dynobjx.dynamicauthenticator.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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

import java.util.List;

/**
 * Created by madeveloper on 10/13/15.
 */
public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {

    private List<Account> accounts;
    private Context context;
    private MainActivity activity;

    public AccountsAdapter(Context context, List<Account> accounts) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_company, parent, false);
        ViewHolder  holder = new ViewHolder (v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInitials;
        TextView tvCompanyName;
        TextView tvBranchAddress;
        TextView tvContactPerson;
        Toolbar toolbar;

        ViewHolder (View view) {
            super(view);
            tvInitials = (TextView)view.findViewById(R.id.tvInitials);
            tvCompanyName = (TextView)view.findViewById(R.id.tvCompanyName);
            tvBranchAddress = (TextView)view.findViewById(R.id.tvAddress);
            tvContactPerson = (TextView)view.findViewById(R.id.tvContactPerson);
            toolbar = (Toolbar)view.findViewById(R.id.tbMenu);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final Account company = accounts.get(i);
        holder.tvCompanyName.setText(company.getBasicInfo().getEnterprise());
        holder.tvBranchAddress.setText(company.getBasicInfo().getEnterpriseAddress());
        holder.tvContactPerson.setText(company.getBasicInfo().getContactPerson());
        String[] initials = company.getBasicInfo().getEnterprise().split(" ");
        if (initials.length > 1) {
            holder.tvInitials.setText(""+initials[0].toUpperCase().charAt(0) + initials[1].toUpperCase().charAt(0));
        } else {
            holder.tvInitials.setText(""+initials[0].toUpperCase().charAt(0));
        }
        GradientDrawable bgShape = (GradientDrawable)holder.tvInitials.getBackground();
        bgShape.setColor(ContextCompat.getColor(context, colors[i % 19]));
        holder.toolbar.getMenu().clear();
        holder.toolbar.inflateMenu(R.menu.menu_company);
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                }
                return true;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
