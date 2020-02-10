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


}
