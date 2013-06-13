# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cidade (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_cidade primary key (id))
;

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

create table estado (
  id                        bigint not null,
  nome                      varchar(255),
  abreviacao                varchar(255),
  constraint pk_estado primary key (id))
;

create table homenagem (
  id                        bigint not null,
  homenagem_pai_id          bigint,
  numero_registro           varchar(255),
  homenageado               integer,
  descricao                 varchar(255),
  tipo_homenagem_id         bigint,
  resumo                    varchar(255),
  local                     varchar(255),
  localizacao               varchar(255),
  precedencia               varchar(255),
  objeto                    varchar(255),
  prateleira                varchar(255),
  material                  varchar(255),
  altura                    double,
  largura                   double,
  comprimento               double,
  profundidade              double,
  quem_entregou             varchar(255),
  data_recebimento          timestamp,
  constraint ck_homenagem_homenageado check (homenageado in (0,1)),
  constraint pk_homenagem primary key (id))
;

create table pais (
  id                        bigint not null,
  nome                      varchar(255),
  abreviacao                varchar(255),
  constraint pk_pais primary key (id))
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
  name                      varchar(255),
  parent_id                 bigint,
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

create sequence cidade_seq;

create sequence conta_seq;

create sequence db_image_seq;

create sequence estado_seq;

create sequence homenagem_seq;

create sequence pais_seq;

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
alter table homenagem add constraint fk_homenagem_homenagemPai_4 foreign key (homenagem_pai_id) references homenagem (id) on delete restrict on update restrict;
create index ix_homenagem_homenagemPai_4 on homenagem (homenagem_pai_id);
alter table homenagem add constraint fk_homenagem_tipoHomenagem_5 foreign key (tipo_homenagem_id) references tipo_homenagem (id) on delete restrict on update restrict;
create index ix_homenagem_tipoHomenagem_5 on homenagem (tipo_homenagem_id);
alter table tipo_homenagem add constraint fk_tipo_homenagem_parent_6 foreign key (parent_id) references tipo_homenagem (id) on delete restrict on update restrict;
create index ix_tipo_homenagem_parent_6 on tipo_homenagem (parent_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists cidade;

drop table if exists conta;

drop table if exists db_image;

drop table if exists estado;

drop table if exists homenagem;

drop table if exists pais;

drop table if exists picture;

drop table if exists raw_image;

drop table if exists tipo_homenagem;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists cidade_seq;

drop sequence if exists conta_seq;

drop sequence if exists db_image_seq;

drop sequence if exists estado_seq;

drop sequence if exists homenagem_seq;

drop sequence if exists pais_seq;

drop sequence if exists picture_seq;

drop sequence if exists raw_image_seq;

drop sequence if exists tipo_homenagem_seq;

drop sequence if exists usuario_seq;

