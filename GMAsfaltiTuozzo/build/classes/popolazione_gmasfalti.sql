insert into magazzino values ("admin0","Amministratore del sistema",""," "," "," ","admin",'admin');
insert into magazzino values ("fornit","Fornitore generico a cui intestare carichi non provenienti da altri magazzini", " "," ", " ", " ","fornit",'mag');
insert into magazzino values ("mag001","Magazzino numero 1","Roma", "Via Napoli","00060","125","mag0001",'mag');
insert into magazzino values ("mag002","Magazzino numero 2","Milano", "Via Roma","00080","120","mag0002",'mag');
insert into magazzino values ("mag003","Magazzino numero 3","Napoli", "Via Milano","00040","130","mag0003",'mag');
insert into magazzino values ("client","Cliente generico a cui intestare gli scarichi", " "," ", " ", " ","client",'mag');


insert into prodotto values("PA33003",4,"Cartongesso normale 120x300");
INSERT INTO Prodotto  VALUES ("PA3200F",4,"Cartongesso ignifugo 120x200");
INSERT INTO Prodotto  VALUES ("PA3300Y",4,"Cartongesso idrorepellente 120x300");
INSERT INTO Prodotto  VALUES ("EPS15050",4,"Polistirolo bianco alta densita da 5cm 100x50");
INSERT INTO Prodotto  VALUES ("EPS10030",4,"Polistirolo bassa densita da 3cm 100x50");
INSERT INTO Prodotto  VALUES ("EK400",4,"Sughero tostato da 4cm 100x50");
INSERT INTO Prodotto  VALUES ("CORKPAN",4,"Pannello in fibra di legno");
INSERT INTO Prodotto  VALUES ("SCRC90",1,"Scrigno da 90 a scomparsa per cartongesso");
INSERT INTO Prodotto  VALUES ("SCRM70",1,"Scrigno da 70 a scomparsa per muratura");
INSERT INTO Prodotto  VALUES ("MD003",1,"Moduli 3cm");
INSERT INTO Prodotto  VALUES ("0010P",1,"Conglomerato a freddo");
INSERT INTO Prodotto  VALUES ("ST25",1,"Stucco in polvere da 25Kg");
INSERT INTO Prodotto  VALUES ("GUAG20",1,"Guaina liquida grigia da 20Kg");
INSERT INTO Prodotto  VALUES ("RUSFEIN25",1,"Intonachino bianco/colorato da 25Kg");
INSERT INTO Prodotto  VALUES ("NF57002B",1,"Viti per cartongesso da 2.5cm scatolo da 500pz");
INSERT INTO Prodotto  VALUES ("M49-300",5,"Mondante da 49cm per cartongesso da 3mt");
INSERT INTO Prodotto  VALUES ("SPL2",5,"Profilo angolare per cartongesso");
INSERT INTO Prodotto  VALUES ("SUPERGUM",6,"Guaina bituminosa 1x10 mt");
INSERT INTO Prodotto  VALUES ("SILENT",6,"Guaina bituminosa per isolamento acustico 1x10 mt");
INSERT INTO Prodotto  VALUES ("TNT",6,"Tessuto non tessuto per proteggere pannelli di fibra");

insert into disponibilita values(10.0,"mag001","PA33003");
insert into disponibilita values(5.0,"mag001","PA3200F");
insert into disponibilita values(7.0,"mag001","PA3300Y");
insert into disponibilita values(100.0,"mag001","TNT");
insert into disponibilita values(50.0,"mag001","GUAG20");

insert into disponibilita values(10.0,"mag002","PA33003");
insert into disponibilita values(5.0,"mag002","PA3200F");
insert into disponibilita values(7.0,"mag002","PA3300Y");
insert into disponibilita values(100.0,"mag002","TNT");
insert into disponibilita values(50.0,"mag002","GUAG20");

insert into disponibilita values(10.0,"mag003","PA33003");
insert into disponibilita values(5.0,"mag003","PA3200F");
insert into disponibilita values(7.0,"mag003","PA3300Y");
insert into disponibilita values(100.0,"mag003","TNT");
insert into disponibilita values(50.0,"mag003","GUAG20");

insert into operazioniCompletate(idM,tipo,data,da_a) values ("mag001","Carico",'2016-12-15',"fornit");
insert into operazioniCompletate(idM,tipo,data,da_a) values ("mag002","Carico",'2016-12-15',"fornit");
insert into operazioniCompletate(idM,tipo,data,da_a) values ("mag003","Carico",'2016-12-15',"fornit");

insert into composizioneOpCompl values(1,"TNT",100.0);
insert into composizioneOpCompl values(1,"PA33003",10.0);
insert into composizioneOpCompl values(1,"PA3200F",5.0);
insert into composizioneOpCompl values(1,"PA3300Y",7.0);
insert into composizioneOpCompl values(1,"GUAG20",50.0);

insert into composizioneOpCompl values(2,"PA33003",10.0);
insert into composizioneOpCompl values(2,"PA3200F",5.0);
insert into composizioneOpCompl values(2,"PA3300Y",7.0);
insert into composizioneOpCompl values(2,"TNT",100.0);
insert into composizioneOpCompl values(2,"GUAG20",50.0);

insert into composizioneOpCompl values(3,"PA33003",10.0);
insert into composizioneOpCompl values(3,"PA3200F",5.0);
insert into composizioneOpCompl values(3,"PA3300Y",7.0);
insert into composizioneOpCompl values(3,"TNT",100.0);
insert into composizioneOpCompl values(3,"GUAG20",50.0);






/* Aggiuntive */

insert into operazioniInSospeso(idM,tipo,stato,data,da_a) values ("mag002","Scarico",1,'2016-12-15',"mag001");
insert into composizioneOpSosp values(1,"TNT",100.0);
insert into composizioneOpSosp values(1,"PA33003",10.0);
insert into composizioneOpSosp values(1,"PA3200F",5.0);
insert into composizioneOpSosp values(1,"PA3300Y",7.0);
insert into composizioneOpSosp values(1,"GUAG20",50.0);

/*mag002 deve ricevere da mag002 merce */
insert into operazioniInSospeso(idM,tipo,stato,data,da_a) values ("mag001","Carico",2,'2016-12-15',"mag002");
insert into composizioneOpSosp values(2,"TNT",100.0);
insert into composizioneOpSosp values(2,"PA33003",10.0);
insert into composizioneOpSosp values(2,"PA3200F",5.0);
insert into composizioneOpSosp values(2,"PA3300Y",7.0);
insert into composizioneOpSosp values(2,"GUAG20",50.0);

