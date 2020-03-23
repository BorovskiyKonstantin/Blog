package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//Перенести все сущности в тестовый проект hibernate. Протестить и настроить всё в нем.

/**ToDo:
 *      1. Подключить Hibernate
 *      2. Создать схему в БД
 *      3. Создать сущности
 *      4. Тщательно проверить правильность типа полей
 *      + users   -   пользователи
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● is_moderator   TINYINT   NOT   NULL   -   является   ли   пользователь   модератором  (может   ли   править   глобальные   настройки   сайта   и   модерировать   посты)
 *       ● reg_time   DATETIME   NOT   NULL   -   дата   и   время   регистрации   пользователя
 *       ● name   VARCHAR(255)   NOT   NULL   -   имя   пользователя
 *       ● email   VARCHAR(255)   NOT   NULL   -   e-mail   пользователя
 *       ● password   VARCHAR(255)   NOT   NULL   -   хэш   пароля   пользователя  ● code   VARCHAR(255)   -   код   для   восстановления   пароля,   может   быть   NULL
 *       ● photo   TEXT   -   фотография   (ссылка   на   файл),   может   быть   NULL
 *       /
 *      + posts   -   посты
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● is_active   TINYINT   NOT   NULL   -   скрыта   или   активна   публикация:   0   или   1
 *       ● moderation_status   ENUM(‘NEW’,   ‘ACCEPTED’,   ‘DECLINED’)   NOT   NULL   -   статус  модерации,   по   умолчанию   значение   “NEW”.
 *       ?● moderator_id   INT   -   ID   пользователя-модератора,   принявшего   решение,   или   NULL
 *       ?● user_id   INT   NOT   NULL   -   автор   поста
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  п убликации   поста
 *       ● title   VARCHAR(255)   NOT   NULL   -   заголовок   поста
 *       ● text   TEXT   NOT   NULL   -   текст   поста
 *       ● view_count   INT   NOT   NULL   -   количество   просмотров   поста
 *       /
 *      +post_votes     -   лайки   и   дизлайки   постов
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ?● user_id   INT   NOT   NULL   -   тот,   кто   поставил   лайк   /   дизлайк
 *       ?● post_id   INT   NOT   NULL   -   пост,   которому   поставлен   лайк   /   дизлайк
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  л айка   /   дизлайка
 *       ● value   TINYINT   NOT   NULL   -   лайк   или   дизлайк:   1   или   -1
 *       /
 *      + tags   -   тэги
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● name   VARCHAR(255)   NOT   NULL
 *       /
 *      ?tag2post   -   связи   тэгов   с   постами
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ?● post_id   INT   NOT   NULL   -   пост
 *       ?● tag_id   INT   NOT   NULL   -   тэг
 *       /
 *      +post_comments   -  к омментарии   к   постам
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ?● parent_id   INT   -   комментарий,   на   который  о ставлен   этот   комментарий   (может  быть   NULL,   если   комментарий   оставлен   просто   к   посту)
 *       ?● post_id   INT   NOT   NULL   -   пост,   к   которому  н аписан   комментарий
 *       ?● user_id   INT   NOT   NULL   -   автор   комментария
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  к омментария
 *       /
 *      +captcha_codes   -    коды   капч
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  г енерации   кода   капчи
 *       ● code   TINYTEXT   NOT   NULL   -   код,   отображаемый   на   картинкке   капчи
 *       ● secret_code   TINYTEXT   NOT   NULL   -   код,   передаваемый   в   параметре
 *       /
 *      +global_settings   -   глобальные   настройки   движка
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● code   VARCHAR(255)   NOT   NULL   -   системное   имя   настройки
 *       ● name   VARCHAR(255)   NOT   NULL   -   название   настройки
 *       ● value   VARCHAR(255)   NOT   NULL   -   значение   настройки
 *
 * */

//Тип поля TINYINT лучше задать типом переменной byte или @Column(columnDefinition = "TINYINT")? Или лучше использовать оба вараинта сразу?
//Как в global_settings поставить зависимость поля "name" от поля "code". Как лучше поставить эти глобальные настройки?

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
