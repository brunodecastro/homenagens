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

create table homenageado (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_homenageado primary key (id))
;

create table picture (
  id                        bigint not null,
  url                       varchar(255),
  constraint pk_picture primary key (id))
;

create table tipo_homenagem (
  id                        bigint not null,
  descricao                 varchar(255),
  sub_tipo_homenagem_id     bigint,
  constraint pk_tipo_homenagem primary key (id))
;

create table usuario (
  id                        bigint not null,
  email                     varchar(255),
  nome                      varchar(255),
  senha                     varchar(255),
  tipo_usuario              integer,
  constraint ck_usuario_tipo_usuario check (tipo_usuario in (0,1)),
  constraint pk_usuario primary key (id))
;

create sequence conta_seq;

create sequence homenageado_seq;

create sequence picture_seq;

create sequence tipo_homenagem_seq;

create sequence usuario_seq;

alter table conta add constraint fk_conta_usuario_1 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_conta_usuario_1 on conta (usuario_id);
alter table tipo_homenagem add constraint fk_tipo_homenagem_subTipoHomen_2 foreign key (sub_tipo_homenagem_id) references tipo_homenagem (id) on delete restrict on update restrict;
create index ix_tipo_homenagem_subTipoHomen_2 on tipo_homenagem (sub_tipo_homenagem_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists conta;

drop table if exists homenageado;

drop table if exists picture;

drop table if exists tipo_homenagem;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists conta_seq;

drop sequence if exists homenageado_seq;

drop sequence if exists picture_seq;

drop sequence if exists tipo_homenagem_seq;

drop sequence if exists usuario_seq;

