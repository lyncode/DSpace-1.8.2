/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.purl.sword.base;

/**
 * List of the namespaces that are used by SWORD. 
 * 
 * Last updated on: $Date: 2010-11-11 23:34:07 -0600 (Thu, 11 Nov 2010) $
 * 
 * @author Neil Taylor
 * @version $Revision: 5845 $
 *
 */
public interface Namespaces {

	/**
	 * Atom Publishing Protocol (APP) Namespace. 
	 */
	public static final String NS_APP = "http://www.w3.org/2007/app";
	
	/**
    * APP Prefix. 
    */
   public static final String PREFIX_APP = "app";
	
	/**
	 * ATOM Namespace.
	 */
	public static final String NS_ATOM = "http://www.w3.org/2005/Atom";
	
	/**
	 * ATOM Prefix. 
	 */
	public static final String PREFIX_ATOM = "atom";
	
	/**
	 * Sword Namespace. 
	 */
	public static final String NS_SWORD = "http://purl.org/net/sword/";
	
	/**
    * SWORD Prefix. 
    */
   public static final String PREFIX_SWORD = "sword";
   
   /**
    * DC Terms Namespace.
    */
	public static final String NS_DC_TERMS = "http://purl.org/dc/terms/";
	
	/**
    * DC Terms Prefix. 
    */
   public static final String PREFIX_DC_TERMS = "dcterms";
   
}
