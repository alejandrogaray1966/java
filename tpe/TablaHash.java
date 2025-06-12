package tpe;

import java.util.Hashtable;

public class TablaHash {
		
	    private Hashtable<Integer, Maquina> tablaH;
	    
	    public TablaHash() {
	    	this.tablaH = new Hashtable<>();
	    }
	    
	    public void agregar(Integer clave, Maquina maquina) {
	    	if (!this.encontrar(clave)) {
	    		this.tablaH.put(clave, maquina);
	        	System.out.println("Se agregó con ÉXITO: " + maquina.getNombre());
	    	} else {
	    		System.out.println("NO se pudo agregar. Clave ya existente: " + clave);
	    	}
	    }
	    
	    public boolean encontrar(Integer clave) {
	    	return this.tablaH.containsKey(clave);
	    }
	    
	    public Maquina acceder(Integer clave) {
	    	return this.tablaH.get(clave);
	    }
	    
	    public boolean borrar(Integer clave) {
	    	return this.tablaH.remove(clave) != null;
	    }

	    public Integer cantidad() {
	    	return this.tablaH.size();
	    }
	    
	    public void clear() {
	    	this.tablaH.clear();
	    	return;
	    }

	    @Override
		public String toString() {
	    	System.out.println(" \n \t \t Listado completo:");
	    	this.tablaH.forEach((clave, maquina) -> {
	    		System.out.println("Clave: " + clave + " - " + maquina);
	    	});
	    	return "Fin";
	    }
  
}
