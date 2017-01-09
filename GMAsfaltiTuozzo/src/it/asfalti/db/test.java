package it.asfalti.db;

import java.util.ArrayList;

import it.asfalti.javabean.OperazioneSospesaBean;

public class test {

	public static void main(String[] args) {
		GetInformation information=new DBInformation();
		System.out.println(information.registraOpSosp("1", "mag001"));
	    

	}

}
