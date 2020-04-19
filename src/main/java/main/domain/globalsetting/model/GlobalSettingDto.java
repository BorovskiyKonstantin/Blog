package main.domain.globalsetting.model;

public class GlobalSettingDto {
    private Boolean MULTIUSER_MODE;
    private Boolean POST_PREMODERATION;
    private Boolean STATISTICS_IN_PUBLIC;

    public GlobalSettingDto(boolean MULTIUSER_MODE, boolean POST_PREMODERATION, boolean STATISTICS_IN_PUBLIC) {
        this.MULTIUSER_MODE = MULTIUSER_MODE;
        this.POST_PREMODERATION = POST_PREMODERATION;
        this.STATISTICS_IN_PUBLIC = STATISTICS_IN_PUBLIC;
    }

    public boolean isEnabledMULTIUSER_MODE() {
        return MULTIUSER_MODE;
    }

    public boolean isEnabledPOST_PREMODERATION() {
        return POST_PREMODERATION;
    }

    public boolean isEnabledSTATISTICS_IN_PUBLIC() {
        return STATISTICS_IN_PUBLIC;
    }


}
