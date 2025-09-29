SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER DATABASE "testUserDB" OWNER TO admin;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER SCHEMA public OWNER TO pg_database_owner;

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public."user" (
    id integer NOT NULL,
    name character varying NOT NULL,
    email text,
    age smallint NOT NULL,
    created_at timestamp with time zone DEFAULT now()
);

ALTER TABLE public."user" OWNER TO admin;

CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.user_id_seq OWNER TO admin;

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);

INSERT INTO public."user" VALUES (1, 'Корзинкин Валентин Всеволодович', 'dasda@demail.su', 47, '2025-09-09 11:41:36.665266+03') ON CONFLICT DO NOTHING;
INSERT INTO public."user" VALUES (2, 'Матарас Тарас Парнасович', 'mataraz@demail.su', 14, '2025-09-09 11:43:53.667167+03') ON CONFLICT DO NOTHING;
INSERT INTO public."user" VALUES (3, 'Равкин Ыгорь Олегович', 'hjhds@demail.su', 54, '2025-09-09 11:43:53.667167+03') ON CONFLICT DO NOTHING;

SELECT pg_catalog.setval('public.user_id_seq', 8, true);

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);