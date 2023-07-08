package id.sch.sman1pangkah.sptos.mazan;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DiscoveryPuisiHolder extends RecyclerView.ViewHolder {

    public TextView titletxt;
    public TextView authortxt;
    public TextView jenistxt;
    public ImageView imgtxt;
    public CardView discovcard;

    public DiscoveryPuisiHolder(@NonNull View itemView) {
        super(itemView);
        titletxt = itemView.findViewById(R.id.titletxt);
        authortxt = itemView.findViewById(R.id.authortxt);
        jenistxt = itemView.findViewById(R.id.jenistxt);
        imgtxt = itemView.findViewById(R.id.imgtxt);
        discovcard = itemView.findViewById(R.id.discovcard);
    }

    public void bindToPuisi(DiscoveryPuisi discoveryPuisi, View.OnClickListener onClickListener) {
        titletxt.setText(discoveryPuisi.judulpuisi);
        authortxt.setText("Karya : " + discoveryPuisi.author);
        jenistxt.setText(discoveryPuisi.jenis);
       // Glide.with(imgtxt.getContext()).load(discoveryPuisi.imgurl).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgtxt);
        discovcard.setOnClickListener(onClickListener);
    }

}
