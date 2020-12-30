package com.example.otthelp.unused;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.otthelp.MyPlatformActivity;
import com.example.otthelp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlatformsActivity extends AppCompatActivity {

    RecyclerView platformsRecyclerView;
    FirebaseRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platforms);

        platformsRecyclerView = findViewById(R.id.platformsRecyclerView);
        DatabaseReference platformsRef = FirebaseDatabase.getInstance().getReference("platforms");

        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(platformsRef, new SnapshotParser<String>() {
                    @NonNull
                    @Override
                    public String parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return snapshot.child("name").getValue(String.class);
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Object model) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.bind(model.toString());
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.platform_view_list,parent,false);
                return new ViewHolder(view);
            }

            class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                TextView textview;
                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    textview = itemView.findViewById(R.id.platformName);
                    itemView.setClickable(true);
                    itemView.setOnClickListener(this);
                }

                public void bind(String platformName){
                    textview.setText(platformName);
                }

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MyPlatformActivity.class);
                    intent.putExtra("PLATFORM",textview.getText().toString());
                    startActivity(intent);
                }
            }
        };
//        adapter.updateOptions();

        platformsRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        platformsRecyclerView.setLayoutManager(linearLayoutManager);
        platformsRecyclerView.addItemDecoration(itemDecoration);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }
}