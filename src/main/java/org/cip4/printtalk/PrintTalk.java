/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2014 The International Cooperation for the Integration of 
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

import org.cip4.jdflib.core.DocumentJDFImpl;
import org.cip4.jdflib.core.JDFAudit;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.HeaderBase.EnumHeaderType;

/**
 * Class to wrap the PrintTalk Root element
 * 
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class PrintTalk extends AbstractPrintTalk
{
	/**
	 * 
	 */
	public static final String PRINT_TALK = "PrintTalk";
	/**
	 * 
	 */
	public static final String MIME_PRINT_TALK = "application/vnd.cip4-ptk+xml";

	/**
	 * 
	 * types of printtalk header
	 * @author rainer prosi
	 * @date Jan 3, 2011
	 */
	public static enum Header
	{
		/** */
		From,
		/** */
		To,
		/** */
		Sender
	}

	/**
	 * 
	 * business object types
	 * @author rainer prosi
	 * @date Jan 3, 2011
	 */
	public static enum EnumBusinessObject
	{
		/** */
		RFQ,
		/** */
		Quotation,
		/** */
		PurchaseOrder,
		/** */
		Confirmation,
		/** */
		Cancellation,
		/** */
		Refusal,
		/** */
		OrderStatusRequest,
		/** */
		OrderStatusResponse,
		/** */
		ProofApprovalRequest,
		/** */
		ProofApprovalResponse,
		/** */
		Invoice,
		/** */
		ReturnJob,
		/** */
		StockLevelRequest,
		/** */
		StockLevelResponse
	}

	private int version = 3;

	/**
	 * 
	 * return the PrintTalk element for theElement id TheElement is a printtalk element
	 * @param theElement
	 * @return
	 */
	public static PrintTalk getPrintTalk(KElement theElement)
	{
		if (theElement == null || !PRINT_TALK.equalsIgnoreCase(theElement.getLocalName()))
			return null;
		return new PrintTalk(theElement);
	}

	/**
	 * create a printtalk helper
	 * @param theElement
	 */
	public PrintTalk(KElement theElement)
	{
		super(theElement);
	}

	/**
	 * create a new printtalk helper
	 *  
	 */
	public PrintTalk()
	{
		super(null);
		XMLDoc doc = new XMLDoc(PRINT_TALK, getNamespaceURI());
		doc = new JDFDoc(doc);
		((DocumentJDFImpl) doc.getMemberDocument()).bInitOnCreate = true;
		setRoot(doc.getRoot());
		init();
	}

	/**
	 * 
	 * get the correct namespace uri string
	 * @param version 10=1.0 etc
	 * @return
	 */
	public static String getNamespaceURI(int version)
	{
		if (version == 0)
			version = 13;
		else if (version <= 2)
			version *= 10;
		return "http://www.printtalk.org/schema_" + version;
	}

	/**
	 * 
	 * get the correct namespace uri string
	 * @return
	 */
	public String getNamespaceURI()
	{
		return getNamespaceURI(version);
	}

	/**
	 * initialize a new printtalk
	 *  
	 */
	@Override
	public void init()
	{
		super.init();
		theElement.setAttribute("version", getVersionString("."));
		String dateTimeISO = new JDFDate().getDateTimeISO();
		theElement.setAttribute("Timestamp", dateTimeISO);
		theElement.setAttribute("payloadID", JDFAudit.software() + dateTimeISO);
	}

	/**
	 *  
	 * @param sep 
	 * @return
	 */
	private String getVersionString(String sep)
	{
		return (getVersion() < 10) ? ("1" + sep + getVersion()) : ("2" + sep + (getVersion() - 20));
	}

	/**
	 * 
	 * set a new credential value
	 * @param header type of header
	 * @param domain domain name
	 * @param identity Identity value
	 */
	public void setCredential(Header header, String domain, String identity)
	{
		String type = header.name();
		getCreateXPathElement("Header/" + type + "/Credential[@domain=\"" + domain + "\"]").getCreateElement("Identity").setText(identity);
	}

	/**
	 * Setter for version attribute.
	 * @param version the version to set
	 */
	public void setVersion(int version)
	{
		this.version = version;
	}

	/**
	 * Getter for version attribute.
	 * @return the version
	 */
	public int getVersion()
	{
		return version;
	}

	/**
	 * Getter for Timestamp attribute.
	 * @return the version
	 */
	public JDFDate getTimestamp()
	{
		return theElement == null ? null : JDFDate.createDate(theElement.getAttribute("Timestamp", null, null));
	}

	/**
	 * 
	 *appends a request with an initialized Business Object
	 * @param bo the business object type
	 * @param ref the printtalk object that this references  
	 * @return the newly created BusinessObject, null if unsuccessful
	 * @throws IllegalArgumentException if bo already exists
	 */
	public BusinessObject appendRequest(EnumBusinessObject bo, PrintTalk ref) throws IllegalArgumentException
	{
		BusinessObject oldBO = getBusinessObject();
		if (oldBO != null)
			throw new IllegalArgumentException("BusinessObject already exists: " + oldBO.theElement.getLocalName());

		String boName = bo.name();
		KElement e = theElement.getCreateElement("Request").appendElement(boName);
		final BusinessObject businessObject = BusinessObject.getBusinessObject(e);
		businessObject.init();
		businessObject.setRef(ref);
		return businessObject;
	}

	/**
	 * get the business object from a request
	 * @return
	 */
	public BusinessObject getRequest()
	{
		return getBusinessObject();
	}

	/**
	 * get the business object from a request
	 * @return
	 */
	public BusinessObject getBusinessObject()
	{
		KElement request = getElement("Request");
		KElement oldBO = request == null ? null : request.getFirstChildElement();
		return BusinessObject.getBusinessObject(oldBO);
	}

	/**
	 * get the header from a request
	 * @param headerType the type (From / to / Sender)
	 * @return
	 */
	public HeaderBase getHeader(EnumHeaderType headerType)
	{
		KElement request = headerType == null ? null : getElement("Header");
		KElement header = request == null ? null : request.getElement(headerType.name());
		return header == null ? null : new HeaderBase(header);
	}

	/**
	 * @see org.cip4.printtalk.AbstractPrintTalk#getPrintTalk()
	 */
	@Override
	public PrintTalk getPrintTalk()
	{
		return this;
	}
}
