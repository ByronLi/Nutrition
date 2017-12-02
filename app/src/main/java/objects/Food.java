package objects;

public class Food {

    public String getFood_name() {
        return food_name;
    }

    public String getImage() {
        return photo.getThumb();
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public String getNix_brand_id() {
        return nix_brand_id;
    }

    public String getBrand_name_item_name() {
        return brand_name_item_name;
    }

    public double getServing_qty() {
        return serving_qty;
    }

    public int getNf_calories() {
        return nf_calories;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getBrand_type() {
        return brand_type;
    }

    public String getNix_item_id() {
        return nix_item_id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTag_id() {
        return tag_id;
    }

    public Food(String food_name, String image, Photo photo, String serving_unit, String nix_brand_id, String brand_name_item_name, double serving_qty, int nf_calories, String brand_name, String brand_type, String nix_item_id, String uuid, String tag_id) {
        this.food_name = food_name;
        this.serving_unit = serving_unit;
        this.nix_brand_id = nix_brand_id;
        this.brand_name_item_name = brand_name_item_name;
        this.serving_qty = serving_qty;
        this.nf_calories = nf_calories;
        this.brand_name = brand_name;
        this.brand_type = brand_type;
        this.nix_item_id = nix_item_id;
        this.uuid = uuid;
        this.tag_id = tag_id;
        this.photo = photo;
    }

    private String food_name;
    private Photo photo;
    private String serving_unit;
    private String nix_brand_id;
    private String brand_name_item_name;
    private double serving_qty;
    private int nf_calories;
    private String brand_name;
    private String brand_type;
    private String nix_item_id;
    private String uuid;
    private String tag_id;

}
