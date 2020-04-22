package Implementations;

import Model.Location.Location;
import View.loaders.LocationLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class GeoNamesLocationLoader implements LocationLoader {

    private static String APIKey="GerardoSantana";

    @Override
    public Location load(int postalCode, String countryCode) {
        String xml = getXMLApiResponse(searchLink(postalCode,countryCode)).html();
        try {
            return getLocationFromXML(xml);
        } catch(Exception e){
            return null;
        }
    }

    private Document getXMLApiResponse(String link){
        try{
            return Jsoup.connect(link).get();
        } catch (Exception e){
            return getXMLApiResponse(link);
        }
    }

    private String searchLink(int postalCode, String countryCode) {
        return "http://api.geonames.org/postalCodeSearch?postalcode=" + postalCode + "&country=" + countryCode + "&maxRows=1&username=" + APIKey;
    }

    private Location getLocationFromXML(String xml) {
        return parseTag(xml, "totalResultsCount").equals("0")? null :
                new Location(parseInt(parseTag(xml,"postalcode")), parseTag(xml, "name"),parseTag(xml, "countryCode"),
                parseDouble(parseTag(xml,"lat")), parseDouble(parseTag(xml, "lng")), parseTag(xml,"adminName1"),
                        parseTag(xml, "adminName2"),parseTag(xml, "adminName3"));
    }

    private String parseTag(String xml, String tag) {
        return xml.substring(xml.indexOf("<"+tag+">")+tag.length()+2,xml.indexOf("</"+tag+">")).trim();
    }




}
