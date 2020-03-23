insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');


insert into estado (id, nome) values (1, "Rio de Janeiro");
insert into estado (id, nome) values (2, "São Paulo");

insert into cidade (nome, estado_id) values ("Ribeirão Preto", 1);
insert into cidade (nome, estado_id) values ("São Caetano", 1);
insert into cidade (nome, estado_id) values ("Niterói", 2);



insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ("Thai Gourmet", 10, 1, utc_timestamp, utc_timestamp,1, '38400-999', 'Rua João Pinheiro', '1000', 'Bairro do Pinheiro');
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ("Thai Delivery", 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ("Tuk Tuk Comida Indiana", 15, 2, utc_timestamp, utc_timestamp);


insert into forma_pagamento (descricao) values ("Débito");
insert into forma_pagamento (descricao) values ("Crédito");
insert into forma_pagamento (descricao) values ("Cheque");


insert into permissao (nome, descricao) values ("Modificar", "Habilita usuário a fazer modificação.");
insert into permissao (nome, descricao) values ("Visualizar", "Habilita usuário a visualizar.");


insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,2), (2,3), (3,1)

insert into produto(nome, descricao, preco, ativo, restaurante_id) values ('Açaí', 'Açaí 500ml', 8.00, true, 1)

insert into usuario(email, senha, data_cadastro) values ('joao@v.com', '@jekf4', utc_timestamp)
insert into usuario(email, senha, data_cadastro) values ('lua@frds.com', '@fre968', utc_timestamp)
insert into usuario(email, senha, data_cadastro) values ('bob@esponja.com', '@e3er35', utc_timestamp)

insert into grupo(nome) values ('Grupo primário'), ('Grupo secundário')

insert into grupo_permissao(grupo_id, permissao_id) values (1,1), (1,2)

insert into usuario_grupo(usuario_id, grupo_id) values (1, 1), (2,1), (3,2)



