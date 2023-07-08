package id.sch.sman1pangkah.sptos.mazan;

public class Story {
    private String judulcerpen;
    private String kontencerpen;
    private String status;

    public Story() {

    }

    public Story(String judulcerpen, String kontencerpen, String status) {
        this.judulcerpen = judulcerpen;
        this.kontencerpen = kontencerpen;
        this.status = status;

    }

    public String getJudulcerpen() {
        return judulcerpen;
    }

    public void setJudulcerpen(String judulcerpen) {
        this.judulcerpen = judulcerpen;
    }

    public String getKontencerpen() {
        return kontencerpen;
    }

    public void setKontencerpen(String kontencerpen) {
        this.kontencerpen = kontencerpen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
