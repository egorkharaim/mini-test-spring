# Mini Spring Project

Два сервиса:  
- **auth-api** — отвечает за регистрацию, логин, выдачу JWT и обработку запросов.  
- **data-api** — вспомогательный сервис для трансформации текста.  

---

## 🚀 Стек
- Java 17
- Spring Boot 3
- Spring Security (JWT)
- PostgreSQL
- Docker + Docker Compose

---

## 📦 Запуск через Docker

1. Собери и запусти проект:
   ```bash
   docker compose up --build
После запуска:

auth-api доступен на: http://localhost:8080

data-api доступен на: http://localhost:8081
  API эндпоинты
Регистрация
 ```bash
curl -i -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{"email":"user@test.com","password":"secret"}'
```

Логин (получить JWT)
```
curl -i -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"user@test.com","password":"secret"}'
```
output 
```bash
{"token":"<JWT_TOKEN>"}
```
Вызов защищённого эндпоинта
```bash
curl -i -X POST http://localhost:8080/api/process \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <JWT_TOKEN>" \
-d '{"text":"hello"}'
```
output
```bash
{"result":"OLLEH"}
```
⚙️ База данных

Используется PostgreSQL.

Таблица users

Хранит зарегистрированных пользователей:

id (UUID, primary key)

email (уникальный логин)

password_hash (захэшированный пароль)

Таблица processing_log

Хранит историю вызовов API:

id (UUID, primary key)

user_id (foreign key → users.id)

input_text (входная строка)

output_text (результат трансформации)

created_at (время запроса)

