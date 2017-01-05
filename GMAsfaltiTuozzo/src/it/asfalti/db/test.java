package it.asfalti.db;

import java.util.ArrayList;

import it.asfalti.javabean.ComposizioneBean;
import it.asfalti.javabean.OperazioneCompletataBean;

public class test {

	public static void main(String[] args) {
		GetInformation information=new DBInformation();
		ArrayList<OperazioneCompletataBean> a=information.getOperazioniComp("mag001");
		for(OperazioneCompletataBean o:a){
			System.out.println(o.getIdM()+" "+o.getIdOp()+" "+o.getTipo()+" "+o.getDa_a());
			for(ComposizioneBean c:o.getListaProdotti()){
				System.out.println(c.getQuantita()+" "+c.getProdotto().getId());
			}
		}
	    

	}

}
