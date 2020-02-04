# async-translate
Demo project for async CompletableFuture

Перевод по отдельному слову. Одновременных запросов к сервисам перевода не более 5. Проект состоит из трех модулей (Сервис перевода и интерфейсы к нему — web и telegram-bot). Написаны только базовые интеграционные тесты в модуле web. Реализован только перевод через Яндекс, для Гугла только заглушка сервиса. 

Реализована асинхронная работа с сервисом перевода. Задание на перевод создается через post /translate/async — возвращает только id задания и ссылка (в header в поле locatio) по которой можно найти результат. Затем результат перевода находится через get /translate/${id}.

### BUILD
**./mvnw clean package** (Linux) или **mvnw.cmd clean package** (Windows)

### RUN APPs
Before runningThe following environment variables must be set:
* TELEGRAM_BOT_API_KEY
* YANDEX_API_KEY

#### RUN WEB APP

java -jar web/target/async-translate-web-1.0-SNAPSHOT.jar

#### RUN TELEGRM BOT APP

java -jar telegram-bot/target/async-translate-telegram-bot-1.0-SNAPSHOT.jar

Note: telegram-bot app work local only through vpn or proxy.
