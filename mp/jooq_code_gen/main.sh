#!/bin/sh

java -classpath jooq-3.11.11.jar:jooq-meta-3.11.11.jar:jooq-codegen-3.11.11.jar:mysql-connector-java-8.0.16.jar:. org.jooq.codegen.GenerationTool main.xml
