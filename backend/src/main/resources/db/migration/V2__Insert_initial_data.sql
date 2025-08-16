-- ユーザー初期データ
-- 初期パスワードは、passwordです。
INSERT INTO `user` (email, is_admin, name, password, tel, department, picture) VALUES
('admin@example.com', 1, '総務美子', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '総務部' , NULL),
('user1@example.com', 0, '山田太郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '営業部' , NULL),
('user2@example.com', 0, '佐藤花子', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '品質管理部' , NULL),
('user3@example.com', 0, '鈴木一郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '製造部' , NULL),
('user4@example.com', 0, '田中美咲', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '経理部' , NULL),
('user5@example.com', 0, '試験零郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '製造部' , NULL),
('user6@example.com', 0, '試験一郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '製造部' , NULL),
('user7@example.com', 0, '試験二郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '製造部' , NULL),
('user8@example.com', 0, '試験三郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '製造部' , NULL),
('user9@example.com', 0, '試験四郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '製造部' , NULL),
('user0@example.com', 0, '試験五郎', '$2a$12$a9NOLu2PK63EIIsNK6CNxuLJVkGpby4o4Y5QsP3pXAOwDmldA5Axm', '03-1234-0001' , '製造部' , NULL);

-- 会議室初期データ
INSERT INTO room (name, place, tel, remarks) VALUES
('会議室A', '東京本社3F', '03-1111-1111', 'プロジェクターあり'),
('会議室B', '東京本社4F', '03-2222-2222', 'ホワイトボードあり'),
('会議室C', '大阪支社2F', '06-3333-3333', '10名まで利用可'),
('会議室D', '名古屋支社1F', '052-4444-4444', 'Web会議対応'),
('会議室E', '福岡支社5F', '092-5555-5555', '飲食可'),
('会議室F', '札幌支社3F', '011-6666-6666', '窓の大きい部屋'),
('会議室G', '仙台支社4F', '022-7777-7777', '音響設備あり'),
('会議室H', '広島支社2F', '082-8888-8888', 'ホワイトボードとプロジェクターあり'),
('会議室I', '沖縄支社1F', '098-9999-9999', '海が見える部屋'),
('会議室J', '京都支社3F', '075-0000-0000', '静かな環境');

-- 予約初期データ
INSERT INTO reservation (room_id, user_id, use_from_datetime, use_to_datetime, remarks) VALUES
(1, 2, '2025-07-16 09:00:00', '2025-07-16 10:00:00', '定例ミーティング'),
(2, 3, '2025-07-16 11:00:00', '2025-07-16 12:00:00', 'プロジェクト打合せ'),
(3, 4, '2025-07-17 13:00:00', '2025-07-17 14:30:00', '面接対応'),
(4, 5, '2025-07-18 15:00:00', '2025-07-18 16:00:00', '営業会議'),
(5, 1, '2025-07-19 17:00:00', '2025-07-19 18:00:00', '管理者確認'),
(1, 2, '2025-08-16 09:00:00', '2025-08-16 10:00:00', '定例ミーティング'),
(2, 3, '2025-08-16 11:00:00', '2025-08-16 12:00:00', 'プロジェクト打合せ'),
(3, 4, '2025-08-17 13:00:00', '2025-08-17 14:30:00', '面接対応'),
(4, 5, '2025-08-18 15:00:00', '2025-08-18 16:00:00', '営業会議'),
(5, 1, '2025-08-19 17:00:00', '2025-08-19 18:00:00', '管理者確認');