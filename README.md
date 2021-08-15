# epx-api [![Build Status](https://travis-ci.com/ranraj/epx-api.svg?branch=master)](https://travis-ci.com/ranraj/epx-api)
epx api 

Environment variables:
```

export MONGO_USER=<mongodb_username>
export MONGO_PASSWORD=<mongodb_password>
export MONGO_CLUSTER=<mongodb_server_with_port>
export TEXT_SIMILARITY_SERVICE_URL=https://<epx_text_service_url>/text/similarity
```

Note : Provide port if it is local mongodb

Building application 
```
mvn install 
```
Run Application
```
cd epx-api-service/epx-service
mvn spring-boot:run
```

Docker : 

Build
```
docker image build -t epx-api .
```
Run
```
docker run -e TEXT_SIMILARITY_SERVICE_URL=${TEXT_SIMILARITY_SERVICE_URL} -e TEXT_SIMILARITY_SERVICE_TOKEN=${TEXT_SIMILARITY_SERVICE_TOKEN} -e MONGO_USER=${MONGO_USER} -e MONGO_PASSWORD=${MONGO_PASSWORD} -e MONGO_CLUSTER=${MONGO_CLUSTER} -p 8080:8080 epx-api:latest

```
Test
```
https://localhost:8080/actuator/health
```
CI/CD : Travis & Heroku

Heroku logs

```
cd epx-api-service/epx-service
heroku logs --tail
```      
