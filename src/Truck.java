public class Truck {
    private float truckLatitude;
    private float truckLongitude;
    private int capacity;

    public Truck(float truckLatitude, float truckLongitude, int capacity){
        this.truckLatitude = truckLatitude;
        this.truckLongitude = truckLongitude;
        this.capacity = capacity;
    }
    public void setTruckLatitude(float truckLatitude) {
        this.truckLatitude = truckLatitude;
    }

    public void setTruckLongitude(float truckLongitude) {
        this.truckLongitude = truckLongitude;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getTruckLatitude() {
        return truckLatitude;
    }

    public float getTruckLongitude() {
        return truckLongitude;
    }

    public int getCapacity() {
        return capacity;
    }




    public boolean availability(){
        return capacity > 0;
    }

    public void truckLoad(int quantity){
        capacity -= quantity;
    }
}
