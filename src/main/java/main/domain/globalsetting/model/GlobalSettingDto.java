package main.domain.globalsetting.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class GlobalSettingDto {
    @JsonProperty("MULTIUSER_MODE")
    private Boolean multiuserMode;
    @JsonProperty("POST_PREMODERATION")
    private Boolean postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private Boolean statisticsInPublic;

    public GlobalSettingDto(boolean multiuserMode, boolean postPremoderation, boolean statisticsInPublic) {
        this.multiuserMode = multiuserMode;
        this.postPremoderation = postPremoderation;
        this.statisticsInPublic = statisticsInPublic;
    }

    @JsonGetter("MULTIUSER_MODE")
    public boolean isMultiuserModeEnabled() {
        return multiuserMode;
    }

    @JsonGetter("POST_PREMODERATION")
    public boolean isPostPremoderationEnabled() {
        return postPremoderation;
    }

    @JsonGetter("STATISTICS_IS_PUBLIC")
    public boolean isStatisticsInPublicEnabled() {
        return statisticsInPublic;
    }
}
