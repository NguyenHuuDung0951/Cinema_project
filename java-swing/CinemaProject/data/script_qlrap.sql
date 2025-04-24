--Tạo cơ sở dữ liệu
CREATE DATABASE QLRAP
GO
USE QLRAP
GO

--Tạo các bảng
CREATE TABLE Account (
    accountID VARCHAR(50) PRIMARY KEY,
    username VARCHAR(100),
    password VARCHAR(100),
);

CREATE TABLE Employee (
    employeeID VARCHAR(50) PRIMARY KEY,
    fullName NVARCHAR(100),
    gender BIT,
    dateOfBirth DATE,
    phoneNumber VARCHAR(20),
    email VARCHAR(100),
    accountID VARCHAR(50),
    FOREIGN KEY (accountID) REFERENCES Account(accountID)
);

CREATE TABLE Orders (
    orderID VARCHAR(50) PRIMARY KEY,
    orderDate DATE,
    totalPrice FLOAT,
    employeeID VARCHAR(50),
	voucherID VARCHAR(50),
    FOREIGN KEY (employeeID) REFERENCES Employee(employeeID),
	FOREIGN KEY (voucherID) REFERENCES Voucher(voucherID)
);

CREATE TABLE Product (
    productID VARCHAR(50) PRIMARY KEY,
    productName NVARCHAR(100),
    quantity INT,
    productType VARCHAR(50),
    price FLOAT
);

CREATE TABLE Movie (
    movieID VARCHAR(50) PRIMARY KEY,
    movieName NVARCHAR(100),
	status NVARCHAR(20),
    duration INT
);

CREATE TABLE Room (
    room VARCHAR(50) PRIMARY KEY,
    roomName VARCHAR(100),
    numberOfSeats INT
);

CREATE TABLE SeatType (
    seatTypeID VARCHAR(50) PRIMARY KEY,
    seatTypeName NVARCHAR(50),
    descriptionSeat NVARCHAR(200)
);

CREATE TABLE Seat (
    seatID VARCHAR(50) PRIMARY KEY,
    location VARCHAR(20),
    room VARCHAR(50),
    seatTypeID VARCHAR(50),
    FOREIGN KEY (room) REFERENCES Room(room),
    FOREIGN KEY (seatTypeID) REFERENCES SeatType(seatTypeID)
);

CREATE TABLE MovieSchedule (
    scheduleID VARCHAR(50) PRIMARY KEY,
    movieID VARCHAR(50),
    room VARCHAR(50),
    startTime DATE,
    endTime DATE,
    FOREIGN KEY (movieID) REFERENCES Movie(movieID),
    FOREIGN KEY (room) REFERENCES Room(room)
);

CREATE TABLE MovieScheduleSeat (
    scheduleID VARCHAR(50),
    seatID VARCHAR(50),
    isAvailable BIT DEFAULT 1,
    PRIMARY KEY (scheduleID, seatID),
    FOREIGN KEY (scheduleID) REFERENCES MovieSchedule(scheduleID),
    FOREIGN KEY (seatID) REFERENCES Seat(seatID)
);

CREATE TABLE TicketDetail (
    ticketID VARCHAR(50) PRIMARY KEY,
    movieID VARCHAR(50),
    showDate DATE,
    seatID VARCHAR(50),
    room VARCHAR(50),
    ticketPrice FLOAT,
    FOREIGN KEY (movieID) REFERENCES Movie(movieID),
    FOREIGN KEY (seatID) REFERENCES Seat(seatID),
    FOREIGN KEY (room) REFERENCES Room(room)
);

CREATE TABLE OrderDetail (
    orderDetailID VARCHAR(50) PRIMARY KEY,
    ticketID VARCHAR(50),
    orderID VARCHAR(50),
    productID VARCHAR(50),
    scheduleID VARCHAR(50),
    quantity INT,
    FOREIGN KEY (ticketID) REFERENCES TicketDetail(ticketID),
    FOREIGN KEY (orderID) REFERENCES Orders(orderID),
    FOREIGN KEY (productID) REFERENCES Product(productID),
    FOREIGN KEY (scheduleID) REFERENCES MovieSchedule(scheduleID)
);

