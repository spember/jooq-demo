create table product (
  sku varchar(20) primary key,
  name varchar(200) not null,
  description text not null,
  price integer not null,
  attributes jsonb not null
);


