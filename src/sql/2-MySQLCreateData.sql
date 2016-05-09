-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "pojo" database.
-------------------------------------------------------------------------------

-- Insercion de usuarios
--admin/admin
INSERT INTO UserProfile (loginName,enPassword,firstName,lastName,email,dni,birthdate,typeuser) VALUES ("admin","SXzKIBuZJ2zBU","admin","admin","admin@udc.es","12344123M",'2000-04-20 16:43:36',0);
--ven/ven
INSERT INTO UserProfile (loginName,enPassword,firstName,lastName,email,dni,birthdate,typeuser) VALUES ("ven","LLbhfdNqrzYMw","ven","ven","ven@udc.es","87344123H",'1986-04-20 16:43:36',1);
--user/user
INSERT INTO UserProfile (loginName,enPassword,firstName,lastName,email,dni,birthdate,typeuser) VALUES ("user","YPb3siGrJsDX6","user","user","user@udc.es","44444123G",'1992-04-20 16:43:36',2);


insert into Station (address, city, stationName) values ("Calle mayor", "La Coruña", "Coruna");
insert into Station (address, city, stationName) values ("Calle menor", "Ourense", "Ourense");
insert into Station (address, city, stationName) values ("Calle real", "Madrid", "Madrid-ATocha");
insert into Station (address, city, stationName) values ("Calle de españa", "Ferrol", "Ferrol");
insert into Station (address, city, stationName) values ("Calle", "Santiago de Compostela", "Santiago");

insert into Train (trainName, trainType) values ("A23", 1);
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Coruña-Ourense", 1);
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Madrid-Coruña", 1);
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Madrid-Coruña", 1);
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Madrid-Coruña", 1);

insert into Days(routeId,day) values (1,1);
insert into Days(routeId,day) values (2,1);
insert into Days(routeId,day) values (3,1);
insert into Days(routeId,day) values (4,1);

insert into Stop ( departTime, routeId, stationId) values ( 25200000, 1, 1);
insert into Stop (arrivalTime, departTime, routeId, stationId) values (26800000, 26900000, 1, 5);
insert into Stop (arrivalTime, routeId, stationId) values (28800000, 1, 2);

insert into Stop ( departTime, routeId, stationId) values ( 25200000, 2, 3);
insert into Stop (arrivalTime, routeId, stationId) values (28800000, 2, 1);

insert into Stop ( departTime, routeId, stationId) values ( 35200000, 3, 3);
insert into Stop (arrivalTime, routeId, stationId) values (48800000, 3, 1);

insert into Stop ( departTime, routeId, stationId) values ( 55200000, 4, 3);
insert into Stop (arrivalTime, routeId, stationId) values (68800000, 4, 1);



insert into Fare(fareName,description, discount, typeFare) values("Normal adulto","Precio estandar para personas adultas",0,"tipoA");
insert into Fare(fareName,description, discount, typeFare) values("Preferente","Precio epreferente",25,"clase");
insert into Fare(fareName,description, discount, typeFare) values("Turista","Precio turista",0,"clase");

insert into Fare(fareName,description, discount, typeFare) values("Familia numerosa general","Descuento familia numerosa",50,"familia");
insert into Fare(fareName,description, discount, typeFare) values("Familia numerosa especial","Descuento familia numerosa",70,"familia");

insert into Car(capacity,carType,trainId, carNum) values (20,1,1,1);
insert into Car(capacity,carType,trainId, carNum) values (20,0,1,2);