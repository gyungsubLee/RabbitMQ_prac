# RabbitMQ를 이용한 비동기 아키텍처 

> Reference <br/>
> [인프런 - RabbitMQ를 이용한 비동기 아키텍처 한방에 해결하기](https://www.inflearn.com/course/rabbitmq-%EB%B9%84%EB%8F%99%EA%B8%B0-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%ED%95%9C%EB%B0%A9%EC%97%90/dashboard) <br/>
> [RabbitMQ Tutorials](https://www.rabbitmq.com/tutorials) <br/>


### [노션 정리 페이지]() 

<br/>

### docker compose 실행 (Spring, RabbitMQ)

```bash
# 햅 시작 = gradle build + compose 실행
./start.sh
```

```bash
# 재실행 = compose 종료 + gradle build + compose 실행
./restart.sh
```


```bash
# build 없이 실행
docker compose up -d
```

**compose 종료**

```bash
docker compose down
```

<br/>
