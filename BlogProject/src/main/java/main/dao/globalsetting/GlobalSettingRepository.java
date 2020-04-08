package main.dao.globalsetting;

import main.domain.globalsetting.entity.GlobalSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Для операций (поиск, замена и т.п.) с БД.
//Помечен как @Autowired в RepositoryPortImpl, куда подгружается как зависимость с помощью Spring
@Repository
public interface GlobalSettingRepository extends CrudRepository<GlobalSetting, Integer> {
    GlobalSetting findByCode(String code);
}
