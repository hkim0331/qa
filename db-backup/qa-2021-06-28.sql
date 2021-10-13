--
-- PostgreSQL database dump
--

-- Dumped from database version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 12.7 (Ubuntu 12.7-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

--
-- Name: answers; Type: TABLE; Schema: public; Owner: qa
--

CREATE TABLE public.answers (
    id integer NOT NULL,
    q_id integer,
    nick character varying(8),
    a text,
    ts timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    g integer DEFAULT 1
);


ALTER TABLE public.answers OWNER TO qa;

--
-- Name: answers_id_seq; Type: SEQUENCE; Schema: public; Owner: qa
--

CREATE SEQUENCE public.answers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.answers_id_seq OWNER TO qa;

--
-- Name: answers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: qa
--

ALTER SEQUENCE public.answers_id_seq OWNED BY public.answers.id;


--
-- Name: goods; Type: TABLE; Schema: public; Owner: qa
--

CREATE TABLE public.goods (
    id integer NOT NULL,
    q_id integer,
    a_id integer,
    nick character varying(8),
    ts timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.goods OWNER TO qa;

--
-- Name: goods_id_seq; Type: SEQUENCE; Schema: public; Owner: qa
--

CREATE SEQUENCE public.goods_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.goods_id_seq OWNER TO qa;

--
-- Name: goods_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: qa
--

ALTER SEQUENCE public.goods_id_seq OWNED BY public.goods.id;


--
-- Name: questions; Type: TABLE; Schema: public; Owner: qa
--

CREATE TABLE public.questions (
    id integer NOT NULL,
    q text,
    nick character varying(8),
    ts timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.questions OWNER TO qa;

--
-- Name: questions_id_seq; Type: SEQUENCE; Schema: public; Owner: qa
--

CREATE SEQUENCE public.questions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.questions_id_seq OWNER TO qa;

--
-- Name: questions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: qa
--

ALTER SEQUENCE public.questions_id_seq OWNED BY public.questions.id;


--
-- Name: answers id; Type: DEFAULT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.answers ALTER COLUMN id SET DEFAULT nextval('public.answers_id_seq'::regclass);


--
-- Name: goods id; Type: DEFAULT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.goods ALTER COLUMN id SET DEFAULT nextval('public.goods_id_seq'::regclass);


--
-- Name: questions id; Type: DEFAULT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.questions ALTER COLUMN id SET DEFAULT nextval('public.questions_id_seq'::regclass);


--
-- Data for Name: answers; Type: TABLE DATA; Schema: public; Owner: qa
--

COPY public.answers (id, q_id, nick, a, ts, g) FROM stdin;
15	10	hkimura	質問に具体性が全くありません。	2021-06-23 10:51:17.099898	9
33	20	manapee	https://kvillage.jp/column/112429#rtoc-3	2021-06-23 20:44:20.09757	8
32	7	hkimura	一番好きは日本酒だな。すぐに記憶なくすけど。	2021-06-23 19:09:54.983458	2
8	4	hkimura	hkimura のタイピングスコアは学生でも見れるようにした。さっき、-500点出してしまった。ズレたんかなあ。	2021-06-19 14:38:48.952523	13
16	5	pe-ko	すごい	2021-06-23 10:51:54.615847	5
24	13	hkimura	あります。表示できないキーを打った時など。コントロールA とか、コントロールDとか。\r\n	2021-06-23 12:09:32.320435	13
14	9	tackey	はい、ニックネームを変更したら、それまでの記録はリセットされるそうです。	2021-06-23 10:51:00.990654	19
34	22	hkimura	* タイプの練習は10分程度に止める。\r\n* かわいい彼女（やさしい彼）と談笑する。\r\n* 歴史に残る文学小説を読む。\r\n*（プログラムに興味があるなら）github 等で見つかる名人のプログラムを鑑賞する。	2021-06-25 19:08:17.36005	7
22	15	Repay	自分は日本史好きなのでこのセンスは好きですよ	2021-06-23 12:07:12.283022	3
31	14	hkimura	今回の課題のことであれば、課題はホームページ作りを通したプログラミングの世界への招待であり、一般公開せず、仲間内で学習効果を讃えあう（大げさ？）ものです。なので OK。ただ、hkimura の評価は見栄えよりも HTML/CSS のプログラムに重点を置くので、女の子の写真をたくさん載せても評価は伸びません。\r\n\r\n一般には、他の人が著作権を所有するイメージ・写真を自分のホームページで許可なく公開すると法律違反になります。	2021-06-23 14:35:03.868858	3
25	21	hkimura	影響しません。	2021-06-23 13:28:56.462098	5
1	1	hkimura	上書き保存を具体的にどうやっているかを説明してください。さすれば適確な回答出やすいです。具体的でない質問には具体的でない回答が行くだろう。	2021-06-18 21:24:35.443001	27
36	23	hkimura	具体例を示すともっといいだろうな。\r\n単語の間にはスペースひとつ打つ必要があるが、abc. とあったら abc. と打てばよろし、\r\nabc . (c と . の間にスペースひとつ)打つと、ミス。で、それ以降、一単語ずつずれるんで、かなりマイナスつくだろう。	2021-06-28 11:31:04.367742	3
19	7	pe-ko	スパゲッティ	2021-06-23 12:03:16.318895	6
10	7	hkimura	ビールです。	2021-06-23 09:15:08.166609	29
35	7	55	白米、肉です\r\nらーめんも好きです	2021-06-26 00:14:57.975206	4
3	3	hkimura	href をタイプミスしてる。	2021-06-18 21:29:35.366836	17
27	18	hkimura	いいよー。	2021-06-23 13:30:06.800097	8
28	7	hkimura	生協のカレーが好きです	2021-06-23 13:48:23.870664	10
9	3	hkimura	タイプミスに気をつけよう	2021-06-22 13:38:51.230568	10
4	4	hkimura	はい。オープン戦だったから。今、見えんやろ？どんどんタイプ練習しないと平常点に響くよ。	2021-06-18 21:30:26.608078	11
20	12	ren	一日三回各五分	2021-06-23 12:05:22.583129	5
18	12	hkimura	5分かける三回に一票。	2021-06-23 11:40:55.001921	30
30	19	hkimura	どんどんやるべし。でも、サイズには気をつけような。昨年度の制限はイメージ、プログラム、全部あわせて 1MB でした。	2021-06-23 14:16:17.519665	6
7	1	hkimura	具体的でない質問には回答がつけにくいので、回答つかないかもな。	2021-06-18 21:45:21.798143	23
6	6	hkimura	= が全角になってるよ。プログラミングではほっとんど全角文字は使わない。全角スペースは特にほんとにじゃまです。	2021-06-18 21:37:39.989841	8
2	2	hkimura	残念。閉じクオートが必要なところにないです。	2021-06-18 21:25:40.024303	34
21	15	hkimura	はい、そうです。安易すぎます。	2021-06-23 12:06:16.188097	9
12	7	hkimura	わいんもウオツカも好きです。	2021-06-23 09:17:11.375967	12
26	21	hkimura	課題、鬼滅のヤイバだっだ？	2021-06-23 13:29:23.936942	23
13	8	hkimura	起きることです。	2021-06-23 10:50:12.462087	15
17	11	hkimura	がんばってね♥️	2021-06-23 10:52:17.247239	41
5	5	hkimura	実は、タイプミスをマイナスするところ、プラスにしてしまったというプログラミング上のエラーがオープン戦にはあったかもしれないが、真実は、hkimura の胸の中にある。	2021-06-18 21:31:29.703512	23
29	17	hkimura	q, r, s, t, u, v の中から例文を選びました。不適切なやつ削って、640くらいあると思います。\r\n削り損ねたのも残っているかなあ。みんなが手伝ってくれるとよかったのにぃ😮‍💨　全部一人でしました。	2021-06-23 14:13:20.908144	6
11	7	kenshin	好きな食べ物はおしるこです。	2021-06-23 09:17:07.148502	19
23	16	ren	グッドマークの上です	2021-06-23 12:08:51.918093	18
\.


--
-- Data for Name: goods; Type: TABLE DATA; Schema: public; Owner: qa
--

COPY public.goods (id, q_id, a_id, nick, ts) FROM stdin;
1	\N	2	hkimura	2021-06-19 13:06:59.822915
2	\N	5	hkimura	2021-06-19 13:07:43.284136
3	\N	5	hkimura	2021-06-19 13:07:46.781908
4	\N	3	ayako	2021-06-21 15:57:11.700287
5	\N	1	hkimura	2021-06-21 16:25:09.81833
6	\N	8	hkimura	2021-06-21 22:33:20.251344
7	\N	3	hkimura	2021-06-22 13:37:35.932529
8	\N	1	hkimura	2021-06-23 09:12:35.535806
9	\N	2	hkimura	2021-06-23 09:13:04.683732
10	\N	3	hkimura	2021-06-23 09:13:29.724998
11	\N	7	gyugyu	2021-06-23 09:14:06.906789
12	\N	1	gyugyu	2021-06-23 09:14:12.275383
13	\N	2	gyugyu	2021-06-23 09:14:18.582431
14	\N	9	gyugyu	2021-06-23 09:14:27.077799
15	\N	3	gyugyu	2021-06-23 09:14:31.012988
16	\N	4	gyugyu	2021-06-23 09:14:38.417286
17	\N	8	gyugyu	2021-06-23 09:14:41.376267
18	\N	5	gyugyu	2021-06-23 09:14:49.567601
19	\N	6	dai77k	2021-06-23 09:14:59.853652
20	\N	6	gyugyu	2021-06-23 09:15:06.324968
21	\N	10	kenshin	2021-06-23 09:15:18.550123
22	\N	10	akiraki	2021-06-23 09:15:19.753143
23	\N	10	gyugyu	2021-06-23 09:15:28.627192
24	\N	10	Masaki	2021-06-23 09:15:37.937364
25	\N	5	55	2021-06-23 09:15:58.176457
26	\N	5	55	2021-06-23 09:16:00.453089
27	\N	5	55	2021-06-23 09:16:04.931913
28	\N	5	yasu	2021-06-23 09:16:16.857445
29	\N	10	avrw1515	2021-06-23 09:16:36.704957
30	\N	10	mya	2021-06-23 09:16:45.272939
31	\N	10	GOGOGO	2021-06-23 09:16:57.292099
32	\N	5	mya	2021-06-23 09:17:09.534836
33	\N	11	hkimura	2021-06-23 09:17:36.059831
34	\N	5	18f11	2021-06-23 09:17:48.687562
35	\N	3	dai77k	2021-06-23 09:17:50.55748
36	\N	10	yu0816	2021-06-23 09:17:52.7661
37	\N	2	55	2021-06-23 09:17:53.608397
38	\N	2	55	2021-06-23 09:17:56.983977
39	\N	2	55	2021-06-23 09:17:57.934239
40	\N	2	55	2021-06-23 09:17:58.141589
41	\N	2	55	2021-06-23 09:17:58.343542
42	\N	2	55	2021-06-23 09:17:58.526815
43	\N	2	55	2021-06-23 09:17:58.751116
44	\N	2	55	2021-06-23 09:18:02.684921
45	\N	2	55	2021-06-23 09:18:09.296289
46	\N	2	18f11	2021-06-23 09:18:09.804785
47	\N	2	55	2021-06-23 09:18:10.301549
48	\N	2	55	2021-06-23 09:18:10.752036
49	\N	2	55	2021-06-23 09:18:11.038486
50	\N	2	55	2021-06-23 09:18:11.219195
51	\N	2	Cnn	2021-06-23 09:18:15.728925
52	\N	11	Kikuyu	2021-06-23 09:18:41.421445
53	\N	6	Cnn	2021-06-23 09:18:45.640349
54	\N	10	Kikuyu	2021-06-23 09:18:46.211012
55	\N	10	sato	2021-06-23 09:18:46.750827
56	\N	11	sato	2021-06-23 09:18:50.835052
57	\N	12	sato	2021-06-23 09:18:56.411571
58	\N	10	yasu	2021-06-23 09:19:08.629708
59	\N	7	mya	2021-06-23 09:19:09.962743
60	\N	11	yasu	2021-06-23 09:19:14.512498
61	\N	12	sato	2021-06-23 09:19:14.73913
62	\N	7	mya	2021-06-23 09:19:18.739305
63	\N	12	yasu	2021-06-23 09:19:21.497762
64	\N	12	sato	2021-06-23 09:19:28.055358
65	\N	2	mya	2021-06-23 09:19:35.530877
66	\N	6	yasu	2021-06-23 09:19:39.156336
67	\N	4	mya	2021-06-23 09:19:54.389653
68	\N	5	yasu	2021-06-23 09:19:58.33151
69	\N	8	mya	2021-06-23 09:20:03.740006
70	\N	10	kenshin	2021-06-23 09:20:10.831253
71	\N	4	yasu	2021-06-23 09:20:13.033222
72	\N	8	yasu	2021-06-23 09:20:19.250627
73	\N	3	yasu	2021-06-23 09:20:35.034913
74	\N	9	yasu	2021-06-23 09:20:41.834197
75	\N	1	yyy	2021-06-23 09:22:17.817734
76	\N	7	yyy	2021-06-23 09:22:55.127103
77	\N	7	yyy	2021-06-23 09:22:57.960787
78	\N	7	yyy	2021-06-23 09:22:59.161134
79	\N	1	yyy	2021-06-23 09:23:02.602599
80	\N	11	GOGOGO	2021-06-23 10:19:24.375719
81	\N	11	Shiba	2021-06-23 10:19:38.814931
82	\N	11	Shiba	2021-06-23 10:19:43.122449
83	\N	11	Shiba	2021-06-23 10:19:45.51417
84	\N	10	ra	2021-06-23 10:22:44.047533
85	\N	7	Canola	2021-06-23 10:32:45.036524
86	\N	1	Canola	2021-06-23 10:32:58.698712
87	\N	10	Canola	2021-06-23 10:33:32.882717
88	\N	11	Canola	2021-06-23 10:33:38.27518
89	\N	2	hkimura	2021-06-23 10:48:56.287765
90	\N	3	T.T	2021-06-23 10:49:20.862594
91	\N	9	hkimura	2021-06-23 10:49:22.713703
92	\N	11	tackey	2021-06-23 10:49:23.74779
93	\N	7	K.T	2021-06-23 10:49:33.723856
94	\N	1	Tano	2021-06-23 10:49:36.407042
95	\N	1	Tano	2021-06-23 10:49:39.907577
96	\N	7	Tano	2021-06-23 10:49:47.025935
97	\N	7	yuji	2021-06-23 10:49:47.04013
98	\N	1	K.T	2021-06-23 10:49:52.249425
99	\N	10	S	2021-06-23 10:49:55.225145
100	\N	7	yuji	2021-06-23 10:49:56.590542
101	\N	7	yuji	2021-06-23 10:49:57.39154
102	\N	7	K.T	2021-06-23 10:49:59.06589
103	\N	11	Tano	2021-06-23 10:49:59.684472
104	\N	11	Tano	2021-06-23 10:50:02.799991
105	\N	1	K.T	2021-06-23 10:50:03.243008
106	\N	10	Tano	2021-06-23 10:50:08.676794
107	\N	2	K.T	2021-06-23 10:50:10.578267
108	\N	12	Tano	2021-06-23 10:50:15.229623
109	\N	3	K.T	2021-06-23 10:50:22.904848
110	\N	10	PIEN	2021-06-23 10:50:24.579254
111	\N	9	K.T	2021-06-23 10:50:26.072035
112	\N	5	Tano	2021-06-23 10:50:29.155679
113	\N	13	K.T	2021-06-23 10:50:34.747358
114	\N	10	PIEN	2021-06-23 10:50:37.439202
115	\N	10	PIEN	2021-06-23 10:50:42.856178
116	\N	1	ren	2021-06-23 10:50:48.582864
117	\N	10	PIEN	2021-06-23 10:50:50.267285
118	\N	7	ren	2021-06-23 10:50:54.684213
119	\N	14	tackey	2021-06-23 10:51:03.727072
120	\N	1	Aoi	2021-06-23 10:51:07.186893
121	\N	7	Aoi	2021-06-23 10:51:10.553859
122	\N	2	Aoi	2021-06-23 10:51:16.53311
123	\N	2	ren	2021-06-23 10:51:21.69712
124	\N	3	Aoi	2021-06-23 10:51:24.170861
125	\N	9	Aoi	2021-06-23 10:51:26.889164
126	\N	4	Aoi	2021-06-23 10:51:32.155144
127	\N	13	ZRNzsBYI	2021-06-23 10:51:32.731055
128	\N	8	Aoi	2021-06-23 10:51:34.898065
129	\N	3	ren	2021-06-23 10:51:40.722921
130	\N	13	ZRNzsBYI	2021-06-23 10:51:40.876323
131	\N	5	Aoi	2021-06-23 10:51:42.331269
132	\N	13	ZRNzsBYI	2021-06-23 10:51:42.42903
133	\N	15	manapee	2021-06-23 10:51:44.975762
134	\N	9	ren	2021-06-23 10:51:45.359153
135	\N	15	pop	2021-06-23 10:51:45.967657
136	\N	15	manapee	2021-06-23 10:51:46.939068
137	\N	6	Aoi	2021-06-23 10:51:47.383982
138	\N	15	pop	2021-06-23 10:51:48.286042
139	\N	15	pop	2021-06-23 10:51:49.436302
140	\N	10	931	2021-06-23 10:51:50.478021
141	\N	2	ZRNzsBYI	2021-06-23 10:51:50.978811
142	\N	12	Aoi	2021-06-23 10:51:51.694169
143	\N	10	Aoi	2021-06-23 10:51:54.254674
144	\N	11	Aoi	2021-06-23 10:51:56.694122
145	\N	13	Aoi	2021-06-23 10:52:01.337108
146	\N	4	ren	2021-06-23 10:52:04.555137
147	\N	5	tfc	2021-06-23 10:52:05.605524
148	\N	14	Aoi	2021-06-23 10:52:06.180981
149	\N	13	manapee	2021-06-23 10:52:06.322045
150	\N	7	muramura	2021-06-23 10:52:09.090305
151	\N	8	ren	2021-06-23 10:52:10.807939
152	\N	15	Aoi	2021-06-23 10:52:11.691602
153	\N	1	muramura	2021-06-23 10:52:14.192146
154	\N	7	muramura	2021-06-23 10:52:20.043203
155	\N	17	ZRNzsBYI	2021-06-23 10:52:21.70129
156	\N	17	931	2021-06-23 10:52:22.697867
160	\N	5	ren	2021-06-23 10:52:24.938678
161	\N	14	tackey	2021-06-23 10:52:26.297072
163	\N	17	garasubo	2021-06-23 10:52:27.297365
164	\N	17	negima	2021-06-23 10:52:27.815319
172	\N	17	Aoi	2021-06-23 10:52:30.881781
173	\N	2	muramura	2021-06-23 10:52:31.17422
174	\N	17	931	2021-06-23 10:52:32.0005
175	\N	6	ren	2021-06-23 10:52:33.390835
177	\N	17	931	2021-06-23 10:52:34.611292
178	\N	14	tackey	2021-06-23 10:52:34.653265
179	\N	14	tackey	2021-06-23 10:52:35.545377
181	\N	17	ZRNzsBYI	2021-06-23 10:52:36.005647
183	\N	14	tackey	2021-06-23 10:52:37.463812
184	\N	17	931	2021-06-23 10:52:37.813914
157	\N	17	eva	2021-06-23 10:52:22.799184
158	\N	17	ZRNzsBYI	2021-06-23 10:52:23.313722
162	\N	17	Tano	2021-06-23 10:52:27.205601
165	\N	17	eva	2021-06-23 10:52:27.889441
168	\N	17	eva	2021-06-23 10:52:30.081401
171	\N	17	Alby	2021-06-23 10:52:30.865829
180	\N	17	Katsuo	2021-06-23 10:52:35.662086
182	\N	17	pop	2021-06-23 10:52:37.054452
159	\N	17	pop	2021-06-23 10:52:24.663607
166	\N	17	ZRNzsBYI	2021-06-23 10:52:28.948086
167	\N	17	931	2021-06-23 10:52:29.373456
169	\N	17	pop	2021-06-23 10:52:30.714949
170	\N	14	tackey	2021-06-23 10:52:30.798597
176	\N	17	PIEN	2021-06-23 10:52:33.992664
185	\N	17	Katsuo	2021-06-23 10:52:38.047234
186	\N	17	morimori	2021-06-23 10:52:38.64854
187	\N	2	muramura	2021-06-23 10:52:40.948511
188	\N	17	931	2021-06-23 10:52:41.199856
189	\N	17	pop	2021-06-23 10:52:42.324948
190	\N	17	yRyo	2021-06-23 10:52:43.549041
191	\N	17	K.T	2021-06-23 10:52:43.992941
192	\N	1	S	2021-06-23 10:52:44.367552
193	\N	12	ren	2021-06-23 10:52:44.57679
194	\N	17	931	2021-06-23 10:52:44.643912
195	\N	17	hori	2021-06-23 10:52:44.918833
196	\N	2	muramura	2021-06-23 10:52:45.568572
197	\N	17	mi	2021-06-23 10:52:45.58585
198	\N	10	ren	2021-06-23 10:52:46.660318
199	\N	11	ren	2021-06-23 10:52:48.968313
200	\N	17	931	2021-06-23 10:52:51.718486
201	\N	7	manapee	2021-06-23 10:52:54.069631
202	\N	3	muramura	2021-06-23 10:52:54.420475
203	\N	17	931	2021-06-23 10:52:54.546324
204	\N	13	ren	2021-06-23 10:52:56.997423
205	\N	9	muramura	2021-06-23 10:52:58.322884
206	\N	14	ren	2021-06-23 10:53:06.340484
207	\N	15	ren	2021-06-23 10:53:11.190838
208	\N	14	tackey	2021-06-23 10:53:19.449362
209	\N	17	ren	2021-06-23 10:53:19.549299
210	\N	14	tackey	2021-06-23 10:53:23.341061
211	\N	8	muramura	2021-06-23 10:53:25.674863
212	\N	14	ssw2	2021-06-23 10:54:35.3161
213	\N	17	ssw2	2021-06-23 11:19:59.083416
214	\N	18	negima	2021-06-23 11:40:57.944414
215	\N	18	negima	2021-06-23 11:40:59.578157
216	\N	15	K.T	2021-06-23 11:41:02.202936
217	\N	18	T.T	2021-06-23 11:41:05.603969
218	\N	18	K.T	2021-06-23 11:41:07.063533
219	\N	18	T.T	2021-06-23 11:41:07.790705
220	\N	18	sodemi	2021-06-23 11:41:08.091136
221	\N	5	K.T	2021-06-23 11:41:16.008213
222	\N	18	sodemi	2021-06-23 11:41:16.91643
223	\N	18	ZRNzsBYI	2021-06-23 11:41:20.950933
224	\N	4	K.T	2021-06-23 11:41:27.209811
225	\N	8	K.T	2021-06-23 11:41:29.876999
226	\N	18	muramura	2021-06-23 11:41:31.484752
227	\N	18	Katsuo	2021-06-23 11:41:33.493994
228	\N	18	garasubo	2021-06-23 11:41:39.169185
229	\N	18	wrxsti	2021-06-23 11:42:30.644591
230	\N	17	wrxsti	2021-06-23 11:51:23.446518
231	\N	14	wrxsti	2021-06-23 11:51:40.505421
232	\N	18	Leo	2021-06-23 11:51:58.108273
233	\N	18	Leo	2021-06-23 11:52:37.824831
234	\N	17	Leo	2021-06-23 11:52:57.301747
235	\N	17	Leo	2021-06-23 11:53:03.702559
236	\N	18	Leo	2021-06-23 11:53:58.961552
237	\N	16	ZRNzsBYI	2021-06-23 11:54:15.037651
238	\N	1	Leo	2021-06-23 11:54:19.779982
239	\N	17	Naoki	2021-06-23 11:59:10.124619
240	\N	18	Naoki	2021-06-23 11:59:25.542076
241	\N	18	ryoken	2021-06-23 11:59:47.184249
242	\N	5	ryoken	2021-06-23 12:00:14.277859
243	\N	13	ryoken	2021-06-23 12:00:54.386624
244	\N	1	kent0506	2021-06-23 12:00:59.154699
245	\N	16	kent0506	2021-06-23 12:01:15.38814
246	\N	5	kent0506	2021-06-23 12:01:17.921647
247	\N	5	kent0506	2021-06-23 12:01:20.9399
248	\N	18	kent0506	2021-06-23 12:01:37.340458
249	\N	17	kent0506	2021-06-23 12:01:53.776807
250	\N	11	pe-ko	2021-06-23 12:03:22.420465
251	\N	18	ren	2021-06-23 12:05:25.231806
252	\N	21	Repay	2021-06-23 12:06:25.934238
253	\N	21	Repay	2021-06-23 12:06:29.31825
254	\N	21	ren	2021-06-23 12:06:30.643686
255	\N	8	K.T	2021-06-23 12:07:22.110566
256	\N	7	natsuki	2021-06-23 12:08:13.781167
257	\N	1	natsuki	2021-06-23 12:08:19.996244
258	\N	21	muramura	2021-06-23 12:08:27.206144
259	\N	2	natsuki	2021-06-23 12:08:28.831756
260	\N	22	muramura	2021-06-23 12:08:37.399373
261	\N	14	natsuki	2021-06-23 12:08:44.799118
262	\N	18	natsuki	2021-06-23 12:08:56.057237
263	\N	7	muramura	2021-06-23 12:09:02.382918
264	\N	23	natsuki	2021-06-23 12:09:05.216345
265	\N	1	muramura	2021-06-23 12:09:08.167296
266	\N	2	muramura	2021-06-23 12:09:19.7668
267	\N	17	natsuki	2021-06-23 12:09:20.375065
268	\N	3	muramura	2021-06-23 12:09:27.892023
269	\N	9	muramura	2021-06-23 12:09:32.075579
270	\N	4	muramura	2021-06-23 12:09:41.513347
271	\N	8	muramura	2021-06-23 12:09:46.887469
272	\N	16	muramura	2021-06-23 12:09:55.469334
273	\N	5	muramura	2021-06-23 12:10:00.611388
274	\N	6	muramura	2021-06-23 12:10:08.523849
275	\N	23	Leo	2021-06-23 12:10:10.494741
276	\N	11	muramura	2021-06-23 12:10:16.36967
277	\N	12	muramura	2021-06-23 12:10:20.061365
278	\N	23	Leo	2021-06-23 12:10:21.378812
279	\N	10	muramura	2021-06-23 12:10:23.962263
280	\N	19	muramura	2021-06-23 12:10:27.647845
281	\N	12	muramura	2021-06-23 12:10:38.032706
282	\N	10	muramura	2021-06-23 12:10:43.513959
283	\N	19	muramura	2021-06-23 12:10:48.273406
284	\N	14	muramura	2021-06-23 12:11:03.173103
285	\N	24	muramura	2021-06-23 12:11:12.506551
286	\N	23	muramura	2021-06-23 12:11:24.210102
287	\N	24	Leo	2021-06-23 12:15:32.651701
288	\N	24	Leo	2021-06-23 12:15:35.053202
289	\N	24	Leo	2021-06-23 12:15:40.369677
290	\N	23	Leo	2021-06-23 12:16:02.013812
291	\N	7	Leo	2021-06-23 12:17:16.355083
292	\N	2	Leo	2021-06-23 12:17:31.639445
293	\N	1	LapiS	2021-06-23 12:17:37.817019
294	\N	1	LapiS	2021-06-23 12:17:41.331835
295	\N	1	LapiS	2021-06-23 12:17:44.482479
296	\N	2	LapiS	2021-06-23 12:18:09.491351
297	\N	3	LapiS	2021-06-23 12:18:20.783794
298	\N	4	LapiS	2021-06-23 12:18:27.983885
299	\N	8	LapiS	2021-06-23 12:18:36.426889
300	\N	21	LapiS	2021-06-23 12:18:47.793009
301	\N	18	km8	2021-06-23 12:18:51.510651
302	\N	18	km8	2021-06-23 12:18:53.801666
303	\N	18	km8	2021-06-23 12:18:55.744216
304	\N	18	km8	2021-06-23 12:19:00.886279
305	\N	18	km8	2021-06-23 12:19:04.495341
306	\N	23	LapiS	2021-06-23 12:20:13.718763
307	\N	12	LapiS	2021-06-23 12:20:23.22763
308	\N	16	931	2021-06-23 12:21:38.579699
309	\N	20	hkimura	2021-06-23 14:03:20.9054
310	\N	23	Naoki	2021-06-23 14:28:08.212725
311	\N	29	negima	2021-06-23 15:21:17.52336
312	\N	26	manapee	2021-06-23 20:42:05.133855
313	\N	29	manapee	2021-06-23 20:45:02.459012
314	\N	31	munemasa	2021-06-23 21:34:09.79685
315	\N	27	munemasa	2021-06-23 21:34:36.347456
316	\N	24	munemasa	2021-06-23 21:34:48.496208
317	\N	33	hkimura	2021-06-23 21:40:24.421155
318	\N	14	km8	2021-06-23 22:12:20.945442
319	\N	14	km8	2021-06-23 22:12:23.395809
320	\N	24	km8	2021-06-23 22:13:10.84535
321	\N	23	km8	2021-06-23 22:13:58.354548
322	\N	29	ren	2021-06-23 22:40:29.112252
323	\N	27	ren	2021-06-23 22:40:46.997092
324	\N	27	ren	2021-06-23 22:40:49.22061
325	\N	27	ren	2021-06-23 22:40:51.124847
326	\N	30	ren	2021-06-23 22:41:19.513857
327	\N	33	ren	2021-06-23 22:41:36.95318
328	\N	25	ren	2021-06-23 22:41:52.665659
329	\N	26	ren	2021-06-23 22:41:55.340914
330	\N	25	K.T	2021-06-23 23:31:22.103077
331	\N	26	K.T	2021-06-23 23:31:24.729344
332	\N	33	K.T	2021-06-23 23:31:29.188316
333	\N	30	K.T	2021-06-23 23:31:33.627767
334	\N	27	K.T	2021-06-23 23:31:39.318821
335	\N	22	K.T	2021-06-23 23:31:45.300225
336	\N	21	K.T	2021-06-23 23:31:47.559564
337	\N	29	K.T	2021-06-23 23:31:54.64429
338	\N	20	ryoken	2021-06-23 23:43:08.431133
339	\N	1	ryoken	2021-06-23 23:43:41.097042
340	\N	9	ryoken	2021-06-23 23:43:58.406027
341	\N	3	ryoken	2021-06-23 23:44:04.115373
342	\N	10	ryoken	2021-06-23 23:44:34.507406
343	\N	11	ryoken	2021-06-23 23:44:47.967339
344	\N	19	ryoken	2021-06-23 23:44:54.408554
345	\N	24	ryoken	2021-06-23 23:45:11.376924
346	\N	23	ryoken	2021-06-23 23:45:22.643851
347	\N	29	ryoken	2021-06-23 23:45:38.902328
348	\N	30	ryoken	2021-06-23 23:45:55.702315
349	\N	33	ryoken	2021-06-23 23:46:10.870025
350	\N	25	ryoken	2021-06-23 23:46:22.343605
351	\N	26	ryoken	2021-06-23 23:46:38.645022
352	\N	13	ryoken	2021-06-23 23:46:48.481363
353	\N	13	ryoken	2021-06-23 23:46:55.853326
354	\N	4	ryoken	2021-06-23 23:47:14.623816
355	\N	2	km8	2021-06-24 09:26:20.748683
356	\N	24	ssw2	2021-06-24 09:31:00.062672
357	\N	21	wrxsti	2021-06-24 10:33:18.891616
358	\N	23	wrxsti	2021-06-24 10:34:53.049587
359	\N	2	unko	2021-06-24 11:43:04.349447
360	\N	21	Naoki	2021-06-24 17:59:31.208746
361	\N	5	hitora-	2021-06-24 19:02:57.386199
362	\N	24	hitora-	2021-06-24 19:04:20.000672
363	\N	32	hitora-	2021-06-24 19:04:44.141968
364	\N	28	hitora-	2021-06-24 19:04:52.792831
365	\N	28	hitora-	2021-06-24 19:04:54.051145
366	\N	28	hitora-	2021-06-24 19:04:59.428006
367	\N	28	hitora-	2021-06-24 19:05:01.81171
368	\N	28	hitora-	2021-06-24 19:05:03.820059
369	\N	28	hitora-	2021-06-24 19:05:06.010911
370	\N	26	hitora-	2021-06-24 19:05:24.753137
371	\N	26	hitora-	2021-06-24 19:05:27.221325
372	\N	26	hitora-	2021-06-24 19:05:29.512381
373	\N	26	hitora-	2021-06-24 19:05:31.546724
374	\N	26	hitora-	2021-06-24 19:05:33.163774
375	\N	26	hitora-	2021-06-24 19:05:37.21402
376	\N	26	hitora-	2021-06-24 19:05:39.380407
377	\N	26	hitora-	2021-06-24 19:05:41.438956
378	\N	26	hitora-	2021-06-24 19:05:43.92129
379	\N	26	hitora-	2021-06-24 19:05:45.931041
380	\N	26	hitora-	2021-06-24 19:05:48.171243
381	\N	26	hitora-	2021-06-24 19:05:50.864518
382	\N	26	hitora-	2021-06-24 19:05:53.072068
383	\N	26	hitora-	2021-06-24 19:05:55.190532
384	\N	26	hitora-	2021-06-24 19:05:57.623313
385	\N	30	ymsho	2021-06-24 20:10:48.483318
386	\N	23	ymsho	2021-06-24 20:11:05.659231
387	\N	24	ymsho	2021-06-24 20:11:17.042615
388	\N	14	ymsho	2021-06-24 20:11:33.184944
389	\N	18	ymsho	2021-06-24 20:11:46.993401
390	\N	5	ymsho	2021-06-24 20:12:04.670332
391	\N	30	K.T	2021-06-24 23:12:20.608099
392	\N	20	ryoken	2021-06-25 00:18:01.154677
393	\N	1	ryoken	2021-06-25 00:19:13.544506
394	\N	1	ryoken	2021-06-25 00:19:19.196127
395	\N	5	sho	2021-06-25 15:55:54.453877
396	\N	23	Kikuyu	2021-06-25 17:48:59.122984
397	\N	14	Kikuyu	2021-06-25 17:49:16.748735
398	\N	13	Kikuyu	2021-06-25 17:49:36.848213
399	\N	13	Kikuyu	2021-06-25 17:49:43.000037
400	\N	13	Kikuyu	2021-06-25 17:49:45.775768
401	\N	20	dai77k	2021-06-25 19:27:03.162158
402	\N	34	Tano	2021-06-25 20:34:59.883968
403	\N	34	km8	2021-06-25 21:59:37.0282
404	\N	23	Kikuyu	2021-06-25 23:42:01.722498
405	\N	23	Kikuyu	2021-06-25 23:42:10.165684
406	\N	26	55	2021-06-26 00:08:21.404613
407	\N	26	55	2021-06-26 00:08:25.645378
408	\N	18	55	2021-06-26 00:09:21.580932
409	\N	18	55	2021-06-26 00:09:29.781377
410	\N	17	55	2021-06-26 00:09:42.86416
411	\N	34	55	2021-06-26 00:10:14.719881
412	\N	28	55	2021-06-26 00:10:43.129834
413	\N	10	55	2021-06-26 00:10:49.363139
414	\N	35	55	2021-06-26 00:15:04.663777
415	\N	35	55	2021-06-26 00:15:12.272565
416	\N	33	55	2021-06-26 00:16:00.099529
417	\N	34	K.T	2021-06-26 14:57:46.855946
418	\N	34	ren	2021-06-26 19:42:27.401182
419	\N	34	Naoki	2021-06-27 12:33:00.136474
420	\N	25	Leo	2021-06-27 22:29:28.37221
421	\N	27	Leo	2021-06-27 22:29:47.381999
422	\N	27	Leo	2021-06-27 22:29:49.382676
423	\N	7	meyu	2021-06-27 23:18:47.784064
424	\N	7	meyu	2021-06-27 23:18:50.459474
425	\N	1	meyu	2021-06-27 23:18:53.319287
426	\N	12	meyu	2021-06-27 23:19:03.688967
427	\N	28	meyu	2021-06-27 23:19:07.455801
428	\N	11	meyu	2021-06-27 23:19:12.614635
429	\N	19	meyu	2021-06-27 23:19:16.048284
430	\N	23	meyu	2021-06-27 23:19:25.291695
431	\N	24	tomi	2021-06-27 23:55:38.319339
432	\N	24	tomi	2021-06-27 23:55:41.828262
433	\N	31	tomi	2021-06-27 23:56:01.819842
434	\N	1	ponsuke	2021-06-28 08:52:13.282432
435	\N	3	ponsuke	2021-06-28 08:52:34.789591
436	\N	3	ponsuke	2021-06-28 08:52:37.460866
437	\N	4	ponsuke	2021-06-28 08:52:58.488738
438	\N	8	ponsuke	2021-06-28 08:53:16.499787
439	\N	19	ponsuke	2021-06-28 08:53:52.991555
440	\N	10	ponsuke	2021-06-28 08:54:00.783264
441	\N	35	ponsuke	2021-06-28 08:54:13.251976
442	\N	28	ponsuke	2021-06-28 08:54:30.445392
443	\N	18	ponsuke	2021-06-28 08:55:03.147879
444	\N	23	ponsuke	2021-06-28 08:56:36.113999
445	\N	33	ponsuke	2021-06-28 08:56:48.354773
446	\N	26	ponsuke	2021-06-28 08:57:09.665536
447	\N	13	gyugyu	2021-06-28 12:00:03.974795
448	\N	17	gyugyu	2021-06-28 12:01:12.674309
449	\N	23	gyugyu	2021-06-28 12:02:40.912822
450	\N	33	gyugyu	2021-06-28 12:03:37.858654
451	\N	36	gyugyu	2021-06-28 12:06:41.350691
452	\N	36	gyugyu	2021-06-28 12:06:45.79282
\.


--
-- Data for Name: questions; Type: TABLE DATA; Schema: public; Owner: qa
--

COPY public.questions (id, q, nick, ts) FROM stdin;
1	メモ帳を上書き保存してもindexに反映されないのですがどうすれば反映されますか。	hkimura	2021-06-18 21:18:37.406584
2	画像のやつ<p><img src='images/hello.png></p>であってますか？	hkimura	2021-06-18 21:18:48.978895
3	リンクののコードはこれで合ってますか？<p><a herf='http://tp.melt.kyutech.ac.jp'>arimura!</a></p>	hkimura	2021-06-18 21:19:17.539273
4	他の人やつ見れます…。	hkimura	2021-06-18 21:20:32.126951
5	タイピングテスト、155点ってすごいですね。さっきの泣きマークはそれです。	hkimura	2021-06-18 21:21:13.564099
6	<a href＝'http://tp.melt.kyutech.ac.jp'>typing</a> がタイピングに飛びません。	hkimura	2021-06-18 21:36:30.388773
7	好きな食べ物はなんですか？	kenshin	2021-06-23 09:14:50.467561
8	眠気の覚まし方とは？	Kikuyu	2021-06-23 10:20:05.356283
9	タイピングで、ニックネームを変更したら練習記録がリセットされることはありますか？	ra	2021-06-23 10:22:18.095645
10		rio	2021-06-23 10:40:25.549967
11	今日も頑張ります	hiroshi	2021-06-23 10:51:19.024379
12	一日一回15分のタイピング練習と一日三回各5分のタイピング練習ではどちらが効果的でしょうか？	ryoken	2021-06-23 10:53:44.163421
13	Back Space 以外でスコアがマイナスになる事例はありますか	wrxsti	2021-06-23 11:42:18.200019
14	自分のホームページに載せられるのは自分で描いた絵のみですか？	LapiS	2021-06-23 11:54:07.255717
15	山口ひろしげ君をあんどうひろしげと呼んでいるのは江戸時代の偉人である安藤広重（歌川広重）から取っているんですか？	Repay	2021-06-23 11:57:37.006668
16	このQAでGoodを押すときってどこをクリックすればいいんですか？	Leo	2021-06-23 12:07:05.773747
17	gtypistはどのレッスンまでするとタイピングテストの範囲をすべてカバーできますか	ren	2021-06-23 12:08:07.218898
18	ホームページを作る時、自分が撮った写真は使っても良いですか？	ryoya	2021-06-23 12:09:35.959871
19	アナログで描いたイラストを取り込んで、ホームページに使うことは可能ですか	Katsuo	2021-06-23 12:09:58.886079
20	僕たちが使っているPCで韓国語（ハングル）をタイプすることは可能ですか？	matsuo	2021-06-23 12:11:35.852331
21	6/23日の鬼滅の刃の絵を描く課題は、絵が下手でも成績に影響しませんか？	natsuki	2021-06-23 12:11:37.670158
22	疲れの取り方は？	Kikuyu	2021-06-25 17:51:24.901111
23	タイピングの文章で、コンマやピリオドの前にはスペースを打たないのが正しいですか？	tomi	2021-06-27 23:55:28.479578
24	タイピングの練習を10分以上しすぎるのはよくないですか？	gyugyu	2021-06-28 12:09:23.139623
25	ホームページ作成の課題は、日本国憲法のプログラムを参考にしつつ、わからないところは適宜調べて進める感じですか。	pen	2021-06-28 12:17:40.685988
\.


--
-- Name: answers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: qa
--

SELECT pg_catalog.setval('public.answers_id_seq', 36, true);


--
-- Name: goods_id_seq; Type: SEQUENCE SET; Schema: public; Owner: qa
--

SELECT pg_catalog.setval('public.goods_id_seq', 452, true);


--
-- Name: questions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: qa
--

SELECT pg_catalog.setval('public.questions_id_seq', 25, true);


--
-- Name: answers answers_pkey; Type: CONSTRAINT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_pkey PRIMARY KEY (id);


--
-- Name: goods goods_pkey; Type: CONSTRAINT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_pkey PRIMARY KEY (id);


--
-- Name: questions questions_pkey; Type: CONSTRAINT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (id);


--
-- Name: answers answers_q_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_q_id_fkey FOREIGN KEY (q_id) REFERENCES public.questions(id);


--
-- Name: goods goods_a_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_a_id_fkey FOREIGN KEY (a_id) REFERENCES public.answers(id);


--
-- Name: goods goods_q_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: qa
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_q_id_fkey FOREIGN KEY (q_id) REFERENCES public.questions(id);


--
-- PostgreSQL database dump complete
--

