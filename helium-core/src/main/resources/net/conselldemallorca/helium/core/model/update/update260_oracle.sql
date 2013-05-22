-- Taules per a execució massiva
CREATE TABLE HEL_EXEC_MASSIVA (
	ID NUMBER(19) NOT NULL,
	DATA_FI DATE,
	DATA_INICI DATE NOT NULL,
	PARAM1 VARCHAR2(255),
	TIPUS NUMBER(10) NOT NULL,
	USUARI VARCHAR2(64) NOT NULL,
	EXPEDIENT_TIPUS_ID NUMBER(19) NOT NULL,
	ENV_CORREU NUMBER(1),
	PARAM2 BLOB,
	ENTORN NUMBER(19),
	PRIMARY KEY (ID),
	CONSTRAINT HEL_EXPTIPUS_EXEMAS_FK FOREIGN KEY (EXPEDIENT_TIPUS_ID) REFERENCES HEL_EXPEDIENT_TIPUS (ID)
);
    
CREATE TABLE HEL_EXEC_MASEXP (
	ID NUMBER(19) NOT NULL,
	DATA_FI DATE,
	DATA_INICI DATE,
	ERROR CLOB,
	ORDRE NUMBER(10) NOT NULL,
	EXECMAS_ID NUMBER(19) NOT NULL,
	EXPEDIENT_ID NUMBER(19) NOT NULL,
	ESTAT NUMBER(10),
	TASCA_ID VARCHAR2(255),
	PRIMARY KEY (ID),
	CONSTRAINT HEL_EXPEDIENT_EXEMASEX_FK FOREIGN KEY (EXPEDIENT_ID) REFERENCES HEL_EXPEDIENT (ID),
	CONSTRAINT HEL_EXECMAS_EXEMASEX_FK FOREIGN KEY (EXECMAS_ID) REFERENCES HEL_EXEC_MASSIVA (ID)
);

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
    '2.6.0' CODI,
    260 ORDRE,
    SYSDATE DATA_CREACIO,
    0 PROCES_EXECUTAT,
    1 SCRIPT_EXECUTAT,
    SYSDATE DATA_EXECUCIO_SCRIPT
FROM DUAL
WHERE (SELECT COUNT(*) FROM HEL_VERSIO WHERE ORDRE = 260) = 0;

--Nous indexos pel millorar el rendiment a la consulta dels camps de la definició de procés
CREATE INDEX
    HEL_CAMP_CODI_TIP
ON
    HEL_CAMP
    (
        CODI,
        TIPUS
    );

CREATE INDEX
    HEL_CAMP_COD_TIP_DP
ON
    HEL_CAMP
    (
        CODI,
        TIPUS,
        DEFINICIO_PROCES_ID
    );

