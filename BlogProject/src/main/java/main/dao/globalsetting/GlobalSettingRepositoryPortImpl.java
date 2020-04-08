package main.dao.globalsetting;

import main.domain.globalsetting.port.GlobalSettingRepositoryPort;
import main.domain.globalsetting.entity.GlobalSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Реализация RepositoryPort в слое dao. Внедряется как компонет в слой domain UseCase (где @Autowired RepositoryPort)
//Осуществляет связь dao-domain (Repository-UseCase)
@Component
public class GlobalSettingRepositoryPortImpl implements GlobalSettingRepositoryPort {

    @Autowired
    private GlobalSettingRepository globalSettingRepository;

    @Override
    public GlobalSetting findByCode(String code) {
        return globalSettingRepository.findByCode(code);
    }

    @Override
    public void save(GlobalSetting globalSetting) {
        globalSettingRepository.save(globalSetting);
    }

}
