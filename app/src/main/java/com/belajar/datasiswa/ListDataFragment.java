package com.belajar.datasiswa;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDataFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    Button btnInput;
    RecyclerView.LayoutManager layoutManager;
    List<Siswa> listSiswa;

    public ListDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_data, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home:
                    getActivity().onBackPressed();

                    return  true;
                default:
                    return  super.onOptionsItemSelected(item);
            }
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.showUpButton();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        btnInput = view.findViewById(R.id.btn_input_list);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputFragment fragment = new InputFragment("Input",new Siswa());
                setFragment(fragment);
            }
        });
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        setupRecyclerView();
    }
    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(context);
        listSiswa = db.selectUserData();
        SiswaAdapter adaper = new SiswaAdapter(context,listSiswa);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaper);

        adaper.setOnItemClickCallback(new SiswaAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Siswa siswa) {
                showPictureDialog(siswa);
                Log.d("msg",siswa.getNama());
            }
        });
        adaper.notifyDataSetChanged();
    }


    private void showPictureDialog(final Siswa siswa){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Pilihan");
        String[] pictureDialogItems = {
                "Lihat Data",
                "Update Data",
                "Hapus Data"
        };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment;
                        switch (which) {
                            case 0:
                                fragment = new InputFragment("Lihat",siswa);
                                setFragment(fragment);
                                 break;
                            case 1:
                                fragment = new InputFragment("Update",siswa);
                                setFragment(fragment);
                                break;
                            case 2:
                                DatabaseHelper db = new DatabaseHelper(context);
                                db.delete(siswa.getNomor());
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public  void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framecontainer,fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

}
