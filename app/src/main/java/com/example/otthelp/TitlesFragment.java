package com.example.otthelp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TitlesFragment extends Fragment {
    RecyclerView titlesRecyclerView;
    FirebaseRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    public TitlesFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MESSAGE","Titles Fragment Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_titles, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titlesRecyclerView = getView().findViewById(R.id.titlesRecyclerView);
        DatabaseReference titlesRef = FirebaseDatabase.getInstance().getReference("titles");

        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(titlesRef, new SnapshotParser<String>() {
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
                View view = LayoutInflater.from(getContext()).inflate(R.layout.title_view_list,parent,false);
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
                    Intent intent = new Intent(getContext(),MyTitleActivity.class);
                    intent.putExtra("TITLE",textview.getText().toString());
                    startActivity(intent);
                }
            }
        };

        titlesRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation());
        titlesRecyclerView.setLayoutManager(linearLayoutManager);
        titlesRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }
}