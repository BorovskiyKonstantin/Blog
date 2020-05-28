package main.domain.globalsetting.usecase;

import main.domain.globalsetting.model.GlobalSettingDto;
import main.domain.globalsetting.port.GlobalSettingRepositoryPort;
import main.domain.globalsetting.entity.GlobalSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalSettingUseCase {
    @Autowired
    GlobalSettingRepositoryPort globalSettingRepositoryPort;

    public void editSettings(GlobalSettingDto settingDto) {
        GlobalSetting multiuserMode = globalSettingRepositoryPort.findByCode("MULTIUSER_MODE").orElseThrow();
        GlobalSetting postPremoderation = globalSettingRepositoryPort.findByCode("POST_PREMODERATION").orElseThrow();
        GlobalSetting statisticsInPublic = globalSettingRepositoryPort.findByCode("STATISTICS_IN_PUBLIC").orElseThrow();

        multiuserMode.setValue(settingDto.isMultiuserModeEnabled() ? "YES" : "NO");
        postPremoderation.setValue(settingDto.isPostPremoderationEnabled() ? "YES" : "NO");
        statisticsInPublic.setValue(settingDto.isStatisticsInPublicEnabled() ? "YES" : "NO");

        globalSettingRepositoryPort.save(multiuserMode);
        globalSettingRepositoryPort.save(postPremoderation);
        globalSettingRepositoryPort.save(statisticsInPublic);
    }

    public GlobalSettingDto getSettings() {
        String multiuserModeValue = globalSettingRepositoryPort.findByCode("MULTIUSER_MODE").orElseThrow().getValue();
        String postPremoderationValue = globalSettingRepositoryPort.findByCode("POST_PREMODERATION").orElseThrow().getValue();
        String statisticsInPublicValue = globalSettingRepositoryPort.findByCode("STATISTICS_IN_PUBLIC").orElseThrow().getValue();

        return new GlobalSettingDto(
                multiuserModeValue.equals("YES"),
                postPremoderationValue.equals("YES"),
                statisticsInPublicValue.equals("YES")
        );
    }
}
