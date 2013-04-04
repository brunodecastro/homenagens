# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table conta (
  id                        bigint not null,
  nome                      varchar(255),
  saldo                     double,
  usuario_id                bigint,
  constraint pk_conta primary key (id))
;

create table usuario (
  id                        bigint not null,
  email                     varchar(255),
  nome                      varchar(255),
  senha                     varchar(255),
  constraint pk_usuario primary key (id))
;

create sequence conta_seq;

create sequence usuario_seq;

alter table conta add constraint fk_conta_usuario_1 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_conta_usuario_1 on conta (usuario_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists conta;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists conta_seq;

drop sequence if exists usuario_seq;

