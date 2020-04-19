package main.domain.globalsetting.port;

import main.domain.globalsetting.entity.GlobalSetting;

import java.util.Optional;

//Через этот интерфейс UseCase (где он помечен @Autowired) из слоя domain обращается к репозиторию в слое dao.
//Spring находит компонент c его реализацией в слое dao и внедряет зависимость.
//Здесь описываются все нужные для UseCase от Repository методы
public interface GlobalSettingRepositoryPort {
    Optional<GlobalSetting> findByCode(String code);

    void save(GlobalSetting globalSetting);
}