--Thực hiện thêm dữ liệu mẫu cho các bảng

-- ACCOUNT
INSERT INTO Account (accountID, username, password)
VALUES 
    ('AC001', 'nhanvien1', '123'),
    ('AC002', 'nhanvien2', '123');

-- EMPLOYEE
INSERT INTO Employee (
    employeeID, fullName, gender, dateOfBirth, phoneNumber, email, accountID
)
VALUES 
    ('EM001', N'Lê Hoàng Khang', 0, '2005-02-26', '0917774020', 'khanglehoang2602@gmail.com', 'AC001'),
    ('EM002', N'Nguyễn Văn Sỹ',  0, '2005-09-06', '0938078300', 'vansy05@gmail.com',         'AC002');

-- PRODUCT
INSERT INTO Product (productID, productName, quantity, productType, price)
VALUES 
    ('P001', N'Bắp rang bơ', 100, 'Đồ ăn', 35000),
    ('P002', N'Nước ngọt Coca-Cola', 120, 'Thức uống', 25000),
    ('P003', N'Combo Bắp nước', 50, 'Combo', 55000),
    ('P004', N'Nước suối Aquafina', 60, 'Thức uống', 15000),
    ('P005', N'Bắp rang bơ size lớn', 40, 'Đồ ăn', 80000);

-- MOVIE 
INSERT INTO Movie (movieID, movieName, status, duration)
VALUES 
    ('M001', N'Avengers: Endgame', N'Đã phát hành', 181),
    ('M002', N'Nhà bà Nữ', N'Đã phát hành', 120),
    ('M003', N'John Wick 4', N'Đã phát hành', 169),
    ('M004', N'Siêu lừa gặp siêu lầy 6', N'Đã phát hành', 115),
    ('M005', N'Spiderman: No Way Home', N'Đã phát hành', 148);

-- ROOM 
INSERT INTO Room (room, roomName, numberOfSeats)
VALUES 
    ('R001', 'Phòng 1', 192),
    ('R002', 'Phòng 2', 192),
    ('R003', 'Phòng 3', 192),
    ('R004', 'Phòng 4', 192),
    ('R005', 'Phòng 5', 192);

-- SEAT TYPE 
INSERT INTO SeatType (seatTypeID, seatTypeName, descriptionSeat)
VALUES 
    ('ST01', N'Thường', N'Ghế tiêu chuẩn'),
    ('ST02', 'VIP', N'Ghế cao cấp'),
    ('ST03', 'Couple', N'Ghế đôi');

-- SEAT 
INSERT INTO Seat (seatID, location, room, seatTypeID) VALUES
('S001', 'A1', 'R001', 'ST01'),
('S002', 'A2', 'R001', 'ST01'),
('S003', 'A3', 'R001', 'ST01'),
('S004', 'A4', 'R001', 'ST01'),
('S005', 'A5', 'R001', 'ST01'),
('S006', 'A6', 'R001', 'ST01'),
('S007', 'A7', 'R001', 'ST01'),
('S008', 'A8', 'R001', 'ST01'),
('S009', 'A9', 'R001', 'ST01'),
('S010', 'A10', 'R001', 'ST01'),
('S011', 'A11', 'R001', 'ST01'),
('S012', 'A12', 'R001', 'ST01'),
('S013', 'A13', 'R001', 'ST01'),
('S014', 'A14', 'R001', 'ST01'),
('S015', 'A15', 'R001', 'ST01'),
('S016', 'A16', 'R001', 'ST01'),

('S017', 'B1', 'R001', 'ST01'),
('S018', 'B2', 'R001', 'ST01'),
('S019', 'B3', 'R001', 'ST01'),
('S020', 'B4', 'R001', 'ST01'),
('S021', 'B5', 'R001', 'ST01'),
('S022', 'B6', 'R001', 'ST01'),
('S023', 'B7', 'R001', 'ST01'),
('S024', 'B8', 'R001', 'ST01'),
('S025', 'B9', 'R001', 'ST01'),
('S026', 'B10', 'R001', 'ST01'),
('S027', 'B11', 'R001', 'ST01'),
('S028', 'B12', 'R001', 'ST01'),
('S029', 'B13', 'R001', 'ST01'),
('S030', 'B14', 'R001', 'ST01'),
('S031', 'B15', 'R001', 'ST01'),
('S032', 'B16', 'R001', 'ST01'),

