# Release Image
FROM adoptopenjdk:15.0.2_7-jre-hotspot-focal AS release

WORKDIR build

COPY . .

COPY --from=build /build/target/*.jar /app.jar

COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]
