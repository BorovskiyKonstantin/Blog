package main.domain.globalsetting.usecase;

import main.domain.globalsetting.model.GlobalSettingDto;
import main.domain.globalsetting.port.GlobalSettingRepositoryPort;
import main.domain.globalsetting.entity.GlobalSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GlobalSettingUseCase {
    private GlobalSettingRepositoryPort globalSettingRepositoryPort;

    @Autowired
    public GlobalSettingUseCase(GlobalSettingRepositoryPort globalSettingRepositoryPort) {
        this.globalSettingRepositoryPort = globalSettingRepositoryPort;
    }

    public void editSettings(GlobalSettingDto settingDto) {
        GlobalSetting multiuserMode = globalSettingRepositoryPort.findByCode("MULTIUSER_MODE").orElseThrow();
        GlobalSetting postPremoderation = globalSettingRepositoryPort.findByCode("POST_PREMODERATION").orElseThrow();
        GlobalSetting statisticsInPublic = globalSettingRepositoryPort.findByCode("STATISTICS_IS_PUBLIC").orElseThrow();

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
        String statisticsInPublicValue = globalSettingRepositoryPort.findByCode("STATISTICS_IS_PUBLIC").orElseThrow().getValue();

        return new GlobalSettingDto(
                multiuserModeValue.equals("YES"),
                postPremoderationValue.equals("YES"),
                statisticsInPublicValue.equals("YES")
        );
    }

    public boolean isMultiuserModeEnabled(){
        return globalSettingRepositoryPort.findByCode("MULTIUSER_MODE").orElseThrow().getValue().equals("YES");
    }

    public boolean isPostPremoderationEnabled(){
        return globalSettingRepositoryPort.findByCode("POST_PREMODERATION").orElseThrow().getValue().equals("YES");
    }

    public boolean isStatisticsInPublicEnabled(){
        return globalSettingRepositoryPort.findByCode("STATISTICS_IS_PUBLIC").orElseThrow().getValue().equals("YES");
    }
}
