-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "pojo" database.
-------------------------------------------------------------------------------

insert into Station (adress, city, stationName) values ("Calle mayor", "La Coruña", "Coruña");
insert into Station (adress, city, stationName) values ("Calle menor", "Ourense", "Ourense");
insert into Station (adress, city, stationName) values ("Calle real", "Madrid", "Madrid-ATocha");
insert into Station (adress, city, stationName) values ("Calle de españa", "Ferrol", "Ferrol");
insert into Station (adress, city, stationName) values ("Calle", "Santiago de Compostela", "Santiago");

insert into Train (trainName, trainType) values ("A23", "AVE");
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Coruña-Ourense", 1);
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Madrid-Coruña", 1);
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Madrid-Coruña", 1);
insert into Route (routeDescription, routeName, trainId) values ("Con paradas", "Madrid-Coruña", 1);


insert into Stop ( departTime, routeId, stationId) values ( 25200000, 1, 1);
insert into Stop (arrivalTime, departTime, routeId, stationId) values (26800000, 26900000, 1, 5);
insert into Stop (arrivalTime, routeId, stationId) values (28800000, 1, 2);

insert into Stop ( departTime, routeId, stationId) values ( 25200000, 2, 3);
insert into Stop (arrivalTime, routeId, stationId) values (28800000, 2, 1);

insert into Stop ( departTime, routeId, stationId) values ( 35200000, 3, 3);
insert into Stop (arrivalTime, routeId, stationId) values (48800000, 3, 1);

insert into Stop ( departTime, routeId, stationId) values ( 55200000, 4, 3);
insert into Stop (arrivalTime, routeId, stationId) values (68800000, 4, 1);