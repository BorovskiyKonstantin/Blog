package main.domain.postvote;

import main.domain.postvote.entity.PostVoteType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PostVoteConverter implements AttributeConverter<PostVoteType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PostVoteType attribute) {
        if (attribute == null)
            return null;
        switch (attribute) {
            case LIKE:
                return -1;
            case DISLIKE:
                return 1;
            default:
                throw new IllegalArgumentException(attribute + " not supported");
        }
    }

    @Override
    public PostVoteType convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;
        switch (dbData){
            case -1:
                return PostVoteType.DISLIKE;
            case 1:
                return PostVoteType.LIKE;
            default:
                throw new IllegalArgumentException(dbData + " not supported");
        }
    }
}
