package commons.questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MoreExpensive.class, name = "moreExpensive"),
        @JsonSubTypes.Type(value = LessExpensive.class, name = "lessExpensive"),
        @JsonSubTypes.Type(value = Estimate.class, name = "Estimate")
})
public abstract class Question {
}
