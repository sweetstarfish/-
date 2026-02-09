USE smbms_db;

-- Insert test bill data with shorter names
INSERT INTO smbms_bill (billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, providerId) VALUES
('BILL2016_001', 'Shampoo', 'Hair care products', 'Bottle', 500.00, 25000.00, 2, 1, 1),
('BILL2016_002', 'Soap', 'Cleaning products', 'Piece', 1000.00, 10000.00, 2, 1, 1),
('BILL2016_003', 'Soybean Oil', 'Cooking oil', 'Jin', 300.00, 5890.00, 2, 1, 2),
('BILL2016_004', 'Olive Oil', 'Imported oil', 'Jin', 200.00, 9800.00, 2, 1, 2),
('BILL2016_005', 'Dish Soap', 'Kitchen cleaning', 'Bottle', 500.00, 7000.00, 2, 1, 3),
('BILL2016_006', 'Toothpaste', 'Oral care', 'Tube', 800.00, 6400.00, 1, 1, 1),
('BILL2016_007', 'Rice', 'Staple food', 'Bag', 100.00, 5000.00, 2, 1, 2),
('BILL2016_008', 'Flour', 'Staple food', 'Bag', 150.00, 4500.00, 1, 1, 2),
('BILL2016_009', 'Cooking Oil', 'Seasoning', 'Barrel', 200.00, 12000.00, 2, 1, 3),
('BILL2016_010', 'Seasonings', 'Seasoning', 'Set', 50.00, 3000.00, 1, 1, 1); 