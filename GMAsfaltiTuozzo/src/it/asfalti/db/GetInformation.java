package it.asfalti.db;

import it.asfalti.javabean.MagazzinoBean;

public interface GetInformation {
	public MagazzinoBean checkLogin(String username, String password);
}
