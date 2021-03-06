# ๐ ๋๋๋นต์ง๋

<p align="center">
  <img width="460" alt="แแณแแณแแตแซแแฃแบ 2021-11-15 แแฉแแฎ 5 26 57" src="https://user-images.githubusercontent.com/58355531/141747595-b0b15d34-6cc5-4347-b06e-058cf518f7cf.png">
</p>

<p align="center">
  ๋นต ๋ง์ง์ ํ๋ฒ์ ๋ชจ์๋ณด๊ณ , ๋ ๋์ ๋นต๐ฅ ๋ผ์ดํ๋ฅผ ์ํด ํจ๊ป ๋ง๋ค์ด๊ฐ๋ ์ดํ๋ฆฌ์ผ์ด์ "๋๋๋นต์ง๋" ์ ๋ฐฑ์๋ Repository ์๋๋ค.
</p>
  
<br>

## ๐ฉ๐ปโ๐ป๐จ๐ปโ๐ป ๋๋๋นต์ง๋ Back-end

|[<img src="https://user-images.githubusercontent.com/58355531/141788110-27767a22-8a7b-49e0-a29e-ae3eae57733e.png" width="80">](https://github.com/Jane096)|[<img src="https://user-images.githubusercontent.com/46235778/144022930-6b0125e7-8574-4ce6-a3e4-a825487f3dff.png" width="80">](https://github.com/2haein)|[<img src="https://avatars.githubusercontent.com/u/70900028?v=4" width="80">](https://github.com/earth-h)|
|:---:|:---:|:---:|
|[์ด์ง์](https://github.com/Jane096)|[์ดํด์ธ](https://github.com/2haein)|[ํฉ์ง์](https://github.com/earth-h)|

<br>

## ๐ฑ ๊ธฐ์  ์คํ

- Java 8
- Spring Boot 2.4
- Spring Data JPA + QueryDsl
- Gradle
- Junit5

<br>

### | ์ด์ํ๊ฒฝ(prod) ์ธํ๋ผ ๊ตฌ์ฑ
- AWS ECR
- AWS Elastic Beanstalk (ALB + EC2(docker))
- AWS RDS (postgreSQL)
- AWS S3
- Github Actions CI/CD

<br>

### | ๊ฐ๋ฐํ๊ฒฝ(dev) ์ธํ๋ผ ๊ตฌ์ฑ
- AWS Elastic Beanstalk (ALB + nginx + EC2(springboot))
- AWS RDS (postgreSQL)
- AWS S3
- AWS Route 53 + AWS Certificate Manager
- Github Actions CI/CD

<br>

### | ๋ฐ์ดํฐ๋ฒ ์ด์ค ๊ตฌ์กฐ

<img width="1196" alt="แแณแแณแแตแซแแฃแบ 2021-11-14 แแฉแแฎ 10 41 23" src="https://user-images.githubusercontent.com/58355531/141683929-672901e8-4476-46b2-b9d3-e4b9bbbc548e.png">

<br>
<br>

## ๊ฐ๋ฐํ๊ฒฝ(dockerdev) ๋ก์ปฌ์์ ๋์๋ณด๊ธฐ
๋ก์ปฌํ๊ฒฝ์์ ๋์ธ ๋ ํ๊ธฐ์ ๊ฐ์ ๊ตฌ์กฐ๋ก ์งํํ์ต๋๋ค.
* ๋ก์ปฌํ๊ฒฝ์ docker๊ฐ ์ด๋ฏธ ์ค์น๋์ด ์๋ค๋ ๊ฐ์ ํ์ ์์ฑํ์์ผ๋ฉฐ, MAC ํ๊ฒฝ(Intel/Apple M1)์์ ํ์คํธํ์์ต๋๋ค.

ํ๊ธฐ์ ๊ฐ์ ๊ตฌ์กฐ๋ก ํ์ผ ์์ฑ ๋ฐ git clone์ด ์๋ฃ๋๋ฉด Gradle bootJar ๋ช๋ น์ด๋ก Jar ํ์ผ ์์ฑ ํ,
docker ์ค์  ์ต์๋จ ๋๋ ํ ๋ฆฌ(ํ๊ธฐ ๊ตฌ์กฐ๋ผ๋ฉด dev-docker)์์ ํ๊ธฐ์ ๊ฐ์ด docker-compose๋ฅผ ๋์์ค๋๋ค.

**Intel Mac ๊ธฐ์ค**
```bash
docker compose up -d --build
```

**Apple M1 ๋ชจ๋ธ์ ๊ฒฝ์ฐ๋ง ์๋์ ๊ฐ์ ๋ช๋ น์ด๋ก ์คํ์์ผ์ผ ํฉ๋๋ค.**
```bash
ARCH=arm64 docker-compose -f docker-compose.yml up -d
```

<br>

**[๋ก์ปฌ ํ๊ฒฝ ๋๋ ํ ๋ฆฌ ๊ตฌ์กฐ]**

```bash
dev-docker
โ   docker-compose.yml
โ
โ   .env
โ
โโโโapp/
โ   โ
โ   โโโโbread-map-backend/
โ       โ   ํด๋น ๋๋ ํ ๋ฆฌ ํ์์ git clone์ ํตํด bread-map-backend ๋ ํฌ๋ฅผ ๋ฐ์์ฃผ์ธ์!
โ   
โโโโdb/
    โ   Dockerfile-postgres
    โ   
    โโโโdata/
    โ
    โโโโinit.d/
        โโโโinit-user-db.sh
```

**[ .env ํ์ผ ]**
```bash
DB_USER_ID=postgres
DB_USER_PASSWORD=postgres
APP_DB_USER=dev
APP_DB_PASS=postgres
APP_DB_NAME=dev_db
DB_BUILD_CONTEXT=./db
POSTGRES_HOME_DIR=./db
APP_BUILD_CONTEXT=./app/bread-map-backend
S3_ACCESSKEY=์์ ์ AWS S3 ACCESSKEY
S3_SECRETKEY=์์ ์ AWS S3 SECRETKEY
S3_RESION=์์ ์ AWS S3 RESION
S3_BUCKET=์์ ์ AWS S3 BUCKET ์ด๋ฆ(์ด๋ฏธ์ง๊ฐ ์ ์ฅ๋  ๋ฒํท ๋๋ ํ ๋ฆฌ)
```

**[ docker-compose.yml ]**
```yml
version: "3"
services:
  db:
    container_name: postgres
    restart: always
    build:
      context: "${DB_BUILD_CONTEXT}"
      dockerfile: Dockerfile-postgres
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: "${DB_USER_ID}"
      POSTGRES_PASSWORD: "${DB_USER_PASSWORD}"
      APP_DB_NAME: "${APP_DB_NAME}"
      APP_DB_USER: "${APP_DB_USER}"
      APP_DB_PASS: "${APP_DB_PASS}"
    volumes:
      - ${POSTGRES_HOME_DIR}/data:/var/lib/postgresql/data
  app:
    container_name: daedong-bread-backend
    build:
      context: "${APP_BUILD_CONTEXT}"
      dockerfile: Dockerfile-dev
    ports:
      - "8080:8080"
    environment:
      S3_ACCESSKEY: "${S3_ACCESSKEY}"
      S3_SECRETKEY: "${S3_SECRETKEY}"
      S3_RESION: "${S3_RESION}"
      S3_BUCKET: "${S3_BUCKET}"
      DB_HOSTNAME: db
      DB_PORT: 5432
      DB_DBNAME: "${APP_DB_NAME}"
      DB_USERNAME: "${APP_DB_USER}"
      DB_PASSWORD: "${APP_DB_PASS}"
    depends_on:
      - db
```
* app์ฉ dockerfile์ ๊ฒฝ์ฐ, git cloneํ์ฌ bread-map-backend ๋ ํฌ์งํ ๋ฆฌ๋ฅผ ๋ฐ์ผ๋ฉด, ๋ ํฌ ์ต์๋จ์ Dockerfile-dev๋ผ๊ณ  ์์ต๋๋ค.
  * ์ด์๊ณผ ๊ตฌ๋ถํ์ฌ ์ฌ์ฉํ๊ธฐ ์ํด Dockerfile-dev๋ก ์์ฑํ์์ต๋๋ค.
* app์ ์ค์ ๋ environment๋ applciation-dockerdev.yml์์ ์ฌ์ฉ๋  ๋ณ์๋ค์๋๋ค.
  * Dockerfile-dev์ ์ํด active-profile์ด dockerdev๋ก ์ค์ ๋์ด์ง๋๋ค.

**[ Dockerfile-postgres ]**

```bash
FROM postgres:12.8
COPY ./init.d /docker-entrypoint-initdb.d
```

**Apple M1 ๋ชจ๋ธ์ ๊ฒฝ์ฐ๋ง arm64์ ๋ง๋ ์ด๋ฏธ์ง๋ก pull ๋๋๋ก ํฉ๋๋ค.**
```bash
FROM arm64v8/postgres:12.8
COPY ./init.d /docker-entrypoint-initdb.d
```

* postgresql ๊ธฐ๋ณธ ์ค์น๋ง ์งํํ  ๊ฒฝ์ฐ์๋, postgreSQL์ฉ dockerfile ์์ด docker-compose๋ฅผ ํตํด ๊ฐ๋ฅํฉ๋๋ค.
* ๋ค๋ง, init ์ database ์์ฑ ๋ฐ extension ์์ฑ์ ์ํด ์์ ๊ฐ์ด dockerfile์ ์ถ๊ฐ๋ก ๊ตฌ์ฑํ์์ต๋๋ค.

**[ init-user-db.sh ]**
```bash
#!/bin/bash
set -e

echo "START INIT-USER-DB";

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';
  CREATE DATABASE $APP_DB_NAME;
  GRANT ALL PRIVILEGES ON DATABASE $APP_DB_NAME TO $APP_DB_USER;
  \connect $APP_DB_NAME $POSTGRES_USER
  BEGIN;
    CREATE EXTENSION CUBE;
    CREATE EXTENSION EARTHDISTANCE;
  COMMIT;
EOSQL
```
* app์์ ์ฌ์ฉํ  user ์์ฑ ๋ฐ database ๊ตฌ์ฑ
* superuser๋ก ์ค์์นญ ํ earthdistance์ฉ extension ์ค์น

## ์ ์์ฌํญ

๋ฐ์ดํฐ ๋ฒ ์ด์ค๋ฅผ ๋ชจ๋ ์ด๊ธฐํ ํด์ผํ๊ฑฐ๋ ์ฒ์๋ถํฐ ์ํํ  ๋์ ์ํฉ์ ๊ฐ์ ํ์ฌ ์์ฑํ์ต๋๋ค.
๋ฆฌ๋ทฐ ํน์ ๋ฉ๋ด ๋ฑ๋ก ์, `bread_categories` ํ์ด๋ธ์ **๊ธฐํ๋จ๊ณ์ ์ ์ ํด๋ 12๊ฐ์ ์นดํ๊ณ ๋ฆฌ ๊ฐ๋ค์ด ๋ฏธ๋ฆฌ ์ ์ฅ์ด ๋์ด์์ด์ผ ํฉ๋๋ค.**    

(์์ฌ๋นต/๊ตฌ์๊ณผ์๋ฅ/๋ง์นด๋กฑ/์ผ์ดํฌ/ํฌ๋ฆผ๋นต/๋๋/์ถ์ต์๋นต/๊ณผ์๋ฅ/ํฌ๋ก์์/์ฟ ํค/ํ์ด๋์ ํธ/๊ธฐํ)

์ ์ฅ์ด ์๋์ด์๋ค๋ฉด, ์๋ฐฉํฅ ๋งตํ๊ด๊ณ๋ก ์ค์  ๋ ์ํฐํฐ ํน์ฑ ์ ์ค๋ฅ๊ฐ ๋ฐ์ํ๋ ์ ์๋ฐ๋๋๋ค! ๐ท

