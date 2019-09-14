package com.belajar.datasiswa;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {
Button btnLihatData,btnInputData,btnInfo;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity)getActivity();
        if (activity != null) {
            activity.hideUpButton();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLihatData = view.findViewById(R.id.btn_lihat_data);
        btnInputData = view.findViewById(R.id.btn_input_data);
        btnInfo = view.findViewById(R.id.btn_info);

        btnLihatData.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnInputData.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()){
            case R.id.btn_lihat_data:
                fragment = new ListDataFragment();
                setFragment(fragment);
                break;
            case R.id.btn_input_data:
                fragment = new InputFragment("Input",new Siswa());
                setFragment(fragment);
                break;
            case R.id.btn_info:
                fragment = new InfoFragment();
                setFragment(fragment);
                break;
        }
    }

    public  void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framecontainer,fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
