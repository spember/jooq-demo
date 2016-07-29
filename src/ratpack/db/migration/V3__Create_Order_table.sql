create table purchase_order (
  id uuid primary key,
  shopper_id uuid references shopper(id),
  total_sale integer not null,
  date timestamp  not null,
  details jsonb not null
);


create index o_shopper_idx on purchase_order(shopper_id);