--
-- PostgreSQL database dump
--

\restrict LPmijSqeLu0j4fbVESTtOhUvzt1573VdG7vgcWJaMeQpodt0hGveYq1jx8kseIe

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-09-11 22:14:08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'WIN1251';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 16395)
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
-- TOC entry 217 (class 1259 OID 16394)
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
-- TOC entry 4897 (class 0 OID 0)
-- Dependencies: 217
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;


--
-- TOC entry 4741 (class 2604 OID 16398)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- TOC entry 4891 (class 0 OID 16395)
-- Dependencies: 218
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (id, name, email, age, created_at) FROM stdin;
6	Корзинкин Валентин Всеволодович	dasda@demail.su	47	2025-09-09 11:41:36.665266+03
7	Матарас Тарас Парнасович	mataraz@demail.su	14	2025-09-09 11:43:53.667167+03
8	Равкин Ыгорь Олегович	hjhds@demail.su	54	2025-09-09 11:43:53.667167+03
\.


--
-- TOC entry 4898 (class 0 OID 0)
-- Dependencies: 217
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 8, true);


--
-- TOC entry 4744 (class 2606 OID 16400)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


-- Completed on 2025-09-11 22:14:08

--
-- PostgreSQL database dump complete
--

\unrestrict LPmijSqeLu0j4fbVESTtOhUvzt1573VdG7vgcWJaMeQpodt0hGveYq1jx8kseIe

