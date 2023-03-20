FROM postgres:9.4

ADD  schema.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB university