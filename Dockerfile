FROM gradle:7.3.0-jdk8

WORKDIR /hackernews-api
COPY . .
RUN gradle clean build

CMD gradle bootRun