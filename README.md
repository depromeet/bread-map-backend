# 🍞 대동빵지도

<p align="center">
  <img width="460" alt="스크린샷 2021-11-15 오후 5 26 57" src="https://user-images.githubusercontent.com/58355531/141747595-b0b15d34-6cc5-4347-b06e-058cf518f7cf.png">
</p>

<p align="center">
  빵 맛집을 한번에 모아보고, 더 나은 빵🥐 라이프를 위해 함께 만들어가는 어플리케이션 "대동빵지도" 의 백엔드 Repository 입니다.
</p>
  
<br>

## 👩🏻‍💻👨🏻‍💻 대동빵지도 Back-end

|[<img src="https://user-images.githubusercontent.com/58355531/141788110-27767a22-8a7b-49e0-a29e-ae3eae57733e.png" width="80">](https://github.com/Jane096)|[<img src="https://user-images.githubusercontent.com/46235778/144022930-6b0125e7-8574-4ce6-a3e4-a825487f3dff.png" width="80">](https://github.com/2haein)|[<img src="https://avatars.githubusercontent.com/u/70900028?v=4" width="80">](https://github.com/earth-h)|
|:---:|:---:|:---:|
|[이지은](https://github.com/Jane096)|[이해인](https://github.com/2haein)|[황지수](https://github.com/earth-h)|

<br>

## 📱 기술 스택

- Java 8
- Spring Boot 2.4
- Spring Data JPA + QueryDsl
- Gradle
- Junit5

<br>

### | 운영환경(prod) 인프라 구성
- AWS ECR
- AWS Elastic Beanstalk (ALB + EC2(docker))
- AWS RDS (postgreSQL)
- AWS S3
- Github Actions CI/CD

<br>

### | 개발환경(dev) 인프라 구성
- AWS Elastic Beanstalk (ALB + nginx + EC2(springboot))
- AWS RDS (postgreSQL)
- AWS S3
- AWS Route 53 + AWS Certificate Manager
- Github Actions CI/CD

<br>

### | 데이터베이스 구조

<img width="1196" alt="스크린샷 2021-11-14 오후 10 41 23" src="https://user-images.githubusercontent.com/58355531/141683929-672901e8-4476-46b2-b9d3-e4b9bbbc548e.png">

<br>
<br>

## 개발환경(dockerdev) 로컬에서 띄워보기
로컬환경에서 띄울 때 하기와 같은 구조로 진행했습니다.
* 로컬환경에 docker가 이미 설치되어 있다는 가정하에 작성하였으며, MAC 환경에서 테스트하였습니다.

하기와 같은 구조로 파일 생성 및 git clone이 완료되면 docker 설정 최상단 디렉토리(하기 구조라면 dev-docker)에서 하기와 같이 docker-compose를 띄워줍니다.
```bash
docker-compose up
```

<br>

**[로컬 환경 디렉토리 구조]**

```bash
dev-docker
│   docker-compose.yml
│
│   .env
│
└───app/
│   │
│   └───bread-map-backend/
│       │   해당 디렉토리 하위에 git clone을 통해 bread-map-backend 레포를 받아주세요!
│   
└───db/
    │   Dockerfile-postgres
    │   
    └───data/
    │
    └───init.d/
        └───init-user-db.sh
```

**[ .env 파일 ]**
```bash
DB_USER_ID=postgres
DB_USER_PASSWORD=postgres
APP_DB_USER=dev
APP_DB_PASS=postgres
APP_DB_NAME=dev_db
DB_BUILD_CONTEXT=./db
POSTGRES_HOME_DIR=./db
APP_BUILD_CONTEXT=./app/bread-map-backend
S3_ACCESSKEY=자신의 AWS S3 ACCESSKEY
S3_SECRETKEY=자신의 AWS S3 SECRETKEY
S3_RESION=자신의 AWS S3 RESION
S3_BUCKET=자신의 AWS S3 BUCKET 이름(이미지가 저장될 버킷 디렉토리)
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
      - ${POSTGRES_HOME_DIR}/data:/var/lib/postgresql/data"
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
* app용 dockerfile의 경우, git clone하여 bread-map-backend 레포지토리를 받으면, 레포 최상단에 Dockerfile-dev라고 있습니다.
  * 운영과 구분하여 사용하기 위해 Dockerfile-dev로 생성하였습니다.
* app에 설정된 environment는 applciation-dockerdev.yml에서 사용될 변수들입니다.
  * Dockerfile-dev에 의해 active-profile이 dockerdev로 설정되어집니다.

**[ Dockerfile-postgres ]**
```bash
FROM postgres:12.8
COPY ./init.d /docker-entrypoint-initdb.d
```
* postgresql 기본 설치만 진행할 경우에는, postgreSQL용 dockerfile 없이 docker-compose를 통해 가능합니다.
* 다만, init 시 database 생성 및 extension 생성을 위해 위와 같이 dockerfile을 추가로 구성하였습니다.

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
* app에서 사용할 user 생성 및 database 구성
* superuser로 스위칭 후 earthdistance용 extension 설치

## 유의사항

데이터 베이스를 모두 초기화 해야하거나 처음부터 셋팅할 때의 상황을 가정하여 작성했습니다.
리뷰 혹은 메뉴 등록 시, `bread_categories` 테이블에 **기획단계에 정의 해둔 12개의 카테고리 값들이 미리 저장이 되어있어야 합니다.**    

(식사빵/구움과자류/마카롱/케이크/크림빵/도넛/추억의빵/과자류/크로와상/쿠키/파이디저트/기타)

저장이 안되어있다면, 양방향 맵핑관계로 설정 된 엔티티 특성 상 오류가 발생하니 유의바랍니다! 😷

