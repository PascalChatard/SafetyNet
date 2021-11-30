package com.safetynet.apirest.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ListChildByAddress, bean permettant de mapper la liste des enfants d'un foyer
 * et la liste des autres membres du foyer. Necessaire depuis l'URL
 * http://localhost:8080/ChildAlert?address=<address>
 *
 */

@NoArgsConstructor
@Data
public class ListChildByAddress {

	/**
	 * la liste des enfants d'un foyer, nom, prenom et age
	 *
	 */
	private List<IdentityAge> children;

	/**
	 * la liste des adultes d'un foyer, nom et prenom
	 *
	 */
	private List<Identity> adults;
}
