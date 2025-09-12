--
-- PostgreSQL database dump
--

\restrict dLPZv70q6gP0Hm4S0BNz8t8b5QASCdE0bfbC3Je0wF2viE35eoFSDgxcHTpcxya

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-09-12 22:28:08

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

DROP DATABASE IF EXISTS "userDB";
--
-- TOC entry 4898 (class 1262 OID 16437)
-- Name: userDB; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "userDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1251';


ALTER DATABASE "userDB" OWNER TO postgres;

\unrestrict dLPZv70q6gP0Hm4S0BNz8t8b5QASCdE0bfbC3Je0wF2viE35eoFSDgxcHTpcxya
\connect "userDB"
\restrict dLPZv70q6gP0Hm4S0BNz8t8b5QASCdE0bfbC3Je0wF2viE35eoFSDgxcHTpcxya

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

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4899 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16438)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    name character varying NOT NULL,
    email text,
    age smallint NOT NULL,
    created_at timestamp with time zone DEFAULT now()
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16444)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 4900 (class 0 OID 0)
-- Dependencies: 218
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;


--
-- TOC entry 4742 (class 2604 OID 16445)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- TOC entry 4891 (class 0 OID 16438)
-- Dependencies: 217
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" VALUES (6, 'Корзинкин Валентин Всеволодович', 'dasda@demail.su', 47, '2025-09-09 11:41:36.665266+03') ON CONFLICT DO NOTHING;
INSERT INTO public."user" VALUES (7, 'Матарас Тарас Парнасович', 'mataraz@demail.su', 14, '2025-09-09 11:43:53.667167+03') ON CONFLICT DO NOTHING;
INSERT INTO public."user" VALUES (8, 'Равкин Ыгорь Олегович', 'hjhds@demail.su', 54, '2025-09-09 11:43:53.667167+03') ON CONFLICT DO NOTHING;


--
-- TOC entry 4901 (class 0 OID 0)
-- Dependencies: 218
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 8, true);


--
-- TOC entry 4745 (class 2606 OID 16447)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


-- Completed on 2025-09-12 22:28:08

--
-- PostgreSQL database dump complete
--

\unrestrict dLPZv70q6gP0Hm4S0BNz8t8b5QASCdE0bfbC3Je0wF2viE35eoFSDgxcHTpcxya

