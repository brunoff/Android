drop table Usuarios;
drop table Imovel;
drop table TipoImovel;
create table Usuarios (nome_usu TEXT NOT NULL PRIMARY KEY, senha_usu text not null);
create table Imovel (codigo_imov TEXT NOT NULL PRIMARY KEY, nome_imov text not null, endereco_imov text ,bairro_imov text ,cidade_imov text,uf_imov text,latitude_imov real,longitude_imov real);
create table TipoImovel (codigo_tpimov TEXT NOT NULL PRIMARY KEY, nome_tpimov text not null);