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

----------------------------------------------- JOB -----------------------------------------------

CREATE TABLE IF NOT EXISTS Jobs
(
	job_id SERIAL NOT NULL PRIMARY KEY,
	fish_type varchar(30) NOT NULL,
	amount_kg int NOT NULL,
	pay_per_kg decimal NOT NULL,
	date_created date NOT NULL DEFAULT CURRENT_DATE,
	date_due date NOT NULL,
	is_completed boolean NOT NULL DEFAULT FALSE
);

-- Valid input checks
ALTER TABLE Jobs ADD CONSTRAINT chk_amountKg CHECK (amount_kg > 0);
ALTER TABLE Jobs ADD CONSTRAINT chk_payPerKg CHECK (pay_per_kg > 0);

----------------------------------------------- FISHERS + INTERMEDIARIES + JOBS -----------------------------------------------

CREATE TABLE IF NOT EXISTS Fishers_Inters_Jobs
(
	job_id SERIAL NOT NULL,
	intermediary_id SERIAL NOT NULL,
	fisher_id SERIAL
);

-- Primary key
ALTER TABLE Fisher_Inters_Jobs ADD CONSTRAINT pk_FishersIntersJobsID PRIMARY KEY (job_id, intermediary_id, fisher_id);

-- Foreign keys
ALTER TABLE Fisher_Inters_Jobs ADD CONSTRAINT fk_JobID FOREIGN KEY (job_id) REFERENCES Jobs(job_id);
ALTER TABLE Fisher_Inters_Jobs ADD CONSTRAINT fk_InterID FOREIGN KEY (intermediary_id) REFERENCES Intermediaries(intermediary_id);
ALTER TABLE Fisher_Inters_Jobs ADD CONSTRAINT fk_FisherID FOREIGN KEY (fisher_id) REFERENCES Fishers(fisher_id);

