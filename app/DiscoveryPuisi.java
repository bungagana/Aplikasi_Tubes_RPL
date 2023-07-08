package id.sch.sman1pangkah.sptos.mazan;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class DiscoveryPuisi {

    public String judulpuisi;
    public String kontenpuisi;
    public String status;
    public String score;
    public String imgurl;
    public String lovemeter;
    public String author;
    public String jenis;

    public DiscoveryPuisi() {
    }

    public DiscoveryPuisi(String judulpuisi, String kontenpuisi, String status, String score, String imgurl, String lovemeter, String author, String jenis) {
        this.judulpuisi = judulpuisi;
        this.kontenpuisi = kontenpuisi;
        this.status = status;
        this.score = score;
        this.imgurl = imgurl;
        this.lovemeter = lovemeter;
        this.author = author;
        this.jenis = jenis;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("judulpuisi", judulpuisi);
        result.put("kontenpuisi", kontenpuisi);
        result.put("status", status);
        result.put("score", score);
        result.put("imgurl", imgurl);
        result.put("lovemeter", lovemeter);
        result.put("author", author);
        result.put("jenis", jenis);
        return result;
    }



}
