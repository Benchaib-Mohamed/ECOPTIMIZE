--
-- Name: students; Type: TABLE; Schema: ezip_ing1
--

/* CREATE TABLE students (
    name varchar(64) NOT NULL,
    firstname varchar(64) NOT NULL,
    groupname varchar(8) NOT NULL,
    id int(20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

INSERT INTO students (name, firstname, groupname) VALUES ("ANONYMOUS", "Stephane", "Admin");
 */
CREATE TABLE aleternatives(
   IdA INT,
   Nom VARCHAR(50),
   Poids INT,
   IG INT,
   Bio BOOLEAN,
   Origine VARCHAR(50),
   PRIMARY KEY(IdA)
);

CREATE TABLE catégorie(
   IdC INT,
   Nom VARCHAR(50),
   PRIMARY KEY(IdC)
);

CREATE TABLE borne(
   IdB INT,
   Emplacement VARCHAR(50),
   PRIMARY KEY(IdB)
);

CREATE TABLE produits(
   IdP INT,
   Nom VARCHAR(50),
   Poids INT,
   IG INT,
   Bio BOOLEAN,
   Origine VARCHAR(50),
   IdC INT,
   IdA INT,
   NbRecherche INT DEFAULT 0,
   EmpreinteC INT ,
   PRIMARY KEY(IdP),
   FOREIGN KEY(IdC) REFERENCES catégorie(IdC),
   FOREIGN KEY(IdA) REFERENCES aleternatives(IdA)
);

CREATE TABLE stat(
   IdP INT,
   IdB INT,
   PRIMARY KEY(IdP, IdB),
   FOREIGN KEY(IdP) REFERENCES produits(IdP),
   FOREIGN KEY(IdB) REFERENCES borne(IdB)
);




