/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of
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
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.util.ContainerUtil;
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

	/** **/
	public static final String ATTR_JOBIDREF = "JobIDRef";

	/**
	 * @param theElement
	 */
	public AbstractPrintTalk(final KElement theElement)
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
	protected void setRoot(final KElement root)
	{
		theElement = root;
	}

	/**
	 *
	 * get the root element that represints myself
	 * @return
	 */
	public KElement getRoot()
	{
		return theElement;
	}

	/**
	 *
	 * get the root element
	 * @return
	 */
	public PrintTalk getPrintTalk()
	{
		final KElement pt = theElement == null ? null : theElement.getDeepParent(PrintTalk.PRINT_TALK, 0);
		return PrintTalk.getPrintTalk(pt);
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
	public void setDescriptiveName(final String description)
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
	 * @return comment
	 */
	public String getComment()
	{
		return getTElem(ElementName.COMMENT);
	}

	/**
	 *
	 * @param comment
	 */
	public void setComment(final String comment)
	{
		final KElement c = getCreateElement(ElementName.COMMENT);
		c.setText(comment);
	}

	/**
	 *
	 * get an attribute from this, null if not there
	 *
	 * @param strLocalName
	 * @return
	 */
	public String getAttribute(final String strLocalName)
	{
		return getAttribute(strLocalName, null);
	}

	/**
	 *
	 * get an attribute from this, def if not there
	 *
	 * @param strLocalName
	 * @param def
	 * @return
	 */
	public String getAttribute(final String strLocalName, final String def)
	{
		return theElement == null ? def : StringUtil.getNonEmpty(theElement.getAttribute(strLocalName, null, def));
	}

	/**
	 *
	 * set an attribute in this
	 * @param key
	 * @param value
	 */
	public void setAttribute(final String key, final String value)
	{
		theElement.setAttribute(key, value);
	}

	/**
	 *
	 * append an element
	 * @param elementName
	 * @return
	 */
	public KElement appendElement(final String elementName)
	{
		return theElement.appendElement(elementName);
	}

	/**
	 *
	 * @param xjdf the xjdf to set
	 */
	void setXJDF(final XJDFHelper xjdf)
	{
		final KElement xjdfe = xjdf == null ? null : xjdf.getRoot();
		if (xjdfe != null)
		{
			theElement.removeChildren(XJDFConstants.XJDF, null, null);
			theElement.copyElement(xjdfe, null);
		}
	}

	/**
	 *
	 * get an element, create it if it does not yet exist
	 * @param nodeName
	 * @return
	 */
	public KElement getCreateElement(final String nodeName)
	{
		return theElement.getCreateElement(nodeName);
	}

	/**
	 *
	 * get an element, create it if it does not yet exist
	 * @param nodeName
	 * @param n index of the element
	 * @return
	 */
	public KElement getCreateElement(final String nodeName, final int n)
	{
		return theElement.getCreateElement(nodeName, null, n);
	}

	/**
	 *
	 * get an element, create it if it does not yet exist
	 * @param nodeName
	 * @return
	 */
	public KElement getElement(final String nodeName)
	{
		return theElement.getElement(nodeName);
	}

	/**
	 *
	 * get an element, create it if it does not yet exist
	 * @param nodeName
	 * @param nSkip
	 * @return
	 */
	public KElement getElement(final String nodeName, final int nSkip)
	{
		return theElement == null ? null : theElement.getElement(nodeName, null, nSkip);
	}

	/**
	 *
	 * get all elements,
	 * @param nodeName
	 * @return
	 */
	public VElement getElements(final String nodeName)
	{
		final VElement v = theElement == null ? null : theElement.getChildElementVector(nodeName, null);
		if (v == null || v.size() == 0)
			return null;
		return v;
	}

	/**
	 *
	 * get number of  all elements,
	 * @param nodeName
	 * @return
	 */
	public int numElements(final String nodeName)
	{
		return theElement == null ? 0 : theElement.numChildElements(nodeName, null);
	}

	/**
	 *
	 * @param path
	 * @param value
	 */
	public void setXPathValue(final String path, final String value)
	{
		theElement.setXPathValue(path, value);
	}

	/**
	 *
	 * @param path
	 * @param value
	 */
	public void setXPathAttribute(final String path, final String value)
	{
		theElement.setXPathAttribute(path, value);
	}

	/**
	 *
	 * @param path
	 * @param def
	 * @return
	 */
	public String getXPathAttribute(final String path, final String def)
	{
		return theElement == null ? def : theElement.getXPathAttribute(path, def);
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public KElement getXPathElement(final String path)
	{
		return theElement == null ? null : theElement.getXPathElement(path);
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public KElement getCreateXPathElement(final String path)
	{
		return theElement.getCreateXPathElement(path);
	}

	/**
	 *get the contents of a text element
	 * @param elemName
	 * @return
	 */
	public String getTElem(final String elemName)
	{
		final KElement payTerm = getElement(elemName);
		return payTerm == null ? null : StringUtil.getNonEmpty(payTerm.getText());
	}

	/**
	 * Create text element and set its value
	 * @param elemName
	 * @param s
	 */
	public void setTElem(final String elemName, final String s)
	{
		getCreateElement(elemName).setText(s);
	}

	/**
	 * we are equal if the underlying element is equal
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object arg0)
	{
		if (!(arg0 instanceof AbstractPrintTalk))
			return false;
		return ContainerUtil.equals(getRoot(), ((AbstractPrintTalk) arg0).getRoot());
	}

	/**
	 * we are equal if the underlying element is equal
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return theElement == null ? 0 : theElement.hashCode();
	}

	/**
	 *
	 * @param key
	 * @param value
	 */
	public void setAttribute(final String key, final int value)
	{
		theElement.setAttribute(key, value, null);
	}

	/**
	 *
	 * @param key
	 * @param value
	 */
	public void setAttribute(final String key, final double value)
	{
		theElement.setAttribute(key, value, null);
	}

	/**
	 *
	 * @param key
	 * @param b
	 */
	public void setAttribute(final String key, final boolean b)
	{
		theElement.setAttribute(key, b, null);
	}

	/**
	 *
	 * @return
	 */
	public JDFAttributeMap getAttributeMap()
	{
		return theElement == null ? null : theElement.getAttributeMap();
	}

	/**
	 *
	 * @param attrib
	 * @param def
	 * @return
	 */
	public boolean getBoolAttribute(final String attrib, final boolean def)
	{
		return theElement == null ? def : theElement.getBoolAttribute(attrib, null, def);
	}

	/**
	 *
	 * @param attrib
	 * @param def
	 * @return
	 */
	public double getRealAttribute(final String attrib, final double def)
	{
		return theElement == null ? def : theElement.getRealAttribute(attrib, null, def);
	}

}
