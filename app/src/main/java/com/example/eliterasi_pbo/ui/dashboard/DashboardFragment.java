package com.example.eliterasi_pbo.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import id.sch.sman1pangkah.sptos.mazan.Artikel;
import id.sch.sman1pangkah.sptos.mazan.Cerpen;
import id.sch.sman1pangkah.sptos.mazan.Creavy;
import id.sch.sman1pangkah.sptos.mazan.Puisi;
import id.sch.sman1pangkah.sptos.mazan.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.putih));
        }
        CardView artikelbtn = root.findViewById(R.id.artikelbtn);
        CardView puisibtn = root.findViewById(R.id.puisibtn);
        CardView umumbtn = root.findViewById(R.id.umumbtn);
        CardView creavybtn = root.findViewById(R.id.creavyBtn);

        artikelbtn.setOnClickListener(v -> startActivity(new Intent(getActivity().getApplicationContext(), Artikel.class)));

        puisibtn.setOnClickListener(v -> startActivity(new Intent(getActivity().getApplicationContext(), Puisi.class)));

        umumbtn.setOnClickListener(v -> startActivity(new Intent(getActivity().getApplicationContext(), Cerpen.class)));

        creavybtn.setOnClickListener(v -> startActivity(new Intent(getActivity().getApplicationContext(), Creavy.class)));
        return root;
    }
}