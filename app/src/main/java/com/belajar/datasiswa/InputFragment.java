package com.belajar.datasiswa;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {
    Context context;
    String action ;
    Button inputData;
    Siswa siswaTadi;
    AlertDialog alertDialog;
    int chekeditem = -1;
    final Calendar myCalendar = Calendar.getInstance();
    EditText edtNomor,edtNama,edtTanggalLahir,edtJenisKelamin,edtAlamat;

    public InputFragment(String action,Siswa siswa) {
        this.action = action;
        this.siswaTadi = siswa;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.showUpButton(action + " Data");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        inputData = view.findViewById(R.id.btn_input);
        edtTanggalLahir = view.findViewById(R.id.edt_ttl);

        //Memasukan Adapter pada Spinner
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }

        };

        edtTanggalLahir.setClickable(true);
        edtTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                edtTanggalLahir.setFocusable(true);
            }
        });
        contentFilter(view);
    }

    private void updateLabel() {
            String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtTanggalLahir.setText(sdf.format(myCalendar.getTime()));
    }

    private void contentFilter(View view) {
        edtNomor = view.findViewById(R.id.edt_nomor);
        edtNama = view.findViewById(R.id.edt_nama);
        edtJenisKelamin = view.findViewById(R.id.edt_jk);
        edtJenisKelamin.setClickable(true);
        edtJenisKelamin.setFocusable(false);
        edtJenisKelamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(edtJenisKelamin);
            }
        });
        edtAlamat = view.findViewById(R.id.edt_alamat);
        if (action.equals("Input")){
             final DatabaseHelper db = new DatabaseHelper(context);
             final Siswa siswa = new Siswa();
                    inputData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                if(edtNama.getText().toString().equals("")){
                                    edtNama.setError("Nama harus Diisi");
                                }
                                if (edtNomor.getText().toString().equals("")){
                                    edtNomor.setError("Nomor Harus Diisi");
                                }else if(cekNomor(db)){
                                    edtNomor.setError("Nomor telah digunakan");
                                 }
                                else{
                                    siswa.setNomor(Integer.parseInt(edtNomor.getText().toString()));
                                    siswa.setNama(edtNama.getText().toString());
                                    siswa.setTanggalLahir(edtTanggalLahir.getText().toString());
                                    siswa.setJenisKelamin(edtJenisKelamin.getText().toString());
                                    siswa.setAlamat(edtAlamat.getText().toString());
                                    db.insert(siswa);
                                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                                }

                            }
                    });

        }
        else if (action.equals("Update")){
            final DatabaseHelper db = new DatabaseHelper(context);
            final Siswa siswa = new Siswa();
            edtNomor.setText(String.valueOf(siswaTadi.getNomor()));
            edtNomor.setEnabled(false);
           edtNama.setText(siswaTadi.getNama());
          if (siswaTadi.getJenisKelamin().equals("Laki-Laki")){
              chekeditem = 0;
          }else{
              chekeditem = 1;
          }

           edtTanggalLahir.setText(siswaTadi.getTanggalLahir());
           edtJenisKelamin.setText(siswaTadi.getJenisKelamin());
           edtAlamat.setText(siswaTadi.getAlamat());
           inputData.setText("Update Data");
            inputData.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edtNama.getText().toString().equals("")){
                        edtNama.setError("Nama harus Diisi");
                    }
                    if (edtNomor.getText().toString().equals("")){
                        edtNomor.setError("Nomor Harus Diisi");
                    }else{
                        siswa.setNomor(Integer.parseInt(edtNomor.getText().toString()));
                        siswa.setNama(edtNama.getText().toString());
                        siswa.setTanggalLahir(edtTanggalLahir.getText().toString());
                        siswa.setJenisKelamin(edtJenisKelamin.getText().toString());
                        siswa.setAlamat(edtAlamat.getText().toString());
                        db.update(siswa);
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
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
    public void showDialog(final EditText editText){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        CharSequence[] values = {"Laki-Laki","Perempuan"};
        builder.setTitle("Pilih Jenis Kelamin");

        builder.setSingleChoiceItems(values, chekeditem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch(item)
                {
                    case 0:
                        editText.setText("Laki-Laki");
                        chekeditem = 0;
                        break;
                    case 1:
                        editText.setText("Perempuan");
                        chekeditem = 1;
                        break;
                }
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean cekNomor(DatabaseHelper db){
        boolean betul = false;
        for (int i = 0; i < db.selectUserData().size();i++){
            if (Integer.parseInt(edtNomor.getText().toString()) == db.selectUserData().get(i).getNomor()){
            betul = true;
            }
        }
        return betul;
    }
}
