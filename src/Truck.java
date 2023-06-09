public class Truck {
    private int truckLatitude;
    private int truckLongitude;
    private int capacity;

    public Truck(int truckLatitude, int truckLongitude, int capacity){
        this.truckLatitude = truckLatitude;
        this.truckLongitude = truckLongitude;
        this.capacity = capacity;
    }
    public void setTruckLatitude(int truckLatitude) {
        this.truckLatitude = truckLatitude;
    }

    public void setTruckLongitude(int truckLongitude) {
        this.truckLongitude = truckLongitude;
    }

    public void setCapcity(int capcity) {
        this.capacity = capcity;
    }

    public int getTruckLatitude() {
        return truckLatitude;
    }

    public int getTruckLongitude() {
        return truckLongitude;
    }

    public int getCapcity() {
        return capacity;
    }

    public boolean availability(){
        return capacity > 0;
    }

    public void truckLoad(int quantity){
        capacity -= quantity;
    }
}
