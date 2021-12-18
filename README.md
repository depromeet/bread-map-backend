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

## 유의사항

데이터 베이스를 모두 초기화 해야하거나 처음부터 셋팅할 때의 상황을 가정하여 작성했습니다.
리뷰 혹은 메뉴 등록 시, `bread_categories` 테이블에 **기획단계에 정의 해둔 12개의 카테고리 값들이 미리 저장이 되어있어야 합니다.**    

(식사빵/구움과자류/마카롱/케이크/크림빵/도넛/추억의빵/과자류/크로와상/쿠키/파이디저트/기타)

저장이 안되어있다면, 양방향 맵핑관계로 설정 된 엔티티 특성 상 오류가 발생하니 유의바랍니다! 😷

