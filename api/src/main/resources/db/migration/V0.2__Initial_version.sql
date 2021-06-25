CREATE TABLE IF NOT EXISTS Kingdoms (
    kingdom_id BINARY(16) PRIMARY KEY,
    kingdom_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Roles (
    role_id BINARY(16) PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    role_kingdom BINARY(16) NULL REFERENCES Kingdoms(kingdom_id)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Permissions (
    permission_ref BINARY(16) NOT NULL REFERENCES Roles(role_id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE,
    permission_val ENUM('ADMIN','MANAGE','PICK','CONTAINER','INTERACT','BUILD','KILL','TALK') NOT NULL
);

CREATE TABLE IF NOT EXISTS Users (
    user_id BINARY(16) PRIMARY KEY,
    user_name VARCHAR(16) UNIQUE NOT NULL,
    user_kingdom BINARY(16) NOT NULL REFERENCES Kingdoms(kingdom_id)
                                        ON DELETE CASCADE
                                        ON UPDATE CASCADE,
    user_role BINARY(16) NULL REFERENCES Roles(role_id)
                                    ON DELETE SET NULL
                                    ON UPDATE CASCADE
);

# Default roles
INSERT INTO Roles (role_id, role_kingdom, role_name) VALUES
    (UNHEX('66e00734bde443c0a42646b79075cbb1'), NULL, 'Owner'),
    (UNHEX('66e00734bde443c0a42646b79075cbb2'), NULL, 'Member'),
    (UNHEX('66e00734bde443c0a42646b79075cbb3'), NULL, 'Visitor');

INSERT INTO Permissions (permission_ref, permission_val) VALUES
    (UNHEX('66e00734bde443c0a42646b79075cbb1'), 'ADMIN'),
    (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'BUILD'),
    (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'CONTAINER'),
    (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'INTERACT'),
    (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'KILL'),
    (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'PICK'),
    (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'TALK'),
    (UNHEX('66e00734bde443c0a42646b79075cbb3'), 'TALK'),
    (UNHEX('66e00734bde443c0a42646b79075cbb3'), 'INTERACT');