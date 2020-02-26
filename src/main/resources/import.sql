insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ("Thai Gourmet", 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ("Thai Delivery", 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ("Tuk Tuk Comida Indiana", 15, 2);


insert into forma_pagamento (descricao) values ("Débito");
insert into forma_pagamento (descricao) values ("Crédito");
insert into forma_pagamento (descricao) values ("Cheque");


insert into permissao (nome, descricao) values ("Modificar", "Habilita usuário a fazer modificação.");
insert into permissao (nome, descricao) values ("Visualizar", "Habilita usuário a visualizar.");


insert into estado (id, nome) values (1, "Rio de Janeiro");
insert into estado (id, nome) values (2, "São Paulo");

insert into cidade (nome, estado_id) values ("Ribeirão Preto", 1);
insert into cidade (nome, estado_id) values ("São Caetano", 1);
insert into cidade (nome, estado_id) values ("Niterói", 2);


