package main.domain.globalsetting.usecase;

import main.domain.globalsetting.model.GlobalSettingDto;
import main.domain.globalsetting.port.GlobalSettingRepositoryPort;
import main.domain.globalsetting.entity.GlobalSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class GlobalSettingUseCase {
    private GlobalSettingRepositoryPort globalSettingRepositoryPort;

    private static Boolean MULTIUSER_MODE;
    private static Boolean POST_PREMODERATION;
    private static Boolean STATISTICS_IS_PUBLIC;

    @Autowired
    public GlobalSettingUseCase(GlobalSettingRepositoryPort globalSettingRepositoryPort) {
        this.globalSettingRepositoryPort = globalSettingRepositoryPort;
    }

    @PostConstruct
    private void setSettings(){
        MULTIUSER_MODE = globalSettingRepositoryPort.findByCode("MULTIUSER_MODE").orElseThrow().getValue().equals("YES");
        POST_PREMODERATION = globalSettingRepositoryPort.findByCode("POST_PREMODERATION").orElseThrow().getValue().equals("YES");
        STATISTICS_IS_PUBLIC = globalSettingRepositoryPort.findByCode("STATISTICS_IS_PUBLIC").orElseThrow().getValue().equals("YES");
    }

    public void editSettings(GlobalSettingDto settingDto) {
        GlobalSetting multiuserMode = globalSettingRepositoryPort.findByCode("MULTIUSER_MODE").orElseThrow();
        GlobalSetting postPremoderation = globalSettingRepositoryPort.findByCode("POST_PREMODERATION").orElseThrow();
        GlobalSetting statisticsInPublic = globalSettingRepositoryPort.findByCode("STATISTICS_IS_PUBLIC").orElseThrow();

        // Изменение настроек в БД
        multiuserMode.setValue(settingDto.isMultiuserModeEnabled() ? "YES" : "NO");
        postPremoderation.setValue(settingDto.isPostPremoderationEnabled() ? "YES" : "NO");
        statisticsInPublic.setValue(settingDto.isStatisticsInPublicEnabled() ? "YES" : "NO");

        // Изменение настроек в статических переменных
        MULTIUSER_MODE = settingDto.isMultiuserModeEnabled();
        POST_PREMODERATION = settingDto.isPostPremoderationEnabled();
        STATISTICS_IS_PUBLIC = settingDto.isStatisticsInPublicEnabled();
    }

    public GlobalSettingDto getSettings() {
        return new GlobalSettingDto(MULTIUSER_MODE, POST_PREMODERATION, STATISTICS_IS_PUBLIC);
    }

    public boolean isMultiuserModeEnabled(){
        return MULTIUSER_MODE;
    }

    public boolean isPostPremoderationEnabled(){
        return POST_PREMODERATION;
    }

    public boolean isStatisticsInPublicEnabled(){
        return STATISTICS_IS_PUBLIC;
    }
}
