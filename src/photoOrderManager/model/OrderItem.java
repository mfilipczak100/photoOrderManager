package photoOrderManager.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderItem {

    private String format;
    private String paper;
    private String crop;
    private Map<String,Integer> pickedPhotosMap=new HashMap<>();
    private int photosPickedCount=0;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public Map<String, Integer> getPickedPhotosMap() {
        return pickedPhotosMap;
    }

    public int getPhotosPickedCount() {
        return photosPickedCount;
    }

    public void setPhotosPickedCount(int photosPickedCount) {
        this.photosPickedCount = photosPickedCount;
    }

    @Override
    public String toString() {
        return format+" "+paper+" "+crop+" ("+photosPickedCount+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(format, orderItem.format) &&
                Objects.equals(paper, orderItem.paper) &&
                Objects.equals(crop, orderItem.crop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, paper, crop);
    }
}
