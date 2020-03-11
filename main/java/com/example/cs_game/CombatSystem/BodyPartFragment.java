package com.example.cs_game.CombatSystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.DisplayableAdapter;
import com.example.cs_game.R;
import com.example.cs_game.ShopGame.OnItemClickListener;

import java.util.List;

public class BodyPartFragment extends Fragment {

    private List<BodyPart> bodyParts;

    //constructor
    BodyPartFragment(List<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
    }

    @Nullable

    //onCreateView action
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bodypartlist, container,
                false);
        RecyclerView bodyList = rootView.findViewById(R.id.bodypartlist);
        final DisplayableAdapter<BodyPart> bodyAdapter = new CombatDisplayableAdapter<>();
        bodyAdapter.addAllItems(bodyParts);

        bodyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DisplayableAdapter.ViewHolder holder, View v, int position) {
                BodyPart bodyPart = bodyAdapter.getItem(position);
                if (listener != null) {
                    listener.onItemClick(bodyPart);
                }
            }
        });

        bodyList.setAdapter(bodyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        bodyList.setLayoutManager(layoutManager);
        return rootView;
    }

    // sets the listener for the fragment
    private OnItemClickedInFragmentListener<BodyPart> listener;
    void setListener(OnItemClickedInFragmentListener<BodyPart> listener) {
        this.listener = listener;
    }
}
