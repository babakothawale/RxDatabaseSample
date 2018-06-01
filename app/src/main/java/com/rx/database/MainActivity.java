package com.rx.database;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rx.database.Adapter.OwnerAdapter;
import com.rx.database.Adapter.ShopAdapter;
import com.rx.database.data.Repository;
import com.rx.database.models.Owner;
import com.rx.database.models.Shop;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView list;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = findViewById(R.id.list);

        init();

        Button button = findViewById(R.id.button_loadList);
        Button button_owner = findViewById(R.id.button_owner);
        Button button_insert_shop = findViewById(R.id.button_insert_shop);
        Button button_insert_owner = findViewById(R.id.button_insert_owner);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.loadShopData(MainActivity.this).subscribe(getObserver());
            }
        });

        button_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.loadOwnerData(MainActivity.this).subscribe(getOwnerObserver());
            }
        });

        button_insert_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.insertShopData(MainActivity.this);
            }
        });

        button_insert_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.insertOwnerData(MainActivity.this);
            }
        });

    }

    public void init(){
        list.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repository.clear();
        if(disposables != null) {
            disposables.clear();
        }
    }

    private Observer<List<Shop>> getObserver() {
        return new Observer<List<Shop>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(List<Shop> shops) {
                Log.d(TAG, "onNext: ");
                setShopAdapter(shops);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

    private Observer<List<Owner>> getOwnerObserver() {
        return new Observer<List<Owner>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(List<Owner> owners) {
                Log.d(TAG, "onNext: ");
                setOwnerAdapter(owners);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

    private void setShopAdapter(List<Shop> shops){
        ShopAdapter adapter = new ShopAdapter(getApplicationContext(), shops,
                new ShopAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Shop Item) {
                        Toast.makeText(getApplicationContext(), Item.getName(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        list.setAdapter(adapter);
    }

    private void setOwnerAdapter(List<Owner> owners){
        OwnerAdapter adapter = new OwnerAdapter(getApplicationContext(), owners,
                new OwnerAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Owner Item) {
                        Toast.makeText(getApplicationContext(), Item.getName(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        list.setAdapter(adapter);
    }


}
