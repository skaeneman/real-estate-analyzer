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

    //nested class fields
    String alias;
    String title;


    /** switch over to using 2 class files like this https://www.youtube.com/watch?v=4F1d6ELxF1c and a constructor */



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


    // nested inner class for categories
    public class YelpInfoCategories {
            String alias;
            String title;
    }

}

