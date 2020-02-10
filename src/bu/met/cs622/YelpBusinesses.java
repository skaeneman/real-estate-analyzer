package bu.met.cs622;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/********************************************************************
 *  This class processes the "business" json data returned Yelps API
 ********************************************************************/
public class YelpBusinesses {

    protected List<YelpBusinesses> businesses;
    protected String id;
    protected String name;
    protected String url;
    protected Double distance;
    protected Double rating;
    @SerializedName("is_closed")
    protected Boolean isClosed;
//  protected YelpCategories categories;
    protected YelpLocation location;

    // constructor
    public YelpBusinesses(List<YelpBusinesses> businesses, YelpLocation location) {
        this.businesses = businesses;
        this.location = location;
    }

    /******************************************
     *  getters and setters
     ******************************************/

    public List<YelpBusinesses> getBusinesses() {
        return businesses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

}
