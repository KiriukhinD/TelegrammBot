--liquibase formatted sql
--changeset jrembo:1
alter table {
    String name;
}