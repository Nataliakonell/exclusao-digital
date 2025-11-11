--
-- PostgreSQL database dump
--

\restrict 3qqc2m18DiAJVzZKbLHwDCG1YplfJhkuiRmRDviVgV82o6doiJ7SS1hhbKeiaQi

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-11-10 21:20:14

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 16523)
-- Name: categorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categorias (
    id integer NOT NULL,
    nome character varying(50) NOT NULL,
    descricao text
);


ALTER TABLE public.categorias OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16522)
-- Name: categorias_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categorias_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categorias_id_seq OWNER TO postgres;

--
-- TOC entry 4919 (class 0 OID 0)
-- Dependencies: 217
-- Name: categorias_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categorias_id_seq OWNED BY public.categorias.id;


--
-- TOC entry 222 (class 1259 OID 16544)
-- Name: motivos_exclusao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.motivos_exclusao (
    id integer NOT NULL,
    id_subcategoria integer,
    falta_interesse_necessidade integer,
    nao_saber_usar integer,
    nao_ter_onde_acessar integer,
    ser_muito_caro integer,
    preocupacoes_seguranca_privacidade integer,
    evitar_contato_conteudo_perigoso integer,
    outro_motivo integer
);


ALTER TABLE public.motivos_exclusao OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16543)
-- Name: motivos_exclusao_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.motivos_exclusao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.motivos_exclusao_id_seq OWNER TO postgres;

--
-- TOC entry 4920 (class 0 OID 0)
-- Dependencies: 221
-- Name: motivos_exclusao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.motivos_exclusao_id_seq OWNED BY public.motivos_exclusao.id;


--
-- TOC entry 220 (class 1259 OID 16532)
-- Name: subcategorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subcategorias (
    id integer NOT NULL,
    id_categoria integer,
    nome character varying(50) NOT NULL
);


ALTER TABLE public.subcategorias OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16531)
-- Name: subcategorias_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subcategorias_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.subcategorias_id_seq OWNER TO postgres;

--
-- TOC entry 4921 (class 0 OID 0)
-- Dependencies: 219
-- Name: subcategorias_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subcategorias_id_seq OWNED BY public.subcategorias.id;


--
-- TOC entry 4752 (class 2604 OID 16526)
-- Name: categorias id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias ALTER COLUMN id SET DEFAULT nextval('public.categorias_id_seq'::regclass);


--
-- TOC entry 4754 (class 2604 OID 16547)
-- Name: motivos_exclusao id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.motivos_exclusao ALTER COLUMN id SET DEFAULT nextval('public.motivos_exclusao_id_seq'::regclass);


--
-- TOC entry 4753 (class 2604 OID 16535)
-- Name: subcategorias id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subcategorias ALTER COLUMN id SET DEFAULT nextval('public.subcategorias_id_seq'::regclass);


--
-- TOC entry 4909 (class 0 OID 16523)
-- Dependencies: 218
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categorias (id, nome, descricao) FROM stdin;
1	TOTAL	Dados gerais
2	ÁREA	Classificação por tipo de área
3	REGIÃO	Divisão geográfica do país
4	SEXO	Divisão por gênero
5	COR OU RAÇA	Classificação por raça ou cor
6	GRAU DE INSTRUÇÃO	Nível educacional
7	FAIXA ETÁRIA	Idade dos indivíduos
8	RENDA FAMILIAR	Faixa de rendimento
9	CLASSE SOCIAL	Estratificação social
10	CONDIÇÃO DE ATIVIDADE	Situação de trabalho
11	TIPO DE OCUPAÇÃO	Tipo de vínculo profissional
\.


