CREATE TABLE IF NOT EXISTS users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS menu_items (
                            id UUID PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            price DOUBLE PRECISION,
                            category VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS orders (
                        id UUID PRIMARY KEY,
                        user_id UUID REFERENCES users(id),
                        created_at TIMESTAMP NOT NULL,
                        total_price DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS order_details (
                               id UUID PRIMARY KEY,
                               order_id UUID REFERENCES orders(id),
                               menu_item_id UUID REFERENCES menu_items(id),
                               quantity INTEGER NOT NULL,
                               price DOUBLE PRECISION
);
