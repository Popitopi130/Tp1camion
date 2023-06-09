public class Building {
    private double longitude;
    private double latitude;
    private int availableBoxes;

    public Building(double latitude, double longitude , int availableBoxes){
        this.latitude = latitude;
        this.longitude = longitude;
        this.availableBoxes = availableBoxes;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAvailableBoxes(int availableBoxes) {
        this.availableBoxes = availableBoxes;
    }

    public double getLongitude(){
        return longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public int getAvailableBoxes(){
        return availableBoxes;
    }

    public void remainingBoxes(int boxesQuantity){
        availableBoxes -= boxesQuantity;
    }
}