--
-- TOC entry 4913 (class 0 OID 16544)
-- Dependencies: 222
-- Data for Name: motivos_exclusao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.motivos_exclusao (id, id_subcategoria, falta_interesse_necessidade, nao_saber_usar, nao_ter_onde_acessar, ser_muito_caro, preocupacoes_seguranca_privacidade, evitar_contato_conteudo_perigoso, outro_motivo) FROM stdin;
2	1	57	79	29	33	39	37	3
3	2	58	77	29	33	39	38	3
4	3	51	84	31	33	40	36	3
5	4	55	73	26	28	33	31	3
6	5	53	85	26	31	34	34	1
7	6	63	81	23	33	45	45	4
8	7	61	72	59	62	64	55	5
9	8	66	85	31	32	50	46	2
10	9	56	76	30	28	34	32	3
11	10	58	82	29	38	44	43	2
12	11	59	70	27	28	38	37	4
13	12	64	80	35	34	44	43	2
14	13	57	86	31	37	43	40	2
15	14	56	84	56	47	42	45	0
16	15	38	84	39	30	24	26	1
17	16	42	78	15	32	22	21	5
18	17	51	89	26	35	34	31	3
19	18	58	80	31	33	39	39	3
20	19	50	63	32	30	44	38	1
21	20	91	53	18	19	54	34	1
22	21	46	38	46	42	43	62	2
23	22	88	84	59	59	84	83	0
24	23	55	71	48	48	46	31	0
25	24	35	87	32	32	56	49	1
26	25	49	71	32	34	34	39	3
27	26	60	82	26	31	38	34	3
28	27	52	82	36	40	41	38	3
29	28	53	76	25	28	36	35	2
30	29	62	82	26	30	36	41	6
31	30	65	70	16	36	56	48	1
32	31	76	97	24	27	53	55	0
33	32	71	77	36	0	57	74	0
34	33	80	41	65	67	62	32	0
35	34	69	72	19	22	24	23	3
36	35	72	90	34	35	62	49	1
37	36	91	100	0	0	29	1	0
38	37	83	66	7	13	44	31	0
39	38	57	76	29	27	38	37	1
40	39	54	81	32	38	40	38	4
41	40	58	73	33	36	38	36	2
42	41	56	82	28	31	39	38	3
43	42	59	79	24	37	53	40	5
44	43	57	71	36	36	35	34	1
45	44	56	81	28	31	39	38	3
\.


--
-- TOC entry 4911 (class 0 OID 16532)
-- Dependencies: 220
-- Data for Name: subcategorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.subcategorias (id, id_categoria, nome) FROM stdin;
1	1	 
2	2	Urbana
3	2	Rural
4	3	Sudeste
5	3	Nordeste
6	3	Sul
7	3	Norte
8	3	Centro-Oeste
9	4	Masculino
10	4	Feminino
11	5	Branca
12	5	Preta
13	5	Parda
14	5	Amarela
15	5	Indígena
16	5	Não respondeu
17	6	Analfabeto/Educação Infantil
18	6	Fundamental
19	6	Médio
20	6	Superior
21	7	De 10 a 15 anos
22	7	De 16 a 24 anos
23	7	De 25 a 34 anos
24	7	De 35 a 44 anos
25	7	De 45 a 59 anos
26	7	De 60 anos ou mais
27	8	Até 1 SM
28	8	Mais de 1 SM até 2 SM
29	8	Mais de 2 SM até 3 SM
30	8	Mais de 3 SM até 5 SM
31	8	Mais de 5 SM até 10 SM
32	8	Mais de 10 SM
33	8	Não tem renda
34	8	Não sabe
35	8	Não respondeu
36	9	A
37	9	B
38	9	C
39	9	DE
40	10	Na força de trabalho
41	10	Fora da força de trabalho
42	11	Formal
43	11	Informal
44	11	Não se aplica
\.


--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 217
-- Name: categorias_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categorias_id_seq', 11, true);


--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 221
-- Name: motivos_exclusao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.motivos_exclusao_id_seq', 45, true);


--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 219
-- Name: subcategorias_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.subcategorias_id_seq', 44, true);


--
-- TOC entry 4756 (class 2606 OID 16530)
-- Name: categorias categorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias
    ADD CONSTRAINT categorias_pkey PRIMARY KEY (id);


--
-- TOC entry 4760 (class 2606 OID 16549)
-- Name: motivos_exclusao motivos_exclusao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.motivos_exclusao
    ADD CONSTRAINT motivos_exclusao_pkey PRIMARY KEY (id);


--
-- TOC entry 4758 (class 2606 OID 16537)
-- Name: subcategorias subcategorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subcategorias
    ADD CONSTRAINT subcategorias_pkey PRIMARY KEY (id);


--
-- TOC entry 4762 (class 2606 OID 16550)
-- Name: motivos_exclusao motivos_exclusao_id_subcategoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.motivos_exclusao
    ADD CONSTRAINT motivos_exclusao_id_subcategoria_fkey FOREIGN KEY (id_subcategoria) REFERENCES public.subcategorias(id);


--
-- TOC entry 4761 (class 2606 OID 16538)
-- Name: subcategorias subcategorias_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subcategorias
    ADD CONSTRAINT subcategorias_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categorias(id);


-- Completed on 2025-11-10 21:20:15

--
-- PostgreSQL database dump complete
--

\unrestrict 3qqc2m18DiAJVzZKbLHwDCG1YplfJhkuiRmRDviVgV82o6doiJ7SS1hhbKeiaQi

