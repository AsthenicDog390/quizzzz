package commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@Table(name = "Activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long activity_ID;

    @JsonProperty("id")
    private String id;
    private String title;
    @JsonProperty("consumption_in_wh")
    private Long consumptionInWh;
    @JsonProperty("image_path")
    private String imagePath;
    private String source;

    @SuppressWarnings("unused")
    private Activity() {
        // for object mapper
    }

    public Activity(String id, Long activity_ID, String imagePath, String title, Long consumptionInWh, String source) {
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
        this.consumptionInWh = consumptionInWh;
        this.source = source;
        this.activity_ID = activity_ID;
    }

    public Long getActivity_ID() {
        return activity_ID;
    }

    public void setActivity_ID(Long activity_ID) {
        this.activity_ID = activity_ID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getConsumptionInWh() {
        return consumptionInWh;
    }

    public void setConsumptionInWh(Long consumptionInWh) {
        this.consumptionInWh = consumptionInWh;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}