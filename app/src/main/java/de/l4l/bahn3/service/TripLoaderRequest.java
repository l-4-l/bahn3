package de.l4l.bahn3.service;

import lombok.Builder;

/**
 * Created by l4l on 21.01.18.
 */
@Builder(toBuilder = true)
public class TripLoaderRequest {
    private final String URL = "https://openservice-test.vrr.de/static02/XML_TRIP_REQUEST2?language=de";

    String sessionID = "0";
    String name_origin = "Aachener Platz";
    String name_destination = "K%C3%B6ln Hbf";
    int useRealtime = 1;
    int SpEncId = 0;
    int lineRestriction = 403;
    boolean commonMacro = true;
    boolean odvMacro = true;
    String itdLPxx_transpCompany = "vrr";
    String type_origin = "any";
    String type_destination="any";
    String nameInfo_origin="invalid";
    String nameInfo_destination="invalid";


    @Override
    public String toString() {
        /* Form Data:
        ANSIMacro=true&sessionID=0&language=de&requestID=0&command=&itdLPxx_ShowFare=+&itdLPxx_view=&useRealtime=1&itdLPxx_enableMobilityRestrictionOptionsWithButton=&execInst=&itdLPxx_mdvMap2_origin=&itdLPxx_mdvMap2_destination=&itdLPxx_mdvMap2_via=&itdLPxx_mapState_origin=&itdLPxx_mapState_destination=&itdLPxx_mapState_via=&itdLPxx_mdvMap_origin=%3A%3A&itdLPxx_mdvMap_destination=%3A%3A&itdLPxx_mdvMap_via=%3A%3A&itdLPxx_command=&itdLPxx_priceCalculator=&itdLPxx_showTariffLevel=1&ptOptionsActive=1&itOptionsActive=1&itdLPxx_transpCompany=vrr&placeInfo_origin=invalid&typeInfo_origin=invalid&nameInfo_origin=invalid&placeState_origin=empty&nameState_origin=empty&useHouseNumberList_origin=1&place_origin=&type_origin=stop&name_origin=Aachener+Platz&itdLPxx_id_origin=%3Aorigin&placeInfo_destination=invalid&typeInfo_destination=invalid&nameInfo_destination=invalid&placeState_destination=empty&nameState_destination=empty&useHouseNumberList_destination=1&place_destination=&type_destination=stop&name_destination=Rath+Mitte+S&itdLPxx_id_destination=%3Adestination&placeInfo_via=invalid&typeInfo_via=invalid&nameInfo_via=invalid&placeState_via=empty&nameState_via=empty&useHouseNumberList_via=1&place_via=&type_via=stop&name_via=&itdLPxx_id_via=%3Avia&lineRestriction=403&routeType=LEASTTIME&changeSpeed=normal&itdTripDateTimeDepArr=dep&itdTimeHour=21&itdTimeMinute=14&itdDateDay=29&itdDateMonth=01&itdDateYear=2018&submitButton=anfordern&imparedOptionsActive=1&trITDepMOT=100&trITDepMOTvalue100=10&trITArrMOT=100&trITArrMOTvalue100=10&trITDepMOTvalue101=15&trITArrMOTvalue101=15&trITDepMOTvalue104=10&trITArrMOTvalue104=10&trITDepMOTvalue105=30&trITArrMOTvalue105=30&includedMeans=checkbox&inclMOT_0=on&inclMOT_3=on&inclMOT_6=on&inclMOT_9=on&inclMOT_1=on&inclMOT_4=on&inclMOT_7=on&inclMOT_10=on&inclMOT_2=on&inclMOT_5=on&inclMOT_8=on&inclMOT_11=on&maxChanges=9
         */
        StringBuilder sb = new StringBuilder()
                .append(URL);
        if (sessionID != null)
            sb.append("&sessionID=").append(sessionID);

        sb
                .append("&odvMacro=").append(odvMacro)
                .append("&commonMacro=").append(commonMacro)
                .append("&name_origin=").append(name_origin)
                .append("&name_destination=").append(name_destination)
                .append("&useRealtime=").append(useRealtime)
                .append("&itdLPxx_transpCompany=").append(itdLPxx_transpCompany)
                .append("&type_origin=").append(type_origin)
                .append("&type_destination=").append(type_destination)
                .append("&lineRestriction=").append(lineRestriction)
                .append("&SpEncId=").append(SpEncId)
                .append("&nameInfo_origin=").append(nameInfo_origin)
                .append("&nameInfo_destination=").append(nameInfo_destination)
                ;
        return sb.toString();
    }
}
