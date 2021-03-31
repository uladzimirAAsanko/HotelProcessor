package com.example.stage.entity;

public class HotelData {
    private long id;
    private String name;
    private String country;
    private String city;
    private String address;
    private double longitude;
    private double latitude;

    public HotelData(long id, String name, String country, String city, String address, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 19 + (int)id;
        result = result * 19 + (int)longitude;
        result = result * 19 + (int)latitude;
        result = result * 19 + name.hashCode();
        result = result * 19 + city.hashCode();
        result = result * 19 + country.hashCode();
        result = result * 19 + address.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        HotelData data = (HotelData) obj;
        return data.id == id && data.getName().equals(name) && data.getCountry().equals(country) && city.equals(data.getCity())
        && address.equals(data.getAddress()) && longitude == data.getLongitude() && latitude == data.getLatitude();
    }
}
