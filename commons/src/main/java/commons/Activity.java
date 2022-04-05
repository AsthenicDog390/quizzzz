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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* activity_id is a field needed to be able to easily select a random activity */
    public long activity_id;

    @JsonProperty("id")
    @Column(name = "id")
    private String id;

    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonProperty("consumption_in_wh")
    @Column(name = "consumption_in_wh")
    private long consumptionInWh;

    @JsonProperty("image_path")
    @Column(name = "imagePath")
    private String imagePath;

    @Column(name = "source", length = 450)
    private String source;

    @SuppressWarnings("unused")
    private Activity() {
        // for object mapper
    }

    public Activity(String id, String imagePath, String title, long consumptionInWh, String source) {
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
        this.consumptionInWh = consumptionInWh;
        this.source = source;
    }

    public long getActivity_ID() {
        return activity_id;
    }

    public void setActivity_ID(long activity_ID) {
        this.activity_id = activity_ID;
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

    public long getConsumptionInWh() {
        return consumptionInWh;
    }

    public void setConsumptionInWh(long consumptionInWh) {
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