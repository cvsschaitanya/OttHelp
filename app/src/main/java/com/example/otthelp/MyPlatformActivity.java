package com.example.otthelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPlatformActivity extends AppCompatActivity {

    private String platformName;
    DatabaseReference ref;
    TextView textview;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_platform);

        Intent intent = getIntent();
        platformName = intent.getStringExtra("PLATFORM");

        toolbar = findViewById(R.id.toolbar);
        textview = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);


        ref = FirebaseDatabase.getInstance().getReference("platforms/"+platformName.toLowerCase());
        textview.setText(platformName+" has");

        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder()
                .setQuery(ref.child("availableTitles"),String.class)
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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_view_grid,parent,false);
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

                public void bind(String titleName){
                    textview.setText(titleName);
                }

                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(),MyTitleActivity.class);
//                    intent.putExtra("TITLE",textview.getText().toString());
//                    startActivity(intent);
                }
            }
        };

        recyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(this,2);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,gridLayoutManager.getOrientation());
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(itemDecoration);

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