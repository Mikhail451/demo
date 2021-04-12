create table user_role(
user_id int8 not null,
roles varchar(255));

create table usr(
id int8 not null auto_increment,
    activ boolean not null,
    activ_code varchar(255),
    email varchar(255),
    password varchar(255),
    username varchar(255),
primary key (id));

create table hero
(id int8  not null auto_increment ,
abilities varchar(255),
filename varchar(255),
hero_name varchar(255),
ranking varchar(255),
user_id int8, primary key (id));


alter table hero add constraint FK9a3mgb007s7x3elly1lvo9urs foreign key (user_id) references usr (id);
alter table user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr (id)