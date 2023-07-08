package com.example.eliterasi_pbo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DiscoverActivity extends AppCompatActivity {

    private DatabaseReference mDatabase, searchDB;
    private FirebaseRecyclerAdapter<DiscoveryPuisi, DiscoveryPuisiHolder> mAdapter, searchAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    SharedPreferences keepPuisi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

        keepPuisi = this.getSharedPreferences("Puisi", Context.MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = findViewById(R.id.discoverylist);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        Query query = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<DiscoveryPuisi>()
                .setQuery(query, DiscoveryPuisi.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<DiscoveryPuisi, DiscoveryPuisiHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DiscoveryPuisiHolder holder, int position, @NonNull final DiscoveryPuisi model) {
                holder.bindToPuisi(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String puisijudul = model.judulpuisi;
                        SharedPreferences.Editor puisiedit = keepPuisi.edit();
                        puisiedit.putString("puisi", puisijudul);
                        puisiedit.apply();
                        startActivity(new Intent(getApplicationContext(), DetailKaryaActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public DiscoveryPuisiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new DiscoveryPuisiHolder(inflater.inflate(R.layout.discovercard, viewGroup, false));
            }
        };
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchProcess(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchProcess(s);
                return false;
            }
        });

    }

    public void searchProcess(String s){
        Query query2 = mDatabase.child("Karya_Sastra").orderByChild("judulpuisi").startAt(s).endAt(s + "\uf8ff");
        FirebaseRecyclerOptions seachh = new FirebaseRecyclerOptions.Builder<DiscoveryPuisi>()
                .setQuery(query2, DiscoveryPuisi.class)
                .build();

        searchAdapter = new FirebaseRecyclerAdapter<DiscoveryPuisi, DiscoveryPuisiHolder>(seachh) {
            @Override
            protected void onBindViewHolder(@NonNull DiscoveryPuisiHolder holder, int position, @NonNull final DiscoveryPuisi model) {
                holder.bindToPuisi(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String puisijudul = model.judulpuisi;
                        SharedPreferences.Editor puisiedit = keepPuisi.edit();
                        puisiedit.putString("puisi", puisijudul);
                        puisiedit.apply();
                        startActivity(new Intent(getApplicationContext(), DetailKaryaActivity.class));
                    }
                });
            }

            @NonNull
            @Override
            public DiscoveryPuisiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new DiscoveryPuisiHolder(inflater.inflate(R.layout.discovercard, viewGroup, false));
            }
        };
        searchAdapter.startListening();
        mRecycler.setAdapter(searchAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    private Query getQuery(DatabaseReference mDatabase) {
        Query query = mDatabase.child("Karya_Sastra");
        return query;
    }

}
