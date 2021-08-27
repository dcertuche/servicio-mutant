"# servicio-mutant" 

Nivel 1.
Como prerequisito ejecutar utilizando cliente Postman.
* Para ejecutar metodo POST: 
	metodo: Post
	params: body
	Header: raw/Content-Type:application/json
	

Running application on localhost
java -jar spring-boot-servicio-mutante-0.0.1-SNAPSHOT.jar
Tomcat started on port(s): 8080 (http) with context path ''
	Servicios
	http://localhost:8080/api/stats
	http://localhost:8080/api/mutant

Running application on Cloud Heroku
	https://dcertuche-mutant.herokuapp.com/api/mutant
	https://dcertuche-mutant.herokuapp.com/api/stats
	
Ejemplos	
	
Caso mutante.
	Request
	POST → https://dcertuche-mutant.herokuapp.com/api/mutant
	Header: Content-Type:application/json
	Body:
	{
		["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
	}
	Response
	{
		"DNA": "[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]",
		"mensaje": "El dna ha sido leido con éxito",
		"Boolean": true
	}

Nota: Por cliente Postman, en la respuesta del servicio, se observa status 200 respuesta de servidor

Caso humano
	Request
	POST → https://dcertuche-mutant.herokuapp.com/api/mutant
	Header: Content-Type:application/json
	body:
	{
		["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
	}
	Response
	{
		"DNA": "[ATGCGA, CAGTGC, TTATTT, AGACGG, GCGTCA, TCACTG]",
		"mensaje": "No se encontró mutantes",
		"Boolean": false
	}
Nota: Por cliente Postman, en la respuesta del servicio, se observa status 403 Forbidden respuesta de servidor

Nivel 2.
* Se crear API REST, del cual contiene dos metodos GET y POST.
* Se hostea en el Cloud de Heroku

Nivel 3.
* Se anexa add{-ons de JawsDB MySQL
* Credenciales de Heroku En el interior del proyecto para acceder a base de datos. 
* Se encontrara archivo application.properties, en el cual encontrara las credenciales de acceso a base de datos.
* Se crear dos tablas; 'mutante' almacena la cadena master ADN, estado correspondiente a humano y mutante. 
  La tabla 'mutant_adns' almacena detalle de secuencia de cuatro letras iguales.
* Se expone servicio REST "/stats", del cual resuelve estadisticas de consulta por cada ADN.

Servicio Stat
	Request
	GET https://dcertuche-mutant.herokuapp.com/api/stats
	Reponse
	{
		"ADN": {
			"countMutantDna": 3,
			"countHumanDna": 5,
			"ratio": 0.6000000238418579
		}
	}
	
Anexo: 
* DDL tablas
create table mutant_adns (id bigint not null auto_increment, adn varchar(255) not null, mutante_id bigint, primary key (id)) engine=InnoDB;
create table mutante (id bigint not null auto_increment, adn varchar(255) not null, create_at date, mutante bit, primary key (id)) engine=InnoDB;
alter table mutant_adns add constraint FKjodny5ls4b61rt4iveugc99p7 foreign key (mutante_id) references mutante (id);
* DML import.sql 
INSERT INTO `mutante` 	  (adn, mutante, create_at) VALUES ('[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]',1,NOW())
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('AAAAAA',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('CCCCCC',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('GGGGGG',1)
INSERT INTO `mutante`     (adn, mutante, create_at) VALUES ('[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]',1,NOW())
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('AAAAAA',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('CCCCCC',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('GGGGGG',1)
INSERT INTO `mutante`     (adn, mutante, create_at) VALUES ('[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]',0,NOW())
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('AAAAAA',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('CCCCCC',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('GGGGGG',1)
INSERT INTO `mutante`     (adn, mutante, create_at) VALUES ('[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]',0,NOW())
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('AAAAAA',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('CCCCCC',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('GGGGGG',1)
INSERT INTO `mutante`     (adn, mutante, create_at) VALUES ('[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]',0,NOW())
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('AAAAAA',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('CCCCCC',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('GGGGGG',1)
INSERT INTO `mutante`     (adn, mutante, create_at) VALUES ('[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]',0,NOW())
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('AAAAAA',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('CCCCCC',1)
INSERT INTO `mutant_adns` (adn, mutante_id) VALUES ('GGGGGG',1)