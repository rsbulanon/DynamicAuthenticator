package com.dynobjx.dynamicauthenticator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dynobjx.dynamicauthenticator.adapters.AccountsAdapter;
import com.dynobjx.dynamicauthenticator.models.Account;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rvAccounts) RecyclerView rvAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm realm = Realm.getInstance(this);
        Account account = new Account("ned@flanders.com", Calendar.getInstance().getTime().getTime());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(account);
        realm.commitTransaction();
        RealmQuery<Account> query = realm.where(Account.class);
        RealmResults<Account> accounts = query.findAll();
        AccountsAdapter adapter = new AccountsAdapter(this,accounts);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        rvAccounts.setAdapter(scaleAdapter);
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
