package bu.met.cs622;

/**
 * The RealEstateAnalyzer program will evaluate different types of real estate
 * investments and generate output to see if the investment is potentially profitable.
 *
 * @author  Scott Kaeneman
 * @version 1.0
 * @since   2020-22-01
 */

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {

    public static void main(String[] args) {
//      ArrayList<String> propertyReport;
        double propertyPrice;
        String propertyType = null;

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter 1 for manual input mode or 2 to load data from a file.");
        try {
            String userInput = in.next();

            switch (userInput) {
                case "1":
                    getUserKeyboardInput();
                    break;
                case "2":
                    getUserFileInput();
                    break;
                default:
                    System.err.printf("%s is not a valid option", userInput);
            }
        }
        catch (Exception ex) {
            System.err.printf("User input error...%s", ex.getMessage());
            ex.printStackTrace();
        }


    } //main

    public static void getUserKeyboardInput() throws Exception {
        ArrayList<String> propertyReport;
        String propertyType;
        HashMap<Boolean, String> wantsToPrint = null;

        // get user input from Scanner
        Scanner input = new Scanner(System.in);

        System.out.println("Enter 'm' for multi-family or 's' for single-family investment property:");
        propertyType = input.nextLine();
        propertyType = propertyType.trim();

        // ensure input is valid
        if (propertyType.equals("m") || propertyType.equals("s")) {
            try {
                System.out.println("Enter price of investment property:");
                Integer propertyPrice = Integer.valueOf(input.nextLine());
                wantsToPrint = getPrintResponse();  // check if user wants to print

            } catch (NumberFormatException e) {
                System.err.printf("error: Not a valid number %s. %nexiting program...", e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            System.err.println("Sorry that was not a valid choice.  Program ending.");
            System.exit(0);
        }

        // run real estate analysis based upon the type of property the user entered
        if (propertyType.equals("m")) {
            // downcast
            InvestmentProperty multiFamProp = new MultiFamilyProperty(4.7, 3000, 300000,
                    30, 1200, 15000,
                    1200, 6000, 850000,
                    90000);

            if (multiFamProp instanceof MultiFamilyProperty) {
                propertyReport =
                        ((MultiFamilyProperty) multiFamProp).analyzeMultiFamilyProperty((MultiFamilyProperty) multiFamProp);
                ((MultiFamilyProperty) multiFamProp).display(propertyReport);

                // output property square feet
                System.out.println(multiFamProp.propertySquareFootage());
                // print the report if the HashMap key is set to true
                if (wantsToPrint.containsKey(true)) {
                    String filePathToPrint = wantsToPrint.get(true); // get the file path from the key\value pair
                    if (filePathToPrint != null && !filePathToPrint.isEmpty())  {
                        print(propertyReport, filePathToPrint);
                    }
                }

                // get data from yelp API
                YelpAPI yelpAPIData = new YelpAPI();
                Object yelp = yelpAPIData.getYelpData("Boston", "MA");
                System.out.printf("Fetching data from Yelp API in JSON format...%n%s", yelp);
                getYelpData();

            }
        }
        else if (propertyType.equals("s")) {
            // downcast
            InvestmentProperty singleFamProp = new SingleFamilyProperty(3.9, 3230, 400000,
                    20, 12550, 12200,
                    1456, 5600, 950000,
                    75000);

            if (singleFamProp instanceof SingleFamilyProperty) {
                propertyReport = ((SingleFamilyProperty) singleFamProp).analyzeSingleFamilyProperty((SingleFamilyProperty) singleFamProp);
                ((SingleFamilyProperty) singleFamProp).display(propertyReport);
                System.out.println(singleFamProp.propertySquareFootage());

                // print the report if the HashMap key is set to true
                if (wantsToPrint.containsKey(true)) {
                    String filePathToPrint = wantsToPrint.get(true); // get the file path from the key\value pair
                    if (filePathToPrint != null && !filePathToPrint.isEmpty())  {
                        print(propertyReport, filePathToPrint);
                    }
                }

                // get data from yelp API
                YelpAPI yelpAPITest = new YelpAPI();
                Object yelpOutput = yelpAPITest.getYelpData("Boston", "MA");
                System.out.printf("Fetching data from Yelp API in JSON format...%n%s", yelpOutput);
            }
        }
        input.close();
    }


    // TODO: Remove pathname /Users/scott/Desktop/testInput.txt
    public static void getUserFileInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter full path including filename (e.g. /Users/scott/myFile.txt):");
        String userInputPath = in.nextLine();

        try{
           Scanner infile = new Scanner(new File(userInputPath.trim()));

            while (infile.hasNext())
            {
                System.out.printf("%s%n", infile.nextLine());
                //          infile.next());
            }
            infile.close();
        } catch(FileNotFoundException e) {
            System.err.printf("Cannot open file: %s ", userInputPath.trim());
            System.exit(0);
        }
        in.close();
    }

    /**
     * Determines if the user wants to print the results to a file
     * @return      a HashMap of true/filePath if they want to print, or false\null
     */
    public static HashMap<Boolean, String> getPrintResponse() {
        String filePath = null;
        String print;

        // create a HashMap that will hold the valued returned to the caller
        HashMap<Boolean, String> printMap = new HashMap<Boolean, String>();

        Scanner in = new Scanner(System.in);

        System.out.println("Write output to file (yes/no):");
        print = in.nextLine();

        // print to a file if user has selected to do so
        if ((print.equalsIgnoreCase("yes")) || (print.equalsIgnoreCase("y"))) {
            System.out.println("Enter file path and file name to generate output (e.g. C:\\report.txt)");
            filePath = in.nextLine();
            printMap.put(true, filePath);  // user wants to print so store the file path
        } else {
            // user does not want to print the results
            printMap.put(false, null);
        }
        return printMap; // return the HashMap to the caller
    }

    /**
     * Prints the output of the property analysis to a file
     * @param  propertyAnalysis    an ArrayList of strings that describe the property
     */
    public static void print(ArrayList<String> propertyAnalysis, String filePath) {
        Formatter outfile = null;

        // get the current date and time and format it
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = time.format(formatter);

        // try and write to an output file
        try {
            outfile = new Formatter(filePath); // open file
            outfile.format("******************************************%n", formatDateTime);
            outfile.format("Property Investment Report %n");
            outfile.format("Timestamp: %s%n", formatDateTime);
            outfile.format("******************************************%n%n", formatDateTime);

            // loop through items and add them to the report
            for (String prop : propertyAnalysis) {
                outfile.format("%s%n", prop);
            }

            // check if the file was successfully written to
            Path path = Paths.get(filePath);
            boolean pathExists = Files.exists(path);
            File file = new File(String.valueOf(path));

            if (pathExists) {
                System.out.printf("File successfully written to: %s%n", file.getAbsolutePath());
            } else {
                System.err.print("Could not write to file.  Check that the path is correct...");
            }
        }
        catch (FileNotFoundException ex) {
            System.err.printf("Uh-oh cannot write to file path: %s ...exiting", filePath);
            System.exit(0);
        }
        outfile.close();
    }


    public static void getYelpData() throws IOException {

        // use googles gson library for handling json objects
        Gson gson = new Gson();

        // get data from yelp API
        YelpAPI yelpAPIData = new YelpAPI();
        String yelp = yelpAPIData.getYelpData("Boston", "MA");
//      System.out.printf("Fetching data from Yelp API in JSON format...%n%s", yelp);

//      String json_string = "{\"businesses\": [{\"id\": \"y2w6rFaO0XEiG5mFfOsiFA\", \"alias\": \"neptune-oyster-boston\", \"name\": \"Neptune Oyster\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/y7AP-ocem6fDCyqV2k-T5Q/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/neptune-oyster-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 4996, \"categories\": [{\"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"alias\": \"bars\", \"title\": \"Bars\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.3632711771092, \"longitude\": -71.0560575975533}, \"transactions\": [], \"price\": \"$$$\", \"location\": {\"address1\": \"63 Salem St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02113\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"63 Salem St\", \"Boston, MA 02113\"]}, \"phone\": \"+16177423474\", \"display_phone\": \"(617) 742-3474\", \"distance\": 2121.4610478884797}, {\"id\": \"PrsvO1rzkgg6qFizlAoEtg\", \"alias\": \"mikes-pastry-boston\", \"name\": \"Mike\u0027s Pastry\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/WFnTLag33g51879Sg12Rgw/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/mikes-pastry-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 7128, \"categories\": [{\"alias\": \"bakeries\", \"title\": \"Bakeries\"}, {\"alias\": \"desserts\", \"title\": \"Desserts\"}, {\"alias\": \"gelato\", \"title\": \"Gelato\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.3641813865072, \"longitude\": -71.0542184385064}, \"transactions\": [], \"price\": \"$\", \"location\": {\"address1\": \"300 Hanover St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02113\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"300 Hanover St\", \"Boston, MA 02113\"]}, \"phone\": \"+16177423050\", \"display_phone\": \"(617) 742-3050\", \"distance\": 2293.052981586687}, {\"id\": \"2u_w3rthRzR2uEihW5BC4A\", \"alias\": \"giacomos-ristorante-boston\", \"name\": \"Giacomo\u0027s Ristorante\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/AfzWBQYFuCvP1gHZ5aUccw/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/giacomos-ristorante-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 3411, \"categories\": [{\"alias\": \"italian\", \"title\": \"Italian\"}, {\"alias\": \"wine_bars\", \"title\": \"Wine Bars\"}, {\"alias\": \"seafood\", \"title\": \"Seafood\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.36458, \"longitude\": -71.05344}, \"transactions\": [\"delivery\", \"pickup\"], \"price\": \"$$\", \"location\": {\"address1\": \"355 Hanover St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02113\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"355 Hanover St\", \"Boston, MA 02113\"]}, \"phone\": \"+16175239026\", \"display_phone\": \"(617) 523-9026\", \"distance\": 2372.4665686945236}, {\"id\": \"VnuD2cojPTWd3nIHQjnL8w\", \"alias\": \"island-creek-oyster-bar-boston\", \"name\": \"Island Creek Oyster Bar\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/z8LVcLUvpjIbXvtTtGJK7g/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/island-creek-oyster-bar-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2736, \"categories\": [{\"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"alias\": \"bars\", \"title\": \"Bars\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.348682243174, \"longitude\": -71.095118452365}, \"transactions\": [], \"price\": \"$$$\", \"location\": {\"address1\": \"500 Commonwealth Ave\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02215\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"500 Commonwealth Ave\", \"Boston, MA 02215\"]}, \"phone\": \"+16175325300\", \"display_phone\": \"(617) 532-5300\", \"distance\": 1964.2876714819959}, {\"id\": \"kP1b-7BO_VhWk_0tvuA_tw\", \"alias\": \"carmelinas-boston-2\", \"name\": \"Carmelina\u0027s\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/WQi0dkl1EWam9toAwLl3qQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/carmelinas-boston-2?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2093, \"categories\": [{\"alias\": \"italian\", \"title\": \"Italian\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.36388, \"longitude\": -71.05415}, \"transactions\": [\"delivery\", \"restaurant_reservation\"], \"price\": \"$$\", \"location\": {\"address1\": \"307 Hanover St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02113\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"307 Hanover St\", \"Boston, MA 02113\"]}, \"phone\": \"+16177420020\", \"display_phone\": \"(617) 742-0020\", \"distance\": 2272.1796574310406}, {\"id\": \"EbUZhM4fLpsWQ8fpBhhgEQ\", \"alias\": \"mike-and-pattys-boston\", \"name\": \"Mike \u0026 Patty\u0027s\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/5yjX0pGQKxWpqvMxtmlFXQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/mike-and-pattys-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1668, \"categories\": [{\"alias\": \"sandwiches\", \"title\": \"Sandwiches\"}, {\"alias\": \"breakfast_brunch\", \"title\": \"Breakfast \u0026 Brunch\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.348561, \"longitude\": -71.067928}, \"transactions\": [\"pickup\", \"delivery\"], \"price\": \"$\", \"location\": {\"address1\": \"12 Church St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"12 Church St\", \"Boston, MA 02116\"]}, \"phone\": \"+16174233447\", \"display_phone\": \"(617) 423-3447\", \"distance\": 283.9483626062025}, {\"id\": \"AGR_kBvN__I7aTl0rBywAg\", \"alias\": \"atlantic-fish-co-boston-2\", \"name\": \"Atlantic Fish Co\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/_e7sr3QD8aY5NkkNrkIy8A/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/atlantic-fish-co-boston-2?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2520, \"categories\": [{\"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"alias\": \"raw_food\", \"title\": \"Live/Raw Food\"}, {\"alias\": \"cocktailbars\", \"title\": \"Cocktail Bars\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.3492217873536, \"longitude\": -71.0811272917217}, \"transactions\": [\"delivery\"], \"price\": \"$$$\", \"location\": {\"address1\": \"761 Boylston St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"761 Boylston St\", \"Boston, MA 02116\"]}, \"phone\": \"+16172674000\", \"display_phone\": \"(617) 267-4000\", \"distance\": 826.6340017964785}, {\"id\": \"NYa-JphaaB41ElGsb3iawA\", \"alias\": \"toro-boston-2\", \"name\": \"Toro\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/vavJneJjBulHvZAGit-bdQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/toro-boston-2?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2103, \"categories\": [{\"alias\": \"tapas\", \"title\": \"Tapas Bars\"}, {\"alias\": \"spanish\", \"title\": \"Spanish\"}, {\"alias\": \"cocktailbars\", \"title\": \"Cocktail Bars\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.33698788, \"longitude\": -71.07592429}, \"transactions\": [], \"price\": \"$$$\", \"location\": {\"address1\": \"1704 Washington St\", \"address2\": null, \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02118\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"1704 Washington St\", \"Boston, MA 02118\"]}, \"phone\": \"+16175364300\", \"display_phone\": \"(617) 536-4300\", \"distance\": 1266.7234617418144}, {\"id\": \"54ElwAyN-o8e4uvOkC85hw\", \"alias\": \"modern-pastry-shop-boston\", \"name\": \"Modern Pastry Shop\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/tFBjPoE10eyvIdxl0aTDzQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/modern-pastry-shop-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1959, \"categories\": [{\"alias\": \"bakeries\", \"title\": \"Bakeries\"}, {\"alias\": \"coffee\", \"title\": \"Coffee \u0026 Tea\"}, {\"alias\": \"desserts\", \"title\": \"Desserts\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.36324, \"longitude\": -71.05474}, \"transactions\": [\"delivery\"], \"price\": \"$\", \"location\": {\"address1\": \"257 Hanover St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02113\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"257 Hanover St\", \"Boston, MA 02113\"]}, \"phone\": \"+16175233783\", \"display_phone\": \"(617) 523-3783\", \"distance\": 2185.181158712615}, {\"id\": \"i6uHrxuS1D_6V8WhaWLy5A\", \"alias\": \"lukes-lobster-back-bay-boston\", \"name\": \"Luke\u0027s Lobster Back Bay\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/fri_Oe_6JlSrKoJjAOTtZA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/lukes-lobster-back-bay-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1736, \"categories\": [{\"alias\": \"seafood\", \"title\": \"Seafood\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.3485601423781, \"longitude\": -71.0791224248571}, \"transactions\": [\"pickup\"], \"price\": \"$$\", \"location\": {\"address1\": \"75 Exeter Street\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"75 Exeter Street\", \"Boston, MA 02116\"]}, \"phone\": \"+18573504626\", \"display_phone\": \"(857) 350-4626\", \"distance\": 683.9136258574645}, {\"id\": \"w2F5N4h26hOrz2KoxThomw\", \"alias\": \"sam-lagrassas-boston-3\", \"name\": \"Sam LaGrassa\u0027s\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/JcSU7Vgu3iQPrJ60FMLiUA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/sam-lagrassas-boston-3?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 998, \"categories\": [{\"alias\": \"delis\", \"title\": \"Delis\"}, {\"alias\": \"sandwiches\", \"title\": \"Sandwiches\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.35689, \"longitude\": -71.05994}, \"transactions\": [\"delivery\", \"pickup\"], \"price\": \"$$\", \"location\": {\"address1\": \"44 Province St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02108\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"44 Province St\", \"Boston, MA 02108\"]}, \"phone\": \"+16173576861\", \"display_phone\": \"(617) 357-6861\", \"distance\": 1368.9618622417086}, {\"id\": \"htEuhPBhBgMs6ShlT3G3JA\", \"alias\": \"regina-pizzeria-boston-28\", \"name\": \"Regina Pizzeria\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/6_j2VgsFthPHf5TGxBhVLA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/regina-pizzeria-boston-28?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1982, \"categories\": [{\"alias\": \"pizza\", \"title\": \"Pizza\"}, {\"alias\": \"italian\", \"title\": \"Italian\"}, {\"alias\": \"wine_bars\", \"title\": \"Wine Bars\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.3654185550275, \"longitude\": -71.0568833173494}, \"transactions\": [\"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"11 1/2 Thacher St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02113\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"11 1/2 Thacher St\", \"Boston, MA 02113\"]}, \"phone\": \"+16172270765\", \"display_phone\": \"(617) 227-0765\", \"distance\": 2283.017583313521}, {\"id\": \"sA2gVTJOBH7Qk32p48ENdQ\", \"alias\": \"saltie-girl-boston\", \"name\": \"Saltie Girl\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/2uVuafFO-X34KCmxS2AhBQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/saltie-girl-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 970, \"categories\": [{\"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"alias\": \"wine_bars\", \"title\": \"Wine Bars\"}, {\"alias\": \"cocktailbars\", \"title\": \"Cocktail Bars\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.3511377, \"longitude\": -71.0776626}, \"transactions\": [\"delivery\"], \"price\": \"$$$\", \"location\": {\"address1\": \"281 Dartmouth St\", \"address2\": \"\", \"address3\": null, \"city\": \"Boston\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"281 Dartmouth St\", \"Boston, MA 02116\"]}, \"phone\": \"+16172670691\", \"display_phone\": \"(617) 267-0691\", \"distance\": 649.1890070149382}, {\"id\": \"yBIN7uSzd5saehWA1I_GBw\", \"alias\": \"row-34-boston\", \"name\": \"Row 34\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/z_htxXSQ6llGq-Zlxvp5qQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/row-34-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1461, \"categories\": [{\"alias\": \"newamerican\", \"title\": \"American (New)\"}, {\"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"alias\": \"bars\", \"title\": \"Bars\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.3495808510305, \"longitude\": -71.0475044128162}, \"transactions\": [\"delivery\"], \"price\": \"$$$\", \"location\": {\"address1\": \"383 Congress St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02210\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"383 Congress St\", \"Boston, MA 02210\"]}, \"phone\": \"+16175535900\", \"display_phone\": \"(617) 553-5900\", \"distance\": 1960.4098750144674}, {\"id\": \"-5gWvrcKOPmhlcZju3tpbw\", \"alias\": \"flour-bakery-caf\\u00e9-boston-4\", \"name\": \"Flour Bakery + Caf\\u00e9\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/luLfrbrEtRC_zw5RsDUE5g/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/flour-bakery-caf%C3%A9-boston-4?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1148, \"categories\": [{\"alias\": \"bakeries\", \"title\": \"Bakeries\"}, {\"alias\": \"coffee\", \"title\": \"Coffee \u0026 Tea\"}, {\"alias\": \"sandwiches\", \"title\": \"Sandwiches\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.35137, \"longitude\": -71.04881}, \"transactions\": [], \"price\": \"$$\", \"location\": {\"address1\": \"12 Farnsworth St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02210\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"12 Farnsworth St\", \"Boston, MA 02210\"]}, \"phone\": \"+16173384333\", \"display_phone\": \"(617) 338-4333\", \"distance\": 1886.6887381554013}, {\"id\": \"u51e3gychuqBYNqe2xG6_w\", \"alias\": \"lolita-cocina-and-tequila-bar-back-bay-boston\", \"name\": \"Lolita Cocina \u0026 Tequila Bar Back Bay\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/4uGTPcEuAgAxaNJbSzecyA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/lolita-cocina-and-tequila-bar-back-bay-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 2134, \"categories\": [{\"alias\": \"mexican\", \"title\": \"Mexican\"}, {\"alias\": \"lounges\", \"title\": \"Lounges\"}, {\"alias\": \"cocktailbars\", \"title\": \"Cocktail Bars\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.3505860446405, \"longitude\": -71.077508474716}, \"transactions\": [\"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"271 Dartmouth St\", \"address2\": null, \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"271 Dartmouth St\", \"Boston, MA 02116\"]}, \"phone\": \"+16173695609\", \"display_phone\": \"(617) 369-5609\", \"distance\": 598.5047280006519}, {\"id\": \"xlOMKjE4omTgkI1eduWj8A\", \"alias\": \"the-friendly-toast-back-bay\", \"name\": \"The Friendly Toast\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/chywdZRvRKjtowlabYhtsg/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/the-friendly-toast-back-bay?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1590, \"categories\": [{\"alias\": \"bars\", \"title\": \"Bars\"}, {\"alias\": \"breakfast_brunch\", \"title\": \"Breakfast \u0026 Brunch\"}, {\"alias\": \"diners\", \"title\": \"Diners\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.3483479318913, \"longitude\": -71.0732730478048}, \"transactions\": [\"pickup\", \"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"35 Stanhope St\", \"address2\": null, \"address3\": \"\", \"city\": \"Back Bay\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"35 Stanhope St\", \"Back Bay, MA 02116\"]}, \"phone\": \"+16174567849\", \"display_phone\": \"(617) 456-7849\", \"distance\": 176.04842333150364}, {\"id\": \"t_FFcwUutj9mIYKGw_gHsQ\", \"alias\": \"the-salty-pig-boston\", \"name\": \"The Salty Pig\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/5UzgfJ2l3LZDBspzRrFfXw/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/the-salty-pig-boston?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1675, \"categories\": [{\"alias\": \"newamerican\", \"title\": \"American (New)\"}, {\"alias\": \"italian\", \"title\": \"Italian\"}, {\"alias\": \"pizza\", \"title\": \"Pizza\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.346881, \"longitude\": -71.076121}, \"transactions\": [\"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"130 Dartmouth St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"130 Dartmouth St\", \"Boston, MA 02116\"]}, \"phone\": \"+16175366200\", \"display_phone\": \"(617) 536-6200\", \"distance\": 414.84627755422105}, {\"id\": \"mP1EdIafQKMuOm9O4PzAfA\", \"alias\": \"barcelona-wine-bar-south-end-boston-6\", \"name\": \"Barcelona Wine Bar South End\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/5lBFhbGSGHiSALgZHBa7XQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/barcelona-wine-bar-south-end-boston-6?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1038, \"categories\": [{\"alias\": \"spanish\", \"title\": \"Spanish\"}, {\"alias\": \"wine_bars\", \"title\": \"Wine Bars\"}, {\"alias\": \"tapasmallplates\", \"title\": \"Tapas/Small Plates\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 42.3449355147724, \"longitude\": -71.0705436362457}, \"transactions\": [\"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"525 Tremont St\", \"address2\": null, \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02116\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"525 Tremont St\", \"Boston, MA 02116\"]}, \"phone\": \"+16172662600\", \"display_phone\": \"(617) 266-2600\", \"distance\": 328.22766734138577}, {\"id\": \"p8ohzzGvGRCHnJKnyO7exA\", \"alias\": \"eastern-standard-kitchen-and-drinks-boston-3\", \"name\": \"Eastern Standard Kitchen \u0026 Drinks\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/ZrQG8dxkvhPj9LxezQe6lQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/eastern-standard-kitchen-and-drinks-boston-3?adjust_creative\u003d8yyv2vjb-urpHJDcUy2pvQ\u0026utm_campaign\u003dyelp_api_v3\u0026utm_medium\u003dapi_v3_business_search\u0026utm_source\u003d8yyv2vjb-urpHJDcUy2pvQ\", \"review_count\": 1886, \"categories\": [{\"alias\": \"newamerican\", \"title\": \"American (New)\"}, {\"alias\": \"lounges\", \"title\": \"Lounges\"}, {\"alias\": \"breakfast_brunch\", \"title\": \"Breakfast \u0026 Brunch\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 42.348740324378, \"longitude\": -71.0960168391466}, \"transactions\": [], \"price\": \"$$$\", \"location\": {\"address1\": \"528 Commonwealth Ave\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Boston\", \"zip_code\": \"02215\", \"country\": \"US\", \"state\": \"MA\", \"display_address\": [\"528 Commonwealth Ave\", \"Boston, MA 02215\"]}, \"phone\": \"+16175329100\", \"display_phone\": \"(617) 532-9100\", \"distance\": 2036.378048351848}], \"total\": 7400, \"region\": {\"center\": {\"longitude\": -71.07124328613281, \"latitude\": 42.34784169448538}}}";

        // process the json data with gson
        YelpInfo jsonYelp =  gson.fromJson(yelp, YelpInfo.class);
        List<YelpInfo> businesses = jsonYelp.getBusinesses();

        GenericStack<String> businessInfo = new GenericStack<>();

        for (int i = 0; i < businesses.size(); i++) {

            List<YelpInfo> cats = businesses.get(i).categories;





//            List<YelpInfo> cat = businesses.get(i);
//            for (int j = 0; j < cat.size(); j++) {
//                System.out.printf("category: %s%n", cat.get(j).);
//            }

            // push elements onto the generic stack
//            businessInfo.push("url: " + businesses.get(i).url);
//            businessInfo.push("distance in meters: " + businesses.get(i).distance);
//            businessInfo.push("business closed: " + businesses.get(i).isClosed);
//            businessInfo.push("rating: " + businesses.get(i).rating);
//            businessInfo.push("name: " + businesses.get(i).name);
////            businessInfo.push("categories: " + businesses.get(i).categories);
//            businessInfo.push(" ");

            System.out.printf("name: %s%n", businesses.get(i).name);
//            System.out.printf("distance in meters: %s%n", businesses.get(i).distance);
//            System.out.printf("closed: %s%n", businesses.get(i).isClosed);
//            System.out.printf("rating: %s%n", businesses.get(i).rating);
//            System.out.printf("url: %s%n", businesses.get(i).url);
//
//            Object location = businesses.get(i).location;
//            System.out.printf("location: %s%n", businesses.get(i).location);
//            System.out.println("");


//            List<YelpInfo> categories = businesses.get(i).getCategories();
//            for (int x = 0; x < categories.size(); x++) {
//                String cat = categories.get(x).toString();
//                System.out.println(cat);
//            }

        }

        // output data by popping elements off the generic stack
//        for (int i = 0; i < businessInfo.stackSize(); i++) {
//            String output = businessInfo.pop();
//            System.out.println(output);
//        }

    }


}// class
