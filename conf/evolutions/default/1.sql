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

create table db_image (
  id                        bigint not null,
  filename                  varchar(255),
  image_id                  bigint,
  thumbnail_id              bigint,
  constraint pk_db_image primary key (id))
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

create table raw_image (
  id                        bigint not null,
  image                     blob,
  width                     integer,
  height                    integer,
  mimetype                  varchar(255),
  last_update               timestamp not null,
  constraint pk_raw_image primary key (id))
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

create sequence db_image_seq;

create sequence homenageado_seq;

create sequence picture_seq;

create sequence raw_image_seq;

create sequence tipo_homenagem_seq;

create sequence usuario_seq;

alter table conta add constraint fk_conta_usuario_1 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_conta_usuario_1 on conta (usuario_id);
alter table db_image add constraint fk_db_image_image_2 foreign key (image_id) references raw_image (id) on delete restrict on update restrict;
create index ix_db_image_image_2 on db_image (image_id);
alter table db_image add constraint fk_db_image_thumbnail_3 foreign key (thumbnail_id) references raw_image (id) on delete restrict on update restrict;
create index ix_db_image_thumbnail_3 on db_image (thumbnail_id);
alter table tipo_homenagem add constraint fk_tipo_homenagem_subTipoHomen_4 foreign key (sub_tipo_homenagem_id) references tipo_homenagem (id) on delete restrict on update restrict;
create index ix_tipo_homenagem_subTipoHomen_4 on tipo_homenagem (sub_tipo_homenagem_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists conta;

drop table if exists db_image;

drop table if exists homenageado;

drop table if exists picture;

drop table if exists raw_image;

drop table if exists tipo_homenagem;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists conta_seq;

drop sequence if exists db_image_seq;

drop sequence if exists homenageado_seq;

drop sequence if exists picture_seq;

drop sequence if exists raw_image_seq;

drop sequence if exists tipo_homenagem_seq;

drop sequence if exists usuario_seq;

