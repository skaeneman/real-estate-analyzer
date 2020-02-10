package bu.met.cs622;

import com.google.gson.annotations.SerializedName;

/*************************************************************************
 *  This class processes the "location" json data returned from Yelps API
 *  and is used in the YelpBusiness classes constructor
 *************************************************************************/
public class YelpLocation {

    protected String city;
    protected String country;
    protected String address1;
//  protected String address2;
//  protected String address3;
    protected String state;
    @SerializedName("zip_code")
    protected String zipCode;

    // constructor
    public YelpLocation(String city, String country, String address1, String state, String zipCode) {
        this.city = city;
        this.country = country;
        this.address1 = address1;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
