/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2012 The International Cooperation for the Integration of 
 * Processes in  Prepress, Press and Postpress (CIP4).  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        The International Cooperation for the Integration of 
 *        Processes in  Prepress, Press and Postpress (www.cip4.org)"
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of 
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior written
 *    permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For
 * details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR
 * THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the The International Cooperation for the Integration 
 * of Processes in Prepress, Press and Postpress and was
 * originally based on software 
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG 
 * copyright (c) 1999-2001, Agfa-Gevaert N.V. 
 *  
 * For more information on The International Cooperation for the 
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *  
 * 
 */
package org.cip4.printtalk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.util.StringUtil;

/**
 * 
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public abstract class AbstractPrintTalk
{

	protected KElement theElement;
	protected final Log log;

	/**
	 * @param theElement
	 */
	public AbstractPrintTalk(KElement theElement)
	{
		super();
		log = LogFactory.getLog(getClass());
		this.theElement = theElement;
	}

	/**
	 * 
	 */
	public AbstractPrintTalk()
	{
		super();
		log = LogFactory.getLog(getClass());
	}

	/**
	 *  
	 * @param root
	 */
	protected void setRoot(KElement root)
	{
		theElement = root;
	}

	/**
	 *  
	 * get the root element 
	 * @return
	 */
	public KElement getRoot()
	{
		return theElement;
	}

	/**
	 *  initialize
	 */
	public void init()
	{
		// nop
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return theElement == null ? "null" : theElement.toString();
	}

	/**
	 *  
	 * @param description
	 */
	public void setDescriptiveName(String description)
	{
		setAttribute(AttributeName.DESCRIPTIVENAME, description);
	}

	/**
	 *  
	 * @return description
	 */
	public String getDescriptiveName()
	{
		return getAttribute(AttributeName.DESCRIPTIVENAME);
	}

	/**
	 * 
	 * get an attribute from this
	 * 
	 * @param strLocalName
	 * @return
	 */
	public String getAttribute(String strLocalName)
	{
		return theElement.getAttribute(strLocalName, null, null);
	}

	/**
	 * 
	 * set an attribute in this
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, String value)
	{
		theElement.setAttribute(key, value);
	}

	/**
	 * 
	 * append an element
	 * @param elementName
	 * @return
	 */
	public KElement appendElement(String elementName)
	{
		return theElement.appendElement(elementName);
	}

	/**
	 * 
	 * get an element, create it if it does not yet exist
	 * @param nodeName
	 * @return
	 */
	public KElement getCreateElement(String nodeName)
	{
		return theElement.getCreateElement(nodeName);
	}

	/**
	 * 
	 * get an element, create it if it does not yet exist
	 * @param nodeName
	 * @return
	 */
	public KElement getElement(String nodeName)
	{
		return theElement.getElement(nodeName);
	}

	/**
	 * 
	 * @param path
	 * @param value
	 */
	public void setXPathValue(String path, String value)
	{
		theElement.setXPathValue(path, value);
	}

	/**
	 * 
	 * @param path
	 * @param value
	 */
	public void setXPathAttribute(String path, String value)
	{
		theElement.setXPathAttribute(path, value);
	}

	/**
	 * 
	 * @param path
	 * @param def
	 * @return
	 */
	public String getXPathAttribute(String path, String def)
	{
		return theElement == null ? null : theElement.getXPathAttribute(path, def);
	}

	/**
	 *  
	 * @param path
	 * @return
	 */
	public KElement getXPathElement(String path)
	{
		return theElement == null ? null : theElement.getXPathElement(path);
	}

	/**
	 *  
	 * @param path
	 * @return
	 */
	public KElement getCreateXPathElement(String path)
	{
		return theElement.getCreateXPathElement(path);
	}

	/**
	 *get the contents of a comment
	 * @param elemName
	 * @return
	 */
	public String getTElem(String elemName)
	{
		KElement payTerm = getElement(elemName);
		return payTerm == null ? null : StringUtil.getNonEmpty(payTerm.getText());
	}

	/**
	 * Create text element and set its value
	 * @param elemName 
	 * @param s
	 */
	public void setTElem(String elemName, String s)
	{
		getCreateElement(elemName).setText(s);
	}

}
