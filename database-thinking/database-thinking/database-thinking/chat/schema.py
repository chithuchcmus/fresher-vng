
DB_NAME = 'employees'

TABLES = {}

TABLES['user'] = (
    "CREATE TABLE `user` ("
    "  `id_user` int NOT NULL AUTO_INCREMENT,"
    "  `password` varchar(512) NOT NULL,"
    "  `username` varchar(50) NOT NULL,"
    "  `email` varchar(50) NOT NULL,"
    "  `isOnline` varchar(100),"
    "  PRIMARY KEY (`id_user`)"
    ") ENGINE=InnoDB")

TABLES['message'] = (
    "CREATE TABLE `message` ("
    "  `id_message` int NOT NULL AUTO_INCREMENT,"
    "  `context` varchar(100),"
    "  `create_at` varchar(100) NOT NULL,"
    "  `id_sender` int NOT NULL,"
    "  `id_conversation` char(100) NOT NULL,"
    "  PRIMARY KEY (`id_message`),"
    "  FOREIGN KEY(id_sender) REFERENCES user(id_user)"
    ") ENGINE=InnoDB")

TABLES['conversation'] = (
    "CREATE TABLE `conversation` ("
    "  `id_conversation` varchar(100) NOT NULL,"
    "  `conversation_name` varchar(100) NOT NULL,"
    "  `is_group` BOOLEAN,"
    "  PRIMARY KEY (`id_conversation`)"
    ") ENGINE=InnoDB")

TABLES['participants'] = (
    "CREATE TABLE `participants` ("
    "  `id_par` int NOT NULL AUTO_INCREMENT,"
    "  `id_user` int NOT NULL,"
    "  `id_conversation` varchar(100),"
    "  PRIMARY KEY (`id_par`),"
    "  FOREIGN KEY(id_user) REFERENCES user(id_user),"
    "  FOREIGN KEY(id_conversation) REFERENCES conversation(id_conversation)"
    ") ENGINE=InnoDB")

TABLES['friends'] = (
    "CREATE TABLE `friends` ("
    "  `id_sender` int NOT NULL,"
    "  `id_receive` int NOT NULL,"
    "  `status` varchar(100),"
    "  PRIMARY KEY (`id_sender`, `id_receive`),"
    "  FOREIGN KEY (id_sender) REFERENCES user(id_user),"
    "  FOREIGN KEY (id_receive) REFERENCES user(id_user)"
    ") ENGINE=InnoDB")
