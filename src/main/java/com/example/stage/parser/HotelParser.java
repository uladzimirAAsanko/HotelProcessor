package com.example.stage.parser;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.example.stage.entity.HotelData;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.api.Record;

public class HotelParser {
    public static final String NULL_DATA = "NA";

    public static HotelData parse(Record record){
        long id = Long.parseLong(getValue(record,0));
        String name = getValue(record, 1);
        String country = getValue(record, 2);
        String city = getValue(record, 3);
        String address = getValue(record, 4);
        String longitude = getValue(record, 5);
        String latitude = getValue(record, 6);
        double real_ltd = Long.parseLong(latitude);
        double real_lng = Long.parseLong(longitude);
        return new HotelData(id, name, country, city, address, real_lng,real_ltd);
    }

    public static String getValue(Record record, int number){
        String fieldPath  = "/" + number;
        Field field = record.get(fieldPath);
        return field.getValueAsString();
    }
}
