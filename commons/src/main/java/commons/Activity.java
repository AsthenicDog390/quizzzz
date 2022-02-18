package commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /** activity_id is a field needed to be able to easily select a random activity.
     * */
    public long activity_id;

    public String id;
    public String title;
    @JsonProperty("consumption_in_wh")
    public int consumptionInWh;
    public String source;

    @SuppressWarnings("unused")
    private Activity() {
        // for object mapper
    }

    public Activity(String id, String title, int consumptionInWh, String source) {
        this.id = id;
        this.title = title;
        this.consumptionInWh = consumptionInWh;
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