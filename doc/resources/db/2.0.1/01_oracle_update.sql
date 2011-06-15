CREATE TABLE HEL_ACCIO
(
  ID                   NUMBER(19)               NOT NULL,
  CODI                 VARCHAR2(64 CHAR)        NOT NULL,
  DESCRIPCIO           VARCHAR2(255 CHAR),
  JBPM_ACTION          VARCHAR2(255 CHAR)       NOT NULL,
  NOM                  VARCHAR2(255 CHAR)       NOT NULL,
  DEFINICIO_PROCES_ID  NUMBER(19)               NOT NULL
);

ALTER TABLE HEL_CAMP
 ADD (JBPM_ACTION  VARCHAR2(255 CHAR));

CREATE TABLE HEL_REDIR
(
  ID               NUMBER(19)                   NOT NULL,
  DATA_CANCELACIO  TIMESTAMP(6),
  DATA_FI          TIMESTAMP(6)                 NOT NULL,
  DATA_INICI       TIMESTAMP(6)                 NOT NULL,
  USUARI_DESTI     VARCHAR2(255 CHAR)           NOT NULL,
  USUARI_ORIGEN    VARCHAR2(255 CHAR)           NOT NULL
);

ALTER TABLE HEL_ACCIO ADD (
  PRIMARY KEY
 (ID),
  UNIQUE (CODI, DEFINICIO_PROCES_ID));

ALTER TABLE HEL_REDIR ADD (
  PRIMARY KEY
 (ID));

ALTER TABLE HEL_ACCIO
DROP CONSTRAINT HEL_DEFPROC_DOCUMENT_FK;
ALTER TABLE HEL_ACCIO ADD (
  CONSTRAINT HEL_DEFPROC_ACCIO_FK 
 FOREIGN KEY (DEFINICIO_PROCES_ID) 
 REFERENCES HEL_DEFINICIO_PROCES (ID));

ALTER TABLE JBPM_VARIABLEINSTANCE
 MODIFY(STRINGVALUE_ VARCHAR2(2048 CHAR));

ALTER TABLE HEL_EXPEDIENT_TIPUS
 MODIFY(SISTRA_MAPCAMPS VARCHAR2(2048 CHAR));
ALTER TABLE HEL_EXPEDIENT_TIPUS
 MODIFY(SISTRA_MAPDOCS VARCHAR2(2048 CHAR));

ALTER TABLE HEL_EXPEDIENT_TIPUS
 ADD (FORMEXT_URL  VARCHAR2(255 CHAR));
ALTER TABLE HEL_EXPEDIENT_TIPUS
 ADD (FORMEXT_USUARI  VARCHAR2(255 CHAR));
ALTER TABLE HEL_EXPEDIENT_TIPUS
 ADD (FORMEXT_CONTRASENYA  VARCHAR2(255 CHAR));
