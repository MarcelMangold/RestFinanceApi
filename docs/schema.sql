-- public.api_user definition

-- Drop table

-- DROP TABLE api_user;

CREATE TABLE api_user (
	id serial NOT NULL,
	username varchar(128) NULL,
	"password" varchar(128) NULL,
	"role" int4 NULL
);


-- public.chat definition

-- Drop table

-- DROP TABLE chat;

CREATE TABLE chat (
	id serial NOT NULL,
	CONSTRAINT chat_pkey PRIMARY KEY (id)
);


-- public.icon definition

-- Drop table

-- DROP TABLE icon;

CREATE TABLE icon (
	id serial NOT NULL,
	name varchar(128) NOT NULL,
	CONSTRAINT icon_pkey PRIMARY KEY (id)
);


-- public.user_management definition

-- Drop table

-- DROP TABLE user_management;

CREATE TABLE user_management (
	id serial NOT NULL,
	name varchar(128) NOT NULL,
	password varchar(128) NOT NULL,
	language integer,
	CONSTRAINT user_management_pkey PRIMARY KEY (id)
);


-- public.category definition

-- Drop table

-- DROP TABLE category;

CREATE TABLE category (
	id serial NOT NULL,
	name varchar(128) NOT NULL,
	userid int4 NOT NULL,
	iconid int4 NULL,
	CONSTRAINT categorie_pkey PRIMARY KEY (id),
	CONSTRAINT categorie_iconid_fkey FOREIGN KEY (iconid) REFERENCES icon(id),
	CONSTRAINT user_id_fkey FOREIGN KEY (userid) REFERENCES user_management(id)
);


-- public."transaction" definition

-- Drop table

-- DROP TABLE "transaction";

CREATE TABLE transaction (
	id serial NOT NULL,
	name varchar(50) NULL,
	amount numeric NULL,
	ispositive bool NULL,
	note varchar(250) NULL,
	categoryid int4 NOT NULL,
	userid int4 NOT NULL,
	chatid int4 NOT NULL,
	timestamp timestamp NOT NULL DEFAULT now(),
	CONSTRAINT transaction_pkey PRIMARY KEY (id),
	CONSTRAINT categorie_id_fkey FOREIGN KEY (categoryid) REFERENCES category(id),
	CONSTRAINT chat_id_fkey FOREIGN KEY (chatid) REFERENCES chat(id),
	CONSTRAINT user_id_fkey FOREIGN KEY (userid) REFERENCES user_management(id)
);