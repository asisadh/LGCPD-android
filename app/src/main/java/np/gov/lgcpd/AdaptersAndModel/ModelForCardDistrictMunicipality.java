package np.gov.lgcpd.AdaptersAndModel;

/**
 * Created by asis on 11/29/16.
 */
public class ModelForCardDistrictMunicipality {
    private String name;
    private String type;
    private String region_id;

    public ModelForCardDistrictMunicipality(String name, String type, String region_id){
        this.name = name;
        this.type = type;
        this.region_id = region_id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getRegion_id() {
        return region_id;
    }
}
