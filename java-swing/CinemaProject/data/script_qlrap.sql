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

CREATE TABLE Voucher (
	voucherID VARCHAR(50) PRIMARY KEY,
	voucherName NVARCHAR(50),
	startDate DATE,
	endDate DATE,
	minimumPrice FLOAT,
	valueVoucher NVARCHAR(10)
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
    startTime DATETIME,
    endTime DATETIME,
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
    showDate DATETIME,
    seatID VARCHAR(50),
    room VARCHAR(50),
    ticketPrice FLOAT,
    FOREIGN KEY (movieID) REFERENCES Movie(movieID),
    FOREIGN KEY (seatID) REFERENCES Seat(seatID),
    FOREIGN KEY (room) REFERENCES Room(room)
);

CREATE TABLE OrderDetail (
    orderDetailID VARCHAR(50) PRIMARY KEY,
    orderID VARCHAR(50),
    productID VARCHAR(50),
    scheduleID VARCHAR(50),
    quantity INT,
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
    ('P003', N'Combo Bắp nước', 50, 'Đồ ăn', 55000),
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


INSERT INTO Voucher (voucherID, voucherName, startDate, endDate, minimumPrice, valueVoucher) VALUES
('KM008', N'Khuyến mãi 8', '2024-09-01', '2024-11-30', 39000.0, '30.0%'),
('KM005', N'Khuyến mãi 5', '2024-10-01', '2024-12-01', 300000.0, '30.0%'),
('KM010', N'Khuyến mãi 10', '2024-11-01', '2025-01-01', 59000.0, '35.0%'),
('KM011', N'Khuyến mãi 11', '2024-11-01', '2025-12-01', 1000000.0, '35.0%');
GO

INSERT INTO Seat (seatID, location, room, seatTypeID) VALUES
-- Hàng A
('S001',  'A1',  'R001', 'ST01'), ('S002',  'A2',  'R001', 'ST01'),
('S003',  'A3',  'R001', 'ST01'), ('S004',  'A4',  'R001', 'ST01'),
('S005',  'A5',  'R001', 'ST01'), ('S006',  'A6',  'R001', 'ST01'),
('S007',  'A7',  'R001', 'ST01'), ('S008',  'A8',  'R001', 'ST01'),
('S009',  'A9',  'R001', 'ST01'), ('S010', 'A10',  'R001', 'ST01'),
('S011', 'A11',  'R001', 'ST01'), ('S012', 'A12',  'R001', 'ST01'),
('S013', 'A13',  'R001', 'ST01'), ('S014', 'A14',  'R001', 'ST01'),
('S015', 'A15',  'R001', 'ST01'), ('S016', 'A16',  'R001', 'ST01'),

-- Hàng B
('S017',  'B1',  'R001', 'ST01'), ('S018',  'B2',  'R001', 'ST01'),
('S019',  'B3',  'R001', 'ST01'), ('S020',  'B4',  'R001', 'ST01'),
('S021',  'B5',  'R001', 'ST01'), ('S022',  'B6',  'R001', 'ST01'),
('S023',  'B7',  'R001', 'ST01'), ('S024',  'B8',  'R001', 'ST01'),
('S025',  'B9',  'R001', 'ST01'), ('S026', 'B10',  'R001', 'ST01'),
('S027', 'B11',  'R001', 'ST01'), ('S028', 'B12',  'R001', 'ST01'),
('S029', 'B13',  'R001', 'ST01'), ('S030', 'B14',  'R001', 'ST01'),
('S031', 'B15',  'R001', 'ST01'), ('S032', 'B16',  'R001', 'ST01'),

-- Hàng C
('S033',  'C1',  'R001', 'ST01'), ('S034',  'C2',  'R001', 'ST01'),
('S035',  'C3',  'R001', 'ST01'), ('S036',  'C4',  'R001', 'ST01'),
('S037',  'C5',  'R001', 'ST01'), ('S038',  'C6',  'R001', 'ST01'),
('S039',  'C7',  'R001', 'ST01'), ('S040',  'C8',  'R001', 'ST01'),
('S041',  'C9',  'R001', 'ST01'), ('S042', 'C10',  'R001', 'ST01'),
('S043', 'C11',  'R001', 'ST01'), ('S044', 'C12',  'R001', 'ST01'),
('S045', 'C13',  'R001', 'ST01'), ('S046', 'C14',  'R001', 'ST01'),
('S047', 'C15',  'R001', 'ST01'), ('S048', 'C16',  'R001', 'ST01'),

-- Hàng D
('S049',  'D1',  'R001', 'ST01'), ('S050',  'D2',  'R001', 'ST01'),
('S051',  'D3',  'R001', 'ST01'), ('S052',  'D4',  'R001', 'ST01'),
('S053',  'D5',  'R001', 'ST01'), ('S054',  'D6',  'R001', 'ST01'),
('S055',  'D7',  'R001', 'ST01'), ('S056',  'D8',  'R001', 'ST01'),
('S057',  'D9',  'R001', 'ST01'), ('S058', 'D10',  'R001', 'ST01'),
('S059', 'D11',  'R001', 'ST01'), ('S060', 'D12',  'R001', 'ST01'),
('S061', 'D13',  'R001', 'ST01'), ('S062', 'D14',  'R001', 'ST01'),
('S063', 'D15',  'R001', 'ST01'), ('S064', 'D16',  'R001', 'ST01'),

-- Hàng E (VIP từ ST02)
('S065',  'E1',  'R001', 'ST02'), ('S066',  'E2',  'R001', 'ST02'),
('S067',  'E3',  'R001', 'ST02'), ('S068',  'E4',  'R001', 'ST02'),
('S069',  'E5',  'R001', 'ST02'), ('S070',  'E6',  'R001', 'ST02'),
('S071',  'E7',  'R001', 'ST02'), ('S072',  'E8',  'R001', 'ST02'),
('S073',  'E9',  'R001', 'ST02'), ('S074', 'E10',  'R001', 'ST02'),
('S075', 'E11',  'R001', 'ST02'), ('S076', 'E12',  'R001', 'ST02'),
('S077', 'E13',  'R001', 'ST02'), ('S078', 'E14',  'R001', 'ST02'),
('S079', 'E15',  'R001', 'ST02'), ('S080', 'E16',  'R001', 'ST02'),

-- Hàng F (VIP)
('S081',  'F1',  'R001', 'ST02'), ('S082',  'F2',  'R001', 'ST02'),
('S083',  'F3',  'R001', 'ST02'), ('S084',  'F4',  'R001', 'ST02'),
('S085',  'F5',  'R001', 'ST02'), ('S086',  'F6',  'R001', 'ST02'),
('S087',  'F7',  'R001', 'ST02'), ('S088',  'F8',  'R001', 'ST02'),
('S089',  'F9',  'R001', 'ST02'), ('S090', 'F10',  'R001', 'ST02'),
('S091', 'F11',  'R001', 'ST02'), ('S092', 'F12',  'R001', 'ST02'),
('S093', 'F13',  'R001', 'ST02'), ('S094', 'F14',  'R001', 'ST02'),
('S095', 'F15',  'R001', 'ST02'), ('S096', 'F16',  'R001', 'ST02'),

-- Hàng G (VIP)
('S097',  'G1',  'R001', 'ST02'), ('S098',  'G2',  'R001', 'ST02'),
('S099',  'G3',  'R001', 'ST02'), ('S100',  'G4',  'R001', 'ST02'),
('S101',  'G5',  'R001', 'ST02'), ('S102',  'G6',  'R001', 'ST02'),
('S103',  'G7',  'R001', 'ST02'), ('S104',  'G8',  'R001', 'ST02'),
('S105',  'G9',  'R001', 'ST02'), ('S106', 'G10',  'R001', 'ST02'),
('S107', 'G11',  'R001', 'ST02'), ('S108', 'G12',  'R001', 'ST02'),
('S109', 'G13',  'R001', 'ST02'), ('S110', 'G14',  'R001', 'ST02'),
('S111', 'G15',  'R001', 'ST02'), ('S112', 'G16',  'R001', 'ST02'),

-- Hàng H (VIP)
('S113',  'H1',  'R001', 'ST02'), ('S114',  'H2',  'R001', 'ST02'),
('S115',  'H3',  'R001', 'ST02'), ('S116',  'H4',  'R001', 'ST02'),
('S117',  'H5',  'R001', 'ST02'), ('S118',  'H6',  'R001', 'ST02'),
('S119',  'H7',  'R001', 'ST02'), ('S120',  'H8',  'R001', 'ST02'),
('S121',  'H9',  'R001', 'ST02'), ('S122', 'H10',  'R001', 'ST02'),
('S123', 'H11',  'R001', 'ST02'), ('S124', 'H12',  'R001', 'ST02'),
('S125', 'H13',  'R001', 'ST02'), ('S126', 'H14',  'R001', 'ST02'),
('S127', 'H15',  'R001', 'ST02'), ('S128', 'H16',  'R001', 'ST02'),

-- Hàng I (VIP)
('S129',  'I1',  'R001', 'ST02'), ('S130',  'I2',  'R001', 'ST02'),
('S131',  'I3',  'R001', 'ST02'), ('S132',  'I4',  'R001', 'ST02'),
('S133',  'I5',  'R001', 'ST02'), ('S134',  'I6',  'R001', 'ST02'),
('S135',  'I7',  'R001', 'ST02'), ('S136',  'I8',  'R001', 'ST02'),
('S137',  'I9',  'R001', 'ST02'), ('S138', 'I10',  'R001', 'ST02'),
('S139', 'I11',  'R001', 'ST02'), ('S140', 'I12',  'R001', 'ST02'),
('S141', 'I13',  'R001', 'ST02'), ('S142', 'I14',  'R001', 'ST02'),
('S143', 'I15',  'R001', 'ST02'), ('S144', 'I16',  'R001', 'ST02'),

-- Hàng J (VIP)
('S145',  'J1',  'R001', 'ST02'), ('S146',  'J2',  'R001', 'ST02'),
('S147',  'J3',  'R001', 'ST02'), ('S148',  'J4',  'R001', 'ST02'),
('S149',  'J5',  'R001', 'ST02'), ('S150',  'J6',  'R001', 'ST02'),
('S151',  'J7',  'R001', 'ST02'), ('S152',  'J8',  'R001', 'ST02'),
('S153',  'J9',  'R001', 'ST02'), ('S154', 'J10',  'R001', 'ST02'),
('S155', 'J11',  'R001', 'ST02'), ('S156', 'J12',  'R001', 'ST02'),
('S157', 'J13',  'R001', 'ST02'), ('S158', 'J14',  'R001', 'ST02'),
('S159', 'J15',  'R001', 'ST02'), ('S160', 'J16',  'R001', 'ST02'),

-- Hàng K (VIP)
('S161',  'K1',  'R001', 'ST02'), ('S162',  'K2',  'R001', 'ST02'),
('S163',  'K3',  'R001', 'ST02'), ('S164',  'K4',  'R001', 'ST02'),
('S165',  'K5',  'R001', 'ST02'), ('S166',  'K6',  'R001', 'ST02'),
('S167',  'K7',  'R001', 'ST02'), ('S168',  'K8',  'R001', 'ST02'),
('S169',  'K9',  'R001', 'ST02'), ('S170', 'K10',  'R001', 'ST02'),
('S171', 'K11',  'R001', 'ST02'), ('S172', 'K12',  'R001', 'ST02'),
('S173', 'K13',  'R001', 'ST02'), ('S174', 'K14',  'R001', 'ST02'),
('S175', 'K15',  'R001', 'ST02'), ('S176', 'K16',  'R001', 'ST02'),

-- Hàng L (VIP)
('S177',  'L1',  'R001', 'ST02'), ('S178',  'L2',  'R001', 'ST02'),
('S179',  'L3',  'R001', 'ST02'), ('S180',  'L4',  'R001', 'ST02'),
('S181',  'L5',  'R001', 'ST02'), ('S182',  'L6',  'R001', 'ST02'),
('S183',  'L7',  'R001', 'ST02'), ('S184',  'L8',  'R001', 'ST02'),
('S185',  'L9',  'R001', 'ST02'), ('S186','L10',  'R001', 'ST02'),
('S187','L11',  'R001', 'ST02'),('S188','L12',  'R001', 'ST02'),
('S189','L13',  'R001', 'ST02'),('S190','L14',  'R001', 'ST02'),
('S191','L15',  'R001', 'ST02'),('S192','L16',  'R001', 'ST02');
GO
INSERT INTO Seat (seatID, location, room, seatTypeID) VALUES
('S193','M01-02','R001','ST03'),('S194','M03-04','R001','ST03'),
('S195','M05-06','R001','ST03'),('S196','M07-08','R001','ST03'),
('S197','M09-10','R001','ST03'),('S198','M11-12','R001','ST03'),
('S199','M13-14','R001','ST03'),('S200','M15-16','R001','ST03');
GO

CREATE TRIGGER trg_AutoID_Orders
ON Orders
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID VARCHAR(50)
    DECLARE @Number INT

    SELECT @Number = ISNULL(MAX(CAST(SUBSTRING(orderID, 2, LEN(orderID)) AS INT)), 0) + 1
    FROM Orders

    SET @NewID = 'O' + RIGHT('000' + CAST(@Number AS VARCHAR(3)), 3)

    INSERT INTO Orders(orderID, orderDate, totalPrice, employeeID, voucherID)
    SELECT @NewID, orderDate, totalPrice, employeeID, voucherID
    FROM inserted
END

CREATE OR ALTER TRIGGER trg_AutoID_OrderDetail
ON OrderDetail
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID VARCHAR(50)
    DECLARE @Number INT

    SELECT @Number = ISNULL(MAX(CAST(SUBSTRING(orderDetailID, 3, LEN(orderDetailID)) AS INT)), 0) + 1
    FROM OrderDetail

    SET @NewID = 'OD' + RIGHT('000' + CAST(@Number AS VARCHAR(3)), 3)

    INSERT INTO OrderDetail(orderDetailID, orderID, productID, scheduleID, quantity)
    SELECT @NewID, orderID, productID, scheduleID, quantity
    FROM inserted
END

CREATE OR ALTER TRIGGER trg_AutoID_TicketDetail
ON TicketDetail
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID VARCHAR(50)
    DECLARE @Number INT

    SELECT @Number = ISNULL(MAX(CAST(SUBSTRING(ticketID, 2, LEN(ticketID)) AS INT)), 0) + 1
    FROM TicketDetail

    SET @NewID = 'T' + RIGHT('000' + CAST(@Number AS VARCHAR(3)), 3)

    INSERT INTO TicketDetail(ticketID, movieID, showDate, seatID, room, ticketPrice)
    SELECT @NewID, movieID, showDate, seatID, room, ticketPrice
    FROM inserted
END
