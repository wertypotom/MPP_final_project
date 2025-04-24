create database ExpenseDB;

use ExpenseDB;

create table User (
id int primary key AUTO_INCREMENT,
`name` varchar(150) not null,
email varchar(255) not null unique,
`password` varchar(512) not null,
userType ENUM('Admin', 'User') not null default 'User',
budgetLimit decimal(18,2) 
);

insert into User(`name`,email,`password`,userType) values ('Andrey', 'andrey@miu.edu', '123', 'Admin');
insert into User(`name`,email,`password`,userType) values ('Shaban', 'shaban@miu.edu', '456', 'Admin');
insert into User(`name`,email,`password`,budgetLimit) values ('Dahlia', 'dahlia@miu.edu', '789', 500);

create table Category (
id int primary key AUTO_INCREMENT,
`name` varchar(100) not null,
`description` varchar(255) not null default '',
createdOn timestamp not null default current_timestamp,
modifiedOn timestamp not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
createdUserId int not null,
modifiedUserId int not null,
foreign key (createdUserId) references `User`(id),
foreign key (modifiedUserId) references `User`(id)
);

create table Expense (
id bigint primary key AUTO_INCREMENT,
`name` varchar(100) not null,
`description` varchar(255) not null default '',
amount decimal(18,2) not null,
categoryId int not null,
userId int not null,
createdOn timestamp not null default current_timestamp,
modifiedOn timestamp not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
foreign key (categoryId) references `Category`(id),
foreign key (userId) references `User`(id)
);


