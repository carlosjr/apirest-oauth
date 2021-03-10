FROM openjdk:14-jdk
ADD ./build/libs/foodsolution-cadastro-1.0.jar /foodsolution-cadastro.jar
ADD ./application_template.properties /application.properties
ADD ./ci/run.sh.template /run.sh
RUN chmod +x /run.sh
CMD ["sh","/run.sh"]
