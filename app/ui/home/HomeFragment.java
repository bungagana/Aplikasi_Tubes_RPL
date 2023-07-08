package id.sch.sman1pangkah.sptos.mazan.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import id.sch.sman1pangkah.sptos.mazan.Disability;
import id.sch.sman1pangkah.sptos.mazan.DiscoverActivity;
import id.sch.sman1pangkah.sptos.mazan.R;
import id.sch.sman1pangkah.sptos.mazan.competitionActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    SharedPreferences namapref;
    CardView chall_btn, discov_btn, dsb_btn;
    NestedScrollView nestedScrollView;
    boolean menu;
    Animation menuanim, fadeout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        namapref = getActivity().getSharedPreferences("nama", Context.MODE_PRIVATE);

        final String nama = namapref.getString("namauser","missing");
        TextView namatxt = root.findViewById(R.id.namaid);
        namatxt.setText(nama);
        menu = false;
         nestedScrollView = root.findViewById(R.id.nestedscrool);
         menuanim = AnimationUtils.loadAnimation(getActivity(),R.anim.frombottom);
         fadeout = AnimationUtils.loadAnimation(getActivity(),R.anim.fadeout);

        ImageView menubtn = root.findViewById(R.id.menubtn);

        discov_btn = root.findViewById(R.id.discovbtn);
        discov_btn.setOnClickListener(view -> startActivity(new Intent(getActivity().getApplicationContext(), DiscoverActivity.class)));

        chall_btn = root.findViewById(R.id.challbtn);
        chall_btn.setOnClickListener(view -> startActivity(new Intent(getActivity().getApplicationContext(), competitionActivity.class)));

        dsb_btn = root.findViewById(R.id.disability);
        dsb_btn.setOnClickListener(view -> {
            SharedPreferences.Editor disably = namapref.edit();
            disably.putBoolean("disablystatus", true);
            disably.apply();
            startActivity(new Intent(getActivity().getApplicationContext(), Disability.class));
        });

        return root;
    }
}