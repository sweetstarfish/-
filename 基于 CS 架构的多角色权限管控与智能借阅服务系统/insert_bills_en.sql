USE smbms_db;

-- Insert test bill data
INSERT INTO smbms_bill (billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, providerId) VALUES
('BILL2016_001', 'Shampoo and Conditioner', 'Daily necessities - Hair care', 'Bottle', 500.00, 25000.00, 2, 1, 1),
('BILL2016_002', 'Soap and Detergent', 'Daily necessities - Soap', 'Piece', 1000.00, 10000.00, 2, 1, 1),
('BILL2016_003', 'Soybean Oil', 'Food - Cooking oil', 'Jin', 300.00, 5890.00, 2, 1, 2),
('BILL2016_004', 'Olive Oil', 'Food - Imported cooking oil', 'Jin', 200.00, 9800.00, 2, 1, 2),
('BILL2016_005', 'Dish Soap', 'Daily necessities - Kitchen cleaning', 'Bottle', 500.00, 7000.00, 2, 1, 3),
('BILL2016_006', 'Toothpaste', 'Daily necessities - Oral care', 'Tube', 800.00, 6400.00, 1, 1, 1),
('BILL2016_007', 'Rice', 'Food - Staple food', 'Bag', 100.00, 5000.00, 2, 1, 2),
('BILL2016_008', 'Flour', 'Food - Staple food', 'Bag', 150.00, 4500.00, 1, 1, 2),
('BILL2016_009', 'Cooking Oil', 'Food - Seasoning', 'Barrel', 200.00, 12000.00, 2, 1, 3),
('BILL2016_010', 'Seasonings', 'Food - Seasoning', 'Set', 50.00, 3000.00, 1, 1, 1); 