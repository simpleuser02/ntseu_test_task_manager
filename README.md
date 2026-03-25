# 📝 Task Manager API

REST API для управления задачами с поддержкой авторизации, ролей и фильтрации.

---

## 🚀 Запуск приложения

Приложение стартует на порту:

```
http://localhost:8080
```

---

## 🛠️ Технологии

* Java 21
* Spring Boot
* Spring Security (JWT)
* Spring Data JPA
* H2 Database
* Flyway
* MapStruct
* Swagger (OpenAPI)

---

## 🗄️ База данных

Используется файловая база H2:

```
jdbc:h2:file:./data/task_manager
```

Файл БД создаётся внутри проекта:

```
/data/task_manager.mv.db
```

---

## 👤 Предсозданный пользователь

В системе уже есть администратор:

```
username: admin
email: admin@example.com
password: 123456
role: ADMIN
```

> ⚠️ Пароль захэширован (BCrypt)

---

## 🔐 Аутентификация

### 📌 Регистрация

```
POST /api/v1/auth/register
```

### 📌 Логин

```
POST /api/v1/auth/login
```

Возвращает JWT токен:

```json
{
  "token": "your-jwt-token"
}
```

---

## 🔑 Использование токена

Добавь заголовок:

```
Authorization: Bearer <your-token>
```

---

## 📚 Swagger (API документация)

Доступен по адресу:

```
http://localhost:8080/swagger-ui.html
```

или:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📌 Основные эндпоинты

### Tasks

| Метод  | URL                | Описание              |
| ------ |--------------------|-----------------------|
| GET    | /api/v1/tasks      | Получить список задач |
| GET    | /api/v1/tasks/{id} | Получить задачу по id  |
| POST   | /api/v1/tasks      | Создать задачу        |
| PATCH  | /api/v1/tasks/{id} | Обновить задачу       |
| DELETE | /api/v1/tasks/{id} | Удалить задачу        |

---

## 🔎 Пагинация

```
GET /api/v1/tasks?page=0&size=10
```

---

## 🧪 Тесты

Запуск тестов:

```bash
./gradlew test
```

---

## ⚠️ Важно

* При использовании H2 file данные сохраняются между перезапусками
* Если нужно сбросить БД:

```bash
rm -rf data/
```

---


### H2 Console

```
http://localhost:8080/h2-console
```

JDBC URL:

```
jdbc:h2:file:./data/task_manager
```

---

## 📦 Сборка

```bash
./gradlew build
```

---

## ▶️ Запуск

```bash
./gradlew bootRun
```
