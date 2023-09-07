CREATE DATABASE duan1
go
USE duan1
GO
-- Ban
CREATE TABLE BAN(
MaBan varchar(10) PRIMARY KEY,
TenBan NVARCHAR(50)  NULL,
MaKV_FK varchar(10)  NULL
)
GO
-- Khu Vuc
CREATE TABLE KHUVUC(
MaKV varchar(10) PRIMARY KEY,
TenKV nvarchar(50) NULL,
)
GO
-- NhanVien
CREATE TABLE NHANVIEN(
MaNV varchar(10) PRIMARY KEY ,
	Password nvarchar(50) NULL,
	TenNV nvarchar(50) NULL,
	Hinh nvarchar(50) NULL,
	CMND nvarchar(9) NULL,
	Diachi nvarchar(50) NULL,
	DienThoai nvarchar(12) NULL,
	NgaySinh date NULL,
	NgayVaoLam date NULL,
	VaiTro bit NULL,
	ViTri bit NULL,
)
GO

--Khachhang
CREATE TABLE KHACHHANG(
	MaKH     varchar(10) PRIMARY KEY,
	TenKh    NVARCHAR(30) NULL,
	SDT NVARCHAR(10) NULL,
	ngaySinh      NVARCHAR(15) NULL,
	email   NVARCHAR(50) NULL,
)
Go

--HoaDon
CREATE TABLE HOADON(
MaHD varchar(10) PRIMARY KEY ,
	MaBan_FK varchar(10) NULL,
	MaNV_FK varchar(10) NULL,
	MaKH_FK varchar(10) NULL,
	NgayHD date NULL,
	Tongtien float NULL,
	Trangthai bit NULL,
)
GO
-- HOA DON CT
CREATE TABLE CTHOADON(
MaCTHD varchar(30) PRIMARY KEY ,
MaHD_FK varchar(10) NULL,
MaMon_FK varchar(10) NULL,
SoLuong int NULL,
DonGia float NULL,
GhiChu varchar(50) NULL,
)
GO
-- SanPham
CREATE TABLE MON(
MaMon varchar(10)  PRIMARY KEY,
	TenMon nvarchar(50) NULL,
	Gia float NULL,
	HinhAnh nvarchar(50) NULL,
	MaLoai_FK varchar(10) NULL,)
GO
-- LOAISANPHAM
CREATE TABLE LOAIMON(
MaLoai varchar(10) PRIMARY KEY,
TenLoai nvarchar(50) NULL,
)
GO
ALTER TABLE HOADON ADD FOREIGN KEY (MaBan_FK) REFERENCES BAN(MaBan)
--NhanVien - ChucVu
ALTER TABLE HOADON ADD FOREIGN KEY (MaNV_FK) REFERENCES NHANVIEN(MaNV)
--NhanVien - GuiBaoCao
ALTER TABLE BAN ADD FOREIGN KEY (MaKV_FK) REFERENCES KHUVUC(MaKV)
-- ban - khu vuc
ALTER TABLE HOADON ADD FOREIGN KEY (MaKH_FK) REFERENCES KHACHHANG(MAKH)
-- HoaDon - KhachHang
ALTER TABLE MON ADD FOREIGN KEY (MaLoai_FK) REFERENCES LOAIMON(MaLoai)
-- GioHang - KhachHang
ALTER TABLE CTHOADON ADD FOREIGN KEY(MaMon_FK) REFERENCES MON(MaMon)
-- ChiTietSP - NSX
ALTER TABLE CTHOADON ADD FOREIGN KEY(MaHD_FK) REFERENCES HOADON(MaHD)
-- ChiTietSP - MauSac
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'14A181627 ', N'Bàn 01', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'14A183535 ', N'Bàn 02', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'15A183536 ', N'Bàn 03', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'15A183537 ', N'Bàn 04', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'16A183538 ', N'Bàn 05', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'17A183539 ', N'Bàn 06', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'10A21111  ', N'Bàn 17', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'12A183534 ', N'Bàn 18', N'KV1205443 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'6A181124  ', N'Bàn 19', N'KV3212527 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'3A21139   ', N'Bàn 20', N'KV3212527 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'41A183430 ', N'Bàn 21', N'KV3212527 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'55A105415 ', N'Bàn 31', N'KV3212527 ')
INSERT [dbo].[BAN] ([MaBan], [TenBan], [MaKV_FK]) VALUES (N'57A123418 ', N'Bàn 32', N'KV3212527 ')

GO

INSERT [dbo].[KHUVUC] ([MaKV], [TenKV]) VALUES (N'KV1205443 ', N'Lầu 1')
INSERT [dbo].[KHUVUC] ([MaKV], [TenKV]) VALUES (N'KV3212527 ', N'Lầu 2')

GO
INSERT [dbo].[LOAIMON] ([MaLoai], [TenLoai]) VALUES (N'NCN       ', N'Nước Ngọt')
INSERT [dbo].[LOAIMON] ([MaLoai], [TenLoai]) VALUES (N'NCN1      ', N'Cà Phê')
GO
INSERT [dbo].[NHANVIEN] ([MaNV], [Password], [TenNV], [Hinh], [CMND], [Diachi], [DienThoai], [NgaySinh], [NgayVaoLam], [VaiTro], [ViTri]) VALUES (N'admin     ', N'admin', N'Admin', NULL, N'253658698', N'Nam Ky', N'0986523541', CAST(N'1999-11-21' AS Date), CAST(N'2018-11-03' AS Date), 1, 0)
INSERT [dbo].[NHANVIEN] ([MaNV], [Password], [TenNV], [Hinh], [CMND], [Diachi], [DienThoai], [NgaySinh], [NgayVaoLam], [VaiTro], [ViTri]) VALUES (N'hieu  ', N'hieu', N'Đoàn Ngọc Thành', NULL, N'12345678', N'', N'0974135042', CAST(N'1999-01-28' AS Date), CAST(N'2018-10-30' AS Date), 0, 1)
GO
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'CF01      ', N'Cà Phê Đen Đá', 10000, N'cpheda.jpg', N'NCN1      ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'CF02      ', N'Cà Phê Sữa', 15000, N'caphesua.jpg', N'NCN1      ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'CF03      ', N'Capuchino', 15000, N'capuchino.jpg', N'NCN1      ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'CF04      ', N'Cà phê nguyên chất', 15000, N'cpheda.jpg', N'NCN1      ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST01      ', N'Sting Chai Vàng', 12000, N'stingvang.jpg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST02      ', N'Sting Chai Đỏ', 12000, N'stingdo.jpg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST03      ', N'Bò Húc', 12000, N'bohuc.jpeg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST04      ', N'Mirinda cam', 12000, N'mirindacam.jpg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST05      ', N'Mirinda xá xị', 12000, N'mirindaxãi.jpg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST06      ', N'Wakkup 247', 12000, N'wakup.jpg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST07      ', N'Nha Đam', 12000, N'nhadam.jpg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'ST08      ', N'Monter Xanh', 75000, N'monterxanh.jpg', N'NCN       ')
INSERT [dbo].[MON] ([MaMon], [TenMon], [Gia], [HinhAnh], [MaLoai_FK]) VALUES (N'sttt      ', N'Cà Phê Đen Đá', 10000, NULL, N'NCN1      ')
GO