public class Building {
    private float longitude;
    private float latitude;
    private int availableBoxes;
    private boolean truck;
    private double distance;

    public Building(float latitude, float longitude , int availableBoxes){
        this.latitude = latitude;
        this.longitude = longitude;
        this.availableBoxes = availableBoxes;
        this.truck = false;
        this.distance = 0;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    public double getDistance() {
        return this.distance;
    }

    public void changeTruck() {
        this.truck = !this.truck;
    }
    public boolean getTruck() {
        return this.truck;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setAvailableBoxes(int availableBoxes) {
        this.availableBoxes = availableBoxes;
    }

    public float getLongitude(){
        return longitude;
    }
    public float getLatitude(){
        return latitude;
    }
    public int getAvailableBoxes(){
        return availableBoxes;
    }

    public void remainingBoxes(int boxesQuantity){
        availableBoxes -= boxesQuantity;
    }

    @Override
    public String toString(){
        return "Position: (" + String.valueOf(latitude) + "," + String.valueOf(longitude) + ")\t"  +
                "Distance: " + String.valueOf(distance) + "\t" +
                "Boxes: " + String.valueOf(availableBoxes) +
                "\n";
    }
}
