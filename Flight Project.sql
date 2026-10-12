create database flight_booking;

use flight_booking;

create table booking_flight (id int primary key auto_increment, passenger_name varchar(100) not null, flight_number varchar(50) not null,
    source varchar(100) not null, destination varchar(100) not null, travel_date date not null, seat_number varchar(20) not null);
    
show tables;    

desc booking_flight;

select * from booking_flight;