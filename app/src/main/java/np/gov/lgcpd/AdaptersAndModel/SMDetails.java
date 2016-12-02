package np.gov.lgcpd.AdaptersAndModel;

/**
 * Created by asis on 12/2/16.
 */
public class SMDetails {

    private String id;
    private String name;
    private String email;
    private String img_name;
    private String phone;
    private String address;
    private String lsp_id;
    private String hired;
    private String vdc;
    private String sex;
    private String dalit;
    private String janajati;
    private String dag;
    private String education;
    private String work_experience;
    private String belong_to;
    private String training;
    private String entry_date;
    private String last_date_modify;
    private String remarks;
    private String district_id;
    private String type;

    public SMDetails(String id, String name, String email, String img_name, String phone, String address, String lsp_id, String hired, String vdc, String sex, String dalit, String janajati, String dag, String education, String work_experience, String belong_to, String training, String entry_date, String last_date_modify, String remarks, String district_id, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.img_name = img_name;
        this.phone = phone;
        this.address = address;
        this.lsp_id = lsp_id;
        this.hired = hired;
        this.vdc = vdc;
        this.sex = sex;
        this.dalit = dalit;
        this.janajati = janajati;
        this.dag = dag;
        this.education = education;
        this.work_experience = work_experience;
        this.belong_to = belong_to;
        this.training = training;
        this.entry_date = entry_date;
        this.last_date_modify = last_date_modify;
        this.remarks = remarks;
        this.district_id = district_id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImg_name() {
        return img_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getLsp_id() {
        return lsp_id;
    }

    public String getHired() {
        return hired;
    }

    public String getVdc() {
        return vdc;
    }

    public String getSex() {
        return sex;
    }

    public String getDalit() {
        return dalit;
    }

    public String getJanajati() {
        return janajati;
    }

    public String getDag() {
        return dag;
    }

    public String getEducation() {
        return education;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public String getBelong_to() {
        return belong_to;
    }

    public String getTraining() {
        return training;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public String getLast_date_modify() {
        return last_date_modify;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public String getType() {
        return type;
    }
}
