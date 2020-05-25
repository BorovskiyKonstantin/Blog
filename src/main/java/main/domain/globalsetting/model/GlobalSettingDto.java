package main.domain.globalsetting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalSettingDto {
    @JsonProperty(value = "MULTIUSER_MODE")
    private Boolean multiuserMode;
    @JsonProperty(value = "POST_PREMODERATION")
    private Boolean POST_PREMODERATION;
    @JsonProperty(value = "STATISTICS_IN_PUBLIC")
    private Boolean STATISTICS_IN_PUBLIC;

    public GlobalSettingDto(boolean multiuserMode, boolean POST_PREMODERATION, boolean STATISTICS_IN_PUBLIC) {
        this.multiuserMode = multiuserMode;
        this.POST_PREMODERATION = POST_PREMODERATION;
        this.STATISTICS_IN_PUBLIC = STATISTICS_IN_PUBLIC;
    }

    public boolean isEnabledMultiuserMode() {
        return multiuserMode;
    }

    public boolean isEnabledPostPremoderation() {
        return POST_PREMODERATION;
    }

    public boolean isEnabledStatisticsInPublic() {
        return STATISTICS_IN_PUBLIC;
    }


}
