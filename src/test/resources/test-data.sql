INSERT INTO uni.Grundstuecke (id, nummer, name)
VALUES (1, 1, 'WHC-property'),
       (2, 2, 'TA-property');

INSERT into uni.Gebaeude(id, gebaeudenummer, baujahr, name, adresse, grundstueck)
VALUES (1, 1, 1954, 'WH C', 'Wilhelminenhofstraße 75a, Berlin', 1),
       (2, 2, 1950, 'TA', 'Treskowallee 8, Berlin', 1);

INSERT into uni.Funktionen (id, name)
VALUES (1, 'Lehre'),
       (2, 'Verwaltung'),
       (3, 'Betrieb'),
       (4, 'Versorgung');

INSERT INTO uni.Gebauede_Funktionen (gebaeude, funktion)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3);

INSERT INTO uni.Stockwerke (id, geschossnummer, gebaeude)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1),
       (4, 4, 1),
       (5, 5, 1),
       (6, 6, 1);

INSERT INTO uni.Eingaenge (id, name, gebaeude)
VALUES (1, 'A', 1),
       (2, 'B', 1),
       (3, 'C', 1),
       (4, 'D', 1);

INSERT INTO uni.Freiflaechen (id, flaeche, name)
VALUES (1, 47.1, 'parking ground'),
       (2, 24.3, 'lobby 1'),
       (3, 100.2, 'atrium 1'),
       (4, 1224.3, 'park 1');

INSERT INTO uni.Aussenflaechen (id, grundstueck)
VALUES (1, 1),
       (4, 1);

INSERT INTO uni.Innenflaechen (id, gebaeude)
VALUES (2, 1),
       (3, 1);

INSERT INTO uni.Parkplaetze (id)
VALUES (1);

INSERT INTO uni.Gruenanlagen (id)
VALUES (4);

INSERT INTO uni.Raeume (id, name, raumnummer, flaeche, raumhoehe, stockwerk)
VALUES (1, 'Office#1', '675', 33.5, 3.5, 6),
       (2, 'Seminar Room', '348', 33.5, 3.5, 6),
       (3, 'Lab', '576', 200.5, 3.5, 5),
       (4, 'Toilet', '501', 100.5, 3.5, 5),
       (5, 'Storage', '502', 230.1, 3.5, 5),
       (6, 'Network Cabinet', '503', 10.1, 3.5, 5),
       (7, 'Office#2', '678', 44.5, 3.5, 6),
       (8, 'Office#3', '679', 55.5, 3.5, 6),
       (9, 'Office#4', '680', 66.5, 3.5, 6),
       (10, 'Office#5', '681', 77.5, 3.5, 6),
       (11, 'Office#6', '682', 88.5, 3.5, 6),
       (12, 'Lecture Hall#1', '100', 388.5, 4.5, 1),
       (13, 'Lecture Hall#2', '101', 438.5, 4.5, 1);


INSERT INTO uni.Toiletten (id)
VALUES (4);

INSERT INTO uni.Lagerraeume (id)
VALUES (5);

INSERT INTO uni.Betriebsraeume (id)
VALUES (6);

INSERT INTO uni.Arbeitsraeume (id, kapazitaet)
VALUES (1, 2),
       (2, 50),
       (3, 25),
       (7, 50),
       (8, 50),
       (9, 50),
       (10, 25),
       (11, 25),
       (12, 125),
       (13, 75);

INSERT INTO uni.Laborraeume (id)
VALUES (3);

INSERT INTO uni.Bueroraeume (id)
VALUES (1),
       (7),
       (8),
       (9),
       (10),
       (11);

INSERT INTO uni.Seminarraeume (id)
VALUES (2),
       (12),
       (13);

INSERT INTO uni.Ausstattungen (id, name, ausstattungstyp, inventarnummer, beschaffungsjahr, raum)
VALUES (1, 'Beamer Decke', 'Beamer', '1234-aa', 1999, 2),
       (2, 'PC#1', 'PC', '1234-p-1', 2016, 3),
       (3, 'PC#2', 'PC', '1234-p-2', 2016, 3),
       (4, 'Deskchair', 'Chair', '1234-s-1', 2011, 1),
       (5, 'Desk', 'Table', '1234-t-1', 2019, 1),
       (6, 'Shelf#1', 'Shelf', '1234-sh-1', 2019, 1),
       (7, 'Shelf#2', 'Shelf', '1234-sh-2', 2019, 7),
       (8, 'Shelf#3', 'Shelf', '1234-sh-3', 2019, 8),
       (9, 'Shelf#4', 'Shelf', '1234-sh-4', 2019, 9),
       (10, 'Shelf#5', 'Shelf', '1234-sh-5', 2019, 10),
       (11, 'Desk-11', 'Table', '1234-t-11', 2010, 11);

