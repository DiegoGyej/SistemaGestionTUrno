CREATE DATABASE GestionTurnos;
USE GestionTurnos;

CREATE TABLE turno (
	idTurno INT AUTO_INCREMENT PRIMARY KEY,
    idCancha INT,
    fecha DATE,
    hora TIME,
    duracion TIME,
    estado ENUM('Disponible', 'Reservado'),
    FOREIGN KEY (idCancha) REFERENCES cancha(idCancha)
);

INSERT INTO turno (idCancha, fecha, hora, duracion, estado)
VALUES (1, '2024-10-10', '18:00:00', '01:00:00', 'Reservado'),
		(7, '2024-10-06', '21:00:00', '01:00:00', 'Disponible');

CREATE TABLE reserva (
	idReserva INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT,
    idTurno INT,
    idCancha INT,
    estado ENUM('Registrada', 'Cancelada', 'Modificada'),
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente),
    FOREIGN KEY (idCancha) REFERENCES cancha(idCancha),
    FOREIGN KEY (idTurno) REFERENCES turno(idTurno)
);

INSERT INTO reserva (idCliente, idTurno, idCancha, estado)
VALUES (1, 1, 1, 'Registrada'),
		(2, 2, 7, 'Registrada');

CREATE TABLE cliente (
	idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    dni VARCHAR(100),
    telefono VARCHAR(50)
);

INSERT INTO cliente (nombre, apellido, dni, telefono)
VALUES ('Diego', 'Osinaga', '34220122', '3885221466'),
		('Pablo', 'Quiroga', '39175601', '3884752980');

CREATE TABLE cancha (
	idCancha INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('Futbol 5', 'Futbol 8'),
    numCancha VARCHAR(50),
    capacidad VARCHAR(20),
    estado ENUM('Disponible','Ocupada'),
    precio DECIMAL
);

INSERT INTO cancha (tipo, numCancha, capacidad, estado, precio)
VALUES ('Futbol 5', 'Cancha 1', '10 jugadores', 'Ocupada', 20000.00),
		('Futbol 5', 'Cancha 2', '10 jugadores', 'Disponible',20000.00),
        ('Futbol 5', 'Cancha 3', '10 jugadores', 'Disponible',20000.00),
        ('Futbol 5', 'Cancha 4', '10 jugadores', 'Ocupada',20000.00),
        ('Futbol 5', 'Cancha 5', '10 jugadores', 'Ocupada',20000.00),
		('Futbol 8', 'Cancha 1', '16 jugadores', 'Ocupada', 32000.00),
		('Futbol 8', 'Cancha 2', '16 jugadores', 'Disponible', 32000.00),
        ('Futbol 8', 'Cancha 3', '16 jugadores', 'Disponible', 32000.00);

SELECT 
    c.nombre,
    c.apellido,
    r.idReserva,
    r.estado AS Estado_Reserva,
    t.fecha,
    t.hora,
    t.duracion,
    ca.tipo AS Tipo_Cancha,
    ca.numCancha,
    ca.precio
FROM 
    cliente c
JOIN 
    reserva r ON c.idCliente = r.idCliente
JOIN 
    turno t ON r.idTurno = t.idTurno
JOIN 
    cancha ca ON r.idCancha = ca.idCancha
WHERE 
    c.idCliente = 1;
    
DELETE FROM cancha;
DELETE FROM turno;
DELETE FROM cliente;
DELETE FROM reserva;

SELECT * FROM cancha;
SELECT * FROM turno;
SELECT * FROM cliente;
SELECT * FROM reserva;