-- DIREITOS, LICENSA E ISENÇÃO DE RESPONSABILIDADE:
-- Este arquivo é parte integrante, indivisível, inseparável de um projeto particular, o qual tem
-- seu uso expressamente exclusivo à seu autor, Ronaldo Marques (ronaldomarques@email.com ,
-- http://ronaldomarques.dev);
-- É vetado qualquer utilização, venda, aluguél, distribuição, em partes ou integral deste projeto;
-- Este projeto tem finalidade exclusiva de demonstração de conhecimento e habilidades no
-- desenvolvimento de software para apresentação de portfólio e processos de recrutamento;
-- Sendo assim, o autor deste projeto (Ronaldo Marques) não reconhece nem assume qualquer
-- responsabilidade pela utilização do mesmo, tão pouco por qualquer possível reflexos ou
-- consequência de tal utilização.
-- ---
-- RIGHTS, LICENSE AND DISCLAIMER:
-- This file is an integral, indivisible, inseparable part of a particular project, which has its
-- use expressly exclusive to its author, Ronaldo Marques (ronaldomarques@email.com ,
-- http://ronaldomarques.dev);
-- Any use, sale, rental, distribution, in parts or integral of this project is prohibited;
-- This project has the sole purpose of demonstrating knowledge and skills in software development
-- for portfolio presentations and recruitment processes;
-- Therefore, the author of this project (Ronaldo Marques) does not recognize or assume any
-- responsibility for the use of it, neither for any possible reflexes or consequence of such use.

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


