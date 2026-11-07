-- CATEGORIAS
INSERT INTO tb_categories (name, created_at) VALUES ('Livros', NOW());
INSERT INTO tb_categories (name, created_at) VALUES ('Eletrônicos', NOW());
INSERT INTO tb_categories (name, created_at) VALUES ('Computadores', NOW());

-- PRODUTOS
INSERT INTO tb_products (name, description, price, img_url) VALUES ('O Senhor dos Anéis', 'Edição única com os três volumes da obra clássica de fantasia.', 90.5, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('Smart TV', 'Smart TV 50 polegadas 4K com Wi-Fi e HDR.', 2190.0, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('Macbook Pro', 'Notebook Apple 14 polegadas, 16GB de RAM e SSD de 512GB.', 1250.0, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('PC Gamer', 'Computador gamer com processador de 8 núcleos e placa de vídeo dedicada.', 1200.0, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('Rails for Dummies', 'Guia introdutório ao desenvolvimento web com Ruby on Rails.', 100.99, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/5-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('PC Gamer Ex', 'Computador gamer de alto desempenho com 32GB de RAM.', 1350.0, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('PC Gamer X', 'Computador gamer com refrigeração líquida e gabinete RGB.', 1350.0, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/7-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('Clean Code', 'Livro de Robert C. Martin sobre boas práticas de código.', 119.9, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/8-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('Fone Bluetooth', 'Fone de ouvido sem fio com cancelamento de ruído.', 349.9, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/9-big.jpg');
INSERT INTO tb_products (name, description, price, img_url) VALUES ('Monitor 27', 'Monitor 27 polegadas QHD, 144Hz, painel IPS.', 1599.0, 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/10-big.jpg');

-- PRODUTO x CATEGORIA
INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (3, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (4, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (5, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (6, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (7, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (8, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (9, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (10, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (10, 3);

-- USUÁRIOS (ids 1..2) - senha de ambos: 123456 (hash BCrypt)
INSERT INTO tb_users (first_name, last_name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_users (first_name, last_name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

-- ROLES
INSERT INTO tb_roles (authority, user_id) VALUES ('ROLE_OPERATOR', 1);
INSERT INTO tb_roles (authority, user_id) VALUES ('ROLE_OPERATOR', 2);
INSERT INTO tb_roles (authority, user_id) VALUES ('ROLE_ADMIN', 2);