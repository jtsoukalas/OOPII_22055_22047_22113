//package gr.hua.oopii.travelAgency.API;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//public class iataParser {
//    public static List parseJSONString(String jsonStr) throws Exception {
//        List<String> keyList = new ArrayList<String>();
//        JSONObject authorJsonObj = (JSONObject) new JSONParser().parse(jsonStr);
//
//        JSONObject citiesJsonObj =iataParser.getChildJSONObjectByName(authorJsonObj, "cities");//441
//        String city_code = (String)citiesJsonObj.get("city_code");
//        String country_code = (String)citiesJsonObj.get("country_code");
//        String distance = ""+citiesJsonObj.get("distance");
//        String lat = ""+citiesJsonObj.get("lat");
//        String lng = ""+citiesJsonObj.get("lng");
//        String name = (String)citiesJsonObj.get("name");
//        String popularity = ""+citiesJsonObj.get("popularity");
//        String slug = (String)citiesJsonObj.get("slug");
//        String timezone = (String)citiesJsonObj.get("timezone");
//        JSONArray airportsJsonArr = iataParser.getChildJSONObjectArrayByName(authorJsonObj, "airports");
//        for(int i=0; i<airportsJsonArr.size(); i++ ) {
//            JSONObject airportsJsonObj = (JSONObject)airportsJsonArr.get(i);
//            String isMulti = (String)airportsJsonObj.get("IsMulti");
//            String city = (String)airportsJsonObj.get("city");
//            String city_codeOf = (String)airportsJsonObj.get("city_code");
//            String country_codeOf = (String)airportsJsonObj.get("country_code");
//            String distanceOf = ""+airportsJsonObj.get("distance");
//            String iata_code = (String)airportsJsonObj.get("iata_code");
//            String icao_code = (String)airportsJsonObj.get("icao_code");
//            String latOf = ""+airportsJsonObj.get("lat");
//            String lngOf = ""+airportsJsonObj.get("lng");
//            String nameOf = (String)airportsJsonObj.get("name");
//            String popularityOf = ""+airportsJsonObj.get("popularity");
//            String slugOf = (String)airportsJsonObj.get("slug");
//            String timezoneOf = (String)airportsJsonObj.get("timezone");
//        }//  airports
//        return keyList;
//    } //parseXmlDocument()
//
//    //CONSTANT ENTRIES:
//
////IMPORT STATEMENTS
//
//
//}
