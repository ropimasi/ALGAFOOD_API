INSERT INTO cozinha (id, nome) VALUES (1, 'Francesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Japonesa');
INSERT INTO cozinha (id, nome) VALUES (3, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (4, 'Indiana');
INSERT INTO cozinha (id, nome) VALUES (5, 'Brasileira');
INSERT INTO cozinha (id, nome) VALUES (6, 'Russa');

INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Gourmet Em Casa', 9.5, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Bistrovisk', 5.0, 6);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Comidinhas', 7.90, 5);

INSERT INTO forma_pagamento (descricao) VALUES ('DINHEIRO')
INSERT INTO forma_pagamento (descricao) VALUES ('CARTAO DEBITO')

INSERT INTO permissao (nome, descricao) VALUES ('Admin', 'Acesso completo para administrar, manutenir, alimentar e oprerar o sistema.')
INSERT INTO permissao (nome, descricao) VALUES ('Gestor', 'Acesso estratégico para gerir o negócio, alimentar e oprerar o sistema.')

INSERT INTO estado (id, nome) VALUES (1, 'SP')
INSERT INTO estado (id, nome) VALUES (2, 'MG')
INSERT INTO estado (id, nome) VALUES (3, 'PR')

INSERT INTO cidade (nome, estado_id) VALUES ('São Paulo', 1)
INSERT INTO cidade (nome, estado_id) VALUES ('S J Rio Preto', 1)
INSERT INTO cidade (nome, estado_id) VALUES ('Belo Horizonte',2)
INSERT INTO cidade (nome, estado_id) VALUES ('Ubertândia',2)
INSERT INTO cidade (nome, estado_id) VALUES ('Curitiba',3)
INSERT INTO cidade (nome, estado_id) VALUES ('Maringá',3)


