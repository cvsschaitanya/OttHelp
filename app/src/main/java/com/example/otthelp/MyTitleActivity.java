package com.example.otthelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyTitleActivity extends AppCompatActivity {

    private String titleName;
    DatabaseReference ref;
    TextView textview;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_title);

        Intent intent = getIntent();
        titleName = intent.getStringExtra("TITLE");

        toolbar = findViewById(R.id.toolbar);
        textview = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);

        ref = FirebaseDatabase.getInstance().getReference("titles/"+titleName.toLowerCase());
        textview.setText(titleName+" is available on");

        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder()
                .setQuery(ref.child("availableOn"),String.class)
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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.platform_view_grid,parent,false);
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