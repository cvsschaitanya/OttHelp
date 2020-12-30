package com.example.otthelp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlatformsFragment extends Fragment {

    RecyclerView platformsRecyclerView;
    FirebaseRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public PlatformsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MESSAGE","Platforms Fragment Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_platforms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        platformsRecyclerView = getView().findViewById(R.id.platformsRecyclerView);
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
                View view = LayoutInflater.from(getContext()).inflate(R.layout.platform_view_list,parent,false);
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
                    Intent intent = new Intent(getContext(),MyPlatformActivity.class);
                    intent.putExtra("PLATFORM",textview.getText().toString());
                    startActivity(intent);
                }
            }
        };
//        adapter.updateOptions();

        platformsRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation());
        platformsRecyclerView.setLayoutManager(linearLayoutManager);
        platformsRecyclerView.addItemDecoration(itemDecoration);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }

}