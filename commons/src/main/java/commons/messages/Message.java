package commons.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewGameMessage.class, name = "newGame"),
        @JsonSubTypes.Type(value = GameEndedMessage.class, name = "gameEnded"),
        @JsonSubTypes.Type(value = NextQuestionMessage.class, name = "nextQuestion"),
        @JsonSubTypes.Type(value = AnswerMessage.class, name = "answer"),
})
public abstract class Message {
}
