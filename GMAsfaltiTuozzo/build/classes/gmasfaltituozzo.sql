drop database if exists gmasfalti;
create database gmasfalti;
	use gmasfalti;

	create table magazzino(
		idM char(6) not null,
		descrizioneM varchar(200) not null,
		citta varchar(20) not null,
		via varchar(20) not null,
		cap varchar(5) not null,
		nCivico varchar(3) not null,
		passwordM varchar(25) not null,
		tipo enum('admin','mag') not null default 'mag',
		primary key(idM)
		);

	create table prodotto(
		idProduct varchar(10) not null,
		unitaDiMisura enum('Pz','Kg','Lt','Mq','Ml','Rt','') not null default '',
		descrizioneP varchar(100) not null,
		

		primary key(idProduct)
		);

	create table disponibilita(
		quantita float(10,2) not null default 0.0,
		idM char(6) not null,
		idProduct varchar(10) not null,
		
		primary key(idM,idProduct),
		foreign key(idM) references magazzino(idM) on update cascade,
		foreign key(idProduct) references prodotto(idProduct) on update cascade
		);

	create table operazioniCompletate(
		idOperazione int unsigned auto_increment,
		idM char(6) not null,
		tipo enum('Carico','Scarico') not null,
		data datetime not null,
		da_a char(6) not null,
		
		primary key(idOperazione),
		foreign key(idM) references magazzino(idM) on update cascade,
		foreign key(da_a) references magazzino(idM) on update cascade
		);

	create table composizioneOpCompl(
		idOperazione int unsigned not null,
		idProduct varchar(10) not null,
		quantita float(10,2) not null,

		primary key(idOperazione,idProduct),
		foreign key(idOperazione) references operazioniCompletate(idOperazione) on update cascade on delete cascade,
		foreign key(idProduct) references prodotto(idProduct) on update cascade
		);

	create table operazioniInSospeso(
		idOperazione int unsigned auto_increment,
		idM char(6) not null,
		tipo enum('Carico','Scarico') not null,
		stato enum('Elaborazione','Spedita','Consegnata'),
		data datetime not null,
		da_a char(6) not null,
		
		primary key(idOperazione,idM,da_a),
		foreign key(idM) references magazzino(idM) on update cascade,
		foreign key(da_a) references magazzino(idM) on update cascade
		);

	create table composizioneOpSosp(
		idOperazione int unsigned not null,
		idProduct varchar(10) not null,
		quantita float(10,2) not null,

		primary key(idOperazione,idProduct),
		foreign key(idOperazione) references operazioniInSospeso(idOperazione) on update cascade on delete cascade,
		foreign key(idProduct) references prodotto(idProduct) on update cascade
		);