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
        GlobalSetting multiuserMode = globalSettingRepositoryPort.findByCode("MULTIUSER_MODE");
        GlobalSetting postPremoderation = globalSettingRepositoryPort.findByCode("POST_PREMODERATION");
        GlobalSetting statisticsInPublic = globalSettingRepositoryPort.findByCode("STATISTICS_IN_PUBLIC");

        multiuserMode.setValue(settingDto.isMULTIUSER_MODE() ? "YES" : "NO");
        postPremoderation.setValue(settingDto.isPOST_PREMODERATION() ? "YES" : "NO");
        statisticsInPublic.setValue(settingDto.isSTATISTICS_IN_PUBLIC() ? "YES" : "NO");

        globalSettingRepositoryPort.save(multiuserMode);
        globalSettingRepositoryPort.save(postPremoderation);
        globalSettingRepositoryPort.save(statisticsInPublic);
    }

    public GlobalSettingDto getSettings() {
        String multiuserModeValue = globalSettingRepositoryPort.findByCode("MULTIUSER_MODE").getValue();
        String postPremoderationValue = globalSettingRepositoryPort.findByCode("POST_PREMODERATION").getValue();
        String statisticsInPublicValue = globalSettingRepositoryPort.findByCode("STATISTICS_IN_PUBLIC").getValue();

        return new GlobalSettingDto(
                multiuserModeValue.equals("YES"),
                postPremoderationValue.equals("YES"),
                statisticsInPublicValue.equals("YES")
        );
    }
}
