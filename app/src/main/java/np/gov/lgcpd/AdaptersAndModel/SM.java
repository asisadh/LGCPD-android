package np.gov.lgcpd.AdaptersAndModel;

/**
 * Created by asis on 11/30/16.
 */
public class SM {

    private String id;
    private String name;
    private String phone;
    private String address;
    private String vdc_ward;

    public SM(){

    }

    public SM(String id, String name, String phone, String address, String vdc_ward) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.vdc_ward = vdc_ward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVdc_ward() {
        return vdc_ward;
    }

    public void setVdc_ward(String vdc_ward) {
        this.vdc_ward = vdc_ward;
    }

}
