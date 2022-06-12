-- Brose, SS 2022
-- basiert auf: https://db.in.tum.de/teaching/bookDBMSeinf/daten/
--
-- DROP SCHEMA uni CASCADE;

CREATE SCHEMA uni; -- not in MS SQL Server!

CREATE DOMAIN uni.d_semester AS CHAR(7); -- MS SQL server: CREATE TYPE SEMESTER FROM [char](7) NULL

CREATE DOMAIN uni.d_semester_anzahl AS SMALLINT CHECK (VALUE BETWEEN 1 and 16); -- MS SQL server: CREATE TYPE SEMESTER_ZAHL FROM SMALLINT

CREATE TABLE uni.Grundstuecke
(
    id     INTEGER PRIMARY KEY,
    nummer INTEGER,
    name   VARCHAR(20)
);

CREATE TABLE uni.Gebaeude
(
    id             INTEGER PRIMARY KEY,
    gebaeudenummer INTEGER,
    baujahr        SMALLINT,
    name           VARCHAR(20),
    adresse        VARCHAR(255),
    grundstueck    INTEGER REFERENCES uni.Grundstuecke
);

CREATE TABLE uni.Funktionen
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(20)
);

CREATE TABLE uni.Gebauede_Funktionen
(
    gebaeude INTEGER REFERENCES uni.Gebaeude,
    funktion INTEGER REFERENCES uni.Funktionen,
    PRIMARY KEY (gebaeude, funktion)
);

CREATE TABLE uni.Stockwerke
(
    id             INTEGER PRIMARY KEY,
    geschossnummer INTEGER,
    gebaeude       INTEGER REFERENCES uni.Gebaeude
);

CREATE TABLE uni.Eingaenge
(
    id       INTEGER PRIMARY KEY,
    name     VARCHAR(20),
    gebaeude INTEGER REFERENCES uni.Gebaeude
);


CREATE TABLE uni.Freiflaechen
(
    id      INTEGER PRIMARY KEY,
    flaeche DECIMAL,
    name    VARCHAR(20)
);

CREATE TABLE uni.Innenflaechen
(
    id       INTEGER PRIMARY KEY,
    gebaeude INTEGER REFERENCES uni.Gebaeude
);

CREATE TABLE uni.Aussenflaechen
(
    id          INTEGER PRIMARY KEY,
    grundstueck INTEGER REFERENCES uni.Grundstuecke
);

CREATE TABLE uni.Sportanlagen
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Parkplaetze
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Gruenanlagen
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Raeume
(
    id         INTEGER PRIMARY KEY,
    name       VARCHAR(20),
    raumnummer VARCHAR(20),
    flaeche    DECIMAL,
    raumhoehe  DECIMAL,
    stockwerk  INTEGER REFERENCES uni.Stockwerke
);

CREATE TABLE uni.Toiletten
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Lagerraeume
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Betriebsraeume
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Arbeitsraeume
(
    id         INTEGER PRIMARY KEY,
    kapazitaet SMALLINT
);

CREATE TABLE uni.Laborraeume
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Bueroraeume
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Seminarraeume
(
    id INTEGER PRIMARY KEY
);

CREATE TABLE uni.Ausstattungen
(
    id               INTEGER PRIMARY KEY,
    name             VARCHAR(20),
    ausstattungstyp  VARCHAR(20),
    inventarnummer   VARCHAR(20),
    beschaffungsjahr SMALLINT,
    raum             INTEGER REFERENCES uni.Arbeitsraeume
);


CREATE TABLE uni.studierende
(
    matr_nr         INTEGER PRIMARY KEY,
    name            VARCHAR(30)    NOT NULL,
    vorname         VARCHAR(30)    NOT NULL,
    geburtsdatum    DATE           NOT NULL,
    geburtsort      VARCHAR(30)    NOT NULL,
    anzahl_semester uni.d_semester_anzahl,
    studienbeginn   uni.d_semester NOT NULL
);

CREATE TABLE uni.professoren
(
    pers_nr INTEGER PRIMARY KEY,
    name    VARCHAR(30) NOT NULL,
    rang    CHAR(2) CHECK (rang in ('C2', 'C3', 'C4')),
    raum    INTEGER     REFERENCES uni.Bueroraeume ON DELETE SET NULL,
    gehalt  INTEGER
);

CREATE TABLE uni.mitarbeiter
(
    pers_nr                 INTEGER PRIMARY KEY,
    name                    VARCHAR(30) NOT NULL,
    fachgebiet              VARCHAR(30),
    gehalt                  INTEGER,
    arbeitet_fuer_professor INTEGER     REFERENCES uni.professoren ON DELETE SET NULL
);

CREATE TABLE uni.lehrveranstaltungen
(
    lv_nr                  INTEGER PRIMARY KEY,
    titel                  VARCHAR(30),
    leistungs_punkte       INTEGER,
    semester               uni.d_semester,
    max_teilnehmer         INTEGER,
    raum                   INTEGER REFERENCES uni.Seminarraeume,
    gehalten_von_professor INTEGER REFERENCES uni.professoren ON DELETE SET NULL
);

CREATE TABLE uni.teilnehmen
(
    matr_nr INTEGER REFERENCES uni.studierende ON DELETE CASCADE,
    lv_nr   INTEGER REFERENCES uni.lehrveranstaltungen ON DELETE CASCADE,
    PRIMARY KEY (matr_nr, lv_nr)
);

CREATE TABLE uni.voraussetzen
(
    vorgaenger INTEGER REFERENCES uni.lehrveranstaltungen ON DELETE CASCADE,
    nachfolger INTEGER REFERENCES uni.lehrveranstaltungen ON DELETE CASCADE,
    PRIMARY KEY (vorgaenger, nachfolger)
);

CREATE TABLE uni.pruefen
(
    matr_nr INTEGER REFERENCES uni.studierende ON DELETE CASCADE,
    lv_nr   INTEGER REFERENCES uni.lehrveranstaltungen,
    pers_nr INTEGER REFERENCES uni.professoren ON DELETE SET NULL,
    note    NUMERIC(2, 1) CHECK (note between 0.7 and 5.0),
    PRIMARY KEY (matr_nr, lv_nr)
);

CREATE VIEW uni.v_Bueros
AS
SELECT b.id,
       r.name,
       r.raumnummer,
       r.flaeche,
       r.raumhoehe as hoehe,
       r.stockwerk as stockwerk_id,
       a.kapazitaet
FROM uni.Raeume r
         JOIN uni.Arbeitsraeume a ON a.id = r.id
         JOIN uni.Bueroraeume b ON b.id = r.id;

CREATE SEQUENCE IF NOT EXISTS uni.id_sequence
    START 1000;