# [Stage 1] 빌드 단계
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# 1. Gradle 래퍼 및 설정 파일 복사 (캐시 효율화)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 2. 멀티 모듈 소스 복사 (common, semicolon 폴더를 통째로 복사)
COPY common common
COPY semicolon semicolon

# 3. 빌드 실행
# :semicolon:bootJar 명령어로 메인 애플리케이션 모듈만 실행 가능한 JAR로 만듭니다.
# (common 모듈은 의존성에 의해 알아서 같이 빌드됩니다)
RUN chmod +x ./gradlew
RUN ./gradlew clean :semicolon:bootJar -x test

# [Stage 2] 실행 단계
FROM eclipse-temurin:25-jdk

WORKDIR /app

# 4. 빌드된 JAR 파일 복사
# 위치 주의: /app/semicolon/build/libs/ 안에 생성됩니다.
COPY --from=builder /app/semicolon/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]