USE smbms_db;

-- Insert more test bill data with different codes
INSERT INTO smbms_bill (billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, providerId) VALUES
('BILL2016_011', 'Shampoo', 'Hair care products', 'Bottle', 500.00, 25000.00, 2, 1, 1),
('BILL2016_012', 'Soap', 'Cleaning products', 'Piece', 1000.00, 10000.00, 2, 1, 1),
('BILL2016_013', 'Soybean Oil', 'Cooking oil', 'Jin', 300.00, 5890.00, 2, 1, 2),
('BILL2016_014', 'Olive Oil', 'Imported oil', 'Jin', 200.00, 9800.00, 2, 1, 2),
('BILL2016_015', 'Dish Soap', 'Kitchen cleaning', 'Bottle', 500.00, 7000.00, 2, 1, 3),
('BILL2016_016', 'Toothpaste', 'Oral care', 'Tube', 800.00, 6400.00, 1, 1, 1),
('BILL2016_017', 'Rice', 'Staple food', 'Bag', 100.00, 5000.00, 2, 1, 2),
('BILL2016_018', 'Flour', 'Staple food', 'Bag', 150.00, 4500.00, 1, 1, 2),
('BILL2016_019', 'Cooking Oil', 'Seasoning', 'Barrel', 200.00, 12000.00, 2, 1, 3),
('BILL2016_020', 'Seasonings', 'Seasoning', 'Set', 50.00, 3000.00, 1, 1, 1); 