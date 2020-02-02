package bu.met.cs622;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class YelpAPITest {

    private Assertions JSONAssert;

    @Test
    void getYelpDataTest() throws IOException {

        YelpAPI yelp = new YelpAPI();  // instantiate new object of YelpAPI class

        Object yelpJsonResponse = yelp.getYelpData();  // get json data back

        // create a test string to check Yelp json response against
        String gosnoldMaExpectedData = "{\"businesses\": [{\"id\": \"xDFhs6vXuD2cFLq0YdQA7Q\", \"alias\": \"beach-plum-inn-and-restaurant-chilmark\", \"name\": \"Beach Plum Inn & Restaurant\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/582-ChZioZ6vxY06leS-Rg/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/beach-plum-inn-and-restaurant-chilmark?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 81, \"categories\": [{\"alias\": \"hotels\", \"title\": \"Hotels\"}, {\"alias\": \"venues\", \"title\": \"Venues & Event Spaces\"}, {\"alias\": \"breakfast_brunch\", \"title\": \"Breakfast & Brunch\"}], \"rating\": 3.0, \"coordinates\": {\"latitude\": 41.35406, \"longitude\": -70.759105}, \"transactions\": [], \"price\": \"$$$$\", \"location\": {\"address1\": \"50 Beach Plum Ln\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Chilmark\", \"zip_code\": \"02535\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"50 Beach Plum Ln\", \"Chilmark, MA 02535\"]}, \"phone\": \"+15086459454\", \"display_phone\": \"(508) 645-9454\", \"distance\": 13573.50519263004}, {\"id\": \"AHzs8Yu4MIMQJNXh9SETgA\", \"alias\": \"cuttyhunk-island-gosnold\", \"name\": \"Cuttyhunk Island\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/NGGvkn6R6ykBgxGUEePoIA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/cuttyhunk-island-gosnold?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1, \"categories\": [{\"alias\": \"landmarks\", \"title\": \"Landmarks & Historical Buildings\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 41.417621, \"longitude\": -70.935003}, \"transactions\": [], \"location\": {\"address1\": \"\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Gosnold\", \"zip_code\": \"02713\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"Gosnold, MA 02713\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 8149.467239540939}, {\"id\": \"hhcZM-ytf5V8h_xQpmwwmA\", \"alias\": \"menemsha-beach-chilmark\", \"name\": \"Menemsha Beach\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/S3UJgpd1vD8xSRpGAe5MSA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/menemsha-beach-chilmark?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 9, \"categories\": [{\"alias\": \"beaches\", \"title\": \"Beaches\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 41.3549077308444, \"longitude\": -70.7669777310353}, \"transactions\": [], \"location\": {\"address1\": \"56 Basin Rd\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Chilmark\", \"zip_code\": \"02535\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"56 Basin Rd\", \"Chilmark, MA 02535\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 13134.662990892879}, {\"id\": \"gOf5GfgI0Nez4RURSEufzw\", \"alias\": \"cuttyhunk-cafe-cuttyhunk\", \"name\": \"Cuttyhunk Cafe\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/RYNQF8pIvAPtEONpFM4F2w/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/cuttyhunk-cafe-cuttyhunk?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 3, \"categories\": [{\"alias\": \"cafes\", \"title\": \"Cafes\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 41.4218711853027, \"longitude\": -70.9290771484375}, \"transactions\": [], \"price\": \"$\", \"location\": {\"address1\": \"Fish Dock\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Cuttyhunk\", \"zip_code\": \"02713\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"Fish Dock\", \"Cuttyhunk, MA 02713\"]}, \"phone\": \"+19419528398\", \"display_phone\": \"(941) 952-8398\", \"distance\": 8031.656406615509}, {\"id\": \"9GXRHktgOm6pOd_BcnXqDQ\", \"alias\": \"woods-hole-oceanogrphic-institute-falmouth\", \"name\": \"Woods Hole Oceanogrphic Institute\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/Fh38mmxos97RUpoD8OUD0w/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/woods-hole-oceanogrphic-institute-falmouth?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1, \"categories\": [{\"alias\": \"nonprofit\", \"title\": \"Community Service/Non-Profit\"}, {\"alias\": \"museums\", \"title\": \"Museums\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 41.5412799, \"longitude\": -70.6485782}, \"transactions\": [], \"location\": {\"address1\": \"\", \"address2\": \"\", \"address3\": \"Woods Hole Oceanographic Institution, 266 Woods Hole Road,\", \"city\": \"Falmouth\", \"zip_code\": \"02543\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"Woods Hole Oceanographic Institution, 266 Woods Hole Road,\", \"Falmouth, MA 02543\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 6455.540065316901}, {\"id\": \"VLMVHHxBmsZFs3hqNmewgA\", \"alias\": \"frank-knowles-little-river-reserve-dartmouth\", \"name\": \"Frank Knowles/Little River Reserve\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/XrbvRWWvQd_pZVYzci5FbQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/frank-knowles-little-river-reserve-dartmouth?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 3, \"categories\": [{\"alias\": \"hiking\", \"title\": \"Hiking\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 41.552435, \"longitude\": -70.985559}, \"transactions\": [], \"location\": {\"address1\": \"Potomska Rd\", \"address2\": \"\", \"address3\": null, \"city\": \"Dartmouth\", \"zip_code\": \"02748\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"Potomska Rd\", \"Dartmouth, MA 02748\"]}, \"phone\": \"+15089912289\", \"display_phone\": \"(508) 991-2289\", \"distance\": 15604.934135252535}, {\"id\": \"6a0C-foCBQvHtDIjx_zm3Q\", \"alias\": \"menemsha-hills-reservation-chilmark\", \"name\": \"Menemsha Hills Reservation\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/DSDN8pr6m0PwaN5uQjY20A/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/menemsha-hills-reservation-chilmark?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2, \"categories\": [{\"alias\": \"hiking\", \"title\": \"Hiking\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 41.364832, \"longitude\": -70.742758}, \"transactions\": [], \"location\": {\"address1\": \"North Rd\", \"address2\": \"\", \"address3\": \"Chilmark\", \"city\": \"Chilmark\", \"zip_code\": \"02535\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"North Rd\", \"Chilmark\", \"Chilmark, MA 02535\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 13472.943379095967}, {\"id\": \"wGQPHH6Wc2A2HIQqFoPS-w\", \"alias\": \"bartholomew-gosnold-monument-cuttyhunk\", \"name\": \"Bartholomew Gosnold Monument\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/OcRZuHtS3auweA9iY8407w/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/bartholomew-gosnold-monument-cuttyhunk?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1, \"categories\": [{\"alias\": \"landmarks\", \"title\": \"Landmarks & Historical Buildings\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 41.42216, \"longitude\": -70.932777}, \"transactions\": [], \"location\": {\"address1\": \"\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Cuttyhunk\", \"zip_code\": \"02713\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"Cuttyhunk, MA 02713\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 8031.656406615509}, {\"id\": \"bjYqWhijEkSmLfMvJyZu-w\", \"alias\": \"sashimi-shack-aquinnah-2\", \"name\": \"Sashimi Shack\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/YE52LNGULIpT2-hxWqX-PQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/sashimi-shack-aquinnah-2?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1, \"categories\": [{\"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"alias\": \"localflavor\", \"title\": \"Local Flavor\"}, {\"alias\": \"raw_food\", \"title\": \"Live/Raw Food\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 41.35423, \"longitude\": -70.7662}, \"transactions\": [], \"location\": {\"address1\": \"58 Basin Rd\", \"address2\": null, \"address3\": \"\", \"city\": \"Aquinnah\", \"zip_code\": \"02535\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"58 Basin Rd\", \"Aquinnah, MA 02535\"]}, \"phone\": \"+17745633394\", \"display_phone\": \"(774) 563-3394\", \"distance\": 13223.752923776121}, {\"id\": \"-SEHb2rvnEGCEcN2eH3XWQ\", \"alias\": \"the-menemsha-deli-chillmark\", \"name\": \"The Menemsha Deli\", \"image_url\": \"\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/the-menemsha-deli-chillmark?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1, \"categories\": [{\"alias\": \"delis\", \"title\": \"Delis\"}, {\"alias\": \"tradamerican\", \"title\": \"American (Traditional)\"}, {\"alias\": \"breakfast_brunch\", \"title\": \"Breakfast & Brunch\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 41.35266, \"longitude\": -70.76499}, \"transactions\": [], \"location\": {\"address1\": \"24 Basin Rd\", \"address2\": null, \"address3\": \"\", \"city\": \"Chillmark\", \"zip_code\": \"02535\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"24 Basin Rd\", \"Chillmark, MA 02535\"]}, \"phone\": \"+15089559471\", \"display_phone\": \"(508) 955-9471\", \"distance\": 13458.690710439207}, {\"id\": \"c3yzVvDhae6bZLCdQVjINA\", \"alias\": \"cuttyhunk-shellfish-farms-cuttyhunk-4\", \"name\": \"Cuttyhunk Shellfish Farms\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/P5VovFH-RtJrrNt1Ch652Q/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/cuttyhunk-shellfish-farms-cuttyhunk-4?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2, \"categories\": [{\"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"alias\": \"raw_food\", \"title\": \"Live/Raw Food\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 41.4214444, \"longitude\": -70.9289679}, \"transactions\": [], \"location\": {\"address1\": \"33 Broadway\", \"address2\": null, \"address3\": \"\", \"city\": \"Cuttyhunk\", \"zip_code\": \"02713\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"33 Broadway\", \"Cuttyhunk, MA 02713\"]}, \"phone\": \"+15086362072\", \"display_phone\": \"(508) 636-2072\", \"distance\": 7500.717119044048}, {\"id\": \"VJKkDUATYmR28XCjIv1fzw\", \"alias\": \"round-hill-beach-hull\", \"name\": \"Round Hill Beach\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/teO0UuwdVKN97dvUZfyJ0g/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/round-hill-beach-hull?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2, \"categories\": [{\"alias\": \"beaches\", \"title\": \"Beaches\"}], \"rating\": 3.5, \"coordinates\": {\"latitude\": 41.5428838, \"longitude\": -70.9480204}, \"transactions\": [], \"location\": {\"address1\": \"Ray Peck Dr\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Hull\", \"zip_code\": \"02748\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"Ray Peck Dr\", \"Hull, MA 02748\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 12698.042407897987}, {\"id\": \"l0f43ZifB-ZUnF1ynjIufw\", \"alias\": \"round-hill-dartmouth\", \"name\": \"Round Hill\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/MLFroGjAkHbvwOMB0s6zhA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/round-hill-dartmouth?adjust_creative=8yyv2vjb-urpHJDcUy2pvQ&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1, \"categories\": [{\"alias\": \"beaches\", \"title\": \"Beaches\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 41.5392033024689, \"longitude\": -70.9444830939174}, \"transactions\": [], \"location\": {\"address1\": \"Smith Neck Rd\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Dartmouth\", \"zip_code\": \"02748\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"Smith Neck Rd\", \"Dartmouth, MA 02748\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 12195.117676692833}], \"total\": 13, \"region\": {\"center\": {\"longitude\": -70.850830078125, \"latitude\": 41.45489478660315}}}";

        JSONAssert.assertEquals(gosnoldMaExpectedData, yelpJsonResponse.toString(), String.valueOf(false));
    }

}