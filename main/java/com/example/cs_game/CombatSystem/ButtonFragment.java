package com.example.cs_game.CombatSystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cs_game.R;


public class ButtonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_button, container,
                false);

        Button attackButton = rootView.findViewById(R.id.attack_button);
        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAttackButtonClicked();
                }
            }
        });

        Button consumableButton = rootView.findViewById(R.id.consumable_button);
        consumableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onConsumableButtonClicked();
                }
            }
        });


        return rootView;
    }
    // listener related creation
    public interface OnButtonClickedListener {
        void onAttackButtonClicked();
        void onConsumableButtonClicked();
    }

    private OnButtonClickedListener listener;

    void setOnButtonClickedListener(OnButtonClickedListener listener) {
        this.listener = listener;
    }
}
