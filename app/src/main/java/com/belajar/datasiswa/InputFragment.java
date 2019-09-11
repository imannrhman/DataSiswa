package com.belajar.datasiswa;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {
    Context context;
    String action ;
    Button inputData;
    Siswa siswaTadi;
    EditText edtNomor,edtNama,edtTanggalLahir,edtJenisKelamin,edtAlamat;

    public InputFragment(String action,Siswa siswa) {
        this.action = action;
        this.siswaTadi = siswa;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        inputData = view.findViewById(R.id.btn_input);
        contentFilter(view);
    }

    private void contentFilter(View view) {
        edtNomor = view.findViewById(R.id.edt_nomor);
        edtNama = view.findViewById(R.id.edt_nama);
        edtTanggalLahir = view.findViewById(R.id.edt_ttl);
        edtJenisKelamin = view.findViewById(R.id.edt_jk);
        edtAlamat = view.findViewById(R.id.edt_alamat);
        if (action.equals("Input")){
             final DatabaseHelper db = new DatabaseHelper(context);
             final Siswa siswa = new Siswa();
            inputData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    siswa.setNomor(Integer.parseInt(edtNomor.getText().toString()));
                    siswa.setNama(edtNama.getText().toString());
                    siswa.setTanggalLahir(edtTanggalLahir.getText().toString());
                    siswa.setJenisKelamin(edtJenisKelamin.getText().toString());
                    siswa.setAlamat(edtAlamat.getText().toString());
                    db.insert(siswa);
                }
            });
        }
        else if (action.equals("Update")){
            final DatabaseHelper db = new DatabaseHelper(context);
            final Siswa siswa = new Siswa();
            edtNomor.setText(String.valueOf(siswaTadi.getNomor()));
            edtNomor.setEnabled(false);
           edtNama.setText(siswaTadi.getNama());
           edtTanggalLahir.setText(siswaTadi.getTanggalLahir());
           edtJenisKelamin.setText(siswaTadi.getJenisKelamin());
           edtAlamat.setText(siswaTadi.getAlamat());
           inputData.setText("Update Data");
            inputData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    siswa.setNomor(Integer.parseInt(edtNomor.getText().toString()));
                    siswa.setNama(edtNama.getText().toString());
                    siswa.setTanggalLahir(edtTanggalLahir.getText().toString());
                    siswa.setJenisKelamin(edtJenisKelamin.getText().toString());
                    siswa.setAlamat(edtAlamat.getText().toString());
                    db.update(siswa);
                }
            });
        }else if(action.equals("Lihat")){
            ImageView img = view.findViewById(R.id.student);
            img.setVisibility(View.VISIBLE);
            edtNomor.setText(String.valueOf(siswaTadi.getNomor()));
            edtNomor.setEnabled(false);
            edtNama.setText(siswaTadi.getNama());
            edtNama.setEnabled(false);
            edtTanggalLahir.setText(siswaTadi.getTanggalLahir());
            edtTanggalLahir.setEnabled(false);
            edtJenisKelamin.setText(siswaTadi.getJenisKelamin());
            edtJenisKelamin.setEnabled(false);
            edtAlamat.setText(siswaTadi.getAlamat());
            edtAlamat.setEnabled(false);
            inputData.setVisibility(View.INVISIBLE);
        }
    }
}
