-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

DROP TABLE Ticket;

DROP TABLE Purchase;


DROP TABLE Car;
DROP TABLE Stop;
DROP TABLE Route;
DROP TABLE Train;




DROP TABLE Station;

DROP TABLE Fare;
DROP TABLE Passenger;
DROP TABLE UserProfile;

-- ------------------------------ UserProfile ----------------------------------

CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL, 
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(40) NOT NULL, 
    email VARCHAR(60) NOT NULL,
	dni VARCHAR(30) NOT NULL,
	birthdate datetime,
	typeuser tinyint,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName)) 
    ENGINE = InnoDB;

CREATE INDEX UserProfileIndexByLoginName ON UserProfile (loginName);

-- ------------------------------ Train ----------------------------------

CREATE TABLE Train (
    trainId BIGINT NOT NULL AUTO_INCREMENT,
	trainName VARCHAR(30)  NOT NULL,
	trainType VARCHAR(100) NOT NULL,
	CONSTRAINT TrainNameUniqueKey UNIQUE (TrainName),
    CONSTRAINT Train_PK PRIMARY KEY (TrainId))
    ENGINE = InnoDB;			
	

-- ------------------------------ Car ----------------------------------

CREATE TABLE Car (
    carId BIGINT NOT NULL AUTO_INCREMENT,
    capacity INTEGER,
	carType VARCHAR(30),
	carNum INTEGER NOT NULL,
	trainId BIGINT NOT NULL,
    CONSTRAINT Car_PK PRIMARY KEY (carId),
    CONSTRAINT Car_Train_FK FOREIGN KEY (trainId) REFERENCES Train(trainId))
    ENGINE = InnoDB;
	
-- ------------------------------ Passenger ----------------------------------

CREATE TABLE Passenger (
    passengerId BIGINT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(40) NOT NULL,
	email VARCHAR(60) NOT NULL,
	dni VARCHAR(30) NOT NULL,
	age INTEGER,
    CONSTRAINT Passenger_PK PRIMARY KEY (passengerId))
    ENGINE = InnoDB;

-- ------------------------------ Purchase ----------------------------------

CREATE TABLE Purchase (
    purchaseId BIGINT NOT NULL AUTO_INCREMENT,
	purchaseDate TIMESTAMP NOT NULL,
	paymentMethod TINYINT NOT NULL,
	usrId BIGINT NOT NULL,
    CONSTRAINT Purchase_PK PRIMARY KEY (purchaseId),
	CONSTRAINT Purchase_User_FK FOREIGN KEY (usrId) REFERENCES UserProfile(usrId))
    ENGINE = InnoDB;
	
-- ------------------------------ Route ----------------------------------

CREATE TABLE Route (
    routeId BIGINT NOT NULL AUTO_INCREMENT,
	routeName VARCHAR(30)  NOT NULL,
	routeDescription VARCHAR(100),
	trainId BIGINT NOT NULL,
    CONSTRAINT Route_PK PRIMARY KEY (routeId),
	CONSTRAINT Route_Train_FK FOREIGN KEY (trainId) REFERENCES Train(trainId))
    ENGINE = InnoDB;	
	
-- ------------------------------ Station ----------------------------------

CREATE TABLE Station (
    stationId BIGINT NOT NULL AUTO_INCREMENT,
	stationName VARCHAR(30)  NOT NULL,
	adress VARCHAR(100) NOT NULL,
	city VARCHAR(30) NOT NULL,
	CONSTRAINT StationNameUniqueKey UNIQUE (stationName),
    CONSTRAINT Station_PK PRIMARY KEY (stationId))
    ENGINE = InnoDB;	
	
-- ------------------------------ Stop ----------------------------------

CREATE TABLE Stop (
    stopId BIGINT NOT NULL AUTO_INCREMENT,
    departTime BIGINT,
	arrivalTime BIGINT,
	routeId BIGINT NOT NULL,
	stationId BIGINT NOT NULL,
	CONSTRAINT Stop_Route_FK FOREIGN KEY (routeId) REFERENCES Route(routeId),
	CONSTRAINT Stop_Station_FK FOREIGN KEY (stationId) REFERENCES Station(stationId),	
    CONSTRAINT Stop_PK PRIMARY KEY (stopId))
    ENGINE = InnoDB;	
		

-- ------------------------------ Fare ----------------------------------

CREATE TABLE Fare (
    fareId BIGINT NOT NULL AUTO_INCREMENT,
	fareName VARCHAR(30)  NOT NULL,
	description VARCHAR(150),
	discount INTEGER,
	typeFare VARCHAR(40),
	CONSTRAINT FareNameUniqueKey UNIQUE (fareName),
    CONSTRAINT Fare_PK PRIMARY KEY (fareId))
    ENGINE = InnoDB;		
	
-- ------------------------------ Ticket ----------------------------------

CREATE TABLE Ticket (
    ticketId BIGINT NOT NULL AUTO_INCREMENT,
	realPrice NUMERIC(5,2) NOT NULL,
	seat VARCHAR(10) NOT NULL,
	ticketDate TIMESTAMP NOT NULL,
	purchaseId BIGINT NOT NULL,
	carId BIGINT NOT NULL,	
	passengerId BIGINT NOT NULL,	
	destinationId BIGINT NOT NULL,	
	originId BIGINT NOT NULL,		
	CONSTRAINT Ticket_Purchase_FK FOREIGN KEY (purchaseId) REFERENCES Purchase(PurchaseId),
	CONSTRAINT Ticket_Car_FK FOREIGN KEY (carId) REFERENCES Car(carId),
	CONSTRAINT Ticket_Passenger_FK FOREIGN KEY (passengerId) REFERENCES Passenger(passengerId),
	CONSTRAINT Ticket_Destination_FK FOREIGN KEY (destinationId) REFERENCES Stop(stopId),
	CONSTRAINT Ticket_Origin_FK FOREIGN KEY (originId) REFERENCES Stop(stopId),
    CONSTRAINT Ticket_PK PRIMARY KEY (ticketId))
    ENGINE = InnoDB;	
			