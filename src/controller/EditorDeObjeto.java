package controller;

import excepciones.OlimpiadasException;

public interface EditorDeObjeto<E> {
	
	
	/**
	 * Comprueba los datos antes de subirlos
	 */
    public void comprobarDatos() throws OlimpiadasException;
    
    /**
     * Método para construir un objeto.
     * @return El objeto construido.
     * @throws OlimpiadasException Si los datos no son válidos.
     */
    public E construirObjeto() throws OlimpiadasException;
    
    /**
     * Método para rellenar el editor.
     */
    public void rellenarEditor();
    
    public <T extends EditorDeObjeto<E>> T setSeleccionado(E seleccionado);
}

