package main.dao.globalsetting;

import main.domain.globalsetting.entity.GlobalSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Для операций (поиск, замена и т.п.) с БД.
//Помечен как @Autowired в RepositoryPortImpl, куда подгружается как зависимость с помощью Spring
@Repository
public interface GlobalSettingRepository extends CrudRepository<GlobalSetting, Integer> {
    Optional<GlobalSetting> findByCode(String code);
}
