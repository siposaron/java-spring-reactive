CREATE TABLE users (
    id UUID default random_uuid(),
    name VARCHAR(50),
    balance NUMERIC,
    primary key (id)
);

CREATE TABLE user_transactions (
    id UUID default random_uuid(),
    user_id UUID,
    amount NUMERIC,
    transaction_date TIMESTAMP,
    primary key (id),
    foreign key (user_id) references users(id) on delete cascade
);

insert into users
    (name, balance)
    values
    ('Aron', 1121.23),
    ('John', 121.53),
    ('Jill', 18921.09);