('S033', 'C1', 'R001', 'ST01'),
('S034', 'C2', 'R001', 'ST01'),
('S035', 'C3', 'R001', 'ST01'),
('S036', 'C4', 'R001', 'ST01'),
('S037', 'C5', 'R001', 'ST01'),
('S038', 'C6', 'R001', 'ST01'),
('S039', 'C7', 'R001', 'ST01'),
('S040', 'C8', 'R001', 'ST01'),
('S041', 'C9', 'R001', 'ST01'),
('S042', 'C10', 'R001', 'ST01'),
('S043', 'C11', 'R001', 'ST01'),
('S044', 'C12', 'R001', 'ST01'),
('S045', 'C13', 'R001', 'ST01'),
('S046', 'C14', 'R001', 'ST01'),
('S047', 'C15', 'R001', 'ST01'),
('S048', 'C16', 'R001', 'ST01'),

('S049', 'D1', 'R001', 'ST01'),
('S050', 'D2', 'R001', 'ST01'),
('S051', 'D3', 'R001', 'ST01'),
('S052', 'D4', 'R001', 'ST01'),
('S053', 'D5', 'R001', 'ST01'),
('S054', 'D6', 'R001', 'ST01'),
('S055', 'D7', 'R001', 'ST01'),
('S056', 'D8', 'R001', 'ST01'),
('S057', 'D9', 'R001', 'ST01'),
('S058', 'D10', 'R001', 'ST01'),
('S059', 'D11', 'R001', 'ST01'),
('S060', 'D12', 'R001', 'ST01'),
('S061', 'D13', 'R001', 'ST01'),
('S062', 'D14', 'R001', 'ST01'),
('S063', 'D15', 'R001', 'ST01'),
('S064', 'D16', 'R001', 'ST01'),

-- Ghế VIP
('S065', 'E1', 'R001', 'ST02'),
('S066', 'E2', 'R001', 'ST02'),
('S067', 'E3', 'R001', 'ST02'),
('S068', 'E4', 'R001', 'ST02'),
('S069', 'E5', 'R001', 'ST02'),
('S070', 'E6', 'R001', 'ST02'),
('S071', 'E7', 'R001', 'ST02'),
('S072', 'E8', 'R001', 'ST02'),
('S073', 'E9', 'R001', 'ST02'),
('S074', 'E10', 'R001', 'ST02'),
('S075', 'E11', 'R001', 'ST02'),
('S076', 'E12', 'R001', 'ST02'),
('S077', 'E13', 'R001', 'ST02'),
('S078', 'E14', 'R001', 'ST02'),
('S079', 'E15', 'R001', 'ST02'),

-- Ghế VIP tiếp theo
('S080', 'F1', 'R001', 'ST02'),
('S081', 'F2', 'R001', 'ST02'),
('S082', 'F3', 'R001', 'ST02'),
('S083', 'F4', 'R001', 'ST02'),
('S084', 'F5', 'R001', 'ST02'),
('S085', 'F6', 'R001', 'ST02'),
('S086', 'F7', 'R001', 'ST02'),
('S087', 'F8', 'R001', 'ST02'),
('S088', 'F9', 'R001', 'ST02'),
('S089', 'F10', 'R001', 'ST02'),
('S090', 'F11', 'R001', 'ST02'),
('S091', 'F12', 'R001', 'ST02'),
('S092', 'F13', 'R001', 'ST02'),
('S093', 'F14', 'R001', 'ST02'),
('S094', 'F15', 'R001', 'ST02'),

