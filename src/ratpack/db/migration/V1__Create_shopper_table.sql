create table shopper (
  id uuid primary key,
  first_name varchar(100) not null,
  last_name varchar(255) not null,
  email varchar(100) not null
);

create index s_email_idx on shopper (email);