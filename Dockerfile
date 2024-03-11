FROM ghcr.io/graalvm/native-image-community:21 as build

WORKDIR /app
COPY . /app
RUN POSTGRES_HOST=r2dbc:postgresql://localhost:5432/rinhav2 ./mvnw -Pnative native:compile

FROM alpine

COPY --from=build /app/target/rinhav2 /rinhav2
RUN apk add libc6-compat
EXPOSE 8080

CMD ["./rinhav2"]