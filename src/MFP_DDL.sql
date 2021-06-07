-- References: https://www.postgresql.org/docs/9.1/plpgsql-trigger.html
--			   https://stackoverflow.com/questions/18114458/fastest-way-to-determine-if-record-exists

----------------------------------------------- FISHERS -----------------------------------------------

CREATE TABLE IF NOT EXISTS Fishers
(
	fisher_id SERIAL NOT NULL PRIMARY KEY,
	username varchar(30) NOT NULL,
	password varchar(30) NOT NULL,
	fname varchar(50) NOT NULL,
	lname varchar(50) NOT NULL,
	mobile_no varchar(50) NOT NULL
);

-- Unique params
ALTER TABLE Fishers ADD CONSTRAINT unq_Fishers_Username UNIQUE (username);
ALTER TABLE Fishers ADD CONSTRAINT unq_Fishers_MobNo UNIQUE (mobile_no);

-- Check username does not exist in Intermediaries before inserting into Fishers
CREATE OR REPLACE FUNCTION check_username_fisher_trg()
RETURNS TRIGGER AS $$
BEGIN
		IF EXISTS (SELECT * FROM Intermediaries WHERE username = NEW.username) THEN
			RAISE EXCEPTION 'Username % already exists in Intermediaries', NEW.username;
		END IF;
		
		RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER check_username_fisher BEFORE INSERT ON Fishers FOR EACH ROW EXECUTE PROCEDURE check_username_fisher_trg();

-- Check mobile number does not exist in Intermediaries before inserting into Fishers
CREATE OR REPLACE FUNCTION check_mobile_no_fisher_trg()
RETURNS TRIGGER AS $$
BEGIN
		IF EXISTS (SELECT * FROM Intermediaries WHERE mobile_no = NEW.mobile_no) THEN
			RAISE EXCEPTION 'Mobile number % already exists in Intermediaries', NEW.mobile_no;
		END IF;
		
		RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER check_mobile_no_fisher BEFORE INSERT ON Fishers FOR EACH ROW EXECUTE PROCEDURE check_mobile_no_fisher_trg();


----------------------------------------------- INTERMEDIARIES -----------------------------------------------

CREATE TABLE IF NOT EXISTS Intermediaries
(
	intermediary_id SERIAL NOT NULL PRIMARY KEY,
	username varchar(30) NOT NULL,
	password varchar(30) NOT NULL,
	fname varchar(50) NOT NULL,
	lname varchar(50) NOT NULL,
	mobile_no varchar(50) NOT NULL
);

-- Unique params
ALTER TABLE Intermediaries ADD CONSTRAINT unq_Intermediaries_Username UNIQUE (username);
ALTER TABLE Intermediaries ADD CONSTRAINT unq_Intermediaries_MobNo UNIQUE (mobile_no);

-- Check username does not exist in Fishers before inserting into Intermediaries
CREATE OR REPLACE FUNCTION check_username_intermediary_trg()
RETURNS TRIGGER AS $$
BEGIN
		IF EXISTS (SELECT * FROM Fishers WHERE username = NEW.username) THEN
			RAISE EXCEPTION 'Username % already exists in Fishers', NEW.username;
		END IF;
		
		RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER check_username_intermediary BEFORE INSERT ON Fishers FOR EACH ROW EXECUTE PROCEDURE check_username_intermediary_trg();

-- Check mobile number does not exist in Fishers before inserting into Intermediaries
CREATE OR REPLACE FUNCTION check_mobile_no_intermediary_trg()
RETURNS TRIGGER AS $$
BEGIN
		IF EXISTS (SELECT * FROM Fishers WHERE mobile_no = NEW.mobile_no) THEN
			RAISE EXCEPTION 'Mobile number % already exists in Fishers', NEW.mobile_no;
		END IF;
		
		RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER check_mobile_no_intermediary BEFORE INSERT ON Fishers FOR EACH ROW EXECUTE PROCEDURE check_mobile_no_intermediary_trg();

----------------------------------------------- JOB -----------------------------------------------

CREATE TABLE IF NOT EXISTS Jobs
(
	job_id SERIAL NOT NULL PRIMARY KEY,
	fish_type varchar(30) NOT NULL,
	amount_kg int NOT NULL,
	pay_per_kg decimal NOT NULL,
	date_created date NOT NULL DEFAULT CURRENT_DATE,
	date_due date NOT NULL,
	description varchar(256),
	is_completed boolean NOT NULL DEFAULT FALSE
);

-- Valid input checks
ALTER TABLE Jobs ADD CONSTRAINT chk_amountKg CHECK (amount_kg > 0);
ALTER TABLE Jobs ADD CONSTRAINT chk_payPerKg CHECK (pay_per_kg > 0);

----------------------------------------------- FISHERS + INTERMEDIARIES + JOBS -----------------------------------------------

CREATE TABLE IF NOT EXISTS Fishers_Inters_Jobs
(
	job_id int NOT NULL,
	intermediary_id int NOT NULL,
	fisher_id int
);

-- Primary key
ALTER TABLE Fishers_Inters_Jobs ADD CONSTRAINT pk_FishersIntersJobsID PRIMARY KEY (job_id, intermediary_id);

-- Foreign keys
ALTER TABLE Fishers_Inters_Jobs ADD CONSTRAINT fk_JobID FOREIGN KEY (job_id) REFERENCES Jobs(job_id);
ALTER TABLE Fishers_Inters_Jobs ADD CONSTRAINT fk_InterID FOREIGN KEY (intermediary_id) REFERENCES Intermediaries(intermediary_id);
ALTER TABLE Fishers_Inters_Jobs ADD CONSTRAINT fk_FisherID FOREIGN KEY (fisher_id) REFERENCES Fishers(fisher_id);

