package np.gov.lgcpd.AdaptersAndModel;

/**
 * Created by asis on 12/2/16.
 */
public class LSPDetail {
    private String id;
    private String name;
    private String address;
    private String officePhone;
    private String contactPerson;
    private String chairman;
    private String contactEmail;
    private String contactPhone;
    private String contactMobile;
    private String chairmanMobile;
    private String chairmanEmail;
    private String remark;

    public LSPDetail(String id, String name, String address, String officePhone, String contactPerson, String chairman, String contactEmail, String contactPhone, String contactMobile, String chairmanMobile, String chairmanEmail, String remark) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.officePhone = officePhone;
        this.contactPerson = contactPerson;
        this.chairman = chairman;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactMobile = contactMobile;
        this.chairmanMobile = chairmanMobile;
        this.chairmanEmail = chairmanEmail;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getChairman() {
        return chairman;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public String getChairmanMobile() {
        return chairmanMobile;
    }

    public String getChairmanEmail() {
        return chairmanEmail;
    }

    public String getRemark() {
        return remark;
    }
}
