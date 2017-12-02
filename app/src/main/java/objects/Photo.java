package objects;

public class Photo {

    public String getThumb() {
        return thumb;
    }

    public Photo(String thumb, String highres) {
        this.thumb = thumb;
        this.highres = highres;
    }

    private String thumb;
    private String highres;

}
