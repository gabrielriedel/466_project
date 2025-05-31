package RandomForestClasses;

public class DataRecord {

    public double velo;
    public double ivb;
    public double hb;
    public double spin;
    public double tilt;
    public String pitch;

    public DataRecord(double velo, double ivb, double hb, double spin, double tilt, String pitch){
        this.velo = velo;
        this.ivb = ivb;
        this.hb = hb;
        this.spin = spin;
        this.tilt = tilt;
        this.pitch = pitch;
    }
    
}
