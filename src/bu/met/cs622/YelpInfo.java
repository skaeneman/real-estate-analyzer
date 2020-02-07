package bu.met.cs622;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YelpInfo {
    List<YelpInfo> businesses;
    String id;
    String name;
    String url;
    Double distance;
    Object location;
    List<YelpInfo> categories;

    @SerializedName("is_closed")
    Boolean isClosed;

    Double rating;

//    String address1;

//    @SerializedName("displayAddress")
//    String display_address;

    public List<YelpInfo> getBusinesses() {
        return businesses;
    }

    public List<YelpInfo> getCategories() {
        return categories;
    }

//    public Object getLocation(String address1) {
//        return this.address1 = address1;
//    }


}

