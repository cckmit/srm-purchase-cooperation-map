FROM open-registry.going-link.com/zhenyun/javabase:8u242-b08-hotspot-0800
COPY app.jar /srm-purchase-cooperation.jar
ENTRYPOINT exec java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap $JAVA_OPTS $APM_OPTS -jar /srm-purchase-cooperation.jar