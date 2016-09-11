CREATE TABLE user (
    id varchar(255) NOT NULL,
    provider_id varchar(255) NOT NULL,
    provider_user_id varchar(255) NOT NULL,
    image_url varchar(255),
    last_name varchar(255),
    name varchar(255),
    username varchar(255),
    PRIMARY KEY (id),
    UNIQUE KEY UK_USER_USERNAME (username),
    UNIQUE KEY UK_USER_PROVIDER_INFO (provider_id, provider_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;