('S095', 'G1', 'R001', 'ST02'),
('S096', 'G2', 'R001', 'ST02'),
('S097', 'G3', 'R001', 'ST02'),
('S098', 'G4', 'R001', 'ST02'),
('S099', 'G5', 'R001', 'ST02'),
('S100', 'G6', 'R001', 'ST02'),
('S101', 'G7', 'R001', 'ST02'),
('S102', 'G8', 'R001', 'ST02'),
('S103', 'G9', 'R001', 'ST02'),
('S104', 'G10', 'R001', 'ST02'),
('S105', 'G11', 'R001', 'ST02'),
('S106', 'G12', 'R001', 'ST02'),
('S107', 'G13', 'R001', 'ST02'),
('S108', 'G14', 'R001', 'ST02'),
('S109', 'G15', 'R001', 'ST02'),

('S110', 'H1', 'R001', 'ST02'),
('S111', 'H2', 'R001', 'ST02'),
('S112', 'H3', 'R001', 'ST02'),
('S113', 'H4', 'R001', 'ST02'),
('S114', 'H5', 'R001', 'ST02'),
('S115', 'H6', 'R001', 'ST02'),
('S116', 'H7', 'R001', 'ST02'),
('S117', 'H8', 'R001', 'ST02'),
('S118', 'H9', 'R001', 'ST02'),
('S119', 'H10', 'R001', 'ST02'),
('S120', 'H11', 'R001', 'ST02'),
('S121', 'H12', 'R001', 'ST02'),
('S122', 'H13', 'R001', 'ST02'),
('S123', 'H14', 'R001', 'ST02'),
('S124', 'H15', 'R001', 'ST02'),

('S125', 'I1', 'R001', 'ST02'),
('S126', 'I2', 'R001', 'ST02'),
('S127', 'I3', 'R001', 'ST02'),
('S128', 'I4', 'R001', 'ST02'),
('S129', 'I5', 'R001', 'ST02'),
('S130', 'I6', 'R001', 'ST02'),
('S131', 'I7', 'R001', 'ST02'),
('S132', 'I8', 'R001', 'ST02'),
('S133', 'I9', 'R001', 'ST02'),
('S134', 'I10', 'R001', 'ST02'),
('S135', 'I11', 'R001', 'ST02'),
('S136', 'I12', 'R001', 'ST02'),
('S137', 'I13', 'R001', 'ST02'),
('S138', 'I14', 'R001', 'ST02'),
('S139', 'I15', 'R001', 'ST02'),

('S140', 'J1', 'R001', 'ST02'),
('S141', 'J2', 'R001', 'ST02'),
('S142', 'J3', 'R001', 'ST02'),
('S143', 'J4', 'R001', 'ST02'),
('S144', 'J5', 'R001', 'ST02'),
('S145', 'J6', 'R001', 'ST02'),
('S146', 'J7', 'R001', 'ST02'),
('S147', 'J8', 'R001', 'ST02'),
('S148', 'J9', 'R001', 'ST02'),
('S149', 'J10', 'R001', 'ST02'),
('S150', 'J11', 'R001', 'ST02'),
('S151', 'J12', 'R001', 'ST02'),
('S152', 'J13', 'R001', 'ST02'),
('S153', 'J14', 'R001', 'ST02'),
('S154', 'J15', 'R001', 'ST02'),

('S155', 'K1', 'R001', 'ST02'),
('S156', 'K2', 'R001', 'ST02'),
('S157', 'K3', 'R001', 'ST02'),
('S158', 'K4', 'R001', 'ST02'),
('S159', 'K5', 'R001', 'ST02'),
('S160', 'K6', 'R001', 'ST02'),
('S161', 'K7', 'R001', 'ST02'),
('S162', 'K8', 'R001', 'ST02'),
('S163', 'K9', 'R001', 'ST02'),
('S164', 'K10', 'R001', 'ST02'),
('S165', 'K11', 'R001', 'ST02'),
('S166', 'K12', 'R001', 'ST02'),
('S167', 'K13', 'R001', 'ST02'),
('S168', 'K14', 'R001', 'ST02'),
('S169', 'K15', 'R001', 'ST02'),

