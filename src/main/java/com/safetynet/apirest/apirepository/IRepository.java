package com.safetynet.apirest.apirepository;

public interface IRepository<E> {

	/**
	 * Supprime l'objet de type E positionne a l'index id, appelle la methode remove
	 * de la liste de E depuis le dataSrc.
	 * 
	 * @param id, le numero d'index de lobjet E a supprimer de la base de donnees
	 * @return une reference a l'objet supprime dans dataSrc
	 * @exception remonte l'exception IndexOutOfBoundsException
	 * 
	 */
	public E deleteById(int id) throws IndexOutOfBoundsException;

	/**
	 * Ajoute l'objet E dans le DataSrc dataSrc, appelle la methode add de la liste
	 * de E depuis le dataSrc.
	 * 
	 * @param element, l'objet E a ajouter dans la base de donnees
	 * @return une reference a l'objet ajouter dans dataSrc
	 * 
	 */
	public E save(E element) throws NullPointerException;

	/**
	 * Update l'objet E positionne a l'index id dans le DataSrc dataSrc, appelle la
	 * methode set de la liste de E depuis le dataSrc.
	 * 
	 * @param id, index de l'objet E a modifier
	 * @param element, l'objet E a modifier dans la base de donnees
	 * @return une reference a l'objet modifier dans dataSrc
	 * 
	 */
	public E update(int id, E element) throws IndexOutOfBoundsException, NullPointerException;

}
