create table users(
    id bigint auto_increment,
    name varchar(50),
    balance double,
    primary key (id)
);

create table user_transactions(
    id bigint auto_increment,
    user_id bigint,
    amount double,
    transaction_date timestamp,
    foreign key (user_id) references users(id) on delete cascade
);

insert into users(name, balance)
values
       ( 'Alice', 1500.00 ),
       ( 'Bob', 750.00 ),
       ( 'Charlie', 4500.00 );