('S170', 'L1', 'R001', 'ST02'),
('S171', 'L2', 'R001', 'ST02'),
('S172', 'L3', 'R001', 'ST02'),
('S173', 'L4', 'R001', 'ST02'),
('S174', 'L5', 'R001', 'ST02'),
('S175', 'L6', 'R001', 'ST02'),
('S176', 'L7', 'R001', 'ST02'),
('S177', 'L8', 'R001', 'ST02'),
('S178', 'L9', 'R001', 'ST02'),
('S179', 'L10', 'R001', 'ST02'),
('S180', 'L11', 'R001', 'ST02'),
('S181', 'L12', 'R001', 'ST02'),
('S182', 'L13', 'R001', 'ST02'),
('S183', 'L14', 'R001', 'ST02'),
('S184', 'L15', 'R001', 'ST02'),

-- Ghế Couple
('S185', 'M1-2', 'R001', 'ST03'),
('S186', 'M3-4', 'R001', 'ST03'),
('S187', 'M5-6', 'R001', 'ST03'),
('S188', 'M7-8', 'R001', 'ST03'),
('S189', 'M9-10', 'R001', 'ST03'),
('S190', 'M11-12', 'R001', 'ST03'),
('S191', 'M13-14', 'R001', 'ST03'),
('S192', 'M15-16', 'R001', 'ST03');

--MOVIE SCHEDULE
INSERT INTO MovieSchedule (scheduleID, movieID, room, startTime, endTime)
VALUES 
('SC001', 'M001', 'R001', '2025-04-20 19:47:00', '2025-04-20 21:41:00'), 
('SC002', 'M002', 'R001', '2025-04-20 19:47:00', '2025-04-20 21:36:00'), 
('SC003', 'M002', 'R001', '2025-04-20 19:48:00', '2025-04-20 21:37:00'), 
('SC004', 'M002', 'R001', '2025-04-20 15:51:00', '2025-04-20 17:40:00'), 
('SC005', 'M003', 'R001', '2025-04-20 03:15:00', '2025-04-20 05:30:00'), 
('SC006', 'M004', 'R001', '2025-04-20 00:00:00', '2025-04-20 02:00:00'), 
('SC007', 'M005', 'R001', '2025-04-20 10:00:00', '2025-04-20 11:30:00');

--MOVIE SCHEDULE SEAT
--Thêm các ghế đã được đặt (không thể đặt được nữa)
INSERT INTO MovieScheduleSeat (scheduleID, seatID, isAvailable)
VALUES 
('SC001', 'S055', 0),
('SC001', 'S071', 0),
('SC001', 'S087', 0),
('SC001', 'S088', 0),
('SC001', 'S102', 0),
('SC001', 'S103', 0),
('SC001', 'S188', 0),
('SC001', 'S189', 0),
('SC001', 'S190', 0);
--Thêm các ghế còn lại (có thể đặt các ghế còn lại)
INSERT INTO MovieScheduleSeat (scheduleID, seatID)
SELECT 'SC001', seatID
FROM Seat
WHERE seatID NOT IN ('S055', 'S071', 'S087', 'S088', 'S102', 'S103', 'S188', 'S189', 'S190');

CREATE TABLE Voucher (
	voucherID VARCHAR(50) PRIMARY KEY,
	voucherName NVARCHAR(50),
	startDate DATE,
	endDate DATE,
	minimumPrice FLOAT,
	valueVoucher NVARCHAR(10)
)

INSERT INTO Voucher (voucherID, voucherName, startDate, endDate, minimumPrice, valueVoucher) VALUES
('KM008', N'Khuyến mãi 8', '2024-09-01', '2024-11-30', 39000.0, '30.0%'),
('KM005', N'Khuyến mãi 5', '2024-10-01', '2024-12-01', 300000.0, '30.0%'),
('KM010', N'Khuyến mãi 10', '2024-11-01', '2025-01-01', 59000.0, '35.0%'),
('KM011', N'Khuyến mãi 11', '2024-11-01', '2025-12-01', 1000000.0, '35.0%');


