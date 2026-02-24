-- =============================================
-- MS-CLIENTES
-- =============================================

CREATE TABLE clientes (
                          id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre           VARCHAR(100) NOT NULL,
                          genero           VARCHAR(10),
                          edad             INT,
                          identificacion   VARCHAR(20),
                          direccion        VARCHAR(200),
                          telefono         VARCHAR(20),
                          cliente_id       VARCHAR(50) NOT NULL UNIQUE,
                          contrasena       VARCHAR(100),
                          estado           BOOLEAN
);

INSERT INTO clientes (nombre, genero, edad, identificacion, direccion, telefono, cliente_id, contrasena, estado)
VALUES
    ('Jose Lema',          'M', 30, '001', 'Otavalo s/n y principal', '098254785', 'jose123',      '1234', true),
    ('Marianela Montalvo', 'F', 28, '002', 'Amazonas y NNUU',         '097548965', 'marianela123', '5678', true),
    ('Juan Osorio',        'M', 25, '003', '13 Junio y Equinoccial',  '098874587', 'juan123',      '1245', true);

-- =============================================
-- CUENTAS
-- =============================================

CREATE TABLE cuentas (
                         id                BIGINT AUTO_INCREMENT PRIMARY KEY,
                         numero_cuenta     VARCHAR(20) NOT NULL UNIQUE,
                         tipo_cuenta       VARCHAR(20),
                         saldo_inicial     DOUBLE,
                         saldo_disponible  DOUBLE,
                         estado            BOOLEAN,
                         cliente_id        VARCHAR(50)
);

CREATE TABLE movimientos (
                             id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                             fecha            TIMESTAMP,
                             tipo_movimiento  VARCHAR(20),
                             valor            DOUBLE,
                             saldo            DOUBLE,
                             cuenta_id        BIGINT,
                             FOREIGN KEY (cuenta_id) REFERENCES cuentas(id)
);

-- Cuentas iniciales
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, saldo_disponible, estado, cliente_id)
VALUES
    ('478758', 'Ahorros',   2000, 2000, true, 'jose123'),
    ('225487', 'Corriente', 100,  100,  true, 'marianela123'),
    ('495878', 'Ahorros',   0,    0,    true, 'juan123'),
    ('496825', 'Ahorros',   540,  540,  true, 'marianela123'),
    ('585545', 'Corriente', 1000, 1000, true, 'jose123');