INSERT INTO uni.studierende(matr_nr, name, vorname, geburtsdatum, geburtsort, anzahl_semester, studienbeginn)
VALUES (24002, 'Xenokrates', 'Anon', '1998-01-01', 'Athen', 2, 'WS 2021'),
       (25403, 'Rubens', 'Peter', '1997-01-01', 'Antwerpen', 3, 'SS 2020'),
       (25555, 'Diotima', 'Anon', '2000-01-01', 'Berlin', 6, 'WS 2019'),
       (26120, 'Lovelace', 'Ada', '2000-01-01', 'London', 3, 'SS 2020'),
       (26830, 'Babbage', 'Charles', '1999-01-01', 'Berlin', 4, 'WS 2020'),
       (27550, 'Schopenhauer', 'Friedrich', '2000-01-01', 'Berlin', 4, 'WS 2020'),
       (28106, 'Turing', 'Alan', '2001-01-01', 'London', 4, 'WS 2020'),
       (29120, 'Theophrastos', 'Anon', '2000-01-01', 'Berlin', 4, 'WS 2020'),
       (29555, 'Feuerbach', 'Ludwig', '2000-01-01', 'Berlin', 4, 'WS 2020');

INSERT INTO uni.professoren(pers_nr, name, rang, raum, gehalt)
VALUES (2125, 'Sokrates', 'C4', 1, 58000),
       (2126, 'Russel', 'C4', 7, 60000),
       (2127, 'Kopernikus', 'C3', 11, 60000),
       (2133, 'Popper', 'C3', 11, 60000),
       (2134, 'Augustinus', 'C3', 11, 55000),
       (2136, 'Curie', 'C4', 8, 62000),
       (2137, 'Kant', 'C4', 9, 59000),
       (2138, 'Meitner', 'C4', 10, 65000);

INSERT INTO uni.mitarbeiter(pers_nr, name, fachgebiet, gehalt, arbeitet_fuer_professor)
VALUES (3002, 'Platon', 'Ideenlehre', 45500, 2125),
       (3003, 'Aristoteles', 'Syllogistik', 45500, 2125),
       (3004, 'Wittgenstein', 'Sprachtheorie', 48500, 2126),
       (3005, 'Rhetikus', 'Planetenbewegung', 50000, 2127),
       (3006, 'Newton', 'Keplersche Gesetze', 52500, 2127),
       (3007, 'Spinoza', 'Gott und Natur', 45000, 2134),
       (3008, 'Arendt', 'Totale Herrschaft', 48800, 2126);

INSERT INTO uni.lehrveranstaltungen(lv_nr, titel, leistungs_punkte, max_teilnehmer, gehalten_von_professor)
VALUES (5001, 'Grundzuege', 4, 50, 2137),
       (5041, 'Ethik', 4, 100, 2125),
       (5043, 'Erkenntnistheorie', 3, 20, 2126),
       (5049, 'Maeeutik', 2, 20, 2125),
       (4052, 'Logik', 4, 20, 2125),
       (5052, 'Wissenschaftstheorie', 3, 20, 2126),
       (5216, 'Bioethik', 2, 20, 2126),
       (5259, 'Der Wiener Kreis', 2, 20, 2133),
       (5022, 'Glaube und Wissen', 2, 20, 2134),
       (4630, 'Die 3 Kritiken', 4, 20, 2137);

INSERT INTO uni.teilnehmen(matr_nr, lv_nr)
VALUES (26120, 5001),
       (27550, 5001),
       (27550, 4052),
       (28106, 5041),
       (28106, 5052),
       (28106, 5216),
       (28106, 5259),
       (29120, 5001),
       (29120, 5041),
       (29120, 5049),
       (29555, 5022),
       (25403, 5022),
       (29555, 5001);

INSERT INTO uni.voraussetzen(vorgaenger, nachfolger)
VALUES (5001, 5041),
       (5001, 5043),
       (5001, 5049),
       (5041, 5216),
       (5043, 5052),
       (5041, 5052),
       (5052, 5259);

INSERT INTO uni.pruefen(matr_nr, lv_nr, pers_nr, note)
VALUES (28106, 5001, 2126, 1.0),
       (25403, 5041, 2125, 2.0),
       (27550, 4630, 2137, 2.0);
