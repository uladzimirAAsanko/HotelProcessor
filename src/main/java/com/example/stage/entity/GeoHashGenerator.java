package com.example.stage.entity;

import ch.hsr.geohash.GeoHash;

public class GeoHashGenerator {
    public static String generateGeoHash(HotelData hotel){
        GeoHash geoHash = GeoHash.withCharacterPrecision(hotel.getLatitude(), hotel.getLongitude(), 4);
        String geoHashString = geoHash.toBase32();
        return geoHashString;
    }
}
