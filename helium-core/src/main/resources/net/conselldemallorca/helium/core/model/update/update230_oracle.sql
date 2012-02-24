-- Tramitació massiva --
ALTER TABLE HEL_TASCA
 ADD (TRAM_MASSIVA NUMBER(1) DEFAULT 0 NOT NULL);
UPDATE HEL_TASCA SET TRAM_MASSIVA = 0 WHERE TRAM_MASSIVA IS NULL;

-- Nous paràmetres pels documents
ALTER TABLE HEL_DOCUMENT
 ADD (CONVERTIR_EXT VARCHAR2(10 CHAR));
ALTER TABLE HEL_DOCUMENT
 ADD (EXTENSIONS_PERMESES VARCHAR2(255 CHAR));

-- Nous camps per autenticació en l'accés als WS
ALTER TABLE HEL_DOMINI
 ADD (TIPUS_AUTH NUMBER(10));
ALTER TABLE HEL_DOMINI
 ADD (ORIGEN_CREDS NUMBER(10));
ALTER TABLE HEL_DOMINI
 ADD (USUARI VARCHAR2(255 CHAR));
ALTER TABLE HEL_DOMINI
 ADD (CONTRASENYA VARCHAR2(255 CHAR));

-- Carrecs JBPM_ID depenents del grup
ALTER TABLE HEL_CARREC_JBPMID
 ADD (GRUP VARCHAR2(64 CHAR));

-- Nova taula hel_area_jbpmid
CREATE TABLE HEL_AREA_JBPMID(
	ID NUMBER(19) NOT NULL,
	CODI VARCHAR2(64 CHAR) NOT NULL,
	NOM VARCHAR2(255 CHAR) NOT NULL,
	DESCRIPCIO VARCHAR2(255 CHAR),
	PRIMARY KEY (ID));

-- Nous camps per a les accions
ALTER TABLE HEL_ACCIO
 ADD (PUBLICA NUMBER(1) DEFAULT 0 NOT NULL);
ALTER TABLE HEL_ACCIO
 ADD (OCULTA NUMBER(1) DEFAULT 0 NOT NULL);
ALTER TABLE HEL_ACCIO
 ADD (CRON VARCHAR2(255 CHAR));

-- Actualització a la nova versió --
INSERT INTO HEL_VERSIO (
    ID,
    CODI,
    ORDRE,
    DATA_CREACIO,
    PROCES_EXECUTAT,
    SCRIPT_EXECUTAT,
    DATA_EXECUCIO_SCRIPT)
SELECT
    HIBERNATE_SEQUENCE.NEXTVAL ID,
    '2.3.0' CODI,
    230 ORDRE,
    SYSDATE DATA_CREACIO,
    0 PROCES_EXECUTAT,
    1 SCRIPT_EXECUTAT,
    SYSDATE DATA_EXECUCIO_SCRIPT
FROM DUAL
WHERE (SELECT COUNT(*) FROM HEL_VERSIO WHERE ORDRE = 230) = 0;
